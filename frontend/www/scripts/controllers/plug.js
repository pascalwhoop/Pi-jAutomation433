'use strict';

angular.module('pi4jfrontend')
    .controller('PlugCtrl', function ($scope, $location,$routeParams, backendService, localStorageService) {



        $scope.init = function () {
            $scope.plugs = localStorageService.getPlugs();
            if($routeParams.id){
                $scope.editPlug = angular.copy($scope.getPlugById($routeParams.id));
            }
        }

        $scope.getPlugById = function(id){
            for(var i = 0;i<$scope.plugs.length;i++){
                if($scope.plugs[i].id == id){
                    return $scope.plugs[i];
                }
            }
        }

        $scope.plugRefresh = function(){
            backendService.fetchPlugsAndUpdateLocalStorage().then(function(result){
                $scope.$broadcast('scroll.refreshComplete');
            })

        }

        $scope.submitPlug = function(plug){
            backendService.submitPlug(plug).then(function(response){
                $location.path("/plugs");
            });
        }

        $scope.deletePlug = function(plug){
            backendService.deletePlug(plug).then(function(response){
                $location.path("/plugs");
            });
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
