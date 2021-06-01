insert into address (city, country, number, street, latitude, longitude) values('Novi Sad','Srbija','43','Bulevar oslobođenja', 45.2596599, 19.8331895)
insert into address (city, country, number, street, latitude, longitude) values('Novi Sad','Srbija','26','Kisačka', 45.2625823, 19.8400889)
insert into address (city, country, number, street, latitude, longitude) values('Beograd','Srbija','16','Katanićeva', 44.7993622, 20.4699663)
insert into address (city, country, number, street) values('Beograd','Srbija','10','Njegoševa')
insert into address (city, country, number, street) values('Beograd','Srbija','110','Vojvode Stepe')
insert into address (city, country, number, street) values('Beograd','Srbija','89','Cetinjska')
insert into address (city, country, number, street) values('Beograd','Srbija','1','Marije Kiri')
insert into address (city, country, number, street) values('Beograd','Srbija','65','Kosovska')
insert into address (city, country, number, street) values('Novi Sad','Srbija','8','Železnicka')
insert into address (city, country, number, street) values('Novi Sad','Srbija','78','Jovana Subotića')
insert into address (city, country, number, street) values('Novi Sad','Srbija','32','Rumenačka')
insert into address (city, country, number, street) values('Novi Sad','Srbija','49','Fruškogorska')
insert into address (city, country, number, street) values('Novi Sad','Srbija','210','Kralja Petra')
insert into address (city, country, number, street) values('Novi Sad','Srbija','55','Bulevar Evrope')
insert into address (city, country, number, street) values('Novi Sad','Srbija','113','Vuka Karadžića')
insert into address (city, country, number, street) values('Novi Sad','Srbija','17','Dunavska')
insert into address (city, country, number, street) values('Novi Sad','Srbija','93','Maksima Gorkog')
insert into address (city, country, number, street) values('Novi Sad','Srbija','100','Stražilovska')
insert into address (city, country, number, street) values('Novi Sad','Srbija','1','Sutjeska')
insert into address (city, country, number, street) values('Beograd','Srbija','3','Bore Stankovića')
insert into address (city, country, number, street) values('Novi Sad','Srbija','43','Balzakova')
insert into address (city, country, number, street) values('Novi Sad', 'Srbija', '22', 'Balzakova')
insert into address (city, country, number, street) values('Novi Sad', 'Srbija', '25', 'Seljačkih buna')
insert into address (city, country, number, street) values('Novi Sad', 'Srbija', '33', 'Bulevar Vojvode Stepe')
insert into address (city, country, number, street) values('Novi Sad', 'Srbija', '29', 'Seljačkih buna')


insert into address (city, country, number, street) values('Novi Sad', 'Srbija', '33', 'Boze Kuzmanovica')

insert into roles (name) values ('ROLE_SYSTEM_ADMIN')
insert into roles (name) values ('ROLE_PHARMACY_ADMIN')
insert into roles (name) values ('ROLE_DERMATOLOGIST')
insert into roles (name) values ('ROLE_PHARMACIST')
insert into roles (name) values ('ROLE_SUPPLIER')
insert into roles (name) values ('ROLE_PATIENT')

insert into category_thresholds (category, threshold, discount, deleted) values ('REGULAR', 50, 0, false)
insert into category_thresholds (category, threshold, discount, deleted) values ('SILVER', 100, 2, false)
insert into category_thresholds (category, threshold, discount, deleted) values ('GOLD', 200, 5, false)


insert into pharmacy (deleted, description, income, name, address_id) values('false','Ono po čemu se BENU izdvaja na tržištu jeste konstantan rad sa zaposlenim farmaceutima na usavršavanju i upoznavanju najnovijih dostignuća medicine i farmakologije.', 0, 'Benu', 1)
insert into pharmacy (deleted, description, income, name, address_id) values('false','Dr.Max je međunarodni lanac apoteka, koji je prisutan u 6 zemalja Centralno Istočne Evrope sa preko 2000 apoteka i 12000 zaposlenih.', 0, 'Dr.Max', 2)
insert into pharmacy (deleted, description, income, name, address_id) values('false','Sa tradicijom dugom preko dve decenije, apoteke Laurus izgradile su prepoznatljiv identitet zasnovan na promociji zdravog života, sa ciljem prevencije bolesti i očuvanja pravilnih životnih navika. ', 0, 'Laurus', 3)

