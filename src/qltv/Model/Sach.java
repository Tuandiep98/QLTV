/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.Model;

/**
 *
 * @author Administrator
 */
public class Sach {
    private String masach,madm,manxb,tiede,tacgia;
    private int namxb,sotrang,sobanluu,phienban,bandientu,muonvenha;
    private double gia;

    public Sach() {
    }

    public Sach(String masach, String madm, String manxb, String tiede, String tacgia, int namxb, int sotrang, int sobanluu, int phienban, int bandientu, int muonvenha, double gia) {
        this.masach = masach;
        this.madm = madm;
        this.manxb = manxb;
        this.tiede = tiede;
        this.tacgia = tacgia;
        this.namxb = namxb;
        this.sotrang = sotrang;
        this.sobanluu = sobanluu;
        this.phienban = phienban;
        this.bandientu = bandientu;
        this.muonvenha = muonvenha;
        this.gia = gia;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getMadm() {
        return madm;
    }

    public void setMacd(String macd) {
        this.madm = macd;
    }

    public String getManxb() {
        return manxb;
    }

    public void setManxb(String manxb) {
        this.manxb = manxb;
    }

    public String getTiede() {
        return tiede;
    }

    public void setTiede(String tiede) {
        this.tiede = tiede;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public int getNamxb() {
        return namxb;
    }

    public void setNamxb(int namxb) {
        this.namxb = namxb;
    }

    public int getSotrang() {
        return sotrang;
    }

    public void setSotrang(int sotrang) {
        this.sotrang = sotrang;
    }

    public int getSobanluu() {
        return sobanluu;
    }

    public void setSobanluu(int sobanluu) {
        this.sobanluu = sobanluu;
    }

    public int getPhienban() {
        return phienban;
    }

    public void setPhienban(int phienban) {
        this.phienban = phienban;
    }

    public int getBandientu() {
        return bandientu;
    }

    public void setBandientu(int bandientu) {
        this.bandientu = bandientu;
    }

    public int getMuonvenha() {
        return muonvenha;
    }

    public void setMuonvenha(int muonvenha) {
        this.muonvenha = muonvenha;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
    
    
    
}
