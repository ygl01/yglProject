package com.ygl.gmall.service;


import com.ygl.gmall.bean.PmsBaseCatalog1;
import com.ygl.gmall.bean.PmsBaseCatalog2;
import com.ygl.gmall.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/24 20:42
 */

public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();
    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);
    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
