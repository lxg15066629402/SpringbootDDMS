package com.example.sqlserver_websocket.entity;

import java.util.Date;

/**
 * @author Jone
 * @function Welding 产品信息
 * @date: 2020/12/14
 */
public class Welding {

//    // id
//
//    // 焊接类型
//    private String wType;
//    // 焊接数量
//    private int wNum;
//    // 时间
//
//
//    public String getwType() {
//        return wType;
//    }
//
//    public void setwType(String wType) {
//        this.wType = wType;
//    }
//
//    public int getwNum() {
//        return wNum;
//    }
//
//    public void setwNum(int wNum) {
//        this.wNum = wNum;
//    }
//
//
//    // 属性规则化输出
//    @Override
//    public String toString() {
//        return "{" +
//                "w_type='" + wType + '\'' +
//                ", w_num='" + wNum + '\'' +
//                '}';
//    }

    // 焊接类型
    private String name;

    // 焊接数量
    private int  value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
