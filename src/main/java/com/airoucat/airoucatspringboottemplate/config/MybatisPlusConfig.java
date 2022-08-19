package com.airoucat.airoucatspringboottemplate.config;

import com.airoucat.airoucatspringboottemplate.handler.DataScopeInterceptor;
import com.airoucat.airoucatspringboottemplate.utils.SpringUtils;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Configuration
public class MybatisPlusConfig {
//    /**
//     * 分页插件
//     * @return
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//        // paginationInterceptor.setOverflow(false);
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        // paginationInterceptor.setLimit(500);
//        return paginationInterceptor;
//    }
//    @Bean
//    public DataScopeInterceptor dataScopeInterceptor(){
//        return new DataScopeInterceptor();
//    }
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    /**
     * 只会执行一次
     * 顺序：Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     */
    @PostConstruct
    public void addMybatisInterceptor() {
        //确保PageHelper拦截器在前面
        SpringUtils.getBean(PageHelperAutoConfiguration.class);
        //创建自定义mybatis拦截器，添加到chain的最后面
        DataScopeInterceptor dataScopeInterceptor = new DataScopeInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            //自己添加
            configuration.addInterceptor(dataScopeInterceptor);
        }
    }

 
}