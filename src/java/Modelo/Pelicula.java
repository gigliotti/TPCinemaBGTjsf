/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDPeliculas;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hernan
 */
public class Pelicula {

    private int idPelicula;
    private String nombre;
    private String director;
    private int duracion;
    private String descripcion;
    private boolean estado;
    private String urlImagen;
    private ArrayList listaPeliculas;
    

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Pelicula(int idPelicula, String nombre, String director, int duracion, String descripcion, boolean estado, String urlImagen) {
        this.idPelicula = idPelicula;
        this.nombre = nombre;
        this.director = director;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.urlImagen = urlImagen;
    }

    public Pelicula(String nombre, String director, int duracion, String descripcion, boolean estado, String urlImagen) {
        this.idPelicula = 0;
        this.nombre = nombre;
        this.director = director;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.urlImagen = urlImagen;
    }
    
    public Pelicula(int idPelicula) {
        this.idPelicula = idPelicula;
        this.nombre = "";
        this.director = "";
        this.duracion = 0;
        this.descripcion = "";
        this.estado = false;
        this.urlImagen = "";
    }

    public Pelicula() {

    }    

    private BDPeliculas datosPeliculas = BDPeliculas.getInstance();

    public void altaPelicula(Pelicula peli) throws SQLException {
        datosPeliculas.alta(peli);
    }

    public void bajaPelicula(int peli) throws SQLException {
        datosPeliculas.baja(peli);
    }
    
    public void modificapelicula(Pelicula peli) throws SQLException{
	datosPeliculas.modificar(peli);
    }

    public ArrayList listarPeliculas() throws SQLException {
        return datosPeliculas.listado();
    }

    public ArrayList listarPeliculasAdmin() throws SQLException {
        return datosPeliculas.listadoAdmin();
    }    

    public void existe(int idPelicula) throws SQLException {
        Pelicula peli = (Pelicula) datosPeliculas.existe(idPelicula);
        this.idPelicula = peli.idPelicula;
        this.nombre = peli.nombre;
        this.director = peli.director;
        this.duracion = peli.duracion;
        this.descripcion = peli.descripcion;
        this.estado = peli.estado;
        this.urlImagen = peli.urlImagen;
    }
    
    public String listar() throws Exception
    {
        listaPeliculas = datosPeliculas.listado();
        return "listarPeliculas";
    }
    
}
