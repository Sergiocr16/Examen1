(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('HorarioAngDetailController', HorarioAngDetailController);

    HorarioAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Horario'];

    function HorarioAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Horario) {
        var vm = this;

        vm.horario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:horarioUpdate', function(event, result) {
            vm.horario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
