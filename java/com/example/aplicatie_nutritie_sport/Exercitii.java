package com.example.aplicatie_nutritie_sport;

public class Exercitii {
    String categorie;
    String denumire;
    long timp;
    int calorii;
    String descriere;
    String sursaImagine;

    public Exercitii() {
    }

    public Exercitii(String categorie, String denumire, long timp, int calorii, String descriere, String sursaImagine) {
        this.categorie = categorie;
        this.denumire = denumire;
        this.timp = timp;
        this.calorii = calorii;
        this.descriere = descriere;
        this.sursaImagine = sursaImagine;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public long getTimp() {
        return timp;
    }

    public void setTimp(long timp) {
        this.timp = timp;
    }

    public int getCalorii() {
        return calorii;
    }

    public void setCalorii(int calorii) {
        this.calorii = calorii;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getSursaImagine() {
        return sursaImagine;
    }

    public void setSursaImagine(String sursaImagine) {
        this.sursaImagine = sursaImagine;
    }

    @Override
    public String toString() {
        return "Exercitii{" +
                "categorie='" + categorie + '\'' +
                ", denumire='" + denumire + '\'' +
                ", timp=" + timp +
                ", calorii=" + calorii +
                ", descriere='" + descriere + '\'' +
                ", sursaImagine='" + sursaImagine + '\'' +
                '}';
    }
}
