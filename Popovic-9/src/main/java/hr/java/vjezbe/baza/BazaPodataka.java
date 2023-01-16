package hr.java.vjezbe.baza;

import hr.java.vjezbe.entitet.Profesor;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BazaPodataka {
    private static final String DATABASE_FILE = "database.properties";
    private static Connection spajanjeNaBazu() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));
        String dataBaseUrl = properties.getProperty("dataBaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(dataBaseUrl, username, password);
        return connection;
    }

    public static List<Profesor> getProfesori(){
        List<Profesor> profesori = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PROFESOR");

            while (rs.next()){
                Long id = rs.getLong("ID");
                String ime = rs.getString("IME");
                String prezime = rs.getString("PREZIME");
                String sifra = rs.getString("SIFRA");
                String titula = rs.getString("TITULA");

                profesori.add(new Profesor.Builder(id, ime, prezime).saSifrom(sifra).saTitulom(titula).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return profesori;
    };
}
