package com.airoucat.airoucatspringboottemplate.pojo;

import lombok.Data;

/**
 * 过滤字段信息
 * author: huwei
 */
@Data
public class TableFieldFilter {
    private String fieldName; //字段名
    private String queryType; //查询类型
    private String value;//值
    private String sep = ",";//多个值的分隔符
    private String label;//标签(中文名)
}
