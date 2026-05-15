package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión JDBC a la base de datos MySQL del sistema de biblioteca.
 * <p>
 * Configuración por defecto apunta al esquema {@code biblioteca} en localhost.
 * </p>
 */
public final class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "253858";//cambias la tuya aqui

    private Conexion() {
        // Clase de utilidad: no instanciable.
    }

    /**
     * Obtiene una nueva conexión a la base de datos.
     *
     * @return conexión JDBC abierta
     * @throws SQLException si no se puede establecer la conexión
     */
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
