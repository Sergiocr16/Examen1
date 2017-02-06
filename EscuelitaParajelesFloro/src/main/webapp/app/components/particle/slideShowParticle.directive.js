/**
 * Created by melvin on 2/4/2017.
 */
var module = angular
    .module('escuelitaParajelesFloroApp')
    .component('slideShowParticle', {
        template: '<h1>Home</h1><p>Hello, {{ $ctrl.user.name }} !</p>',
        controller: function() {
            this.user = {name: 'world'};
        }
    });
