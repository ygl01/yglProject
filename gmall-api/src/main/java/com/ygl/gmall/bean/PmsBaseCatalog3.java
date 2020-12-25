package com.ygl.gmall.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author ygl
 * @description
 * @date 2020/12/24 20:22
 */
public class PmsBaseCatalog3 implements Serializable {
    @Id //数据库主键
    @Column //实体类字段与数据库字段映射
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键生成策略
    private String id;
    @Column
    private String name;
    @Column
    private String catalog2Id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(String catalog2Id) {
        this.catalog2Id = catalog2Id;
    }
}
