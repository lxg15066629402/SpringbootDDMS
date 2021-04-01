package com.example.sqlserver_websocket.entity;


import java.util.Date;

/**
 * @author Jone
 * @function Order 订货信息类
 * @date: 2020/12/14
 */
public class Order {

    // 规范Java 命名方式
    // id
    // private int orderId;
    // 备料单号
    private String rName;
    // 生产订单号
    private String pName;
    // 备料类型
    private String rType;
    // 备料状态
    private String rState;
    // 备料人
    private String rMan;
    // 时间
    // private Date rDate;


    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getrState() {
        return rState;
    }

    public void setrState(String rState) {
        this.rState = rState;
    }

    public String getrMan() {
        return rMan;
    }

    public void setrMan(String rMan) {
        this.rMan = rMan;
    }

    // 属性规则化输出
    @Override
    public String toString() {
        return '{' +
                "r_name='" + rName + '\'' +
                ", p_name='" + pName + '\'' +
                ", r_type='" + rType + '\'' +
                ", r_state='" + rState + '\'' +
                ", r_man='" + rMan + '\'' +
                '}';
    }
}
