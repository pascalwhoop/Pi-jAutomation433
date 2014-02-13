'use strict';

angular.module('pi4jfrontend')
    .factory('localStorageService', function () {
        // Service logic
        // ...

        var allPlugs = JSON.parse(localStorage.getItem("allPlugs"));
        if (allPlugs == null){
            allPlugs = [];
        }

        var updatePlugsInLocalStorage = function (serverList) {
            if(allPlugs){
                allPlugs.splice(0, allPlugs.length);
            }else{
                allPlugs = [];
            }

            for (var i = 0; i < serverList.length; i++) {
                allPlugs.push(serverList[i]);
            }

            updateLocalStoragePlugs();

        }

        var removePlugFromLocalStorage = function(plug){
            var index = getPlugIndexInArray(plug);
            allPlugs.splice(index, 1);
            updateLocalStoragePlugs();
        }

        var updateSpecificPlugInLocalStorage = function(plug){
            var index= getPlugIndexInArray(plug);
            if(index >= 0){
                allPlugs[index] = plug;
                updateLocalStoragePlugs();
            }
        }

        var getPlugIndexInArray = function(plug){
            if(allPlugs){
                for(var i= 0; i<allPlugs.length;i++){
                    if(allPlugs[i].id == plug.id){
                        return i;
                    }
                }
            }
        }

        var updateLocalStoragePlugs = function(){
            localStorage.setItem("allPlugs", JSON.stringify(allPlugs));
        }

        var getAllPlugs = function(){
            return allPlugs;
        }


        // Public API here
        return {
            updatePlugsInLocalStorage: updatePlugsInLocalStorage,
            getAllPlugs: getAllPlugs,
            removePlugFromLocalStorage: removePlugFromLocalStorage,
            updateSpecificPlugInLocalStorage: updateSpecificPlugInLocalStorage
        }

    });
