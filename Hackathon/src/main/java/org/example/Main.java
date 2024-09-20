package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import static java.lang.System.exit;

public class Main {

    // Variables globales de elementos gráficos
    private static JFrame frame;
    private static JButton btnSi;
    private static JButton btnNo;
    private static JTextArea Muestra;

    //Indices
    private static int indiceSi = 0;
    private static int indice = 0;
    private static int indiceS = 0;
    private static int indiceaux = 4;
    //Indices
    public static void main(String[] args) {
        // ↓ Apartado gráfico ↓

        // Crea el JFrame
        frame = new JFrame("Heladería Sistema Experto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 760);
        frame.getContentPane().setLayout(null);

        // Crea un JLabel
        JLabel Titulo = new JLabel("Heladeria Sistema Experto con Prolog y Java");
        // Establece la fuente personalizada
        Font font = new Font("Courier New", Font.BOLD, 24);
        Titulo.setFont(font);
        // Establece el color de fuente personalizado
        Color color = new Color(0,204,204); // Rojo
        Titulo.setForeground(color);
        // Establece la posición y tamaño del JLabel
        Titulo.setBounds(250, -75, 700, 200);
        // Agrega el JLabel al contenido del JFrame
        frame.getContentPane().add(Titulo);

        // Crea un JLabel
        JLabel Equipo = new JLabel("Equipo 4");
        // Establece la fuente personalizada
        Font font1 = new Font("Courier New", Font.BOLD, 24);
        Equipo.setFont(font1);
        // Establece el color de fuente personalizado
        Color color1 = new Color(0,102,153); // Rojo
        Equipo.setForeground(color1);
        // Establece la posición y tamaño del JLabel
        Equipo.setBounds(1075, -75, 700, 200);
        // Agrega el JLabel al contenido del JFrame
        frame.getContentPane().add(Equipo);

        // Crea un JButton
        btnSi = new JButton();
        btnNo = new JButton();

        // Carga la imagen y la asigna al botón
        ImageIcon IMGbtnSi = new ImageIcon("C:\\Users\\USER\\IdeaProjects\\Sistema-experto\\img\\si.png");
        btnSi.setIcon(IMGbtnSi);
        ImageIcon IMGbtnNo = new ImageIcon("C:\\Users\\USER\\IdeaProjects\\Sistema-experto\\img\\no.png");
        btnNo.setIcon(IMGbtnNo);

        // Establece el tamaño y la posición del botón
        btnSi.setBounds(35, 250, IMGbtnSi.getIconWidth(), IMGbtnSi.getIconHeight());
        btnNo.setBounds(175, 250, IMGbtnNo.getIconWidth(), IMGbtnNo.getIconHeight());

        // Crea un JTextArea
        Muestra = new JTextArea();
        Muestra.setBounds(720, 360, 480, 240);
        Muestra.setEditable(false);
        Color colorta = new Color(0, 176, 207);
        Muestra.setBackground(colorta);
        // Agrega el JTextArea al contenido del JFrame
        frame.getContentPane().add(Muestra);

        // Agrega ActionListener para el botón "Si"
        btnSi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón Si clickeado");
            }
        });

        // Agrega ActionListener para el botón "No"
        btnNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón No clickeado");
            }
        });

        // Agrega los componentes al contenido del JFrame
        frame.getContentPane().add(btnSi);
        frame.getContentPane().add(btnNo);

        // Configura el JLabel de fondo
        JLabel bg = new JLabel();
        ImageIcon imageIconL = new ImageIcon("C:\\Users\\USER\\IdeaProjects\\Sistema-experto\\img\\backgroundp.png");
        bg.setIcon(imageIconL);
        bg.setBounds(0, -20, frame.getWidth(), frame.getHeight());
        frame.getContentPane().add(bg);

        // Asigna el objeto frame a Main.frame
        Main.frame = frame;

        // Muestra la ventana
        frame.setVisible(true);

        // ↑ Apartado gráfico ↑

        //Mensaje de entrada
        Muestra.setText("Hola, bienvenido a nuestra heladería, ¿quisiera un helado cremoso?");

        //Botón Si
        btnSi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Mensaje de control
                System.out.println("Botón Si clickado");

                indiceSi++;

                //Aquí se debe conseguir la premisa de la consulta en base al índice
                //y luego se convierte a un String manejable
                Term preferencia = preferencias.get(indice);
                String p = preferencia.toString();

                //Se compara el String con los casos existentes, si se agregaran más premisas a "preferencia"
                //se tendría que abrir otro "case" con esa nueva premisa
                switch (p){
                    //Caso 1 de preferencias: Helados cremosos
                    case "cremoso":
                        //Mensaje de control
                        System.out.println("Cremoso");

                        //Mensaje de inicio de opciones
                        Muestra.setText("Bien, nuestras opciones de helado cremoso son: ");

                        Term sabor = sabores.get(indiceS);
                        String s = sabor.toString();

                        switch (s){
                            //Caso 1: Se pulsó "SI", se recomienda helado de vainilla, el contador de "SI" sube de 2
                            //a 4
                            case "helado_vainilla":
                                Muestra.append(s);
                                indiceSi++;
                                System.out.println("SI:" + indiceSi);
                                System.out.println(sabor);
                                if(indiceSi==4){
                                    Muestra.setText("Perfecto, espero disfrutes tu helado de vainilla :D");
                                }
                                break;

                            //Caso 2: Se pulsó "SI" pero después "NO", el contador de "SI está en 2 así que se puede
                            //repetir el codigo del caso anterior
                            case "helado_chocolate":
                                indiceSi++;
                                System.out.println("SI:" + indiceSi);
                                System.out.println(sabor);
                                if(indiceSi==4){
                                    Muestra.setText("Perfecto, espero disfrutes tu helado de chocolate :D");
                                }
                                break;
                        }
                        break;

                    //Caso 2 de preferencias: Helados refrescantes
                    case "refrescante":
                        //Mensaje de control
                        System.out.println("Refrescante");

                        Muestra.setText("Bien, nuestras opciones de helado refrescante son: ");

                        indiceS = indiceaux;
                        sabor = sabores.get(indiceS);
                        s = sabor.toString();

                        switch (s){
                            case "helado_fresa":
                                Muestra.append(s);
                                indiceSi++;
                                System.out.println("SI:" + indiceSi);
                                System.out.println(sabor);
                                if(indiceSi==4){
                                    Muestra.setText("Perfecto, espero disfrutes tu helado de fresa :D");
                                }
                                break;

                            case "helado_limon":
                                indiceSi++;
                                System.out.println("SI:" + indiceSi);
                                System.out.println(sabor);
                                if(indiceSi==4){
                                    Muestra.setText("Perfecto, espero disfrutes tu helado de limon :D");
                                }
                                break;
                        }
                        break;

                    default: System.out.println("No hay opción correcta");
                }
            }
        });

        //Botón No
        btnNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Mensaje de control
                System.out.println("Botón No clickado");

                //Aquí se debe conseguir la premisa de la consulta en base al índice
                //y luego se convierte a un String manejable
                Term preferencia = preferencias.get(indice);
                String p = preferencia.toString();

                //Si el indice "SI" es 0 quiere decir que se rechazó la primera opción, con lo cual se pasa a preguntar
                //las siguientes opciones de la lista hasta que no haya más opciones
                if(indiceSi == 0){
                    //Se compara el String con los casos existentes, si se agregaran más premisas a "preferencia"
                    //se tendría que abrir otro "case" con esa nueva premisa
                    switch (p){
                        //Si fue "cremoso la opción rechazada, se aumenta el índice para preguntar la siguiente
                        case "cremoso":
                            //Mensaje de control
                            System.out.println("No Cremoso");
                            Muestra.setText("¿Entonces uno refrescante?");
                            indice++;
                            break;

                        case "refrescante":
                            //Mensaje de control
                            System.out.println("No Refrescante");
                            Muestra.setText("En ese caso le recomiendo el sabor de mango :D");
                            break;
                    }
                }

                //Se comprueba si se está en helados cremosos
                if(indiceSi == 2){
                    Term sabor = sabores.get(indiceS);
                    String s = sabor.toString();

                    switch (s){
                        //Caso 1: Se pulsó "SI" y luego "NO"
                        case "helado_vainilla":
                            Muestra.setText("Bien, nuestras opciones de helado cremoso son: ");
                            indiceS++;
                            indiceS++;
                            System.out.println("Sabor:" + indiceS);
                            sabor = sabores.get(indiceS);
                            s = sabor.toString();
                            Muestra.append(s);
                            break;

                        //Caso 2: Se pulsó "SI", luego "NO", luego "NO" nuevamente
                        case "helado_chocolate":
                            Muestra.setText("No hay más helados cremosos :(");
                            break;

                        case "helado_fresa":
                            Muestra.setText("Bien, nuestras opciones de helado refrescante son: ");
                            indiceS++;
                            indiceS++;
                            indiceaux = 6;
                            System.out.println("Sabor:" + indiceS);
                            sabor = sabores.get(indiceS);
                            s = sabor.toString();
                            Muestra.append(s);
                            break;

                        case "helado_limon":
                            Muestra.setText("No hay más helados refrescantes :(");
                            break;
                    }
                }
            }
        });

        // ↑ Apartado Funcional ↑
    }
}