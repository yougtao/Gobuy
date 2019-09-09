package com.gobuy.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
public class Goods {
    @Id
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;    // 卖点
    private Integer brandId;       // 品牌id
    private Integer cid1;      // 1级分类id
    private Integer cid2;      // 2级分类id
    private Integer cid3;      // 3级分类id
    private Timestamp createTime;   // 创建时间
    private List<Integer> price;       // 价格

    @Field(type = FieldType.Keyword, index = false)
    private String skus;                    // sku信息的json结构
    private Map<String, Object> specs;      // 可搜索的规格参数，key是参数名，值是参数值


    public Goods() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCid1() {
        return cid1;
    }

    public void setCid1(Integer cid1) {
        this.cid1 = cid1;
    }

    public Integer getCid2() {
        return cid2;
    }

    public void setCid2(Integer cid2) {
        this.cid2 = cid2;
    }

    public Integer getCid3() {
        return cid3;
    }

    public void setCid3(Integer cid3) {
        this.cid3 = cid3;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<Integer> getPrice() {
        return price;
    }

    public void setPrice(List<Integer> price) {
        this.price = price;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public Map<String, Object> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, Object> specs) {
        this.specs = specs;
    }


}
