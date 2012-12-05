/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.*;

/**
 *
 * @author Miguel
 */
public class dacConexion {

    private String sBaseDatos;    
    private String sUsuario;
    private String sContrasena;
    private String sURL;
    private Connection cConexion;

    public void setsBaseDatos(String sBaseDatos) {
        this.sBaseDatos = sBaseDatos;
    }

    public void setsContrasena(String sContrasena) {
        this.sContrasena = sContrasena;
    }

    public void setsURL(String sURL) {
        this.sURL = sURL;
    }

    public void setsUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }
    
    public String getsBaseDatos() {
        return sBaseDatos;
    }

    public String getsContrasena() {
        return sContrasena;
    }

    public String getsURL() {
        return sURL;
    }

    public String getsUsuario() {
        return sUsuario;
    }
    
    /** Constructor de DbConnection */
    public dacConexion() {
        this.sBaseDatos = new String();
        this.sUsuario = new String();
        this.sContrasena = new String();
        this.sURL = new String();
        this.cConexion = null;
    }

    /** Permite retornar la conexión */
    public Connection getConnection() {
        return this.cConexion;
    }

    public boolean Conectar() {
        boolean bConexion = false;
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            cConexion = DriverManager.getConnection(this.sURL + this.sBaseDatos, this.sUsuario, this.sContrasena);
            if (cConexion != null) {
                System.out.println("Conección a base de datos " + this.sBaseDatos + ". listo");
                bConexion = true;
            }
        } catch (SQLException e) {
            System.out.println(e);
            bConexion = false;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            bConexion = false;
        }
        return bConexion;
    }

    public void Desconectar() {
        this.cConexion = null;
        System.out.println("La conexion a la  base de datos " + this.sBaseDatos + " a terminado");
    }
}
