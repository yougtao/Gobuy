package com.gobuy.search.pojo;

import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.Brand;
import com.gobuy.item.pojo.Category;

import java.util.List;

public class SearchResult extends PageResult<Goods> {
    private List<Category> categories;

    private List<Brand> brands;

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categories, List<Brand> brands) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
