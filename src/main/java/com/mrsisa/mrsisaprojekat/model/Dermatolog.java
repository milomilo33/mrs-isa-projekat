package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Dermatolog extends Zaposleni {
	private HashMap<Integer,Pregled>pregledi;
	private HashMap<Integer,Apoteka>apoteke;
	
	public Dermatolog() {}

	public HashMap<Integer,Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(HashMap<Integer,Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public HashMap<Integer,Apoteka> getApoteke() {
		return apoteke;
	}

	public void setApoteke(HashMap<Integer,Apoteka> apoteke) {
		this.apoteke = apoteke;
	}
	

}
