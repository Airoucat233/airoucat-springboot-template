package com.airoucat.airoucatspringboottemplate.domain;

import java.util.Date;
import java.io.Serializable;

/**
 * (Test)实体类
 *
 * @author makejava
 * @since 2022-06-16 16:50:33
 */
public class Test implements Serializable {
    private static final long serialVersionUID = -44213267333607676L;
    
    private Integer id;
    
    private String name;
    
    private String password;
    
    private Date date;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}