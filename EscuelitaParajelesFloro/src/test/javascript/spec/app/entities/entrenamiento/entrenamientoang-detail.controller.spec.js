'use strict';

describe('Controller Tests', function() {

    describe('Entrenamiento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEntrenamiento, MockDesenpeno, MockUser, MockHorario;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEntrenamiento = jasmine.createSpy('MockEntrenamiento');
            MockDesenpeno = jasmine.createSpy('MockDesenpeno');
            MockUser = jasmine.createSpy('MockUser');
            MockHorario = jasmine.createSpy('MockHorario');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Entrenamiento': MockEntrenamiento,
                'Desenpeno': MockDesenpeno,
                'User': MockUser,
                'Horario': MockHorario
            };
            createController = function() {
                $injector.get('$controller')("EntrenamientoAngDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'escuelitaParajelesFloroApp:entrenamientoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
