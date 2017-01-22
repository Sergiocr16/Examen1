(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('CategoriaAngDetailController', CategoriaAngDetailController);

    CategoriaAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Categoria', 'Horario', 'Jugador'];

    function CategoriaAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Categoria, Horario, Jugador) {
        var vm = this;

        vm.categoria = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:categoriaUpdate', function(event, result) {
            vm.categoria = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
