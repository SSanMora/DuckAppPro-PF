package DuckAppPro.controller;

import DuckAppPro.model.Carrera;
import DuckAppPro.model.ResultadoCarrera;
import DuckAppPro.view.EstadisticasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// este es el controlador de estadísticas de duckapp pro usando listas y bucles simples
// se encarga de mostrar los resultados, calcular el participante con más podios
// y el pato más rápido, y exportar la información a un archivo txt
public class EstadisticasController
{
    // referencia a la vista de estadísticas
    private EstadisticasView view;

    // referencia al controlador principal para volver al menú
    private MainController mainController;

    // lista de resultados de todas las carreras
    private List<ResultadoCarrera> listaResultados;

    // lista de todas las carreras creadas
    private List<Carrera> listaCarreras;

    // modelo de la tabla donde se muestran los resultados
    private DefaultTableModel modeloTabla;

    // constructor del controlador de estadísticas
    // recibe controlador principal, vista y listas de resultados y carreras
    public EstadisticasController(MainController mainController,
                                  EstadisticasView view,
                                  List<ResultadoCarrera> listaResultados,
                                  List<Carrera> listaCarreras)
    {
        this.mainController = mainController;
        this.view = view;
        this.listaResultados = listaResultados;
        this.listaCarreras = listaCarreras;

        // inicializa la tabla de resultados
        inicializarTabla();

        // conecta los botones con sus acciones
        initListeners();

        // actualiza las estadísticas al abrir la vista
        actualizarEstadisticas();

        // botón para volver al menú principal
        view.volverAlMenúButton.addActionListener(e -> mainController.mostrarMain());
    }

    // configura la tabla de resultados con las columnas carrera, ganador y tiempo
    private void inicializarTabla()
    {
        String[] columnas = {"Carrera", "Ganador", "Tiempo (s)"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        view.tableEstadisticas.setModel(modeloTabla);
    }

    // inicializa los botones de la vista y sus acciones
    private void initListeners()
    {
        view.actualizarButton.addActionListener(e -> actualizarEstadisticas());
        view.exportarEnTxtButton.addActionListener(e -> exportarTXT());
        view.exportacionButton.addActionListener(e -> exportarTXT());
    }

    // actualiza todas las estadísticas mostradas en la vista
    private void actualizarEstadisticas()
    {
        actualizarTablaResultados();
        actualizarMasPodios();
        actualizarPatoMasRapido();
        view.actualizarTotalCanneras(listaCarreras.size());
    }

    // llena la tabla con los resultados de cada carrera
    private void actualizarTablaResultados()
    {
        modeloTabla.setRowCount(0);

        for (ResultadoCarrera r : listaResultados)
        {
            modeloTabla.addRow(new Object[]
            {
                    r.getCarrera().getNombreCarrera(),
                    r.getGanador() != null ? r.getGanador().getNombre() : "---",
                    r.getTiempos().isEmpty() ? "---" : r.getTiempos().get(0)
            });
        }
    }

    // calcula el participante con más podios usando listas y bucles simples
    private void actualizarMasPodios()
    {
        if (listaResultados.isEmpty())
        {
            view.actualizarMasPodio("---");
            return;
        }

        // listas paralelas: nombres de participantes y cantidad de podios
        List<String> nombres = new ArrayList<>();
        List<Integer> contadores = new ArrayList<>();

        for (ResultadoCarrera r : listaResultados)
        {
            if (r.getGanador() != null)
            {
                String nombre = r.getGanador().getNombre();
                boolean encontrado = false;

                // verifica si el participante ya está registrado y aumenta el contador
                for (int i = 0; i < nombres.size(); i++)
                {
                    if (nombres.get(i).equals(nombre))
                    {
                        contadores.set(i, contadores.get(i) + 1);
                        encontrado = true;
                        break;
                    }
                }

                // si el participante no estaba registrado, se agrega con contador 1
                if (!encontrado)
                {
                    nombres.add(nombre);
                    contadores.add(1);
                }
            }
        }

        // busca el participante con mayor cantidad de podios
        String mejor = "---";
        int maxPodios = 0;
        for (int i = 0; i < nombres.size(); i++)
        {
            if (contadores.get(i) > maxPodios)
            {
                maxPodios = contadores.get(i);
                mejor = nombres.get(i);
            }
        }

        // actualiza la vista con el participante con más podios
        view.actualizarMasPodio(mejor);
    }

    // calcula el pato más rápido promediando sus tiempos
    // utiliza listas paralelas sin streams ni maps
    private void actualizarPatoMasRapido()
    {
        if (listaResultados.isEmpty())
        {
            view.actualizarPatoMasRapido("---");
            return;
        }

        // listas paralelas: nombres, suma de tiempos y cantidad de tiempos
        List<String> nombres = new ArrayList<>();
        List<Double> sumaTiempos = new ArrayList<>();
        List<Integer> cantidadTiempos = new ArrayList<>();

        for (ResultadoCarrera r : listaResultados)
        {
            if (!r.getTiempos().isEmpty() && r.getGanador() != null)
            {
                String nombre = r.getGanador().getNombre();
                double tiempo = r.getTiempos().get(0);

                boolean encontrado = false;
                for (int i = 0; i < nombres.size(); i++)
                {
                    if (nombres.get(i).equals(nombre))
                    {
                        sumaTiempos.set(i, sumaTiempos.get(i) + tiempo);
                        cantidadTiempos.set(i, cantidadTiempos.get(i) + 1);
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado)
                {
                    nombres.add(nombre);
                    sumaTiempos.add(tiempo);
                    cantidadTiempos.add(1);
                }
            }
        }

        // calcula el promedio de tiempos y determina el más rápido
        String masRapido = "---";
        double mejorPromedio = Double.MAX_VALUE;

        for (int i = 0; i < nombres.size(); i++)
        {
            double promedio = sumaTiempos.get(i) / cantidadTiempos.get(i);
            if (promedio < mejorPromedio)
            {
                mejorPromedio = promedio;
                masRapido = nombres.get(i);
            }
        }

        // actualiza la vista con el pato más rápido
        view.actualizarPatoMasRapido(masRapido);
    }

    // exporta las estadísticas a un archivo txt
    private void exportarTXT()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar estadísticas como txt");

        int opcion = chooser.showSaveDialog(null);
        if (opcion != JFileChooser.APPROVE_OPTION) return;

        try (FileWriter writer = new FileWriter(chooser.getSelectedFile() + ".txt"))
        {
            // encabezado del archivo
            writer.write("=== Estadísticas DuckApp pro ===\n\n");
            writer.write("Participante con más podios: " + view.lblMasPodio.getText() + "\n");
            writer.write("Pato más rápido: " + view.lblPatoMasRapido.getText() + "\n");
            writer.write("Total de carreras: " + view.lblTotalCarreras.getText() + "\n\n");
            writer.write("=== Resultados ===\n");
            writer.write(String.format("%-20s %-20s %-10s\n", "Carrera", "Ganador", "Tiempo"));

            // se escriben todas las filas de la tabla
            for (int i = 0; i < modeloTabla.getRowCount(); i++)
            {
                writer.write(String.format("%-20s %-20s %-10s\n",
                        modeloTabla.getValueAt(i, 0),
                        modeloTabla.getValueAt(i, 1),
                        modeloTabla.getValueAt(i, 2)
                ));
            }

            JOptionPane.showMessageDialog(null, "Archivo exportado correctamente");

        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al exportar archivo");
        }
    }
}
