package com.ricemerchant.ricemerchantapp.Repository;

import org.springframework.data.repository.CrudRepository;

import com.ricemerchant.ricemerchantapp.Entity.StockEntity;

public interface StockRepository extends CrudRepository<StockEntity, Integer> {}
