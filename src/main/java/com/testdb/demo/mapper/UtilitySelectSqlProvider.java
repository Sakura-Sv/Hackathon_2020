package com.testdb.demo.mapper;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class UtilitySelectSqlProvider implements ProviderMethodResolver {
    public static String selectByParam(final String tableName,final String name){
        return new SQL(){{
            SELECT("*");
            FROM("#{tableName}");
            WHERE("name = #{name}");
        }}.toString();
    }
}
