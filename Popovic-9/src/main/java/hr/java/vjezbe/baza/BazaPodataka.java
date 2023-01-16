package hr.java.vjezbe.baza;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

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

    public static List<Profesor> getProfesori() throws BazaPodatakaException {
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
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }

        return profesori;
    }

    public static List<Profesor> getFilteredProfesori(Profesor profesor) throws BazaPodatakaException {
        try(Connection connection = spajanjeNaBazu()) {
            if(profesor == null){
                return getProfesori();
            }
            else{
                List<Profesor> profesori = new ArrayList<>();
                StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PROFESOR WHERE 1 = 1");

                if(profesor.getId() != null)
                    sqlUpit.append(" AND ID = " + profesor.getId());
                if(profesor.getIme() != null)
                    sqlUpit.append(" AND IME LIKE '%" + profesor.getIme() + "%'");
                if(profesor.getPrezime() != null)
                    sqlUpit.append(" AND PREZIME LIKE '%" + profesor.getPrezime() + "%'");
                if(profesor.getSifra() != null)
                    sqlUpit.append(" AND SIFRA LIKE '%" + profesor.getSifra() + "%'");
                if(profesor.getTitula() != null)
                    sqlUpit.append(" AND TITULA LIKE '%" + profesor.getTitula() + "%'");

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlUpit.toString());

                while (rs.next()){
                    Long id = rs.getLong("ID");
                    String ime = rs.getString("IME");
                    String prezime = rs.getString("PREZIME");
                    String sifra = rs.getString("SIFRA");
                    String titula = rs.getString("TITULA");

                    profesori.add(new Profesor.Builder(id, ime, prezime).saSifrom(sifra).saTitulom(titula).build());
                }
                return profesori;
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }

    public static void addProfesor(Profesor profesor) throws BazaPodatakaException{
        try(Connection connection = spajanjeNaBazu()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PROFESOR (IME, PREZIME, SIFRA, TITULA) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, profesor.getIme());
            preparedStatement.setString(2, profesor.getPrezime());
            preparedStatement.setString(3, profesor.getSifra());
            preparedStatement.setString(4, profesor.getTitula());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }
}
