/**
 * Created by melvin on 2/4/2017.
 */
(function () {
    'use strict';
    angular.module('escuelitaParajelesFloroApp')
        .filter('shuffle', function() {
            return function (a) {
                var j, x, i;
                for (i = a.length; i; i--) {
                    j = Math.floor(Math.random() * i);
                    x = a[i - 1];
                    a[i - 1] = a[j];
                    a[j] = x;
                }
                return a;
            };
        });

})();
