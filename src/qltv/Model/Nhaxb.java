/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.Model;

/**
 *
 * @author Admin
 */
public class Nhaxb {
    private String manxb,tennxb;

    public Nhaxb(String manxb, String tennxb) {
        this.manxb = manxb;
        this.tennxb = tennxb;
    }

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getTennxb() {
        return tennxb;
    }

    public void setTennxb(String tennxb) {
        this.tennxb = tennxb;
    }
    
}
