package com.taotao.controller;
import com.github.pagehelper.Page;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("item/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page,Integer rows){
        System.out.println("page:"+page);
        System.out.println("rows："+rows);
        EUDataGridResult result = itemService.getItemList(page,rows);
        System.out.println(result.getRows().getClass());
        Page page1 = (Page) result.getRows();
        System.out.println("page1:"+page1);
        System.out.println(result.getTotal());
        System.out.println("result="+result);
        return result;
    }
}
