package hr.java.vjezbe.entitet;


public sealed interface Online permits Ispit{
    default void ispisSoftware(String software){
        System.out.println("koristi se: " + software);
    }
}
