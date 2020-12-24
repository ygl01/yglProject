package com.ygl.gmall.user.service.impl;

import com.ygl.gmall.bean.UmsMember;
import com.ygl.gmall.bean.UmsMemberReceiveAddress;
import com.ygl.gmall.manage.UserService;
import com.ygl.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.ygl.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/21 20:11
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllUser(){

        List<com.ygl.gmall.bean.UmsMember> umsMembers = userMapper.selectAll();// userMapper.selectAllUser();
        return umsMembers;
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        //根据memberId进行查询
        Example e = new Example(UmsMemberReceiveAddress.class);
        e.createCriteria().andEqualTo("memberId",memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectByExample(e);

        //上面这种方法类似于下面注释的这种
        //UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        //umsMemberReceiveAddress.setMemberId(memberId);
        //umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);

        return umsMemberReceiveAddresses;
    }
}
