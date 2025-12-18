package DuckAppPro.util;

import DuckAppPro.model.Carrera;
import DuckAppPro.model.Participante;
import DuckAppPro.model.ResultadoCarrera;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// esta clase se encarga de manejar la persistencia de datos de la aplicación
// guarda y carga los participantes, carreras y resultados usando archivos .dat
public class PersistenciaUtil
{
    // nombres de los archivos de persistencia
    private static final String ARCHIVO_PARTICIPANTES = "participantes.dat";
    private static final String ARCHIVO_CARRERAS = "carreras.dat";
    private static final String ARCHIVO_RESULTADOS = "resultados.dat";

    // guardar lista de participantes
    public static void guardarParticipantes(List<Participante> participantes)
    {
        guardarObjeto(ARCHIVO_PARTICIPANTES, participantes);
    }

    // cargar lista de participantes
    public static List<Participante> cargarParticipantes()
    {
        return cargarObjeto(ARCHIVO_PARTICIPANTES);
    }

    // guardar lista de carreras
    public static void guardarCarreras(List<Carrera> carreras)
    {
        guardarObjeto(ARCHIVO_CARRERAS, carreras);
    }

    // cargar lista de carreras
    public static List<Carrera> cargarCarreras()
    {
        return cargarObjeto(ARCHIVO_CARRERAS);
    }

    // guardar lista de resultados
    public static void guardarResultados(List<ResultadoCarrera> resultados)
    {
        guardarObjeto(ARCHIVO_RESULTADOS, resultados);
    }

    // cargar lista de resultados
    public static List<ResultadoCarrera> cargarResultados()
    {
        return cargarObjeto(ARCHIVO_RESULTADOS);
    }

    // método genérico para guardar cualquier objeto
    private static void guardarObjeto(String nombreArchivo, Object objeto)
    {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(nombreArchivo)))
        {
            oos.writeObject(objeto);
        }
        catch (IOException e)
        {
            System.out.println("Error al guardar " + nombreArchivo);
        }
    }

    // método genérico para cargar cualquier objeto
    @SuppressWarnings("unchecked")
    private static <T> List<T> cargarObjeto(String nombreArchivo)
    {
        File archivo = new File(nombreArchivo);

        // si el archivo no existe, se devuelve una lista vacía
        if (!archivo.exists())
        {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(archivo)))
        {
            return (List<T>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Error al cargar " + nombreArchivo);
            return new ArrayList<>();
        }
    }
}
