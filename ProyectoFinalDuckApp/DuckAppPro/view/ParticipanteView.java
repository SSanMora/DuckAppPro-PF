package DuckAppPro.view;

import javax.swing.*;

public class ParticipanteView {
    // EXACTAMENTE como en PetCareCenterPlus - todos públicos
    public JPanel mainPanel;
    public JComboBox comboCategoria;
    public JTextField txtNombre;
    public JTextField txtEdad;
    public JTextField txtDocumento;
    public JTextField txtNumeroPato;
    public JTextField txtBuscar;
    public JTabbedPane tabbedPane2;
    public JTabbedPane tabbedPane1;
    public JButton agregarButton;
    public JButton limpiarButton;
    public JButton eliminarButton;
    public JButton buscarButton;
    public JTable tableParticipantes;
    public JButton volverAlMenúButton;
    public JTabbedPane tabbedPane3;

    // Constructor
    public ParticipanteView() {
        // Podemos inicializar algunos valores aquí
        // Por ejemplo, cargar las categorías en el ComboBox
        comboCategoria.addItem("NIÑO");
        comboCategoria.addItem("JOVEN");
        comboCategoria.addItem("ADULTO");

        // Limpiar campos iniciales
        limpiarCampos();
    }

    // SOLO el getter del mainPanel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Este es el metodo auxiliar para limpiar campos
    public void limpiarCampos() {
        txtNombre.setText("");
        txtEdad.setText("");
        txtDocumento.setText("");
        txtNumeroPato.setText("");
        comboCategoria.setSelectedIndex(0);
        txtBuscar.setText("");
    }

    // Usamos este metodo para obtener los datos del formulario
    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getEdad() {
        return txtEdad.getText().trim();
    }

    public String getDocumento() {
        return txtDocumento.getText().trim();
    }

    public String getNumeroPato() {
        return txtNumeroPato.getText().trim();
    }

    public String getCategoria() {
        return comboCategoria.getSelectedItem().toString();
    }
}
