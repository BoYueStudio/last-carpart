package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.vo.CompanyUploadVo;

public interface CompanyMapper extends BaseMapper<Company> {

    Company findCompanyByName(String companyname);

    void uploadCompanyPictureById1(CompanyUploadVo vo);

    void uploadCompanyPictureById2(CompanyUploadVo vo);

    void uploadCompanyPictureById3(CompanyUploadVo vo);
}