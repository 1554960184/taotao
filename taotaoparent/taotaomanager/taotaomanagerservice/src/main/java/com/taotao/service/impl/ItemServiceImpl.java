package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
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

    @Override
    public TaotaoResult createItem(TbItem tbItem,String desc,String itemParam) throws Exception{
        //item补全
        //生成商品ID
        Long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        //商品状态   1-正常，2-上架，3-删除
        tbItem.setStatus((byte)1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //插入到数据库
        itemMapper.insert(tbItem);
        //添加商品描述信息
        TaotaoResult result = insertItemDesc(itemId,desc);
        if (result.getStatus()!=200){
            throw new Exception();
        }
        //添加规格参数
        result = insertItemParamItem(itemId,itemParam);
        if (result.getStatus()!=200){
            throw new Exception();
        }
        return TaotaoResult.ok();
    }

    private TaotaoResult insertItemDesc(Long itemId,String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    private TaotaoResult insertItemParamItem(Long itemParamId,String itemParam){
//        创建一个pojo
        TbItemParamItem itemParamItem1 = new TbItemParamItem();
        itemParamItem1.setItemId(itemParamId);
        itemParamItem1.setParamData(itemParam);
        itemParamItem1.setCreated(new Date());
        itemParamItem1.setUpdated(new Date());
        itemParamItemMapper.insert(itemParamItem1);
        return TaotaoResult.ok();
    }
}
