package com.ygl.gmall.service;

import com.ygl.gmall.bean.PmsBaseAttrInfo;
import com.ygl.gmall.bean.PmsBaseAttrValue;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/25 15:42
 */
public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);
    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);
    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
