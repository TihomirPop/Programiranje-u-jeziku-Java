package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Sveuciliste <T extends ObrazovnaUstanova>{
    List<T> obrazovneUstanove;

    public Sveuciliste(){
        this.obrazovneUstanove = new ArrayList<>();
    }

    public void dodajObrazovnuUstanovu(T obrazovnaUstanova){
        obrazovneUstanove.add(obrazovnaUstanova);
    }

    public T dohvatiObrazovnuUstanovu(int i){
        return obrazovneUstanove.get(i);
    }

    public List<T> getObrazovneUstanove() {
        return new ArrayList<>(obrazovneUstanove);
    }
}
