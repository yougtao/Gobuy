package com.gobuy.item.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gobuy.item.GobuyItemApplication;
import com.gobuy.item.mapper.SpuMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GobuyItemApplication.class)
public class Convert {

    @Autowired
    private SpuMapper spuMapper;

    private static ObjectMapper objectMapper = new ObjectMapper();

    //@Test
    public void convert() {
        List<Integer> ids = spuMapper.getIds();
        ids.forEach(id -> {
            try {
                go(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void go(Integer id) throws IOException {
        String old_spec = spuMapper.querySpecification(id);

        List<Map<String, Object>> specs = objectMapper.readValue(old_spec, new TypeReference<List<Map<String, Object>>>() {});

        Map<String, Object> new_spec = new LinkedHashMap<>();

        specs.forEach(go -> {
            String groupName = (String) go.get("group");
            Object params = go.get("params");

            new_spec.put(groupName, params);
        });

        // save
        String string = objectMapper.writeValueAsString(new_spec);
        System.out.println(string);
        spuMapper.saveSpecification(id, string);
    }


}// end
