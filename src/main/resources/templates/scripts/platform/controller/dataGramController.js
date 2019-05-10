'use strict';
angular.module('platform').controller('dataGramCtrl', function($scope, $log, $uibModal, $timeout,potRestService, dataRestService, commonService) {


    $scope.dataPageInfo = commonService.getDefaultPageSetting();
    $scope.dataPageInfo.sort = "updatedAt,desc";
    $scope.dataPageInfo.totalPages = 0;
    $scope.dataPageInfo.size = 15;
    $scope.dataPageInfo.maxSize = 15;
    $scope.dataPageInfo.page = 1;

    $scope.isConditionCollapsed = true;

    $scope.dataCondition={
        // type: "updatedAt"
    };

    $scope.search = function(dataCondition){
        $scope.queryAllData(dataCondition);
        commonService.showMessage('查询数据成功');
    }

    $scope.queryAllData = function () {
        var dataCondition = commonService.buildPageCondition($scope.dataCondition,$scope.dataPageInfo)
        dataRestService.query(dataCondition).$promise.then(function (value) {
            $scope.dataPageInfo.totalElements = value.totalElements;
            $scope.dataPageInfo.totalPages = value.totalPages;
            $scope.allData = value.content;
        });
    }

    $scope.refreshData = function(){
        $scope.getQueBtn = true;
        potRestService.getLatestStatus();
        commonService.showInfo('正在获取最新数据');
        $timeout(function () {
            $scope.getQueBtn = false;
            $scope.queryAllData();
            commonService.showMessage('更新记录成功');
        },3000);
    }

    $scope.cleanDataCondition = function() {
        $scope.dataCondition = {
            // type: "updatedAt"
        };
        $scope.queryAllData();
        commonService.showInfo('查询条件清空');
    }

    $scope.changeDataPage = function() {
        $scope.queryAllData();
    }

    $scope.getTempData = function(){
        dataRestService.getAllTempData().$promise.then(function (response) {

            // 基于准备好的dom，初始化echarts实例
            var dataChart = echarts.init($("#dataChart").get(0));

            // 指定图表的配置项和数据
            var option = {
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
                    type: 'category',
                    boundaryGap: false,
                    data:response['time']
                },
                yAxis: {
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
            dataChart.setOption(option);

        });
    }

    $scope.getTempData();
    $scope.queryAllData();
    $scope.refreshData();


    // $scope.$watch('dataCondition.type',function (newVal,oldVal) {
    //     console.log("dataCondition.type change");
    //     if(newVal === "constantTime")
    //         $scope.dataCondition.data = "2分钟";
    //     else if (newVal === "taste")
    //         $scope.dataCondition.data = "偏淡";
    //     else if (newVal === "heatintSwitch")
    //         $scope.dataCondition.data = "开";
    //     else if (newVal === "online")
    //         $scope.dataCondition.data = "在线";
    //     else if (newVal === "heatingOrNot")
    //         $scope.dataCondition.data = "待机";
    //     else
    //         $scope.dataCondition.data = "";
    // })


});