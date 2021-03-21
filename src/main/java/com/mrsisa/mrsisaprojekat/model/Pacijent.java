package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Pacijent extends Korisnik {
	private int poeni;
	private Kategorija kategorija;
	private HashMap<Integer,PrepisaniLek> prepisaniLekovi;
	private HashMap<Integer,Termin> termini;
	private HashMap<Integer,Zalba>zalbe;
	private HashMap<Integer,Lek> alergije;
	private HashMap<Integer,eRecept> eRecepti;
	
	public Pacijent() {}

	public int getPoeni() {
		return poeni;
	}

	public void setPoeni(int poeni) {
		this.poeni = poeni;
	}

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public HashMap<Integer,PrepisaniLek> getPrepisaniLekovi() {
		return prepisaniLekovi;
	}

	public void setPrepisaniLekovi(HashMap<Integer,PrepisaniLek> prepisaniLekovi) {
		this.prepisaniLekovi = prepisaniLekovi;
	}

	public HashMap<Integer,Termin> getTermini() {
		return termini;
	}

	public void setTermini(HashMap<Integer,Termin> termini) {
		this.termini = termini;
	}

	public HashMap<Integer,Zalba> getZalbe() {
		return zalbe;
	}

	public void setZalbe(HashMap<Integer,Zalba> zalbe) {
		this.zalbe = zalbe;
	}

	public HashMap<Integer,Lek> getAlergije() {
		return alergije;
	}

	public void setAlergije(HashMap<Integer,Lek> alergije) {
		this.alergije = alergije;
	}

	public HashMap<Integer, eRecept> geteRecepti() {
		return eRecepti;
	}

	public void seteRecepti(HashMap<Integer, eRecept> eRecepti) {
		this.eRecepti = eRecepti;
	}
	
	
	
}
