'use strict';

angular.module('pi4jfrontend')
    .controller('SettingsCtrl', function ($scope, backendService) {

        $scope.ip = "";

        $scope.setNewIP = function(){
            backendService.setIP($scope.ip);
        }

        $scope.init = function () {
           $scope.ip = backendService.getIP();
        }

        //trigger at the end
        $scope.init();
    });
