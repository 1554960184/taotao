package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;

public interface ItemParamItemService {
    EUDataGridResult getItemParamItemList(int page, int rows);
    String getItemParamItemById(Long itemId);
}
