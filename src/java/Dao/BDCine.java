/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Modelo.Cine;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author herna
 */
public class BDCine implements IBD {

    private static BDCine instance = null;

    public static BDCine getInstance() {
	if (instance == null) {
	    instance = new BDCine();
	}
	return instance;
    }

    private BDCine() {
    }

    @Override
    public void alta(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	Cine aux = (Cine) dato;
	String insert = "INSERT INTO cines(nombre, direccion, estado) VALUES(?,?,?)";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
	    sentencia.setString(1, aux.getNombre());
	    sentencia.setString(2, aux.getDireccion());
	    sentencia.setBoolean(3, aux.isEstado());
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
	String consulta = "UPDATE Cines set Estado = 0 where idCine =" + ((int) dato);
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    sentencia.execute();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	}
    }

    @Override
    public void modificar(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	Cine aux = (Cine) dato;
	String update = "UPDATE cines SET  nombre = '" + aux.getNombre() + "', direccion = '" + aux.getDireccion() + "', estado = " + aux.isEstado() + " WHERE idCine = " + aux.getIdCine();
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
	Cine resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	oCon.getConexion();
	String consulta = "SELECT * FROM cines where idCine =" + ((Cine) dato).getIdCine();
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		resp = new Cine(rs.getInt("idCine"), rs.getString("Nombre"), rs.getString("Direccion"), rs.getBoolean("Estado"));
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

	Cine resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaPeliculas = new ArrayList();
	oCon.getConexion();
	String consulta = "SELECT * FROM cines";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		resp = new Cine(rs.getInt("idCine"), rs.getString("Nombre"), rs.getString("Direccion"), rs.getBoolean("Estado"));
		listaPeliculas.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaPeliculas;
	}
    }

    public ArrayList listadoActivos() throws SQLException {

	Cine resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaPeliculas = new ArrayList();
	oCon.getConexion();
	String consulta = "SELECT * FROM cines where Estado = 1";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		resp = new Cine(rs.getInt("idCine"), rs.getString("Nombre"), rs.getString("Direccion"), rs.getBoolean("Estado"));
		listaPeliculas.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaPeliculas;
	}
    }

}
