/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

/**
 *
 * @author mahmu
 */
public class User {

    private int id_user, id_maloprodaja;
    private String ime, prezime, datum_rodjenja, datum_zaposlenja, kontakt_broj, 
            email, username, password, ovlasti, pozicija, opis_posla;
    private double satnica;

    public User() {
    }

    public User(int id_user, int id_maloprodaja, String ime, String prezime, String datum_rodjenja, 
            String datum_zaposlenja, String kontakt_broj, String email, String username, String password, 
            String ovlasti, String pozicija, String opis_posla, double satnica) {
        this.id_user = id_user;
        this.id_maloprodaja = id_maloprodaja;
        this.ime = ime;
        this.prezime = prezime;
        this.datum_rodjenja = datum_rodjenja;
        this.datum_zaposlenja = datum_zaposlenja;
        this.kontakt_broj = kontakt_broj;
        this.email = email;
        this.username = username;
        this.password = password;
        this.ovlasti = ovlasti;
        this.pozicija = pozicija;
        this.opis_posla = opis_posla;
        this.satnica = satnica;
    }

    /**
     * @return the id_user
     */
    public int getId_user() {
        return id_user;
    }

    /**
     * @param id_user the id_user to set
     */
    public void setId_user(int id_user) {
        this.id_user = id_user;
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
     * @return the ime
     */
    public String getIme() {
        return ime;
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * @return the prezime
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * @param prezime the prezime to set
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * @return the datum_rodjenja
     */
    public String getDatum_rodjenja() {
        return datum_rodjenja;
    }

    /**
     * @param datum_rodjenja the datum_rodjenja to set
     */
    public void setDatum_rodjenja(String datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    /**
     * @return the datum_zaposlenja
     */
    public String getDatum_zaposlenja() {
        return datum_zaposlenja;
    }

    /**
     * @param datum_zaposlenja the datum_zaposlenja to set
     */
    public void setDatum_zaposlenja(String datum_zaposlenja) {
        this.datum_zaposlenja = datum_zaposlenja;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the ovlasti
     */
    public String getOvlasti() {
        return ovlasti;
    }

    /**
     * @param ovlasti the ovlasti to set
     */
    public void setOvlasti(String ovlasti) {
        this.ovlasti = ovlasti;
    }

    /**
     * @return the pozicija
     */
    public String getPozicija() {
        return pozicija;
    }

    /**
     * @param pozicija the pozicija to set
     */
    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    /**
     * @return the opis_posla
     */
    public String getOpis_posla() {
        return opis_posla;
    }

    /**
     * @param opis_posla the opis_posla to set
     */
    public void setOpis_posla(String opis_posla) {
        this.opis_posla = opis_posla;
    }

    /**
     * @return the satnica
     */
    public double getSatnica() {
        return satnica;
    }

    /**
     * @param satnica the satnica to set
     */
    public void setSatnica(double satnica) {
        this.satnica = satnica;
    }

    @Override
    public String toString() {
        return "\nID: " + id_user +
                "\nID_maloprodaja: " + id_maloprodaja +
                "\nIme: " + ime +
                "\nPrezime: " + prezime +
                "\nDatum rodjenja: " + datum_rodjenja +
                "\nDatum zaposlenja" + datum_zaposlenja +
                "\nKontakt broj: " + kontakt_broj +
                "\nEmail: " + email +
                "\nKorisničko ime: " + username + 
                "\nLozinka: " + password +
                "\nOvlasti: " + ovlasti +
                "\nPozicija: " + pozicija +
                "\nOpis posla: " + opis_posla +
                "\nSatnica: " + satnica;
    }

    
}
