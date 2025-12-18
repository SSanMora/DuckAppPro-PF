package DuckAppPro.model;

// este enum se usa para definir las categorías posibles dentro del sistema
// sirve tanto para participantes como para carreras
public enum Categoria
{
    // categorías disponibles
    NIÑO("NIÑO"),
    JOVEN("JOVEN"),
    ADULTO("ADULTO");

    // este atributo guarda el nombre que se mostrará en la interfaz
    private final String nombre;

    // este constructor asigna el nombre a cada categoría
    Categoria(String nombre)
    {
        this.nombre = nombre;
    }

    // este método devuelve el nombre de la categoría
    public String getNombre()
    {
        return nombre;
    }

    // este método se usa para que la categoría se muestre correctamente en los comboBox
    @Override
    public String toString()
    {
        return nombre;
    }
}
