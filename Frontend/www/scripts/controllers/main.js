'use strict';

angular.module('pi4jfrontend')
    .controller('MainCtrl', function ($scope, backendService, localStorageService) {


        $scope.init = function () {
            $scope.plugs = localStorageService.getAllPlugs();
            backendService.fetchPlugsAndUpdateLocalStorage();
        }


        $scope.setPlugState = function(plug, state){
            var promise;
            if(state){
                promise = backendService.plugOn(plug);
            }
            if(!state){
                promise = backendService.plugOff(plug);
            }
            promise.success(function(result){
                    $scope.updateLocalPlugStateById(result.id, result.lastKnownState)

            })
        }

        $scope.updateLocalPlugStateById = function(plugId, state){
            for(var i = 0; i<$scope.plugs.length;i++){
                if ($scope.plugs[i].id == plugId){
                    $scope.plugs[i].lastKnownState = state
                }
            }

        }


        //trigger at the end
        $scope.init();
    });
