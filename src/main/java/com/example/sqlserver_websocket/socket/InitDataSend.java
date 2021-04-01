package com.example.sqlserver_websocket.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sqlserver_websocket.entity.InitData;
import com.example.sqlserver_websocket.entity.Order;
import com.example.sqlserver_websocket.entity.Product;
import com.example.sqlserver_websocket.entity.Welding;
import com.example.sqlserver_websocket.service.ProdectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebListener;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class InitDataSend {

    @Autowired
    private ProdectService prodectService;

    // 定义一个全局变量
    public static Map<String, Object> bufferData = new HashMap<String, Object>();


    // 获取当前时间
    Date dateCurr = new Date();
    Calendar c = Calendar.getInstance();


    @PostConstruct
    public void run(){

        List<Product> product = prodectService.findAllProduct();
        try {

            // 计划完成率
            c.setTime(dateCurr);
            int weekdayPlanCom = c.get(Calendar.DAY_OF_WEEK);
            Map<String, Object> ComRateTest = new LinkedHashMap<String, Object>();
            JSONArray jsonArrayComRate_num = new JSONArray();
            ComRateTest.put("type", 3);
            ComRateTest.put("data", jsonArrayComRate_num);

            // 按时完成率
            int weekdayPlan = c.get(Calendar.DAY_OF_WEEK);
            Map<String, Object> ontimeComRateTest = new LinkedHashMap<String, Object>();
            JSONArray jsonArrayPlanRateCurrNum = new JSONArray();
            ontimeComRateTest.put("type", 4);
            ontimeComRateTest.put("data", jsonArrayPlanRateCurrNum);

            // 一周维修情况
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            Map<String, Object> NewWeekRepairTest = new LinkedHashMap<String, Object>();
            JSONArray jsonArrayCurrentRepairNum = new JSONArray();
            NewWeekRepairTest.put("type", 5);
            NewWeekRepairTest.put("data", jsonArrayCurrentRepairNum);

            // 遍历 product 表
            for (Product attribute : product) {
                /**
                 * 获取当天情况
                 */
                int plan_num = attribute.getPlanNum();
                int com_num = attribute.getComNum();
                // Java 除法，保留两位小数，使用double类型
                double plan_rate = com_num / plan_num * 100;

                /**
                 * 获取按时完成数据
                 */
                int order_num = attribute.getOrderNum();
                float order_rate = (float) (order_num) / (float) plan_num * 100;

                // 获取一周时间
                c.setTime(dateCurr);

                // 设置输出时间的格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String currentdate = dateFormat.format(dateCurr);

                //获取表中时间
                Date date2 = attribute.getDate();
                String gettdate = dateFormat.format(date2);

                // 进行时间的比较
                if (currentdate.compareTo(gettdate) == 0) {

                    /**
                     * 获取当天完成情况
                     */
                    Map<String, Object> test = new HashMap<String, Object>(new LinkedHashMap());
                    JSONObject jsonCompleteRate_today = new JSONObject(new LinkedHashMap());
                    test.put("type", 1);
                    test.put("data", jsonCompleteRate_today);

                    jsonCompleteRate_today.put("planNum", plan_num);
                    jsonCompleteRate_today.put("completeNum", com_num);
                    jsonCompleteRate_today.put("planComRate", plan_rate);
                    bufferData.put("1", test);

                    /**
                     *  按时完成率
                      */
                    Map<String, Object> testPlan = new HashMap<String, Object>(new LinkedHashMap());
                    JSONObject jsonPlanRate = new JSONObject(new LinkedHashMap());
                    testPlan.put("type", 2);
                    testPlan.put("data", jsonPlanRate);

                    jsonPlanRate.put("planNum", plan_num);
                    jsonPlanRate.put("orderComNum", order_num);
                    jsonPlanRate.put("orderComRate", order_rate);
                    bufferData.put("2", testPlan);


                }

                // 获取表格中的时间
                Date tableDate = attribute.getDate();
                Calendar tablep = Calendar.getInstance();
                tablep.setTime(tableDate);
                if(c.get(Calendar.WEEK_OF_YEAR) == tablep.get(Calendar.WEEK_OF_YEAR)){
                    int plan = attribute.getPlanNum();

                    int com = attribute.getComNum();
                    float OrderRate = (float)com / (float)plan * 100;
                    // 此时处理时间  -1
                    // jsonNewPlanRateCurrNum.put(String.format("week=%d", tableweek-1), OrderRate);
                    jsonArrayComRate_num.add(OrderRate);

                }


                // 获取表格中的时间
                Calendar tablec = Calendar.getInstance();
                tablec.setTime(tableDate);
                int tableweek = tablec.get(Calendar.DAY_OF_WEEK);
                // 获取当前时间 dateCurr，首先判断这两个时间是否在一周中
                if((tableweek <= weekdayPlan)  &&  (c.get(Calendar.WEEK_OF_YEAR) == tablec.get(Calendar.WEEK_OF_YEAR))){

                    int plan = attribute.getPlanNum();

                    int order_ = attribute.getOrderNum();
                    double OrderRate = (double)order_ / (double)plan * 100;

                    jsonArrayPlanRateCurrNum.add(OrderRate);

                }

                /**
                 * 维修不良情况
                 */
                Date repairDate = attribute.getDate();
                Calendar repairc = Calendar.getInstance();
                repairc.setTime(repairDate);
                int repairweek = repairc.get(Calendar.DAY_OF_WEEK);
                // 进行判断条件设置
                if((repairweek <= weekday)  &&  (c.get(Calendar.WEEK_OF_YEAR) == repairc.get(Calendar.WEEK_OF_YEAR))) {
                    jsonArrayCurrentRepairNum.add(attribute.getRepairNum());
                }
            }

            // 分别加入buffer
            InitDataSend.bufferData.put("3", ComRateTest);

            InitDataSend.bufferData.put("4", ontimeComRateTest);

            InitDataSend.bufferData.put("5", NewWeekRepairTest);

            List<Order> order_test =  prodectService.findAllOrder();

            Map<String, Object> orderTest = new LinkedHashMap<String, Object>();

            JSONArray jsonAOrderEvent = new JSONArray();

            orderTest.put("type", 6);
            orderTest.put("data", jsonAOrderEvent);

            Order id_1 = order_test.get(0);
            Order id_2 = order_test.get(1);
            Order id_3 = order_test.get(2);
            Order id_4 = order_test.get(3);
            jsonAOrderEvent.add(id_1);
            jsonAOrderEvent.add(id_2);
            jsonAOrderEvent.add(id_3);
            jsonAOrderEvent.add(id_4);

            InitDataSend.bufferData.put("6", orderTest);

            /**
             * 维修情况
             */
            List<Welding> welding_test = prodectService.findAllWelding();
            Map<String, Object>  testWelding = new LinkedHashMap<String, Object>();

            JSONArray  jsonArrayWedingEvent = new JSONArray();

            testWelding.put("type", 7);
            testWelding.put("data", jsonArrayWedingEvent);

            Welding w_id_1 = welding_test.get(0);
            Welding w_id_2 = welding_test.get(1);
            Welding w_id_3 = welding_test.get(2);
            Welding w_id_4 = welding_test.get(3);
            Welding w_id_5 = welding_test.get(4);

            jsonArrayWedingEvent.add( w_id_1);
            jsonArrayWedingEvent.add( w_id_2);
            jsonArrayWedingEvent.add( w_id_3);
            jsonArrayWedingEvent.add( w_id_4);
            jsonArrayWedingEvent.add( w_id_5);


            InitDataSend.bufferData.put("7", testWelding);

        }catch(Exception e){
        e.printStackTrace();
        }
    }
}
