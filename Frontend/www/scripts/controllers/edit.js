'use strict';

angular.module('pi4jfrontend')
    .controller('EditCtrl', function ($scope, backendService, localStorageService) {

        $scope.submitPlug = function(plug){
            backendService.submitPlug(plug);
        }

        $scope.deletePlug = function(plug){
            backendService.deletePlug(plug);
        }

        $scope.init = function () {
            $scope.plugs = localStorageService.getAllPlugs();
        }

        //trigger at the end
        $scope.init();
    });
