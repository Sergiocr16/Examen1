(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('DesempenoAngDeleteController',DesempenoAngDeleteController);

    DesempenoAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Desempeno'];

    function DesempenoAngDeleteController($uibModalInstance, entity, Desempeno) {
        var vm = this;

        vm.desempeno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Desempeno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
