package DuckAppPro.model;

import java.io.Serializable;

// esta clase representa a un participante del sistema
// cada participante está asociado a un pato que corre en las carreras
public class Participante implements Serializable
{
    // nombre del participante
    private String nombre;

    // edad del participante
    private int edad;

    // documento de identificación del participante
    private String documento;

    // categoría a la que pertenece el participante
    private Categoria categoria;

    // número del pato asignado al participante
    private String numeroPato;

    // este constructor se usa para crear un participante con todos sus datos
    public Participante(String nombre, int edad, String documento,
                        Categoria categoria, String numeroPato)
    {
        // se guarda el nombre del participante
        this.nombre = nombre;

        // se guarda la edad del participante
        this.edad = edad;

        // se guarda el documento del participante
        this.documento = documento;

        // se guarda la categoría del participante
        this.categoria = categoria;

        // se guarda el número del pato asignado
        this.numeroPato = numeroPato;
    }

    // este método devuelve el nombre del participante
    public String getNombre()
    {
        return nombre;
    }

    // este método devuelve la edad del participante
    public int getEdad()
    {
        return edad;
    }

    // este método devuelve el documento del participante
    public String getDocumento()
    {
        return documento;
    }

    // este método devuelve la categoría del participante
    public Categoria getCategoria()
    {
        return categoria;
    }

    // este método devuelve el número del pato del participante
    public String getNumeroPato()
    {
        return numeroPato;
    }

    // este método se usa para mostrar el participante de forma legible en listas o tablas
    @Override
    public String toString()
    {
        return nombre + " (Pato #" + numeroPato + ")";
    }
}
