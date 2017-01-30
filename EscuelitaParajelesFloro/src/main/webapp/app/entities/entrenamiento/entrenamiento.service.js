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
                        data.fecha = DateUtils.convertLocalDateFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
