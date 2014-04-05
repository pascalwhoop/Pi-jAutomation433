'use strict';

angular.module('pi4jfrontend')
    .factory('localStorageService', function () {

        /*initiating*/
        var plugs = JSON.parse(localStorage.getItem("plugs"));
        if (plugs == null) {
            plugs = [];
        }

        var users = JSON.parse(localStorage.getItem("users"));
        if (users == null) {
            users = [];
        }


        /* USERS */
        var setUsers = function (serverList) {
            if (users) {
                users.splice(0, users.length);
            } else {
                users = [];
            }
            for (var i = 0; i < serverList.length; i++) {
                users.push(serverList[i]);
            }
            updateLocalStorageItem("users", users);
        }

        var removeUserFromLocalStorage = function (user) {
            var index = getItemIndexInArrayByItemId(user, users);
            if (index >= 0) {
                users.splice(index, 1);
                updateLocalStorageItem("users", users);
            }
        }

        var updateSpecificUserInLocalStorage = function (user) {
            var index = getItemIndexInArrayByItemId(user, users);
            if (index >= 0) {
                users[index] = user;
            }else{
                users.push(user);
            }
            updateLocalStorageItem("users", users);
        }

        var getUsers = function () {
            return users;
        }


        /* PLUGS */
        var setPlugs = function (serverList) {
            if (plugs) {
                plugs.splice(0, plugs.length);
            } else {
                plugs = [];
            }

            for (var i = 0; i < serverList.length; i++) {
                plugs.push(serverList[i]);
            }

            updateLocalStorageItem("plugs", plugs);


        }

        var removePlugFromLocalStorage = function (plug) {
            var index = getItemIndexInArrayByItemId(plug, plugs);
            if (index >= 0) {
                plugs.splice(index, 1);
                updateLocalStorageItem("plugs", plugs);
            }
        }

        var updateSpecificPlugInLocalStorage = function (plug) {
            var index = getItemIndexInArrayByItemId(plug, plugs);
            if (index >= 0) {
                plugs[index] = plug;
            } else {
                plugs.push(plug);
            }
            updateLocalStorageItem("plugs", plugs);

        }


        var getPlugs = function () {
            return plugs;
        }





        /* utility functions */

        var getItemIndexInArrayByItemId = function (item, array) {
            if (array) {
                for (var i = 0; i < array.length; i++) {
                    if (array[i].id == item.id) {
                        return i;
                    }
                }
            }
            return -1;
        }


        var updateLocalStorageItem = function (key, value) {
            localStorage.setItem(key, JSON.stringify(value));
        }


        // Public API here
        return {
            setPlugs: setPlugs,
            getPlugs: getPlugs,
            removePlugFromLocalStorage: removePlugFromLocalStorage,
            updateSpecificPlugInLocalStorage: updateSpecificPlugInLocalStorage,
            setUsers: setUsers,
            getUsers: getUsers,
            removeUserFromLocalStorage: removeUserFromLocalStorage,
            updateSpecificUserInLocalStorage: updateSpecificUserInLocalStorage

        }

    });
