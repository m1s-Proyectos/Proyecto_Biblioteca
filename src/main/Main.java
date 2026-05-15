package main;

import vistas.FrmMenuPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Punto de arranque de la aplicación de reportes de biblioteca.
 */
public final class Main {

    private Main() {
    }

    /**
     * Inicializa el aspecto visual y abre el menú principal.
     *
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        aplicarLookAndFeelModerno();
        SwingUtilities.invokeLater(() -> {
            FrmMenuPrincipal menu = new FrmMenuPrincipal();
            menu.setVisible(true);
        });
    }

    private static void aplicarLookAndFeelModerno() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException
                    | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
                // Si ningún L&F está disponible, Swing usará el predeterminado.
            }
        }
    }
}
