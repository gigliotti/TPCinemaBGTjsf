/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Dao.BDReservas;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Bruno, Jorge, Hernan
 */
public class Reserva {

    private int idReserva;
    private Usuario user;
    private int butaca;
    private boolean confirmacion;
    private Funcion funcion;
    private String idsReservas;
    

    public int getIdReserva() {
	return idReserva;
    }

    public void setIdReserva(int idReserva) {
	this.idReserva = idReserva;
    }

    public Usuario getUser() {
	return user;
    }

    public void setUser(Usuario user) {
	this.user = user;
    }

    public int getButaca() {
	return butaca;
    }

    public void setButaca(int butaca) {
	this.butaca = butaca;
    }

    public boolean isConfirmacion() {
	return confirmacion;
    }

    public void setConfirmacion(boolean confirmacion) {
	this.confirmacion = confirmacion;
    }

    public Funcion getFuncion() {
	return funcion;
    }

    public void setFuncion(Funcion funcion) {
	this.funcion = funcion;
    }

    public Reserva(int idReserva, Usuario user, int butaca, boolean confirmacion, Funcion funcion) {
	this.idReserva = idReserva;
	this.user = user;
	this.butaca = butaca;
	this.confirmacion = confirmacion;
	this.funcion = funcion;
    }

    public Reserva() {
    }

    public Reserva(int idReserva) {
	this.idReserva = idReserva;
    }

    public Reserva(Usuario user, int butaca, boolean confirmacion, Funcion funcion) {
	this.user = user;
	this.butaca = butaca;
	this.confirmacion = confirmacion;
	this.funcion = funcion;
    }

    private BDReservas datosReservas = BDReservas.getInstance();

    public void altaReserva(ArrayList<Reserva> reservas) throws SQLException {
	datosReservas.altaMultiplesReservas(reservas);
    }

    public void bajaReserva(Reserva reserva) throws SQLException {
	datosReservas.baja(reserva);
    }

    public Reserva existe(int idReserva) throws SQLException {
	Reserva reserva = new Reserva(idReserva);
	return (Reserva) datosReservas.existe(reserva);
    }

    public ArrayList listarXFuncion(int idFuncion) throws SQLException {
	return datosReservas.listadoXFuncion(idFuncion);
    }

    public ArrayList listarXUsuario(int idUsuario) throws SQLException {
	return datosReservas.listadoXUsuario(idUsuario);
    }
    
    @Override
    public String toString(){
        return String.valueOf(this.butaca);
    }
    
    public void realizarReserva() throws SQLException{
        String[] ids = this.idsReservas.split("-");
        int i = 0;
        ArrayList<Reserva> reservas = new ArrayList<>();
        Reserva aux;
        for(i = 0;i < ids.length ; i++ ){
            if(ids[i] != ""){
                aux = new Reserva(this.user,Integer.parseInt(ids[i]),true,this.funcion);
                reservas.add(aux);
            }
        }
        altaReserva(reservas);
        //rertornar un string para ir a la pagina de reservas del usuario.
    }

    public String getIdsReservas() {
        return idsReservas;
    }

    public void setIdsReservas(String idsReservas) {
        this.idsReservas = idsReservas;
    }

}
