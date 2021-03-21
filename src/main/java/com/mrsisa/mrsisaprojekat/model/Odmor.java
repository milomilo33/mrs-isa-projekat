package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

public class Odmor {
	private int sifra;
	private LocalDate datumOd;
	private LocalDate datumDo;
	private Zaposleni zaposleni;
	
	public Odmor() {}

	public Odmor(int sifra,LocalDate datumOd, LocalDate datumDo, Zaposleni zaposleni) {
		super();
		this.sifra = sifra;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.zaposleni = zaposleni;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public LocalDate getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(LocalDate datumOd) {
		this.datumOd = datumOd;
	}

	public LocalDate getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(LocalDate datumDo) {
		this.datumDo = datumDo;
	}

	public Zaposleni getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(Zaposleni zaposleni) {
		this.zaposleni = zaposleni;
	}
	
	

}
