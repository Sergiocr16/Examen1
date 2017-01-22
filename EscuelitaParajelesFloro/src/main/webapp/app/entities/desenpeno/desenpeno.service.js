(function() {
    'use strict';
    angular
        .module('escuelitaParajelesFloroApp')
        .factory('Desenpeno', Desenpeno);

    Desenpeno.$inject = ['$resource'];

    function Desenpeno ($resource) {
        var resourceUrl =  'api/desenpenos/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
