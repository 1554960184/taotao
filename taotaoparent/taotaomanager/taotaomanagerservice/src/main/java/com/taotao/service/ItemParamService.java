package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
    TaotaoResult getItemParamById(Long cid);
    TaotaoResult insertItemParam(TbItemParam itemParam);
}
