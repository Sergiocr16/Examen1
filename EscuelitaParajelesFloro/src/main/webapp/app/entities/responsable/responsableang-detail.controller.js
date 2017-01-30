(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('ResponsableAngDetailController', ResponsableAngDetailController);

    ResponsableAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Responsable'];

    function ResponsableAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Responsable) {
        var vm = this;

        vm.responsable = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:responsableUpdate', function(event, result) {
            vm.responsable = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
