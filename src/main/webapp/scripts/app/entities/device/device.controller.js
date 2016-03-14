'use strict';

angular.module('opowerApp')
    .controller('DeviceController', function ($scope, $state, Device) {

        $scope.devices = [];
        $scope.loadAll = function() {
            Device.query(function(result) {
               $scope.devices = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.device = {
                name: null,
                conso: null,
                id: null
            };
        };
    });
