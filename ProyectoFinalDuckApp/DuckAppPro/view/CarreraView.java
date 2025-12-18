package DuckAppPro.view;

import javax.swing.*;

public class CarreraView {
    public JPanel mainPanel;
    public JButton crearCarreraButton;
    public JButton limpiarDatosButton;
    public JTextField txtNombreCarrera;
    public JTextField txtFecha;
    public JComboBox comboCategoria;
    public JTable tableParticipantesDisponibles;
    public JButton asignarParticipantesButton;
    public JTable tableCarreras;
    public JButton eliminarCarreraButton;
    public JButton iniciarCarreraButton;
    public JTabbedPane tabbedPane1;
    public JTabbedPane tabbedPane2;
    public JTabbedPane tabbedPane3;
    public JTabbedPane tabbedPane4;
    public JButton volverAlMenúButton;
    public JButton filtrarParticipantesButton;

    // Constructor
    public CarreraView() {
        // Inicializar categorías en el ComboBox
        comboCategoria.addItem("NIÑO");
        comboCategoria.addItem("JOVEN");
        comboCategoria.addItem("ADULTO");

        // Establecer fecha actual
        txtFecha.setText(java.time.LocalDate.now().toString());

        // Limpiar campos iniciales
        limpiarCampos();
    }

    // SOLO el getter del mainPanel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Metodo para limpiar campos
    public void limpiarCampos() {
        txtNombreCarrera.setText("");
        comboCategoria.setSelectedIndex(0);
        // La fecha se mantiene con la actual
    }

    // Metodos para obtener datos del formulario
    public String getNombreCarrera() {
        return txtNombreCarrera.getText().trim();
    }

    public String getCategoria() {
        return comboCategoria.getSelectedItem().toString();
    }

    public String getFecha() {
        return txtFecha.getText().trim();
    }

    // Metodo para actualizar la fecha si llega ha ser necesario
    public void setFecha(String fecha) {
        txtFecha.setText(fecha);
    }
}