insert into admin_pharmacy (email, deleted, active, last_name, name, password, phone_number, address_id, pharmacy_id) values ('jovanpetrovic@gmail.com','false','true','Petrović','Jovan','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','060915893', 9, 1)
insert into admin_pharmacy (email, deleted, active, last_name, name, password, phone_number, address_id, pharmacy_id) values ('sarajovic@gmail.com','false','true','Jović','Sara','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0645515823', 4, 2)
insert into admin_pharmacy (email, deleted, active, last_name, name, password, phone_number, address_id, pharmacy_id) values ('markoperic@gmail.com','false','true','Perić','Marko','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0654123692', 14, 3)
insert into admin_pharmacy (email, deleted, active, last_name, name, password, phone_number, address_id, pharmacy_id) values ('filipovic.dada@gmail.com','false','true','Dragana','Filipovic','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0654123692', 25, null)

INSERT INTO USER_ROLE (user_id, role_id) VALUES ('jovanpetrovic@gmail.com', 2) 
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('sarajovic@gmail.com', 2) 
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('markoperic@gmail.com', 2)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('filipovic.dada@gmail.com', 2)

insert into admin_system (email, deleted, active, last_name, name, password, phone_number, address_id ) values ('milicamaric@gmail.com','false','true','Marić','Milica','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0635124793', 10)
insert into admin_system (email, deleted, active, last_name, name, password, phone_number, address_id ) values ('petarsavic@gmail.com','false','true','Savić','Petar','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0693206589', 5)
insert into admin_system (email, deleted, active, last_name, name, password, phone_number, address_id ) values ('kristinamisic@gmail.com','false','true','Mišić','Kristina','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','062153269', 15)

INSERT INTO USER_ROLE (user_id, role_id) VALUES ('milicamaric@gmail.com', 1)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('petarsavic@gmail.com', 1)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('kristinamisic@gmail.com', 1)

insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('anasimic@gmail.com','false','true','Simič','Ana','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0603258476', 19 , 'REGULAR', 0, 0)
insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('teodorabozic@gmail.com','false','true','Božić','Teodora','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0658563255',20 , 'SILVER', 56, 0)
insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('lukamarkovic@gmail.com','false','true','Marković','Luka','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0636258776', 21, 'GOLD', 220, 0)


insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('darkomilev@gmail.com','false','true','Milev','Darko','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0606258776', 22, 'REGULAR', 33, 3)
insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('milosmilosevic@gmail.com','false','true','Milošević','Miloš','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0616258776', 23, 'SILVER', 99, 0)
insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('ivicamarkov@gmail.com','false','true','Markov','Ivica','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0626258776', 10, 'GOLD', 299, 0)
insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('peraperic@gmail.com','false','true','Perić','Pera','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0646258776', 15, 'REGULAR', 33, 0)
insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('teodorabatinica@gmail.com','false','true','Batinica','Teodora','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0633434343', 16, 'SILVER', 65, 0)
insert into patient (email, deleted, active, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('anjastupar@gmail.com','false','true','Stupar','Anja','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0637676767', 17, 'GOLD', 243, 0)


INSERT INTO USER_ROLE (user_id, role_id) VALUES ('anjastupar@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('teodorabatinica@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('peraperic@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('ivicamarkov@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('milosmilosevic@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('darkomilev@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('lukamarkovic@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('teodorabozic@gmail.com', 6)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('anasimic@gmail.com', 6)

insert into patient_subscribed_pharmacies (patient_email, subscribed_pharmacies_id) values ('darkomilev@gmail.com', 1)
insert into patient_subscribed_pharmacies (patient_email, subscribed_pharmacies_id) values ('lukamarkovic@gmail.com', 2)
insert into patient_subscribed_pharmacies (patient_email, subscribed_pharmacies_id) values ('peraperic@gmail.com', 1)
insert into patient_subscribed_pharmacies (patient_email, subscribed_pharmacies_id) values ('teodorabatinica@gmail.com', 3)
insert into patient_subscribed_pharmacies (patient_email, subscribed_pharmacies_id) values ('anjastupar@gmail.com', 3)

insert into work_calendar (id) values (1)
insert into work_calendar (id) values (2)
insert into work_calendar (id) values (3)

insert into dermatologist (email, deleted, active, last_name, name, password, phone_number, address_id, calendar_id ) values ('janatot@gmail.com','false','true','Tot','Jana','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0665135793', 11, null)
insert into dermatologist (email, deleted, active, last_name, name, password, phone_number, address_id, calendar_id ) values ('aleksandarstevanovic@gmail.com','false','true','Stevanović','Aleksandar','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0605239833', 6, null)
insert into dermatologist (email, deleted, active, last_name, name, password, phone_number, address_id, calendar_id ) values ('lazarpopovic@gmail.com','false','true','Popović','Lazar','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','0614523981', 16, null)


INSERT INTO USER_ROLE (user_id, role_id) VALUES ('janatot@gmail.com', 3)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('aleksandarstevanovic@gmail.com', 3)
INSERT INTO USER_ROLE (user_id, role_id) VALUES ('lazarpopovic@gmail.com', 3)


insert into pharmacist (email, deleted, active, last_name, name, password, phone_number, address_id, calendar_id, pharmacy_id) values ('svetozartodorovic@gmail.com', false, 'true','Todorović', 'Svetozar', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '0645556464', 22, 1, 1)
insert into pharmacist (email, deleted, active, last_name, name, password, phone_number, address_id, calendar_id, pharmacy_id) values ('milanm@gmail.com', false,'true','Milanović', 'Milan', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '0621234123', 23, 2,  2)
insert into pharmacist (email, deleted, active, last_name, name, password, phone_number, address_id, calendar_id, pharmacy_id) values ('micicmilica@gmail.com', false,'true', 'Micić', 'Milica', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '0609879879', 24, 3, 3)


insert into e_prescription (date, patient_email, deleted, done, pharmacy_id, taken_date, price) values ('2021-04-25 10:00','anasimic@gmail.com', false, false, 2, '2021-04-23', 2500.00)
insert into e_prescription (date, patient_email, deleted, done, pharmacy_id, taken_date, price) values ('2021-06-30 10:00','anasimic@gmail.com', false, false, 1, null, 3000.00)
insert into e_prescription (date, patient_email, deleted, done, pharmacy_id, taken_date, price) values ('2021-04-07 10:00','anasimic@gmail.com', false, false, 2, '2021-04-07', 1000.00)

insert into e_prescription (date, patient_email, deleted, done, pharmacy_id, taken_date, price) values ('2021-07-08 10:00','anjastupar@gmail.com', false, false, 3, null, 700.00)
insert into e_prescription (date, patient_email, deleted, done, pharmacy_id, taken_date, price) values ('2021-07-09 10:00','teodorabatinica@gmail.com', false, false, 3, null, 1800.00)
insert into e_prescription (date, patient_email, deleted, done, pharmacy_id, taken_date, price) values ('2021-07-10 10:00','peraperic@gmail.com', false, false, 3, null, 650.00)


insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-01 14:28', 'false','Izvestaj..' ,1)
insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-05 10:35','false','Pacijent pokazuje znake oporavka.', 2)
insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-06 19:40','false','Na pregledu je rađeno xyz. Pacijentu se preporučuje da pored prepisanog konzumira i vitamin C.' ,3)

insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-06 19:50','false','Izvestaj...' ,4)
insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-06 19:10','false','Izvestaj...' ,5)
insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-06 19:20','false','Izvestaj...' ,6)

-- 0 - counseling, 1 - examination
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-06',false,'14:00','14:30', 1, null, 'janatot@gmail.com', 'teodorabozic@gmail.com')
--insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-04-01','false','14:00','14:30', 1, null, 'janatot@gmail.com', null)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-06',false,'15:00','15:30', 1, null, 'janatot@gmail.com', 'anasimic@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-04-01',false,'17:00','18:00', 1, null, 'janatot@gmail.com', null)

insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-25',false,'10:00','10:30', 1, 2, 'aleksandarstevanovic@gmail.com', 'teodorabozic@gmail.com', true)

insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-06',false,'19:00','19:45', 0, 3, 'svetozartodorovic@gmail.com', 'darkomilev@gmail.com', true)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-07',false,'18:00','19:00', 0, null, 'svetozartodorovic@gmail.com', 'anasimic@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-28',false,'16:00','17:00', 0, null, 'svetozartodorovic@gmail.com', 'anasimic@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-25',false,'15:00','16:00', 0, null, 'svetozartodorovic@gmail.com', null)

insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-26',false,'20:00','20:45', 1, 4, 'lazarpopovic@gmail.com', 'lukamarkovic@gmail.com', true)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-07',false,'16:00','16:45', 0, 5, 'milanm@gmail.com', 'milosmilosevic@gmail.com', true)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-08',false,'17:00','17:45', 0, 6, 'micicmilica@gmail.com', 'ivicamarkov@gmail.com', true)

-- svi imaju isti MedicalReport (obrisati ako bude trebalo)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-23',false,'10:00','10:30', 1, 3, 'aleksandarstevanovic@gmail.com', 'anasimic@gmail.com', true)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-15',false,'17:45','18:50', 1, 4, 'aleksandarstevanovic@gmail.com', 'anasimic@gmail.com', true)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email, done) values ('2021-04-05',false,'11:30','12:40', 1, 5, 'aleksandarstevanovic@gmail.com', 'anasimic@gmail.com', true)

insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-05-26',false,'15:00','16:00', 1, null, 'aleksandarstevanovic@gmail.com', 'teodorabozic@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-05-17',false,'14:00','15:00', 1, null, 'aleksandarstevanovic@gmail.com', 'lukamarkovic@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-05-24',false,'09:00','09:45', 1, null, 'aleksandarstevanovic@gmail.com', 'darkomilev@gmail.com')

insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-07-26',false,'16:30','17:00', 1, null, 'aleksandarstevanovic@gmail.com', 'teodorabozic@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-07-17',false,'13:00','14:00', 1, null, 'aleksandarstevanovic@gmail.com', 'lukamarkovic@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-24',false,'08:45','09:00', 1, null, 'aleksandarstevanovic@gmail.com', 'darkomilev@gmail.com')
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-07-07',false,'18:00','19:00', 1, null, 'aleksandarstevanovic@gmail.com', null)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-05-24',false,'08:45','09:00', 1, null, 'janatot@gmail.com', null)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-07',false,'18:00','19:00', 0, null, 'svetozartodorovic@gmail.com', null)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-05-24',false,'08:45','09:00', 0, null, 'micicmilica@gmail.com', null)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id, chosen_employee_email, patient_email) values ('2021-06-07',false,'18:00','19:00', 0, null, 'milanm@gmail.com', null)


insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('janatot@gmail.com', 1)
insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('aleksandarstevanovic@gmail.com', 1)
insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('aleksandarstevanovic@gmail.com', 2)
insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('lazarpopovic@gmail.com', 3)
insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('janatot@gmail.com', 3)

insert into medicament (id, annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type, points) values (1, 'Lek Trandolapril PharmaS ne smete uzimati ako ste alergični (preosetljivi) na trandolapril ili na bilo koju od pomoćnih supstanci ovog leka','false' , 1,'PHARMAS D.O.O. BEOGRAD', 1,'Trandolapril PharmaS',' gvožđe(III)-oksid, žuti (E 172); eritrozin (E 127); titan-dioksid (E 171); natrijum-laurilsulfat;
želatin' , 'Humani lekovi', 2)
insert into medicament (id, annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type, points) values (2, 'Pacijenti koji boluju od retkog naslednog oboljenja netolerancije galaktoze, Lapp laktaznog deficita ili loše
glukozno-galaktozne apsorpcije ne bi trebalo koristiti ovaj lek zbog sadržaja laktoze. ','false' , 1,'FAMAR A.V.E. ANTHOUSSA PLANT ', 2,'Brufen','celuloza, mikrokristalna;kroskarmeloza-natrijum;
laktoza, monohidrat;silicijum-dioksid koloidni bezvodni;natrijum-lauril sulfat;magnezijum-stearat' , 'Humani lekovi', 3)
insert into medicament (id, annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type, points) values (3, 'Lek Buscopan ne smete koristiti ako ste alergični (preosetljivi) na hioscin-butilbromid ili neku drugu komponentu leka, ukoliko patite od mijastenije gravis (bolest koju karakteriše izrazita slabost mišića ili čak nemogućnost kretanja tela),','false' , 1,'Delpharm Reims ', 2,'Buscopan','Kalcijum-hidrogenfosfat, bezvodni; kukuruzni skrob,osušen; kukuruzni skrob, rastvorni; Silicijum-dioksid,
koloidni, bezvodni; Vinska kiselina; Stearinska kiselina.' , 'Humani lekovi', 1)
insert into medicament (id,annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type, points) values (4, 'Lek Prospan ne smete uzimati ako ste alergični (preosetljivi) na bršljan ili na bilo koju pomoćnu supstancu ovog leka','false', 1,'ENGELHARD ARZNEIMITTEL GMBH &CO.KG',  7,'Prospan','Aktivna supstanca je: bršljan, suvi ekstrakt lista
1 mL sirupa sadrži 7 mg suvog ekstrakta lista bršljana.Pomoćne supstance su: kalijum-sorbat; limunska kiselina, bezvodna; ksantan guma; aroma trešnje.' , 'Humani lekovi', 2)
insert into medicament (id, annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type, points) values (5, 'Kada uzimate lek Andol posebno vodite računa: ako ste preosetljivi (alergični) na druge lekove protiv bolova, zapaljenja (antiinflamatorna sredstva) ili
na neke druge alergene, ako ste ranije imali čir na želucu ili crevu ili želudačnocrevna krvarenja. ','false' , 1,'Pliva Hrvatska d.o.o.', 1,'Andol','Aktivna supstanca je acetilsalicilna kiselina.Sadržaj pomoćnih supstanci: magnezijum-oksid, teški; skrob, kukuruzni; želatin; talk; skrob, kukuruzni, preželatinovani; celuloza, mikrokristalna; silicijum dioksid, koloidni, bezvodni ' , 'Humani lekovi', 2)

insert into medicament_item (deleted, quantity, medicament_id) values ('false', 10, 1)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 50, 2)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 25, 1)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 100, 4)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 10, 5)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 5, 3)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 6, 4)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 90, 5)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 5, 3)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 6, 2)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 20, 1)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 2, 1)

