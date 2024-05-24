package com.szalay.thinktools.io.db;

import com.szalay.thinktools.model.DataModel;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface DataModelDBIO {

    Set<String> getJDBCDriverClasses(String directory, Consumer<String> listener);

    DataModel loadDataModelFromQuery(String directory, DBSettings settings, String query, Map<String, String> columnFactoryMapping);

    Set<String> getColumnNames(String directory, DBSettings settings, String query);
}
