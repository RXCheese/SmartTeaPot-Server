'use strict';

/**
 * @ngdoc overview
 * @name testApp
 * @description
 * # testApp
 *
 * Main module of the application.
 */
//应用主模块
angular.module('admin', [
	//angular官方模块                           
	'ngAnimate', 'ngCookies', 'ngResource', 'ngSanitize',  
	//第三方模块
	'ui.router', 'ui.bootstrap', 'treeControl', 'ui.uploader', 'ui.tinymce', 'ui.select',
	//自定义模块,所有开发人员自己编写的模块需要在这里注册.
	'common', 'platform'
//常量配置
])
//应用配置	
.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
	//路由定义
	$urlRouterProvider.otherwise("/index/potManage");
	$stateProvider.state('index', {
		url: "/index",
		templateUrl: "views/main.html"
	}).state('index.manage', {
		url: "/manage",
		templateUrl: "views/manage.html"
	});
	$httpProvider.interceptors.push(function($q){
		return {
			responseError: function(response){
				if(response.status == 500) {
					toastr["error"](response.data.errorMsg, "系统异常");
				}
				return $q.reject(response);;
			}
		};
	});
//应用主控制器，所有控制器的父，定义整个应用公用的作用域
}).controller('mainCtrl', function($scope, $uibModal, $state, $interval, resourceRestService,roleRestService,  adminRestService, commonService) {

	$scope.tinymceOptions = { 
		plugins: [
          "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
          "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
          "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
        ],
        toolbar: 'fontsizeselect bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | forecolor backcolor | link ',
        menubar: false,
        toolbar_items_size: 'small'
	};
	
	$scope.isConditionCollapsed = true;
	
	$scope.panelHeight = 400;
	
	$scope.currentAdmin = adminRestService.getCurrentAdmin();
	
	resourceRestService.query().$promise.then(function(result){
		 $scope.menus = result.children;
	});
	
	$scope.clickSubMenu = function(submenu) {
		$scope.currentSubmenu = submenu;
	}
	
	$scope.clickMenu = function(menu) {
		for (var i = 0; i < $scope.menus.length; i++) {
			if(menu.id != $scope.menus[i].id) {
				$scope.menus[i].open = false;
			}
		}
		menu.open = !menu.open;
		if(menu.icon == null || menu.icon == ''){
			$scope.currentMenu = menu;
		}
		if(menu.link != null && menu.link != ''){
			$state.go("index."+menu.link);
		}
		if(menu.icon == 'home'){
			$state.go("index");
		}
	}

    $scope.updatePassword = function(admin){
        $uibModal.open({
            templateUrl : 'views/commons/password.html',
            controller: 'passwordCtrl',
            resolve: {
                admin : function() {return admin;},
                roles : function() {return roleRestService.query();}
            }
        }).result.then(function(formAdmin){
            formAdmin.username = $scope.currentAdmin.username;
            //console.log(formAdmin.username);
            if(formAdmin.confirmNewPassword === formAdmin.newPassword){
            	adminRestService.updatePassword(formAdmin).$promise.then(function(response) {
            			if (response['result'] === "error") {
                            commonService.showError("密码格式不正确");
						}else if (response['result'] === "true") {
                            commonService.showMessage("修改密码成功");
                        }else if (response['result'] === "false") {
            				commonService.showError("密码不正确");
                        } else {
                            commonService.showError("系统错误");
                        }
					}
				)
            }else{
                commonService.showError("确认密码有误");
            }

        })
    }

    $scope.currentInfo = function(admin){
        $uibModal.open({
            templateUrl : 'views/platform/adminForm.html',
            controller: 'infoFormCtrl',
            resolve: {
                admin : function() {return admin;},
                roles : function() {return roleRestService.query();},
                type : function () {return "update";}
            }
        }).result.then(function(formAdmin){
        	if(formAdmin.id){
                adminRestService.getExist({id:formAdmin.id,email:formAdmin.email,phone:formAdmin.phone}).$promise.then(function (value) {
                	if (value === "" || value === null)
					{
                        commonService.showError("系统错误");
                        return;
					}
                    if(value['email'] === "true")
                    {
                        commonService.showError("邮箱已被使用");
                    }
                    if(value['phone'] === "true")
                    {
                        commonService.showError("手机号码已被使用");
                    }
                    if(value['email'] === "false" && value['phone'] === "false")
					{
                        new adminRestService(formAdmin).$save().then(function () {
                            commonService.showMessage("修改个人信息成功");
                        });
					}
                    else{
                        $scope.currentAdmin = adminRestService.getCurrentAdmin();
					}

                })

			}
		});
	}

    $scope.theDate = new Date().toLocaleDateString();
	$scope.theTime = new Date().toLocaleTimeString();
	$interval(function () {
        $scope.theDate = new Date().toLocaleDateString();
		$scope.theTime = new Date().toLocaleTimeString();
    },1000);

//登录控制器
}).controller('signinCtrl', function($scope, $http) {
	$scope.signin = function($state) {
		$http.post("/auth?username="+$scope.user.username+"&password="+$scope.user.password).success(function(response){
            $state.go("index");
		});
	}
//消息控制器,消息窗口的控制器,配合主控制器,为所有页面提供消息提示服务.
}).controller('confirmCtrl', function($scope, $uibModalInstance, title, message) {
	$scope.title = title;
	$scope.message = message;
	$scope.ok = function() {
		$uibModalInstance.close();
	};
	$scope.cancel = function() {
		$uibModalInstance.dismiss();
	};
//修改密码窗口控制器
}).controller('passwordCtrl',function ($scope, $uibModalInstance, admin) {

    $scope.admin = admin;

    $scope.save = function(admin) {
    	$uibModalInstance.close(admin);
    };
}).controller('infoFormCtrl',function ($scope, $uibModalInstance, admin, roles, type) {


    $scope.admin = admin;
    $scope.roles = roles;
    $scope.type = type;

    $scope.save = function(admin) {
        $uibModalInstance.close(admin);
    };

    $scope.checkType = function (type) {
        if (type === "update"){
            $scope.isDisabled = true;
            $scope.roleDisabled = true;
        }else {
            $scope.isDisabled = false;
            $scope.roleDisabled = false;
        }
    }
    $scope.infoDisabled = false;

});

toastr.options = {
	"closeButton" : false,
	"debug" : false,
	"newestOnTop" : true,
	"progressBar" : false,
	"positionClass" : "toast-top-right",
	"preventDuplicates" : false,
	"onclick" : null,
	"showDuration" : "300",
	"hideDuration" : "1000",
	"timeOut" : "3000",
	"extendedTimeOut" : "1000",
	"showEasing" : "swing",
	"hideEasing" : "linear",
	"showMethod" : "fadeIn",
	"hideMethod" : "fadeOut"
}