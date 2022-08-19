package com.airoucat.airoucatspringboottemplate.handler;

import com.airoucat.airoucatspringboottemplate.aop.annotation.DataScope;
import com.airoucat.airoucatspringboottemplate.domain.SysUser;
import com.airoucat.airoucatspringboottemplate.global.CurrentUserContextHolder;
import com.airoucat.airoucatspringboottemplate.utils.ObjectUtils;
import com.airoucat.airoucatspringboottemplate.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Properties;

@Slf4j
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        })
public class DataScopeInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // TODO Auto-generated method stub
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        DataScope dataScope = getDataScope(ms);
        if (dataScope == null){
            return invocation.proceed();
        }
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
        String origSql = boundSql.getSql();
//        log.info("origSql : {}", origSql);
        // 组装新的 sql
        // todo you weaving business
        String newSql = modifyOrgSql(origSql,dataScope);

        // 重新new一个查询语句对象
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), newSql,
                boundSql.getParameterMappings(), boundSql.getParameterObject());

        // 把新的查询放到statement里
        MappedStatement newMs = newMappedStatement(ms, new BoundSqlSource(newBoundSql));
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }

        args[0] = newMs;
        if (args.length == 6) {
            args[5] = newMs.getBoundSql(parameter);
        }
        log.info("mybatis intercept sql:{},Mapper方法是：{}", boundSql.getSql(), ms.getId());


        return invocation.proceed();
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new
                MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }


    private DataScope getDataScope(MappedStatement mappedStatement) {
        String id = mappedStatement.getId();
        // 获取 Class Method
        String clazzName = id.substring(0, id.lastIndexOf('.'));
        String mapperMethod = id.substring(id.lastIndexOf('.') + 1);

        Class<?> clazz;
        try {
            clazz = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            return null;
        }
        Method[] methods = clazz.getMethods();

        DataScope dataScope = null;
        for (Method method : methods) {
            if (method.getName().equals(mapperMethod)) {
                dataScope = method.getAnnotation(DataScope.class);
                break;
            }
        }
        return dataScope;
    }

    @Override
    public Object plugin(Object target) {
        // TODO Auto-generated method stub
//        log.info("MysqlInterCeptor plugin>>>>>>>{}", target);
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
        String dialect = properties.getProperty("dialect");
//        log.info("mybatis intercept dialect:>>>>>>>{}", dialect);
    }

    /**
     * 定义一个内部辅助类，作用是包装 SQL
     */
    class BoundSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }

    }

    /**
     * 根据权限点拼装对应sql
     * @return 拼装后的sql
     */
    private String modifyOrgSql(String orgSQql,DataScope dataScope) throws JSQLParserException {
        String authSql = getAuthSql(dataScope);
        if (StringUtils.isEmpty(authSql)){
            return orgSQql;
        }
        //使用CCJSqlParserUtil对sql进行解析
        //参考mybatis-plus源码{@link com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor#autoCountSql}
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(orgSQql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
//        Table fromItem = (Table) plain.getFromItem();
        //有别名用别名，无别名用表名，防止字段冲突报错
//        String aliasTableName = fromItem.getAlias() == null ? fromItem.getName() : fromItem.getAlias().getName();
        //对authSql中的占位符进行替换
//        authSql = authSql.replace(TABLE_NAME, aliasTableName).replace(USER_ID, MOCK_USERID).replace(DEPT_ID, MOCK_DEPT_ID);
        if (plain.getWhere() == null) {
            plain.setWhere(CCJSqlParserUtil.parseCondExpression(authSql));
        } else {
            plain.setWhere(new AndExpression(plain.getWhere(), CCJSqlParserUtil.parseCondExpression(authSql)));
        }
        return select.toString();
    }

    private String getAuthSql(DataScope scope){
        SysUser user = CurrentUserContextHolder.getCurrentUser();
        Object fieldValue = ObjectUtils.getFieldValueByName(user, scope.type().getFieldName());
        return String.format("%s = %s",scope.fieldName(),fieldValue);
    }

}
