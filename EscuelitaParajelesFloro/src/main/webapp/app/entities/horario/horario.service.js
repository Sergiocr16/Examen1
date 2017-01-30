(function() {
    'use strict';
    angular
        .module('escuelitaParajelesFloroApp')
        .factory('Horario', Horario);

    Horario.$inject = ['$resource'];

    function Horario ($resource) {
        var resourceUrl =  'api/horarios/:id';

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
            'update': { method:'PUT' },
            'mostRecent' : {
                method: 'GET',
                url: 'api/horarioMasCercano'
            }
        });
    }
})();
