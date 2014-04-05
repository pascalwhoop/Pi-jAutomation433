'use strict';

angular.module('pi4jfrontend')
    .controller('GroupCtrl', function ($scope, backendService, localStorageService) {



        $scope.init = function () {
            backendService.getAllUsers(function(result){
                $scope.availableUsers = result;
            });
        }

        $scope.foo = [{label: "foo"}, {label: "dnno"}];

        //trigger at the end
        $scope.init();
    });
