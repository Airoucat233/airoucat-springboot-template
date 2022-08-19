package com.airoucat.airoucatspringboottemplate.service;


import com.airoucat.airoucatspringboottemplate.domain.Test;
import com.airoucat.airoucatspringboottemplate.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Test)表服务实现类
 *
 * @author makejava
 * @since 2022-06-17 14:47:50
 */
@Service("testService")
public class TestService {
    @Resource
    private TestMapper testMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Test queryById(Integer id) {
        return this.testMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<Test> queryAllByLimit(int offset, int limit) {
        return this.testMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param test 实例对象
     * @return 实例对象
     */
    public Test insert(Test test) {
        this.testMapper.insert(test);
        return test;
    }

    /**
     * 修改数据
     *
     * @param test 实例对象
     * @return 实例对象
     */
    public Test update(Test test) {
        this.testMapper.update(test);
        return this.queryById(test.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer id) {
        return this.testMapper.deleteById(id) > 0;
    }


    public List<Test> listAll() {
        return testMapper.listAll();
    }
}