package com.ygl.gmall.manage.mapper;

import com.ygl.gmall.bean.PmsBaseAttrInfo;
import com.ygl.gmall.bean.PmsBaseAttrValue;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/25 16:19
 */
@Mapper
public interface AttrValueMapper extends tk.mybatis.mapper.common.Mapper<PmsBaseAttrValue> {

    List<PmsBaseAttrInfo> selectAttrValueListByValueId(@Param("valueIdStr") String valueIdStr);

}
