package DuckAppPro;

import DuckAppPro.controller.MainController;
import DuckAppPro.view.MainView;

import javax.swing.*;

// clase principal que inicia la aplicación DuckApp pro
// se encarga de crear la ventana principal y el controlador inicial
public class main
{
    // método main que arranca la aplicación
    public static void main(String[] args)
    {
        // asegurar que la interfaz gráfica se ejecute en el hilo de swing
        SwingUtilities.invokeLater(() ->
        {
            // crear la vista principal del menú
            MainView mainView = new MainView();

            // crear el frame principal que contendrá todas las vistas
            JFrame frame = new JFrame("DuckApp Pro");
            frame.setContentPane(mainView.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null); // centrar la ventana en pantalla
            frame.setVisible(true); // mostrar la ventana

            // crear el controlador principal y pasarle la vista y el frame
            new MainController(mainView, frame);
        });
    }
}