insert into medicament_substitute_medicaments (medicament_id, substitute_medicaments_id) values (1, 5)
insert into medicament_substitute_medicaments (medicament_id, substitute_medicaments_id) values (5, 1)
insert into medicament_substitute_medicaments (medicament_id, substitute_medicaments_id) values (1, 4)
insert into medicament_substitute_medicaments (medicament_id, substitute_medicaments_id) values (4, 1)

insert into complaint (deleted, description, employee_email, pharmacy_id, responder_email, response, patient_email) values ('false','Žalba na rad apoteke Benu.', null, 1, null, null, 'anasimic@gmail.com' )
insert into complaint (deleted, description, employee_email, pharmacy_id, responder_email, response, patient_email) values ('false','Žalba na rad dermatologa.', 'janatot@gmail.com', null, null, null, 'teodorabozic@gmail.com')
insert into complaint (deleted, description, employee_email, pharmacy_id, responder_email, response, patient_email) values ('true','Žalba na rad apoteke Dr.Max. Cesto nema potrebnih lijekova', null, 2,'milicamaric@gmail.com', 'U apoteci bio manjak ljudi, sve je sredjeno i radice u najboljem redu.', 'lukamarkovic@gmail.com')

insert into patient_allergies (patient_email, allergies_id) values ('anasimic@gmail.com', 2)
insert into patient_allergies (patient_email, allergies_id) values ('lukamarkovic@gmail.com', 3)




insert into patient_complaints (patient_email, complaints_id) values ('anasimic@gmail.com', 1)
insert into patient_complaints (patient_email, complaints_id) values ('teodorabozic@gmail.com', 2)
insert into patient_complaints (patient_email, complaints_id) values ('lukamarkovic@gmail.com', 3)



insert into patient_e_prescriptions (patient_email, e_prescriptions_id) values ('anasimic@gmail.com', 1)
insert into patient_e_prescriptions (patient_email, e_prescriptions_id) values ('teodorabatinica@gmail.com', 3)
insert into patient_e_prescriptions (patient_email, e_prescriptions_id) values ('lukamarkovic@gmail.com', 5)

insert into vacation (date_from, date_to, deleted, employee_email) values ('2021-04-04', '2021-04-20', false, 'janatot@gmail.com')
insert into vacation (date_from, date_to, deleted, employee_email) values ('2021-05-04', '2021-05-20', false, 'svetozartodorovic@gmail.com')
insert into vacation (date_from, date_to, deleted, employee_email) values ('2021-05-10', '2021-05-24', false, 'micicmilica@gmail.com')


insert into work_calendar_vacations (work_calendar_id, vacations_id) values (1, 1)
insert into work_calendar_vacations (work_calendar_id, vacations_id) values (2, 2)
insert into work_calendar_vacations (work_calendar_id, vacations_id) values (3, 3)

insert into work_calendar_appointments (work_calendar_id, appointments_id) values (1, 1)
insert into work_calendar_appointments (work_calendar_id, appointments_id) values (2, 2)
insert into work_calendar_appointments (work_calendar_id, appointments_id) values (3, 3)


insert into user_role(user_id, role_id) values ('svetozartodorovic@gmail.com', 4)
insert into user_role(user_id, role_id) values ('milanm@gmail.com', 4)
insert into user_role(user_id, role_id) values ('micicmilica@gmail.com', 4)

insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 1)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 2)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 21)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 4)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 3)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 15)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 16)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 17)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 18)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 19)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 20)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (3, 22)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 23)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (3, 24)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 25)

-- provereno korektno
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 5)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 6)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 7)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (1, 8)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (3, 9)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 10)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (3, 11)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 12)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 13)
insert into pharmacy_appointments (pharmacy_id, appointments_id) values (2, 14)

