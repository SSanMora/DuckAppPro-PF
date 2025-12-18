package DuckAppPro.view;

import javax.swing.*;

public class SimulacionView {
    // Todos públicos
    public JPanel mainPanel;
    public JButton iniciarSimulacionButton;
    public JButton pausarButton;
    public JButton reiniciarButton;
    public JPanel panelPista;
    public JLabel lblTiempo;
    public JLabel lblGanador;
    public JTextArea textAreaPodio;
    public JButton guardarResultadosButton;
    public JLabel lblEstadoSimulacion;
    public JTabbedPane tabbedPane2;
    public JComboBox comboCarreras;
    public JTabbedPane tabbedPane3;
    public JTabbedPane tabbedPane4;
    public JTabbedPane tabbedPane5;
    public JButton volverAlMenúButton;

    // Constructor
    public SimulacionView() {
        // Inicializar estado de los componentes
        lblEstadoSimulacion.setText("Selecciona una carrera para comenzar");
        lblGanador.setText("---");
        lblTiempo.setText("---");
        textAreaPodio.setText("1° ---\n2° ---\n3° ---");

        panelPista.setPreferredSize(new java.awt.Dimension(300, 300));
        panelPista.setMinimumSize(new java.awt.Dimension(300, 300));
        panelPista.setSize(300, 300);

        // Aqui configuramos el área del podio
        textAreaPodio.setEditable(false); // Solo lectura como en el ejemplo

        // Deshabilitar botones inicialmente
        iniciarSimulacionButton.setEnabled(false);
        pausarButton.setEnabled(false);
        reiniciarButton.setEnabled(false);
        guardarResultadosButton.setEnabled(false);

        // Configurar panel de pista (se personalizará después)
        panelPista.setLayout(null); // Layout absoluto para animación
    }

    // SOLO el getter del mainPanel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Métodos para actualizar la interfaz
    public void actualizarPodio(String podioTexto) {
        textAreaPodio.setText(podioTexto);
    }

    public void actualizarGanador(String ganador) {
        lblGanador.setText(ganador);
    }

    public void actualizarTiempo(String tiempo) {
        lblTiempo.setText(tiempo);
    }

    public void actualizarEstado(String estado) {
        lblEstadoSimulacion.setText(estado);
    }

    // Metodos para controlar estado de botones
    public void setControlesHabilitados(boolean habilitado) {
        iniciarSimulacionButton.setEnabled(habilitado);
        pausarButton.setEnabled(false); // Pausar empieza deshabilitado
        reiniciarButton.setEnabled(habilitado);
        guardarResultadosButton.setEnabled(false); // Solo después de carrera
    }

    // Este metodo es para habilitar guardar resultados (cuando termina carrera)
    public void setGuardarResultadosHabilitado(boolean habilitado) {
        guardarResultadosButton.setEnabled(habilitado);
    }

    // Metodo para limpiar la simulación
    public void limpiarSimulacion() {
        lblGanador.setText("---");
        lblTiempo.setText("---");
        textAreaPodio.setText("1° ---\n2° ---\n3° ---");
        panelPista.removeAll(); // Limpiar patos de la pista
        panelPista.repaint(); // Redibujar el panel
    }
}