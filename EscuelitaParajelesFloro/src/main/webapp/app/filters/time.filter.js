/**
 * Created by melvin on 1/24/2017.
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
        })
        .filter('timeToMin', function () {
            return function(t) {
                let v = t.split(':');
                if (v.length < 2) {
                    throw "invalid format error";
                }
                return parseInt(v[0], 10) * 60
                    + parseInt(v[1], 10)
            };
        });

})();
