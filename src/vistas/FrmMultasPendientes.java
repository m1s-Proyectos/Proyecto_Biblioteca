package vistas;

import dao.MultaPendienteDAO;
import modelos.MultaPendiente;
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
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Ventana de reporte para multas pendientes (vista {@code vista_multas_pendientes}).
 */
public class FrmMultasPendientes extends JFrame {

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final NumberFormat MONEDA = NumberFormat.getCurrencyInstance(new Locale("es", "SV"));

    private final MultaPendienteDAO dao;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    /**
     * Construye la interfaz y carga el contenido inicial.
     */
    public FrmMultasPendientes() {
        this.dao = new MultaPendienteDAO();
        setTitle("Biblioteca — Multas pendientes");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(920, 520));
        setLocationRelativeTo(null);

        JLabel lblTituloReporte = new JLabel("Reporte: multas pendientes", SwingConstants.CENTER);
        lblTituloReporte.setFont(lblTituloReporte.getFont().deriveFont(Font.BOLD, 20f));

        String[] columnas = {"Estudiante", "Libro", "Monto", "Fecha generación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        TableStyle.aplicar(tabla);
        TableStyle.aplicarAnchosColumnas(tabla, 200, 280, 120, 160);

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
        SwingWorker<List<MultaPendiente>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<MultaPendiente> doInBackground() throws Exception {
                return dao.listarTodos();
            }

            @Override
            protected void done() {
                try {
                    List<MultaPendiente> datos = get();
                    modeloTabla.setRowCount(0);
                    for (MultaPendiente fila : datos) {
                        String fechaTexto = fila.getFechaGeneracion() == null
                                ? "-"
                                : FMT_FECHA.format(fila.getFechaGeneracion());
                        modeloTabla.addRow(new Object[] {
                                textoSeguro(fila.getEstudiante()),
                                textoSeguro(fila.getLibro()),
                                MONEDA.format(fila.getMonto()),
                                fechaTexto
                        });
                    }
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(
                            FrmMultasPendientes.this,
                            "Error al consultar multas pendientes:\n" + error.getMessage(),
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
