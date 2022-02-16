package com.xmaven.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmaven.entity.SysUser;
import com.xmaven.mapper.SysUserMapper;
import com.xmaven.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getAll() {
        return this.sysUserMapper.selectAll();
    }

    @Override
    public PageInfo<SysUser> selectPage(Integer pageNum, Integer pageSize) {

        //翻到多少页
        PageHelper.startPage(pageNum, pageSize);
        //取对象
        List<SysUser> list = this.sysUserMapper.selectAll();
        //放到集合里面
        PageInfo<SysUser> pi = new PageInfo<>(list);

        return pi;
    }
}
