package DuckAppPro.model;

// esta clase representa a un pato dentro de la simulación de la carrera
// cada pato está asociado a un participante y se mueve sobre la pista
public class Pato
{
    // participante al que pertenece este pato
    private Participante participante;

    // posición actual del pato en el eje x de la pista
    private int posicionX;

    // velocidad actual del pato durante la simulación
    private int velocidad;

    // tiempo acumulado que lleva el pato en la carrera
    private double tiempo;

    // este constructor crea un pato asociado a un participante
    public Pato(Participante participante)
    {
        // se guarda el participante al que pertenece el pato
        this.participante = participante;

        // el pato inicia desde la posición cero de la pista
        this.posicionX = 0;

        // la velocidad inicia en cero y se ajusta durante la simulación
        this.velocidad = 0;

        // el tiempo inicia en cero
        this.tiempo = 0.0;
    }

    // este método devuelve el participante asociado al pato
    public Participante getParticipante()
    {
        return participante;
    }

    // este método devuelve la posición actual del pato en la pista
    public int getPosicionX()
    {
        return posicionX;
    }

    // este método devuelve la velocidad actual del pato
    public int getVelocidad()
    {
        return velocidad;
    }

    // este método devuelve el tiempo acumulado del pato en la carrera
    public double getTiempo()
    {
        return tiempo;
    }

    // este método permite cambiar la posición del pato en la pista
    public void setPosicionX(int posicionX)
    {
        this.posicionX = posicionX;
    }

    // este método permite cambiar la velocidad del pato durante la simulación
    public void setVelocidad(int velocidad)
    {
        this.velocidad = velocidad;
    }

    // este método permite actualizar el tiempo acumulado del pato
    public void setTiempo(double tiempo)
    {
        this.tiempo = tiempo;
    }

    // este método se encarga de mover el pato hacia adelante en la pista
    public void avanzar()
    {
        this.posicionX += this.velocidad;
    }
}
