package com.xmaven.service;

import com.github.pagehelper.PageInfo;
import com.xmaven.entity.SysUser;

import java.util.List;

/**
 * @author Sanji
 */
public interface SysUserService {
    List<SysUser> getAll();

    PageInfo<SysUser> selectPage(Integer pageNum, Integer pageSize);
}
