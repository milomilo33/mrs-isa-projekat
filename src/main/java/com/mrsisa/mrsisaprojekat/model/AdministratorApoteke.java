package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class AdministratorApoteke extends Korisnik {
	private HashMap<Integer,Odmor> odmori;
	private HashMap<Integer,Zahtev> zahtevi;
	private Apoteka apoteka;
	
	public AdministratorApoteke() {}
	
	public HashMap<Integer,Odmor> getOdmori() {
		return odmori;
	}
	public void setOdmori(HashMap<Integer,Odmor> odmori) {
		this.odmori = odmori;
	}
	public HashMap<Integer,Zahtev> getZahtevi() {
		return zahtevi;
	}
	public void setZahtevi(HashMap<Integer,Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
	
	

}
