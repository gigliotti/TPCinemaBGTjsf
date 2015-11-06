/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jorge
 */
public interface IBD <T> {
    public void alta(T dato) throws SQLException;
    public void baja(T dato) throws SQLException;
    public void modificar(T dato) throws SQLException;
    public T existe(T dato) throws SQLException;
    public ArrayList listado() throws SQLException;    
}
