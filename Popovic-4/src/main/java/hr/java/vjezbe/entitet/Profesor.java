package hr.java.vjezbe.entitet;

/**
 * Klasa koja predstavlja profesora neke obrazovne ustanove, nasljeduje osobu
 */
public class Profesor extends Osoba {
    private String sifra;
    private  String titula;
    private String JMBG;

    private Profesor(Builder builder){
        super(builder.ime, builder.prezime);
        this.sifra = builder.sifra;
        this.titula = builder.titula;
        this.JMBG = builder.JMBG;
    }

    /**
     * Builder klasa za objekt tipa Profesor
     */
    public static class Builder{
        private String ime;
        private String prezime;
        private String sifra;
        private String titula;
        private String JMBG;

        /**
         * Konstruktor buildera sa obaveznim parametrima
         * @param ime - ime profesora
         * @param prezime - prezime profesora
         */
        public Builder(String ime, String prezime){
            this.ime = ime;
            this.prezime = prezime;
        }

        /**
         * metoda za dodavanje sifre builderu profesora
         * @param sifra - sifra profesora
         * @return - builder vraca sam sebe
         */
        public Builder saSifrom(String sifra){
            this.sifra = sifra;
            return this;
        }

        /**
         * metoda za dodavanje titule builderu profesora
         * @param titula - titula profesora
         * @return - builder vraca sam sebe
         */
        public Builder saTitulom(String titula){
            this.titula = titula;
            return this;
        }

        public Builder saJMBG(String JMBG){
            this.JMBG = JMBG;
            return this;
        }

        /**
         * metoda koja builda objekt tipa profesor sa zadanim elementima
         * @return - profesor sa zadanim elementima
         */
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

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }
}
