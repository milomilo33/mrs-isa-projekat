package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;
import java.util.HashMap;

public class eRecept {
	private int sifra;
	private String emailPacijenta;
	private LocalDate datumIzdavanja;
	private HashMap<Integer,PrepisaniLek>prepisaniLekovi;
	
	public eRecept() {}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getEmailPacijenta() {
		return emailPacijenta;
	}

	public void setEmailPacijenta(String emailPacijenta) {
		this.emailPacijenta = emailPacijenta;
	}

	public LocalDate getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(LocalDate datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}

	public HashMap<Integer,PrepisaniLek> getPrepisaniLekovi() {
		return prepisaniLekovi;
	}

	public void setPrepisaniLekovi(HashMap<Integer,PrepisaniLek> prepisaniLekovi) {
		this.prepisaniLekovi = prepisaniLekovi;
	}
	

}
