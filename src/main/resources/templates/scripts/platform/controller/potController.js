'use strict';
angular.module('platform').controller('potManageCtrl', function ($scope, $uibModal,$interval, $log, $timeout,sharingRestService, potRestService, commonService) {

    $scope.getTempData = function(){
        potRestService.getTempData().$promise.then(function (response) {

            // 基于准备好的dom，初始化echarts实例
            var potChart = echarts.init($("#potChart").get(0));

            // 指定图表的配置项和数据
            var option = {
                // title: {
                //     text: '智能茶壶温度折线图'
                // },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['泡茶温度','当前温度']
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        dataView: {readOnly: false},
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis:  {
                    name: '时间',
                    type: 'category',
                    boundaryGap: false,
                    data:response['time']
                },
                yAxis: {
                    name: '温度',
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} °C'
                    }
                },
                series: [
                    {
                        name:'泡茶温度',
                        type:'line',
                        data:response['temperature'],
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name:'当前温度',
                        type:'line',
                        data:response['temp'],
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'},
                                [{
                                    symbol: 'none',
                                    x: '90%',
                                    yAxis: 'max'
                                }, {
                                    symbol: 'circle',
                                    label: {
                                        normal: {
                                            position: 'start',
                                            formatter: '最大值'
                                        }
                                    },
                                    type: 'max',
                                    name: '最高点'
                                }]
                            ]
                        }
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            potChart.setOption(option);

        });
    }



    $scope.getLatestStatus = function () {
        $scope.status = potRestService.getLatestStatus();
        potRestService.getOnline().$promise.then(function (value) {
            if (value['online'] === "true")
            {
                $scope.isOnline = "green";
                $scope.extBtn = true;
                $scope.getBtn = true;
                $timeout(function () {
                    $scope.extBtn = false;
                    $scope.getBtn = false;
                    $scope.getTempData();
                },2000);
            }
            else
            {
                $scope.isOnline = "red";
                $scope.extBtn = true;
                commonService.showError("设备已离线");
            }
        })
    }

    $scope.getLatestStatus();
    $scope.getTempData();

    $scope.tasteClick = function ($event) {
        $scope.taste = $event.target.value;
    }

    $scope.constantTimeClick = function ($event) {
        $scope.constantTime = $event.target.value;
    }

    $scope.heatintSwitchClick = function ($event) {
        if ($event.target.value === "true")
            $scope.heatintSwitch = "开";
        else
            $scope.heatintSwitch = "关";
    }

    $scope.$watch('temperature', function () {
        if (isNaN($scope.temperature))
            $scope.temperature = null;

        if ($scope.temperature < 0)
            $scope.temperature = 0;
        else if ($scope.temperature > 100)
            $scope.temperature = 100;
    })

    $scope.sharing = function(){
      $uibModal.open({
          templateUrl:'views/platform/sharingForm.html',
          controller:'sharingConfigCtrl',
          resolve:{
              taste: function () {
                  return $scope.taste;
              },
              temperature: function () {
                  return $scope.temperature * 1;
              },
              constantTime: function () {
                  return $scope.constantTime;
              }
          }
      }).result.then(function (formValue) {
          formValue.author = $scope.currentAdmin.username;
          sharingRestService.getExistConfig({configName:formValue.configName}).$promise.then(function (value) {
              if (value['result'] === "false"){
                  new sharingRestService(formValue).$create().then(function (value) {
                      commonService.showMessage("分享味道成功");
                  });
              }else if (value['result'] === "true"){
                  commonService.showError("分享失败,该配置名已被使用");
              }else {
                  commonService.showError("系统错误");
              }
          })
            //commonService.showInfo("测试按键");
      })
    }

    $scope.executeOp = function () {

        $scope.extBtn = true;
        $scope.getBtn = true;

        var temp = {};

        if (angular.isDefined($scope.temperature)) {
            temp.temperature = $scope.temperature;
        }

        if (angular.isDefined($scope.taste)) {
            temp.taste = $scope.taste;
        }

        if (angular.isDefined($scope.constantTime)) {
            temp.constantTime = $scope.constantTime;
        }

        if (angular.isDefined($scope.heatintSwitch)) {
            temp.heatintSwitch = $scope.heatintSwitch;
        }

        if (($scope.temperature === null)
            && angular.isUndefined($scope.heatintSwitch)
            && angular.isUndefined($scope.constantTime)
            && angular.isUndefined($scope.taste))
        {
            commonService.showError("操作错误,请选择正确控制项");
            $scope.extBtn = false;
            return;
        }

        potRestService.executeOpeation(temp).$promise.then(function (response) {
            if (response['result'] === "true"){
                commonService.showMessage("操作成功");
                $scope.getBtn = true;
                $timeout(function () {
                    $scope.getLatestStatus();
                },2000);
                return;
            }else if (response['result']==="false"){
                commonService.showError("操作失败");
            }else if (response['result']==="error"){
                commonService.showError("操作错误,请选择正确控制项");
            } else{
                commonService.showError("系统错误,请刷新页面重试");
            }
        });

        $timeout(function () {
            $scope.extBtn = false;
        },2000);

    }



    $scope.auto = true;
    $scope.autoStatus = function (auto) {
        if (auto)
        {
            $scope.task = $interval(function () {
                $scope.getLatestStatus();
                //console.log("autoStatusTask running");
            },10000);
            $scope.auto = false;
            commonService.showInfo("已启用自动更新(10s)");
        }else {
            $interval.cancel($scope.task);
            //console.log("autoStatusTask cancel");
            $scope.auto = true;
            commonService.showError("已关闭自动更新");
        }
    }

}).controller('sharingConfigCtrl',function ($scope, $uibModalInstance,adminRestService, taste,constantTime,temperature) {
    $scope.sharing ={
        taste : taste,
        temperature : temperature,
        constantTime : constantTime
    };
    $scope.currentAdmin = adminRestService.getCurrentAdmin();
    $scope.save = function (sharing) {
        $uibModalInstance.close(sharing);
    }
});