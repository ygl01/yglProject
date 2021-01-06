package com.ygl.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ygl.gmall.bean.PmsBaseAttrInfo;
import com.ygl.gmall.bean.PmsBaseAttrValue;
import com.ygl.gmall.manage.mapper.AttrInfoMapper;
import com.ygl.gmall.manage.mapper.AttrValueMapper;
import com.ygl.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/25 15:44
 */
@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    AttrInfoMapper attrInfoMapper;
    @Autowired
    AttrValueMapper attrValueMapper;


    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo attrInfo = new PmsBaseAttrInfo();
        attrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> attrInfos = attrInfoMapper.select(attrInfo);
        for (PmsBaseAttrInfo attrInfo1 : attrInfos) { //尺寸
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            String attrInfoId = attrInfo1.getId();
            pmsBaseAttrValue.setAttrId(attrInfoId);
            List<PmsBaseAttrValue> select = attrValueMapper.select(pmsBaseAttrValue);
            attrInfo1.setAttrValueList(select);

        }
        return attrInfos;
    }

    @Override
    public void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        if (StringUtils.isBlank(id)) {
            //判断id是否为空，如果为空咋插入
            //插入属性
            attrInfoMapper.insertSelective(pmsBaseAttrInfo); //insert（null值也插入） 和 insertSelective（null不插入）是否将null值进行插入，
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue p : attrValueList) {
                p.setAttrId(pmsBaseAttrInfo.getId());
                //插入属性值
                attrValueMapper.insertSelective(p);
            }

        } else {
            //不为空则更新
            //更新 属性
            Example example = new Example(PmsBaseAttrInfo.class);
            //根据id进行更新，这个id的获取的值是从前端传来的值
            example.createCriteria().andEqualTo("id", pmsBaseAttrInfo.getId());
            attrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo, example);
            //更改属性值
            //采用先删除属性值，然后插入属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            //按照属性id删除所有
            attrValueMapper.delete(pmsBaseAttrValue);
            //原来删除，再进行插入，这也是一种更新操作
            for (PmsBaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                attrValueMapper.insertSelective(baseAttrValue);
            }
        }


    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = attrValueMapper.select(pmsBaseAttrValue);
        return pmsBaseAttrValues;
    }
}
