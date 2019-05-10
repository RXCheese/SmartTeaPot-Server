'use strict';
angular.module('platform').controller('tasteSharingCtrl', function ($scope, $uibModal, $log, sharingRestService, adminRestService, potRestService, commonService) {

    $scope.sharingPageInfo = commonService.getDefaultPageSetting();
    $scope.sharingPageInfo.sort = "Id,desc";
    $scope.sharingPageInfo.totalPages = 0;
    $scope.sharingPageInfo.size = 15;
    $scope.sharingPageInfo.maxSize = 15;
    $scope.sharingPageInfo.page = 1;

    $scope.isConditionCollapsed = true;

    $scope.currentAdmin = adminRestService.getCurrentAdmin();

    $scope.sharingCondition = {};

    $scope.search = function(sharingCondition){
        $scope.querySharing(sharingCondition);
        commonService.showMessage('查询味道成功');
    }

    $scope.refreshData = function(){
        $scope.querySharing();
        commonService.showMessage('更新味道成功');
    }

    $scope.querySharing = function () {
        var sharingCondition = commonService.buildPageCondition($scope.sharingCondition, $scope.sharingPageInfo);
        sharingRestService.query(sharingCondition).$promise.then(function (value) {
            $scope.sharingPageInfo.totalElements = value.totalElements;
            $scope.sharingPageInfo.totalPages = value.totalPages;
            $scope.sharings = value.content;
        });
    }

    $scope.create = function () {
        $scope.save({});
    }

    $scope.update = function (sharing) {
        if ((sharing.author === $scope.currentAdmin.username) || $scope.currentAdmin.username === 'admin') {
            $scope.save(sharing);
        } else {
            commonService.showError("操作错误");
        }
    }

    $scope.save = function (sharing) {
        $uibModal.open({
            templateUrl: 'views/platform/sharingForm.html',
            controller: 'sharingFormCtrl',
            resolve: {
                sharing: function () {
                    return sharing;
                },
                temperature: function () {
                    return sharing.temperature * 1;
                }
            }
        }).result.then(function (formValue) {
            formValue.author = $scope.currentAdmin.username;
            if (formValue.id) {
                sharingRestService.getExistConfig({configName: formValue.configName}).$promise.then(function (value) {
                    if (formValue.configName === sharing.configName || value['result'] === "false") {
                        new sharingRestService(formValue).$save().then(function (value) {
                            commonService.showMessage("修改味道成功");
                            $scope.querySharing();
                            $scope.getCount();
                        }, function (reason) {
                            //$scope.querySharing();
                            // for (var i = 0; i < $scope.sharings.length; i++) {
                            //     if (formValue.id === $scope.sharings[i].id) {
                            //         $scope.sharings[i] = sharingRestService.get({id: formValue.id});
                            //         break;
                            //     }
                            // }
                        });
                    } else {
                        if (value['result'] === "true") {
                            commonService.showError("修改失败,该配置名已被使用");

                        } else {
                            commonService.showError("系统错误");
                        }
                    }
                })

            } else {
                sharingRestService.getExistConfig({configName: formValue.configName}).$promise.then(function (value) {
                    if (value['result'] === "false") {
                        new sharingRestService(formValue).$create().then(function (value) {
                            $scope.sharings.unshift(value);
                            commonService.showMessage("分享味道成功");
                            $scope.getCount();
                        });
                    } else if (value['result'] === "true") {
                        commonService.showError("分享失败,该配置名已被使用");
                    } else {
                        commonService.showError("系统错误");
                    }
                })

            }

        });
    };

    $scope.remove = function (sharing) {
        if ((sharing.author === $scope.currentAdmin.username) || $scope.currentAdmin.username === 'admin') {
            commonService.showConfirm("您确认要删除[" + sharing.configName + "]味道?").result.then(function () {
                sharingRestService.remove({id: sharing.id});
            }).then(function (value) {
                commonService.showMessage("删除[" + sharing.configName + "]味道成功");
                $scope.sharings.splice($scope.sharings.indexOf(sharing), 1);
                if ($scope.sharings.length === 0) {
                    $scope.sharingPageInfo.page = $scope.sharingPageInfo - 1;
                    $scope.querySharing();
                }
                $scope.getCount();
            })
        } else {
            commonService.showError("操作错误");
        }
    };

    $scope.applySharing = function (sharing) {
        var temp = {
            temperature: sharing.temperature,
            taste: sharing.taste,
            constantTime: sharing.constantTime,
            heatintSwitch: "开"
        };

        commonService.showSharingConfirm("享受至臻味道",sharing).result.then(function (value) {

        }).then(function (value) {
            potRestService.executeOpeation(temp).$promise.then(function (response) {
                if (response['result'] === "true") {
                    commonService.showMessage("享受味道成功,水壶已按照配置启动");
                } else if (response['result'] === "false") {
                    commonService.showError("享受味道失败,请刷新页面重试");
                } else if (response['result'] === "error") {
                    commonService.showError("暂时无法享受到该味道");
                } else {
                    commonService.showError("系统错误,请刷新页面重试");
                }
            });
        })

    }

    $scope.cleanSharingCondition = function () {
        $scope.sharingCondition = {};
        $scope.querySharing();
    };

    $scope.changePage = function () {
        $scope.querySharing();
    }

    $scope.querySharing();


    $scope.getCount = function () {
        sharingRestService.getAllConfigCount().$promise.then(function (value) {
            // $log.info(value);
            var leafChart = echarts.init($("#leafChart").get(0));
            var configChart = echarts.init($("#configChart").get(0));

            var leafOption = {
                title: {
                    text: '茶叶占比',
                    subtext: '',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['碧螺春', '信阳毛尖', '西湖龙井', '君山银针', '黄山毛峰', '武夷岩茶', '祁门红茶', '都匀毛尖', '铁观音', '六安瓜片', '其他']
                },
                series: [
                    {
                        name: '茶叶',
                        type: 'pie',
                        radius: '60%',
                        center: ['50%', '50%'],
                        data: [
                            {value: value['leaf1'], name: '碧螺春'},
                            {value: value['leaf2'], name: '信阳毛尖'},
                            {value: value['leaf3'], name: '西湖龙井'},
                            {value: value['leaf4'], name: '君山银针'},
                            {value: value['leaf5'], name: '黄山毛峰'},
                            {value: value['leaf6'], name: '武夷岩茶'},
                            {value: value['leaf7'], name: '祁门红茶'},
                            {value: value['leaf8'], name: '都匀毛尖'},
                            {value: value['leaf9'], name: '铁观音'},
                            {value: value['leaf10'], name: '六安瓜片'},
                            {value: value['leaf11'], name: '其他'}
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            },
                            normal:{
                                label:{
                                    show: true,
                                    formatter: '{b} : {c} ({d}%)'
                                },
                                labelLine :{show:true}
                            }
                        }
                    }
                ]
            };


            var configOption = {
                title: {
                    text: '[泡茶浓度]                       [泡茶温度(℃)]                    [泡茶时间(分钟)]',
                    subtext: '',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                xAxis: {
                    type: 'category',
                    data: ['偏淡', '适中', '偏浓', '', '<69', '70-79', '80-89', '90>', '', '2min', '3min', '4min', '5min']
                },
                yAxis: {
                    name: '数量(个)',
                    type: 'value'
                },
                series: [{
                    name: '数量(个)',
                    data: [value['taste1'], value['taste2'], value['taste3'], "", value['temp'], value['temp70'], value['temp80'], value['temp90'], "", value['time1'], value['time2'], value['time3'], value['time4']],
                    type: 'bar',
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        normal: {
                            color: function (params) {
                                var colorList = [
                                    "#c23531",
                                    "#2f4554",
                                    "#61a0a8",
                                    "",
                                    "#d48265",
                                    "#91c7ae",
                                    "#749f83",
                                    "#ca8622",
                                    "",
                                    "#bda29a",
                                    "#6e7074",
                                    "#546570",
                                    "#c4ccd3"
                                ];
                                return colorList[params.dataIndex];
                            }
                        }

                    },
                    label: {
                        show: "true",
                        position: "top",
                        color: "auto",
                        fontWeight: "bolder",
                        //backgroundColor: "auto",
                        fontSize: "20"
                    }
                }]

            }
            leafChart.setOption(leafOption);
            configChart.setOption(configOption);
        })
    }

    $scope.getCount();

}).controller('sharingFormCtrl', function ($scope, $uibModalInstance, adminRestService, sharing, temperature) {
    $scope.sharing = {
        id: sharing.id,
        configName: sharing.configName,
        leaf: sharing.leaf,
        taste: sharing.taste,
        temperature: temperature,
        constantTime: sharing.constantTime
    };
    $scope.currentAdmin = adminRestService.getCurrentAdmin();
    $scope.save = function (sharing) {
        $uibModalInstance.close(sharing);
    }
})