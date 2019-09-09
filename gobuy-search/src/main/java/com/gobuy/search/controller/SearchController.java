package com.gobuy.search.controller;


import com.gobuy.common.pojo.PageResult;
import com.gobuy.search.pojo.Goods;
import com.gobuy.search.pojo.SearchRequest;
import com.gobuy.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class SearchController {

    @Autowired
    private SearchService searchService;


    @PostMapping
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request) {
        PageResult<Goods> result = searchService.search(request);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

}// end
