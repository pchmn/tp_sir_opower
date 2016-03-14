'use strict';

angular.module('opowerApp')
    .controller('DeviceDetailController', function ($scope, $rootScope, $stateParams, entity, Device, House) {
        $scope.device = entity;
        $scope.load = function (id) {
            Device.get({id: id}, function(result) {
                $scope.device = result;
            });
        };
        var unsubscribe = $rootScope.$on('opowerApp:deviceUpdate', function(event, result) {
            $scope.device = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
