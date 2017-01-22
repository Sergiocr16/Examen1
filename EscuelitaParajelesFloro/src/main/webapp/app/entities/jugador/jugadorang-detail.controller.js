(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('JugadorAngDetailController', JugadorAngDetailController);

    JugadorAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Jugador', 'Responsable', 'Categoria'];

    function JugadorAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Jugador, Responsable, Categoria) {
        var vm = this;

        vm.jugador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:jugadorUpdate', function(event, result) {
            vm.jugador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
