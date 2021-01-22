package com.ygl.gmall.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ygl.gmall.bean.UmsMember;
import com.ygl.gmall.bean.UmsMemberReceiveAddress;
import com.ygl.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/21 20:10
 */
@Controller
public class UserController {

    @Reference
    UserService userService;

    /**
     * @author ygl
     * 查询所有用户信息
     * @date 2020-12-22 10:23
     */
    @GetMapping("/getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        List<UmsMemberReceiveAddress> umsMemberReceiveAddress = userService.getReceiveAddressByMemberId(memberId);
        return umsMemberReceiveAddress;
    }

    /**
     * @author ygl
     * 查询所有用户信息
     * @date 2020-12-22 10:23
     */
    @GetMapping("/getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUSer() {
        List<UmsMember> umsMembers = userService.getAllUser();
        return umsMembers;
    }


    /**
     * @author ygl
     * 测试类
     * @date 2020-12-21 20:17
     */

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "hello";
    }
}
