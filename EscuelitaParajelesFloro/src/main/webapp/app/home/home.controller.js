(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'minToTimeFilter', 'Horario'];

    function HomeController ($scope, Principal, LoginService, $state, minToTimeFilter, Horario) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.saludo = null;

        vm.login = LoginService.open;
        vm.register = register;
        vm.showHorarioMasCercano = showHorarioMasCercano;

        $scope.$on('authenticationSuccess', getHorarioMasCercano);
        getHorarioMasCercano();

        function getAccount() {
            return Principal.identity()
               .then(account => {
                   vm.account = account;
                   vm.isAuthenticated = Principal.isAuthenticated;
                   return account;
               });
        }
        function register () {
            $state.go('register');
        }

        function showHorarioMasCercano() {
            return vm.saludo !== null;
        }

        function getHorarioMasCercano() {
            Promise.all([Horario.mostRecent().$promise, getAccount()])
                   .then(([r, u]) => {
                       vm.saludo = {
                           saludoNombre: {
                               firstName: u.firstName
                           },
                           rangoTiempo: {
                               horaInicio: minToTimeFilter(r.horario.horaInicio),
                               horaFin: minToTimeFilter(r.horario.horaFin),
                           },
                           cantidadJugadores: {
                               cantidad: r.cantidadJugadores
                           },
                           dia: r.horario.dia,
                       };});
        }
    }
})();
