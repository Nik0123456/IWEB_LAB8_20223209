package org.example.iweb_lab8_20223209.daos;

import org.example.iweb_lab8_20223209.beans.*;
import java.sql.*;
import java.util.ArrayList;

public class ActorDao {

    public ArrayList<Actor> listarActores(int idPelicula) {

        ArrayList<Actor> listaActores = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
        String username = "root";
        String password = "root";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

            String sql = "SELECT A.*\n" +
                    "FROM \n" +
                    "(SELECT * FROM ACTOR ) AS A\n" +
                    "INNER JOIN\n" +
                    "(SELECT * FROM PROTAGONISTAS WHERE IDPELICULA = \n" +
                    idPelicula +
                    ") AS B\n" +
                    "on a.idactor = b.idactor\n";


            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Actor tempActor = new Actor();
                int idActor = rs.getInt(1);
                tempActor.setIdActor(idActor);
                String nombre = rs.getString("nombre");
                tempActor.setNombre(nombre);
                String apellido = rs.getString("apellido");
                tempActor.setApellido(apellido);
                int anoNacimiento = rs.getInt("anoNacimiento");
                tempActor.setAnoNacimiento(anoNacimiento);
                boolean oscar = rs.getBoolean("premioOscar");
                tempActor.setPremioOscar(oscar);

                listaActores.add(tempActor);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaActores;
    }

}
