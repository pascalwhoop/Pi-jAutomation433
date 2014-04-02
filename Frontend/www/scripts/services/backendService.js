'use strict';

angular.module('pi4jfrontend')
    .factory('backendService', function ($http, $resource, localStorageService) {

        //connection data for backend
        var ip = localStorage.getItem("backendIP");
        var username = localStorage.getItem("username");

        var port = "8080";
        var baseURI = "/IoT_Spring_BPM/service/";


        /* Local Network Services*/

        var getLocalNetworkNodes = function(callback){
            var resource = $resource("http://" + ip + ":" + port + baseURI + "networkdevices/getall")
            var result = resource.get()
             result.$promise.then(function(result){
                callback(result);
            });
        }

        var getOwnIpAddress = function(callback){
            $http.get("http://" + ip + ":" + port + baseURI + "networkdevices/getownip")
                .success(callback);
        }

        /* plug services */

        var fetchPlugsFromServerAndUpdateLocalStorage = function () {


            var backend = $resource("http://" + ip + ":" + port + baseURI+ 'switch/getAllPlugsAvailable');
            var result = backend.query();
            result.$promise.then(function(result){
                localStorageService.updatePlugsInLocalStorage(result);
            })
        };



        var plugOff = function (plug) {
            var promise =  $http(
                {
                    method: 'PUT',
                    url: "http://" + ip + ":" + port + baseURI+ 'switch/deactivate',
                    data: plug,
                    headers: {'Content-Type': 'application/json'}
                }
            )

            promise.then(function(response){
                localStorageService.updateSpecificPlugInLocalStorage(response.data);
            })

            return promise;
        }

        var plugOn = function (plug) {
            var promise =  $http(
                {
                    method: 'PUT',
                    url: "http://" + ip + ":" + port + baseURI+ 'switch/activate',
                    data: plug,
                    headers: {'Content-Type': 'application/json'}
                }
            )

            promise.then(function(response){
                localStorageService.updateSpecificPlugInLocalStorage(response.data);
            })

            return promise;
        }


        var syncWithLocalStorage = function (promise) {
            //TODO sync with localStorage


        }

        var submitPlug = function (plug) {
            return $http({
                method: 'POST',
                url: "http://" + ip + ":" + port + baseURI+ "switch/addplug",
                data: plug
            }).then(function (response) {
                    return response.data;
                }
            )
        };

        var deletePlug = function(plug){
            return $http({
                method: 'DELETE',
                url: "http://" + ip + ":" + port + baseURI+ "switch/deleteplug/" + plug.id
            }).then(function (response) {
                    if(response.status == 200){
                        localStorageService.removePlugFromLocalStorage(plug);
                    }
                }
            )
        }

        /* user services*/
        var getAllUsers = function(callback){
            var resource = $resource("http://" + ip + ":" + port + baseURI + "user/getall")
            var result = resource.query();
             result.$promise.then(function(result){
                callback(result);
            })
        }


        var submitNewUser = function(user, callback){
            $http.put("http://" + ip + ":" + port + baseURI + "user/add", user).success(callback);
        }

        var deleteUser = function(user, callback){
            $http.delete("http://"+ ip + ":" + port + baseURI + "user/delete", {
                data:user
            }).success(callback);
        }

        /* getter and setter e.g. for local storage */

        var setIP = function(newIP){
            ip = newIP;
            localStorage.setItem("backendIP", newIP);
        }

        var getIP = function(){
            return ip;
        }

        var setUsername = function(newUsername){
            username = newUsername;
            localStorage.setItem("username", newUsername);
        }

        var getUsername = function(){
            return username;
        }

        // Public API here
        return {
            fetchPlugsAndUpdateLocalStorage: fetchPlugsFromServerAndUpdateLocalStorage,
            submitPlug: submitPlug,
            plugOff: plugOff,
            plugOn: plugOn,
            setIP: setIP,
            getIP: getIP,
            getUsername: getUsername,
            setUsername: setUsername,
            deletePlug: deletePlug,
            getLocalNetworkNodes:getLocalNetworkNodes,
            getOwnIpAddress:getOwnIpAddress,
            getAllUsers: getAllUsers,
            submitNewUser: submitNewUser,
            deleteUser: deleteUser
        };

    }
);
