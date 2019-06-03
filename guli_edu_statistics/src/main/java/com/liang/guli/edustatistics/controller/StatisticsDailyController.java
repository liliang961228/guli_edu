package com.liang.guli.edustatistics.controller;


import com.liang.guli.educommon.R;
import com.liang.guli.edustatistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author liliang
 * @since 2019-05-21
 */
@Api(tags = "网站统计日数据")
@RestController
@RequestMapping("/edustatistics/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 根据具体的一天得到当天注册人数
     * @param day
     * @return
     */
    @ApiOperation(value = "根据具体的一天得到当天注册人数")
    @GetMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        statisticsDailyService.getDay(day);
        return R.success();
    }

    /**
     * 通过类型和起始时间来查询集合
     * @param type
     * @param beginTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "通过类型和起始时间来查询集合")
    @GetMapping("getTypeCountLists/{type}/{beginTime}/{endTime}")
    public R getTypeCountLists(@PathVariable String type,
                               @PathVariable String beginTime,
                               @PathVariable String endTime){
        Map<String,List<String>> map = statisticsDailyService.getTypeCountLists(type,beginTime,endTime);

        return R.success().data("map",map);

    }

}

