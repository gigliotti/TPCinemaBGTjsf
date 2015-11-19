/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Modelo.Cine;
import Modelo.Funcion;
import Modelo.Pelicula;
import Modelo.Sala;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jorge
 */
public class BDFunciones implements IBD {

    private static BDFunciones instance = null;

    public static BDFunciones getInstance() {
	if (instance == null) {
	    instance = new BDFunciones();
	}
	return instance;
    }

    public BDFunciones() {
    }

    @Override
    public void alta(Object dato) throws SQLException {
	Conexion oCon = new Conexion();
	oCon.getConexion();
	Funcion aux = (Funcion) dato;
	String insert = "INSERT INTO funciones(FechaYHora, Duracion, Precio, idSala, idPelicula,Estado) VALUES(?,?,?,?,?,?)";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
	    sentencia.setTimestamp(1, new Timestamp(aux.getFechaYHora().getTime()));
	    sentencia.setInt(2, aux.getDuracion());
	    sentencia.setFloat(3, aux.getPrecio());
	    sentencia.setInt(4, aux.getSala().getIdSala());
	    sentencia.setInt(5, aux.getPelicula().getIdPelicula());
	    sentencia.setBoolean(6, true);
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
	String consulta = "UPDATE funciones set Estado = false where idFuncion =" + (int) dato;
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
	Funcion aux = (Funcion) dato;
	String update = "UPDATE funciones SET FechaYHora = '" + new Timestamp(aux.getFechaYHora().getTime()) + "', Duracion = '" + aux.getDuracion() + "', Precio = " + aux.getPrecio() + ", idSala = " + aux.getSala().getIdSala() + ", idPelicula = " + aux.getPelicula().getIdPelicula() + ", Estado = true WHERE idFuncion = " + aux.getIdFuncion();
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
	Funcion resp = null;
	Sala sala = null;
	Cine cine = null;
	Pelicula peli = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	oCon.getConexion();
	Funcion fun = (Funcion) dato;
	String consulta = "SELECT P.idPelicula,P.Nombre as NombrePeli,P.Director,P.DuracionPeli,P.Descripcion,P.Estado as EstadoPeli, P.UrlImagen,C.idCine,C.Nombre as NombreCine,C.Direccion,C.Estado as EstadoCine,S.idSala,S.NumSala,S.Columna,S.Fila,S.Estado as EstadoSala,funciones.idFuncion,funciones.FechaYHora,funciones.Duracion as DuracionFuncion,funciones.Precio,funciones.Estado as EstadoFuncion FROM funciones inner join salas S on S.idSala = funciones.idSala inner join cines C on C.idCine = S.idCine inner join peliculas P on P.idPelicula = funciones.idPelicula where funciones.FechaYHora >= now() and funciones.idFuncion =" + fun.getIdFuncion();
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		peli = new Pelicula(rs.getInt("idPelicula"), rs.getString("NombrePeli"), rs.getString("Director"), rs.getInt("DuracionPeli"), rs.getString("Descripcion"), rs.getBoolean("EstadoPeli"), rs.getString("UrlImagen"));
		cine = new Cine(rs.getInt("idCine"), rs.getString("NombreCine"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		Date newDate = rs.getTimestamp("FechaYHora");
		resp = new Funcion(rs.getInt("idFuncion"), newDate, rs.getInt("DuracionFuncion"), rs.getFloat("Precio"), sala, peli, rs.getBoolean("EstadoFuncion"));
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
	Funcion resp = null;
	Sala sala = null;
	Cine cine = null;
	Pelicula peli = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaFunciones = new ArrayList();
	oCon.getConexion();
	String consulta = "SELECT P.idPelicula,P.Nombre as NombrePeli,P.Director,P.DuracionPeli,P.Descripcion,P.Estado as EstadoPeli, P.UrlImagen,C.idCine,C.Nombre as NombreCine,C.Direccion,C.Estado as EstadoCine,S.idSala,S.NumSala,S.Columna,S.Fila,S.Estado as EstadoSala,funciones.idFuncion,funciones.FechaYHora,funciones.Duracion as DuracionFuncion,funciones.Precio,funciones.Estado as EstadoFuncion FROM funciones inner join salas S on S.idSala = funciones.idSala inner join cines C on C.idCine = S.idCine inner join peliculas P on P.idPelicula = funciones.idPelicula where funciones.FechaYHora >= now() and funciones.Estado = 1";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		peli = new Pelicula(rs.getInt("idPelicula"), rs.getString("NombrePeli"), rs.getString("Director"), rs.getInt("DuracionPeli"), rs.getString("Descripcion"), rs.getBoolean("EstadoPeli"), rs.getString("UrlImagen"));
		cine = new Cine(rs.getInt("idCine"), rs.getString("NombreCine"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		Date newDate = rs.getTimestamp("FechaYHora");
		resp = new Funcion(rs.getInt("idFuncion"), newDate, rs.getInt("DuracionFuncion"), rs.getFloat("Precio"), sala, peli, rs.getBoolean("EstadoFuncion"));
		listaFunciones.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaFunciones;
	}
    }

    public ArrayList validaFuncion(Object dato) throws SQLException {
	Funcion resp = null;
	Sala sala = null;
	Cine cine = null;
	Pelicula peli = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaFunciones = new ArrayList();
	oCon.getConexion();
	Funcion fun = (Funcion) dato;
	String consulta = "SELECT P.idPelicula,P.Nombre as NombrePeli,P.Director,P.DuracionPeli,P.Descripcion,P.Estado as EstadoPeli, P.UrlImagen,C.idCine,C.Nombre as NombreCine,\n"
		+ "C.Direccion,C.Estado as EstadoCine,S.idSala,S.NumSala,S.Columna,S.Fila,S.Estado as EstadoSala,F.idFuncion,F.FechaYHora,\n"
		+ "F.Duracion as DuracionFuncion,F.Precio,F.Estado as EstadoFuncion \n"
		+ "FROM funciones as F\n"
		+ "inner join salas S on S.idSala = F.idSala \n"
		+ "inner join cines C on C.idCine = S.idCine \n"
		+ "inner join peliculas P on P.idPelicula = F.idPelicula \n"
		+ "where F.Estado = 1\n"
		+ "and F.idSala = " + fun.getSala().getIdSala() + "\n"
		+ "and F.idFuncion <> " + fun.getIdFuncion() + "\n"
		+ "and (TIMESTAMPADD(MINUTE, F.Duracion, F.FechaYHora) >= '" + new Timestamp(fun.getFechaYHora().getTime()) + "' and TIMESTAMPADD(MINUTE, " + fun.getDuracion() + ", '" + new Timestamp(fun.getFechaYHora().getTime()) + "') >= F.FechaYHora);";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		peli = new Pelicula(rs.getInt("idPelicula"), rs.getString("NombrePeli"), rs.getString("Director"), rs.getInt("DuracionPeli"), rs.getString("Descripcion"), rs.getBoolean("EstadoPeli"), rs.getString("UrlImagen"));
		cine = new Cine(rs.getInt("idCine"), rs.getString("NombreCine"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		Date newDate = rs.getTimestamp("FechaYHora");
		resp = new Funcion(rs.getInt("idFuncion"), newDate, rs.getInt("DuracionFuncion"), rs.getFloat("Precio"), sala, peli, rs.getBoolean("EstadoFuncion"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		listaFunciones.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaFunciones;
	}
    }

    public ArrayList listadoXPelicula(int idPelicula) throws SQLException {
	Funcion resp = null;
	Sala sala = null;
	Cine cine = null;
	Pelicula peli = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaFunciones = new ArrayList();
	oCon.getConexion();
	String consulta = "SELECT P.idPelicula,P.Nombre as NombrePeli,P.Director,P.DuracionPeli,P.Descripcion,P.Estado as EstadoPeli, P.UrlImagen,C.idCine,C.Nombre as NombreCine,C.Direccion,C.Estado as EstadoCine,S.idSala,S.NumSala,S.Columna,S.Fila,S.Estado as EstadoSala,funciones.idFuncion,funciones.FechaYHora,funciones.Duracion as DuracionFuncion,funciones.Precio,funciones.Estado as EstadoFuncion FROM funciones inner join salas S on S.idSala = funciones.idSala inner join cines C on C.idCine = S.idCine inner join peliculas P on P.idPelicula = funciones.idPelicula where funciones.FechaYHora >= now() and funciones.Estado = 1 and funciones.IdPelicula = " + idPelicula;
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		peli = new Pelicula(rs.getInt("idPelicula"), rs.getString("NombrePeli"), rs.getString("Director"), rs.getInt("DuracionPeli"), rs.getString("Descripcion"), rs.getBoolean("EstadoPeli"), rs.getString("UrlImagen"));
		cine = new Cine(rs.getInt("idCine"), rs.getString("NombreCine"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		Date newDate = rs.getTimestamp("FechaYHora");
		resp = new Funcion(rs.getInt("idFuncion"), newDate, rs.getInt("DuracionFuncion"), rs.getFloat("Precio"), sala, peli, rs.getBoolean("EstadoFuncion"));
		listaFunciones.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaFunciones;
	}
    }

    public ArrayList listadoBusquedaAvanzada(int idCine, int idSala, int idPelicula, int numeroButacas) throws SQLException {
	Funcion resp = null;
	Sala sala = null;
	Cine cine = null;
	Pelicula peli = null;
	Conexion oCon = new Conexion();
	ResultSet rs = null;
	ArrayList listaFunciones = new ArrayList();
	oCon.getConexion();
	String consulta = "SELECT DISTINCT P.idPelicula,P.Nombre as NombrePeli,P.Director,P.DuracionPeli,P.Descripcion,P.Estado as EstadoPeli, P.UrlImagen,C.idCine,\n"
		+ "C.Nombre as NombreCine,C.Direccion,C.Estado as EstadoCine,S.idSala,S.NumSala,S.Columna,S.Fila,S.Estado as EstadoSala,\n"
		+ "F.idFuncion,F.FechaYHora,F.Duracion as DuracionFuncion,F.Precio,F.Estado as EstadoFuncion FROM funciones as F\n"
		+ " inner join salas S on S.idSala = F.idSala\n"
		+ " inner join cines C on C.idCine = S.idCine\n"
		+ " inner join peliculas P on P.idPelicula = F.idPelicula \n"
		+ " left outer join reservas R on R.idFuncion = F.idFuncion\n"
		+ " where F.FechaYHora >= now() and F.Estado = 1  \n"
		+ " and (S.IdCine = " + idCine + " or " + idCine + " = 0)\n"
		+ " and (S.IdSala = " + idSala + " or " + idSala + " = 0)\n"
		+ " and (F.IdPelicula = " + idPelicula + " or " + idPelicula + " = 0)\n"
		+ " and (S.Fila * S.Columna - (select count(*) from reservas where idFuncion = F.idFuncion) >= " + numeroButacas + " or " + numeroButacas + " = 0)";
	try {
	    PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(consulta);
	    rs = sentencia.executeQuery();
	    while (rs.next()) {
		peli = new Pelicula(rs.getInt("idPelicula"), rs.getString("NombrePeli"), rs.getString("Director"), rs.getInt("DuracionPeli"), rs.getString("Descripcion"), rs.getBoolean("EstadoPeli"), rs.getString("UrlImagen"));
		cine = new Cine(rs.getInt("idCine"), rs.getString("NombreCine"), rs.getString("Direccion"), rs.getBoolean("EstadoCine"));
		sala = new Sala(rs.getInt("idSala"), rs.getInt("NumSala"), cine, rs.getInt("Columna"), rs.getInt("Fila"), rs.getBoolean("EstadoSala"));
		Date newDate = rs.getTimestamp("FechaYHora");
		resp = new Funcion(rs.getInt("idFuncion"), newDate, rs.getInt("DuracionFuncion"), rs.getFloat("Precio"), sala, peli, rs.getBoolean("EstadoFuncion"));
		listaFunciones.add(resp);
	    }
	    rs.close();
	    sentencia.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    oCon.close();
	    return listaFunciones;
	}
    }
    
    
}
