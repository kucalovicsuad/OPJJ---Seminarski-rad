/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CashRegister;

/**
 *
 * @author Suad's Laptop
 */
public class Register {
    private String datum;
    private int id_maloprodaja;
    private String vrijeme_otvaranja;
    private String vrijeme_zatvaranja;
    private double depozit;
    private double promet;
    private int id_zaposlenik_otvaranja;
    private int id_zaposlenik_zatvaranja;

    public Register() {
    }

    public Register(String datum, int id_maloprodaja, String vrijeme_otvaranja, String vrijeme_zatvaranja, double depozit, double promet, int id_zaposlenik_otvaranja, int id_zaposlenik_zatvaranja) {
        this.datum = datum;
        this.id_maloprodaja = id_maloprodaja;
        this.vrijeme_otvaranja = vrijeme_otvaranja;
        this.vrijeme_zatvaranja = vrijeme_zatvaranja;
        this.depozit = depozit;
        this.promet = promet;
        this.id_zaposlenik_otvaranja = id_zaposlenik_otvaranja;
        this.id_zaposlenik_zatvaranja = id_zaposlenik_zatvaranja;
    }

    /**
     * @return the datum
     */
    public String getDatum() {
        return datum;
    }

    /**
     * @param datum the datum to set
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }

    /**
     * @return the id_maloprodaja
     */
    public int getId_maloprodaja() {
        return id_maloprodaja;
    }

    /**
     * @param id_maloprodaja the id_maloprodaja to set
     */
    public void setId_maloprodaja(int id_maloprodaja) {
        this.id_maloprodaja = id_maloprodaja;
    }

    /**
     * @return the vrijeme_otvaranja
     */
    public String getVrijeme_otvaranja() {
        return vrijeme_otvaranja;
    }

    /**
     * @param vrijeme_otvaranja the vrijeme_otvaranja to set
     */
    public void setVrijeme_otvaranja(String vrijeme_otvaranja) {
        this.vrijeme_otvaranja = vrijeme_otvaranja;
    }

    /**
     * @return the vrijeme_zatvaranja
     */
    public String getVrijeme_zatvaranja() {
        return vrijeme_zatvaranja;
    }

    /**
     * @param vrijeme_zatvaranja the vrijeme_zatvaranja to set
     */
    public void setVrijeme_zatvaranja(String vrijeme_zatvaranja) {
        this.vrijeme_zatvaranja = vrijeme_zatvaranja;
    }

    /**
     * @return the depozit
     */
    public double getDepozit() {
        return depozit;
    }

    /**
     * @param depozit the depozit to set
     */
    public void setDepozit(double depozit) {
        this.depozit = depozit;
    }

    /**
     * @return the promet
     */
    public double getPromet() {
        return promet;
    }

    /**
     * @param promet the promet to set
     */
    public void setPromet(double promet) {
        this.promet = promet;
    }

    /**
     * @return the id_zaposlenik_otvaranja
     */
    public int getId_zaposlenik_otvaranja() {
        return id_zaposlenik_otvaranja;
    }

    /**
     * @param id_zaposlenik_otvaranja the id_zaposlenik_otvaranja to set
     */
    public void setId_zaposlenik_otvaranja(int id_zaposlenik_otvaranja) {
        this.id_zaposlenik_otvaranja = id_zaposlenik_otvaranja;
    }

    /**
     * @return the id_zaposlenik_zatvaranja
     */
    public int getId_zaposlenik_zatvaranja() {
        return id_zaposlenik_zatvaranja;
    }

    /**
     * @param id_zaposlenik_zatvaranja the id_zaposlenik_zatvaranja to set
     */
    public void setId_zaposlenik_zatvaranja(int id_zaposlenik_zatvaranja) {
        this.id_zaposlenik_zatvaranja = id_zaposlenik_zatvaranja;
    }

    
}
