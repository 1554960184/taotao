package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;

public interface ContentService {
    TaotaoResult insertContent(TbContent content);
    EUDataGridResult getContentList(int page, int rows, Long categoryId);
}
