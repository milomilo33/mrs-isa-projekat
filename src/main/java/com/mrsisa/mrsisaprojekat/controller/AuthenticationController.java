package com.mrsisa.mrsisaprojekat.controller;

import com.mrsisa.mrsisaprojekat.dto.JwtAuthenticationRequest;
import com.mrsisa.mrsisaprojekat.dto.PatientDTO;
import com.mrsisa.mrsisaprojekat.dto.UserTokenState;
import com.mrsisa.mrsisaprojekat.model.Address;
import com.mrsisa.mrsisaprojekat.model.Category;
import com.mrsisa.mrsisaprojekat.model.Patient;
import com.mrsisa.mrsisaprojekat.model.User;
import com.mrsisa.mrsisaprojekat.service.*;
import com.mrsisa.mrsisaprojekat.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private SystemAdminService sysAdminService;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@Autowired
	private DermatologistService dermatologistService;
	
	@Autowired
	private AddressService addressService;
	
	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
		// Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
		// AuthenticationException
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		
		// Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
		// kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		if(!user.isActive()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String role = user.getRoles().get(0).getName();
		String jwt = tokenUtils.generateToken(user.getUsername(), role);
		int expiresIn = tokenUtils.getExpiredIn();
		
		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/signup")
	public ResponseEntity<PatientDTO> addUser(@RequestBody PatientDTO patientDTO, UriComponentsBuilder ucBuilder) throws Exception {

	//	Patient existUser = this.patientService.findOne(userRequest.getEmail());

	//	if (existUser != null) {
	//		throw new ResourceConflictException(userRequest.getEmail(), "Username already exists");
	//	}

		Address address = new Address();
		address.setCountry(patientDTO.getAddress().getCountry());
		address.setCity(patientDTO.getAddress().getCity());
		address.setStreet(patientDTO.getAddress().getStreet());
		address.setNumber(patientDTO.getAddress().getNumber());
		
		Address saved = addressService.create(address);
		
		Patient patient = new Patient();
		patient.setEmail(patientDTO.getEmail());
		patient.setPassword(patientDTO.getPassword());
		patient.setName(patientDTO.getName());
		patient.setLastName(patientDTO.getLastName());
		patient.setPhoneNumber(patientDTO.getPhoneNumber());
		patient.setAddress(saved);
		patient.setCategory(Category.REGULAR);
		patient.setLoyaltyPoints(0);
		patient.setPenaltyPoints(0);
		patient = patientService.create(patient);
		

		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.CREATED);
	}

}
