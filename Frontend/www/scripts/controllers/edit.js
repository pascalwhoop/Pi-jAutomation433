'use strict';

angular.module('pi4jfrontend')
    .controller('EditCtrl', function ($scope, backendService) {

        $scope.submitPlug = function(plug){
            backendService.submitPlug(plug);
        }

        $scope.init = function () {

        }

        //trigger at the end
        $scope.init();
    });
