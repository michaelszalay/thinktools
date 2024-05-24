package com.szalay.thinktools.io.db;

import com.szalay.thinktools.model.DataModel;
import com.szalay.thinktools.model.DataProperty;
import com.szalay.thinktools.model.Individual;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;

public class DataModelIOImpl implements DataModelDBIO {

    @Override
    public Set<String> getJDBCDriverClasses(String directory, Consumer<String> listener) {
        Objects.requireNonNull(directory);

        final Set<URI> jars;
        try {
            jars = getJars(directory, listener);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final Set<String> result = new HashSet<>();
        for (final URI jar : jars) {
            try (final URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{ jar.toURL() })) {
                final InputStream resource = urlClassLoader.getResourceAsStream("META-INF/services/java.sql.Driver");
                if (resource == null) {
                    continue;
                }

                final InputStreamReader inputStreamReader = new InputStreamReader(resource);
                final BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line.trim());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public DataModel loadDataModelFromQuery(String directory, DBSettings settings, String query, Map<String, String> columnFactoryMapping) {
        Objects.requireNonNull(settings);
        Objects.requireNonNull(query);

        try {
            final Driver driver = loadDriver(directory, settings.getDriverClassName());
            if (driver == null) {
                throw new IllegalArgumentException("Driver not found: " + settings.getDriverClassName());
            }

            final Properties properties = new Properties();
            properties.put("user", settings.getUsername());
            properties.put("password", settings.getPassword());

            final DataModel model = new DataModel();

            try (
                    final Connection connection = driver.connect(settings.getUrl(), properties);
                    final Statement statement = connection.createStatement();
                    final ResultSet resultSet = statement.executeQuery(query)
            ) {

                while (resultSet.next()) {

                    final Individual i = new Individual();

                    for (final Map.Entry<String, String> key : columnFactoryMapping.entrySet()) {

                        final Object object = resultSet.getObject(key.getKey());
                        final String factorName = key.getValue();

                        final DataProperty property = new DataProperty(factorName, (Serializable) object);
                        i.add(property);
                    }

                    if (!i.isEmpty()) {
                        model.add(i);
                    }
                }
            }

            return model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getColumnNames(String directory, DBSettings settings, String query) {
        Objects.requireNonNull(settings);
        Objects.requireNonNull(query);

        try {
            final Driver driver = loadDriver(directory, settings.getDriverClassName());
            if (driver == null) {
                throw new IllegalArgumentException("Driver not found: " + settings.getDriverClassName());
            }

            final Properties properties = new Properties();
            properties.put("user", settings.getUsername());
            properties.put("password", settings.getPassword());

            final Set<String> columns = new HashSet<>();

            try (
                    final Connection connection = driver.connect(settings.getUrl(), properties);
                    final Statement statement = connection.createStatement();
                    final ResultSet resultSet = statement.executeQuery(query)
            ) {

                final ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columns.add(metaData.getColumnName(i));
                }
            }

            return columns;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Driver loadDriver(String directory, String name) throws Exception {
        final Set<URI> jars = getJars(directory, s -> {

        });
        for (final URI jar : jars) {
            try (final URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{ jar.toURL()})) {
                final InputStream resource = urlClassLoader.getResourceAsStream("META-INF/services/java.sql.Driver");
                if (resource == null) {
                    continue;
                }

                final InputStreamReader inputStreamReader = new InputStreamReader(resource);
                final BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(name)) {
                        final Class<?> driverClass = urlClassLoader.loadClass(name);
                        return (Driver) driverClass.getDeclaredConstructor().newInstance();
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private Set<URI> getJars(String directory, Consumer<String> listener) throws Exception {
        final File dir = new File(directory);
        if (!dir.exists()) {
            throw new IllegalArgumentException("Directory does not exist");
        }

        if (!dir.canRead()) {
            throw new IllegalArgumentException("Directory is not readable.");
        }

        final Set<URI> result = new HashSet<>();
        Files.walkFileTree(dir.toPath(), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().endsWith("jar")) {
                    listener.accept(file.toString());
                    result.add(file.toUri());
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

        return result;
    }
}
