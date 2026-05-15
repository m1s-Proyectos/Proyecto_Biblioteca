package dao;

import conexion.Conexion;
import modelos.PrestamoActivo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Acceso a datos para el reporte de préstamos activos mediante la vista
 * {@code vista_prestamos_activos}.
 */
public class PrestamoActivoDAO {

    private static final String SQL_LISTAR = "SELECT * FROM vista_prestamos_activos";

    /**
     * Consulta todos los préstamos activos expuestos por la vista.
     *
     * @return lista de préstamos (puede estar vacía)
     * @throws SQLException si ocurre un error de base de datos
     */
    public ArrayList<PrestamoActivo> listarTodos() throws SQLException {
        ArrayList<PrestamoActivo> lista = new ArrayList<>();
        try (
                Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR);
                ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                PrestamoActivo fila = new PrestamoActivo();
                fila.setIdPrestamo(leerInt(rs, md, "id_prestamo", "idPrestamo", "idprestamo"));
                fila.setEstudiante(leerString(rs, md, "estudiante", "nombre_estudiante", "alumno"));
                fila.setLibro(leerString(rs, md, "libro", "titulo_libro", "titulo"));
                fila.setBibliotecario(leerString(rs, md, "bibliotecario", "nombre_bibliotecario"));
                Timestamp fecha = leerTimestamp(rs, md, "fecha_prestamo", "fechaPrestamo", "fecha");
                fila.setFechaPrestamoDesdeSql(fecha);
                fila.setEstado(leerString(rs, md, "estado", "estado_prestamo"));
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

    private static Timestamp leerTimestamp(ResultSet rs, ResultSetMetaData md, String... nombres) throws SQLException {
        int idx = indiceColumna(md, nombres);
        if (idx < 0) {
            return null;
        }
        return rs.getTimestamp(idx);
    }

}
