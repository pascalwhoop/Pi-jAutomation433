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
            .when('/settings', {
                templateUrl: 'views/settings.html',
                controller: 'SettingsCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    })
    .config(function ($httpProvider) {
        //setting http provider defaults
        $httpProvider.defaults.headers.common['Content-Type'] = "application/json; charset=utf-8"
    })
;
