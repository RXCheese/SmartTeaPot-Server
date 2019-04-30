'use strict';
angular.module('platform').controller('adminManageCtrl', function($scope, $uibModal, adminRestService, roleRestService, commonService) {
	
	$scope.pageInfo = commonService.getDefaultPageSetting();
	
	$scope.isConditionCollapsed = true;
	
	$scope.queryRole = function() {
		$scope.roles = roleRestService.query();
	}

	$scope.queryAdmin = function() {
		var condition = commonService.buildPageCondition($scope.condition, $scope.pageInfo);
		adminRestService.query(condition).$promise.then(function(data){
			$scope.pageInfo.totalElements = data.totalElements;
			$scope.admins = data.content;
		});
	}
	
	$scope.create = function() {
        $scope.saveType = "create";
		$scope.save({});
	}
	
	$scope.update = function(admin) {
		if(admin.username === "admin")
		{
			commonService.showConfirm("该用户无法修改");
		}else{
            $scope.saveType = "update";
            $scope.save(admin);
        }
	}
	
	$scope.save = function(admin){
		$uibModal.open({
			templateUrl : 'views/platform/adminForm.html',
			controller: 'adminFormCtrl',
			resolve: {
		        admin : function() {return admin;},
		        roles : function() {return $scope.roles;},
		        type : function () {return $scope.saveType;}
			}
		}).result.then(function(formAdmin){
			if(formAdmin.id){
				new adminRestService(formAdmin).$save().then(function(){
					commonService.showMessage("修改["+formAdmin.username+"]信息成功");
                    $scope.queryAdmin();
				},function(response){
                    //$scope.queryAdmin();
					// for (var i = 0; i < $scope.admins.length; i++) {
					// 	if(formAdmin.id == $scope.admins[i].id) {
					// 		$scope.admins[i] = adminRestService.get({id:formAdmin.id});
					// 		break;
					// 	}
					// }
				});
			}else{
				adminRestService.getExist({username:formAdmin.username,email:formAdmin.email,phone:formAdmin.phone}).$promise.then(function (value) {
                    if (value === "" || value === null)
                    {
                        commonService.showError("系统错误");
                        return;
                    }
					if(value['username'] === "true")
					{
                        commonService.showError("用户名已被使用");
					}
                    if(value['email'] === "true")
                    {
                        commonService.showError("邮箱已被使用");
                    }
                    if(value['phone'] === "true")
                    {
                        commonService.showError("手机号码已被使用");
                    }
					if(value['username'] === "false" && value['email'] === "false" && value['phone'] === "false")
					{
                        new adminRestService(formAdmin).$create().then(function(admin){
                            $scope.admins.unshift(admin);
                            commonService.showMessage("新建用户成功,默认密码为123456");
                        });
					}
					// else
					// {
                    //     commonService.showError("系统错误，请稍后重试");
					// }
                    $scope.queryAdmin();
				})

			}
		});
	}
	
	$scope.remove = function(admin) {
        if(admin.username === "admin")
        {
            commonService.showConfirm("该用户无法删除");
        }else{
			commonService.showConfirm("您确认要删除用户["+admin.username+"]?").result.then(function() {
				adminRestService.remove({id:admin.id});
			}).then(function(){
				commonService.showMessage("删除用户["+admin.username+"]成功");
				$scope.admins.splice($scope.admins.indexOf(admin), 1);
				if($scope.admins.length == 0){
					$scope.pageInfo.page = $scope.pageInfo.page - 1;
					$scope.query();
				}
			});
        }
	}
	
	$scope.cleanCondition = function() {
		$scope.condition = {};
		$scope.queryAdmin();
	}
	
	$scope.changePage = function() {
		$scope.queryAdmin();
	}

	$scope.queryAdmin();
	$scope.queryRole();
	
	
}).controller('adminFormCtrl',function ($scope, $uibModalInstance, admin, roles, type) {


	$scope.admin = admin;
	$scope.roles = roles;
	$scope.type = type;

	$scope.roleDisabled = false;
	
	$scope.save = function(admin) {
		$uibModalInstance.close(admin);
	};

	$scope.checkType = function (type) {
		if (type === "update"){
			$scope.isDisabled = true;
            $scope.infoDisabled = true;
		}else {
            $scope.isDisabled = false;
            $scope.infoDisabled = false;
		}
    }
    //$scope.checkType();

});