'use strict';

describe('Controller Tests', function() {

    describe('Entrenamiento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEntrenamiento, MockDesempeno, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEntrenamiento = jasmine.createSpy('MockEntrenamiento');
            MockDesempeno = jasmine.createSpy('MockDesempeno');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Entrenamiento': MockEntrenamiento,
                'Desempeno': MockDesempeno,
                'User': MockUser
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
