package com.ygl.gmall;

import java.util.ArrayList;

public class TestMain {
    public static void main(String[] args) {
        ArrayList a = new ArrayList();
        int n = 4;
        Integer n1 = new Integer(n);
        a.add(n);//报错，编译不通过。
        a.add(n1);// 编译通过，可以add

        System.out.println("打印："+a);
    }
}
