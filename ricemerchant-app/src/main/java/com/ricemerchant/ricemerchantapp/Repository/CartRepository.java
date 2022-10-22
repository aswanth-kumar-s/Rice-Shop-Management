package com.ricemerchant.ricemerchantapp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ricemerchant.ricemerchantapp.Entity.CartEntity;

@Repository
public interface CartRepository extends CrudRepository<CartEntity, Integer> {}
