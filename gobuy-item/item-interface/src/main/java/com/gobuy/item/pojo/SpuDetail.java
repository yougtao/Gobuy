package com.gobuy.item.pojo;


import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "spu_detail")
public class SpuDetail {
    @Id
    private Integer spuId;
    private String description; // 描述
    private String specTemplate;

    private String specifications;  // 商品的全局属性规格
    private String packingList;     // 包装清单
    private String afterService;    // 售后服务


    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecTemplate() {
        return specTemplate;
    }

    public void setSpecTemplate(String specTemplate) {
        this.specTemplate = specTemplate;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }
}
