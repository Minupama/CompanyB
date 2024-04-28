package com.example.CompanyB.InventoryStocksModule.Service;

import com.example.CompanyB.InventoryStocksModule.Repository.OrderDao;
import com.example.CompanyB.InventoryStocksModule.Repository.StockDao;
import com.example.CompanyB.InventoryStocksModule.Repository.SupplierDao;
import com.example.CompanyB.InventoryStocksModule.Model.OrderDetail;
import com.example.CompanyB.InventoryStocksModule.Model.stock1;
import com.example.CompanyB.InventoryStocksModule.Model.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    StockDao stockDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    SupplierDao supplierDao;

    /*public List<stock1> getAllStock() {
        return stockDao.findAll();*/
    public List<stock1> getAllStock() {
            return stockDao.findAllWithDetails(); 
    }
    
    public List<supplier> getAllSupplier() {
        return supplierDao.findAllWithDetails(); 
}

    public  stock1 addStock(stock1 stock, String supplierId) throws Exception {
        supplier existingSupplier = supplierDao.findById(supplierId).get();
        if (existingSupplier == null) {
            throw new Exception("Supplier Not found");
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        stock.setCreatedDateTime(formattedDateTime);
        stock.setSuppliername(existingSupplier.getSuppliername());
        stock1 addedStock = stockDao.save(stock);
        return addedStock;
    }

    public stock1 updateStockUnits(String id, Integer units) throws Exception {
        stock1 existingStock = stockDao.findById(id).get();
        int newUnits = units;
        if (existingStock == null) {
            throw new Exception("stock not found");
        }
        existingStock.setUnits(existingStock.getUnits() + newUnits);
         // Add new units to current units
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        existingStock.setUpdatedDateTime(formattedDateTime); 
        stock1 updatedstock=stockDao.save(existingStock);
        return updatedstock;
    
    }

    public OrderDetail addOrder(OrderDetail orderDetail) {
        OrderDetail newOrder = orderDao.save(orderDetail);
        return newOrder;
}}
