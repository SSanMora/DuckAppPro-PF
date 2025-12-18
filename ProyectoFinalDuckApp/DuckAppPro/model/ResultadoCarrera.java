package DuckAppPro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// esta clase se usa para guardar los resultados finales de una carrera
// aquí se almacena la carrera, el podio y los tiempos obtenidos
public class ResultadoCarrera implements Serializable
{
    // referencia a la carrera a la que pertenecen estos resultados
    private Carrera carrera;

    // lista con los participantes que quedaron en el podio
    // el primer elemento es el ganador
    private List<Participante> podio;

    // lista con los tiempos registrados durante la carrera
    private List<Double> tiempos;

    // este constructor recibe la carrera y los datos finales de la simulación
    public ResultadoCarrera(Carrera carrera, List<Participante> podio, List<Double> tiempos)
    {
        // se guarda la carrera asociada a estos resultados
        this.carrera = carrera;

        // se crea una copia de la lista del podio para no modificar la original
        this.podio = new ArrayList<>(podio);

        // se crea una copia de la lista de tiempos
        this.tiempos = new ArrayList<>(tiempos);
    }

    // este método devuelve la carrera asociada al resultado
    public Carrera getCarrera()
    {
        return carrera;
    }

    // este método devuelve la lista de participantes que quedaron en el podio
    public List<Participante> getPodio()
    {
        return podio;
    }

    // este método devuelve la lista de tiempos registrados
    public List<Double> getTiempos()
    {
        return tiempos;
    }

    // este método devuelve el ganador de la carrera
    // el ganador siempre es el primer elemento del podio
    public Participante getGanador()
    {
        return podio.isEmpty() ? null : podio.get(0);
    }
}
