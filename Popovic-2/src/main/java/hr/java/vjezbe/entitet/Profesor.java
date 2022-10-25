package hr.java.vjezbe.entitet;

public class Profesor extends Osoba {
    private String sifra;
    private  String titula;

    private Profesor(Builder builder){
        super(builder.ime, builder.prezime);
        this.sifra = builder.sifra;
        this.titula = builder.titula;
    }
    public static class Builder{
        private String ime;
        private String prezime;
        private String sifra;
        private String titula;

        public Builder(String ime, String prezime){
            this.ime = ime;
            this.prezime = prezime;
        }
        public Builder saSifrom(String sifra){
            this.sifra = sifra;
            return this;
        }
        public Builder saTitulom(String titula){
            this.titula = titula;
            return this;
        }

        public Profesor build(){
            return new Profesor(this);
        }
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }
}
