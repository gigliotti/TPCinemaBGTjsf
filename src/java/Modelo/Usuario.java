/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDUsuarios;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public class Usuario {

    private int id = 0;
    private String nombre;
    private String apellido;
    private int dni;
    private boolean administrador;
    private String user;
    private String pass;
    private String email;
    private String telefono;
    private String urlImg;
    private boolean logueado;

    public boolean isLogueado() {
	return logueado;
    }

    public void setLogueado(boolean logueado) {
	this.logueado = logueado;
    }

    public String getUrlImg() {
	return urlImg;
    }

    public void setUrlImg(String urlImg) {
	this.urlImg = urlImg;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getApellido() {
	return apellido;
    }

    public void setApellido(String apellido) {
	this.apellido = apellido;
    }

    public int getDni() {
	return dni;
    }

    public void setDni(int dni) {
	this.dni = dni;
    }

    public boolean isAdministrador() {
	return administrador;
    }

    public void setAdministrador(boolean administrador) {
	this.administrador = administrador;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getPass() {
	return pass;
    }

    public void setPass(String pass) {
	this.pass = pass;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Usuario(int id, String nombre, String apellido, int dni, boolean administrador, String user, String pass, String email, String telefono, String urlImg) {
	this.id = id;
	this.nombre = nombre;
	this.apellido = apellido;
	this.dni = dni;
	this.administrador = administrador;
	this.user = user;
	this.pass = pass;
	this.email = email;
	this.telefono = telefono;
	this.urlImg = urlImg;
    }

    public Usuario() {
	this.logueado = false;
    }

    public Usuario(String user, String pass) {
	this.user = user;
	this.pass = pass;
	this.id = 0;
	this.nombre = "";
	this.apellido = "";
	this.dni = 0;
	this.administrador = false;
	this.email = "";
	this.telefono = "";
    }

    private BDUsuarios datosUsuarios = BDUsuarios.getInstance();

    public void registraUsuario(Usuario user) throws SQLException {
	datosUsuarios.alta(user);
    }

    public void modificarUsuario(Usuario user) throws SQLException {
	datosUsuarios.modificar(user);
    }

    public void bajaUsuario(int id) throws SQLException {
	datosUsuarios.baja(datosUsuarios.traePorId(id));
    }

    public ArrayList listarUsuarios() throws SQLException {
	return datosUsuarios.listado();
    }

    public Usuario traePorId(int id) throws SQLException {
	return datosUsuarios.traePorId(id);
    }

    public Usuario validaSIExiste(String user, String email) throws SQLException {
	return datosUsuarios.validaUser(user, email);
    }

    public String validaUsuario() throws SQLException {
	Usuario validado = null;
	validado = (Usuario) datosUsuarios.existe(new Usuario(this.user, this.pass));
	if (validado != null) {
	    this.nombre = validado.nombre;
	    this.apellido = validado.apellido;
	    this.dni = validado.dni;
	    this.email = validado.email;
	    this.id = validado.id;
	    this.user = validado.user;
	    this.pass = validado.pass;
	    this.telefono = validado.telefono;
	    this.urlImg = validado.urlImg;
	    this.administrador = validado.administrador;
	    this.logueado = true;
	}
	//else
	// this.logueado = false;
	return "log";
    }

    public String desLoguear() {
	this.logueado = false;
	return "nolog";
    }

}
