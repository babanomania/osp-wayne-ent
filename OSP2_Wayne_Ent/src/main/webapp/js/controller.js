var app = angular.module("wayne-ent-ui", ['ngResource']);

app.controller("GetCustomerData", function($scope, $http, $resource) {

        var CustomerEntity = $resource('/api/customer/:lastname/:firstname');

        $scope.editorEnabled = false;
        $scope.showEdit = false;
        $scope.showData = false;

        $scope.enableEditor = function() {
            $scope.editorEnabled = true;
            $scope.showEdit = false;
        };

        $scope.getData = function() {
            $scope.customerdata = CustomerEntity.get({
                lastname: $scope.lastName,
                firstname: $scope.firstName
            });
            
            $scope.showEdit = true;
            $scope.showData = true;
        };
        
        $scope.resetData = function() {
        	$scope.lastName = "";
            $scope.firstName = "";
            
            $scope.showEdit = false;
            $scope.showData = false;
        };

        $scope.save = function() {
            CustomerEntity.save($scope.customerdata);
            $scope.editorEnabled = false;
            $scope.showEdit = true;

        }
    }

);

app.controller("GetPolicyData", function($scope, $http, $resource) {

        var PolicyEntity = $resource('/api/policy/:policynum');

        $scope.editorEnabled = false;
        $scope.showEdit = false;
        $scope.showData = false;

        $scope.enableEditor = function() {
            $scope.editorEnabled = true;
            $scope.showEdit = false;
        };

        $scope.getData = function() {

            $scope.policydata = PolicyEntity.get({
                policynum: $scope.policyNum
            });
            
            $scope.showEdit = true;
            $scope.showData = true;
        };

        $scope.save = function() {
        	PolicyEntity.save($scope.policydata);
            $scope.editorEnabled = false;
            $scope.showEdit = true;

        }

    }

);

app.controller("UserLogin", function($scope, $http) {

        $scope.login = function() {
            window.location.href = "customers.html";
        }
    }

);