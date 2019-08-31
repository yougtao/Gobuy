package com.gobuy.common.pojo;

import java.util.List;

public class PageResult<T> {
    private Integer total;
    private Integer totalpage;
    private List<T> items;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
