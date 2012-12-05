/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BRL;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Miguel
 */
public class brcGenerador {
    
    private File f;
    
    private Connection cConexion;
    
    public Connection getcConexion() {        
        return cConexion;
    }

    public void setcConexion(Connection cConexion) {
        this.cConexion = cConexion;
    }
    
    public brcGenerador(){
        this.cConexion = null;
    }
    
    public ResultSet getTablas(){
        Statement stmt;
        try {
            stmt = this.cConexion.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            rs = stmt.executeQuery("SHOW TABLES");
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(brcGenerador.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    private ResultSet getCampos(String sTabla){
        Statement stmt;
        try {
            stmt = this.cConexion.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            rs = stmt.executeQuery("DESC " + sTabla);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(brcGenerador.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }   
    }
    
    public String getClase(String sTabla, String sNombreClase){
        String sClase = new String();
        sClase += "<?php \n ";
        sClase += "\t class " + sNombreClase + "{ \n ";        
        try {            
            ResultSet rs = this.getCampos(sTabla);
            while(rs.next()){
                sClase += "\n ";
                sClase += "\t private $" + rs.getString("Field") + "; ";
            }
            sClase += "\n ";
            rs.beforeFirst();
            while(rs.next()){                
                sClase += "\t function set_" + rs.getString("Field") + "($" + rs.getString("Field") + ") { \n ";
                sClase +=  "\t\t $this->" + rs.getString("Field") + " = $" + rs.getString("Field") + "; \n ";
                sClase += "\t }";
                sClase += "\n ";
                sClase += "\t function get_" + rs.getString("Field") + "() { \n ";
                sClase +=  "\t\t return $this->" + rs.getString("Field") + "; \n ";
                sClase += "\t }";
                sClase += "\n ";
            }
            sClase += "\t \n }";
            sClase += "?>";
            sClase.trim();
            return sClase;
        } catch (SQLException ex) {
            Logger.getLogger(brcGenerador.class.getName()).log(Level.SEVERE, null, ex);
            return sClase;
        }
    }
           
    public void GenerarClases(File f){        
        this.f = f;
        ResultSet rs = this.getTablas();
        try {            
            while(rs.next()){
                //this.GuardarClase(rs.getString(1), this.getClase(rs.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(brcGenerador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
