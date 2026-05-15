package vistas;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Menú principal desde el cual se accede a los distintos reportes de la biblioteca.
 */
public class FrmMenuPrincipal extends JFrame {

    /**
     * Crea el marco principal con accesos directos a cada reporte.
     */
    public FrmMenuPrincipal() {
        setTitle("Biblioteca — Sistema de reportes");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(520, 380));
        setSize(640, 440);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Reportes de biblioteca", JLabel.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        titulo.setBorder(BorderFactory.createEmptyBorder(18, 12, 8, 12));

        JPanel centro = new JPanel(new GridLayout(5, 1, 12, 12));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(8, 48, 8, 48));

        JButton btnPrestamos = new JButton("Préstamos Activos");
        JButton btnMultas = new JButton("Multas Pendientes");
        JButton btnLibros = new JButton("Libros Más Prestados");
        JButton btnEjemplares = new JButton("Reporte Ejemplares");
        JButton btnSalir = new JButton("Salir");

        estiloBotonMenu(btnPrestamos);
        estiloBotonMenu(btnMultas);
        estiloBotonMenu(btnLibros);
        estiloBotonMenu(btnEjemplares);
        estiloBotonMenu(btnSalir);

        btnPrestamos.addActionListener(e -> new FrmPrestamosActivos().setVisible(true));
        btnMultas.addActionListener(e -> new FrmMultasPendientes().setVisible(true));
        btnLibros.addActionListener(e -> new FrmLibrosMasPrestados().setVisible(true));
        btnEjemplares.addActionListener(e -> new FrmReporteEjemplares().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));

        centro.add(btnPrestamos);
        centro.add(btnMultas);
        centro.add(btnLibros);
        centro.add(btnEjemplares);
        centro.add(btnSalir);

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sur.setOpaque(false);
        JLabel pie = new JLabel("BAD115 · JDBC + DAO + Swing", JLabel.CENTER);
        pie.setForeground(new Color(90, 90, 90));
        sur.add(pie);

        JPanel raiz = new JPanel(new BorderLayout(10, 10));
        raiz.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));
        raiz.setBackground(new Color(245, 247, 250));
        raiz.add(titulo, BorderLayout.NORTH);
        raiz.add(centro, BorderLayout.CENTER);
        raiz.add(sur, BorderLayout.SOUTH);

        setContentPane(raiz);
    }

    private static void estiloBotonMenu(JButton boton) {
        boton.setFont(boton.getFont().deriveFont(Font.PLAIN, 16f));
        boton.setFocusPainted(false);
    }
}
