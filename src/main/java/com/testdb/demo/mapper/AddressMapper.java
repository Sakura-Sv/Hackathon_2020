package com.testdb.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface AddressMapper {

    @Select("SELECT cname FROM country")
    List<String> getCountry();

    @Select("SELECT id, sname FROM region where level = 1")
    List<HashMap<String, String>> getProvince();

    @Select("SELECT pid, sname FROM region where level=2 AND pid=#{provinceId}")
    List<HashMap<String, String>> getCity(@Param("proId") String provinceId);
}
