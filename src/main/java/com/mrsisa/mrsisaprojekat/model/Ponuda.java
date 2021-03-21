package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;

public class Ponuda {
	private int sifra;
	private StatusPonude status;
	private double ukupnaCena;
	private LocalDateTime rokIsporuke;
	private Dobavljac dobavljac;
	private Narudzbenica narudzbenica;
	
	public Ponuda() {}
	
	public Ponuda(int sifra, StatusPonude status, double ukupnaCena, LocalDateTime rokIsporuke) {
		super();
		this.sifra = sifra;
		this.status = status;
		this.ukupnaCena = ukupnaCena;
		this.rokIsporuke = rokIsporuke;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public StatusPonude getStatus() {
		return status;
	}

	public void setStatus(StatusPonude status) {
		this.status = status;
	}

	public double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public LocalDateTime getRokIsporuke() {
		return rokIsporuke;
	}

	public void setRokIsporuke(LocalDateTime rokIsporuke) {
		this.rokIsporuke = rokIsporuke;
	}

	public Dobavljac getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}

	public Narudzbenica getNarudzbenica() {
		return narudzbenica;
	}

	public void setNarudzbenica(Narudzbenica narudzbenica) {
		this.narudzbenica = narudzbenica;
	}
	
	
	

}
