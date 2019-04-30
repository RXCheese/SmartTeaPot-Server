'use strict';
angular.module('platform').controller('roleManageCtrl', function($scope, $uibModal, roleRestService, resourceRestService, commonService) {
	
	$scope.treedata = resourceRestService.query();
	$scope.treedata.$promise.then(function(data){
		$scope.expandedNodes = commonService.getAllNode([], data.children);
	})
	
	$scope.treeOptions = {
		dirSelectable : false,
	};
	
	$scope.query = function() {
		$scope.roles = roleRestService.query();
	}
	
	$scope.selectRole = function(role) {
		$scope.currentRole = role;
		for (var i = 0; i < $scope.roles.length; i++) {
			if(role.id == $scope.roles[i].id) {
				$scope.roles[i].selected = true;
			}else{
				$scope.roles[i].selected = false;
			}
		}
		roleRestService.getRoleMenus({id:role.id}).$promise.then(function(resources){
			commonService.forEachNode($scope.treedata.children, function(node){
				var checked = false;
				for (var i = 0; i < resources.length; i++) {
					if(node.id == resources[i]){
						checked = true;
						break;
					}
				}
				node.checked = checked;
			});
		});
	}
	
	$scope.checkNode = function(id, checked){
		var ids = '';
		commonService.forEachNode($scope.treedata.children, function(node){
			if(id == node.id){
				node.checked = checked;
			}
			if(node.checked){
				ids = ids + node.id +",";
			}
		});
		roleRestService.updateRoleMenus({id:$scope.currentRole.id, ids:ids});
	}
	
	$scope.create = function() {
		$scope.save({});
	}
	
	$scope.update = function(role) {
        if(role.name==="超级管理员") {
            commonService.showConfirm("该角色无法修改");
        }else {
            $scope.save(role);
		}
	}
	
	$scope.save = function(role){
		$uibModal.open({
			templateUrl : 'views/platform/roleForm.html',
			controller: 'roleFormCtrl',
			resolve: {
		        role : function() {return role;},
			}
		}).result.then(function(formRole){
			roleRestService.getExistRoleName({name:formRole.name}).$promise.then(function (value) {
                if(value['result'] === "true")
                {
                    commonService.showError("角色已存在");
                    $scope.query();
                    return;
                }else if (value['result'] === "false"){
                    if(formRole.id){
                        new roleRestService(formRole).$save();
                    }else{
                        new roleRestService(formRole).$create().then(function(role){
                            $scope.roles.push(role);
                        });
                    }
				}else {
                    commonService.showError("系统错误，请稍后重试");
				}
			});

		});
	}
	
	$scope.remove = function(role) {
		if(role.name==="超级管理员"){
			commonService.showConfirm("该角色无法删除");
		}else {
            commonService.showConfirm("您确认要删除["+role.name+"]角色?").result.then(function() {
                roleRestService.remove({id:role.id}).$promise.then(function(response){
                	if(response['result'] === "true")
					{
                        $scope.roles.splice($scope.roles.indexOf(role), 1);
                        commonService.showMessage("删除角色["+role.name+"]成功");
					}else{
                        commonService.showError("操作失败，无法删除具有下挂用户的角色");
					}

                });
            });
		}
	}
	
	$scope.query();
	
}).controller('roleFormCtrl',function ($scope, $uibModalInstance, role) {
	$scope.role = role;
	$scope.save = function(role) {
		$uibModalInstance.close(role);
	};
});