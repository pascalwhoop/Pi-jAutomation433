'use strict';

angular.module('pi4jfrontend')
    .controller('SettingsCtrl', function ($scope, backendService) {

        $scope.ip = "";
        $scope.username = "";

        $scope.setNewIP = function (ip) {
            backendService.setIP(ip);
        }

        $scope.setNewUsername = function (username) {
            backendService.setUsername(username);
        }

        $scope.getLocalNetworkNodes = function () {
            backendService.getLocalNetworkNodes();
        }

        $scope.submitNewUser = function(user){
            backendService.submitNewUser(user, function(response){
                $scope.availableUsers.push(response);
            });
        }

        $scope.deleteUser = function(user){
            backendService.deleteUser(user, function(result){
                if(result){
                    var index = $scope.availableUsers.indexOf(user);
                    $scope.availableUsers.splice(index, 1);
                }
            })
        }

        $scope.init = function () {
            $scope.ip = backendService.getIP();
            $scope.username = backendService.getUsername();

            backendService.getOwnIpAddress(function(result){
                $scope.deviceIp = JSON.parse(result);
            });

            backendService.getLocalNetworkNodes(function(result){
                $scope.localNetworkNodes = result;
            });

            backendService.getAllUsers(function(result){
                $scope.availableUsers = result;
            });
        }

        //trigger at the end
        $scope.init();
    });
