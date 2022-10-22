package com.ricemerchant.ricemerchantapp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ricemerchant.ricemerchantapp.Entity.PurchaseHistoryEntity;

@Repository
public interface PurchaseHistoryRepository extends CrudRepository<PurchaseHistoryEntity, Integer>{}
