package DuckAppPro.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// esta clase representa una carrera dentro del sistema
// aquí se guarda toda la información básica de una carrera de patos
public class Carrera implements Serializable
{
    // este contador sirve para asignar un número único a cada carrera que se crea
    private static int contadorCarreras = 1;

    // número identificador de la carrera
    private int numeroCarrera;

    // nombre que se le da a la carrera
    private String nombre;

    // categoría de la carrera (niño, joven o adulto)
    private Categoria categoria;

    // fecha en la que se crea la carrera, guardada como texto
    private String fecha;

    // lista de participantes que van a correr en esta carrera
    private List<Participante> participantes;

    // estado actual de la carrera (programada, en curso o finalizada)
    private EstadoCarrera estado;

    // este constructor se usa para crear una nueva carrera con su nombre y categoría
    public Carrera(String nombre, Categoria categoria)
    {
        // se asigna un número de carrera automático usando el contador
        this.numeroCarrera = contadorCarreras++;

        // se guarda el nombre de la carrera
        this.nombre = nombre;

        // se guarda la categoría de la carrera
        this.categoria = categoria;

        // aquí se obtiene la fecha y hora actual y se guarda con el formato indicado
        this.fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // se inicializa la lista donde se guardarán los participantes
        this.participantes = new ArrayList<>();

        // la carrera siempre inicia en estado programada
        this.estado = EstadoCarrera.PROGRAMADA;
    }

    // este método sirve para agregar un participante a la carrera
    public void agregarParticipante(Participante participante)
    {
        // el participante recibido se añade a la lista
        participantes.add(participante);
    }

    // este método devuelve el número de la carrera
    public int getNumeroCarrera()
    {
        return numeroCarrera;
    }

    // este método devuelve el nombre de la carrera
    public String getNombreCarrera()
    {
        return nombre;
    }

    // este método devuelve la categoría de la carrera
    public Categoria getCategoria()
    {
        return categoria;
    }

    // este método devuelve la fecha en la que se creó la carrera
    public String getFecha()
    {
        return fecha;
    }

    // este método devuelve la lista de participantes de la carrera
    public List<Participante> getParticipantes()
    {
        return participantes;
    }

    // este método devuelve el estado actual de la carrera
    public EstadoCarrera getEstado()
    {
        return estado;
    }

    // este método permite cambiar el estado de la carrera cuando avanza el proceso
    public void setEstado(EstadoCarrera estado)
    {
        this.estado = estado;
    }
}
