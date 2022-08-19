package com.airoucat.airoucatspringboottemplate.pojo.vo;

import com.airoucat.airoucatspringboottemplate.pojo.TableFieldFilter;
import com.airoucat.airoucatspringboottemplate.utils.StringUtils;
import com.airoucat.airoucatspringboottemplate.utils.page.PageDomain;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;

import java.util.List;

@Data
public class QueryVo<T> {
    private PageDomain pageDomain;
    private List<TableFieldFilter> fieldFilters;

    public QueryWrapper<T> parseQueryCondition(){
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for(TableFieldFilter filter : this.fieldFilters){
            if (!StringUtils.isEmpty(filter.getValue())){
                switch (filter.getQueryType()){
                    case "eq": wrapper.eq(filter.getFieldName(),filter.getValue());break;
                    case "ne": wrapper.ne(filter.getFieldName(),filter.getValue());break;
                    case "like": wrapper.like(filter.getFieldName(),filter.getValue());break;
                    case "notlike": wrapper.notLike(filter.getFieldName(),filter.getValue());break;
                    case "in": wrapper.in(filter.getFieldName(), (Object[]) filter.getValue().split(filter.getSep()));break;
                }
            }
        }
        return wrapper;
    }
}
