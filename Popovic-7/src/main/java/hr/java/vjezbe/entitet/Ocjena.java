package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public enum Ocjena {
    NEDOVOLJAN(1, "nedovoljan"),
    DOVOLJAN(2, "dovoljan"),
    DOBAR(3, "dobar"),
    VRLODOBAR(4, "vrlo dobar"),
    ODLICAN(5, "odlican");

    private final int ocjena;
    private final String opis;

    Ocjena(int ocjena, String opis) {
        this.ocjena = ocjena;
        this.opis = opis;
    }

    public Integer getInt() {
        return ocjena;
    }

    public String getOpis() {
        return opis;
    }

    public BigDecimal getBigDecimal(){
        return new BigDecimal(ocjena);
    }
}
