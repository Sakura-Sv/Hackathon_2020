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

    @Select("SELECT id, sname FROM region WHERE level = 1")
    List<HashMap<String, String>> getProvince();

    @Select("SELECT id, sname FROM region WHERE level=2 AND pid=#{provinceId}")
    List<HashMap<String, String>> getCityByPid(@Param("provinceId") String provinceId);

    @Select("SELECT mername FROM region WHERE id=#{cityId}")
    String getCityById(@Param("cityId") String cityId);
}
