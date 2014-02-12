'use strict';

angular.module('pi4jfrontend')
    .factory('localStorageService', function () {
        // Service logic
        // ...

        var allPlugs = JSON.parse(localStorage.getItem("allPlugs"));

        var updatePlugsInLocalStorage = function (serverList) {
            if(allPlugs){
                allPlugs.splice(0, allPlugs.length);
            }else{
                allPlugs = [];
            }

            for (var i = 0; i < serverList.length; i++) {
                allPlugs.push(serverList[i]);
            }

            localStorage.setItem("allPlugs", JSON.stringify(allPlugs));

        }

        var getAllPlugs = function(){
            return allPlugs;
        }


        // Public API here
        return {
            updatePlugsInLocalStorage: updatePlugsInLocalStorage,
            getAllPlugs: getAllPlugs
        }

    });
