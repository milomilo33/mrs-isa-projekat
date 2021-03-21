package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Dobavljac extends Korisnik {
	private HashMap<Integer,Ponuda> ponude;
	private HashMap<Integer,Zahtev> zahtevi;
	private HashMap<Integer,Narudzbenica> narudzbenice;
	private HashMap<Integer,StavkaLek> lekovi;
	
	public Dobavljac() {}
	
	public HashMap<Integer,Ponuda> getPonude() {
		return ponude;
	}
	public void setPonude(HashMap<Integer,Ponuda> ponude) {
		this.ponude = ponude;
	}
	public HashMap<Integer,Zahtev> getZahtevi() {
		return zahtevi;
	}
	public void setZahtevi(HashMap<Integer,Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}
	public HashMap<Integer,Narudzbenica> getNarudzbenice() {
		return narudzbenice;
	}
	public void setNarudzbenice(HashMap<Integer,Narudzbenica> narudzbenice) {
		this.narudzbenice = narudzbenice;
	}
	public HashMap<Integer,StavkaLek> getLekovi() {
		return lekovi;
	}
	public void setLekovi(HashMap<Integer,StavkaLek> lekovi) {
		this.lekovi = lekovi;
	}
	

}
