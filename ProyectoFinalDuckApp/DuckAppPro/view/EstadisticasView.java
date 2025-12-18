package DuckAppPro.view;

import javax.swing.*;

public class EstadisticasView {
    // Todos públicos
    public JPanel mainPanel;
    public JLabel lblMasPodio;
    public JLabel lblPatoMasRapido;
    public JLabel lblTotalCarreras;
    public JTable tableEstadisticas;
    public JButton exportarEnTxtButton;
    public JButton actualizarButton;
    public JButton exportacionButton;
    public JTabbedPane tabbedPane1;
    public JButton volverAlMenúButton;

    // Constructor
    public EstadisticasView() {
        // Inicializar valores por defecto - usando los nombres exactos
        lblMasPodio.setText("---");
        lblPatoMasRapido.setText("---");
        lblTotalCarreras.setText("0");

        // Configurar la tabla como no editable (solo lectura)
        tableEstadisticas.setEnabled(false);

        // Inicializar el botón de exportación es)
        if (exportacionButton != null) {
            // Si es un JButton, deshabilitar inicialmente
            exportacionButton.setEnabled(true);
        }
    }

    // SOLO el getter del mainPanel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Métodos para actualizar estadísticas
    public void actualizarMasPodio(String participante) {
        lblMasPodio.setText(participante);
    }

    public void actualizarPatoMasRapido(String patoInfo) {
        lblPatoMasRapido.setText(patoInfo);
    }

    public void actualizarTotalCanneras(int total) {
        lblTotalCarreras.setText(String.valueOf(total));
    }

    // Con este metodo limpiamos estadísticas
    public void limpiarEstadisticas() {
        lblMasPodio.setText("---");
        lblPatoMasRapido.setText("---");
        lblTotalCarreras.setText("0");
    }

    // Metodo para actualizar la tabla de estadísticas
    public void actualizarTablaEstadisticas(Object[][] datos, String[] columnas) {
        // Esto se implementará en el controller
    }
}