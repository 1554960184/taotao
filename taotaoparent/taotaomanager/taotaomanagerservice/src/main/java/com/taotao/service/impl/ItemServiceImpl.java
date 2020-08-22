package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public TbItem getItemById(Long itemID) {
//        itemMapper.select
        TbItemExample example = new TbItemExample();
        //添加查询条件
        TbItemExample.Criteria criteria= example.createCriteria();
        criteria.andIdEqualTo(itemID);
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            TbItem item = list.get(0);
            return item;
        }
        return null;
    }

    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample() ;
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbItem>pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}
