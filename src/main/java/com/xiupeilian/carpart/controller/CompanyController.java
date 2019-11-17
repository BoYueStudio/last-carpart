package com.xiupeilian.carpart.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.*;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.vo.CompanyUploadVo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BrandService brandService;

    @RequestMapping("/companyManage")
    public String toEditInfo(HttpServletRequest request) {

        SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
        Company company=companyService.findSalebusinessById(user.getCompanyId());
        if (!(company.getMain().equals("-1"))) {//有主营的产品信息需要去数据库查询
            //如果有数据的话只能是多种品牌
            Brand main = brandService.findBrandById(company.getMain());
            request.setAttribute("main",main);
        }
        if (!(company.getSingleBrand().equals("-1"))) {
            //单一品牌
            Brand brand = brandService.findBrandById(company.getSingleBrand());
            request.setAttribute("brand",brand);
        }
        if (!(company.getSingleParts().equals("-1"))) {
            //单项配件
            Parts parts = brandService.findPartsById(company.getSingleParts());
            request.setAttribute("parts",parts);
        }
        if (!(company.getPrime().equals("-1"))) {
            //精品专卖
            Prime prime = brandService.findPrimeById(company.getPrime());
            request.setAttribute("prime",prime);
        }

        request.setAttribute("company",company);

        return "company/salebusinessEdit";
    }

    //修改企业宣传
    @RequestMapping("/updatecompany")
    public  String updateCompany(Company company){
        companyService.updateCompany(company);
        return "redirect:companyManage";
    }

    @RequestMapping("toCompanyUpload")
    public String toCompanyUpload(CompanyUploadVo vo, HttpServletRequest request){
        request.setAttribute("picture",vo.getPicture());
        request.setAttribute("id",vo.getId());
        return "public/index";
    }
    @RequestMapping("/uploadCompany")
    public void uploadCompany(CompanyUploadVo vo,HttpServletResponse response)throws  Exception{
        System.out.println("76543213454");
        companyService.uploadCompanyPictureById(vo);
        response.getWriter().write("1");
    }

    @RequestMapping(value = "photoupload", method = {RequestMethod.POST, RequestMethod.GET})
    public void myphotoupload(HttpServletRequest request,  MultipartFile file, HttpServletResponse response) throws IOException {

        String fileName=file.getOriginalFilename();//获取文件名称
        String suffixName=fileName.substring(fileName.lastIndexOf("."));//取上传文件的后缀
        String newName=UUID.randomUUID().toString()+suffixName;//随机生成文件名

        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI8J3o3KISsBPC";
        String accessKeySecret = "ENNyadCg7HJMObqQYHiJHozY6I7lhq";
        String bucketName = "2019boyue";//筒的名称
        String objectName = newName+suffixName;//上传的文件
        String url="";
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
            // 关闭OSSClient。
            ossClient.shutdown();
            //设置URL过期时间为10年  3600l* 1000*24*365*10
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            // 生成URL
            url=ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
        } catch (IOException  e) {
            e.printStackTrace();
            //上传失败
        }

        response.getWriter().write(url);

    }



}
