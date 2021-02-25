package com.ygl.gmall.service;

import com.ygl.gmall.bean.PmsSearchParam;
import com.ygl.gmall.bean.PmsSearchSkuInfo;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) throws IOException;
}
