package com.example.sqlserver_websocket.entity;

import java.util.Date;


/**
 * @author Jone
 * @function Product 产品信息
 * @date: 2020/12/14
 */
public class Product {
    // id
    private int id;
    // 计划产品数量
    private int planNum;
    // 产品完成数量
    private int comNum;
    // 产品按时完成数量
    private int orderNum;
    // 预计完成时间
    private Date planDate;
    // 维修数量
    private int repairNum;
    // 时间
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanNum() {
        return planNum;
    }

    public void setPlanNum(int planNum) {
        this.planNum = planNum;
    }

    public int getComNum() {
        return comNum;
    }

    public void setComNum(int comNum) {
        this.comNum = comNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public int getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(int repairNum) {
        this.repairNum = repairNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // 实参构造方法


    public Product(int id, int planNum, int comNum, int orderNum, Date planDate, int repairNum, Date date) {
        this.id = id;
        this.planNum = planNum;
        this.comNum = comNum;
        this.orderNum = orderNum;
        this.planDate = planDate;
        this.repairNum = repairNum;
        this.date = date;
    }

    public Product(int repair_num){
        this.repairNum = repair_num;
    }

    // 属性规则化输出
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", plan_num='" + planNum + '\'' +
                ", com_num='" + comNum + '\'' +
                ", order_num='" + orderNum + '\'' +
                ", plan_date='" + planDate + '\'' +
                ", repair_num='" + repairNum + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
