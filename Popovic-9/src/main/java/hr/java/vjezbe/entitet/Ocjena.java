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
    public static Ocjena intToOcjena(Integer intOcjena) {
        return switch (intOcjena) {
            case 1 -> Ocjena.NEDOVOLJAN;
            case 2 -> Ocjena.DOVOLJAN;
            case 3 -> Ocjena.DOBAR;
            case 4 -> Ocjena.VRLODOBAR;
            case 5 -> Ocjena.ODLICAN;
            default -> throw new RuntimeException("Critical error, nije moguce doci do ovog dijela koda!");
        };
    }

    @Override
    public String toString() {
        return ocjena + " - " + opis;
    }
}
