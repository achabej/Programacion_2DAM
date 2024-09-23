
package adrianchao_p01_java_swing;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;

public class GesEmpresa extends JFrame{
    //Lista de empleados
    public static ArrayList<Empleado> ListaE = new ArrayList<Empleado>();
    public int currentEmpl = 0;
    //Cadenas para las etiquetas
    private static String nombreString = "Nombre: ";
    private static String fechaNacString = "Fecha Nacimiento: ";
    private static String salarioString = "Salario: ";
    
    //Etiquetas para identificar los campos de texto
    private JLabel nombreLabel;
    private JLabel fechaNacLabel;
    private JLabel salarioLabel;
    
    //Text fields para introducir números
    private TextField nombreField;
    private TextField fechaNacField;
    private TextField salarioField;
    
    //Botones
    JButton begginingButton = new JButton("<<--");
    JButton endButton = new JButton("-->>");
    JButton leftButton = new JButton("<-");
    JButton rightButton = new JButton("->");
    
    public static void main(String[] args) {
       try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }
        
        try{
            //Empleados de prueba
            ListaE.add(new Empleado("Jose", "05/07/2002", "1200"));
            ListaE.add(new Empleado("Paco", "08/02/2002", "2200"));
            ListaE.add(new Empleado("Maria", "24/04/2005", "1300"));
            ListaE.add(new Empleado("Javier", "12/02/2002", "1500"));
            ListaE.add(new Empleado("Miguel", "03/09/2003", "1400"));

             //Crea el contenedor de más alto nivel y le añade contenido.
            JFrame frame = new JFrame("Gestion Empresa");

            // Llamada la constructor por defecto "GesEmpresa(){};".
            final GesEmpresa app = new GesEmpresa();
            
            //Lo que pasa si cerramos la ventana
             app.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        app.pack();
        app.setVisible(true);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    //Metodo para crear los botones de la intefaz
    public Component CreateComponent(){
        begginingButton.setMnemonic(KeyEvent.VK_I);  
        endButton.setMnemonic(KeyEvent.VK_I);  
        leftButton.setMnemonic(KeyEvent.VK_I);  
        rightButton.setMnemonic(KeyEvent.VK_I);  

        begginingButton.addActionListener(new ActionListener() {  // Boton principio
            public void actionPerformed(ActionEvent e) {
                    currentEmpl = 0;
                    CheckButtons(); //Metodo para cambiar el estado de los botones
                    ChangeValues(); //Metodo para cambiar los valores que se muestran en los TextFields
            }
            });
        
        endButton.addActionListener(new ActionListener() {  // Boton final
            public void actionPerformed(ActionEvent e) {
                    currentEmpl = ListaE.size()-1;
                    CheckButtons();
                    ChangeValues();
            }
            });
        
        //Los botones izquierda y derecha comprueban el texto que llevan actualmente para realizar
        //Diferentes opciones, el texto de los botones lo controla CheckButtons()
        leftButton.addActionListener(new ActionListener() {  // Boton izquierda
            public void actionPerformed(ActionEvent e) {
                if(leftButton.getText() == "<-"){
                    currentEmpl--;
                    ChangeValues();
                    CheckButtons();
                }
                else{
                    //Añadimos un nuevo empleado
                    try{
                        ListaE.add(new Empleado(nombreField.getText(), fechaNacField.getText(), salarioField.getText()));
                        currentEmpl++; 
                        JOptionPane.showMessageDialog(null, "Usuario añadido correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

                        ChangeValues();
                        CheckButtons();
                    }
                    catch(ParseException ex){
                        //Excepcion para cuando no se añaden los datos necesarios
                        if(nombreField.getText().isEmpty() || fechaNacField.getText().isEmpty() || salarioField.getText().isEmpty())
                            JOptionPane.showMessageDialog(null, "No se ha introducido los datos necesarios", "Error", JOptionPane.INFORMATION_MESSAGE);
                        else
                            //Excepcion de fecha incorrecta
                            JOptionPane.showMessageDialog(null, "La fecha introducida tiene el formato incorrecto (dd/mm/yyyy)", "Error", JOptionPane.INFORMATION_MESSAGE);

                    }
                    catch(NumberFormatException ex){
                        //Excepcion de formato salario
                        JOptionPane.showMessageDialog(null, "El salario no es un número o no tiene el formato correcto", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            });
        
        rightButton.addActionListener(new ActionListener() {  // Boton derecha
            public void actionPerformed(ActionEvent e) {
                if(rightButton.getText() == "->"){
                    currentEmpl++;
                    ChangeValues();
                    CheckButtons();
                    
                }
                else if(rightButton.getText() == "+")
                        ActivateNewEmpleInterface();
                else if(rightButton.getText() == "Cancelar"){
                    currentEmpl = 0; 
                    ChangeValues();
                    CheckButtons();
                }
            }
        });
        
        //Creamos el panel de los botones
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(1, 0));
        
        pane.add(begginingButton);
        pane.add(leftButton);
        pane.add(rightButton);
        pane.add(endButton);
        
        return pane;    
    }
    
    public GesEmpresa(){
        //Crea las etiquetas.
        nombreLabel = new JLabel(nombreString);
        fechaNacLabel = new JLabel(fechaNacString);
        salarioLabel = new JLabel(salarioString);

        //Campos de texto
        nombreField = new TextField(10);
        nombreField.setText(ListaE.get(currentEmpl).getNombre());
        
        fechaNacField = new TextField(10);
        fechaNacField.setText(ListaE.get(currentEmpl).getFechaNac());
        
        salarioField = new TextField(10);
        salarioField.setText(ListaE.get(currentEmpl).getSalario().toString());
    
        Component c = null;
        
        //Añadimos los label a un panel
        JPanel labelPane = new JPanel();
        labelPane.setLayout(new GridLayout(0, 1));
        labelPane.add(nombreLabel);
        labelPane.add(fechaNacLabel);
        labelPane.add(salarioLabel);
        
        //Añadimos los field a un panel
        JPanel fieldPane = new JPanel();
        fieldPane.setLayout(new GridLayout(0, 1));
        fieldPane.add(nombreField);
        fieldPane.add(fechaNacField);
        fieldPane.add(salarioField);
             
        c = CreateComponent();
       
        //Creamos el panel
        JPanel contentPane = new JPanel();
        contentPane.setBorder(
               BorderFactory.createEmptyBorder(60, 80, 60, 80));
        contentPane.setLayout(new BorderLayout());
        contentPane.add(labelPane, BorderLayout.CENTER);
        contentPane.add(fieldPane, BorderLayout.EAST);
        contentPane.add(c, BorderLayout.SOUTH);//Añadimos los botones al sur del panel
        
        setContentPane(contentPane); 
        
        CheckButtons(); //Actualiza los botones al crearse el panel
    }
    //Metodo para activar los TextFields y que el usuario pueda introducir un nuevo empleado
    public void ActivateNewEmpleInterface(){
        nombreField.setEditable(true);
        fechaNacField.setEditable(true);
        salarioField.setEditable(true);
        
        //Limpieza de TextFields
        nombreField.setText("");
        fechaNacField.setText("");
        salarioField.setText("");
        
        //Renombramos los botones izquierdo y derecho
        leftButton.setText("Añadir");
        rightButton.setText("Cancelar");
        
        //Desactivamos los botones principio y fin
        begginingButton.setEnabled(false);
        endButton.setEnabled(false);
    }
    
    //Metodo para actualizar los valores al empleado actual de la lista
    private void ChangeValues(){
        nombreField.setText(ListaE.get(currentEmpl).getNombre());
        fechaNacField.setText(ListaE.get(currentEmpl).getFechaNac());
        salarioField.setText(ListaE.get(currentEmpl).getSalario().toString());
    }
    
    //Metodo para actualizar el estado de los botones dependiendo de nuestra posicion en la lista
    //Este metodo se llama cuando se muestran datos y no cuando se vaya a añadir un usuario
    private void CheckButtons(){
        //Activa o desactiva los botones si esta al principio o al final
        if(currentEmpl != 0){
            leftButton.setEnabled(true);
            begginingButton.setEnabled(true);
        }
        else{
            leftButton.setEnabled(false);
            begginingButton.setEnabled(false);
            rightButton.setEnabled(true);
        }
        if(currentEmpl != ListaE.size()-1)
            endButton.setEnabled(true);
        else
            endButton.setEnabled(false);

        //Cambia el texto del boton derecho si nos encontramos al final 
        if(currentEmpl == ListaE.size()-1)
            rightButton.setText("+");
        else
            rightButton.setText("->");

        leftButton.setText("<-");
        
        //Impide escribir en los recuadros cuando se muestren datos
        nombreField.setEditable(false);
        fechaNacField.setEditable(false);
        salarioField.setEditable(false);
    }
}
