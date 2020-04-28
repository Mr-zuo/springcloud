package com.ron.combat.microserviceuser.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private Long id;
    private String code;
    private String name;
    private String pid;
    private Integer type;
    private Integer lit;
}
