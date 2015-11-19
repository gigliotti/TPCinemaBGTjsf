/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDCine;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/**
 *
 * @author hernan
 */
public class Cine implements Serializable{

    private int idCine;
    private String nombre;
    private String direccion;
    private boolean estado;
    private ArrayList listadoCines;    
    private Cine cineNuevo;
    private ArrayList salasPorCine = new ArrayList();
    private int idCineSeleccionado;

    public ArrayList getSalasPorCine() {
	return salasPorCine;
    }

    public void setSalasPorCine(ArrayList salasPorCine) {
	this.salasPorCine = salasPorCine;
    }
    

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

    private BDCine datosCine = BDCine.getInstance();

    public String altaCine() throws SQLException {
        this.estado = true;
        datosCine.alta(this);
        refresh();
        return "AltaCine";
    }

    public String bajaCine() throws SQLException {
        datosCine.baja(this.cineNuevo);
        return "EliminaCine";
    }

    public String modificaCine() throws SQLException {
        datosCine.modificar(this.cineNuevo);
        this.cineNuevo = null;
        return "EditaCine";
    }

    public ArrayList listarCines() throws SQLException {
	return datosCine.listado();
    }

    public ArrayList listarCinesActivos() throws SQLException {
	return datosCine.listadoActivos();
    }

    public Cine existe(int idCine) throws SQLException {
	Cine aux = new Cine(idCine);
	return (Cine) datosCine.existe(aux);
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

    public Cine getCineNuevo() {
        return cineNuevo;
    }

    public void setCineNuevo(Cine cineNuevo) {
        this.cineNuevo = cineNuevo;
    }
    
    
    public void refresh() {
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cine");
    }
    
    
        public void onCineChange() throws SQLException {
	    Sala sala = new Sala();
	    salasPorCine =  sala.listarSalasXCine(this.idCine);
    }

    public int getIdCineSeleccionado() {
	return idCineSeleccionado;
    }

    public void setIdCineSeleccionado(int idCineSeleccionado) {
	this.idCineSeleccionado = idCineSeleccionado;
    }


    
    
}
