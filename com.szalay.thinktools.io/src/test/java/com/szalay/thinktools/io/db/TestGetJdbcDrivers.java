package com.szalay.thinktools.io.db;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class TestGetJdbcDrivers {

    @Test
    public void testGetJdbcDrivers() {

        final DataModelDBIO io = new DataModelIOImpl();
        final Set<String> jdbcDriverClasses = io.getJDBCDriverClasses("src/test/resources", s -> {});

        Assert.assertNotNull(jdbcDriverClasses);
        Assert.assertEquals(1, jdbcDriverClasses.size());
        Assert.assertTrue(jdbcDriverClasses.contains("org.postgresql.Driver"));
    }
}
