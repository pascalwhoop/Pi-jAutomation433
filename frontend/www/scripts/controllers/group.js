'use strict';

angular.module('pi4jfrontend')
    .controller('GroupCtrl', function ($scope, backendService, localStorageService) {


        $scope.newGroup = {
            plugs : {},
            users : {}
        }

        $scope.init = function () {
            $scope.users = localStorageService.getUsers();
            backendService.getAllUsers();
            $scope.plugs = localStorageService.getPlugs();
        }

        //trigger at the end
        $scope.init();
    });
