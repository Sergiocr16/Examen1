(function() {
    'use strict';
    angular
        .module('escuelitaParajelesFloroApp')
        .factory('Jugador', Jugador);

    Jugador.$inject = ['$resource'];

    function Jugador ($resource) {
        var resourceUrl =  'api/jugadors/:id';

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
            },
           'getByCategoria' : {
                        method: 'GET',
                        url: 'api/jugadors/categoria/:id'
                        , isArray: true
                    },
            'update': { method:'PUT' }
        });
    }
})();
