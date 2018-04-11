package com.vidor.Baby.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)//only need the properities here
public class BabyModel {

    private Integer id;

    private String name;

    @JsonIgnore
    private Integer sex;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC+8", locale = "zh")
    private Date dob;

    public BabyModel() {
    }

    public BabyModel(Integer id, String name, Integer sex, Integer age, Date dob) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.dob = dob;
    }

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "BabyModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", dob=" + dob +
                '}';
    }
}
