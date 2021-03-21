package com.mrsisa.mrsisaprojekat.model;

public class StavkaLek {
	private int sifra;
	private Lek lek;
	private int kolicina;
	
	public StavkaLek() {}

	public int getSifra() {
		return sifra;
	}


	public void setSifra(int sifra) {
		this.sifra = sifra;
	}


	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek lek) {
		this.lek = lek;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	
	

}
