/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDPeliculas;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.primefaces.model.UploadedFile;

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
    private List<Pelicula> listaPeliculas;
    private List<Pelicula> listaPeliculasAdmin;
    private UploadedFile uploadedFile;

    public List<Pelicula> getListaPeliculas() {
	return listaPeliculas;
    }

    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
	this.listaPeliculas = listaPeliculas;
    }

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

    public void modificapelicula(Pelicula peli) throws SQLException {
	datosPeliculas.modificar(peli);
    }

    public ArrayList listarPeliculas() throws SQLException {
	return datosPeliculas.listado();
    }

    public ArrayList listarPeliculasAdmin() throws SQLException {
	return datosPeliculas.listadoAdmin();
    }

    public String existe(int idPelicula) throws SQLException {
	Pelicula peli = null;
	peli = (Pelicula) datosPeliculas.existe(idPelicula);
	if (peli != null) {
	    this.idPelicula = peli.idPelicula;
	    this.nombre = peli.nombre;
	    this.director = peli.director;
	    this.duracion = peli.duracion;
	    this.descripcion = peli.descripcion;
	    this.estado = peli.estado;
	    this.urlImagen = peli.urlImagen;
	}
	return "existe";
    }

    public String listar() throws Exception {
	listaPeliculas = new ArrayList();
	this.listaPeliculas = datosPeliculas.listado();
	return "principal";
    }
    
    public String listarAdmin() throws Exception {
	listaPeliculasAdmin = new ArrayList();
	this.listaPeliculasAdmin = datosPeliculas.listadoAdmin();
	return "listarPeliculas";
    }

    public List<Pelicula> getListaPeliculasAdmin() {
	return listaPeliculasAdmin;
    }

    public void setListaPeliculasAdmin(List<Pelicula> listaPeliculasAdmin) {
	this.listaPeliculasAdmin = listaPeliculasAdmin;
    }
    
        public String altaPelicula() throws SQLException{
        if (this.uploadedFile != null) {
            if (!this.uploadedFile.getFileName().equals("")) {
                uploadFile(this);
                this.urlImagen = this.uploadedFile.getFileName();
            } else {
                this.urlImagen = "movie-default.png";
            }
        }
        datosPeliculas.alta(this);
        alta();
        return "AltaPelicula";
    }
    

    public void uploadFile(Pelicula peli) {
        try {
            HttpServletRequest request
                    = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String dir = request.getRealPath("/");
            String dir2 = dir.replaceAll("web", "img");
            String dir3 = dir2.replaceAll("build", "web");
            String filename = peli.uploadedFile.getFileName();
            Path folder = Paths.get(dir3, filename);
            Path file = Files.createFile(folder);
            InputStream input = peli.uploadedFile.getInputstream();
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void alta() {
	this.idPelicula = 0;
	this.nombre = "";
	this.director = "";
	this.duracion = 0;
	this.descripcion = "";
	this.estado = false;
	this.urlImagen = "";
    }

}