insert into pharmacy_medicament_items (pharmacy_id, medicament_items_id) values (1, 1)
insert into pharmacy_medicament_items (pharmacy_id, medicament_items_id) values (1, 2)
insert into pharmacy_medicament_items (pharmacy_id, medicament_items_id) values (2, 3)
insert into pharmacy_medicament_items (pharmacy_id, medicament_items_id) values (2, 4)
insert into pharmacy_medicament_items (pharmacy_id, medicament_items_id) values (3, 5)
insert into pharmacy_medicament_items (pharmacy_id, medicament_items_id) values (3, 6)



insert into supplier (email, deleted, active, last_name, name, password, phone_number, address_id) values ('mikaantic@gmail.com', 'false','true', 'Antic', 'Mika', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '0624354231', 21)
insert into supplier (email, deleted, active, last_name, name, password, phone_number, address_id) values ('svetlanaraznatovic@gmail.com', 'false', 'true', 'Raznatovic', 'Svetlana', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '0604354231', 20)
insert into supplier (email, deleted, active, last_name, name, password, phone_number, address_id) values ('lupulovb@gmail.com', 'false','true', 'Lupulov', 'Bojan', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '0614354231', 19)

insert into supplier (email, deleted, active, last_name, name, password, phone_number, address_id) values ('nikolinatosic999@gmail.com', 'false','true', 'Tosic', 'Nikolina', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '0614354231', 25)


insert into user_role(user_id, role_id) values ('mikaantic@gmail.com', 5)
insert into user_role(user_id, role_id) values ('svetlanaraznatovic@gmail.com', 5)
insert into user_role(user_id, role_id) values ('lupulovb@gmail.com', 5)
insert into user_role(user_id, role_id) values ('nikolinatosic999@gmail.com', 5)

insert into order_t (deadline, deleted, status, admin_email, supplier_email) values ('2021-04-04','false', 0,'jovanpetrovic@gmail.com' , 'mikaantic@gmail.com')
insert into order_t (deadline, deleted, status, admin_email, supplier_email) values ('2021-06-08','false', 1,'sarajovic@gmail.com', 'svetlanaraznatovic@gmail.com')
insert into order_t (deadline, deleted, status, admin_email, supplier_email) values ('2021-06-11','false', 0,'markoperic@gmail.com', 'lupulovb@gmail.com')
insert into order_t (deadline, deleted, status, admin_email, supplier_email) values ('2021-06-08','false', 1,'sarajovic@gmail.com', 'nikolinatosic999@gmail.com')

insert into pharmacy_orders (pharmacy_id, orders_id) values (1, 1)
insert into pharmacy_orders (pharmacy_id, orders_id) values (2, 2)
insert into pharmacy_orders (pharmacy_id, orders_id) values (3, 3)
insert into pharmacy_orders (pharmacy_id, orders_id) values (2, 4)

insert into rating (value, patient_email) values (2, 'anasimic@gmail.com')
insert into rating (value, patient_email) values (4, 'teodorabozic@gmail.com')
insert into rating (value, patient_email) values (5, 'anjastupar@gmail.com')
insert into rating (value, patient_email) values (4, 'anjastupar@gmail.com')
insert into rating (value, patient_email) values (3, 'teodorabozic@gmail.com')
insert into rating (value, patient_email) values (5, 'ivicamarkov@gmail.com')
insert into rating (value, patient_email) values (5, 'ivicamarkov@gmail.com')
insert into rating (value, patient_email) values (4, 'darkomilev@gmail.com')
insert into rating (value, patient_email) values (1, 'peraperic@gmail.com')
insert into rating (value, patient_email) values (2, 'ivicamarkov@gmail.com')
insert into rating (value, patient_email) values (4, 'teodorabatinica@gmail.com')
insert into rating (value, patient_email) values (4, 'anasimic@gmail.com')
insert into rating (value, patient_email) values (2, 'anasimic@gmail.com')
insert into rating (value, patient_email) values (3, 'anasimic@gmail.com')

insert into pharmacy_ratings (pharmacy_id, ratings_id) values (1, 1)
insert into pharmacy_ratings (pharmacy_id, ratings_id) values (2, 2)
insert into pharmacy_ratings (pharmacy_id, ratings_id) values (1, 3)
insert into pharmacy_ratings (pharmacy_id, ratings_id) values (3, 13)

insert into request (accepted, date_from, date_to, deleted, description, rejection_reason, employee_email) values (true, '2021-03-03', '2021-04-03', false, 'Zahtev za godisnji odmor', '', 'janatot@gmail.com')
insert into request (accepted, date_from, date_to, deleted, description, rejection_reason, employee_email) values (false, '2021-04-03', '2021-05-04', false, 'Zahtev za odsustvo', '', 'svetozartodorovic@gmail.com')
insert into request (accepted, date_from, date_to, deleted, description, rejection_reason, employee_email) values (true, '2021-04-03', '2021-05-04', false, 'Zahtev za bolovanje', '', 'micicmilica@gmail.com')

