package com.mrsisa.mrsisaprojekat.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class AdminSystem extends User {
	
	@OneToMany(mappedBy = "responder", fetch = FetchType.LAZY)
	private Set<Complaint> respondedComplaints;
	
	public AdminSystem() {}
	

	public Set<Complaint> getRespondedComplaints() {
		return respondedComplaints;
	}


	public void setRespondedComplaints(Set<Complaint> respondedComplaints) {
		this.respondedComplaints = respondedComplaints;
	}

}
