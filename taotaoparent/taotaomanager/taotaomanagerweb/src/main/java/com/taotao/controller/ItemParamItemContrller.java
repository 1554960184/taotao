package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemParamItemContrller {
    @Autowired
    private ItemParamItemService itemService;
    @RequestMapping("showItem/{itemId}")
    public String showItemParamItemById(@PathVariable Long itemId, Model model){
        String paramData = itemService.getItemParamItemById(itemId);
        model.addAttribute("itemParam",paramData);
        return "item";
    }

    @RequestMapping("/item/param/list")
    @ResponseBody
    public EUDataGridResult getItemParamItemById(Integer page,Integer rows){
        EUDataGridResult result = itemService.getItemParamItemList(page,rows);
        return result;
    }
}
