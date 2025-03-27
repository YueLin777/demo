package com.mind.contract.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
/**
 * @author ：long
 * @date ：Created in 2024/8/15 0015 15:34
 * @description：拦截器配置
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 登录拦截器
         * */
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/contract/exportContract")
                .excludePathPatterns("/download/*")
                .excludePathPatterns("/contract/exportInvoice")
                .excludePathPatterns("/downloadInvoice/*/*")
                .excludePathPatterns("/contract/exportStatement")
                .excludePathPatterns("/contract/exportMaterial")
                .excludePathPatterns("/downloadSell/*")
                .excludePathPatterns("/contract/exportSell")
                .excludePathPatterns("/contract/exportSellInvoice")
                .excludePathPatterns("/downloadSellInvoice/*")
                .excludePathPatterns("/contract/exportAcceptance")
                .excludePathPatterns("/contract/exportDelivery")
                .excludePathPatterns("/contract/uploadContractExcel")
                .excludePathPatterns("/contract/uploadInvoiceExcel")
                .excludePathPatterns("/contract/uploadMaterialExcel")
                .excludePathPatterns("/contract/uploadStatementExcel")
                .excludePathPatterns("/contract/uploadSellExcel")
                .excludePathPatterns("/contract/uploadSellInvoiceExcel")
                .excludePathPatterns("/contract/uploadDeliveryExcel")
                .excludePathPatterns("/contract/uploadAcceptanceExcel")
                .order(1);
    }
}
