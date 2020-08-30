package com.taotao.rest.service.impl;


import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类服务
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        //查询分类列表
        catResult.setData(getCatList(0));
        return catResult;
    }

    /**
     * 查询分类结果的方法
     * @param parentId
     * @return
     */
    private List<?> getCatList(long parentId){
        //创建查询调节
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat>list = itemCatMapper.selectByExample(example);
        //返回值list
        List resultList = new ArrayList<>();
        int count = 0;
        for (TbItemCat itemCat:list) {
            //判断是否为父节点
            if (itemCat.getIsParent()){
                CatNode catNode= new CatNode();
                if(parentId==0) {
                    catNode.setName("<a href = '/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
                }else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                catNode.setItem(getCatList(itemCat.getId()));
                resultList.add(catNode);
                count++;
                //第一层只取14条记录
                if (parentId==0&&count>=14){
                    break;
                }
            }else {//判断是否为子节点
                resultList.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }
        }
        return resultList;
    }
}
