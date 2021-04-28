package com.mrsisa.mrsisaprojekat.dto;

import com.mrsisa.mrsisaprojekat.model.RequestMedicament;

public class RequestMedicamentDTO {

	private Long id;
	private MedicamentDTO medicament;
	private int quantity;
	private boolean accepted;
	private EmployeeDTO employee;
	
	
	public RequestMedicamentDTO() {
		
	}

	public RequestMedicamentDTO(RequestMedicament requestMedicament) {
		this(requestMedicament.getId(), new MedicamentDTO(requestMedicament.getMedicament()),requestMedicament.getQuantity(),requestMedicament.isAccepted(),new EmployeeDTO(requestMedicament.getEmployee()));
	}


	public RequestMedicamentDTO(Long id, MedicamentDTO medicament, int quantity, boolean accepted, EmployeeDTO employee) {
		super();
		this.id = id;
		this.medicament = medicament;
		this.quantity = quantity;
		this.accepted = accepted;
		this.employee = employee;
	}


	public Long getId() {
		return id;
	}


	public MedicamentDTO getMedicament() {
		return medicament;
	}


	public int getQuantity() {
		return quantity;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMedicament(MedicamentDTO medicament) {
		this.medicament = medicament;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	
}
