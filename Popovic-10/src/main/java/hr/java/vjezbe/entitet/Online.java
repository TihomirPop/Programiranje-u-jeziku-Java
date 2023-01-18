package hr.java.vjezbe.entitet;

/**
 * sealed interface vezan za online ispite kojega moze implementirati samo klasa Ispit
 */
public sealed interface Online permits Ispit{
    /**
     * vraca ime softwarea za online Ispit kada mu je dano ime softwarea
     * @param software - ime softwarea
     */
    default void ispisSoftware(String software){
        System.out.println("koristi se: " + software);
    }
}
