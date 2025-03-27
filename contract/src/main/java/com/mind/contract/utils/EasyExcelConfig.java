package com.mind.contract.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * @author ：long
 * @date ：Created in 2024/8/26 0026 11:17
 * @description：
 */
@Component
@Scope("prototype")
public class EasyExcelConfig<T> extends AnalysisEventListener<T> {
    private List<T> data;

    //读取excel内容
    //从第二行开始读取（默认第一行是表头）把每行读取到的内容封装到t对象中
    @Override
    public void invoke(T o, AnalysisContext analysisContext) {
    //将读取到的数据放入list集合中
        data.add(o);
    }
    public void init(){
        data = new ArrayList<>();
    }
    //定义一个方法，数据list集合
    public List<T> getData(){
        return data;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
