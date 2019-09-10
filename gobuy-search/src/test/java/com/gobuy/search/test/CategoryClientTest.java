package com.gobuy.search.test;

import com.gobuy.search.GobuySearchApplication;
import com.gobuy.search.client.CategoryClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GobuySearchApplication.class)
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    //@Test
    public void testQueryCategories() {
        List<String> names = categoryClient.queryNameByIds(Arrays.asList(1, 2, 3));
        names.forEach(System.out::println);
    }

}// end