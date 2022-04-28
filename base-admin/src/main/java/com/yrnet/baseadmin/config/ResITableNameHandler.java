package com.yrnet.baseadmin.config;

import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @author dengbp
 * @ClassName ResITableNameHandler
 * @Description TODO
 * @date 2020-05-28 20:08
 */
public class ResITableNameHandler implements ITableNameHandler {

    @Override
    public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
        return "tmp";//LicenseUtil.getCurrentUser().getTableName();
    }
}
