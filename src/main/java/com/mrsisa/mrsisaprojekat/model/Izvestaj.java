package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Izvestaj {
	private String opis;
	private LocalDateTime datum;
	private HashMap<Integer,PrepisaniLek> prepisaniLekovi;
	
	public Izvestaj() {}

	public Izvestaj(String opis, LocalDateTime datum,HashMap<Integer,PrepisaniLek>prepisaniLekovi) {
		super();
		this.opis = opis;
		this.datum = datum;
		this.prepisaniLekovi = prepisaniLekovi;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public LocalDateTime getDatum() {
		return datum;
	}

	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}

	public HashMap<Integer,PrepisaniLek> getPrepisaniLekovi() {
		return prepisaniLekovi;
	}

	public void setPrepisaniLekovi(HashMap<Integer,PrepisaniLek> prepisaniLekovi) {
		this.prepisaniLekovi = prepisaniLekovi;
	}
	
	

}
