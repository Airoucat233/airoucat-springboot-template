package com.airoucat.airoucatspringboottemplate.mapper;

import com.airoucat.airoucatspringboottemplate.domain.SysLogininfor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 系统访问记录(sys_logininfor)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-19 17:38:02
 */
@Mapper
public interface SysLogininforMapper extends BaseMapper<SysLogininfor> {

    /**
     * 通过ID查询单条数据
     *
     * @param infoId 主键
     * @return 实例对象
     */
    SysLogininfor queryById(Long infoId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysLogininfor> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysLogininfor 实例对象
     * @return 对象列表
     */
    List<SysLogininfor> queryAll(SysLogininfor sysLogininfor);

    /**
     * 新增数据
     *
     * @param sysLogininfor 实例对象
     * @return 影响行数
     */
    int insert(SysLogininfor sysLogininfor);

    /**
     * 修改数据
     *
     * @param sysLogininfor 实例对象
     * @return 影响行数
     */
    int update(SysLogininfor sysLogininfor);

    /**
     * 通过主键删除数据
     *
     * @param infoId 主键
     * @return 影响行数
     */
    int deleteById(Long infoId);

}