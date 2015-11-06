/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Modelo.Cine;
import Modelo.Sala;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class BDSala implements IBD {

    private static BDSala instance = null;

    public static BDSala getInstance() {
	if (instance == null) {
	    instance = new BDSala();
	}
	return instance;
    }

    private BDSala() {
    }

    @Override
    public void alta(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	Sala aux = (Sala) dato;
	String insert = "INSERT INTO salas(idSala, NumSala, idCine, Columna, Fila, Estado) VALUES(?,?,?,?,?,?)";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
	    sentencia.setInt(1, aux.getIdSala());
	    sentencia.setInt(2, aux.getNumSala());
	    sentencia.setInt(3, aux.getCine().getIdCine());
	    sentencia.setInt(4, aux.getColumna());
	    sentencia.setInt(5, aux.getFila());
	    sentencia.setBoolean(6, aux.isEstado());
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
	String update = "UPDATE Salas SET Estado= false Where idSala=" + ((Sala) dato).getIdSala();
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
    public void modificar(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	String update = "UPDATE Salas SET NumSala= " + ((Sala) dato).getNumSala() + ",idCine= " + ((Sala) dato).getCine().getIdCine() + ",Columna=" + ((Sala) dato).getColumna() + ",Fila= " + ((Sala) dato).getFila() + " Where idSala=" + ((Sala) dato).getIdSala();
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
	Sala resp = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	oCon.getConexion();
	Cine cine = null;
	String consulta = "SELECT S.idSala,S.NumSala,S.idCine,S.Columna,S.Fila,S.Estado as EstadoSala,C.idCine,C.Nombre,C.Direccion,C.Estado as EstadoCine FROM salas as S \n"
		+ "inner join cines C on C.idCine = S.idCine\n"
		+ "where S.idSala = " + ((Sala) dato).getIdSala();
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		cine = new Cine(rs.getInt("idCine"), rs.getString("Nombre"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		resp = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
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
	Cine cine = null;
	Sala sala = null;
	ArrayList listaSalas = new ArrayList();
	ResultSet rs = null;
	Conexion oCon = new Conexion();
	oCon.getConexion();
	String consulta = "SELECT S.idSala,S.NumSala,S.idCine,S.Columna,S.Fila,S.Estado as EstadoSala,C.idCine,C.Nombre,C.Direccion,C.Estado as EstadoCine FROM salas as S \n"
		+ "inner join cines C on C.idCine = S.idCine\n"
		+ "where S.Estado = 1";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		cine = new Cine(rs.getInt("idCine"), rs.getString("Nombre"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		listaSalas.add(sala);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaSalas;
	}
    }

    public ArrayList listadoXCine(int idCine) throws SQLException {

	Sala resp = null;
	Cine cine = null;

	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaSalas = new ArrayList();
	oCon.getConexion();
	String consulta = "SELECT S.idSala,S.NumSala,S.idCine,S.Columna,S.Fila,S.Estado as EstadoSala,C.idCine,C.Nombre,C.Direccion,C.Estado as EstadoCine FROM salas as S \n"
		+ "inner join cines C on C.idCine = S.idCine\n"
		+ "where S.Estado = 1\n"
		+ "and S.idCine =" + idCine;
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		cine = new Cine(rs.getInt("idCine"), rs.getString("Nombre"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		resp = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		listaSalas.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaSalas;
	}
    }
    
    public ArrayList listadoAdmin() throws SQLException {
	Cine cine = null;
	Sala sala = null;
	ArrayList listaSalas = new ArrayList();
	ResultSet rs = null;
	Conexion oCon = new Conexion();
	oCon.getConexion();
	String consulta = "SELECT S.idSala,S.NumSala,S.idCine,S.Columna,S.Fila,S.Estado as EstadoSala,C.idCine,C.Nombre,C.Direccion,C.Estado as EstadoCine FROM salas as S \n"
		+ "inner join cines C on C.idCine = S.idCine";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		cine = new Cine(rs.getInt("idCine"), rs.getString("Nombre"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		listaSalas.add(sala);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaSalas;
	}
    }

}
