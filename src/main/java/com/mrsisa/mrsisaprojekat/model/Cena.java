package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

public class Cena {
	private int sifra;
	private double vrednost;
	private LocalDate datumOd;
	private LocalDate datumDo;
	private int brojPoena;
	
	public Cena() {}

	public Cena(int sifra,double vrednost, LocalDate datumOd, LocalDate datumDo, int brojPoena) {
		super();
		this.sifra = sifra;
		this.vrednost = vrednost;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.brojPoena = brojPoena;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public double getVrednost() {
		return vrednost;
	}

	public void setVrednost(double vrednost) {
		this.vrednost = vrednost;
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

	public int getBrojPoena() {
		return brojPoena;
	}

	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}
	
	

}
