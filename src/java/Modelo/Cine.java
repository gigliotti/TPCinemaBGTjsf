/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDCine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hernan
 */
public class Cine {

    private int idCine;
    private String nombre;
    private String direccion;
    private boolean estado;
    private ArrayList listadoCines;    

    public int getIdCine() {
	return idCine;
    }

    public void setIdCine(int idCine) {
	this.idCine = idCine;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getDireccion() {
	return direccion;
    }

    public void setDireccion(String direccion) {
	this.direccion = direccion;
    }

    public boolean isEstado() {
	return estado;
    }

    public void setEstado(boolean estado) {
	this.estado = estado;
    }

    
    
    public Cine(int idCine, String nombre, String direccion, boolean estado) {
	this.idCine = idCine;
	this.nombre = nombre;
	this.direccion = direccion;
	this.estado = estado;
    }

    public Cine(int idCine) {
	this.idCine = idCine;
	this.nombre = "";
	this.direccion = "";
	this.estado = false;
    }

    public Cine() {
    }

    private BDCine datosCines = BDCine.getInstance();

    public String altaCine() throws SQLException {
        Cine aux = new Cine(0, this.nombre, this.direccion, true);
	datosCines.alta(aux);
        return "altaCine";
    }

    public void bajaCine(int cine) throws SQLException {
	datosCines.baja(cine);
    }

    public void modificaCine(Cine cine) throws SQLException {
	datosCines.modificar(cine);
    }

    public ArrayList listarCines() throws SQLException {
	return datosCines.listado();
    }

    public ArrayList listarCinesActivos() throws SQLException {
	return datosCines.listadoActivos();
    }

    public Cine existe(int idCine) throws SQLException {
	Cine aux = new Cine(idCine);
	return (Cine) datosCines.existe(aux);
    }

    public ArrayList getListadoCines() {
        return listadoCines;
    }

    public void setListadoCines(ArrayList listadoCines) {
        this.listadoCines = listadoCines;
        
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }
}
