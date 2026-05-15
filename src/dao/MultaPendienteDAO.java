package dao;

import conexion.Conexion;
import modelos.MultaPendiente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Acceso a datos para multas pendientes expuestas por {@code vista_multas_pendientes}.
 */
public class MultaPendienteDAO {

    private static final String SQL_LISTAR = "SELECT * FROM vista_multas_pendientes";

    /**
     * Consulta todas las multas pendientes.
     *
     * @return lista de multas (puede estar vacía)
     * @throws SQLException si ocurre un error de base de datos
     */
    public ArrayList<MultaPendiente> listarTodos() throws SQLException {
        ArrayList<MultaPendiente> lista = new ArrayList<>();
        try (
                Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR);
                ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                MultaPendiente fila = new MultaPendiente();
                fila.setEstudiante(leerString(rs, md, "estudiante", "nombre_estudiante", "alumno"));
                fila.setLibro(leerString(rs, md, "libro", "titulo_libro", "titulo"));
                fila.setMonto(leerDouble(rs, md, "monto", "importe", "valor"));
                Timestamp fecha = leerTimestamp(rs, md, "fecha_generacion", "fechaGeneracion", "fecha");
                fila.setFechaGeneracionDesdeSql(fecha);
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

    private static double leerDouble(ResultSet rs, ResultSetMetaData md, String... nombres) throws SQLException {
        int idx = indiceColumna(md, nombres);
        if (idx < 0) {
            return 0.0;
        }
        return rs.getDouble(idx);
    }

    private static Timestamp leerTimestamp(ResultSet rs, ResultSetMetaData md, String... nombres) throws SQLException {
        int idx = indiceColumna(md, nombres);
        if (idx < 0) {
            return null;
        }
        return rs.getTimestamp(idx);
    }

}
