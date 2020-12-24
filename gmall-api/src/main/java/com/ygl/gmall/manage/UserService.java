package com.ygl.gmall.manage;

import com.ygl.gmall.bean.UmsMember;
import com.ygl.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/21 20:10
 */
public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
