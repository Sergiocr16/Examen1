(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('HorarioAngDeleteController',HorarioAngDeleteController);

    HorarioAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Horario'];

    function HorarioAngDeleteController($uibModalInstance, entity, Horario) {
        var vm = this;

        vm.horario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Horario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
