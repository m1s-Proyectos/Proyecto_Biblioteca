package vistas;

import dao.PrestamoActivoDAO;
import modelos.PrestamoActivo;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana de reporte para préstamos activos (vista {@code vista_prestamos_activos}).
 */
public class FrmPrestamosActivos extends JFrame {

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final PrestamoActivoDAO dao;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    /**
     * Construye la interfaz y dispara la carga inicial de datos.
     */
    public FrmPrestamosActivos() {
        this.dao = new PrestamoActivoDAO();
        setTitle("Biblioteca — Préstamos activos");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(1020, 540));
        setLocationRelativeTo(null);

        JLabel lblTituloReporte = new JLabel("Reporte: préstamos activos", SwingConstants.CENTER);
        lblTituloReporte.setFont(lblTituloReporte.getFont().deriveFont(Font.BOLD, 20f));

        String[] columnas = {"ID", "Estudiante", "Libro", "Bibliotecario", "Fecha préstamo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        TableStyle.aplicar(tabla);
        TableStyle.aplicarAnchosColumnas(tabla, 70, 170, 240, 170, 160, 120);

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
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnActualizar)
                        .addComponent(btnRegresar)));

        panelRaiz.setBorder(BorderFactory.createEmptyBorder(10, 14, 14, 14));
        setContentPane(panelRaiz);
        cargarDatosAsincrono();
    }

    private void cargarDatosAsincrono() {
        SwingWorker<List<PrestamoActivo>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<PrestamoActivo> doInBackground() throws Exception {
                return dao.listarTodos();
            }

            @Override
            protected void done() {
                try {
                    List<PrestamoActivo> datos = get();
                    modeloTabla.setRowCount(0);
                    for (PrestamoActivo fila : datos) {
                        String fechaTexto = fila.getFechaPrestamo() == null
                                ? "-"
                                : FMT_FECHA.format(fila.getFechaPrestamo());
                        modeloTabla.addRow(new Object[] {
                                fila.getIdPrestamo(),
                                textoSeguro(fila.getEstudiante()),
                                textoSeguro(fila.getLibro()),
                                textoSeguro(fila.getBibliotecario()),
                                fechaTexto,
                                textoSeguro(fila.getEstado())
                        });
                    }
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(
                            FrmPrestamosActivos.this,
                            "Error al consultar préstamos activos:\n" + error.getMessage(),
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
