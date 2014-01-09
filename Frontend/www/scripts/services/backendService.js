'use strict';

angular.module('pi4jfrontend')
    .factory('backendService', function ($http, $resource) {


        var baseURL = "http://192.168.1.102:8080/IoT_Spring_BPM/service/switch";
        //var baseURL = "http://localhost:8080/IoT_Spring_BPM/service/switch";


        var getAllPlugs = function () {
            var Backend = $resource(baseURL + '/getAllPlugsAvailable');
            return Backend.query();
        };

        var plugOff = function (plug) {
            return $http(
                {
                    method: 'PUT',
                    url: baseURL + '/deactivate',
                    data: plug,
                    headers: {'Content-Type': 'application/json'}
                }
            )
        }

        var plugOn = function (plug) {
            return $http(
                {
                    method: 'PUT',
                    url: baseURL + '/activate',
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
                url: baseURL + "/addplug",
                data: plug
            }).then(function (response) {
                    return response.data;
                }
            )
        };


        // Public API here
        return {
            getAllPlugs: getAllPlugs,
            submitPlug: submitPlug,
            plugOff: plugOff,
            plugOn: plugOn
        };

    }
);
