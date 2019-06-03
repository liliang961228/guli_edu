package com.liang.guli.edustatistics.service;

import com.liang.guli.edustatistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-21
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getDay(String day);

    Map<String,List<String>> getTypeCountLists(String type, String beginTime, String endTime);

}
