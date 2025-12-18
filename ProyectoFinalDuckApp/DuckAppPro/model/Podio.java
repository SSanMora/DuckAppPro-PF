package DuckAppPro.model;

import java.util.ArrayList;
import java.util.List;

// esta clase se usa para manejar el podio de una carrera
// aquí se guardan los tres primeros lugares en el orden en que llegan
public class Podio
{
    // esta lista guarda los participantes del podio
    // la posición 0 es el primer lugar, 1 el segundo y 2 el tercero
    private List<Participante> lugares;

    // este constructor inicializa la lista donde se guardará el podio
    public Podio()
    {
        this.lugares = new ArrayList<>();
    }

    // este método sirve para agregar un participante a una posición específica del podio
    public void agregarAlPodio(Participante participante, int posicion)
    {
        // se valida que la posición sea válida para el podio
        if (posicion >= 0 && posicion < 3)
        {
            // aquí se asegura que la lista tenga el tamaño necesario
            while (lugares.size() <= posicion)
            {
                lugares.add(null);
            }

            // finalmente se asigna el participante a la posición indicada
            lugares.set(posicion, participante);
        }
    }

    // este método devuelve la lista completa del podio
    public List<Participante> getLugares()
    {
        return lugares;
    }

    // este método devuelve el participante que quedó en primer lugar
    public Participante getPrimerLugar()
    {
        return lugares.size() > 0 ? lugares.get(0) : null;
    }

    // este método devuelve el participante que quedó en segundo lugar
    public Participante getSegundoLugar()
    {
        return lugares.size() > 1 ? lugares.get(1) : null;
    }

    // este método devuelve el participante que quedó en tercer lugar
    public Participante getTercerLugar()
    {
        return lugares.size() > 2 ? lugares.get(2) : null;
    }

    // este método sirve para verificar si el podio ya está completo
    public boolean estaCompleto()
    {
        return lugares.size() >= 3 &&
                lugares.get(0) != null &&
                lugares.get(1) != null &&
                lugares.get(2) != null;
    }
}
