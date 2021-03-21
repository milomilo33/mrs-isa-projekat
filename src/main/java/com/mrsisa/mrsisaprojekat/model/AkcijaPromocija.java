package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDate;

public class AkcijaPromocija {
	private int sifra;
	private LocalDate datumOd;
	private LocalDate datumDo;
	private String opis;
	private boolean vrednost; // true akcija, false promocija
	
	public AkcijaPromocija() {}
	
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public boolean isVrednost() {
		return vrednost;
	}

	public void setVrednost(boolean vrednost) {
		this.vrednost = vrednost;
	}
	

}
