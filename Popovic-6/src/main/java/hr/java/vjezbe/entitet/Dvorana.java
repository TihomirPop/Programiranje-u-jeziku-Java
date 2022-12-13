package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Record dvorane u kojoj se odrzava ispit
 * @param naziv - naziv dvorane
 * @param zgrada - naziv zgrade u kojoj je dvorana
 */
public record Dvorana(String naziv, String zgrada) implements Serializable {}
