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
        	
        	$scope.showEdit = false;
        	$scope.showData = false;
        	
            $scope.customerdata = CustomerEntity.get({
                lastname: $scope.lastName,
                firstname: $scope.firstName
                
            }, function() {
            	$scope.showEdit = true;
                $scope.showData = true;
            	$scope.showError = false;
            	
            },function(response) {
            	$scope.showEdit = false;
            	$scope.showData = false;
            	$scope.showError = true;
            	
            	if(response.status === 404) {
            		$scope.errorMessage = "Customer Not Found"
                }else{
                	if( response.message == null ){
                		$scope.errorMessage = "Server Error";
                		
                	}else{
                		$scope.errorMessage = response.message;
                	}
                }
            	
            });
            
            
        };

        $scope.save = function() {
            CustomerEntity.save($scope.customerdata,
				  function(resp, headers){
	                //success callback
	                console.log(resp);
	              },
	              function(err){
	            	  
	            	$scope.showEdit = false;
	              	$scope.showData = false;
	              	$scope.showError = true;
	              	
	              	$scope.errorMessage = "error occured while saving ";
	                console.log(err);
	              }
            );

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

        	$scope.showEdit = false;
        	$scope.showData = false;
        	
            $scope.policydata = PolicyEntity.get({
                policynum: $scope.policyNum
                
            }, function() {
            	$scope.showEdit = true;
                $scope.showData = true;
            	$scope.showError = false;
            	
            },function(response) {
            	$scope.showEdit = false;
            	$scope.showData = false;
            	$scope.showError = true;
            	
            	if(response.status === 404) {
            		$scope.errorMessage = "Policy Not Found"
                }else{
                	
                	if( response.message == null ){
                		$scope.errorMessage = "Server Error";
                		
                	}else{
                		$scope.errorMessage = response.message;
                	}
                }
            	
            });
            
        };

        $scope.save = function() {
        	PolicyEntity.save($scope.policydata,
  				  function(resp, headers){
	                //success callback
	                console.log(resp);
	              },
	              function(err){
	            	$scope.showEdit = false;
					$scope.showData = false;
					$scope.showError = true;
					
					$scope.errorMessage = "error occured while saving ";
	                console.log(err);
	              }
        	);

        }

    }

);

app.controller("UserLogin", function($scope, $http) {

        $scope.login = function() {
            window.location.href = "customers.html";
        }
    }

);