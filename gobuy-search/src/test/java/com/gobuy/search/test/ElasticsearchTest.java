package com.gobuy.search.test;

import com.gobuy.common.pojo.PageResult;
import com.gobuy.item.pojo.SpuBo;
import com.gobuy.search.client.GoodsClient;
import com.gobuy.search.pojo.Goods;
import com.gobuy.search.repository.GoodsRepository;
import com.gobuy.search.service.SearchService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest//(classes = GobuySearchApplication.class)
public class ElasticsearchTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsClient goodsClient;


    //@Test
    public void createIndex() {
        // 创建索引
        this.esTemplate.createIndex(Goods.class);
        // 配置映射
        this.esTemplate.putMapping(Goods.class);
    }


    /*
     * 搜索数据导入
     * */
    //@Test
    public void importData() {
        int page = 1;
        while (true) {
            PageResult<SpuBo> result = goodsClient.querySpuByPage(page++, 100, true, null);
            if (result == null || result.getItems().isEmpty())
                break;
            List<SpuBo> items = result.getItems();

            // 处理List<SpuBo> ==> List<Goods>
            List<Goods> goodsList = items.stream().map(spuBo -> {
                try {
                    return searchService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());

            // save to es索引库
            goodsRepository.saveAll(goodsList);
        }
    }

}// end
