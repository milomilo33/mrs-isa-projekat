package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Termin {
	private int sifra;
	private LocalDate datum;
	private LocalTime trajanjeOd;
	private LocalTime trajanjedDo;
	private Izvestaj izvestaj;
	
	public Termin() {}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public LocalTime getTrajanjeOd() {
		return trajanjeOd;
	}

	public void setTrajanjeOd(LocalTime trajanjeOd) {
		this.trajanjeOd = trajanjeOd;
	}

	public LocalTime getTrajanjedDo() {
		return trajanjedDo;
	}

	public void setTrajanjedDo(LocalTime trajanjedDo) {
		this.trajanjedDo = trajanjedDo;
	}

	public Izvestaj getIzvestaj() {
		return izvestaj;
	}

	public void setIzvestaj(Izvestaj izvestaj) {
		this.izvestaj = izvestaj;
	}
	
	
	

}
