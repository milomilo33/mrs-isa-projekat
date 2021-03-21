package com.mrsisa.mrsisaprojekat.model;

public class PrepisaniLek {
	private int sifra;
	private boolean kupljen;
	private boolean rezervisan;
	private int kolicina;
	
	public PrepisaniLek() {}

	public PrepisaniLek(int sifra, boolean kupljen, boolean rezervisan, int kolicina) {
		super();
		this.sifra = sifra;
		this.kupljen = kupljen;
		this.rezervisan = rezervisan;
		this.kolicina = kolicina;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public boolean isKupljen() {
		return kupljen;
	}

	public void setKupljen(boolean kupljen) {
		this.kupljen = kupljen;
	}

	public boolean isRezervisan() {
		return rezervisan;
	}

	public void setRezervisan(boolean rezervisan) {
		this.rezervisan = rezervisan;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	

}
