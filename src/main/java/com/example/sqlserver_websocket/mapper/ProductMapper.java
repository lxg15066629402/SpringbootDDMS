package com.example.sqlserver_websocket.mapper;


import com.example.sqlserver_websocket.entity.Order;
import com.example.sqlserver_websocket.entity.Product;
import com.example.sqlserver_websocket.entity.Welding;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Jone
 * @function ProductMapper
 * @date: 2020/12/14
 */
@Mapper
public interface ProductMapper {

    // 查找表中所有信息
    public List<Product> findAllProduct();

    public List<Order> findAllOrder();

    public List<Welding> findAllWelding();

    // 根据id 进行查询
    public List<Product> findById(Integer id);

    // 查询repair_num进行查询
    public List<Integer> SelectById();

    // 查询部分数据
    public List<Map<String, Object>> SelectPartWelding();

    // 查询当天时间
    public List<Product> SelectProductDate(Date productDate);

    // 查询一周时间数据
    public List<Product>  SelectWeekProductDate(Date productDate);
}
