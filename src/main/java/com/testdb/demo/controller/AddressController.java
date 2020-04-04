package com.testdb.demo.controller;

import com.testdb.demo.mapper.AddressMapper;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@AjaxResponseBody
@Slf4j
public class AddressController {

    @Autowired
    AddressMapper am;

    @GetMapping("/country")
    public Result<List<String>> getCountry(){
        return Result.success(am.getCountry());
    }

}
