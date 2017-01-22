(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('DesenpenoAngDeleteController',DesenpenoAngDeleteController);

    DesenpenoAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Desenpeno'];

    function DesenpenoAngDeleteController($uibModalInstance, entity, Desenpeno) {
        var vm = this;

        vm.desenpeno = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Desenpeno.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
