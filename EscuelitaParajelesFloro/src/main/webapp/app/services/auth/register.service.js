(function () {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
