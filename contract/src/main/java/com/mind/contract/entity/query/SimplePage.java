package com.mind.contract.entity.query;

import com.mind.contract.entity.enums.PageSize;
import lombok.Getter;
import lombok.ToString;

/**
 * ClassName：SimplePage
 *
 * @author:l
 * @Date: 2024/7/16
 * @Description:
 * @version: 1.0
 */
@Getter
@ToString
public class SimplePage {
    //当前页
    private int pageNo;
    //总记录数
    private int countTotal;
    //每页显示数量
    private int pageSize;
    //总页数
    private int pageTotal;
    //当前页的起始索引
    private int start;

    public SimplePage() {
    }

    public SimplePage(int start) {
        this.start = start;
    }

    public SimplePage(Integer pageNo, int countTotal, int pageSize) {
        if (pageNo == null) {
            pageNo = 0;
        }
        this.pageNo = pageNo;
        this.countTotal = countTotal;
        this.pageSize = pageSize;
        action();
    }

    public void action() {
        if (this.pageSize <= 0){
            this.pageSize = PageSize.SIZE5.getSize();
        }

        //TODO 如果  总记录数 > 0, 计算总页数， 否则默认总页数为1
        if (this.countTotal > 0) {
            this.pageTotal = this.countTotal % this.pageSize == 0 ?
                    this.countTotal/this.pageSize : this.countTotal/this.pageSize + 1;
        }else {
            this.pageTotal = 1;
        }

        //TODO 如果 当前页 <= 1,默认是第 1 页
        if (pageNo <= 1) {
            pageNo = 1;
        }

        //TODO 如果当前页 大于 总页数， 让当前页= 最后一页
        if (pageNo > pageTotal) {
            pageNo = pageTotal;
        }

        //计算起始索引
        this.start = (pageNo - 1) * pageSize;
    }
}
