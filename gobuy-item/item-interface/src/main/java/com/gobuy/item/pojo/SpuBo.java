package com.gobuy.item.pojo;

import java.util.List;

public class SpuBo extends Spu {
    private String cname;
    private String bname;

    private List<Sku> skuList;
    private SpuDetail spuDetail;


    public SpuBo() {
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public List<Sku> getSkus() {
        return skuList;
    }

    public void setSkus(List<Sku> skus) {
        this.skuList = skus;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }
}
