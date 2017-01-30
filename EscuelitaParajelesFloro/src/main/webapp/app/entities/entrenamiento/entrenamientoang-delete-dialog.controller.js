(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('EntrenamientoAngDeleteController',EntrenamientoAngDeleteController);

    EntrenamientoAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entrenamiento'];

    function EntrenamientoAngDeleteController($uibModalInstance, entity, Entrenamiento) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entrenamiento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
