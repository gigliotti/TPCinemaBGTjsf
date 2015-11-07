/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge
 */
public class Conexion {

    private String driver;
    private String servidor;
    private String url;
    private int puerto;
    private String usuario;
    private String pass;
    private String bd;
    private Connection conexion;

    public Conexion() {
	usuario = "root";
	pass = "";
	servidor = "localhost";
	puerto = 3306;
	bd = "bdcinemacenterbgt";
	url = "jdbc:mysql://" + servidor + ":" + puerto + "/" + bd;
	driver = "com.mysql.jdbc.Driver";
    }

    public Connection getConexion() {
	try {
	    Class.forName(driver);
	    conexion = DriverManager.getConnection(url, usuario, pass);
	} catch (ClassNotFoundException ex) {
	    JOptionPane.showMessageDialog(null, ex, "Error1 en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
	    conexion = null;
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, ex, "Error2 en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
	    conexion = null;
	} catch (Exception ex) {
	    JOptionPane.showMessageDialog(null, ex, "Error3 en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
	    conexion = null;
	} finally {
	    return conexion;
	}
    }

    public void close() throws SQLException {
	if (!conexion.isClosed()) {
	    conexion.close();
	}
    }
}
