/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flyhigh;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author marqu
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button ingresar;
    @FXML
    private Button aprobar;
    @FXML
    private Button rechazar;
    @FXML
    private Button nPeticion;
    @FXML
    private Button reparacionRegresar;
    @FXML
    private Button admiRegresar;
    @FXML
    private Button ePeticion;
    @FXML
    private Button Enviar;
    @FXML
    private TextField usuario;
    @FXML
    private TextField tipoAvion = new TextField();
    @FXML
    private TextField numAvion = new TextField();
    @FXML
    private ComboBox<String> prevCorre = new ComboBox();
    @FXML
    private ComboBox<String> dificultad = new ComboBox();
    @FXML
    private DatePicker entrada = new DatePicker();
    @FXML
    private DatePicker salida = new DatePicker();
    @FXML
    private TextArea piezas;
    @FXML
    private TextField revisar;
    @FXML
    private TextField numeroPiezas = new TextField();
    @FXML
    private PasswordField contraseña;
    @FXML
    private TableView<Peticion> tablaPeticiones = new TableView();
    @FXML
    private TableColumn<Peticion,Integer> idFolio = new TableColumn();
    @FXML
    private TableColumn<Peticion,String> idAvion = new TableColumn();
    @FXML
    private TableColumn<Peticion,String> tipoCAvion = new TableColumn();
    @FXML
    private TableColumn<Peticion,String> estado = new TableColumn();
    
    private ObservableList<Peticion> listaPeticiones;
    @FXML
    private void funtionIngresar(ActionEvent event) throws IOException{
        if(usuario.getText().equals("")){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Alerta!");
                alert.setHeaderText("No ha ingresado ningun usuario o contraseña");
                alert.setContentText("Estos campos no puede estar vacios");

                alert.showAndWait();
        }else{
                if(usuario.getText().equals("rep") && contraseña.getText().equals("1234")){
                    System.out.println("REPARACION");
                        try {
                            Stage stage = new Stage();            
                            Parent root = FXMLLoader.load(getClass().getResource("Reparacion.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.getIcons().add(new Image("flyhigh/avion.png"));
                            stage.setTitle("FLYHIGH");
                            stage.setResizable(false);
                            stage.show();
                            final Node source = (Node) event.getSource();
                            final Stage stage2 = (Stage) source.getScene().getWindow();
                            stage2.close();
                        } catch (IOException ex) {
                            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }else{
                    if(usuario.getText().equals("jefe") && contraseña.getText().equals("1234")){
                        System.out.println("JEFE");
                            try {
                                Stage stage = new Stage();            
                                Parent root = FXMLLoader.load(getClass().getResource("Revisar.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.getIcons().add(new Image("flyhigh/avion.png"));
                                stage.setTitle("FLYHIGH");
                                stage.setResizable(false);
                                stage.show();
                                final Node source = (Node) event.getSource();
                                final Stage stage2 = (Stage) source.getScene().getWindow();
                                stage2.close();
                            } catch (IOException ex) {
                                //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }else{
                        if(usuario.getText().equals("adm") && contraseña.getText().equals("1234")){
                            System.out.println("Admi");
                            try {
                                Stage stage = new Stage();            
                                Parent root = FXMLLoader.load(getClass().getResource("Admi.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.getIcons().add(new Image("flyhigh/avion.png"));
                                stage.setTitle("FLYHIGH");
                                stage.setResizable(false);
                                stage.show();
                                final Node source = (Node) event.getSource();
                                final Stage stage2 = (Stage) source.getScene().getWindow();
                                stage2.close();
                            } catch (IOException ex) {
                                //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else{
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Ha ingesado un usuario o contraseña incorrecto");
                        alert.setContentText("Vuelva a intentarlo");

                        alert.showAndWait();
                        }

                    }
            }
        }


    }
    @FXML
    private void funtionNuevaPeticion(ActionEvent event){
        try {
            Stage stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("NuevaPeticion.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);                            
            stage.getIcons().add(new Image("flyhigh/avion.png"));
            stage.setTitle("FLYHIGH");
            stage.setResizable(false);
            stage.show();
            final Node source = (Node) event.getSource();
            final Stage stage2 = (Stage) source.getScene().getWindow();
            stage2.close();
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void funtionEditarPeticion(ActionEvent event) throws ClassNotFoundException, SQLException{
        try {
            Stage stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("EditarPeticion.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("flyhigh/avion.png"));
            stage.setTitle("FLYHIGH");
            stage.setResizable(false);
            stage.show();
            final Node source = (Node) event.getSource();
            final Stage stage2 = (Stage) source.getScene().getWindow();
            stage2.close();
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void funtionRegresareparacion(ActionEvent event){
        try {
            Stage stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("Reparacion.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("flyhigh/avion.png"));
            stage.setTitle("FLYHIGH");
            stage.setResizable(false);
            stage.show();
            final Node source = (Node) event.getSource();
            final Stage stage2 = (Stage) source.getScene().getWindow();
            stage2.close();
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void funtionEnviarPeticion(ActionEvent event) throws SQLException, ClassNotFoundException{
        if(dificultad.getSelectionModel().getSelectedItem().toString().equals("")|| prevCorre.getSelectionModel().getSelectedItem().toString().equals("") ||
           tipoAvion.getText().equals("") || numAvion.getText().equals("") || entrada.getValue().toString().isEmpty() ||
           salida.getValue().toString().isEmpty() || piezas.getText().equals("") || numeroPiezas.getText().equals("")){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Alerta!");
                    alert.setHeaderText("No ha ingresado algun campo.");
                    alert.setContentText("Ingrese todos los datos para continuar.");

                    alert.showAndWait();
    }else{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Esta correcta su información?");
            alert.setContentText("Se enviara la información");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flyhigh", "root", "");
                    Statement st = con.createStatement();
                    st.execute("INSERT INTO peticiones (idAvion,tipoAvion,dificultad,reparacion,entrada,salida,numeroPiezas,piezas,revisionAdmi,estado) VALUES ('"+numAvion.getText()+"','"+tipoAvion.getText()+"','"+dificultad.getSelectionModel().getSelectedItem().toString()+"','"+prevCorre.getSelectionModel().getSelectedItem().toString()+"','"+entrada.getValue().toString()+"','"+salida.getValue().toString()+"','"+numeroPiezas.getText()+"','"+piezas.getText()+"','','En proceso')");
                }catch(SQLException e){
                    e.printStackTrace();
                Alert alert1 = new Alert(AlertType.WARNING);
                alert1.setTitle("¡ALERTA!");
                alert1.setHeaderText("No se ha llevado acabo la petición.");
                alert1.setContentText("Reinitente ingresar su petición.");
                }
                   

                try {
                    tablaPeticiones.setItems(listaPeticiones);
                    Stage stage = new Stage();            
                    Parent root = FXMLLoader.load(getClass().getResource("Reparacion.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.getIcons().add(new Image("flyhigh/avion.png"));
                    stage.setTitle("FLYHIGH");
                    stage.setResizable(false);
                    stage.show();
                    final Node source = (Node) event.getSource();
                    final Stage stage2 = (Stage) source.getScene().getWindow();
                    stage2.close();
                } catch (IOException ex) {
                    //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }
    @FXML
    private void funtionCerrarSesion(ActionEvent event){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Quiere cerrar su sesión?");
            alert.setContentText("Se le devolvera a la pantalla de inicio");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                try {
                    Stage stage = new Stage();            
                    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.getIcons().add(new Image("flyhigh/avion.png"));
                    stage.setTitle("FLYHIGH");
                    stage.setResizable(false);
                    stage.show();
                    final Node source = (Node) event.getSource();
                    final Stage stage2 = (Stage) source.getScene().getWindow();
                    stage2.close();
                } catch (IOException ex) {
                    //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Puede poner el numero de piezas con numero o letras.");
        numeroPiezas.setTooltip(tooltip);
        entrada.setValue(LocalDate.now());
        salida.setValue(LocalDate.now());
        dificultad.getItems().clear();
        dificultad.getItems().addAll("Alta","Media","Baja");
        dificultad.setValue("Alta");
        prevCorre.getItems().clear();
        prevCorre.getItems().addAll("Preventivo","Correctivo");
        prevCorre.setValue("Preventivo");
        
         listaPeticiones = FXCollections.observableArrayList();
        
        Peticion.llenarTablasPeticiones(listaPeticiones);
        
        tablaPeticiones.setItems(listaPeticiones);
        
        idFolio.setCellValueFactory(new PropertyValueFactory<Peticion,Integer>("idPeticion"));
        idAvion.setCellValueFactory(new PropertyValueFactory<Peticion,String>("idAvion"));
        tipoCAvion.setCellValueFactory(new PropertyValueFactory<Peticion,String>("tipoAvion"));
        estado.setCellValueFactory(new PropertyValueFactory<Peticion,String>("estado"));
    }    
    
}
