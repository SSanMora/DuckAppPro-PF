package DuckAppPro.view;

import javax.swing.*;

public class MainView {
    // todos públicos
    public JPanel mainPanel;
    public JButton gestionDeParticipantesButton;
    public JButton gestionDeCarrerasButton;
    public JButton simulacionDeCarreraButton;
    public JButton estadisticasButton;
    public JLabel lblEstado;
    public JLabel lblVersion;
    public JLabel lblDesarrolladores;
    public JLabel lblTitulo;
    public JTabbedPane tabbedPane1;
    public JButton salirButton;

    // Constructor
    public MainView() {
        // Inicializar textos
        lblEstado.setText("Sistema listo");
        lblVersion.setText("v1.0");
        lblDesarrolladores.setText("Desarrollado por: Santiago y Laura");
        lblTitulo.setText("DUCKAPP PRO - SISTEMA DE CARRERAS DE PATOS");
    }

    // SOLO el getter del mainPanel
    public JPanel getMainPanel() {
        return mainPanel;
    }
}