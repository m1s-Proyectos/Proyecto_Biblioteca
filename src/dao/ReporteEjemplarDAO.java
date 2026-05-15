package dao;

import conexion.Conexion;
import modelos.ReporteEjemplar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Acceso a datos del reporte de ejemplares físicos desde {@code vista_reporte_ejemplares}.
 */
public class ReporteEjemplarDAO {

    private static final String SQL_LISTAR = "SELECT * FROM vista_reporte_ejemplares";

    /**
     * Obtiene el inventario de ejemplares con métricas agregadas de préstamo.
     *
     * @return lista de ejemplares
     * @throws SQLException si ocurre un error de base de datos
     */
    public ArrayList<ReporteEjemplar> listarTodos() throws SQLException {
        ArrayList<ReporteEjemplar> lista = new ArrayList<>();
        try (
                Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR);
                ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                ReporteEjemplar fila = new ReporteEjemplar();
                fila.setIdEjemplar(leerInt(rs, md, "id_ejemplar", "idEjemplar", "idejemplar"));
                fila.setTitulo(leerString(rs, md, "titulo", "titulo_libro"));
                fila.setAutor(leerString(rs, md, "autor", "nombre_autor"));
                fila.setIsbn(leerString(rs, md, "isbn", "ISBN"));
                fila.setCodigoBarra(leerString(rs, md, "codigo_barra", "codigoBarra", "codigo", "barcode"));
                fila.setUbicacion(leerString(rs, md, "ubicacion", "estante", "ubicacion_fisica"));
                fila.setEstado(leerString(rs, md, "estado", "estado_ejemplar"));
                fila.setTotalPrestamos(leerInt(rs, md, "total_prestamos", "totalPrestamos", "prestamos_totales"));
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
