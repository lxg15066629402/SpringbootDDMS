//package com.example.sqlserver_websocket.socket;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.example.sqlserver_websocket.entity.Order;
//import com.example.sqlserver_websocket.entity.Product;
//import com.example.sqlserver_websocket.entity.Welding;
//import com.example.sqlserver_websocket.service.ProdectService;
//import com.fasterxml.jackson.annotation.JsonAlias;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//// 无初始化信息数据连接
//@Component
//public class SendInitTable {
//
//    @Autowired
//    private ProdectService prodectService;
//
//    @Autowired
//    private WebSocketServer webSocketServer;
//
//    /**
//     * 全局变量
//      */
//    // 获取当前时间
//    Date dateCurr = new Date();
//    Calendar c = Calendar.getInstance();
//
//
//    /**
//     * @Author Jone
//     * @Description 设置每隔10秒执行一次
//     * @Date 2020/12/14
//     * @Param
//     * @return void
//     **/
//    @Scheduled(cron = "0/5 * * * * ?")
//    private void getToday(){
//
//
//        List<Product> product =  prodectService.findAllProduct();
//        // 数据发送
//        try{
//
//            // 遍历 product 表
//            for(Product attribute : product){
//                /**
//                 * 获取当天情况
//                 */
//                int plan_num = attribute.getPlanNum();
//                int com_num = attribute.getComNum();
//                // Java 除法，保留两位小数，使用double类型
//                double plan_rate  = com_num / plan_num * 100;
//
//                // 获取一周时间
//                c.setTime(dateCurr);
//                int weekday = c.get(Calendar.DAY_OF_WEEK);
//
//                // 设置输出时间的格式
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//                String currentdate = dateFormat.format(dateCurr);
//
//                //获取表中时间
//                Date date2 = attribute.getDate();
//                String gettdate = dateFormat.format(date2);
//
//                // 进行时间的比较
//                if(currentdate.compareTo(gettdate) == 0){
//
//                    /**
//                     * 获取当天完成情况
//                     */
//
//                    Map<String, Object> test = new HashMap<String, Object>(new LinkedHashMap());
//                    JSONObject jsonCompleteRate_today = new JSONObject(new LinkedHashMap());
//                    test.put("type", 1);
//                    test.put("data", jsonCompleteRate_today);
//
//                    jsonCompleteRate_today.put("planNum", plan_num);
//                    jsonCompleteRate_today.put("completeNum", com_num);
//                    jsonCompleteRate_today.put("planComRate", plan_rate);
//
//                    String msg_complete = jsonCompleteRate_today.toString();
//                    System.out.println("=====TodayPlan====" + msg_complete);
//                    Thread.sleep(1000 * 1);
//                    // webSocketServer.sendInfo(msg_complete);
//
//                    InitDataSend.bufferData.put("1", test);
//                    // Json序列化
//                    String maptest = JSON.toJSONString(test); //
//                    webSocketServer.sendInfo(maptest);
//                }
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
////        return msg_plan;
//    }
//
//    /**
//     * 获得完成数据
//     */
//    @Scheduled(cron = "0/10 * * * * ?")
//    private void getPlan(){
//
//        List<Product> product =  prodectService.findAllProduct();
//
//        try{
//
//            // 遍历 product 表
//            for(Product attribute : product) {
//
//
//                // 获取一周时间
//                c.setTime(dateCurr);
//
//                // 设置输出时间的格式
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//                String currentdate = dateFormat.format(dateCurr);
//
//                //获取表中时间
//                Date date2 = attribute.getDate();
//                String gettdate = dateFormat.format(date2);
//
//                // 进行时间的比较
//                if (currentdate.compareTo(gettdate) == 0) {
//                    // 按时完成率
//                    int plan_num = attribute.getPlanNum();
//                    int order_num = attribute.getOrderNum();
//                    double order_rate = (double) (order_num) / (double) plan_num * 100; //
//                    Date plan_date = attribute.getPlanDate();
//
//                    Map<String, Object> testPlan = new HashMap<String, Object>(new LinkedHashMap());
//                    JSONObject jsonPlanRate = new JSONObject(new LinkedHashMap());
//                    testPlan.put("type", 2);
//                    testPlan.put("data", jsonPlanRate);
//
//                    jsonPlanRate.put("planNum", plan_num);
//                    jsonPlanRate.put("orderComNum", order_num);
//                    jsonPlanRate.put("orderComRate", order_rate);
//                    InitDataSend.bufferData.put("2", testPlan);
//                    // Json序列化
//                    String maptestPlan = JSON.toJSONString(testPlan); //
//                    Thread.sleep(1000 * 1);
//                    webSocketServer.sendInfo(maptestPlan);
//                }
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获得计划周完成率信息
//     */
//    @Scheduled(cron = "0/20 * * * * ?")
//    private void getWeekRate(){
//
//        try{
//
//            List<Product> product =  prodectService.findAllProduct();
//            // 计划完成率
//            Map<String, Object> ComRateTest = new HashMap<String, Object>(new LinkedHashMap());
//            JSONArray jsonArrayComRate_num = new JSONArray();
//
//
//            ComRateTest.put("type", 3);
//            ComRateTest.put("info3", jsonArrayComRate_num);
//
//            Product p1 = product.get(0);
//
//            int plan_1 = p1.getPlanNum();
//            int com_1 = p1.getComNum();
//            double ComRate_1 = (double)com_1 / (double)plan_1 * 100;
//
//            jsonArrayComRate_num.add(ComRate_1);
//
//
//            Product p2 = product.get(1);
//
//            int plan_2 = p2.getPlanNum();
//            int com_2 = p2.getComNum();
//            double ComRate_2 = (double)com_2 / (double)plan_2 * 100;
//            jsonArrayComRate_num.add(ComRate_2);
//
//
//
//            Product p3 = product.get(2);
//
//            int plan_3 = p3.getPlanNum();
//            int com_3 = p3.getComNum();
//            double ComRate_3 = (double)com_3 / (double)plan_3 * 100;
//
//            jsonArrayComRate_num.add(ComRate_3);
//
//
//
//            Product p4 = product.get(3);
//
//            int plan_4 = p4.getPlanNum();
//            int com_4 = p4.getComNum();
//            double ComRate_4 = (double)com_4 / (double)plan_4 * 100;
//            jsonArrayComRate_num.add(ComRate_4);
//
//
//            Product p5 = product.get(4);
//
//            int plan_5 = p5.getPlanNum();
//            int com_5 = p5.getComNum();
//            double ComRate_5 = (double)com_5 / (double)plan_5 * 100;
//            jsonArrayComRate_num.add( ComRate_5);
//
//
//
//            Product p6 = product.get(5);
//
//            int plan_6 = p6.getPlanNum();
//            int com_6 = p6.getComNum();
//            double ComRate_6 = (double)com_6 / (double)plan_6 * 100;
//            jsonArrayComRate_num.add( ComRate_6);
//
//
//            Product p7 = product.get(6);
//
//            int plan_7 = p7.getPlanNum();
//            int com_7 = p7.getComNum();
//            double ComRate_7 = (double)com_7 / (double)plan_7 * 100;
//            jsonArrayComRate_num.add( ComRate_7);
//
//            InitDataSend.bufferData.put("3", ComRateTest);
//
//            //Jone 序列化
//            String mapComRateTest = JSON.toJSONString(ComRateTest); //
//            webSocketServer.sendInfo(mapComRateTest);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 获取按时完成率情况
//     */
//    @Scheduled(cron = "*/40 * * * * ?")
//    private void ontimeNewComRate(){
//
//        try{
//
//            List<Product> product =  prodectService.findAllProduct();
//            c.setTime(dateCurr);
//            int weekdayPlan = c.get(Calendar.DAY_OF_WEEK);
//
//            // 按时完成率
//            Map<String, Object> ontimeComRateTest = new HashMap<String, Object>(new LinkedHashMap());
//
//            JSONArray jsonArrayPlanRateCurrNum = new JSONArray();
//
//            ontimeComRateTest.put("type", 4);
//            ontimeComRateTest.put("data", jsonArrayPlanRateCurrNum);
//
//            for(Product productlist: product){
//                // 获取表格中的时间
//                Date tableDate = productlist.getDate();
//                Calendar tablec = Calendar.getInstance();
//                tablec.setTime(tableDate);
//                int tableweek = tablec.get(Calendar.DAY_OF_WEEK);
//
//                // 获取当前时间 dateCurr，首先判断这两个时间是否在一周中
//
//                if((tableweek <= weekdayPlan)  &&  (c.get(Calendar.WEEK_OF_YEAR) == tablec.get(Calendar.WEEK_OF_YEAR))){
//
//                    int plan = productlist.getPlanNum();
//
//                    int order = productlist.getOrderNum();
//                    double OrderRate = (double)order / (double)plan * 100;
//                    // 此时处理时间  -1
//                    // jsonNewPlanRateCurrNum.put(String.format("week=%d", tableweek-1), OrderRate);
//                    jsonArrayPlanRateCurrNum.add(OrderRate);
//
//                }
//            }
//
//
//            InitDataSend.bufferData.put("4", ontimeComRateTest);
//            // Json 序列化
//            String mapontimeComRateTest = JSON.toJSONString(ontimeComRateTest); //
//            webSocketServer.sendInfo( mapontimeComRateTest);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     维修信息
//     */
//    @Scheduled(cron = "0/40 * * * * ?")
//    private void getNewWeekRepair(){
//
//        c.setTime(dateCurr);
//        int weekday = c.get(Calendar.DAY_OF_WEEK);
//        List<Product> product =  prodectService.findAllProduct();
//
//        try{
//
//            Map<String, Object> NewWeekRepairTest = new HashMap<String, Object>(new LinkedHashMap());
//            // JSONObject jsonNewCurrentRepairNum = new JSONObject(new LinkedHashMap());
//
//            JSONArray jsonArrayCurrentRepairNum = new JSONArray();
//            NewWeekRepairTest.put("type", 5);
//            NewWeekRepairTest.put("data", jsonArrayCurrentRepairNum);
//
//            for(Product productRepairList: product){
//                Date repairDate = productRepairList.getDate();
//                Calendar repairc = Calendar.getInstance();
//                repairc.setTime(repairDate);
//                int repairweek = repairc.get(Calendar.DAY_OF_WEEK);
//                // 进行判断条件设置
//                if((repairweek <= weekday)  &&  (c.get(Calendar.WEEK_OF_YEAR) == repairc.get(Calendar.WEEK_OF_YEAR))) {
//                    // jsonNewCurrentRepairNum.put(String.format("week=%d", repairweek-1), productRepairList.getRepairNum());
//                    jsonArrayCurrentRepairNum.add(productRepairList.getRepairNum());
//                }
//
//            }
//
//            InitDataSend.bufferData.put("5", NewWeekRepairTest);
//            // Json 序列化
//            String mapNewWeekRepairTest = JSON.toJSONString(NewWeekRepairTest); //
//            webSocketServer.sendInfo(mapNewWeekRepairTest);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 备料执行情况
//     */
//    @Scheduled(cron = "0/60 * * * * ?")
//    private void getOrder(){
//        try{
//            /**
//             * 备料执行情况
//             */
//
//            List<Order> order_test =  prodectService.findAllOrder();
//
//            Map<String, Object> orderTest = new HashMap<String, Object>(new LinkedHashMap());
//
//            JSONArray jsonAOrderEvent = new JSONArray();
//
//            orderTest.put("type", 6);
//            orderTest.put("data", jsonAOrderEvent);
//
//            Order id_1 = order_test.get(0);
//            Order id_2 = order_test.get(1);
//            Order id_3 = order_test.get(2);
//            Order id_4 = order_test.get(3);
//            jsonAOrderEvent.add(id_1);
//            jsonAOrderEvent.add(id_2);
//            jsonAOrderEvent.add(id_3);
//            jsonAOrderEvent.add(id_4);
//
//            InitDataSend.bufferData.put("6", orderTest);
//            // Json 序列化
//            String mapOrder = JSON.toJSONString(orderTest); //
//            webSocketServer.sendInfo(mapOrder);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     *  今日维修不良情况
//     */
//    @Scheduled(cron = "0/70 * * * * ?")
//    private void getWelding(){
//
//        try{
//
//            /**
//             * 今日维修不良情况
//             */
//            List<Welding> welding_test =  prodectService.findAllWelding();
//            Map<String, Object>  testWelding = new HashMap<String, Object>(new LinkedHashMap());
//
//            JSONArray  jsonArrayWedingEvent = new JSONArray();
//
//            testWelding.put("type", 7);
//            testWelding.put("data", jsonArrayWedingEvent);
//
//            Welding w_id_1 = welding_test.get(0);
//            Welding w_id_2 = welding_test.get(1);
//            Welding w_id_3 = welding_test.get(2);
//            Welding w_id_4 = welding_test.get(3);
//            Welding w_id_5 = welding_test.get(4);
//
//            jsonArrayWedingEvent.add( w_id_1);
//            jsonArrayWedingEvent.add( w_id_2);
//            jsonArrayWedingEvent.add( w_id_3);
//            jsonArrayWedingEvent.add( w_id_4);
//            jsonArrayWedingEvent.add( w_id_5);
//
//
//            InitDataSend.bufferData.put("7", testWelding);
//            // Json 序列化
//            String mapWelding = JSON.toJSONString(testWelding); //
//            webSocketServer.sendInfo(mapWelding);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
//
//}
//
//
