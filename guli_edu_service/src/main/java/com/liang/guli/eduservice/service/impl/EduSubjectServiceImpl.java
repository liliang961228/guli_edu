package com.liang.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liang.guli.eduservice.entity.EduSubject;
import com.liang.guli.eduservice.entity.dto.OneSubject;
import com.liang.guli.eduservice.entity.dto.TwoSubject;
import com.liang.guli.eduservice.mapper.EduSubjectMapper;
import com.liang.guli.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 上传文件，在edu_subject表中添加对应的数据
     * @param file
     * @return
     */
    @Override
    public List<String> uploadFile(MultipartFile file){

        //保存一些错误信息
        List<String> arrayList = new ArrayList<>();

        try{

            //获取传到后端的文件
            InputStream fileInputStream = file.getInputStream();

            //通过获取到的文件，得到WorkBook
            Workbook workbook = new HSSFWorkbook(fileInputStream);

            //通过获取到的workbook得到sheet
            Sheet sheetAt = workbook.getSheetAt(0);

            //通过获取到的sheet，得到rows。因为不知道有具体的多少行，所有要循环行数
            int lastRowNum = sheetAt.getLastRowNum();//从0开始

            for (int i = 1 ; i <=lastRowNum ; i++){

                //设置parentID
                String parentId = null;

                Row row = sheetAt.getRow(i);

                //获取第一列的数据
                Cell cell = row.getCell(0);
                if (cell == null){
                    arrayList.add("第"+i+"行，第一列的数据为空");
                    continue;
                }
                String stringCellValue = cell.getStringCellValue();



                //判断行的第一列是否存在
                if(StringUtils.isEmpty(stringCellValue)){
                    arrayList.add("第"+i+"行，第一列的数据为空");
                    continue;
                }


                //查询数库中是否存在该一级标题的数据
                EduSubject eduSubject = this.selectTitleAndParentId(stringCellValue);
                if(eduSubject == null){
                    EduSubject subject = new EduSubject();
                    subject.setTitle(stringCellValue);
                    subject.setParentId("0");

                    //该列未在数据库中保存过，添加到数据库中
                    baseMapper.insert(subject);

                    parentId = subject.getId();
                   // System.out.println("parentId++++++++"+parentId);
                }else {
                    parentId = eduSubject.getId();
                }



                //获取第二列的二级标题数据
                Cell cell2 = row.getCell(1);

                if (cell2 == null){
                    arrayList.add("第"+i+"行，第二列的数据为空");
                    continue;

                }
                String stringCellValue2 = cell2.getStringCellValue();

                ////判断该行的第二列是否存在
                if(StringUtils.isEmpty(stringCellValue2)){
                    arrayList.add("第"+i+"行，第二列的数据为空");
                    continue;

                }

                //判断该二级标题是否在数据库中是否存在
                EduSubject eduSubject1 = this.selectTitleAndParentId(stringCellValue2,parentId);
                if (eduSubject1 == null){
                    EduSubject subject2 = new EduSubject();
                    subject2.setTitle(stringCellValue2);
                    subject2.setParentId(parentId);

                    baseMapper.insert(subject2);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }


    /**
     * 查询该列的内容是否在数据库中存在(一级标题)
     * @param stringCellValue
     * @return
     */
    public EduSubject selectTitleAndParentId(String stringCellValue){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",stringCellValue);
        wrapper.eq("parent_id",0);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 查询该列的内容是否在数据库中存在(二级标题)
     * @param stringCellValue
     * @param parentId
     * @return
     */
    public EduSubject selectTitleAndParentId(String stringCellValue,String parentId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",stringCellValue);
        wrapper.eq("parent_id",parentId);
        return baseMapper.selectOne(wrapper);
    }


    /**
     * 查询到Tree格式的Subject
     * @return
     */
    @Override
    public List<OneSubject> getSubjectList() {

        //从数据库中查找为一级标题所有项数
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneEduSubjects = baseMapper.selectList(wrapperOne);

        //从数据库中查出二级标题
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoEduSubjects = baseMapper.selectList(wrapperTwo);

        //创建一个集合用来存储所有的一级标题
        ArrayList<OneSubject> oneList = new ArrayList<>();

        //循环遍历一级标题的集合
        for (int i = 0; i < oneEduSubjects.size(); i++) {

            //获取每一个一级标题
            EduSubject oneSbuject = oneEduSubjects.get(i);
            OneSubject oneTargetSubject = new OneSubject();
            BeanUtils.copyProperties(oneSbuject,oneTargetSubject);
            oneList.add(oneTargetSubject);

            //创建一个集合用来存储所有的二级标题
            ArrayList<TwoSubject> twoList = new ArrayList<>();

            //循环遍历二级标题的集合
            for (int j = 0; j < twoEduSubjects.size(); j++) {
                //获取每一个二级标题
                EduSubject twoSubject = twoEduSubjects.get(j);
                if(twoSubject.getParentId().equals(oneSbuject.getId())){
                    TwoSubject twoTargetSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject,twoTargetSubject);
                    twoList.add(twoTargetSubject);
                }
            }
            //把所有的二级分类list集合放到每个一级分类中
            oneTargetSubject.setList(twoList);
        }
        return oneList;
    }

    @Override
    public boolean deleteSubjectById(String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            return false;
        }else {
            int i = baseMapper.deleteById(id);
            return i>0;
        }

    }

    /**
     * 添加课表一级分类
     * @param eduSubject
     * @return
     */
    @Override
    public boolean saveOneSubject(EduSubject eduSubject) {
        eduSubject.setParentId("0");
        int insert = baseMapper.insert(eduSubject);
        if (insert>0){
            return true;
        }
        return false;
    }

    /**
     * 添加课表二级标题
     * @param eduSubject
     * @return
     */
    @Override
    public boolean saveTwoSubject(EduSubject eduSubject) {
        int i = baseMapper.insert(eduSubject);
        if (i>0){
            return true;
        }
        return false;
    }


}
