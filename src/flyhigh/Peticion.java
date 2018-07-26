/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flyhigh;

import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author marqu
 */
public class Peticion {
    private final SimpleIntegerProperty idPeticion;
    private final SimpleStringProperty idAvion;
    private final SimpleStringProperty tipoAvion;
    private final SimpleStringProperty estado;
    
    private Peticion(int peticion, String avion, String tipo,String estado) {
        this.idPeticion = new SimpleIntegerProperty(peticion);
        this.idAvion = new SimpleStringProperty(avion);
        this.tipoAvion = new SimpleStringProperty(tipo);
        this.estado = new SimpleStringProperty(estado);
}

    public int getIdPeticion() {
        return idPeticion.get();
    }
    public void setIdPeticion(int fName) {
        idPeticion.set(fName);
    }
        
    public String getIdAvion() {
        return idAvion.get();
    }
    public void setIdAvion(String fName) {
        idAvion.set(fName);
    }
    
    public String getTipoAvion() {
        return tipoAvion.get();
    }
    public void setTipoAvion(String fName) {
        tipoAvion.set(fName);
    }
    public String getEstado() {
        return estado.get();
    }
    public void setEstado(String fName) {
        estado.set(fName);
    }
    
    public static void llenarTablasPeticiones(ObservableList<Peticion> listaPeticiones){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/flyhigh", "root", "");
            Statement st = con.createStatement();
            String sql = "SELECT * FROM peticiones";
            ResultSet rs = st.executeQuery(sql);
            try {
                while (rs.next()){
                    listaPeticiones.add(
                            new Peticion(
                                    rs.getInt("idPeticion"),
                                    rs.getString("idAvion"),
                                    rs.getString("entrada"),
                                    rs.getString("estado")
                            )
                    );
                }
                con.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
