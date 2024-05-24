package com.szalay.thinktools.io.db;

import com.szalay.thinktools.model.DataModel;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestGetColumns {

    @Test
    @Ignore
    public void testGetColumns() {

        final DataModelDBIO dbio = new DataModelIOImpl();

        final DBSettings settings = new DBSettings();
        settings.setUrl("jdbc:postgresql://localhost/hiks");
        settings.setDriverClassName("org.postgresql.Driver");
        settings.setPassword("");
        settings.setUsername("root");

        Set<String> columnNames = dbio.getColumnNames("src/test/resources", settings, "select lat, lng from waypoint");

        Assert.assertNotNull(columnNames);
    }

    @Test
    @Ignore
    public void getDataModel() {
        final DataModelDBIO dbio = new DataModelIOImpl();

        final DBSettings settings = new DBSettings();
        settings.setUrl("jdbc:postgresql://localhost/hiks");
        settings.setDriverClassName("org.postgresql.Driver");
        settings.setPassword("");
        settings.setUsername("root");

        final Map<String, String> mapping = new HashMap<>();
        mapping.put("lat", "f1");
        mapping.put("lng", "f2");

        DataModel individuals = dbio.loadDataModelFromQuery("src/test/resources", settings, "select lat, lng from waypoint", mapping);

        Assert.assertNotNull(individuals);
    }
}
