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
public class Danhmuc {
    String madm,tendm;

    public Danhmuc(String madm, String tendm) {
        this.madm = madm;
        this.tendm = tendm;
    }

	public String getMadm() {
		return madm;
	}

	public void setMadm(String madm) {
		this.madm = madm;
	}

	public String getTendm() {
		return tendm;
	}

	public void setTendm(String tendm) {
		this.tendm = tendm;
	}
    

    
}
