package com.airoucat.airoucatspringboottemplate.utils.page;

import com.qjyy.swjzgxweb.utils.StringUtils;
import lombok.Data;

/**
 * 分页数据
 * 
 * @author ruoyi
 */
@Data
public class PageDomain
{
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderBy;

    /** 排序的方向desc或者asc */
    private String sort = "asc";

    /** 分页参数合理化 */
    private Boolean reasonable = true;

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderBy))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderBy) + " " + sort;
    }

    public void setSort(String sort)
    {
        if (StringUtils.isNotEmpty(sort))
        {
            // 兼容前端排序类型
            if ("ascending".equals(sort))
            {
                sort = "asc";
            }
            else if ("descending".equals(sort))
            {
                sort = "desc";
            }
            this.sort = sort;
        }
    }

    public Boolean getReasonable()
    {
        if (StringUtils.isNull(reasonable))
        {
            return Boolean.TRUE;
        }
        return reasonable;
    }

}
