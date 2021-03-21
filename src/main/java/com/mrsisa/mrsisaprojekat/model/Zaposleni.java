package com.mrsisa.mrsisaprojekat.model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Zaposleni extends Korisnik {
	
	private RadniKalendar kalendar;
	private ArrayList<Ocena> ocene;
	private HashMap<Integer,Zahtev> zahtevi;
	
	public Zaposleni() {}

	public RadniKalendar getKalendar() {
		return kalendar;
	}

	public void setKalendar(RadniKalendar kalendar) {
		this.kalendar = kalendar;
	}

	public ArrayList<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(ArrayList<Ocena> ocene) {
		this.ocene = ocene;
	}

	public HashMap<Integer, Zahtev> getZahtevi() {
		return zahtevi;
	}

	public void setZahtevi(HashMap<Integer, Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}
	
	
	

}
