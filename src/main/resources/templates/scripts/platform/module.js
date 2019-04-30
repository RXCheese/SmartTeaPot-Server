'use strict';
//平台管理模块的配置
angular.module('platform',[]).config(function($stateProvider) {
	//路由配置
	$stateProvider.state('index.adminManage', {
		url: "/adminManage",
		controller: "adminManageCtrl",
		templateUrl: "views/platform/adminManage.html",
        resolve: {
            roleList: function (roleRestService) {
                return roleRestService.query();
            },
            adminList: function (adminRestService, commonService) {
                var pageinfo = commonService.getDefaultPageSetting();
                var condition = commonService.buildPageCondition({}, pageinfo);
                return adminRestService.query(condition).$promise.then(function (data) {
                    return data;
                });
            }
        }
	})
		.state('index.roleManage', {
		url: "/roleManage",
		controller: "roleManageCtrl",
		templateUrl: "views/platform/roleManage.html"
	})
		.state('index.resourceManage', {
		url: "/resourceManage",
		controller: "resourceManageCtrl",
		templateUrl: "views/platform/resourceManage.html"
	})
		.state('index.potManage', {
        url: "/potManage",
        controller: "potManageCtrl",
        templateUrl: "views/platform/potManage.html"
    })
		.state('index.dataGram', {
        url: "/dataGram",
        controller: "dataGramCtrl",
        templateUrl: "views/platform/dataGram.html"
    })
		.state('index.tasteSharing', {
        url: "/tasteSharing",
        controller: "tasteSharingCtrl",
        templateUrl: "views/platform/tasteSharing.html"
    });

//服务配置
})
	.service("adminRestService", function($resource, commonService){
	var adminRestServiceSetting = commonService.getDefaultRestSetting();
	adminRestServiceSetting.getCurrentAdmin = {method: "GET", url: "admin/me"};
	adminRestServiceSetting.updatePassword = {method: "POST", url: "admin/password"};
	adminRestServiceSetting.getExist = {method: "GET", url: "admin/exist?:info"}
	return $resource("admin/:id", {id:"@id"}, adminRestServiceSetting);
})
	.service("resourceRestService", function($resource, commonService){
	var config = commonService.getDefaultRestSetting();
	config.moveUp = {url:"resource/:id/up", method:"POST"};
	config.moveDown = {url:"resource/:id/down", method:"POST"};
	return $resource("resource/:id", {id:"@id"}, config);
})
	.service("roleRestService", function($resource, commonService){
	var roleRestServiceSetting = commonService.getDefaultRestSetting();
	roleRestServiceSetting.query = {isArray: true};
	roleRestServiceSetting.updateRoleMenus = {method: "POST", url: "role/:id/resource?ids=:ids"};
	roleRestServiceSetting.getRoleMenus = {method: "GET", url: "role/:id/resource", isArray: true};
	roleRestServiceSetting.getExistRoleName = {method: "GET", url: "role/:name/exist"}
	return $resource("role/:id", {id:"@id", ids:"@ids"}, roleRestServiceSetting);
})
	.service("potRestService",function ($resource, commonService) {
	var potRestServiceSetting = commonService.getDefaultRestSetting();
	potRestServiceSetting.getLatestStatus = {method: "GET", url: "pot/latest"};
    potRestServiceSetting.executeOpeation = {method: "POST", url: "pot/execute"};
    potRestServiceSetting.getTempData = {method: "GET", url: "pot/tempData"};
    potRestServiceSetting.getOnline = {method: "GET", url: "pot/isOnline"};
	return $resource("pot/:id",{id:"@id"}, potRestServiceSetting);
})
	.service("dataRestService",function ($resource, commonService) {
	var dataRestServiceSetting = commonService.getDefaultRestSetting();
	dataRestServiceSetting.getAllTempData = {method: "GET",url:"pot/allTemp"}
	return $resource("pot/:id",{id:"@id"}, dataRestServiceSetting);
})
	.service("sharingRestService",function ($resource, commonService) {
	var sharingRestServiceSetting = commonService.getDefaultRestSetting();
	sharingRestServiceSetting.getExistConfig = {method:"GET",url:"sharing/existConfig"}
	sharingRestServiceSetting.getAllConfigCount = {method:"GET",url:"sharing/countAllSharing"}
	return $resource("sharing/:id",{id:"@id"}, sharingRestServiceSetting);
});