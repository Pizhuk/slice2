import com.services.DBService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBServiceTest {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/slice2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "***";
    private static final String REQUEST_GET = "SELECT customer_name FROM customers JOIN customer_product ON customers.id = customer_product.customer_id  " +
            "JOIN products ON products.id = customer_product.product_id WHERE products.product_name = ?";
    private static final String REQUEST_GET_ALL = "SELECT products.product_name, customers.customer_name  FROM products JOIN customer_product " +
            "ON products.id = customer_product.product_id JOIN customers ON customers.id = customer_product.customer_id";
    private static final String REQUEST_UPDATE = "UPDATE products SET product_description = ? FROM customer_product JOIN customers " +
            "ON customer_product.customer_id = customers.id WHERE products.product_name = ? AND customers.customer_name = ?";

    private final Connection connection = Mockito.mock(Connection.class);
    private final PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    private final ResultSet resultSet = Mockito.mock(ResultSet.class);


    private final DBService cut = new DBService();

    @Test
    void getCustomersTest() {
        try (MockedStatic<DriverManager> mockDriveManager = Mockito.mockStatic(DriverManager.class)) {
            mockDriveManager.when(() -> DriverManager.getConnection(DB_URL, USER, PASSWORD)).thenReturn(connection);
            Mockito.when(connection.prepareStatement(REQUEST_GET)).thenReturn(preparedStatement);
            Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

            cut.getCustomers("Book");

            Mockito.verify(preparedStatement, Mockito.times(1)).executeQuery();
            Mockito.verify(resultSet, Mockito.times(1)).getString("customer_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllProductsByCustomersTest() {
        try (MockedStatic<DriverManager> mockDriveManager = Mockito.mockStatic(DriverManager.class)) {
            mockDriveManager.when(() -> DriverManager.getConnection(DB_URL, USER, PASSWORD)).thenReturn(connection);
            Mockito.when(connection.prepareStatement(REQUEST_GET_ALL)).thenReturn(preparedStatement);
            Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

            cut.getAllProductsByCustomers();

            Mockito.verify(preparedStatement, Mockito.times(1)).executeQuery();
            Mockito.verify(resultSet, Mockito.times(1)).getString("product_name");
            Mockito.verify(resultSet, Mockito.times(1)).getString("customer_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateProductDescriptionByNameAndCustomerTest() {
        try (MockedStatic<DriverManager> mockDriveManager = Mockito.mockStatic(DriverManager.class)) {
            mockDriveManager.when(() -> DriverManager.getConnection(DB_URL, USER, PASSWORD)).thenReturn(connection);
            Mockito.when(connection.prepareStatement(REQUEST_UPDATE)).thenReturn(preparedStatement);
            Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
            Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);

            cut.updateProductDescriptionByNameAndCustomer("BBBB", "Book", "Bill");

            Mockito.verify(preparedStatement, Mockito.times(1)).execute();
            Mockito.verify(preparedStatement, Mockito.times(1)).setString(1, "BBBB");
            Mockito.verify(preparedStatement, Mockito.times(1)).setString(2, "Book");
            Mockito.verify(preparedStatement, Mockito.times(1)).setString(3, "Bill");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
