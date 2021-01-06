package com.ygl.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.PmsBaseAttrInfo;
import com.ygl.gmall.bean.PmsBaseAttrValue;
import com.ygl.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/25 15:35
 */
@Controller
@CrossOrigin //请求跨域注解
public class AttrController {

    @Reference
    AttrService attrService;


    /**
     * @author ygl
     * 查询属性列表
     * @date 2020-12-25 16:13
     */
    @GetMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        List<PmsBaseAttrInfo> attrInfos = attrService.attrInfoList(catalog3Id);


        return attrInfos;
    }

    /**
     * @author ygl
     * 增加属性名称和属性值名称
     * @date 2020-12-25 16:15
     */
    @PostMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        attrService.saveAttrInfo(pmsBaseAttrInfo);
        return "succes";
    }

    /**
     * @author ygl
     * 根据id查找所有属性值
     * @date 2020-12-25 20:12
     */
    @PostMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {


        List<PmsBaseAttrValue> pmsBaseAttrValues = attrService.getAttrValueList(attrId);
        return pmsBaseAttrValues;

    }

}
