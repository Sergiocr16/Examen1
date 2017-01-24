/**
 * Created by melvin on 1/24/2017.
 */

(function () {
    'use strict';
    angular.module('escuelitaParajelesFloroApp')
        .filter('minToTime', () => {
            return (m) => {
                let horas = Math.floor(m / 60);
                let minutos = m % 60;
                let addZero = m => (m < 10 ? "0" : "").concat(m);
                return addZero(horas) + ":" + addZero(minutos);
            };
        })
        .filter('timeToMin', () => {
            return (t) => {
                let v = t.split(':');
                if (v.length < 2) {
                    throw "invalid format error";
                }
                return parseInt(v[0], 10) * 60
                    + parseInt(v[1], 10)
            };
        });

})();
