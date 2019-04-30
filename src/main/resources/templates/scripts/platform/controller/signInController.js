
angular.module("platform").controller("signInCtrl",function($scope,$http) {
    $scope.user = null;

    // 登录验证
    $scope.loginValidate = function () {
        console.log("进入AJAX的点击事件内");

        $http({
            method: 'POST',
            url: '/femis/getUserBySid?sId=' + $scope.user.sId
        })
            .success(function (data) {
                console.log(data);//打印从后台接收的数据
                $scope.user = data;//将数据赋值给angular模型变量
            });
    }

})
