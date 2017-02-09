(function() {
    'use strict';
    angular
        .module('escuelitaParajelesFloroApp')
        .factory('Desempeno', Desempeno);

    Desempeno.$inject = ['$resource'];

    function Desempeno ($resource) {
        var resourceUrl =  'api/desempenos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },'fingByEntrenamientoIdAndjugadorId' : {
               method: 'GET',
                url: 'api/desenpenosFindByEntrenamientoIdAndJugadorId/:entrenamientoId/:jugadorId',
                params:{
                entrenamientoId:'@entrenamientoId',
                jugadorId: '@jugadorId'
               }
             },
            'update': { method:'PUT' }
        });
    }
})();
