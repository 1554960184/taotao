package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EUTreeNode> getCategoryList(Long parentId) {
        //根据parendid查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory>list=contentCategoryMapper.selectByExample(example);
        List<EUTreeNode>resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory:list) {
            //创建一个节点
            EUTreeNode node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {

        //创建一个pojo
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //状态，1-正常，2-删除
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //添加记录
        contentCategoryMapper.insert(contentCategory);
        //查看分界点的isParent列是否为true，不是则改为true
        TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断是否为true
        if (!parentCat.getIsParent()){
            parentCat.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回结果
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteContentCateGory(Long id) {
        TbContentCategory targetCat = contentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = targetCat.getParentId();
        contentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        if (targetCat.getIsParent()){
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory>list = contentCategoryMapper.selectByExample(example);
            if (list.size()>0){
                for (TbContentCategory category:list) {
                    contentCategoryMapper.deleteByPrimaryKey(category.getId());
                }
            }
        }
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory>list = contentCategoryMapper.selectByExample(example);
        if (list.size()==0){
            TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
            parentCat.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parentCat);
            return TaotaoResult.ok();
        }
        return TaotaoResult.ok();
    }

    @Override
    public TbContentCategory updateContentCateGory(Long id,String name) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        contentCategoryMapper.updateByPrimaryKey(contentCategory);
        return contentCategory;
    }

}
