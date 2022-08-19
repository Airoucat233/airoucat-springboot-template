package com.airoucat.airoucatspringboottemplate.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageParams;
import com.qjyy.swjzgxweb.utils.page.PageDomain;
import com.qjyy.swjzgxweb.utils.page.TableSupport;


/**
 * 分页工具类
 * 
 * @author ruoyi
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据(GET)
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        startPage(pageDomain);
    }
    /**
     * 设置请求分页数据(POST)
     */
    public static void startPage(PageDomain pageDomain){
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = pageDomain.getOrderBy();
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
