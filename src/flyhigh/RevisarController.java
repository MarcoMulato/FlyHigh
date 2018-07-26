/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flyhigh;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marqu
 */
public class RevisarController implements Initializable {    
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
    private TextField prevCorre;
    @FXML
    private TextField dificultad;
    @FXML
    private TextField entrada;
    @FXML
    private TextField salida;
    @FXML
    private TextArea piezas;
    @FXML
    private TextArea piezasAdm;
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
    private void funtionBuscarPeticion(ActionEvent event) throws ClassNotFoundException{
        if(TextEditar.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("¡Alerta!");
            alert.setHeaderText("No ha ingresado ningun folio de petición.");
            alert.setContentText("Para buscar alguna petción ingrese su número.");

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
                dificultad.setText(rs.getString("dificultad"));
                prevCorre.setText(rs.getString("reparacion"));
                entrada.setText(rs.getString("entrada"));
                salida.setText(rs.getString("salida"));
                piezasAdm.setText(rs.getString("revisionAdmi"));
                numeroPiezas.setText(rs.getString("numeroPiezas"));
            }catch(SQLException ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("¡Alerta!");
                    alert.setHeaderText("No se encuentra en numero de petición.");
                    alert.setContentText("Ingrese un número valido.");

                    alert.showAndWait();
            }
        }
    }
    @FXML
    private void funtionAprobarPeticion(ActionEvent event) throws ClassNotFoundException{
        if(TextEditar.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alerta!");
                    alert.setHeaderText("No ha ingresado la petición a buscar.");
                    alert.setContentText("Ingrese algún número de petición.");

                    alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Esta seguro de aprobar esta petición?");
            alert.setContentText("Se enviara la información y no se prodrá modificar.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try{                
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flyhigh", "root", "");
                    Statement st = con.createStatement();
                    String sql = "UPDATE peticiones SET estado='Aprobado' WHERE idPeticion=" + TextEditar.getText();
                    System.out.println("SQL: " + sql);
                    
                    String sql2 ="SELECT estado FROM peticiones WHERE idPeticion=" + TextEditar.getText();
                    ResultSet rs = st.executeQuery(sql2);
                    rs.next();
                    String resultado = rs.getString("estado");
                    if(resultado.equals("Aprobado")){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.setTitle("Alerta!");
                        alert1.setHeaderText("Esta petición ha sido aprobada.");
                        alert1.setContentText("No se necesita aprobarla de nuevo.");

                        alert1.showAndWait();
                    }else{
                        if(resultado.equals("Rechazado")){
                        Alert alert2 = new Alert(Alert.AlertType.WARNING);
                        alert2.setTitle("Alerta!");
                        alert2.setHeaderText("Esta petición ha sido rechazada.");
                        alert2.setContentText("No se necesita rechazarla de nuevo.");

                        alert2.showAndWait();
                        }else{
                            st.execute(sql);
                        }
                    }
                    tablaPeticiones.getItems().removeAll(listaPeticiones);
                    Peticion.llenarTablasPeticiones(listaPeticiones);
                    con.close();
                }catch(SQLException ex){

                }
            }
        }
    }
    @FXML
    private void funtionRechazarPeticion(ActionEvent event) throws ClassNotFoundException{
        if(TextEditar.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Alerta!");
                    alert.setHeaderText("No ha ingresado la petición a buscar.");
                    alert.setContentText("Ingrese algún número de petición.");

                    alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Esta seguro de rechazar esta petición?");
            alert.setContentText("Se enviara la información y no se prodrá modificar.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try{                
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flyhigh", "root", "");
                    Statement st = con.createStatement();
                    String sql = "UPDATE peticiones SET estado='Rechazado' WHERE idPeticion=" + TextEditar.getText();
                    System.out.println("SQL: " + sql);
                    
                    String sql2 ="SELECT estado FROM peticiones WHERE idPeticion=" + TextEditar.getText();
                    ResultSet rs = st.executeQuery(sql2);
                    rs.next();
                    String resultado = rs.getString("estado");
                    if(resultado.equals("Aprobado")){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert1.setTitle("Alerta!");
                        alert1.setHeaderText("Esta petición ha sido aprobada.");
                        alert1.setContentText("No se necesita aprobarla de nuevo.");

                        alert1.showAndWait();
                    }else{
                        if(resultado.equals("Rechazado")){
                        Alert alert2 = new Alert(Alert.AlertType.WARNING);
                        alert2.setTitle("Alerta!");
                        alert2.setHeaderText("Esta petición ha sido rechazada.");
                        alert2.setContentText("No se necesita rechazarla de nuevo.");

                        alert2.showAndWait();
                        }else{
                            st.execute(sql);
                        }
                    }
                    tablaPeticiones.getItems().removeAll(listaPeticiones);
                    Peticion.llenarTablasPeticiones(listaPeticiones);
                    con.close();
                    rs.close();
                }catch(SQLException ex){

                }
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

        listaPeticiones = FXCollections.observableArrayList();
        
        Peticion.llenarTablasPeticiones(listaPeticiones);
        
        tablaPeticiones.setItems(listaPeticiones);
        
        idFolio.setCellValueFactory(new PropertyValueFactory<Peticion,Integer>("idPeticion"));
        idAvion.setCellValueFactory(new PropertyValueFactory<Peticion,String>("idAvion"));
        tipoCAvion.setCellValueFactory(new PropertyValueFactory<Peticion,String>("tipoAvion"));
        estado.setCellValueFactory(new PropertyValueFactory<Peticion,String>("estado"));
    }    
    
}
