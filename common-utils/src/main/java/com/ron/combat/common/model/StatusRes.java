package com.ron.combat.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
public class StatusRes {

    private boolean success;
    private String code;
    private String msg;
    private Object bean;

    public StatusRes() {
        this.success = true;
        this.code = "200";
        this.msg = "success";
    }

}