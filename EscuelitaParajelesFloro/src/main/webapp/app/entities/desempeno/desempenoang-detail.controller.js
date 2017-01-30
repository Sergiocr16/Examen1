(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('DesempenoAngDetailController', DesempenoAngDetailController);

    DesempenoAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Desempeno', 'Jugador', 'Entrenamiento'];

    function DesempenoAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Desempeno, Jugador, Entrenamiento) {
        var vm = this;

        vm.desempeno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:desempenoUpdate', function(event, result) {
            vm.desempeno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
