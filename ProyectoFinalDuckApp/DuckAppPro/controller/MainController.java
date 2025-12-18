package DuckAppPro.controller;

import DuckAppPro.model.Carrera;
import DuckAppPro.model.Participante;
import DuckAppPro.model.ResultadoCarrera;
import DuckAppPro.util.PersistenciaUtil;
import DuckAppPro.view.*;

import javax.swing.*;
import java.util.List;

// este es el controlador principal de toda la aplicación
// se encarga de manejar el cambio entre pantallas y de compartir las listas globales
public class MainController
{
    // referencia a la vista principal (menú)
    private MainView view;

    // frame principal de la aplicación, es único para todas las vistas
    private JFrame mainFrame;

    // lista global de participantes
    private List<Participante> listaParticipantes;

    // lista global de carreras
    private List<Carrera> listaCarreras;

    // lista global de resultados de carreras
    private List<ResultadoCarrera> listaResultados;

    // constructor del controlador principal
    public MainController(MainView view, JFrame frame)
    {
        // se guarda la vista principal
        this.view = view;

        // se guarda el frame principal
        this.mainFrame = frame;

        // se inicializan las listas globales
//        listaParticipantes = new ArrayList<>();
//        listaCarreras = new ArrayList<>();
//        listaResultados = new ArrayList<>();

        // se cargan los datos guardados desde archivos .dat
        listaParticipantes = PersistenciaUtil.cargarParticipantes();
        listaCarreras = PersistenciaUtil.cargarCarreras();
        listaResultados = PersistenciaUtil.cargarResultados();

        // botón para cerrar la aplicación
        view.salirButton.addActionListener(e -> System.exit(0));

        // se inicializan los listeners del menú
        initListeners();

        // se muestra el menú principal con un tamaño fijo
        cambiarVista(view.getMainPanel(), 700, 300);
    }

    // este método inicializa las acciones de los botones del menú principal
    private void initListeners()
    {
        // botón para la gestión de participantes
        view.gestionDeParticipantesButton.addActionListener(e ->
        {
            // se crea la vista de participantes
            ParticipanteView participanteView = new ParticipanteView();

            // se crea su controlador y se le pasa la lista global
            new ParticipanteController(this, participanteView, listaParticipantes);

            // se cambia la vista y se ajusta el tamaño del frame
            cambiarVista(participanteView.getMainPanel(), 900, 400);
        });

        // botón para la gestión de carreras
        view.gestionDeCarrerasButton.addActionListener(e ->
        {
            // se crea la vista de carreras
            CarreraView carreraView = new CarreraView();

            // se crea su controlador con las listas necesarias
            new CarreraController(this, carreraView, listaCarreras, listaParticipantes);

            // se cambia la vista y se ajusta el tamaño del frame
            cambiarVista(carreraView.getMainPanel(), 1260, 320);
        });

        // botón para la simulación de carreras
        view.simulacionDeCarreraButton.addActionListener(e ->
        {
            // se crea la vista de simulación
            SimulacionView simulacionView = new SimulacionView();

            // se crea su controlador con las listas necesarias
            new SimulacionController(this, simulacionView, listaCarreras, listaResultados);

            // se cambia la vista y se ajusta el tamaño del frame
            cambiarVista(simulacionView.getMainPanel(), 810, 510);
        });

        // botón para la vista de estadísticas
        view.estadisticasButton.addActionListener(e ->
        {
            // se crea la vista de estadísticas
            EstadisticasView estadisticasView = new EstadisticasView();

            // se crea su controlador con las listas globales
            new EstadisticasController(this, estadisticasView, listaResultados, listaCarreras);

            // se cambia la vista y se ajusta el tamaño del frame
            cambiarVista(estadisticasView.getMainPanel(), 450, 430);
        });

        // botón para salir de la aplicación
        view.salirButton.addActionListener(e ->
        {

            // se guardan todos los datos antes de cerrar la aplicación
            PersistenciaUtil.guardarParticipantes(listaParticipantes);
            PersistenciaUtil.guardarCarreras(listaCarreras);
            PersistenciaUtil.guardarResultados(listaResultados);

            System.exit(0);
        });
    }

    // este método centraliza el cambio de vistas dentro del mismo frame
    // también se encarga de fijar el tamaño de cada pantalla
    private void cambiarVista(JPanel nuevaVista, int ancho, int alto)
    {
        // se cambia el panel mostrado en el frame
        mainFrame.setContentPane(nuevaVista);

        // se ajusta el tamaño del frame
        mainFrame.setSize(ancho, alto);

        // se centra la ventana en la pantalla
        mainFrame.setLocationRelativeTo(null);

        // se actualiza el frame
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // este método permite volver al menú principal desde cualquier vista
    public void mostrarMain()
    {
        // se muestra nuevamente el panel del menú con su tamaño fijo
        cambiarVista(view.getMainPanel(), 700, 300);
    }
}
