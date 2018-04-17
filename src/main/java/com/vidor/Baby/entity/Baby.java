package com.vidor.Baby.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Baby implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(max = 1000, message = "string length limit")
    private String name;

    @Max(value = 1, message = "only 0,1")
    @Min(value = 0, message = "only 0,1")
    private Integer sex;

    @Max(value = 10, message = "exceed the limit")
    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC+8", locale = "zh")
    private Date dob;

   // private String nation;

   // private String birthPlace;

    //private Date createDate = new Date();

    public Baby() {
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
        return "Baby{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", dob=" + dob +
                '}';
    }
}
