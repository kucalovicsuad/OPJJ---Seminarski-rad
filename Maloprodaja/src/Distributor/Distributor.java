/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Distributor;

/**
 *
 * @author mahmu
 */
public class Distributor {
    private int id_distributer;
    private String naziv, adresa, grad, drzava, email, kontakt_broj;

    public Distributor() {
    }

    public Distributor(int id_distributer, String naziv, String adresa, String grad, String drzava, String email, String kontakt_broj) {
        this.id_distributer = id_distributer;
        this.naziv = naziv;
        this.adresa = adresa;
        this.grad = grad;
        this.drzava = drzava;
        this.email = email;
        this.kontakt_broj = kontakt_broj;
    }

    /**
     * @return the id_distributer
     */
    public int getId_distributer() {
        return id_distributer;
    }

    /**
     * @param id_distributer the id_distributer to set
     */
    public void setId_distributer(int id_distributer) {
        this.id_distributer = id_distributer;
    }

    /**
     * @return the naziv
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * @param naziv the naziv to set
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * @return the adresa
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * @param adresa the adresa to set
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     * @return the grad
     */
    public String getGrad() {
        return grad;
    }

    /**
     * @param grad the grad to set
     */
    public void setGrad(String grad) {
        this.grad = grad;
    }

    /**
     * @return the drzava
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * @param drzava the drzava to set
     */
    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the kontakt_broj
     */
    public String getKontakt_broj() {
        return kontakt_broj;
    }

    /**
     * @param kontakt_broj the kontakt_broj to set
     */
    public void setKontakt_broj(String kontakt_broj) {
        this.kontakt_broj = kontakt_broj;
    }
    
    
}
