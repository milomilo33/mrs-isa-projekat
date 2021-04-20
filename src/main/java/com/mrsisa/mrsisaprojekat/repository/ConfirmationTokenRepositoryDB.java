package com.mrsisa.mrsisaprojekat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrsisa.mrsisaprojekat.model.ConfirmationToken;

public interface ConfirmationTokenRepositoryDB extends JpaRepository<ConfirmationToken, Long>{
	 ConfirmationToken findByConfirmationToken(String confirmationToken);

}
