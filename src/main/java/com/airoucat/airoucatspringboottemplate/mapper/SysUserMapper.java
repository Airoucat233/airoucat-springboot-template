package com.airoucat.airoucatspringboottemplate.mapper;

import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户信息表(sys_user)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-22 09:17:14
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> queryRoles(Long userId);

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(Long userId);

    SysUser queryByUserName(String userName);

    SysUser queryByloginId(@Param("loginId") String loginId);



    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser sysUser);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

}