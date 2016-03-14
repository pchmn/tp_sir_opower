'use strict';

angular.module('opowerApp').controller('DeviceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Device', 'House',
        function($scope, $stateParams, $uibModalInstance, entity, Device, House) {

        $scope.device = entity;
        $scope.houses = House.query();
        $scope.load = function(id) {
            Device.get({id : id}, function(result) {
                $scope.device = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('opowerApp:deviceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.device.id != null) {
                Device.update($scope.device, onSaveSuccess, onSaveError);
            } else {
                Device.save($scope.device, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
