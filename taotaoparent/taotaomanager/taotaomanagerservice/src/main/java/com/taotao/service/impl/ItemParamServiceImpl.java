package com.taotao.service.impl;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService{
    /**
     * 商品规格参数模板
     * @param cid
     * @return
     */
    @Autowired
    private TbItemParamMapper itemParamMapper;
    @Override
    public TaotaoResult getItemParamById(Long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam>list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if (list!=null && list.size()>0){
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
//        补全pojo
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //        插入到规格参数模板表
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }
}
