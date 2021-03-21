package com.mrsisa.mrsisaprojekat.model;

import java.time.LocalTime;

public class Zaposlenje {
	
	private LocalTime radnoVremeOd;
	private LocalTime radnoVremeDo;
	private String emailDermatologa;
	private int sifraApoteke;
	
	public Zaposlenje() {}

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

	public String getEmailDermatologa() {
		return emailDermatologa;
	}

	public void setEmailDermatologa(String emailDermatologa) {
		this.emailDermatologa = emailDermatologa;
	}

	public int getSifraApoteke() {
		return sifraApoteke;
	}

	public void setSifraApoteke(int sifraApoteke) {
		this.sifraApoteke = sifraApoteke;
	}
	
	
	
	

}
