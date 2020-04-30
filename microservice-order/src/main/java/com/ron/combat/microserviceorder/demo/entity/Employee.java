package com.ron.combat.microserviceorder.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Employee implements Serializable {


    private Integer age;
    private String empno;
    private String name;
}
