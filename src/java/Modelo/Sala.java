/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDSala;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class Sala {

    private int idSala;
    private int numSala;
    private Cine cine;
    private int columna;
    private int fila;
    private boolean Estado;

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }

    public Cine getCine() {
        return cine;
    }

    public void setCine(Cine cine) {
        this.cine = cine;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean Estado) {
        this.Estado = Estado;
    }

    public Sala(int idSala, int numSala, Cine cine, int columna, int fila, boolean Estado) {
        this.idSala = idSala;
        this.numSala = numSala;
        this.cine = cine;
        this.columna = columna;
        this.fila = fila;
        this.Estado = Estado;
    }

    public Sala(int idSala) {
        this.idSala = idSala;
    }    
    public Sala(int idSala,Cine cine) {
        this.idSala = idSala;
        this.cine = cine;
    }

    private BDSala datosSalas = BDSala.getInstance();
    
    public void altaSala(Sala peli) throws SQLException {
	datosSalas.alta(peli);
    }
    
    public void modificaSala(Sala peli) throws SQLException {
	datosSalas.modificar(peli);
    }
    
    public void bajaSala(Sala peli) throws SQLException {
	datosSalas.baja(peli);
    }
    
    public ArrayList listarSalas() throws SQLException {
	return datosSalas.listado();
    }
    
     public ArrayList listarSalasAdmin() throws SQLException {
	return datosSalas.listadoAdmin();
    }
     
    public ArrayList listarSalasXCine(int idCine) throws SQLException {
	return datosSalas.listadoXCine(idCine);
    }
    
    public Sala existe(int idSala) throws SQLException {
	Sala aux = new Sala(idSala);
	return (Sala) datosSalas.existe(aux);
    }
     
    
}
