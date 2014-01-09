'use strict';

angular.module('pi4jfrontend', ['ionic', 'ngRoute', 'ngAnimate', 'ngResource'])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
        .when('/edit', {
            templateUrl: 'views/edit.html',
            controller: 'EditCtrl'
        })
      .otherwise({
        redirectTo: '/'
      });
  });
