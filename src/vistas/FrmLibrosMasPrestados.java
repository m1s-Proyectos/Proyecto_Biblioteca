package vistas;

import dao.LibroMasPrestadoDAO;
import modelos.LibroMasPrestado;
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
 * Ventana de reporte para los libros más prestados (vista {@code vista_libros_mas_prestados}).
 */
public class FrmLibrosMasPrestados extends JFrame {

    private final LibroMasPrestadoDAO dao;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    /**
     * Construye la interfaz y realiza la carga inicial de datos.
     */
    public FrmLibrosMasPrestados() {
        this.dao = new LibroMasPrestadoDAO();
        setTitle("Biblioteca — Libros más prestados");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(880, 520));
        setLocationRelativeTo(null);

        JLabel lblTituloReporte = new JLabel("Reporte: libros más prestados", SwingConstants.CENTER);
        lblTituloReporte.setFont(lblTituloReporte.getFont().deriveFont(Font.BOLD, 20f));

        String[] columnas = {"Título", "Cantidad de préstamos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        TableStyle.aplicar(tabla);
        TableStyle.aplicarAnchosColumnas(tabla, 520, 200);

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
        SwingWorker<List<LibroMasPrestado>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<LibroMasPrestado> doInBackground() throws Exception {
                return dao.listarTodos();
            }

            @Override
            protected void done() {
                try {
                    List<LibroMasPrestado> datos = get();
                    modeloTabla.setRowCount(0);
                    for (LibroMasPrestado fila : datos) {
                        modeloTabla.addRow(new Object[] {
                                textoSeguro(fila.getTitulo()),
                                fila.getCantidadPrestamos()
                        });
                    }
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(
                            FrmLibrosMasPrestados.this,
                            "Error al consultar libros más prestados:\n" + error.getMessage(),
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
