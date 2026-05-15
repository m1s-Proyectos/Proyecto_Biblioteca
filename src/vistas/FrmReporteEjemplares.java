package vistas;

import dao.ReporteEjemplarDAO;
import modelos.ReporteEjemplar;
import utilidades.TableStyle;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

/**
 * Ventana de reporte detallado de ejemplares físicos (vista {@code vista_reporte_ejemplares}).
 */
public class FrmReporteEjemplares extends JFrame {

    private final ReporteEjemplarDAO dao;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    /**
     * Construye la interfaz y ejecuta la consulta inicial.
     */
    public FrmReporteEjemplares() {
        this.dao = new ReporteEjemplarDAO();
        setTitle("Biblioteca — Reporte de ejemplares");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(1180, 560));
        setLocationRelativeTo(null);

        JLabel lblTituloReporte = new JLabel("Reporte: ejemplares e historial de préstamos", SwingConstants.CENTER);
        lblTituloReporte.setFont(lblTituloReporte.getFont().deriveFont(Font.BOLD, 20f));

        String[] columnas = {
                "ID ejemplar", "Título", "Autor", "ISBN", "Código barras", "Ubicación", "Estado", "Total préstamos"
        };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        TableStyle.aplicar(tabla);
        TableStyle.aplicarAnchosColumnas(tabla, 90, 220, 160, 120, 140, 140, 110, 110);

        JScrollPane scrollPane = new JScrollPane(tabla);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatosAsincrono());

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> dispose());

        JPanel panelRaiz = new JPanel();
        panelRaiz.setBackground(new Color(245, 247, 250));
        GroupLayout layout = new GroupLayout(panelRaiz);
        panelRaiz.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblTituloReporte)
                .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar)
                        .addComponent(btnRegresar)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(lblTituloReporte)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnActualizar)
                        .addComponent(btnRegresar)));

        panelRaiz.setBorder(BorderFactory.createEmptyBorder(10, 14, 14, 14));
        setContentPane(panelRaiz);
        cargarDatosAsincrono();
    }

    private void cargarDatosAsincrono() {
        SwingWorker<List<ReporteEjemplar>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ReporteEjemplar> doInBackground() throws Exception {
                return dao.listarTodos();
            }

            @Override
            protected void done() {
                try {
                    List<ReporteEjemplar> datos = get();
                    modeloTabla.setRowCount(0);
                    for (ReporteEjemplar fila : datos) {
                        modeloTabla.addRow(new Object[] {
                                fila.getIdEjemplar(),
                                textoSeguro(fila.getTitulo()),
                                textoSeguro(fila.getAutor()),
                                textoSeguro(fila.getIsbn()),
                                textoSeguro(fila.getCodigoBarra()),
                                textoSeguro(fila.getUbicacion()),
                                textoSeguro(fila.getEstado()),
                                fila.getTotalPrestamos()
                        });
                    }
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(
                            FrmReporteEjemplares.this,
                            "Error al consultar ejemplares:\n" + error.getMessage(),
                            "Error JDBC",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private static String textoSeguro(String valor) {
        return valor == null ? "" : valor;
    }
}
