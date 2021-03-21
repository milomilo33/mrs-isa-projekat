package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Cenovnik {
	
	private HashMap<Integer,StavkaCenovnika> cene;
	
	public Cenovnik() {}
	
	public HashMap<Integer, StavkaCenovnika> getCene() {
		return cene;
	}
	public void setCene(HashMap<Integer, StavkaCenovnika> cene) {
		this.cene = cene;
	}
	

}
