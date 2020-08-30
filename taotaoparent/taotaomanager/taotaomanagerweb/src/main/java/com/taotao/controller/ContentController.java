package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult instertContent(TbContent tbContent){
        TaotaoResult result = contentService.insertContent(tbContent);
        return result;
    }
    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult queryContentList(Integer page, Integer rows, Long categoryId){
        EUDataGridResult categoryList = contentService.getContentList(page, rows, categoryId);
        return categoryList;
    }
}
