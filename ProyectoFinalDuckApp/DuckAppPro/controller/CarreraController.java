package DuckAppPro.controller;

import DuckAppPro.model.Carrera;
import DuckAppPro.model.Categoria;
import DuckAppPro.model.EstadoCarrera;
import DuckAppPro.model.Participante;
import DuckAppPro.view.CarreraView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

// este controlador se encarga de toda la lógica relacionada con la gestión de carreras
// aquí se conectan la vista de carreras, los modelos y el controlador principal
public class CarreraController
{
    // referencia a la vista de gestión de carreras
    private CarreraView view;

    // referencia al controlador principal para poder volver al menú
    private MainController mainController;

    // lista global donde se almacenan todas las carreras creadas
    private List<Carrera> listaCarreras;

    // lista global con todos los participantes registrados
    private List<Participante> listaParticipantes;

    // modelo de tabla para mostrar participantes filtrados
    private DefaultTableModel modeloParticipantes;

    // modelo de tabla para mostrar las carreras creadas
    private DefaultTableModel modeloCarreras;

    // constructor del controlador de carreras
    // recibe el controlador principal, la vista y las listas globales
    public CarreraController(MainController mainController,
                             CarreraView view,
                             List<Carrera> listaCarreras,
                             List<Participante> listaParticipantes)
    {
        // se guarda la referencia al controlador principal
        this.mainController = mainController;

        // se guarda la vista asociada a este controlador
        this.view = view;

        // se guarda la lista de carreras
        this.listaCarreras = listaCarreras;

        // se guarda la lista de participantes
        this.listaParticipantes = listaParticipantes;

        // se inicializan las tablas de la vista
        inicializarTablas();

        // se inicializan los listeners de los botones
        initListeners();

        // listener para volver al menú principal
        view.volverAlMenúButton.addActionListener(e -> mainController.mostrarMain());
    }

    // este método inicializa las tablas que se muestran en la vista
    private void inicializarTablas()
    {
        // se definen las columnas de la tabla de participantes
        String[] columnasP = {"Nombre", "Documento", "Categoría", "Pato #"};
        modeloParticipantes = new DefaultTableModel(columnasP, 0);

        // se asigna el modelo a la tabla de participantes
        view.tableParticipantesDisponibles.setModel(modeloParticipantes);

        // se definen las columnas de la tabla de carreras
        String[] columnasC = {"#", "Nombre", "Categoría", "Fecha", "Estado"};
        modeloCarreras = new DefaultTableModel(columnasC, 0);

        // se asigna el modelo a la tabla de carreras
        view.tableCarreras.setModel(modeloCarreras);

        // se cargan las carreras existentes en la tabla
        actualizarTablaCarreras();
    }

    // este método conecta los botones de la vista con sus acciones
    private void initListeners()
    {
        // botón para crear una nueva carrera
        view.crearCarreraButton.addActionListener(e -> crearCarrera());

        // botón para filtrar participantes según la categoría
        view.filtrarParticipantesButton.addActionListener(e -> filtrarParticipantes());

        // botón para asignar participantes a una carrera
        view.asignarParticipantesButton.addActionListener(e -> asignarParticipantesACarrera());

        // botón para eliminar una carrera
        view.eliminarCarreraButton.addActionListener(e -> eliminarCarrera());

        // botón para iniciar una carrera
        view.iniciarCarreraButton.addActionListener(e -> iniciarCarrera());

        // botón para limpiar los campos del formulario
        view.limpiarDatosButton.addActionListener(e -> view.limpiarCampos());
    }

    // este método crea una nueva carrera con los datos ingresados en la vista
    private void crearCarrera()
    {
        // se obtiene el nombre de la carrera desde la vista
        String nombre = view.getNombreCarrera();

        // se obtiene la categoría seleccionada
        String categoriaStr = view.getCategoria();

        // validación para que el nombre no esté vacío
        if (nombre.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "El nombre de la carrera es obligatorio");
            return;
        }

        // se convierte el texto de la categoría al enum correspondiente
        Categoria categoria = Categoria.valueOf(categoriaStr);

        // se crea la carrera
        Carrera carrera = new Carrera(nombre, categoria);

        // se agrega la carrera a la lista global
        listaCarreras.add(carrera);

        // mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Carrera creada correctamente");

