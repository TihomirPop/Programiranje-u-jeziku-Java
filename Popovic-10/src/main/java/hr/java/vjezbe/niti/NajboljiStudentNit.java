package hr.java.vjezbe.niti;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class NajboljiStudentNit implements Runnable{
    @Override
    public void run() {
        try {
            List<Student> studenti = BazaPodataka.getStudenti();
            Map<Student, Double> prosjeci = new HashMap<>();
            for(Student student: studenti){
                List<Ispit> ispiti = BazaPodataka.getFilteredIspiti(new Ispit(null, null, student, null, null, null));
                prosjeci.put(student, ispiti.stream().mapToDouble(i -> i.getOcjena().getInt()).average().orElse(0));
            }
            Student najboljiStudent = null;
            double najboljiProsjek = -1;
            for(Student student: prosjeci.keySet())
                if(prosjeci.get(student) > najboljiProsjek){
                    najboljiStudent = student;
                    najboljiProsjek = prosjeci.get(student);
                }
            Glavna.setMainStageTitle(String.format("Najbolji student: %s %s (%.2f)", najboljiStudent.getIme(), najboljiStudent.getPrezime(),  najboljiProsjek));
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }
    }
}
