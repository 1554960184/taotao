package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    /**
     * 展示规格参数
     */
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public EUDataGridResult getItemParamItemList(int page, int rows) {
        //查询商品参数规格列表
        TbItemParamItemExample example = new TbItemParamItemExample();
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //记录总条数
        PageInfo<TbItemParamItem> pageInfo = new PageInfo(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public String getItemParamItemById(Long itemId) {
        TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = itemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem>list = itemParamItemMapper.selectByExampleWithBLOBs(itemParamItemExample);
        if (list==null&&list.size()==0){
            return null;
        }
        //取商品规格参数
        TbItemParamItem itemParam = list.get(0);
        String Param = itemParam.getParamData();
        //生成html
        //将规格参数转化为Java对象
        List<Map> mapList = JsonUtils.jsonToList(Param,Map.class);
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("<tbody>\n");
        for (Map map:mapList) {
            sb.append("<tr>\n");
            sb.append("<th class=\"tdTitle\">"+map.get("group")+"</th>\n") ;
            sb.append("</tr>\n");
            List<Map>list1 = (List<Map>) map.get("params");
            for(Map m1:list1) {
                sb.append("<tr>\n");
                sb.append("<td class=\"tdTitle\">"+m1.get("k")+"</td>\n");
                sb.append("<td>"+m1.get("v")+"</td>\n");
                sb.append("</tr>\n");
            }
        }
        sb.append("</tbody>\n");
        sb.append("</table>\n");
        return sb.toString();
    }
}
