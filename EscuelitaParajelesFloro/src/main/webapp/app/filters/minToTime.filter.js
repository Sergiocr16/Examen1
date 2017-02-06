/**
 * Created by melvin on 2/5/2017.
 */
/**
 * Created by melvin on 1/29/2017.
 */

(function () {
    'use strict';
    angular.module('escuelitaParajelesFloroApp')
        .filter('minToTime', function() {
            return function (m) {
                function addZero(m) {
                    return (m < 10 ? "0" : "").concat(m);
                }
                return addZero(Math.floor(m / 60))
                    + ":"
                    + addZero(m % 60);
            };
        });
})();
