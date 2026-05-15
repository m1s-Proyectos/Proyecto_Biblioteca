package utilidades;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * Aplica un estilo visual consistente a las tablas de reportes.
 */
public final class TableStyle {

    /** Altura de fila recomendada para tablas densas. */
    public static final int ALTURA_FILA = 28;

    /** Ancho preferido por defecto para columnas numéricas cortas. */
    public static final int ANCHO_COLUMNA_CORTA = 90;

    /** Ancho preferido para columnas de texto medio. */
    public static final int ANCHO_COLUMNA_MEDIA = 200;

    private TableStyle() {
    }

    /**
     * Configura altura de filas, encabezado y alineación centrada de celdas.
     *
     * @param tabla tabla a estilizar
     */
    public static void aplicar(JTable tabla) {
        tabla.setRowHeight(ALTURA_FILA);
        tabla.setShowGrid(true);
        tabla.setFillsViewportHeight(true);
        JTableHeader header = tabla.getTableHeader();
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setDefaultRenderer(Object.class, centrado);

        DefaultTableCellRenderer encabezado = new DefaultTableCellRenderer();
        encabezado.setHorizontalAlignment(SwingConstants.CENTER);
        header.setDefaultRenderer(encabezado);
    }

    /**
     * Asigna anchos preferidos a columnas concretas (índices visuales empezando en 0).
     *
     * @param tabla         tabla destino
     * @param anchosPixeles ancho preferido por columna, en el mismo orden visual
     */
    public static void aplicarAnchosColumnas(JTable tabla, int... anchosPixeles) {
        if (tabla == null || anchosPixeles == null || anchosPixeles.length == 0) {
            return;
        }
        int columnas = tabla.getColumnModel().getColumnCount();
        int limite = Math.min(columnas, anchosPixeles.length);
        for (int i = 0; i < limite; i++) {
            TableColumn columna = tabla.getColumnModel().getColumn(i);
            int ancho = Math.max(30, anchosPixeles[i]);
            columna.setPreferredWidth(ancho);
        }
    }
}
