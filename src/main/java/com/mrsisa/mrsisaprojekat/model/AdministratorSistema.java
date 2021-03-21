package com.mrsisa.mrsisaprojekat.model;

import java.util.ArrayList;
import java.util.HashMap;

public class AdministratorSistema extends Korisnik {
	
	private HashMap<Zaposleni,ArrayList<Odmor>> odmori;
	private HashMap<Integer,Lek> sifarnik;
	private HashMap<Integer,Zalba> zalbe;
	
	public AdministratorSistema() {}
	public HashMap<Zaposleni, ArrayList<Odmor>> getOdmori() {
		return odmori;
	}
	public void setOdmori(HashMap<Zaposleni, ArrayList<Odmor>> odmori) {
		this.odmori = odmori;
	}
	public HashMap<Integer, Lek> getSifarnik() {
		return sifarnik;
	}
	public void setSifarnik(HashMap<Integer, Lek> sifarnik) {
		this.sifarnik = sifarnik;
	}
	public HashMap<Integer,Zalba> getZalbe() {
		return zalbe;
	}
	public void setZalbe(HashMap<Integer,Zalba> zalbe) {
		this.zalbe = zalbe;
	}
	
	
	
	

}
