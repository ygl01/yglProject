package com.ygl.gmall.user.mapper;

import com.ygl.gmall.bean.UmsMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/21 20:11
 */
@Mapper
@Repository
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<UmsMember> {
    List<UmsMember> selectAllUser();
}
