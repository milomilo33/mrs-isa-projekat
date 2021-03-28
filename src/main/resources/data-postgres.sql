insert into address (city, country, number, street) values('Novi Sad','Srbija','43','Bulevar oslobođenja')
insert into address (city, country, number, street) values('Novi Sad','Srbija','26','Kisačka')
insert into address (city, country, number, street) values('Beograd','Srbija','16','Katanićeva')
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

insert into category_thresholds (category, threshold) values (0, 50)
insert into category_thresholds (category, threshold) values (1, 100)
insert into category_thresholds (category, threshold) values (2, 200)


insert into pharmacy (deleted, description, income, name, address_id) values('false','Benu apoteka...', 0, 'Benu', 1)
insert into pharmacy (deleted, description, income, name, address_id) values('false','Dr.Max apoteka...', 0, 'Dr.Max', 2)
insert into pharmacy (deleted, description, income, name, address_id) values('false','Laurus apoteka...', 0, 'Laurus', 3)

insert into admin_pharmacy (email, deleted, last_name, name, password, phone_number, address_id, pharmacy_id) values ('jovanpetrovic@gmail.com','false','Petrović','Jovan','admin1','060915893', 9, 1)
insert into admin_pharmacy (email, deleted, last_name, name, password, phone_number, address_id, pharmacy_id) values ('sarajovic@gmail.com','false','Jović','Sara','admin2','0645515823', 4, 2)
insert into admin_pharmacy (email, deleted, last_name, name, password, phone_number, address_id, pharmacy_id) values ('markoperic@gmail.com','false','Perić','Marko','admin3','0654123692', 14, 3)


insert into admin_system (email, deleted, last_name, name, password, phone_number, address_id ) values ('milicamaric@gmail.com','false','Marić','Milica','admin1','0635124793', 10)
insert into admin_system (email, deleted, last_name, name, password, phone_number, address_id ) values ('petarsavic@gmail.com','false','Savić','Petar','admin2','0693206589', 5)
insert into admin_system (email, deleted, last_name, name, password, phone_number, address_id ) values ('kristinamisic@gmail.com','false','Mišić','Kristina','admin3','062153269', 15)


