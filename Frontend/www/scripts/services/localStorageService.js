'use strict';

angular.module('pi4jfrontend')
  .factory('localStorageService', function () {
    // Service logic
    // ...


    var meaningOfLife = 42;

    // Public API here
    return {
      someMethod: function () {
        return meaningOfLife;
      }
    };
  });
