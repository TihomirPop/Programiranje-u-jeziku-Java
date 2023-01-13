package hr.java.vjezbe.sortiranje;

import hr.java.vjezbe.entitet.ObrazovnaUstanova;

import java.util.Comparator;

public class ObrazovneUstanoveSorter implements Comparator<ObrazovnaUstanova> {
    @Override
    public int compare(ObrazovnaUstanova a, ObrazovnaUstanova b) {
        int brojStudentaCompare = ((Integer) a.getStudenti().size()).compareTo(b.getStudenti().size());
        if(brojStudentaCompare == 0)
            return a.getNaziv().compareTo(b.getNaziv());
        return brojStudentaCompare;
    }
}
