package DuckAppPro.controller;

import DuckAppPro.model.*;
import DuckAppPro.view.SimulacionView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// este controlador maneja la simulación de las carreras
// conecta la vista de simulación con las carreras y resultados
public class SimulacionController
{
    // referencia a la vista de simulación
    private SimulacionView view;

    // referencia al controlador principal para volver al menú
    private MainController mainController;

    // lista global de carreras
    private List<Carrera> listaCarreras;

    // lista global de resultados de carreras
    private List<ResultadoCarrera> listaResultados;

    // carrera actualmente seleccionada
    private Carrera carreraActual;

    // lista de patos participantes en la simulación
    private List<Pato> patos;

    // lista de labels de patos que se muestran en la pista
    private List<JLabel> labelsPatos;

    // temporizador que controla el avance de los patos
    private Timer timer;

    // tiempo de inicio de la simulación
    private long tiempoInicio;

    // posición final (meta) en la pista
    private int metaFinal;

    // lista con el orden de llegada de los participantes
    private List<Participante> podioFinal = new ArrayList<>();

    // constructor del controlador de simulación
    // recibe el controlador principal, la vista y las listas globales
    public SimulacionController(MainController mainController,
                                SimulacionView view,
                                List<Carrera> listaCarreras,
                                List<ResultadoCarrera> listaResultados)
    {
        // se guardan las referencias recibidas
        this.mainController = mainController;
        this.view = view;
        this.listaCarreras = listaCarreras;
        this.listaResultados = listaResultados;

        // se inicializan las listas internas
        patos = new ArrayList<>();
        labelsPatos = new ArrayList<>();

        // se cargan las carreras disponibles en el combo
        cargarCarrerasEnCombo();

        // se inicializan los listeners de los botones y combo
        initListeners();

        // listener para volver al menú principal
        view.volverAlMenúButton.addActionListener(e -> mainController.mostrarMain());
    }

    // este método carga en el combo las carreras que están en curso
    private void cargarCarrerasEnCombo()
    {
        view.comboCarreras.removeAllItems();
        for (Carrera c : listaCarreras)
        {
            if (c.getEstado() == EstadoCarrera.EN_CURSO)
            {
                view.comboCarreras.addItem("Carrera #" + c.getNumeroCarrera());
            }
        }
    }

    // este método conecta los botones y el combo de la vista con sus acciones
    private void initListeners()
    {
        view.comboCarreras.addActionListener(e -> seleccionarCarrera());
        view.iniciarSimulacionButton.addActionListener(e -> iniciarSimulacion());
        view.pausarButton.addActionListener(e -> pausar());
        view.reiniciarButton.addActionListener(e -> reiniciar());
        view.guardarResultadosButton.addActionListener(e -> guardarResultados());
    }

    // este método selecciona la carrera elegida en el combo
    private void seleccionarCarrera()
    {
        if (view.comboCarreras.getSelectedIndex() == -1) return;

        int numero = Integer.parseInt(
                view.comboCarreras.getSelectedItem().toString().replace("Carrera #", "")
        );

        carreraActual = null;
        for (Carrera c : listaCarreras)
        {
            if (c.getNumeroCarrera() == numero)
            {
                carreraActual = c;
                break;
            }
        }

        if (carreraActual == null) return;

        // actualizar estado y preparar los patos en la pista
        view.actualizarEstado("Carrera lista para simulación");
        prepararPatosEnPista();
        view.iniciarSimulacionButton.setEnabled(true);
    }

    // este método coloca los patos en la pista al iniciar la simulación
    private void prepararPatosEnPista()
    {
        // limpiar panel de pista y resetear layout
        view.panelPista.removeAll();
        view.panelPista.setLayout(null);
        view.panelPista.revalidate();
        view.panelPista.repaint();

        // limpiar listas de patos y podio
        patos.clear();
        labelsPatos.clear();
        podioFinal.clear();

        int y = 20;

        // agregar cada participante como pato y label en la pista
        for (Participante p : carreraActual.getParticipantes())
        {
            Pato pato = new Pato(p);
            pato.setVelocidad(2 + (int)(Math.random() * 6));

            JLabel lbl = new JLabel("🦆 " + p.getNumeroPato());
            lbl.setBounds(10, y, 150, 40);
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

            patos.add(pato);
            labelsPatos.add(lbl);
            view.panelPista.add(lbl);

            y += 60;
        }

        view.panelPista.repaint();
    }

