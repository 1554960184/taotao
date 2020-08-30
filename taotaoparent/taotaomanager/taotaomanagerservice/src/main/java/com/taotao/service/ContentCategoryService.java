package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

public interface ContentCategoryService {
    List<EUTreeNode> getCategoryList(Long parentId);
    TaotaoResult insertContentCategory(long parentId,String name);
    TaotaoResult deleteContentCateGory(Long id);
    TbContentCategory updateContentCateGory(Long id, String name);
}
