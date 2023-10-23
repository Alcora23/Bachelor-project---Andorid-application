package com.example.aplicatie_nutritie_sport;

public class Produs {
    private String denumire;
    private String cantitate;
    private String calorii;
    private String proteine;
    private String grasimi;
    private String carbohidrati;

    public Produs(){}

    public Produs(String denumire, String cantitate, String calorii, String proteine, String grasimi, String carbohidrati) {
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.calorii = calorii;
        this.proteine = proteine;
        this.grasimi = grasimi;
        this.carbohidrati = carbohidrati;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCantitate() {
        return cantitate;
    }

    public void setCantitate(String cantitate) {
        this.cantitate = cantitate;
    }

    public String getCalorii() {
        return calorii;
    }

    public void setCalorii(String calorii) {
        this.calorii = calorii;
    }

    public String getProteine() {
        return proteine;
    }

    public void setProteine(String proteine) {
        this.proteine = proteine;
    }

    public String getGrasimi() {
        return grasimi;
    }

    public void setGrasimi(String grasimi) {
        this.grasimi = grasimi;
    }

    public String getCarbohidrati() {
        return carbohidrati;
    }

    public void setCarbohidrati(String carbohidrati) {
        this.carbohidrati = carbohidrati;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "denumire='" + denumire + '\'' +
                ", cantitate='" + cantitate + '\'' +
                ", calorii='" + calorii + '\'' +
                ", proteine='" + proteine + '\'' +
                ", grasimi='" + grasimi + '\'' +
                ", carbohidrati='" + carbohidrati + '\'' +
                '}';
    }
}
