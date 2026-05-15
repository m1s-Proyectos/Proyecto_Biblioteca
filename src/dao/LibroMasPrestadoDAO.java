package dao;

import conexion.Conexion;
import modelos.LibroMasPrestado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Acceso a datos para los libros más prestados según {@code vista_libros_mas_prestados}.
 */
public class LibroMasPrestadoDAO {

    private static final String SQL_LISTAR = "SELECT * FROM vista_libros_mas_prestados";

    /**
     * Consulta los libros más prestados según la vista definida en MySQL.
     *
     * @return lista de títulos con su total de préstamos
     * @throws SQLException si ocurre un error de base de datos
     */
    public ArrayList<LibroMasPrestado> listarTodos() throws SQLException {
        ArrayList<LibroMasPrestado> lista = new ArrayList<>();
        try (
                Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR);
                ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                LibroMasPrestado fila = new LibroMasPrestado();
                fila.setTitulo(leerString(rs, md, "titulo", "titulo_libro", "nombre_libro"));
                fila.setCantidadPrestamos(leerInt(rs, md, "cantidad_prestamos", "cantidadPrestamos", "total_prestamos", "prestamos"));
                lista.add(fila);
            }
        }
        return lista;
    }

    private static int indiceColumna(ResultSetMetaData md, String... nombres) throws SQLException {
        int cols = md.getColumnCount();
        for (String nombre : nombres) {
            for (int i = 1; i <= cols; i++) {
                String etiqueta = md.getColumnLabel(i);
                String real = md.getColumnName(i);
                if (nombre.equalsIgnoreCase(etiqueta) || nombre.equalsIgnoreCase(real)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String leerString(ResultSet rs, ResultSetMetaData md, String... nombres) throws SQLException {
        int idx = indiceColumna(md, nombres);
        if (idx < 0) {
            return null;
        }
        return rs.getString(idx);
    }

    private static int leerInt(ResultSet rs, ResultSetMetaData md, String... nombres) throws SQLException {
        int idx = indiceColumna(md, nombres);
        if (idx < 0) {
            return 0;
        }
        return rs.getInt(idx);
    }

}
