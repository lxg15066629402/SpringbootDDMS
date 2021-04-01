package com.example.sqlserver_websocket.service;

import com.example.sqlserver_websocket.dao.ProductDao;
import com.example.sqlserver_websocket.entity.Order;
import com.example.sqlserver_websocket.entity.Product;
import com.example.sqlserver_websocket.entity.Welding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jone
 * @function ProductService 业务逻辑层
 * @date: 2020/12/14
 */

@Service
public class ProdectService {

    @Autowired
    private ProductDao productDao;

    public List<Product> findAllProduct() {
        return productDao.findAllProduct();
    }

    public List<Order> findAllOrder() {
        return productDao.findAllOrder();
    }

    public List<Welding> findAllWelding() {
        return productDao.findAllWelding();
    }


    public List<Integer> SelectById(){
        return productDao.SelectById();
    }

    public List<Product> findById(Integer id){
        return productDao.findById(id);
    }

    public List<Map<String, Object>> SelectPartWelding(){
        return productDao.SelectPartWelding();
    }

    public List<Product> SelectProductDate(Date productDate){
        return productDao.SelectProductDate(productDate);
    }

    public List<Product>  SelectWeekProductDate(Date productDate){
        return productDao.SelectWeekProductDate(productDate);
    }


}
