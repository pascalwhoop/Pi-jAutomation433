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
            return $http(
                {
                    method: 'PUT',
                    url: getURLString()+ '/deactivate',
                    data: plug,
                    headers: {'Content-Type': 'application/json'}
                }
            )
        }

        var plugOn = function (plug) {
            return $http(
                {
                    method: 'PUT',
                    url: getURLString() + '/activate',
                    data: plug,
                    headers: {'Content-Type': 'application/json'}
                }
            )
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
            getIP: getIP
        };

    }
);
