package com.xmaven.controller;

import com.github.pagehelper.PageInfo;
import com.xmaven.entity.SysUser;
import com.xmaven.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author luf
 */
@Controller
@RequestMapping("userctrl")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("page")
    public ModelAndView page(ModelAndView mav,@RequestParam(defaultValue="1")Integer pageNum,@RequestParam(defaultValue="8")Integer pageSize){
        PageInfo<SysUser> pageInfo = sysUserService.selectPage(pageNum, pageSize);
        mav.addObject("pi",pageInfo);//把集合装入模型数据
        mav.setViewName("selectpage");//路径：/WEB-INF/selectpage.jsp
        return mav;
    }
}
