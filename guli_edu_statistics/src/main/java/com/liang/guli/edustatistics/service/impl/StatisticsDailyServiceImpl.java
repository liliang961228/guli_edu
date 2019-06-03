package com.liang.guli.edustatistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liang.guli.educommon.R;
import com.liang.guli.edustatistics.entity.StatisticsDaily;
import com.liang.guli.edustatistics.feign.EduStatisticsFeign;
import com.liang.guli.edustatistics.mapper.StatisticsDailyMapper;
import com.liang.guli.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-21
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private EduStatisticsFeign eduStatisticsFeign;

    /**
     * 统计一天中的注册数据
     * @param day
     */
    @Override
    public void getDay(String day) {
        //远程调用，获取到一天中的注册数据
        R r = eduStatisticsFeign.registerCount(day);

        Integer number = (Integer) r.getData().get("number");

        //在每次统计之前先删除上一次的统计结果
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //把获取的数据添加到统计分析表里面
        StatisticsDaily daily = new StatisticsDaily();

        daily.setRegisterNum(number);//统计人数
        daily.setDateCalculated(day);//要统计的时间
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(daily);
    }

    /**
     * 通过类型和起始时间来查询集合
     * @param type
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Map<String, List<String>> getTypeCountLists(String type, String beginTime, String endTime) {


        //根据前端传入的条件来查询
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", beginTime, endTime);
        wrapper.select(type,"date_calculated");

        List<StatisticsDaily> list = baseMapper.selectList(wrapper);

        //定义存储x轴的结果的集合
        List<String> xList = new ArrayList<>();
        //定义存储x轴的结果的集合
        List<String> yList = new ArrayList<>();

        //遍历根据条件查询得到的结果集
        for (int i=0;i<list.size();i++){
            StatisticsDaily statisticsDaily = list.get(i);

            //判断前端要查询的是什么值
            switch (type){
                case "register_num":
                    Integer registerNum = statisticsDaily.getRegisterNum();
                    yList.add(registerNum.toString());
                    break;
                case "login_num":
                    Integer loginNum = statisticsDaily.getLoginNum();
                    yList.add(loginNum.toString());
                    break;
                case "video-view_num":
                    Integer videoViewNum = statisticsDaily.getVideoViewNum();
                    yList.add(videoViewNum.toString());
                    break;
                case "course_num":
                    Integer courseNum = statisticsDaily.getCourseNum();
                    yList.add(courseNum.toString());
                    break;
            }

            String dateCalculated = statisticsDaily.getDateCalculated();
            xList.add(dateCalculated);
        }

        HashMap<String, List<String>> map = new HashMap<>();
        map.put("xData",xList);
        map.put("yData",yList);

        return map;
    }
}
