'use strict';

angular.module('pi4jfrontend')
    .factory('backendService', function ($http, $resource, localStorageService) {

        var ip = localStorage.getItem("backendIP");

        var portAndUri = ":8080/IoT_Spring_BPM/service/switch";
        //var baseURL = "http://localhost:8080/IoT_Spring_BPM/service/switch";

        var getURLString = function(){
            return "http://"+ip+portAndUri;
        }

        var fetchPlugsFromServerAndUpdateLocalStorage = function () {


            var backend = $resource(getURLString() + '/getAllPlugsAvailable');
            var result = backend.query();
            result.$promise.then(function(result){
                localStorageService.updatePlugsInLocalStorage(result);
            })
        };



        var plugOff = function (plug) {
            var promise =  $http(
                {
                    method: 'PUT',
                    url: getURLString()+ '/deactivate',
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
                    url: getURLString() + '/activate',
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
                url: getURLString() + "/addplug",
                data: plug
            }).then(function (response) {
                    return response.data;
                }
            )
        };

        var deletePlug = function(plug){
            return $http({
                method: 'DELETE',
                url: getURLString() + "/deleteplug/" + plug.id
            }).then(function (response) {
                    if(response.status == 200){
                        localStorageService.removePlugFromLocalStorage(plug);
                    }
                }
            )
        }

        var setIP = function(newIP){
            ip = newIP;

            localStorage.setItem("backendIP", newIP);

        }

        var getIP = function(){
            return ip;
        }


        // Public API here
        return {
            fetchPlugsAndUpdateLocalStorage: fetchPlugsFromServerAndUpdateLocalStorage,
            submitPlug: submitPlug,
            plugOff: plugOff,
            plugOn: plugOn,
            setIP: setIP,
            getIP: getIP,
            deletePlug: deletePlug
        };

    }
);
