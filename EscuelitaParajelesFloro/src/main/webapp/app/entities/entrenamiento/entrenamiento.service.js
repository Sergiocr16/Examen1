(function() {
    'use strict';
    angular
        .module('escuelitaParajelesFloroApp')
        .factory('Entrenamiento', Entrenamiento);

    Entrenamiento.$inject = ['$resource', 'DateUtils'];

    function Entrenamiento ($resource, DateUtils) {
        var resourceUrl =  'api/entrenamientos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaHora = DateUtils.convertDateTimeFromServer(data.fechaHora);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
