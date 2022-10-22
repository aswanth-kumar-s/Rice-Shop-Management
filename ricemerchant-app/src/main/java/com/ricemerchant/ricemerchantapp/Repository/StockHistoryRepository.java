package com.ricemerchant.ricemerchantapp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ricemerchant.ricemerchantapp.Entity.StockHistoryEntity;

@Repository
public interface StockHistoryRepository extends CrudRepository<StockHistoryEntity, Integer> {}
