package com.xiupeilian.carpart.service.impl;


import com.xiupeilian.carpart.mapper.CompanyMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.vo.CompanyUploadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Override
    public Company findSalebusinessById(Integer companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
    }

    @Override
    public void updateCompany(Company company) {
        companyMapper.updateByPrimaryKeySelective(company);
    }
    @Override
    public void uploadCompanyPictureById(CompanyUploadVo vo) {
        if (vo.getPicture().equals("1")){
            System.err.println("1");
            System.err.println(vo);
            companyMapper.uploadCompanyPictureById1(vo);
        }else if(vo.getPicture().equals("2")){

            System.err.println("2");
            System.err.println(vo);
            companyMapper.uploadCompanyPictureById2(vo);
        }else {

            System.err.println("3");
            System.err.println(vo);
            companyMapper.uploadCompanyPictureById3(vo);
        }
    }
}
