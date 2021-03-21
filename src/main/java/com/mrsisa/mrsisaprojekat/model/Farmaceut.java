package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalTime;
import java.util.HashMap;

public class Farmaceut extends Zaposleni {
	
	private LocalTime radnoVremeOd;
	private LocalTime radnoVremeDo;
	private HashMap<Integer,Savetovanje>savetovanja;
	private Apoteka apoteka;
	
	public Farmaceut() {}

	public LocalTime getRadnoVremeOd() {
		return radnoVremeOd;
	}

	public void setRadnoVremeOd(LocalTime radnoVremeOd) {
		this.radnoVremeOd = radnoVremeOd;
	}

	public LocalTime getRadnoVremeDo() {
		return radnoVremeDo;
	}

	public void setRadnoVremeDo(LocalTime radnoVremeDo) {
		this.radnoVremeDo = radnoVremeDo;
	}

	public HashMap<Integer,Savetovanje> getSavetovanja() {
		return savetovanja;
	}

	public void setSavetovanja(HashMap<Integer,Savetovanje> savetovanja) {
		this.savetovanja = savetovanja;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
	
	
	

}
