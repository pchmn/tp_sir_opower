'use strict';

describe('Controller Tests', function() {

    describe('House Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHouse, MockPerson, MockDevice, MockHeater;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHouse = jasmine.createSpy('MockHouse');
            MockPerson = jasmine.createSpy('MockPerson');
            MockDevice = jasmine.createSpy('MockDevice');
            MockHeater = jasmine.createSpy('MockHeater');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'House': MockHouse,
                'Person': MockPerson,
                'Device': MockDevice,
                'Heater': MockHeater
            };
            createController = function() {
                $injector.get('$controller')("HouseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opowerApp:houseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
