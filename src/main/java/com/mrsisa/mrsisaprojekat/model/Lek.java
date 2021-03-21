package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Lek {
	private int sifra;
	private String naziv,vrsta,sastav,proizvodjac,napomene;
	private OblikLeka oblik;
	private RezimIzdavanja rezim;
	private HashMap<Integer,Lek> zamenskiLekovi;
	
	public Lek() {}

	public Lek(int sifra, String naziv, String vrsta, String sastav, String proizvodjac, String napomene,
			OblikLeka oblik, RezimIzdavanja rezim,HashMap<Integer,Lek>zamenskiLekovi) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.vrsta = vrsta;
		this.sastav = sastav;
		this.proizvodjac = proizvodjac;
		this.napomene = napomene;
		this.oblik = oblik;
		this.rezim = rezim;
		this.zamenskiLekovi = zamenskiLekovi;
	}


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


	public String getVrsta() {
		return vrsta;
	}


	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}


	public String getSastav() {
		return sastav;
	}


	public void setSastav(String sastav) {
		this.sastav = sastav;
	}


	public String getProizvodjac() {
		return proizvodjac;
	}


	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}


	public String getNapomene() {
		return napomene;
	}


	public void setNapomene(String napomene) {
		this.napomene = napomene;
	}


	public OblikLeka getOblik() {
		return oblik;
	}


	public void setOblik(OblikLeka oblik) {
		this.oblik = oblik;
	}


	public RezimIzdavanja getRezim() {
		return rezim;
	}


	public void setRezim(RezimIzdavanja rezim) {
		this.rezim = rezim;
	}

	public HashMap<Integer,Lek> getZamenskiLekovi() {
		return zamenskiLekovi;
	}

	public void setZamenskiLekovi(HashMap<Integer,Lek> zamenskiLekovi) {
		this.zamenskiLekovi = zamenskiLekovi;
	}
	
	
	

}