        // se actualiza la tabla de carreras
        actualizarTablaCarreras();
    }

    // este método filtra los participantes según la categoría seleccionada
    private void filtrarParticipantes()
    {
        // se limpia la tabla antes de volver a llenarla
        modeloParticipantes.setRowCount(0);

        // se obtiene la categoría seleccionada en la vista
        Categoria categoriaSeleccionada = Categoria.valueOf(view.getCategoria());

        // se recorren todos los participantes registrados
        for (Participante p : listaParticipantes)
        {
            // se verifica que la categoría coincida
            if (p.getCategoria() == categoriaSeleccionada)
            {
                // se agrega el participante a la tabla
                modeloParticipantes.addRow(new Object[]{
                        p.getNombre(),
                        p.getDocumento(),
                        p.getCategoria(),
                        p.getNumeroPato()
                });
            }
        }
    }

    // este método asigna los participantes filtrados a una carrera seleccionada
    private void asignarParticipantesACarrera()
    {
        // se obtiene la fila seleccionada en la tabla de carreras
        int fila = view.tableCarreras.getSelectedRow();

        // validación para asegurar que se haya seleccionado una carrera
        if (fila == -1)
        {
            JOptionPane.showMessageDialog(null, "Selecciona una carrera en la tabla inferior");
            return;
        }

        // se obtiene el número de la carrera seleccionada
        int numero = Integer.parseInt(modeloCarreras.getValueAt(fila, 0).toString());

        // variable para almacenar la carrera seleccionada
        Carrera carreraSeleccionada = null;

        // se busca la carrera correspondiente en la lista
        for (Carrera c : listaCarreras)
        {
            if (c.getNumeroCarrera() == numero)
            {
                carreraSeleccionada = c;
                break;
            }
        }

        // validación en caso de error
        if (carreraSeleccionada == null)
        {
            JOptionPane.showMessageDialog(null, "Error cargando la carrera");
            return;
        }

        // se limpian los participantes previamente asignados
        carreraSeleccionada.getParticipantes().clear();

        // se recorren los participantes mostrados en la tabla
        for (int i = 0; i < modeloParticipantes.getRowCount(); i++)
        {
            // se obtiene el documento del participante
            String documento = modeloParticipantes.getValueAt(i, 1).toString();

            // se busca el participante en la lista global
            for (Participante p : listaParticipantes)
            {
                if (p.getDocumento().equals(documento))
                {
                    carreraSeleccionada.agregarParticipante(p);
                }
            }
        }

        // mensaje de confirmación
        JOptionPane.showMessageDialog(null,
                "Participantes asignados correctamente a la carrera");

        // se actualiza la tabla de carreras
        actualizarTablaCarreras();
    }

    // este método elimina una carrera seleccionada
    private void eliminarCarrera()
    {
        // se obtiene la fila seleccionada
        int fila = view.tableCarreras.getSelectedRow();

        // validación para asegurar selección
        if (fila == -1)
        {
            JOptionPane.showMessageDialog(null, "Selecciona una carrera para eliminar");
            return;
        }

        // se obtiene el número de la carrera
        int numero = Integer.parseInt(modeloCarreras.getValueAt(fila, 0).toString());

        // se elimina la carrera de la lista
        listaCarreras.removeIf(c -> c.getNumeroCarrera() == numero);

        // mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Carrera eliminada");

        // se actualiza la tabla
        actualizarTablaCarreras();
    }

    // este método cambia el estado de una carrera a en curso
    private void iniciarCarrera()
    {
        // se obtiene la fila seleccionada
        int fila = view.tableCarreras.getSelectedRow();

        // validación de selección
        if (fila == -1)
        {
            JOptionPane.showMessageDialog(null, "Selecciona una carrera para iniciar");
            return;
        }

        // se obtiene el número de la carrera
        int numero = Integer.parseInt(modeloCarreras.getValueAt(fila, 0).toString());

        // variable para almacenar la carrera
        Carrera carreraSeleccionada = null;

        // se busca la carrera en la lista
        for (Carrera c : listaCarreras)
        {
            if (c.getNumeroCarrera() == numero)
            {
                carreraSeleccionada = c;
                break;
            }
        }

        // validación de error
        if (carreraSeleccionada == null)
        {
            JOptionPane.showMessageDialog(null, "Error cargando la carrera");
            return;
        }

        // se cambia el estado de la carrera a en curso
        carreraSeleccionada.setEstado(EstadoCarrera.EN_CURSO);

        // mensaje informativo
        JOptionPane.showMessageDialog(null,
                "La carrera está lista para la simulación.\n" +
                        "Ve al módulo de simulación.");

        // se actualiza la tabla
        actualizarTablaCarreras();
    }

    // este método actualiza la tabla de carreras con los datos actuales
    private void actualizarTablaCarreras()
    {
        // se limpia la tabla
        modeloCarreras.setRowCount(0);

        // se recorren todas las carreras registradas
        for (Carrera c : listaCarreras)
        {
            // se agrega cada carrera a la tabla
            modeloCarreras.addRow(new Object[]
                    {
                        c.getNumeroCarrera(),
                        c.getNombreCarrera(),
                        c.getCategoria(),
                        c.getFecha(),
                        c.getEstado().getNombre()
                    });
        }
    }
}
