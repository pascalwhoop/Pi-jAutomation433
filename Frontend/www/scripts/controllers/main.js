'use strict';

angular.module('pi4jfrontend')
    .controller('MainCtrl', function ($scope, backendService, localStorageService) {


        $scope.init = function () {
            backendService.getAllUserStates(function(result){
                $scope.userStates = result;
            })
        }

        //trigger at the end
        $scope.init();
    });
