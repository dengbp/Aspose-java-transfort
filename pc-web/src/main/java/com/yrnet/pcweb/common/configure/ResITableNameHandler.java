package com.yrnet.pcweb.common.configure;

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
        return "license_resource";//LicenseUtil.getCurrentUser().getTableName();
    }
}
