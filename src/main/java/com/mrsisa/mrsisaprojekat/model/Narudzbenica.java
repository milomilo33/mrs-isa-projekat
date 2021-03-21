package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Narudzbenica {
	private int sifra;
	private StatusNarudzbenice status;
	private LocalDateTime rok;
	private AdministratorApoteke admin;
	private HashMap<Integer,Ponuda> ponude;
	private HashMap<Integer,StavkaLek> lekovi;
	private Dobavljac dobavljac;
	private HashMap<Integer,Apoteka> apoteke;
	
	public Narudzbenica() {}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public StatusNarudzbenice getStatus() {
		return status;
	}

	public void setStatus(StatusNarudzbenice status) {
		this.status = status;
	}

	public LocalDateTime getRok() {
		return rok;
	}

	public void setRok(LocalDateTime rok) {
		this.rok = rok;
	}

	public AdministratorApoteke getAdmin() {
		return admin;
	}

	public void setAdmin(AdministratorApoteke admin) {
		this.admin = admin;
	}

	public HashMap<Integer,Ponuda> getPonude() {
		return ponude;
	}

	public void setPonude(HashMap<Integer,Ponuda> ponude) {
		this.ponude = ponude;
	}

	public HashMap<Integer,StavkaLek> getLekovi() {
		return lekovi;
	}

	public void setLekovi(HashMap<Integer,StavkaLek> lekovi) {
		this.lekovi = lekovi;
	}

	public Dobavljac getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}

	public HashMap<Integer, Apoteka> getApoteke() {
		return apoteke;
	}

	public void setApoteke(HashMap<Integer, Apoteka> apoteke) {
		this.apoteke = apoteke;
	}
	
	
	
	

}
