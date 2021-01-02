-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- Pabcalper, named pabcalper with password 0wn7r
INSERT INTO users(username,password,enabled) VALUES ('pabcalper','0wn7r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'pabcalper','owner');
-- oscdoraba, named oscdoraba with password oscdoraba
INSERT INTO users(username,password,enabled) VALUES ('oscdoraba','oscdoraba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'oscdoraba','owner');
-- felconmar, named felconmar with password 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('felconmar','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'felconmar','owner');
-- davgangar1, named davgangar1 with password davgangar1
INSERT INTO users(username,password,enabled) VALUES ('davgangar1','davgangar1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'davgangar1','owner');
-- Pablosky, named pablosky with password pabloEscritor7
INSERT INTO users(username,password,enabled) VALUES ('pablosky','pabloEscritor7',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (19,'pablosky','autor');

-- moderador1, named moderador1 with password moderador1
INSERT INTO users(username,password,enabled) VALUES ('moderador1','moderador1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (20,'moderador1','moderador');
-- editorial1, named editorial1 with password editorial1
-- INSERT INTO users(username,password,enabled) VALUES ('editorial1','editorial1',TRUE);
-- INSERT INTO authorities(id,username,authority) VALUES (21,'editorial1','company');



-- INSERT INTO vets VALUES (1, 'James', 'Carter');
-- INSERT INTO vets VALUES (2, 'Helen', 'Leary');
-- INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
-- INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
-- INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
-- INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');
-- 
-- INSERT INTO specialties VALUES (1, 'radiology');
-- INSERT INTO specialties VALUES (2, 'surgery');
-- INSERT INTO specialties VALUES (3, 'dentistry');
-- 
-- INSERT INTO vet_specialties VALUES (2, 1);
-- INSERT INTO vet_specialties VALUES (3, 2);
-- INSERT INTO vet_specialties VALUES (3, 3);
-- INSERT INTO vet_specialties VALUES (4, 2);
-- INSERT INTO vet_specialties VALUES (5, 1);
-- 
-- INSERT INTO types VALUES (1, 'cat');
-- INSERT INTO types VALUES (2, 'dog');
-- INSERT INTO types VALUES (3, 'lizard');
-- INSERT INTO types VALUES (4, 'snake');
-- INSERT INTO types VALUES (5, 'bird');
-- INSERT INTO types VALUES (6, 'hamster');
-- 
-- INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
-- INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
-- INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
-- INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
-- INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
-- INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
-- INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
-- INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
-- INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
-- INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
-- INSERT INTO owners VALUES (11, 'Oscar', 'Dorado', '2335 Brooklyn Street', 'Tennessee', '6085559087', 'oscdoraba');
-- INSERT INTO owners VALUES (12, 'Pabcalper', 'Calle', 'Dirección desconocida', 'Sevilla', '6301785873', 'pabcalper');
-- INSERT INTO owners VALUES (13, 'Felix', 'Conde', 'Reina Mercedes', 'Sevilla', '636123456', 'felconmar');
-- INSERT INTO owners VALUES (14, 'David', 'Ganan', 'Anonima', 'Sevilla', '656874579', 'davgangar1');

-- INSERT INTO moderators VALUES (15, 'Moderador', 'Moderando Moderaciones', 'moderador1');
-- 
--  INSERT INTO companies VALUES (1, 'Editorial Magistral', 'editorial1');


-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Tobby', '2009-06-08', 1, 11);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Tango', '2014-02-16', 2, 12);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Pelusa', '2010-09-01', 1, 13);
-- 
-- 
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');


-- =================== Index =================== --

-- 1. Authors
-- 2. Moderators
-- 3. Stories and Chapters
-- 4. Contributions
-- 5. Reviews
-- 6. Companies y Contracts
-- 7. Reports


-- ========================== Datos de ejemplo ========================== --
-- 1. Authors -----------------------------------------------------------------------

	-- Author1 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('author1','author1',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('author1','author');
	
	INSERT INTO authors(dni, first_name, last_name, email, birth_date, url_picture, biography, username) 
		VALUES ('52874678D','Marco','Medina Sandoval','candidoMedina@mail.com','1988-11-23',
		'/resources/images/authors/author1.jpg', 'Viajero de historias.','author1');
		
	-- Author2 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('author2','author2',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('author2','author');
	
	INSERT INTO authors(dni, first_name, last_name, email, birth_date, url_picture, biography, username) 
		VALUES ('79283637F','Sonia','Arredondo Zaragoza','arredondoZaragoza@mail.com','1975-01-20',
		'/resources/images/authors/author2.jpg', 'El cine y la novela: Mis dos grandes pasiones.',
		'author2');
		
	-- Author3 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('author3','author3',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('author3','author');
	
	INSERT INTO authors(dni, first_name, last_name, email, birth_date, url_picture, biography, username) 
		VALUES ('01014675N','Julio','Pineda Ceja','pinedaCeja@mail.com','1990-07-14',
		'/resources/images/authors/author3.jpg', null ,'author3');
		
	-- Author4 (MENOR DE EDAD: 12/13 años)----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('author4','author4',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('author4','author');
	
	INSERT INTO authors(dni, first_name, last_name, email, birth_date, url_picture, biography, username) 
		VALUES ('12344675M','Tania','Carmona Jimenez','carmonaJimenez@mail.com','2008-02-27',
		'/resources/images/authors/author4.jpg', null ,'author4');
		
	-- Author5 (MENOR DE EDAD: 17 años)----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('author5','author5',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('author5','author');
	
	INSERT INTO authors(dni, first_name, last_name, email, birth_date, url_picture, biography, username) 
		VALUES ('76544675U','Rodolfo','Mendez Pelayo','mendezPelayo@mail.com','2003-12-16',
		'/resources/images/authors/author5.jpg', null ,'author5');
		
	-- Author6 (Baneable)----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('author6','author6',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('author6','author');
	
	INSERT INTO authors(dni, first_name, last_name, email, birth_date, url_picture, biography, username) 
		VALUES ('01234567Q','Slax','Edgy Troll','slax@mail.com','1996-06-06',
		'/resources/images/authors/author6.jpg', 
		'Solo quiero sabotear esta web, no me importa incumplir las normas con tal de divertirme. Troll profesional' ,'author6');
		
-- 2. Moderators -----------------------------------------------------------------------

	-- Moderator1 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('moderator1','moderator1',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('moderator1','moderator');
	
	INSERT INTO moderators(dni, first_name, last_name, email, birth_date, url_picture, username) 
		VALUES ('45556952J','Emilio','Sevillano','moderator1@alexandria.com','1992-03-05',
		'/resources/images/moderators/moderator1.jpg', 'moderator1');
		
	-- Moderator2 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('moderator2','moderator2',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('moderator2','moderator');
	
	INSERT INTO moderators(dni, first_name, last_name, email, birth_date, url_picture, username) 
		VALUES ('87654321B','Blanca','Serrano Pineda','moderator2@alexandria.com','1995-08-13',
		null, 'moderator2');
		
-- 3. Stories and Chapters -----------------------------------------------------------------------
	
	/* StoryStatus: 	DRAFT, PUBLISHED, SUSPECT, REVIEW,	ON_HOLD, DELETED*/
	
	/* Genre: 	SCRIPT, TALE, NOVEL, CHRONICLE,	CHILDREN_STORY,	POETRY*/

	-- Stories from Author1 -----------------------------------------------------
		
		-- Story 1 assigned to Moderator1
		INSERT INTO stories(title, description, dedication, genre, story_status, is_adult, update_date, moderator_id, author_id) 
			VALUES ('Lorem ipsum', 
			'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at blandit dolor, at laoreet nulla. Donec nibh nisi.',
			'Dicata latine loquentium', 'NOVEL', 'PUBLISHED', false, '2020-10-12 15:00', 1, 1);
			
			-- Chapter 1 - Story 1
			INSERT INTO chapters(index, title, body, is_published, story_id) 
				VALUES(1, 'Principium', 
				'Integer porttitor nulla id felis maximus pharetra. Etiam at neque eu justo placerat cursus. Proin blandit eu justo ac gravida. Proin ac metus sed dui.',
				true,1);
			
			-- Chapter 2 - Story 1
			INSERT INTO chapters(index, title, body, is_published, story_id) 
				VALUES(2, 'Sequentia', 
				'Maecenas tempor nisl quis malesuada pulvinar. Donec molestie posuere augue nec luctus. Sed ex lectus, faucibus vel dapibus in, hendrerit non massa. Aenean fringilla mi nunc, sit amet rhoncus nibh euismod placerat.',
				true,1);
				
			-- Chapter 3 - Story 1
			INSERT INTO chapters(index, title, body, is_published, story_id) 
				VALUES(3, 'Finalem', 
				'Etiam id massa nibh. Fusce in convallis arcu. Vivamus aliquet ultrices felis, at varius urna facilisis eget. Sed eget tortor quam. Ut dui diam, auctor commodo elementum non, finibus sit amet nibh.',
				false,1);
			
			
		-- Story 2 (DRAFT)
		INSERT INTO stories(title, description, dedication, genre, story_status, is_adult, update_date, moderator_id, author_id) 
			VALUES ('Lorem ipsum 2', 
			'Donec fringilla interdum diam elementum accumsan. Fusce urna neque, luctus ut ultrices non, vehicula eu neque. Nam sit amet massa id odio ut.',
			null, 'NOVEL', 'DRAFT', false, '2020-10-12 15:00', null,1);
			
			-- Chapter 4 - Story 2
			INSERT INTO chapters(index, title, body, is_published, story_id) 
				VALUES(1, 'Initium novum', 
				'Cras rhoncus diam in est aliquam, eu eleifend nulla maximus. Ut sollicitudin venenatis feugiat. Donec vitae scelerisque dui. Vestibulum rutrum vitae purus ac tincidunt.',
				false,2);
		
	
	-- Stories from Author2  -----------------------------------------------------
	
		-- Story 3 assigned to Moderator1
		INSERT INTO stories(title, description, dedication, genre, story_status, is_adult, update_date, url_cover, moderator_id, author_id) 
			VALUES ('Wild Robber ', 
			'Generous navigator MRS CHARLOTTE DONALDSON is arguing with bold teacher MRS GEMMA FISH. CHARLOTTE tries to hug GEMMA but she shakes her off.',
			'To my beloved mother... ', 'SCRIPT', 'PUBLISHED', false, '2020-10-12 15:00','/resources/images/stories/story3.jpg', 1, 2);
			
			-- Chapter 5 - Story 3
			INSERT INTO chapters(index, title, body, is_published, story_id) 
				VALUES(1, 'ACT 1', 
				'Generous navigator MRS CHARLOTTE DONALDSON is arguing with bold teacher MRS GEMMA FISH. CHARLOTTE tries to hug GEMMA but she shakes her off. <br/> <br/> CHARLOTTE: Please Gemma, don''t leave me. <br/> GEMMA: I''m sorry Charlotte, but I''m looking for somebody a bit more brave. Somebody who faces her fears head on, instead of running away. <br/> CHARLOTTE: I am such a person!',
				true , 3);
			
			-- Chapter 6 - Story 3
			INSERT INTO chapters(index, title, body, is_published, story_id) 
				VALUES(2, 'ACT 2', 
				'CHARLOTTE rushes along the street, followed by GEORGINA. They take a short cut through some back gardens, jumping fences along the way. <br/> <br/> GEORGINA: What is is? What''s the matter? <br/> CHARLOTTE: That''s not just any old robber, that''s Sharon Bond!',
				false , 3);

-- 4. Contributions -----------------------------------------------------------------------

-- 5. Reviews -----------------------------------------------------------------------------

-- 6. Companies y Contracts ---------------------------------------------------------------


	-- Company1 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('company1','company1',true);
	INSERT INTO authorities(username, authority) VALUES ('company1','company');
		
		INSERT INTO companies(cif, name, email, company_type, url_Logo, description, username) 
		VALUES ('A58818501', 'Bookista','info@bookista.com','PUBLISHER','/resources/images/companies/company1.png', 
		'Bookista S.A: A book publisher company since 1966.','company1');
	
		-- Contracts from Company1 -----------------------------------------------------
		
				-- Contract1 with Author1 (EXCLUSIVO - ACEPTADO - EXPIRADO)--------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-08-20 16:30', 'Mecenazgo EXCLUSIVO SEPTIEMBRE 2020',
						'Por el presente contrato se estipula que Bookista ofrece un mecenazgo a Marco Medina Sandoval. Marco Medina se compromete a NO ACEPTAR PATROCINIOS de otras compañías durante el mes de SEPTIEMBRE 2020.',
						12000.50, '2020-08-25 18:27', 'ACCEPTED' , '2020-09-01 00:00', '2020-09-30 23:59', true,
						1,1);
				
				-- Contract2 with Author2 (PENDIENTE - EXPIRADO)--------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-08-20 16:30', 'Mecenazgo EXCLUSIVO SEPTIEMBRE 2020',
						'Bookista ofrece un mecenazgo EXCLUSIVO a Sonia Arredondo Zaragoza, que se compromete a NO ACEPTAR PATROCINIOS de otras compañías durante el mes de SEPTIEMBRE 2020.',
						12500.50, null, 'PENDING' , '2020-09-01 00:00', '2020-09-30 23:59', true,
						2,1);
				
				-- Contract3 with Author2 ( PENDIENTE )--------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-09-23 08:12', 'Patrocinio Q4 2020 hasta Q2 2021',
						'Bookista ofrece un mecenazgo a Sonia Arredondo Zaragoza sin cláusulas de exclusividad. Bookista se reserva el derecho de retirar el patrocinio en caso de violación a los principios de la empresa por parte del autor.',
						4300.00, null, 'PENDING' , '2020-10-01 00:00', '2021-06-30 23:59', false,
						2,1);

				-- Contract4 with Author3 ( PENDIENTE  )--------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-09-23 08:12', 'Patrocinio Q4 2020 hasta Q2 2021',
						'Bookista ofrece un mecenazgo a Julio Pineda Ceja sin cláusulas de exclusividad. Bookista se reserva el derecho de retirar el patrocinio en caso de violación a los principios de la empresa por parte del autor.',
						4300.00, null, 'PENDING' , '2020-10-01 00:00', '2021-06-30 23:59', false,
						3,1);									
				
	-- Company2 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('company2','company2',true);
	INSERT INTO authorities(username, authority) VALUES ('company2','company');
	
		
		INSERT INTO companies(cif, name, email, company_type, url_Logo, description, username) 
		VALUES ('F58812345', 'Authorsy','contact@authorsy.org','PUBLISHER','/resources/images/companies/company2.png', 
		'A cooperative book publisher. Built by authors for authors and readers.','company2');
	
		-- Contracts from Company2 -----------------------------------------------------
		
				-- Contract5 with Author1 ( PENDIENTE )----------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-11-15 09:28', 'Patrocinio Q4 2020 hasta Q2 2021 NO EXCLUSIVO',
						'Authorsy, por iniciativa propia, ofrece a Marco Medina Sandoval RECONOCER sus méritos creativos de forma monetaria. Si se acepta se deben cumplir los términos y condiciones de los premios, ayudas y patrocinios otorgados por Authorsy en authorsy.org/sponsorships',
						900.80, null, 'PENDING' ,'2020-11-19 00:00', '2021-12-15 23:59', false,
						1,2);
						
				-- Contract6 with Author2 ( EXCLUSIVO - PENDIENTE )----------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-11-15 09:28', 'Representación de Authorsy Q4 2020 hasta Q2 2021 EXCLUSIVO',
						'Authorsy SE COMPROMETE CON Sonia Arredondo Zaragoza a PATROCINAR sus creaciones literarias en el período desde octubre 2020 hasta junio del 2021. Sonia Arredondo Zaragoza se compromete, en caso de aceptar el patrocinio, a ser la cara pública de la empresa y se compromete a respetar su visión y misión empresarial.',
						100320.95, null, 'PENDING' ,'2020-10-01 00:00', '2021-06-30 23:59', true,
						2,2);
				
	-- Company3 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('company3','company3',true);
	INSERT INTO authorities(username, authority) VALUES ('company3','company');
	
		
		INSERT INTO companies(cif, name, email, company_type, url_Logo, description, username) 
		VALUES ('A90812345', 'FILMION','info@filmion.com','PRODUCER','/resources/images/companies/company3.png', 
		'Indie film producer. Making movies and short series since 2012 from the bottom of our heart.','company3');
	
		-- Contracts from Company3 -----------------------------------------------------
		
				-- Contract7 with Author1 ( EXCLUSIVO RECHAZADO )--------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-09-15 09:28', 'DIRECTOR CREATIVO ASOCIADO para FILMIN OCT-2020 a JUN-2021',
						'La parte empleadora FILMIN contrata a Marco Medina Sandoval como DIRECTOR CREATIVO ASOCIADO. La parte contratada se compromete a trabajar solo en proyectos de FILMIN S.A en el periodo de vigencia del contrato y solo aceptando representar a FILMIN y marcas asociadas.',
						60782.95, '2020-09-15 12:54', 'REJECTED' ,'2020-10-01 00:00', '2021-06-30 23:59', true,
						1,3);
		
				-- Contract8 with Author3 ( EXCLUSIVO - VIGENTE )--------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-08-19 11:40', 'SCRIPTWRITER DIRECTOR FILMIN SEPTEMBER 2020 to SEPTEMBER 2021',
						'The employer part FILMIN and the employee Julio Pineda Ceja accept to stablish a work relationship. Salary for the employee part for this period is stated in the contract. General labour legislation in this field is applied.',
						250600.00, '2020-08-24 08:30', 'ACCEPTED' ,'2020-09-01 00:00', '2021-09-30 23:59', true,
						3,3);
				
	-- Company4 ----------------------------------------------------------------------
	INSERT INTO users(username,password,enabled) VALUES ('company4','company4',TRUE);
	INSERT INTO authorities(username, authority) VALUES ('company4','company');
	
		
		INSERT INTO companies(cif, name, email, company_type, url_Logo, description, username) 
		VALUES ('A40329876', 'NEWS SECTOR','info@newssector.com','NEWSPAPER','/resources/images/companies/company4.png', 
		'News and media outlet. Breaking news, quality journalism and literature reviewrs','company4');
	
		-- Contracts from Company4 -----------------------------------------------------
		
				-- Contract9 with Author1 ( EXCLUSIVO - PENDIENTE ) ----------------------
						INSERT INTO contracts(offer_date, header, body, remuneration, answer_date, contract_status, start_date, end_date, is_exclusive, 
						author_id, company_id) 
						VALUES ('2020-11-05', 'PUBLIC EXCLUSIVE SPONSORSHIP 2020/2021 from NEWS SECTOR',
						'NEWS SECTOR offers Marco Medina Sandoval to be the public face of the aforementioned company. The sponsorhip that all the work produced in that period by the author will be associated with the NEWS SECTOR trademark. A compensation will be given as stated in this contract.',
						140420.49, null, 'PENDING' ,'2020-10-01 00:00', '2021-06-30 23:59', true,
						1,4);
						
				-- Contract10 with Author3 ( EXCLUSIVO - PENDIENTE ) ----------------------
					INSERT INTO contracts(offer_date, header ,body,remuneration , answer_date, contract_status, start_date,end_date,is_exclusive,
					author_id,company_id) 
					VALUES ('2020-12-08 15:00','Oferta de contrato 10','Nos ponemos en contacto con usted porque estamos interesados en contratarle en nuestra editorial',
					6.89,'2020-12-15 12:00', 'PENDING', '2021-01-01 14:00','2021-02-02 14:00', true,
					3,4);

-- 7. Reports -----------------------------------------------------------------------------


--Habría que descomentarlo cuando se creen los ejemplos de story, company y algunos más de author

--INSERT INTO reviews(title,rating,text,publication_date,author_id,story_id) 
-- VALUES('Title 1', 5, 'Comment 1','2020-09-01 11:11',1,1)
-- INSERT INTO reviews(title,rating,text,publication_date,author_id,story_id) 
-- VALUES('Title 2', 3, 'Comment 2', '2020-11-10 10:13',1,1)
-- INSERT INTO reviews(title,rating,text,publication_date,author_id,story_id) 
-- VALUES('Title 3', 4, 'Comment 3', '2018-10-01 01:06',2,3)

 --INSERT INTO contracts(offer_date,header,text,remuneration,answer_date,contractStatus,start_date,end_date,is_exclusive,author_id,company_id) 
 --VALUES('2019-07-05 11:11', 'Header 1','Text 1', '75/2', '2020-09-01 11:11', 'ACEPTED', '2021-10-01 12:11','2022-10-01 11:30', TRUE, 1 ,1)
-- INSERT INTO contracts(ioffer_date,header,text,remuneration,answer_date,contractStatus,start_date,end_date,is_exclusive,author_id,company_id) 
 --VALUES('2016-10-13 11:11'. 'Header 2','Text 2', '37/3', '2019-11-07 11:11', 'REJECTED', '2023-10-01 9:07','2024-10-01 14:41', FALSE , 1, 2)
 --INSERT INTO contracts(offer_date,header,text,remuneration,answer_date,contractStatus,start_date,end_date,is_exclusive,author_id,company_id) 
 --VALUES('2015-07-09 11:11', 'Header 3','Text 3', '100/2', '2020-06-09 11:11', 'PENDING', '2021-12-10 11:11','2022-11-01 10:13', TRUE, 2,3)

-- INSERT INTO contributions(contributionType,author_id,story_id) VALUES(EDITOR,1,1)
-- INSERT INTO contributions(contributionType,author_id,story_id) VALUES(COAUTHOR,2,2)
-- INSERT INTO contributions(contributionType,author_id,story_id) VALUES(CONSULTANT,3,3)