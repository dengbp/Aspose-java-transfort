package com.yrnet.appweb.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yrnet.appweb.common.entity.QueryRequestPage;
import com.yrnet.appweb.generator.entity.Column;
import com.yrnet.appweb.generator.entity.Table;

import java.util.List;

/**
 * @author dengbp
 */
public interface IGeneratorService {

    List<String> getDatabases(String databaseType);

    IPage<Table> getTables(String tableName, QueryRequestPage request, String databaseType, String schemaName);

    List<Column> getColumns(String databaseType, String schemaName, String tableName);
}
