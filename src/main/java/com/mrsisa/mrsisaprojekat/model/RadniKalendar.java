package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class RadniKalendar {
	private HashMap<Integer,Termin> termini;
	
	public RadniKalendar() {}

	public HashMap<Integer, Termin> getTermini() {
		return termini;
	}

	public void setTermini(HashMap<Integer, Termin> termini) {
		this.termini = termini;
	}
	
	

}
