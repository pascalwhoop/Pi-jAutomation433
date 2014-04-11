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
            /* +++ groups+++ */
            .when('/groups',{
                templateUrl: 'views/groups.html',
                controller: 'GroupCtrl'
            })
            .when('/groups/add', {
                templateUrl: 'views/addGroup.html',
                controller: 'GroupCtrl'
            })
            /* +++ plugs +++ */
            .when('/plugs',{
                templateUrl: 'views/plugs.html',
                controller: 'PlugCtrl'
            })
            .when('/plugs/add',{
                templateUrl: 'views/addPlug.html',
                controller: 'PlugCtrl'
            })
            .when('/plugs/:id',{
                templateUrl: 'views/editPlug.html',
                controller: 'PlugCtrl'
            })
            /* +++ users +++ */
            .when('/users',{
                templateUrl: 'views/users.html',
                controller: 'UserCtrl'
            })
            .when('/users/add',{
                templateUrl: 'views/addUser.html',
                controller: 'UserCtrl'
            })
            .when('/users/:username',{
                templateUrl: 'views/editUser.html',
                controller: 'UserCtrl'
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
