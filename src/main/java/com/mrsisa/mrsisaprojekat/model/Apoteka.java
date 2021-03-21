package com.mrsisa.mrsisaprojekat.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Apoteka {
	private int sifra;
	private String naziv,opis;
	private Adresa adresa;
	private double prihod;
	private HashMap<String,Dermatolog>dermatolozi;
	private HashMap<String,Farmaceut> farmaceuti;
	private HashMap<Integer,Narudzbenica> narudzbenice;
	private HashMap<Integer,StavkaLek> lekovi;
	private Cenovnik cenovnik;
	private ArrayList<Ocena> ocene;
	private HashMap<Integer,AkcijaPromocija>akcije;
	private HashMap<String,AdministratorApoteke> admini;
	private HashMap<Integer,Termin> termini;
	
	public Apoteka() {}
	
	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public double getPrihod() {
		return prihod;
	}

	public void setPrihod(double prihod) {
		this.prihod = prihod;
	}

	public HashMap<String,Dermatolog> getDermatolozi() {
		return dermatolozi;
	}

	public void setDermatolozi(HashMap<String,Dermatolog> dermatolozi) {
		this.dermatolozi = dermatolozi;
	}

	public HashMap<String,Farmaceut> getFarmaceuti() {
		return farmaceuti;
	}

	public void setFarmaceuti(HashMap<String,Farmaceut> farmaceuti) {
		this.farmaceuti = farmaceuti;
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

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}

	public ArrayList<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(ArrayList<Ocena> ocene) {
		this.ocene = ocene;
	}

	public HashMap<Integer,AkcijaPromocija> getAkcije() {
		return akcije;
	}

	public void setAkcije(HashMap<Integer,AkcijaPromocija> akcije) {
		this.akcije = akcije;
	}

	public HashMap<String,AdministratorApoteke> getAdmini() {
		return admini;
	}

	public void setAdmini(HashMap<String,AdministratorApoteke> admini) {
		this.admini = admini;
	}

	public HashMap<Integer,Termin> getTermini() {
		return termini;
	}

	public void setTermini(HashMap<Integer,Termin> termini) {
		this.termini = termini;
	}
	
	

}
