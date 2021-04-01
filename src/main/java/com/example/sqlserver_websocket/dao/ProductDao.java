package com.example.sqlserver_websocket.dao;

import com.example.sqlserver_websocket.entity.Order;
import com.example.sqlserver_websocket.entity.Product;
import com.example.sqlserver_websocket.entity.Welding;
import com.example.sqlserver_websocket.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jone
 * @function ProductDao
 * @date: 2020/12/14
 */
@Repository
public class ProductDao {

    @Autowired
    private ProductMapper productMapper;


    // 查找表中的所有数据信息
    public List<Product> findAllProduct(){
        return productMapper.findAllProduct();
    }

    public List<Order> findAllOrder(){
        return productMapper.findAllOrder();
    }

    public List<Welding> findAllWelding(){
        return productMapper.findAllWelding();
    }

    // 查找表中的某列数据信息
    public List<Integer> SelectById(){
        return productMapper.SelectById();
    }

    //
    public List<Product> findById(Integer id){
        return productMapper.findById(id);
    }

    public List<Map<String, Object>> SelectPartWelding(){
        return productMapper.SelectPartWelding();
    }

    public List<Product> SelectProductDate(Date productDate){
        return productMapper.SelectProductDate(productDate);
    }

    public List<Product>  SelectWeekProductDate(Date productDate){
        return productMapper.SelectWeekProductDate(productDate);
    }


}
