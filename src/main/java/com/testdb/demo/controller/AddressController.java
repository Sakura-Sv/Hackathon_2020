package com.testdb.demo.controller;

import com.testdb.demo.mapper.AddressMapper;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @GetMapping("/province")
    public Result<List<HashMap<String, String>>> getProvince(){
        return Result.success(am.getProvince());
    }

    @GetMapping("/city")
    public Result<List<HashMap<String, String>>> getCity(@RequestParam("pid") String provinceId ){
        return Result.success(am.getCity(provinceId));
    }

}
