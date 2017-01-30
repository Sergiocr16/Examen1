(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('EntrenamientoAngDetailController', EntrenamientoAngDetailController);

    EntrenamientoAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrenamiento', 'Desempeno', 'User'];

    function EntrenamientoAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrenamiento, Desempeno, User) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:entrenamientoUpdate', function(event, result) {
            vm.entrenamiento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
