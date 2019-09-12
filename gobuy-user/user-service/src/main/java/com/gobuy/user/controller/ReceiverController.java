package com.gobuy.user.controller;


import com.gobuy.user.pojo.Receiver;
import com.gobuy.user.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * 收件人地址管理
 * */
@Controller
@RequestMapping("address")
public class ReceiverController {

    @Autowired
    private ReceiverService receiverService;


    @GetMapping
    public ResponseEntity<List<Receiver>> queryAddress() {
        List<Receiver> list = receiverService.queryAddress();
        if (list == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Boolean> addAddress(@RequestBody Receiver receiver) {
        Boolean bool = receiverService.addAddress(receiver);
        if (bool == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bool);
    }

    @PutMapping
    public ResponseEntity<Boolean> editAddress(@RequestBody Receiver receiver) {
        Boolean bool = receiverService.editAddress(receiver);
        if (bool == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bool);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteAddress(@PathVariable("id") Integer id) {
        Boolean bool = receiverService.deleteAddress(id);
        if (bool == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bool);
    }


}// end
