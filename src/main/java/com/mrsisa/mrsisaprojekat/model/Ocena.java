package com.mrsisa.mrsisaprojekat.model;

public class Ocena {
	private int vrednost;
	private Pacijent pacijent;
	
	public Ocena() {}

	public int getVrednost() {
		return vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
	

}
