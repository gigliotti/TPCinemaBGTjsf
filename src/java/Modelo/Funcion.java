/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDFunciones;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Jorge
 */
public class Funcion {

    private int idFuncion;
    private Date fechaYHora;
    private int duracion;
    private float precio;
    private Sala sala = new Sala();
    private Pelicula pelicula = new Pelicula();
    private boolean estado;
    private Funcion funcionNueva;
    private List<Funcion> filteredFuns;

    public Date getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(Date fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    public String getFecha(){
        return this.fechaYHora.toString();
    }

    public Funcion(int idFuncion, Date fechaYHora, int duracion, float precio, Sala sala, Pelicula pelicula, boolean estado) {
        this.idFuncion = idFuncion;
        this.fechaYHora = fechaYHora;
        this.duracion = duracion;
        this.precio = precio;
        this.sala = sala;
        this.pelicula = pelicula;
        this.estado = estado;
    }

    public Funcion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Funcion() {
    }

    private BDFunciones datosFunciones = BDFunciones.getInstance();

    public void altaFuncion(Funcion funcion) throws SQLException {
        datosFunciones.alta(funcion);
    }

    public void bajaFuncion(int idFuncion) throws SQLException {
        datosFunciones.baja(idFuncion);
    }

    public void modificaFuncion(Funcion funcion) throws SQLException {
        datosFunciones.modificar(funcion);
    }

    public ArrayList listarFunciones() throws SQLException {
        return datosFunciones.listado();
    }

    public Funcion existe(int idFuncion) throws SQLException {
        Funcion aux = new Funcion(idFuncion);
        return (Funcion) datosFunciones.existe(aux);
    }

    public ArrayList validaFuncion(Funcion funcion) throws SQLException {
        return datosFunciones.validaFuncion(funcion);
    }

    public ArrayList listarFuncionesXPelicula(int idPelicula) throws SQLException {
        return datosFunciones.listadoXPelicula(idPelicula);
    }

    public ArrayList listarFuncionesBusquedaAvanzada(int idCine, int idSala, int idPelicula, int NumeroButacas) throws SQLException {
        return datosFunciones.listadoBusquedaAvanzada(idCine, idSala, idPelicula, NumeroButacas);
    }

    public Funcion getFuncionNueva() {
        return funcionNueva;
    }

    public void setFuncionNueva(Funcion funcionNueva) {
        this.funcionNueva = funcionNueva;
    }

    public String modificaFuncion() {
        return "";
    }

    public List<Funcion> getFilteredFuns() {
        return filteredFuns;
    }

    public void setFilteredFuns(List<Funcion> filteredFuns) {
        this.filteredFuns = filteredFuns;
    }

    public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

}
