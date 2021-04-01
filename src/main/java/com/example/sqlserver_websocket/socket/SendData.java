package com.example.sqlserver_websocket.socket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sqlserver_websocket.entity.Order;
import com.example.sqlserver_websocket.entity.Product;
import com.example.sqlserver_websocket.entity.Welding;
import com.example.sqlserver_websocket.service.ProdectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

// 无初始化信息数据连接
@Component
public class SendData {

    @Autowired
    private ProdectService prodectService;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 全局变量
      */
    // 获取当前时间
    Date dateCurr = new Date();
    Calendar c = Calendar.getInstance();


    /**
     * @Author Jone
     * @Description 设置每隔10秒执行一次
     * @Date 2020/12/14
     * @Param
     * @return void
     **/
    @Scheduled(cron = "0/3 * * * * ?")
    private void getToday(){

        List<Product> product =  prodectService.findAllProduct();
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        // List<Product> producttime = prodectService.SelectWeekProductDate(dateCurr);
        // System.out.println("==============================================*" + producttime);


        // 数据发送
        try{

            // 遍历 product 表
            for(Product attribute : product){
                /**
                 * 获取当天情况
                 */
                int plan_num = attribute.getPlanNum();
                int com_num = attribute.getComNum();
                // Java 除法，保留两位小数，使用double类型 ==
                double plan_rate  =  (double) (com_num) /  (double)(plan_num)* 100;

                // 获取一周时间
                c.setTime(dateCurr);
                int weekday = c.get(Calendar.DAY_OF_WEEK);

                // 设置输出时间的格式
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String currentdate = dateFormat.format(dateCurr);

                //获取表中时间
                Date date2 = attribute.getDate();
                String gettdate = dateFormat.format(date2);

                // 进行时间的比较
                if(currentdate.compareTo(gettdate) == 0){

                    /**
                     * 获取当天完成情况
                     */

                    Map<String, Object> test = new LinkedHashMap<String, Object>();
                    JSONObject jsonCompleteRate_today = new JSONObject(new LinkedHashMap());
                    test.put("type", 1);
                    test.put("data", jsonCompleteRate_today);

                    jsonCompleteRate_today.put("planNum", plan_num);
                    jsonCompleteRate_today.put("completeNum", com_num);
                    jsonCompleteRate_today.put("planComRate", plan_rate);

                    String msg_complete = jsonCompleteRate_today.toString();
                    System.out.println("=====TodayPlan====" + msg_complete);
                    Thread.sleep(1000 * 1);
                    // webSocketServer.sendInfo(msg_complete);

                    InitDataSend.bufferData.put("1", test);
                    // Json序列化
                    String maptest = JSON.toJSONString(test); //
                    webSocketServer.sendInfo(maptest);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 获得完成数据
     */
    @Scheduled(cron = "0/4 * * * * ?")
    private void getPlan(){

        List<Product> product =  prodectService.findAllProduct();

        try{

            // 遍历 product 表
            for(Product attribute : product) {


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
                    // 按时完成率
                    int plan_num = attribute.getPlanNum();
                    int order_num = attribute.getOrderNum();
                    double order_rate = (double) (order_num) / (double) plan_num * 100; //
                    Date plan_date = attribute.getPlanDate();

                    Map<String, Object> testPlan = new LinkedHashMap<String, Object>();
                    JSONObject jsonPlanRate = new JSONObject(new LinkedHashMap());
                    testPlan.put("type", 2);
                    testPlan.put("data", jsonPlanRate);

                    jsonPlanRate.put("planNum", plan_num);
                    jsonPlanRate.put("orderComNum", order_num);
                    jsonPlanRate.put("orderComRate", order_rate);
                    InitDataSend.bufferData.put("2", testPlan);
                    // Json序列化
                    String maptestPlan = JSON.toJSONString(testPlan); //
                    Thread.sleep(1000 * 1);
                    webSocketServer.sendInfo(maptestPlan);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获得计划周完成率信息
     */
    @Scheduled(cron = "0/6 * * * * ?")
    private void getWeekRate(){

        try{

            List<Product> product =  prodectService.findAllProduct();
            c.setTime(dateCurr);
            int weekdayPlanCom = c.get(Calendar.DAY_OF_WEEK);
            // 计划完成率
            Map<String, Object> ComRateTest = new LinkedHashMap<String, Object>();
            JSONArray jsonArrayComRate_num = new JSONArray();


            ComRateTest.put("type", 3);
            ComRateTest.put("data", jsonArrayComRate_num);

            for(Product proweeklist : product){

                // 获取表格中的时间
                Date tableDate = proweeklist.getDate();
                Calendar tablep = Calendar.getInstance();
                tablep.setTime(tableDate);
                if(c.get(Calendar.WEEK_OF_YEAR) == tablep.get(Calendar.WEEK_OF_YEAR)){
                    int plan = proweeklist.getPlanNum();

                    int com = proweeklist.getComNum();
                    float OrderRate = (float)com / (float)plan * 100;
                    // 此时处理时间  -1
                    // jsonNewPlanRateCurrNum.put(String.format("week=%d", tableweek-1), OrderRate);
                    jsonArrayComRate_num.add(OrderRate);

                }
            }

            InitDataSend.bufferData.put("3", ComRateTest);

            //Jone 序列化
            String mapComRateTest = JSON.toJSONString(ComRateTest); //
            webSocketServer.sendInfo(mapComRateTest);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取按时完成率情况
     */
    @Scheduled(cron = "*/8 * * * * ?")
    private void ontimeNewComRate(){

        try{

            List<Product> product =  prodectService.findAllProduct();
            c.setTime(dateCurr);
            int weekdayPlan = c.get(Calendar.DAY_OF_WEEK);

            // 按时完成率
            Map<String, Object> ontimeComRateTest = new LinkedHashMap<String, Object>();

            JSONArray jsonArrayPlanRateCurrNum = new JSONArray();

            ontimeComRateTest.put("type", 4);
            ontimeComRateTest.put("data", jsonArrayPlanRateCurrNum);

            for(Product productlist: product){
                // 获取表格中的时间
                Date tableDate = productlist.getDate();
                Calendar tablec = Calendar.getInstance();
                tablec.setTime(tableDate);
                int tableweek = tablec.get(Calendar.DAY_OF_WEEK);

                // 获取当前时间 dateCurr，首先判断这两个时间是否在一周中

                if((tableweek <= weekdayPlan)  &&  (c.get(Calendar.WEEK_OF_YEAR) == tablec.get(Calendar.WEEK_OF_YEAR))){

                    int plan = productlist.getPlanNum();

                    int order = productlist.getOrderNum();
                    double OrderRate = (double)order / (double)plan * 100;
                    // 此时处理时间  -1
                    // jsonNewPlanRateCurrNum.put(String.format("week=%d", tableweek-1), OrderRate);
                    jsonArrayPlanRateCurrNum.add(OrderRate);

                }
            }


            InitDataSend.bufferData.put("4", ontimeComRateTest);
            // Json 序列化
            String mapontimeComRateTest = JSON.toJSONString(ontimeComRateTest); //
            webSocketServer.sendInfo( mapontimeComRateTest);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     维修信息
     */
    @Scheduled(cron = "0/10 * * * * ?")
    private void getNewWeekRepair(){

        c.setTime(dateCurr);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        List<Product> product =  prodectService.findAllProduct();

        try{

            Map<String, Object> NewWeekRepairTest = new LinkedHashMap<String, Object>();
            // JSONObject jsonNewCurrentRepairNum = new JSONObject(new LinkedHashMap());

            JSONArray jsonArrayCurrentRepairNum = new JSONArray();
            NewWeekRepairTest.put("type", 5);
            NewWeekRepairTest.put("data", jsonArrayCurrentRepairNum);

            for(Product productRepairList: product){
                Date repairDate = productRepairList.getDate();
                Calendar repairc = Calendar.getInstance();
                repairc.setTime(repairDate);
                int repairweek = repairc.get(Calendar.DAY_OF_WEEK);
                // 进行判断条件设置
                if((repairweek <= weekday)  &&  (c.get(Calendar.WEEK_OF_YEAR) == repairc.get(Calendar.WEEK_OF_YEAR))) {
                    // jsonNewCurrentRepairNum.put(String.format("week=%d", repairweek-1), productRepairList.getRepairNum());
                    jsonArrayCurrentRepairNum.add(productRepairList.getRepairNum());
                }

            }

            InitDataSend.bufferData.put("5", NewWeekRepairTest);
            // Json 序列化
            String mapNewWeekRepairTest = JSON.toJSONString(NewWeekRepairTest); //
            webSocketServer.sendInfo(mapNewWeekRepairTest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 备料执行情况
     */
    @Scheduled(cron = "0/12 * * * * ?")
    private void getOrder(){
        try{
            /**
             * 备料执行情况
             */
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
            // Json 序列化
            String mapOrder = JSON.toJSONString(orderTest); //
            webSocketServer.sendInfo(mapOrder);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *  今日维修不良情况
     */
    @Scheduled(cron = "0/14 * * * * ?")
    private void getWelding(){

        try{

            /**
             * 今日维修不良情况
             */
            List<Welding> welding_test =  prodectService.findAllWelding();
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
            // Json 序列化
            String mapWelding = JSON.toJSONString(testWelding); //
            webSocketServer.sendInfo(mapWelding);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}