    // este método inicia la simulación de la carrera
    private void iniciarSimulacion()
    {
        if (carreraActual == null) return;

        // calcular posición de meta según el ancho del panel
        if (!labelsPatos.isEmpty())
        {
            metaFinal = view.panelPista.getWidth()
                    - labelsPatos.get(0).getWidth()
                    - 20;
        }

        view.actualizarEstado("Carrera en curso...");
        view.pausarButton.setEnabled(true);
        view.reiniciarButton.setEnabled(true);
        view.iniciarSimulacionButton.setEnabled(false);

        // registrar tiempo de inicio
        tiempoInicio = System.currentTimeMillis();

        // iniciar timer que mueve los patos
        timer = new Timer(50, e -> moverPatos());
        timer.start();
    }

    // este método mueve los patos y controla llegada a meta
    private void moverPatos()
    {
        for (int i = 0; i < patos.size(); i++)
        {
            Pato pato = patos.get(i);
            JLabel lbl = labelsPatos.get(i);

            // no mover patos que ya llegaron
            if (podioFinal.contains(pato.getParticipante())) continue;

            // avanzar pato y actualizar label
            pato.avanzar();
            int nuevaX = pato.getPosicionX();
            lbl.setLocation(nuevaX, lbl.getY());

            // verificar si llegó a la meta
            if (nuevaX >= metaFinal)
            {
                pato.setPosicionX(metaFinal);
                lbl.setLocation(metaFinal, lbl.getY());

                // agregar participante al podio
                podioFinal.add(pato.getParticipante());

                // si hay podio completo, terminar carrera
                if (podioFinal.size() == 3)
                {
                    terminarCarrera();
                    return;
                }
            }
        }
    }

    // este método termina la carrera y muestra resultados
    private void terminarCarrera()
    {
        // detener timer y actualizar estado
        timer.stop();
        view.actualizarEstado("Carrera finalizada");

        // calcular tiempo total de carrera
        double tiempoTotal = (System.currentTimeMillis() - tiempoInicio) / 1000.0;

        // actualizar ganador y tiempo
        Participante ganador = podioFinal.get(0);
        view.actualizarGanador(ganador.getNombre());
        view.actualizarTiempo(tiempoTotal + " s");

        // actualizar podio completo
        String podio =
                "1° " + podioFinal.get(0).getNombre() + "\n" +
                "2° " + (podioFinal.size() > 1 ? podioFinal.get(1).getNombre() : "---") + "\n" +
                "3° " + (podioFinal.size() > 2 ? podioFinal.get(2).getNombre() : "---");

        view.actualizarPodio(podio);

        // habilitar botón para guardar resultados y actualizar estado de carrera
        view.setGuardarResultadosHabilitado(true);
        carreraActual.setEstado(EstadoCarrera.FINALIZADA);
    }

    // este método pausa la simulación
    private void pausar()
    {
        if (timer != null) timer.stop();
        view.actualizarEstado("Simulación pausada");
    }

    // este método reinicia la simulación desde el inicio
    private void reiniciar()
    {
        if (timer != null) timer.stop();
        seleccionarCarrera();
        view.actualizarEstado("Simulación reiniciada");
    }

    // este método guarda los resultados de la carrera en la lista global
    private void guardarResultados()
    {
        ResultadoCarrera res = new ResultadoCarrera(carreraActual, podioFinal,
                List.of((System.currentTimeMillis() - tiempoInicio) / 1000.0));

        listaResultados.add(res);

        JOptionPane.showMessageDialog(null, "Resultados guardados correctamente");
        view.setGuardarResultadosHabilitado(false);
        view.actualizarEstado("Resultados guardados");
    }
}
