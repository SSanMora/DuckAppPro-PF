package DuckAppPro.controller;

import DuckAppPro.model.Categoria;
import DuckAppPro.model.Participante;
import DuckAppPro.view.ParticipanteView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

// este controlador se encarga de la gestión de participantes
// conecta la vista de participantes con los modelos y el controlador principal
public class ParticipanteController
{
    // referencia a la vista de participantes
    private ParticipanteView view;

    // referencia al controlador principal para volver al menú
    private MainController mainController;

    // lista global de participantes registrados
    private List<Participante> listaParticipantes;

    // modelo de tabla para mostrar los participantes en la vista
    private DefaultTableModel modeloTabla;

    // constructor del controlador de participantes
    // recibe el controlador principal, la vista y la lista global de participantes
    public ParticipanteController(MainController mainController,
                                  ParticipanteView view,
                                  List<Participante> listaParticipantes)
    {
        // se guarda la referencia al controlador principal
        this.mainController = mainController;

        // se guarda la vista asociada a este controlador
        this.view = view;

        // se guarda la lista de participantes
        this.listaParticipantes = listaParticipantes;

        // se inicializa la tabla de participantes
        inicializarTabla();

        // se inicializan los listeners de los botones
        initListeners();

        // listener para volver al menú principal
        view.volverAlMenúButton.addActionListener(e -> mainController.mostrarMain());
    }

    // este método inicializa la tabla de participantes
    private void inicializarTabla()
    {
        // se definen las columnas de la tabla
        String[] columnas = {"Nombre", "Edad", "Documento", "Categoría", "Pato #"};

        // se crea el modelo de la tabla y se asigna a la vista
        modeloTabla = new DefaultTableModel(columnas, 0);
        view.tableParticipantes.setModel(modeloTabla);

        // se carga la información de la lista en la tabla
        actualizarTabla();
    }

    // este método conecta los botones de la vista con sus acciones
    private void initListeners()
    {
        // botón para agregar un nuevo participante
        view.agregarButton.addActionListener(e -> agregarParticipante());

        // botón para limpiar los campos del formulario
        view.limpiarButton.addActionListener(e -> view.limpiarCampos());

        // botón para buscar participantes por nombre o documento
        view.buscarButton.addActionListener(e -> buscarParticipante());

        // botón para eliminar un participante seleccionado
        view.eliminarButton.addActionListener(e -> eliminarParticipante());
    }

    // este método agrega un nuevo participante a la lista
    private void agregarParticipante()
    {
        // se obtienen los datos ingresados en la vista
        String nombre = view.getNombre();
        String edadStr = view.getEdad();
        String documento = view.getDocumento();
        String numeroPato = view.getNumeroPato();
        String categoriaStr = view.getCategoria();

        // validación de campos obligatorios
        if (nombre.isEmpty() || edadStr.isEmpty() || documento.isEmpty() || numeroPato.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        // conversión de edad a número
        int edad;
        try
        {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "La edad debe ser un número");
            return;
        }

        // verificación de documento único
        for (Participante p : listaParticipantes)
        {
            if (p.getDocumento().equals(documento))
            {
                JOptionPane.showMessageDialog(null, "El documento ya está registrado");
                return;
            }
        }

        // verificación de número de pato único
        for (Participante p : listaParticipantes)
        {
            if (p.getNumeroPato().equals(numeroPato))
            {
                JOptionPane.showMessageDialog(null, "Ese número de pato ya pertenece a otro participante");
                return;
            }
        }

        // se convierte el string de categoría al enum correspondiente
        Categoria categoria = Categoria.valueOf(categoriaStr);

        // se crea el participante y se agrega a la lista
        Participante participante = new Participante(nombre, edad, documento, categoria, numeroPato);
        listaParticipantes.add(participante);

        // mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Participante registrado con éxito");

        // se limpian los campos y se actualiza la tabla
        view.limpiarCampos();
        actualizarTabla();
    }

    // este método busca participantes por nombre o documento
    private void buscarParticipante()
    {
        // se obtiene el texto de búsqueda en minúsculas
        String buscar = view.txtBuscar.getText().trim().toLowerCase();

        // si no hay búsqueda se carga la tabla completa
        if (buscar.isEmpty())
        {
            actualizarTabla();
            return;
        }

        // se limpia la tabla antes de mostrar resultados
        modeloTabla.setRowCount(0);

        // se recorren los participantes y se agregan los que coinciden
        for (Participante p : listaParticipantes)
        {
            if (p.getNombre().toLowerCase().contains(buscar) ||
                    p.getDocumento().toLowerCase().contains(buscar))
            {

                modeloTabla.addRow(new Object[]
                        {
                            p.getNombre(),
                            p.getEdad(),
                            p.getDocumento(),
                            p.getCategoria(),
                            p.getNumeroPato()
                        });
            }
        }
    }

    // este método elimina el participante seleccionado en la tabla
    private void eliminarParticipante()
    {
        // se obtiene la fila seleccionada
        int fila = view.tableParticipantes.getSelectedRow();

        // validación para asegurar selección
        if (fila == -1)
        {
            JOptionPane.showMessageDialog(null, "Selecciona un participante a eliminar");
            return;
        }

        // se obtiene el documento del participante seleccionado
        String documento = modeloTabla.getValueAt(fila, 2).toString();

        // se elimina el participante de la lista
        listaParticipantes.removeIf(p -> p.getDocumento().equals(documento));

        // mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Participante eliminado");

        // se actualiza la tabla
        actualizarTabla();
    }

    // este método actualiza la tabla con todos los participantes de la lista
    private void actualizarTabla()
    {
        // se limpia la tabla antes de volver a llenarla
        modeloTabla.setRowCount(0);

        // se agregan todos los participantes a la tabla
        for (Participante p : listaParticipantes)
        {
            modeloTabla.addRow(new Object[]
            {
                p.getNombre(),
                p.getEdad(),
                p.getDocumento(),
                p.getCategoria(),
                p.getNumeroPato()
            });
        }
    }
}