insert into pharmacy_requests (pharmacy_id, requests_id) values (1, 1)
insert into pharmacy_requests (pharmacy_id, requests_id) values (2, 2)
insert into pharmacy_requests (pharmacy_id, requests_id) values (3, 3)


insert into pharmacy_vacations (pharmacy_id, vacations_id) values (1, 1)
insert into pharmacy_vacations (pharmacy_id, vacations_id) values (2, 2)
insert into pharmacy_vacations (pharmacy_id, vacations_id) values (3, 3)

insert into prescription_medicament (deleted, expiry_date, purchased, quantity, medicament_id) values (false, '2021-04-28', false, 200, 1)
insert into prescription_medicament (deleted, expiry_date, purchased, quantity, medicament_id) values (false, '2023-02-02', false, 300, 2)
insert into prescription_medicament (deleted, expiry_date, purchased, quantity, medicament_id) values (false, '2022-06-12', false, 130, 3)


insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 5, 899.99, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 6, 1199.99, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 7, 999.99, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 0, 1500.00, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 1, 99.99, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 1, 129.99, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 0, 2000.00, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 0, 2500.00, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 0, 1700.00, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 0, 2200.00, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-05-24', null, false, 0, 550.00, false)
insert into price (date_from, date_to, deleted, points, value, promotion) values ('2021-01-01', '2022-01-01', false, 0, 220.00, false)


insert into patient_reserved_medicaments (patient_email, reserved_medicaments_id) values ('anasimic@gmail.com', 1)
insert into patient_reserved_medicaments (patient_email, reserved_medicaments_id) values ('anasimic@gmail.com', 2)
insert into patient_reserved_medicaments (patient_email, reserved_medicaments_id) values ('anasimic@gmail.com', 3)

insert into pricelist_item_appointment (appointment, pharmacy_id) values (0, 1)
insert into pricelist_item_appointment (appointment, pharmacy_id) values (1, 1)
insert into pricelist_item_appointment (appointment, pharmacy_id) values (0, 2)
insert into pricelist_item_appointment (appointment, pharmacy_id) values (1, 2)
insert into pricelist_item_appointment (appointment, pharmacy_id) values (0, 3)
insert into pricelist_item_appointment (appointment, pharmacy_id) values (1, 3)

insert into pricelist_item_medicament (medicament_id, pharmacy_id) values (1, 1)
insert into pricelist_item_medicament (medicament_id, pharmacy_id) values (3, 2)
insert into pricelist_item_medicament (medicament_id, pharmacy_id) values (1, 3)
insert into pricelist_item_medicament (medicament_id, pharmacy_id) values (2, 1)
insert into pricelist_item_medicament (medicament_id, pharmacy_id) values (1, 2)


insert into pricelist_item_medicament_price(pricelist_item_medicament_id, price_id) values (1,1)
insert into pricelist_item_medicament_price(pricelist_item_medicament_id, price_id) values (2,2)
insert into pricelist_item_medicament_price(pricelist_item_medicament_id, price_id) values (3,3)
insert into pricelist_item_medicament_price(pricelist_item_medicament_id, price_id) values (5,12)


insert into pricelist_item_appointment_price(pricelist_item_appointment_id, price_id) values (1,4)
insert into pricelist_item_appointment_price(pricelist_item_appointment_id, price_id) values (2,5)
insert into pricelist_item_appointment_price(pricelist_item_appointment_id, price_id) values (3,7)
insert into pricelist_item_appointment_price(pricelist_item_appointment_id, price_id) values (4,8)
insert into pricelist_item_appointment_price(pricelist_item_appointment_id, price_id) values (5,9)
insert into pricelist_item_appointment_price(pricelist_item_appointment_id, price_id) values (6,10)

insert into supplier_medicament_items (supplier_email, medicament_items_id) values ('mikaantic@gmail.com', 1)
insert into supplier_medicament_items (supplier_email, medicament_items_id) values ('svetlanaraznatovic@gmail.com', 2)
insert into supplier_medicament_items (supplier_email, medicament_items_id) values ('lupulovb@gmail.com', 3)



insert into medicament_ratings (medicament_id, ratings_id) values (1, 4)
insert into medicament_ratings (medicament_id, ratings_id) values (2, 5)
insert into medicament_ratings (medicament_id, ratings_id) values (3, 6)
insert into medicament_ratings (medicament_id, ratings_id) values (4, 7)
insert into medicament_ratings (medicament_id, ratings_id) values (4, 8)