insert into patient (email, deleted, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('anasimic@gmail.com','false','Simič','Ana','patient1','0603258476', 19 , 0, 0, 0)
insert into patient (email, deleted, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('teodorabozic@gmail.com','false','Božić','Teodora','patient2','0658563255',20 , 0, 0, 0)
insert into patient (email, deleted, last_name, name, password, phone_number, address_id, category, loyalty_points, penalty_points) values ('lukamarkovic@gmail.com','false','Marković','Luka','patient3','0636258776', 21, 0, 0, 0)


insert into dermatologist (email, deleted, last_name, name, password, phone_number, address_id, calendar_id ) values ('janatot@gmail.com','false','Tot','Jana','dermatolog1','0665135793', 11, null)
insert into dermatologist (email, deleted, last_name, name, password, phone_number, address_id, calendar_id ) values ('aleksandarstevanovic@gmail.com','false','Stevanović','Aleksandar','dermatolog2','0605239833', 6, null)
insert into dermatologist (email, deleted, last_name, name, password, phone_number, address_id, calendar_id ) values ('lazarpopovic@gmail.com','false','Popović','Lazar','dermatolog3','0614523981', 16, null)


insert into e_prescription (date, patient_email) values ('2021-04-02 10:00','anasimic@gmail.com')
insert into e_prescription (date, patient_email) values ('2021-04-06 10:00','teodorabozic@gmail.com')
insert into e_prescription (date, patient_email) values ('2021-04-07 10:00','lukamarkovic@gmail.com')


insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-01 14:28', 'false','Izvestaj..' ,1)
insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-05 10:35','false','Izvestaj.. ', 2)
insert into medical_report (date, deleted, description, eprescription_id) values ('2021-04-06 19:40','false','Izvestaj...' ,3)



insert into appointment (date, deleted, term_from, term_to, type, medical_report_id) values ('2021-04-01','false','14:00','14:30', 0, 1)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id) values ('2021-04-05','false','10:00','10:30', 1, 2)
insert into appointment (date, deleted, term_from, term_to, type, medical_report_id) values ('2021-04-06','false','19:00','19:45', 0, 3)



insert into dermatologist_medical_examinations (dermatologist_email, medical_examinations_id) values ('janatot@gmail.com', 1)
insert into dermatologist_medical_examinations (dermatologist_email, medical_examinations_id) values ('aleksandarstevanovic@gmail.com', 2)
insert into dermatologist_medical_examinations (dermatologist_email, medical_examinations_id) values ('lazarpopovic@gmail.com', 3)


insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('janatot@gmail.com', 1)
insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('aleksandarstevanovic@gmail.com', 2)
insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('lazarpopovic@gmail.com', 3)
insert into dermatologists_pharmacies (dermatologist_id, pharmacy_id) values ('janatot@gmail.com', 3)

insert into employment (deleted, work_hour_from, work_hour_to, dermatologist_email, pharmacy_id) values ('false', '08:00', '12:00','janatot@gmail.com', 1 )
insert into employment (deleted, work_hour_from, work_hour_to, dermatologist_email, pharmacy_id) values ('false', '16:00', '20:00','janatot@gmail.com', 3 )
insert into employment (deleted, work_hour_from, work_hour_to, dermatologist_email, pharmacy_id) values ('false', '08:00','15:00','aleksandarstevanovic@gmail.com', 2 )
insert into employment (deleted, work_hour_from, work_hour_to, dermatologist_email, pharmacy_id) values ('false', '08:00','15:00','lazarpopovic@gmail.com', 3 )

insert into medicament (annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type) values ( 'Lek Trandolapril PharmaS ne smete uzimati ako ste alergični (preosetljivi) na trandolapril ili na bilo koju od pomoćnih supstanci ovog leka','false' , 1,'PHARMAS D.O.O. BEOGRAD', 1,'Trandolapril PharmaS',' gvožđe(III)-oksid, žuti (E 172); eritrozin (E 127); titan-dioksid (E 171); natrijum-laurilsulfat;
želatin' , 'Humani lekovi')
insert into medicament (annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type) values ( 'Pacijenti koji boluju od retkog naslednog oboljenja netolerancije galaktoze, Lapp laktaznog deficita ili loše
glukozno-galaktozne apsorpcije ne bi trebalo koristiti ovaj lek zbog sadržaja laktoze. ','false' , 1,'FAMAR A.V.E. ANTHOUSSA PLANT ', 2,'Brufen','celuloza, mikrokristalna;kroskarmeloza-natrijum;
laktoza, monohidrat;silicijum-dioksid koloidni bezvodni;natrijum-lauril sulfat;magnezijum-stearat' , 'Humani lekovi')
insert into medicament (annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type) values ( 'Lek Buscopan ne smete koristiti ako ste alergični (preosetljivi) na hioscin-butilbromid ili neku drugu komponentu leka, ukoliko patite od mijastenije gravis (bolest koju karakteriše izrazita slabost mišića ili čak nemogućnost kretanja tela),','false' , 1,'Delpharm Reims ', 2,'Buscopan','Kalcijum-hidrogenfosfat, bezvodni; kukuruzni skrob,osušen; kukuruzni skrob, rastvorni; Silicijum-dioksid,
koloidni, bezvodni; Vinska kiselina; Stearinska kiselina.' , 'Humani lekovi')
insert into medicament (annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type) values ( 'Lek Prospan ne smete uzimati ako ste alergični (preosetljivi) na bršljan ili na bilo koju pomoćnu supstancu ovog leka','false', 1,'ENGELHARD ARZNEIMITTEL GMBH &CO.KG',  7,'Prospan','Aktivna supstanca je: bršljan, suvi ekstrakt lista
1 mL sirupa sadrži 7 mg suvog ekstrakta lista bršljana.Pomoćne supstance su: kalijum-sorbat; limunska kiselina, bezvodna; ksantan guma; aroma trešnje.' , 'Humani lekovi')
insert into medicament (annotations, deleted, issuance_mode, manufacturer, medicament_form, name, structure, type) values ( 'Kada uzimate lek Andol posebno vodite računa: ako ste preosetljivi (alergični) na druge lekove protiv bolova, zapaljenja (antiinflamatorna sredstva) ili
na neke druge alergene, ako ste ranije imali čir na želucu ili crevu ili želudačnocrevna krvarenja. ','false' , 1,'Pliva Hrvatska d.o.o.', 1,'Andol','Aktivna supstanca je acetilsalicilna kiselina.Sadržaj pomoćnih supstanci: magnezijum-oksid, teški; skrob, kukuruzni; želatin; talk; skrob, kukuruzni, preželatinovani; celuloza, mikrokristalna; silicijum dioksid, koloidni, bezvodni ' , 'Humani lekovi')

insert into medicament_item (deleted, quantity, medicament_id) values ('false', 10, 1)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 50, 2)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 25, 3)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 100, 4)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 10, 5)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 5, 3)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 6, 4)
insert into medicament_item (deleted, quantity, medicament_id) values ('false', 90, 5)


