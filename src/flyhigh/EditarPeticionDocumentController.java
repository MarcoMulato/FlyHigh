/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flyhigh;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marqu
 */
public class EditarPeticionDocumentController implements Initializable {
    @FXML
    private Button editar; 
    @FXML
    private TextField TextEditar;
    @FXML
    private TextField numeroPiezas;
    @FXML
    private TextField tipoAvion;
    @FXML
    private TextField numAvion;
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
    private void funtionRegresareparacion(ActionEvent event){
        try {
            Stage stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("Reparacion.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
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
           tipoAvion.getText().equals("") || numAvion.getText().equals("") || entrada.getValue().toString().equals("") ||
           salida.getValue().toString().equals("") || piezas.getText().equals("") || numeroPiezas.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alerta!");
                    alert.setHeaderText("No ha ingresado algun campo.");
                    alert.setContentText("Ingrese todos los datos para continuar.");

                    alert.showAndWait();
    }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Esta correcta su información?");
            alert.setContentText("Se enviara la información");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flyhigh", "root", "");
                    Statement st = con.createStatement();
                    String sql = "UPDATE peticiones SET idAvion='"+numAvion.getText()+"',tipoAvion='"+tipoAvion.getText()
                            +"',dificultad='"+dificultad.getSelectionModel().getSelectedItem().toString()+"',reparacion='"
                            +prevCorre.getSelectionModel().getSelectedItem().toString()+"',entrada='"+entrada.getValue().toString()+
                            "',salida='"+salida.getValue().toString()+"',numeroPiezas='"+numeroPiezas.getText()+"',piezas='"
                            +piezas.getText()+"' WHERE idPeticion = '"+TextEditar.getText()+"'";
                    System.out.println("SQL: " + sql);
                    st.execute(sql);
                
                
                }catch(SQLException e){
                    e.printStackTrace();
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
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
    private void funtionEditarPeticion(ActionEvent event) throws ClassNotFoundException, SQLException{
        if(TextEditar.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("¡Alerta!");
                alert.setHeaderText("No ha ingresado ningun folio de petición.");
                alert.setContentText("Para editar alguna petción ingrese su número.");

                alert.showAndWait();
        }else{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flyhigh", "root", "");
                Statement st = con.createStatement();
                String sql = "SELECT * FROM `peticiones` WHERE idPeticion = "+TextEditar.getText();
                System.out.println("SQL: " + sql);
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                System.out.println("idAvion: " + rs.getString("idAvion"));
                numAvion.setText(rs.getString("idAvion"));
                tipoAvion.setText(rs.getString("tipoAvion"));
                piezas.setText(rs.getString("piezas"));
            }catch(SQLException ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("¡Alerta!");
                    alert.setHeaderText("No se encuentra en numero de petición.");
                    alert.setContentText("Ingrese un número valido.");

                    alert.showAndWait();
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
