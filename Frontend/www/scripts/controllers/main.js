'use strict';

angular.module('pi4jfrontend')
    .controller('MainCtrl', function ($scope, backendService) {


        $scope.init = function () {
            $scope.plugs = backendService.getAllPlugs();

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
                if(result.data.lastKnownState != plug.lastKnownState){
                    $scope.updateLocalPlugStateById(result.data.id, result.data.lastKnownState)
                }
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