insert into medicament_substitute_medicaments (medicament_id, substitute_medicaments_id) values (1, 5)
insert into medicament_substitute_medicaments (medicament_id, substitute_medicaments_id) values (5, 1)

insert into complaint (deleted, description, employee_email, pharmacy_id, responder_email) values ('false','Žalba na rad apoteke Benu.', null, 1, null)
insert into complaint (deleted, description, employee_email, pharmacy_id, responder_email) values ('false','Žalba na rad dermatologa.', 'janatot@gmail.com', null, null)
insert into complaint (deleted, description, employee_email, pharmacy_id, responder_email) values ('false','Žalba na rad apoteke Dr.Max.', null, 2, null)

insert into patient_allergies (patient_email, allergies_id) values ('anasimic@gmail.com', 2)
insert into patient_allergies (patient_email, allergies_id) values ('lukamarkovic@gmail.com', 3)


insert into patient_appointments (patient_email, appointments_id) values ('anasimic@gmail.com', 1)
insert into patient_appointments (patient_email, appointments_id) values ('teodorabozic@gmail.com', 2)
insert into patient_appointments (patient_email, appointments_id) values ('lukamarkovic@gmail.com', 3)

insert into patient_complaints (patient_email, complaints_id) values ('anasimic@gmail.com', 1)
insert into patient_complaints (patient_email, complaints_id) values ('teodorabozic@gmail.com', 2)
insert into patient_complaints (patient_email, complaints_id) values ('lukamarkovic@gmail.com', 3)




/*insert into e_prescription_prescription_medicaments (e_prescription_id, prescription_medicaments_id) values (1, )
insert into e_prescription_prescription_medicaments (e_prescription_id, prescription_medicaments_id) values (2, )
insert into e_prescription_prescription_medicaments (e_prescription_id, prescription_medicaments_id) values (3, )

insert into employee_ratings (employee_email, ratings_id ) values ('janatot@gmail.com', )
insert into employee_ratings (employee_email, ratings_id ) values ('lazarpopovic@gmail.com',)
insert into employee_ratings (employee_email, ratings_id ) values ('milamilic@gmail.com', )

insert into medicament_ratings (medicament_id, ratings_id) values (1,)
insert into medicament_ratings (medicament_id, ratings_id) values (2,)
insert into medicament_ratings (medicament_id, ratings_id) values (3,)
insert into medicament_ratings (medicament_id, ratings_id) values (4,)
insert into medicament_ratings (medicament_id, ratings_id) values (4,)


insert into offer (deadline, status, total_price, order_id, supplier_email) values ('2021-01-05', 2, 10000, 1, supplier_email)
insert into offer (deadline, status, total_price, order_id, supplier_email) values ('2021-01-07', 2, 50000, 2, supplier_email)
insert into offer (deadline, status, total_price, order_id, supplier_email) values ('2021-01-10', 2, 100000, 3, supplier_email)

insert into order_t (deadline, deleted, status, admin_email, supplier_email) values ('2021-01-06','false', 1,'jovanpetrovic@gmail.com' , supplier_email)
insert into order_t (deadline, deleted, status, admin_email, supplier_email) values ('2021-01-08','false', 1,'sarajovic@gmail.com',supplier_email)
insert into order_t (deadline, deleted, status, admin_email, supplier_email) values ('2021-01-11','false', 1,'markoperic@gmail.com', supplier_email)

insert into order_t_medicament_items (order_id, medicament_items_id) values (1, 6)
insert into order_t_medicament_items (order_id, medicament_items_id) values (2, 7)
insert into order_t_medicament_items (order_id, medicament_items_id) values (3, 8)
*/

