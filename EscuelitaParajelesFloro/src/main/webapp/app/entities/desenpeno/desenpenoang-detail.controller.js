(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('DesenpenoAngDetailController', DesenpenoAngDetailController);

    DesenpenoAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Desenpeno', 'Jugador', 'Entrenamiento'];

    function DesenpenoAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Desenpeno, Jugador, Entrenamiento) {
        var vm = this;

        vm.desenpeno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:desenpenoUpdate', function(event, result) {
            vm.desenpeno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
