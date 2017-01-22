'use strict';

describe('Controller Tests', function() {

    describe('Desenpeno Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDesenpeno, MockJugador, MockEntrenamiento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDesenpeno = jasmine.createSpy('MockDesenpeno');
            MockJugador = jasmine.createSpy('MockJugador');
            MockEntrenamiento = jasmine.createSpy('MockEntrenamiento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Desenpeno': MockDesenpeno,
                'Jugador': MockJugador,
                'Entrenamiento': MockEntrenamiento
            };
            createController = function() {
                $injector.get('$controller')("DesenpenoAngDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escuelitaParajelesFloroApp:desenpenoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
