package com.gobuy.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.*;
import com.gobuy.search.client.BrandClient;
import com.gobuy.search.client.CategoryClient;
import com.gobuy.search.client.GoodsClient;
import com.gobuy.search.pojo.Goods;
import com.gobuy.search.pojo.SearchRequest;
import com.gobuy.search.pojo.SearchResult;
import com.gobuy.search.repository.GoodsRepository;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SearchService {
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper mapper = new ObjectMapper();


    // 根据spu构建goods
    public Goods buildGoods(Spu spu) throws IOException {
        Goods goods = new Goods();

        // 查询商品分类名称
        List<String> names = categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        // 品牌名称
        Brand brand = null;
        if (spu.getBrandId() != null)
            brand = brandClient.queryBrand(spu.getBrandId());

        // 查询sku
        List<Sku> skus = goodsClient.querySkuBySpuId(spu.getId());

        // 查询详情
        System.out.println(spu.getId());
        SpuDetail spuDetail = goodsClient.querySpuDetailById(spu.getId());

        // 查询规格参数
        //List<SpecParam> params = this.specificationClient.querySpecParam(null, spu.getCid3(), true, null);

        // 处理sku，仅封装id、标题title、价格price、图片image，并获得价格集合
        List<Integer> prices = new ArrayList<>();
        List<Map<String, Object>> skuDetailList = new ArrayList<>();
        skus.forEach(sku -> {
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("price", sku.getPrice());
            skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            skuDetailList.add(skuMap);
            prices.add(sku.getPrice());
        });

        // 处理规格参数
        new TypeReference<List<Map<String, Object>>>() {};
        Map<String, List<Map<String, Object>>> spuSpecs = mapper.readValue(spuDetail.getSpecifications(), new TypeReference<Map<String, List<Map<String, Object>>>>() {});
        // Map<String, List<Object>> specialSpecs = mapper.readValue(spuDetail.getSpecTemplate(), new TypeReference<Map<String, List<Object>>>() {});

        // 过滤规格模板，把所有可搜索的信息保存到Map中
        Map<String, Object> specMap = new HashMap<>();
        // 新的遍历
        for (Map.Entry<String, List<Map<String, Object>>> entry : spuSpecs.entrySet()) {
            // String groupName = entry.getKey();   // 暂时用不到
            List<Map<String, Object>> list = entry.getValue();
            list.forEach(spec -> {
                if (!(Boolean) spec.get("searchable"))
                    return;
                if ((Boolean) spec.get("global"))
                    specMap.put((String) spec.get("k"), spec.get("v"));
                else
                    specMap.put((String) spec.get("k"), spec.get("options"));
            });
        }

        // 设置数据
        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        // 同时查询
        goods.setAll(spu.getTitle() + " " + ((brand == null || brand.getName() == null) ? "" : brand.getName()) + " " + StringUtils.join(names, " "));

        goods.setPrice(prices);
        // 所有sku信息
        goods.setSkus(mapper.writeValueAsString(skuDetailList));
        goods.setSpecs(specMap);
        return goods;
    }

    // 搜索页面
    public PageResult<Goods> search(SearchRequest request) {
        String key = request.getKey();

        if (StringUtils.isBlank(key))
            return null;

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 对key进行全文检索
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));
        // 设置返回的结果字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));

        // 分页
        int page = request.getPage();
        int size = request.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        // 排序
        String sortBy = request.getSortBy();
        Boolean desc = request.getDescending();
        if (StringUtils.isNotBlank(sortBy))
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));

        // 聚合
        String categoryAggName = "category";
        String brandAggName = "brand";

        // 对商品分类聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        // 对品牌进行聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 查询, 获取聚合结果
        AggregatedPage<Goods> pageInfo = (AggregatedPage<Goods>) goodsRepository.search(queryBuilder.build());

        // 解析查询结果
        long total = pageInfo.getTotalElements();
        int totalPage = ((int) total + request.getSize() - 1) / request.getSize();
        // 获取商品分类和品牌的聚合结果
        List<Category> categories = getCategoryAggResult(pageInfo.getAggregation(categoryAggName));
        List<Brand> brands = getBrandAggResult(pageInfo.getAggregation(brandAggName));

        return new SearchResult(pageInfo.getTotalElements(), pageInfo.getTotalPages(), pageInfo.getContent(), categories, brands);
    }


    // 解析商品分类聚合结果
    private List<Category> getCategoryAggResult(Aggregation aggregation) {
        List<Category> categories = new ArrayList<>();
        LongTerms categoryAgg = (LongTerms) aggregation;
        List<Integer> cids = new ArrayList<>();

        for (LongTerms.Bucket bucket : categoryAgg.getBuckets()) {
            cids.add(bucket.getKeyAsNumber().intValue());
        }

        // 根据id查询分类名称
        List<String> names = categoryClient.queryNameByIds(cids);

        for (int i = 0; i < names.size(); i++) {
            Category c = new Category();
            c.setId(cids.get(i));
            c.setName(names.get(i));
            categories.add(c);
        }
        return categories;
    }

    // 解析品牌聚合结果
    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        //List<Brand> brands = new ArrayList<>();
        LongTerms brandAgg = (LongTerms) aggregation;
        List<Integer> bids = new ArrayList<>();

        for (LongTerms.Bucket bucket : brandAgg.getBuckets()) {
            bids.add(bucket.getKeyAsNumber().intValue());
        }

        return brandClient.queryBrandByIds(bids);
    }

}// end
