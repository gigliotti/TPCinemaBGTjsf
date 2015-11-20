/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDSala;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.context.FacesContext;


/**
 *
 * @author Jorge
 */
public class Sala implements Serializable{

    private int idSala;
    private int numSala;
    private Cine cine = new Cine();
    private int columna;
    private int fila;
    private boolean Estado;
    private Sala salaNueva;
    private int idCineSala;

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

    public Sala getSalaNueva() {
        return salaNueva;
    }

    public void setSalaNueva(Sala salaNueva) {
        this.salaNueva = salaNueva;
    }

    public int getIdCineSala() {
        return idCineSala;
    }

    public void setIdCineSala(int idCineSala) {
        this.idCineSala = idCineSala;
    }

    public Sala(int idSala, int numSala, Cine cine, int columna, int fila, boolean Estado) {
        this.idSala = idSala;
        this.numSala = numSala;
        this.cine = cine;
        this.columna = columna;
        this.fila = fila;
        this.Estado = Estado;
        this.idCineSala = cine.getIdCine();
    }

    public Sala() {

    }

    public Sala(int idSala) {
        this.idSala = idSala;
    }

    public Sala(int idSala, Cine cine) {
        this.idSala = idSala;
        this.cine = cine;
    }

    private BDSala datosSalas = BDSala.getInstance();

    public String altaSala() throws SQLException {
        this.cine = (new Cine()).existe(this.idCineSala);
        this.Estado = true;
        datosSalas.alta(this);
        refresh();
        return "AltaSala";
    }

    public String modificaSala() throws SQLException {
        this.salaNueva.cine = (new Cine()).existe(this.salaNueva.idCineSala);
        datosSalas.modificar(this.salaNueva);
        this.salaNueva = null;
        return "EditaSala";
    }

    public String bajaSala() throws SQLException {
        datosSalas.baja(this.salaNueva);
        return "EliminaSala";
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

    public void refresh() {
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("sala");
//        this.idSala = 0;
//        this.numSala = 0;
//        this.cine = null;
//        this.columna = 0;
//        this.fila = 0;
//        this.Estado = false;
//        this.idCineSala = 0;
    }
    
    public ArrayList cantButacas() throws SQLException{
        //Sala aux = new Sala(1);
        //aux = (Sala) datosSalas.existe(aux);
        ArrayList butacas = new ArrayList();
        Butaca aux;
        for(int i = 1; i <= this.fila * this.columna; i++){
            aux= new Butaca(i);
            butacas.add(aux);
        }        
        return butacas;
    }
    
    public class Butaca{
        private int idButaca;
        
        public Butaca(int id){
            this.idButaca = id;
        }

        public int getIdButaca() {
            return idButaca;
        }

        public void setIdButaca(int idButaca) {
            this.idButaca = idButaca;
        }
        
        public String toString(){
            return String.valueOf(this.idButaca);
        }
        
    }

}

