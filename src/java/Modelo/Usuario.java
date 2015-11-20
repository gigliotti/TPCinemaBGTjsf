/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDReservas;
import Dao.BDUsuarios;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.UploadedFile;
/**
 *
 * @author Jorge
 */
public class Usuario implements Serializable {

    private int id;
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
    private Usuario selectedUsuario;
    private Usuario editarUsuario;
    UploadedFile uploadedFile;
    private Usuario nuevoUsuario;
    
    private BDUsuarios datosUsuarios = BDUsuarios.getInstance();

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

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public Usuario getEditarUsuario() {
        return editarUsuario;
    }

    public void setEditarUsuario(Usuario editarUsuario) {
        this.editarUsuario = editarUsuario;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
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

    public Usuario() throws SQLException {
        this.logueado = false;       
        nuevoUsuario = new Usuario("");        
    }

    public Usuario(String edit) {
        this.logueado = false;
    }

    public Usuario(String user, String pass) throws SQLException {
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

    

    public void registraUsuario(Usuario user) throws SQLException {
        datosUsuarios.alta(user);
    }

    public void modificarUsuario(Usuario user) throws SQLException {
        datosUsuarios.modificar(user);
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
        System.getProperties().put("org.apache.el.parser.COERCE_TO_ZERO", "false");
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
   

    public String altaUsuario() throws SQLException{
        if (this.uploadedFile != null) {
            if (!this.uploadedFile.getFileName().equals("")) {
                uploadFile(this);
                this.urlImg = this.uploadedFile.getFileName();
            } else {
                this.urlImg = "user-default.png";
            }
        }
        datosUsuarios.alta(this);
        alta();
        return "AltaUsuario";
    }
    
    public String registrarUsuario() throws SQLException{
        if (this.uploadedFile != null) {
            if (!this.uploadedFile.getFileName().equals("")) {
                uploadFile(this);
                this.urlImg = this.uploadedFile.getFileName();
            } else {
                this.urlImg = "user-default.png";
            }
        }
        datosUsuarios.alta(this);
        alta();
        return "RegistrarUsuario";
    }

    public String modificaUsuario() throws Exception {
        Usuario userBd = datosUsuarios.traePorId(this.editarUsuario.id);
        if (!this.editarUsuario.uploadedFile.getFileName().equals("")) {
            uploadFile(this.editarUsuario);
            this.editarUsuario.urlImg = this.editarUsuario.uploadedFile.getFileName();
        } else {
            if (userBd != null && userBd.urlImg == null) {
                this.editarUsuario.urlImg = "user-default.png";
            }
        }
        if (userBd != null) {
            datosUsuarios.modificar(this.editarUsuario);
        }
        this.editarUsuario = null;
        return "EditaUsuario";
    }

    public void uploadFile(Usuario user) {
        try {
            HttpServletRequest request
                    = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String dir = request.getRealPath("/");
            String dir2 = dir.replaceAll("web", "img");
            String dir3 = dir2.replaceAll("build", "web");
            dir3 = dir3.concat("imgUsuarios/");
            String filename = user.uploadedFile.getFileName();
            Path folder = Paths.get(dir3, filename);
            Path file = Files.createFile(folder);
            InputStream input = user.uploadedFile.getInputstream();
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String bajaUsuario() throws Exception {
        datosUsuarios.baja(datosUsuarios.traePorId(this.editarUsuario.id));
        return "EliminaUsuario";
    }

    public void alta() throws SQLException {        
        this.user = "";
        this.pass = "";
        this.id = 0;
        this.nombre = "";
        this.apellido = "";
        this.dni = 0;
        this.administrador = false;
        this.email = "";
        this.telefono = "";
    }

    public void editar() {
        this.editarUsuario = this.selectedUsuario;
    }

    public void eliminar() {
        this.editarUsuario = this.selectedUsuario;
    }
    
    public void captcha() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "Correct");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    BDReservas datosReservas = BDReservas.getInstance();
    
        public ArrayList traerPorUsuario() throws SQLException{
	return datosReservas.listadoXUsuario(this.id);
    }
  
}
