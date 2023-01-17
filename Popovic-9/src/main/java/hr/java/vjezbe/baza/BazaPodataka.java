package hr.java.vjezbe.baza;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
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

    public static Profesor getProfesorById(Long id) throws  BazaPodatakaException{
        try(Connection connection = spajanjeNaBazu()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PROFESOR WHERE ID = " + id);

            rs.next();
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            String sifra = rs.getString("SIFRA");
            String titula = rs.getString("TITULA");

            return new Profesor.Builder(id, ime, prezime).saSifrom(sifra).saTitulom(titula).build();
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

    public static List<Student> getStudenti() throws BazaPodatakaException {
        List<Student> studenti = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENT");

            while (rs.next()){
                Long id = rs.getLong("ID");
                String ime = rs.getString("IME");
                String prezime = rs.getString("PREZIME");
                String jmbag = rs.getString("JMBAG");
                LocalDate datumRodenja = rs.getDate("DATUM_RODJENJA").toLocalDate();

                studenti.add(new Student(id, ime, prezime, jmbag, datumRodenja, Ocjena.ODLICAN, Ocjena.ODLICAN));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }

        return studenti;
    }

    public static List<Student> getFilteredStudenti(Student student) throws BazaPodatakaException {
        try(Connection connection = spajanjeNaBazu()) {
            if(student == null){
                return getStudenti();
            }
            else{
                List<Student> studenti = new ArrayList<>();
                StringBuilder sqlUpit = new StringBuilder("SELECT * FROM STUDENT WHERE 1 = 1");

                if(student.getId() != null)
                    sqlUpit.append(" AND ID = " + student.getId());
                if(student.getIme() != null)
                    sqlUpit.append(" AND IME LIKE '%" + student.getIme() + "%'");
                if(student.getPrezime() != null)
                    sqlUpit.append(" AND PREZIME LIKE '%" + student.getPrezime() + "%'");
                if(student.getJmbag() != null)
                    sqlUpit.append(" AND JMBAG LIKE '%" + student.getJmbag() + "%'");
                if(student.getDatumRodjenja() != null)
                    sqlUpit.append(" AND DATUM_RODJENJA = '" + Date.valueOf(student.getDatumRodjenja()) + "'");

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlUpit.toString());

                while (rs.next()){
                    Long id = rs.getLong("ID");
                    String ime = rs.getString("IME");
                    String prezime = rs.getString("PREZIME");
                    String jmbag = rs.getString("JMBAG");
                    LocalDate datumRodenja = rs.getDate("DATUM_RODJENJA").toLocalDate();

                    studenti.add(new Student(id, ime, prezime, jmbag, datumRodenja, Ocjena.ODLICAN, Ocjena.ODLICAN));
                }
                return studenti;
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }

    private static Student getStudentById(Long id) throws BazaPodatakaException{
        try(Connection connection = spajanjeNaBazu()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENT WHERE ID = " + id);

            rs.next();
            String ime = rs.getString("IME");
            String prezime = rs.getString("PREZIME");
            String jmbag = rs.getString("JMBAG");
            LocalDate datumRodenja = rs.getDate("DATUM_RODJENJA").toLocalDate();

            return new Student(id, ime, prezime, jmbag, datumRodenja, Ocjena.ODLICAN, Ocjena.ODLICAN);
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }

    public static void addStudent(Student student) throws BazaPodatakaException{
        try(Connection connection = spajanjeNaBazu()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STUDENT (IME, PREZIME, JMBAG, DATUM_RODJENJA) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, student.getIme());
            preparedStatement.setString(2, student.getPrezime());
            preparedStatement.setString(3, student.getJmbag());
            preparedStatement.setDate(4, Date.valueOf(student.getDatumRodjenja()));

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }

    public static List<Predmet> getPredmeti() throws BazaPodatakaException {
        List<Predmet> predmeti = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PREDMET");

            while (rs.next()){
                Long id = rs.getLong("ID");
                String sifra = rs.getString("SIFRA");
                String naziv = rs.getString("NAZIV");
                Integer brojEctsBodova = rs.getInt("BROJ_ECTS_BODOVA");
                Long profesorId = rs.getLong("PROFESOR_ID");
                Profesor profesor = getProfesorById(profesorId);
                predmeti.add(new Predmet(id, sifra, naziv, brojEctsBodova, profesor, new HashSet<>()));
            }

            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM PREDMET_STUDENT");

            while (rs.next()){
                Long predmetId = rs.getLong("PREDMET_ID");
                Long studentId = rs.getLong("STUDENT_ID");
                Student student = getStudentById(studentId);
                predmeti.stream().filter(p -> p.getId().equals(predmetId)).forEach(p -> p.getStudenti().add(student));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }

        return predmeti;
    }

    public static List<Predmet> getFilteredPredmeti(Predmet predmet) throws BazaPodatakaException {
        try(Connection connection = spajanjeNaBazu()) {
            if(predmet == null){
                return getPredmeti();
            }
            else{
                List<Predmet> predmeti = new ArrayList<>();
                StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PREDMET WHERE 1 = 1");

                if(predmet.getId() != null)
                    sqlUpit.append(" AND ID = " + predmet.getId());
                if(predmet.getSifra() != null)
                    sqlUpit.append(" AND SIFRA LIKE '%" + predmet.getSifra() + "%'");
                if(predmet.getNaziv() != null)
                    sqlUpit.append(" AND NAZIV LIKE '%" + predmet.getNaziv() + "%'");
                if(predmet.getBrojEctsBodova() != null)
                    sqlUpit.append(" AND BROJ_ECTS_BODOVA = " + predmet.getBrojEctsBodova());
                if(predmet.getNositelj() != null)
                    sqlUpit.append(" AND PROFESOR_ID = " + predmet.getNositelj().getId());

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlUpit.toString());

                while (rs.next()){
                    Long id = rs.getLong("ID");
                    String sifra = rs.getString("SIFRA");
                    String naziv = rs.getString("NAZIV");
                    Integer brojEctsBodova = rs.getInt("BROJ_ECTS_BODOVA");
                    Long profesorId = rs.getLong("PROFESOR_ID");
                    Profesor profesor = getProfesorById(profesorId);
                    predmeti.add(new Predmet(id, sifra, naziv, brojEctsBodova, profesor, new HashSet<>()));
                }

                for(Predmet p: predmeti){
                    statement = connection.createStatement();
                    rs = statement.executeQuery("SELECT * FROM PREDMET_STUDENT WHERE PREDMET_ID = " + p.getId());

                    while (rs.next()){
                        Long studentId = rs.getLong("STUDENT_ID");
                        Student student = getStudentById(studentId);
                        p.getStudenti().add(student);
                    }
                }

                return predmeti;
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }

    public static Predmet getPredmetById(Long id) throws BazaPodatakaException{
        try(Connection connection = spajanjeNaBazu()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PREDMET WHERE ID = " + id);

            rs.next();
            String sifra = rs.getString("SIFRA");
            String naziv = rs.getString("NAZIV");
            Integer brojEctsBodova = rs.getInt("BROJ_ECTS_BODOVA");
            Long profesorId = rs.getLong("PROFESOR_ID");
            Profesor profesor = getProfesorById(profesorId);

            Predmet predmet = new Predmet(id, sifra, naziv, brojEctsBodova, profesor, new HashSet<>());

            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM PREDMET_STUDENT WHERE PREDMET_ID = " + predmet.getId());

            while (rs.next()){
                Long studentId = rs.getLong("STUDENT_ID");
                Student student = getStudentById(studentId);
                predmet.getStudenti().add(student);
            }

            return predmet;

        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }

    public static void addPredmet(Predmet predmet) throws BazaPodatakaException{
        try(Connection connection = spajanjeNaBazu()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PREDMET (SIFRA, NAZIV, BROJ_ECTS_BODOVA, PROFESOR_ID) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, predmet.getSifra());
            preparedStatement.setString(2, predmet.getNaziv());
            preparedStatement.setInt(3, predmet.getBrojEctsBodova());
            preparedStatement.setLong(4, predmet.getNositelj().getId());
            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PREDMET ORDER BY ID DESC LIMIT 0, 1");
            rs.next();
            predmet.setId(rs.getLong("ID"));

            for(Student s: predmet.getStudenti()){
                preparedStatement = connection.prepareStatement("INSERT INTO PREDMET_STUDENT (PREDMET_ID, STUDENT_ID) VALUES (?, ?)");
                preparedStatement.setLong(1, predmet.getId());
                preparedStatement.setLong(2, s.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }
    }

    public static List<Ispit> getIspiti() throws BazaPodatakaException {
        List<Ispit> ispiti = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ISPIT");

            while (rs.next()){
                Long id = rs.getLong("ID");
                Long predmetId = rs.getLong("PREDMET_ID");
                Long studentId = rs.getLong("STUDENT_ID");
                Ocjena ocjena = intToOcjena(rs.getInt("OCJENA"));
                LocalDateTime datumIVrijeme = rs.getTimestamp("DATUM_I_VRIJEME").toLocalDateTime();

                ispiti.add(new Ispit(id, getPredmetById(predmetId), getStudentById(studentId), ocjena, datumIVrijeme, new Dvorana("Dvorana", "Zgrada")));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException(e);
        }

        return ispiti;
    }

    private static Ocjena intToOcjena(Integer intOcjena) {
        return switch (intOcjena) {
            case 1 -> Ocjena.NEDOVOLJAN;
            case 2 -> Ocjena.DOVOLJAN;
            case 3 -> Ocjena.DOBAR;
            case 4 -> Ocjena.VRLODOBAR;
            case 5 -> Ocjena.ODLICAN;
            default -> throw new RuntimeException("Critical error, nije moguce doci do ovog dijela koda!");
        };
    }
}
