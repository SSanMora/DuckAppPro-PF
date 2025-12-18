package DuckAppPro.model;

// este enum se usa para representar el estado en el que se encuentra una carrera
// ayuda a controlar el flujo desde que se crea hasta que termina
public enum EstadoCarrera
{
    // la carrera fue creada pero aún no ha iniciado
    PROGRAMADA("Programada"),

    // la carrera está actualmente en ejecución
    EN_CURSO("En Curso"),

    // la carrera ya terminó y tiene resultados
    FINALIZADA("Finalizada");

    // este atributo guarda el nombre del estado tal como se muestra en la interfaz
    private final String nombre;

    // este constructor asigna el nombre a cada estado
    EstadoCarrera(String nombre)
    {
        this.nombre = nombre;
    }

    // este método devuelve el nombre del estado
    public String getNombre()
    {
        return nombre;
    }

    // este método permite mostrar el nombre del estado directamente en textos o combos
    @Override
    public String toString()
    {
        return nombre;
    }
}
