package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.model.Prime;

import java.util.List;

public interface BrandService {
    List<Brand> findBrandAll();

    List<Parts> findPartsAll();

    List<Prime> findPrimeAll();

    Prime findPrimeById(String primeId);

    Parts findPartsById(String partsId);

    Brand findBrandById(String brandId);

}
