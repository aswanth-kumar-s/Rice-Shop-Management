package com.ricemerchant.ricemerchantapp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ricemerchant.ricemerchantapp.Entity.BrandEntity;

@Repository
public interface BrandRepository extends CrudRepository<BrandEntity, Integer>{}
