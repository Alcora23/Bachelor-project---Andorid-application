package com.example.aplicatie_nutritie_sport;

import java.util.List;

public class Utilizator {
    private String username;
    private String email;
    private String data_nasterii;
    private String telefon;
    private String parola;
    private String km_initiale;
    private String inaltime;
    private String tip_activitate;
    private String gen;
    private String areNotificare;

    public Utilizator(){

    }

    public Utilizator(String username, String email, String data_nasterii, String telefon, String parola, String km_initiale, String inaltime, String tip_activitate, String gen, String areNotificare) {
        this.username = username;
        this.email = email;
        this.data_nasterii = data_nasterii;
        this.telefon = telefon;
        this.parola = parola;
        this.km_initiale = km_initiale;
        this.inaltime = inaltime;
        this.tip_activitate = tip_activitate;
        this.gen = gen;
        this.areNotificare = areNotificare;
    }

    public Utilizator(String username, String email, String data_nasterii, String telefon, String parola, String km_initiale, String inaltime, String tip_activitate, String gen) {
        this.username = username;
        this.email = email;
        this.data_nasterii = data_nasterii;
        this.telefon = telefon;
        this.parola = parola;
        this.km_initiale = km_initiale;
        this.inaltime = inaltime;
        this.tip_activitate = tip_activitate;
        this.gen = gen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_nasterii() {
        return data_nasterii;
    }

    public void setData_nasterii(String data_nasterii) {
        this.data_nasterii = data_nasterii;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getKm_initiale() {
        return km_initiale;
    }

    public void setKm_initiale(String km_initiale) {
        this.km_initiale = km_initiale;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getInaltime() {
        return inaltime;
    }

    public void setInaltime(String inaltime) {
        this.inaltime = inaltime;
    }

    public String getTip_activitate() {
        return tip_activitate;
    }

    public void setTip_activitate(String tip_activitate) {
        this.tip_activitate = tip_activitate;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getAreNotificare() {
        return areNotificare;
    }

    public void setAreNotificare(String areNotificare) {
        this.areNotificare = areNotificare;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", data_nasterii='" + data_nasterii + '\'' +
                ", telefon='" + telefon + '\'' +
                ", parola='" + parola + '\'' +
                ", km_initiale='" + km_initiale + '\'' +
                ", inaltime='" + inaltime + '\'' +
                ", tip_activitate='" + tip_activitate + '\'' +
                ", gen='" + gen + '\'' +
                ", areNotificare=" + areNotificare +
                '}';
    }
}
