package com.yrnet.viewweb.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.generator.entity.Column;
import com.yrnet.viewweb.generator.entity.Table;

import java.util.List;

/**
 * @author dengbp
 */
public interface IGeneratorService {

    List<String> getDatabases(String databaseType);

    IPage<Table> getTables(String tableName, QueryRequestPage request, String databaseType, String schemaName);

    List<Column> getColumns(String databaseType, String schemaName, String tableName);
}
