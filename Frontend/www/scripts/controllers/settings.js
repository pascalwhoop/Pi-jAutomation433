'use strict';

angular.module('pi4jfrontend')
    .controller('SettingsCtrl', function ($scope, backendService, localStorageService) {

        $scope.ip = "";
        $scope.username = "";

        $scope.setNewIP = function (ip) {
            backendService.setIP(ip);
        }

        $scope.setNewUsername = function (username) {
            backendService.setUsername(username);
        }



        $scope.init = function () {
            $scope.ip = backendService.getIP();
            $scope.username = backendService.getUsername();

            //get users from local storage then get users from backend (backendService will update localStorage
            $scope.users = localStorageService.getUsers();
            backendService.getAllUsers();

        }

        //trigger at the end
        $scope.init();
    });
