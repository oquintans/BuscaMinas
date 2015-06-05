/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author oquintansocampo
 */
public class BaseData {

    Connection con = null;
    Statement s = null;
    ResultSet rs = null;
    String usur = "root", pass = "root";

    public Connection conDerby() {

        try {
            //Cargar Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //Conectar a la base en derby
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BuscaminasDB", usur, pass);
            //Confirmamos conexion
            System.out.println("Conexión establecida con el servidor Derby.");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("ERROR DE CONEXION ---> " + ex);
        }
        return con;
    }

    public ArrayList select() {
        ArrayList<Score> aux = new ArrayList();
        try {
            //Declarar consulta
            s = con.createStatement();
            //Ejecutar consulta
            rs = s.executeQuery("select * from score");
            while (rs.next()) {
                aux.add(new Score(
                        rs.getString("nombre"),
                        Integer.parseInt(rs.getString("tiempo"))));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR ---> " + ex);
        }
        return aux;
    }

    public void insert(int coda, String nomb, int nota) {
        try {
            //Declarar consulta
            s = con.createStatement();
            //Ejecutar consulta
            s.executeUpdate("INSERT INTO alumnos values ('" + nomb + "'," + nota + ')');
            //Confirmacion
            System.out.println("Inserción realizada");
        } catch (SQLException ex) {
            System.out.println("ERROR ---> " + ex);
        }
    }

}
