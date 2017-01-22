(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('JugadorAngDeleteController',JugadorAngDeleteController);

    JugadorAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Jugador'];

    function JugadorAngDeleteController($uibModalInstance, entity, Jugador) {
        var vm = this;

        vm.jugador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Jugador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
