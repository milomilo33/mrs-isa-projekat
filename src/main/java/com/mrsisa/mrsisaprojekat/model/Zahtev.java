package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

public class Zahtev {
	private int sifra;
	private String opis;
	private LocalDate datumOd;
	private LocalDate datumDo;
	private boolean prihvacen;
	
	public Zahtev() {}
	
	public Zahtev(int sifra,String opis, LocalDate datumOd, LocalDate datumDo, boolean prihvacen) {
		super();
		this.sifra = sifra;
		this.opis = opis;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.prihvacen = prihvacen;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
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

	public boolean isPrihvacen() {
		return prihvacen;
	}

	public void setPrihvacen(boolean prihvacen) {
		this.prihvacen = prihvacen;
	}
	

}
