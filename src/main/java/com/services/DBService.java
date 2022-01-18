package com.services;

import java.sql.*;

public class DBService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/slice2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "***";

    private static Connection getConnectionToPostgresql() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
        return connection;
    }

    public void getCustomers(String productName) {
        String s = "SELECT customer_name FROM customers JOIN customer_product ON customers.id = customer_product.customer_id  " +
                "JOIN products ON products.id = customer_product.product_id WHERE products.product_name = ?";
        try (
                Connection connection = getConnectionToPostgresql();
                PreparedStatement ps = connection.prepareStatement(s)
        ) {
            ps.setString(1, productName);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("customer_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllProductsByCustomers() {
        String s = "SELECT products.product_name, customers.customer_name  FROM products JOIN customer_product " +
                "ON products.id = customer_product.product_id JOIN customers ON customers.id = customer_product.customer_id";
        try (
                Connection connection = getConnectionToPostgresql();
                PreparedStatement ps = connection.prepareStatement(s)
        ) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("product_name") + " " + resultSet.getString("customer_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductDescriptionByNameAndCustomer(String description , String productName, String customerName) {
        String s = "UPDATE products SET product_description = ? FROM customer_product JOIN customers " +
                "ON customer_product.customer_id = customers.id WHERE products.product_name = ? AND customers.customer_name = ?";
        try (
                Connection connection = getConnectionToPostgresql();
                PreparedStatement ps = connection.prepareStatement(s)
        ) {
            ps.setString(1, description);
            ps.setString(2, productName);
            ps.setString(3, customerName);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
