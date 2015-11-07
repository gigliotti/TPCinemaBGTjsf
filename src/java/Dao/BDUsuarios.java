/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class BDUsuarios implements IBD {

    private static BDUsuarios instance = null;

    public static BDUsuarios getInstance() {
	if (instance == null) {
	    instance = new BDUsuarios();
	}
	return instance;
    }

    public BDUsuarios() {

    }

    @Override
    public void alta(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	Usuario aux = (Usuario) dato;
	String insert = "INSERT INTO usuarios(nombre, apellido, DNI, Administrador, User, Pass, email,Telefono,UrlImg) VALUES('" + aux.getNombre() + "','" + aux.getApellido() + "'," + aux.getDni() + "," + aux.isAdministrador() + ",'" + aux.getUser() + "','" + aux.getPass() + "','" + aux.getEmail() + "','" + aux.getTelefono() + "','" + aux.getUrlImg() + "')";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
	    sentencia.execute();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	}
    }

    @Override
    public void baja(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	Usuario aux = (Usuario) dato;
	String borrar = "DELETE from usuarios WHERE idUsuario = " + aux.getId();
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(borrar);
	    sentencia.execute();
	    sentencia.close();
	} catch (SQLException e) {
	    throw e;
	} finally {
	    oCon.close();
	}
    }

    @Override
    public void modificar(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	Usuario aux = (Usuario) dato;
	String update = "UPDATE usuarios SET  nombre = '" + aux.getNombre() + "', apellido = '" + aux.getApellido() + "', DNI = " + aux.getDni() + ", Administrador = " + aux.isAdministrador() + ", User = '" + aux.getUser() + "', Pass = '" + aux.getPass() + "', email = '" + aux.getEmail() + "', Telefono = '" + aux.getTelefono() + "', UrlImg = '" + aux.getUrlImg() + "'  WHERE idUsuario = " + aux.getId();
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
	    sentencia.execute();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	}
    }

    @Override
    public Object existe(Object dato) throws SQLException {
	Usuario resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	oCon.getConexion();
	String insert = "SELECT * FROM usuarios where User='" + ((Usuario) dato).getUser() + "' and Pass='" + ((Usuario) dato).getPass() + "'";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		resp = new Usuario(rs.getInt("idUsuario"), rs.getString("Nombre"), rs.getString("Apellido"), rs.getInt("DNI"), rs.getBoolean("Administrador"), rs.getString("User"), rs.getString("Pass"), rs.getString("email"), rs.getString("Telefono"), rs.getString("UrlImg"));
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return resp;
	}
    }

    @Override
    public ArrayList listado() throws SQLException {
	Usuario resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaUsuarios = new ArrayList();
	oCon.getConexion();
	String consulta = "SELECT * FROM usuarios";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		resp = new Usuario(rs.getInt("idUsuario"), rs.getString("Nombre"), rs.getString("Apellido"), rs.getInt("DNI"), rs.getBoolean("Administrador"), rs.getString("User"), rs.getString("Pass"), rs.getString("email"), rs.getString("Telefono"), rs.getString("UrlImg"));
		listaUsuarios.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaUsuarios;
	}
    }

    public Usuario traePorId(int id) throws SQLException {
	Usuario resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	oCon.getConexion();
	String consulta = "SELECT * FROM usuarios where idUsuario = " + id;
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		resp = new Usuario(rs.getInt("idUsuario"), rs.getString("Nombre"), rs.getString("Apellido"), rs.getInt("DNI"), rs.getBoolean("Administrador"), rs.getString("User"), rs.getString("Pass"), rs.getString("email"), rs.getString("Telefono"), rs.getString("UrlImg"));
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return resp;
	}
    }

    public Usuario validaUser(String user, String email) throws SQLException {
	Usuario resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	String consulta = "SELECT * FROM usuarios where User = '" + user + "' or email = '" + email + "'";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		resp = new Usuario(rs.getInt("idUsuario"), rs.getString("Nombre"), rs.getString("Apellido"), rs.getInt("DNI"), rs.getBoolean("Administrador"), rs.getString("User"), rs.getString("Pass"), rs.getString("email"), rs.getString("Telefono"), rs.getString("UrlImg"));
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return resp;
	}
    }

}
