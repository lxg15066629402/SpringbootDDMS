package com.example.sqlserver_websocket.entity;

public class InitData {

    // today
    private String today;

    private String plan;

    private String weekCom;

    private String weekPlan;

    private String repariNum;

    private String OrderNum;

    private String weldingNum;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getWeekCom() {
        return weekCom;
    }

    public void setWeekCom(String weekCom) {
        this.weekCom = weekCom;
    }

    public String getWeekPlan() {
        return weekPlan;
    }

    public void setWeekPlan(String weekPlan) {
        this.weekPlan = weekPlan;
    }

    public String getRepariNum() {
        return repariNum;
    }

    public void setRepariNum(String repariNum) {
        this.repariNum = repariNum;
    }

    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(String orderNum) {
        OrderNum = orderNum;
    }

    public String getWeldingNum() {
        return weldingNum;
    }

    public void setWeldingNum(String weldingNum) {
        this.weldingNum = weldingNum;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
