package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *内容分页管理
 */
@Controller
@RequestMapping("/content")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService categoryService;
    @RequestMapping("/category/list")
    @ResponseBody
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EUTreeNode>list = categoryService.getCategoryList(parentId);
        return list;
    }

    @RequestMapping("/category/create")
    @ResponseBody
    public TaotaoResult createContentCategroy(Long parentId,String name){
        TaotaoResult result = categoryService.insertContentCategory(parentId,name);
        return result;
    }

    @RequestMapping("/category/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategroy(Long id){
        TaotaoResult result = categoryService.deleteContentCateGory(id);
        return result;
    }

    @RequestMapping("/category/update")
    @ResponseBody
    public TaotaoResult updateContentCategroy(Long id,String name){
        TbContentCategory category = categoryService.updateContentCateGory(id,name);
        return TaotaoResult.ok(category);
    }


}