insert into employee_ratings (employee_email, ratings_id ) values ('janatot@gmail.com', 9)
insert into employee_ratings (employee_email, ratings_id ) values ('lazarpopovic@gmail.com', 10)
insert into employee_ratings (employee_email, ratings_id ) values ('milamilic@gmail.com', 11)
insert into employee_ratings (employee_email, ratings_id ) values ('aleksandarstevanovic@gmail.com', 12)
insert into employee_ratings (employee_email, ratings_id ) values ('milanm@gmail.com', 13)

insert into e_prescription_prescription_medicaments (e_prescription_id, prescription_medicaments_id) values (1, 1)
insert into e_prescription_prescription_medicaments (e_prescription_id, prescription_medicaments_id) values (2, 2)
insert into e_prescription_prescription_medicaments (e_prescription_id, prescription_medicaments_id) values (3, 3)

insert into offer (deadline, status, total_price, order_id, supplier_email, accepted_date) values ('2021-01-05', 2, 10000, 1, 'mikaantic@gmail.com', null)
insert into offer (deadline, status, total_price, order_id, supplier_email, accepted_date) values ('2021-01-10', 2, 100000, 3, 'lupulovb@gmail.com', null)


insert into offer (deadline, status, total_price, order_id, supplier_email, accepted_date) values ('2021-01-10', 2, 100000, 4, 'nikolinatosic999@gmail.com', null)
insert into offer (deadline, status, total_price, order_id, supplier_email, accepted_date) values ('2021-01-10', 2, 500, 4, 'mikaantic@gmail.com', null)

insert into offer (deadline, status, total_price, order_id, supplier_email, accepted_date) values ('2021-01-10', 2, 500, 2, 'nikolinatosic999@gmail.com', null)


insert into order_t_medicament_items (order_id, medicament_items_id) values (1, 6)
insert into order_t_medicament_items (order_id, medicament_items_id) values (2, 7)
insert into order_t_medicament_items (order_id, medicament_items_id) values (3, 8)
insert into order_t_medicament_items (order_id, medicament_items_id) values  (1, 10)
insert into order_t_medicament_items (order_id, medicament_items_id) values (2, 11)
insert into order_t_medicament_items (order_id, medicament_items_id) values  (3, 9)
insert into order_t_medicament_items (order_id, medicament_items_id) values  (4, 12)

insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (0, false, '08:00', '12:00', 1)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (1, false, '08:00', '12:00', 1)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (2, false, '08:00', '12:00', 1)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (3, false, '08:00', '12:00', 1)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (4, false, '08:00', '12:00', 1)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (5, false, '08:00', '12:00', 1)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (6, false, '08:00', '12:00', 1)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (2, false, '08:00', '12:00', 2)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (2, false, '08:00', '12:00', 2)
insert into work_hour(day, deleted, work_hour_from, work_hour_to, pharmacy_id) values (2, false, '10:00', '12:00', 1)

insert into employee_work_hour(employee_email, work_hour_id) values ('svetozartodorovic@gmail.com', 1)
insert into employee_work_hour(employee_email, work_hour_id) values ('svetozartodorovic@gmail.com', 2)
insert into employee_work_hour(employee_email, work_hour_id) values ('svetozartodorovic@gmail.com', 3)
insert into employee_work_hour(employee_email, work_hour_id) values ('svetozartodorovic@gmail.com', 4)
insert into employee_work_hour(employee_email, work_hour_id) values ('svetozartodorovic@gmail.com', 5)
insert into employee_work_hour(employee_email, work_hour_id) values ('svetozartodorovic@gmail.com', 6)
insert into employee_work_hour(employee_email, work_hour_id) values ('svetozartodorovic@gmail.com', 7)
insert into employee_work_hour(employee_email, work_hour_id) values ('aleksandarstevanovic@gmail.com', 8)
insert into employee_work_hour(employee_email, work_hour_id) values ('milanm@gmail.com', 9)
insert into employee_work_hour(employee_email, work_hour_id) values ('janatot@gmail.com', 10)
 
insert into appointment_points(type, points) values(0, 2)
insert into appointment_points(type, points) values(1, 3)


insert into request_medicament(accepted, quantity, admin_email,employee_email,medicament_id) values (false,10,'sarajovic@gmail.com','aleksandarstevanovic@gmail.com',3)
insert into request_medicament(accepted, quantity, admin_email,employee_email,medicament_id) values (false,2,'sarajovic@gmail.com','milanm@gmail.com',4)
