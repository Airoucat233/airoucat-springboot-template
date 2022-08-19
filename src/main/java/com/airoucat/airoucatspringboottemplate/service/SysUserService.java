package com.airoucat.airoucatspringboottemplate.service;


import com.airoucat.airoucatspringboottemplate.domain.SysRole;
import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.mapper.SysUserMapper;
import com.airoucat.airoucatspringboottemplate.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * 用户信息表(sys_user)表服务实现类
 *
 * @author makejava
 * @since 2022-06-22 09:19:52
 */
@Service("sysUserService")
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    public SysUser queryById(Long userId) {
        return this.sysUserMapper.queryById(userId);
    }



    public List<SysUser> queryAll(SysUser query) {
        return sysUserMapper.queryAll(query);
    }


    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    public SysUser insert(SysUser sysUser) {
        this.sysUserMapper.insert(sysUser);
        return sysUser;
    }


    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    public SysUser update(SysUser sysUser) {
        this.sysUserMapper.update(sysUser);
        return this.queryById(sysUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    public boolean deleteById(Long userId) {
        return this.sysUserMapper.deleteById(userId) > 0;
    }

    /**
     * 随机用户名
     * author: huwei
     */
    public String genRandomUserName() {
        Random r = new Random();
        String username = String.valueOf(10000000 + r.nextInt(9999999));
        if (queryByUserName(username) == null) {
            return username;
        } else {
            return genRandomUserName();
        }
    }

    public SysUser queryByloginId(String loginId) {
        return sysUserMapper.queryByloginId(loginId);
    }

    private SysUser queryByUserName(String userName) {
        return sysUserMapper.queryByUserName(userName);
    }

    public List<String> queryRoles(Long userId) {
        return sysUserMapper.queryRoles(userId);
    }

    public SysRole queryRoleByName(String name){
        return sysRoleMapper.queryByName(name);
    }

    /**
     * 设置权限
     * author: huwei
     */
    public void setRoles(Long userId,Long roleId){
        sysRoleMapper.insertUserRole(userId,roleId);
    }

    public boolean validateMail(String loginId, String mail) {
        SysUser user = queryByloginId(loginId);
        return user != null && user.getEmail().equals(mail);
    }

}