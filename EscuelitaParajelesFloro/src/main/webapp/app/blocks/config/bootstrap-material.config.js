(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(bootstrapMaterialDesignConfig);

    //compileServiceConfig.$inject = [];

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();
