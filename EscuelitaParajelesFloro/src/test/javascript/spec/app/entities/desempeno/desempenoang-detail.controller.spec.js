'use strict';

describe('Controller Tests', function() {

    describe('Desempeno Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDesempeno, MockJugador, MockEntrenamiento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDesempeno = jasmine.createSpy('MockDesempeno');
            MockJugador = jasmine.createSpy('MockJugador');
            MockEntrenamiento = jasmine.createSpy('MockEntrenamiento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Desempeno': MockDesempeno,
                'Jugador': MockJugador,
                'Entrenamiento': MockEntrenamiento
            };
            createController = function() {
                $injector.get('$controller')("DesempenoAngDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escuelitaParajelesFloroApp:desempenoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
