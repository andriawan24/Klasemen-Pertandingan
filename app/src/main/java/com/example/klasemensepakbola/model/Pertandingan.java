package com.example.klasemensepakbola.model;

public class Pertandingan {
    String club1, club2;

    public Pertandingan(String club1, String club2) {
        this.club1 = club1;
        this.club2 = club2;
    }

    public String getClub1() {
        return club1;
    }

    public void setClub1(String club1) {
        this.club1 = club1;
    }

    public String getClub2() {
        return club2;
    }

    public void setClub2(String club2) {
        this.club2 = club2;
    }
}
