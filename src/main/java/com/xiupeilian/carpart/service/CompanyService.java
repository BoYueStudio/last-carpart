package com.xiupeilian.carpart.service;


import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.vo.CompanyUploadVo;

public interface CompanyService {

    Company findSalebusinessById(Integer companyId);

    void updateCompany(Company company);

    void uploadCompanyPictureById(CompanyUploadVo vo);
}
