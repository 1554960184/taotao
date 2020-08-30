package com.taotao.portal.service.impl;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用服务层，查询内容列表
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;
    @Override
    public String getContentList() {
        //调用服务成服务
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        System.out.println(REST_BASE_URL+REST_INDEX_AD_URL);
        System.out.println(result);
        try {
            //把字符串转换为TaoTaoResult
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            System.out.println("taotaoResult-------------"+taotaoResult);
            List<TbContent>list = (List<TbContent>) taotaoResult.getData();
            List<Map>resultMapList = new ArrayList<>();
            //创建一个jsp页面要求的pojo列表
            for (TbContent content:list) {
                Map map = new HashMap();
                map.put("src",content.getPic());
                map.put("height",240);
                map.put("width",670);
                map.put("srcB",content.getPic2());
                map.put("heightB",550);
                map.put("widthB",240);
                map.put("href",content.getUrl());
                map.put("alt",content.getSubTitle());
                resultMapList.add(map);
            }
            return JsonUtils.objectToJson(resultMapList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
