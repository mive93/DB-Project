﻿CREATE TABLE TIPOLOGIA
(
	IdT		INTEGER NOT NULL,
	OraInizio	TIME NOT NULL,
	OraFine		TIME NOT NULL,

	PRIMARY KEY (IdT),
	CHECK(OraFine > OraInizio)
);

CREATE TABLE CORSOLAUREA
(
	IdCDL	INTEGER NOT NULL,
	Nome	CHAR(50) ,

	PRIMARY KEY (IdCDL)
);

CREATE TABLE GRUPPOACCOUNT
(
	IdCDL		INTEGER NOT NULL,
	AnnoIscrizione	INTEGER NOT NULL,

	PRIMARY KEY (IdCDL, AnnoIscrizione),
	FOREIGN KEY (IdCDL) REFERENCES CORSOLAUREA (IdCDL)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ACCOUNT
(
	IdCDL		INTEGER NOT NULL,
	AnnoIscrizione	INTEGER NOT NULL,
	NomeUser	CHAR(50) NOT NULL UNIQUE,
	Password	CHAR(50) NOT NULL,

	PRIMARY KEY (IdCDL, AnnoIscrizione,NomeUser),
	FOREIGN KEY (IdCDL, AnnoIscrizione) REFERENCES GRUPPOACCOUNT (IdCDL, AnnoIscrizione) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE UTENTE
(
	CF		CHAR(16) NOT NULL,
	Nome 		CHAR(50),
	Cognome		CHAR(50),
	DataN 		DATE,
	LuogoN		CHAR(50),
	Residenza 	CHAR(50),
	CAP 		INTEGER,
	Nazionalita	CHAR(50),
	CodTessera	INTEGER NOT NULL,

	PRIMARY KEY (CF),
	UNIQUE (CodTessera)
);

CREATE TABLE STUDENTE
(
	CF		CHAR(16) NOT NULL,
	Matr		INTEGER NOT NULL,
	IdCDL		INTEGER,
	AnnoI		INTEGER,
	NomeUser	CHAR(50),

	PRIMARY KEY (CF),
	UNIQUE (Matr),
	FOREIGN KEY (CF) REFERENCES UTENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (IdCDL,AnnoI,NomeUser) REFERENCES ACCOUNT(IdCDL,AnnoIscrizione,NomeUser)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE LAUREANDO
(
	CF	CHAR(16) NOT NULL,

	PRIMARY KEY (CF),
	FOREIGN KEY (CF) REFERENCES STUDENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PERSONALE
(
	CF	CHAR(16) NOT NULL,
	Salario	INTEGER ,
	IdP	INTEGER NOT NULL,

	PRIMARY KEY (CF),
	UNIQUE (IdP),
	FOREIGN KEY (CF) REFERENCES UTENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE	
);

CREATE TABLE DOCENTE
(
	CF		CHAR(16) NOT NULL,
	TITOLARE 	BOOLEAN NOT NULL,

	PRIMARY KEY (CF),
	FOREIGN KEY (CF) REFERENCES PERSONALE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE NONDOCENTE
(
	CF	CHAR(16) NOT NULL,

	PRIMARY KEY (CF),
	FOREIGN KEY (CF) REFERENCES PERSONALE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE TECNICO
(
	CF	CHAR(16) NOT NULL,
	Ruolo	CHAR(50),

	PRIMARY KEY (CF),
	FOREIGN KEY (CF) REFERENCES NONDOCENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE LOCALE
(
	IdLoc		INTEGER NOT NULL,
	Nome		CHAR(50),
	Luogo		CHAR(50),
	Dimensioni	DECIMAL(6,4),
	Capacita	INTEGER ,

	PRIMARY KEY (IdLoc)
);

CREATE TABLE NUMACCESSI
(
	IdLoc	INTEGER NOT NULL,
	Numero 	INTEGER,
	Data	DATE	NOT NULL,

	PRIMARY KEY (IdLoc,Data),
	FOREIGN KEY (IdLoc) REFERENCES LOCALE (IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
		
);

CREATE TABLE LABORATORIO
(
	IdLoc		INTEGER NOT NULL,
	Sede		CHAR(50),
	CFT	CHAR(16) NOT NULL,
	CFD	CHAR(16) NOT NULL,

	PRIMARY KEY (IdLoc),
	FOREIGN KEY (IdLoc) REFERENCES LOCALE(IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (CFT) REFERENCES TECNICO(CF)
		ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (CFD) REFERENCES DOCENTE(CF)
		ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE PDLAVORO
(
	IdPosto	INTEGER NOT NULL UNIQUE,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (IdPosto, IdLoc),
	FOREIGN KEY (IdLoc) REFERENCES LABORATORIO(IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ASSEGNATO
(
	IdPosto	INTEGER NOT NULL,
	IdLoc		INTEGER NOT NULL,

	PRIMARY KEY (IdPosto,IdLoc),
	FOREIGN KEY (IdPosto,IdLoc)REFERENCES PDLAVORO(IdPosto,IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE LIBERO
(
	IdPosto	INTEGER NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (IdPosto,IdLoc),
	FOREIGN KEY (IdPosto,IdLoc)REFERENCES PDLAVORO(IdPosto,IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE PRENOTABILE
(
	IdPosto	INTEGER NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (IdPosto,IdLoc),
	FOREIGN KEY (IdPosto,IdLoc)REFERENCES PDLAVORO(IdPosto,IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ASSEGNAZIONE
(
	Mese 	INTEGER NOT NULL,
	CFL	CHAR(16) NOT NULL,
	CFD	CHAR(16) NOT NULL,
	IdPosto	INTEGER NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (Mese,CFL),
	UNIQUE  (Mese,IdPosto,IdLoc),
	FOREIGN KEY (IdPosto,IdLoc)REFERENCES ASSEGNATO(IdPosto,IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (CFD) REFERENCES DOCENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (CFL) REFERENCES LAUREANDO(CF)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK (Mese>0 and mese<=12)
);

CREATE TABLE PRENOTAZIONE
(
	Data	DATE NOT NULL,
	Ora 	TIME NOT NULL,
	IdPosto	INTEGER NOT NULL,
	CF	CHAR(16) NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (Data,Ora,IdPosto,IdLoc),
	UNIQUE  (CF,Data,Ora),
	FOREIGN KEY (CF) REFERENCES UTENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (IdPosto,IdLoc)REFERENCES PRENOTABILE(IdPosto,IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ACCEDE
(
	IdLib	INTEGER NOT NULL,
	CF	CHAR(16) NOT NULL,
	Ora	TIME NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (Ora,CF),
	UNIQUE (Ora,IdLib,IdLoc),
	FOREIGN KEY (CF) REFERENCES STUDENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (IdLib,IdLoc)REFERENCES LIBERO(IdPosto,IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE RISORSA 
(
	IdRis	INTEGER NOT NULL,
	IdPosto	INTEGER ,
	Nome	CHAR(50),
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (IdRis),
	FOREIGN KEY (IdPosto,IdLoc)REFERENCES PDLAVORO(IdPosto,IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE DISTRIBUZIONEPR
(
	IdCDL		INTEGER NOT NULL,
	AnnoIscrizione	INTEGER NOT NULL,
	IdRis		INTEGER NOT NULL,
	CF		CHAR(16) NOT NULL,

	PRIMARY KEY (IdCDL, AnnoIscrizione, IdRis),
	FOREIGN KEY (IdCDL)REFERENCES CORSOLAUREA(IdCDL)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (IdRis)REFERENCES RISORSA(IdRis)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (CF) REFERENCES TECNICO(CF)	
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PERMESSO
(
	CF	CHAR(16) NOT NULL,
	IdT	INTEGER NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (CF, IdLoc),
	FOREIGN KEY (IdLoc) REFERENCES LOCALE(IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (CF) REFERENCES UTENTE(CF)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (IdT) REFERENCES TIPOLOGIA(IdT)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE RIFIUTO
(
	Data 	DATE NOT NULL,
	Ora	TIME NOT NULL,
	CodTessera	INTEGER NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (Data,Ora,CodTessera),
	FOREIGN KEY (IdLoc) REFERENCES LOCALE(IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (CodTessera) REFERENCES UTENTE(CodTessera)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ACCESSO
(	Data 	DATE NOT NULL,
	Ora	TIME NOT NULL,
	CodTessera	INTEGER NOT NULL,
	IdLoc	INTEGER NOT NULL,

	PRIMARY KEY (Data,Ora,CodTessera),
	FOREIGN KEY (IdLoc) REFERENCES LOCALE(IdLoc)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (CodTessera) REFERENCES UTENTE(CodTessera)	
		ON DELETE CASCADE ON UPDATE CASCADE
);






CREATE FUNCTION controlloPosti() RETURNS trigger AS $$
	DECLARE 
		CONT INTEGER;
	BEGIN
		SELECT COUNT(IdPosto) INTO CONT
		FROM PDLAVORO 
		WHERE IdLoc = NEW.IdLoc;
		IF CONT > 100 THEN 
			RAISE EXCEPTION 'Laboratorio pieno';
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE 'plpgsql';	

CREATE TRIGGER CAPIENZALAB
AFTER INSERT ON PDLAVORO
FOR EACH ROW EXECUTE PROCEDURE controlloPosti();






CREATE FUNCTION controlloRisorse() RETURNS trigger AS $$
	DECLARE 
		CONT INTEGER;
	BEGIN
		SELECT COUNT(IdRis) INTO CONT
		FROM RISORSA 
		WHERE IdPosto = NEW.IdPosto;
		IF CONT > 20 THEN 
			RAISE EXCEPTION 'Impossibile assegnare ulteriore risorsa al posto di lavoro';
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE 'plpgsql';
		
CREATE TRIGGER NUMRISORSE
AFTER INSERT ON RISORSA
FOR EACH ROW EXECUTE PROCEDURE controlloRisorse();
		
CREATE TRIGGER NUMRISORSEONUPDATE
AFTER UPDATE ON RISORSA
FOR EACH ROW EXECUTE PROCEDURE controlloRisorse();





CREATE FUNCTION controlloPrenotazioni() RETURNS trigger AS $$
	DECLARE 
		APP   CHAR(16);
		APP2 CHAR(16) ;
		APP3 BOOLEAN;
	BEGIN
		SELECT CF INTO APP 
		FROM NONDOCENTE 
		WHERE CF = NEW.CF;
		
		IF (APP = NEW.CF) THEN
			RAISE EXCEPTION 'Non è possibile prenotare';
		ELSE
			SELECT CF, TITOLARE INTO APP2 ,  APP3
			FROM DOCENTE 
			WHERE CF = NEW.CF;

			IF (APP2 = NEW.CF AND APP3 = false ) THEN
				RAISE EXCEPTION 'Non è possibile prenotare';
			END IF;
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE 'plpgsql';
		
CREATE TRIGGER CONTROLLOPRENOTAZIONE
AFTER INSERT ON PRENOTAZIONE
FOR EACH ROW EXECUTE PROCEDURE controlloPrenotazioni();






CREATE FUNCTION controlloACC() RETURNS trigger AS $$
	DECLARE
		APP CHAR(16);
		APP1 CHAR(16);
		APP2 INTEGER;
		OI  TIME;
		OF TIME;
		LOC INTEGER;
	BEGIN
		SELECT CF INTO APP1
		FROM UTENTE
		WHERE CodTessera = NEW.CodTessera;
	
		SELECT CF, IdT INTO  APP, APP2
		FROM PERMESSO
		WHERE CF  = APP1 AND IdLoc = NEW.IdLoc;

		SELECT OraInizio, OraFine INTO OI,OF
		FROM TIPOLOGIA
		WHERE IdT = APP2;
	
		IF(APP <> APP1 OR APP IS NULL OR NEW.Ora > OF OR NEW.Ora < OI) THEN
			INSERT INTO RIFIUTO VALUES (NEW.Data,NEW.Ora,NEW.CodTessera, NEW.IdLoc);
			RETURN NULL;
		ELSE

			SELECT IdLoc INTO LOC
			FROM NUMACCESSI
			WHERE Data = NEW.Data;

			IF(LOC = NEW.IdLoc) THEN
				UPDATE NUMACCESSI			
				SET	Numero = Numero +1
				WHERE IdLoc = NEW.IdLoc;
			ELSE
				INSERT INTO NUMACCESSI VALUES(NEW.IdLoc,1,NEW.Data);
			END IF;
			
			RETURN NEW;
		END IF;
			
	END;
$$ LANGUAGE 'plpgsql';
		
CREATE TRIGGER CONTROLLOACC
BEFORE INSERT ON ACCESSO
FOR EACH ROW EXECUTE PROCEDURE controlloACC();





CREATE FUNCTION controlloSA() RETURNS trigger AS $$
	DECLARE
		APP CHAR(50);
	BEGIN
		SELECT RUOLO INTO APP
		FROM TECNICO
		WHERE CF  = NEW.CF;
		IF(APP <> 'System Administrator') THEN
			RAISE EXCEPTION 'Tecnico non autorizzato';
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE 'plpgsql';
		
CREATE TRIGGER CONTROLLOSA
AFTER INSERT ON DISTRIBUZIONEPR
FOR EACH ROW EXECUTE PROCEDURE controlloSA();





INSERT INTO Utente VALUES('ASDFNH92L010K','Mario','Rossi','24/12/1992','Bondeno','strada zocca 3',46022,'italiano',21340);
INSERT INTO Utente VALUES('SDFRTY45L871H','Pietro','Grandi','02/02/1992','Sassuolo','strada dei caduti 1',45234,'italiano',22354);
INSERT INTO Utente VALUES('ERTYUI23L543D','Sara','Cortese','03/06/1994','Mantova','strada valla del oca 12',45678,'italiano',34560);
INSERT INTO Utente VALUES('QWEASD12G234C','Giovanna','Trombelli','06/07/1991','Maranello','via corso garibaldi 12',34562,'italiano',12345);
INSERT INTO Utente VALUES('POIUYT23S543S','Mirko','Movida','07/07/1969','Bancole','via non lo so 13',54678,'italiano',23675);
INSERT INTO Utente VALUES('ZXCVBN12D563A','Valentino','Rossi','09/08/1953','Modena','via Garibaldi 10',34231,'italiano',12002);
INSERT INTO Utente VALUES('ZAFCDF16D513F','Valentino','Marconi','29/08/1970','Modena','via Garibaldi 11',34231,'italiano',12456);
INSERT INTO Utente VALUES('GHCXBA45D163A','Mario','Mosconi','19/10/1963','Modena','via Verucchi 10',34231,'italiano',15672);
INSERT INTO Utente VALUES('LKJHGF12A094D','Pino','Lavatrice','01/01/1971','Modena','via Trento-Trieste 32',34231,'italiano',23455);
INSERT INTO Utente VALUES('KJHDSA92T242S','Alberto','Rossi','30/10/1980','Modena','via Vignolese 123',34231,'italiano',12356);

INSERT INTO CorsoLaurea VALUES(100,'Informatica');
INSERT INTO CorsoLaurea VALUES(210,'Ing. Meccanica');
INSERT INTO CorsoLaurea VALUES(110,'Matematica');
INSERT INTO CorsoLaurea VALUES(112,'Fisica');
INSERT INTO CorsoLaurea VALUES(310,'Economia');

INSERT INTO GruppoAccount VALUES(100,2011);
INSERT INTO GruppoAccount VALUES(100,2012);
INSERT INTO GruppoAccount VALUES(310,2013);
INSERT INTO GruppoAccount VALUES(110,2010);
INSERT INTO GruppoAccount VALUES(112,2014);

INSERT INTO Account VALUES(110,2010,'ciao','mamma');
INSERT INTO Account VALUES(110,2010,'sempo','c1992');
INSERT INTO Account VALUES(112,2014,'davide','dav23121992');
INSERT INTO Account VALUES(310,2013,'mirko','ssdf123');

INSERT INTO Studente VALUES('ASDFNH92L010K',71498,110,2010,'ciao');
INSERT INTO Studente VALUES('SDFRTY45L871H',34566,110,2010,'sempo');
INSERT INTO Studente VALUES('ERTYUI23L543D',87654,112,2014,'davide');
INSERT INTO Studente VALUES('QWEASD12G234C',72298,310,2013,'mirko');

INSERT INTO Laureando VALUES('ASDFNH92L010K');
INSERT INTO Laureando VALUES('ERTYUI23L543D');

INSERT INTO Personale VALUES('POIUYT23S543S',32000,1);
INSERT INTO Personale VALUES('ZXCVBN12D563A',45000,2);
INSERT INTO Personale VALUES('ZAFCDF16D513F',23000,3);
INSERT INTO Personale VALUES('GHCXBA45D163A',31000,4);
INSERT INTO Personale VALUES('LKJHGF12A094D',23000,5);
INSERT INTO Personale VALUES('KJHDSA92T242S',16000,6);

INSERT INTO Docente VALUES('ZXCVBN12D563A',true);
INSERT INTO Docente VALUES('GHCXBA45D163A',true);
INSERT INTO Docente VALUES('POIUYT23S543S',false);

INSERT INTO NonDocente VALUES('ZAFCDF16D513F');
INSERT INTO NonDocente VALUES('LKJHGF12A094D');
INSERT INTO NonDocente VALUES('KJHDSA92T242S');

INSERT INTO Tecnico VALUES('KJHDSA92T242S','System Administrator');
INSERT INTO Tecnico VALUES('LKJHGF12A094D','System Administrator');
INSERT INTO Tecnico VALUES('ZAFCDF16D513F','Responsabile materiale didattico');

INSERT INTO Locale VALUES(001,'Laboratorio base','Edificio Matematica',80,60);
INSERT INTO Locale VALUES(002,'Sala disegno','Ingegneria',90,65);
INSERT INTO Locale VALUES(003,'Lab. chimica','Farmacia',60,70);
INSERT INTO Locale VALUES(004,'Aula V','Matematica',50,100);
INSERT INTO Locale VALUES(005,'Laboratorio fisica nucleare','Fisica',90,50);
INSERT INTO Locale VALUES(006,'Laboratorio biologia','Biologia',75,60);
INSERT INTO Locale VALUES(007,'Laboratorio meccanica','Ingengeria',99,50);
INSERT INTO Locale VALUES(008,'BSI','Campus Unimore',98,300);
INSERT INTO Locale VALUES(009,'CUS','Campus Unimore',95,200);
INSERT INTO Locale VALUES(010,'Tito Speri','Biotecnologia',97,350);

INSERT INTO Laboratorio VALUES(001,'Edificio Matematica','KJHDSA92T242S','ZXCVBN12D563A');
INSERT INTO Laboratorio VALUES(003,'Farmacia','LKJHGF12A094D','POIUYT23S543S');
INSERT INTO Laboratorio VALUES(006,'Biologia','ZAFCDF16D513F','ZXCVBN12D563A');

INSERT INTO PDLavoro VALUES(0001,001);
INSERT INTO PDLavoro VALUES(0002,006);
INSERT INTO PDLavoro VALUES(0003,003);
INSERT INTO PDLavoro VALUES(0004,001);
INSERT INTO PDLavoro VALUES(0005,003);
INSERT INTO PDLavoro VALUES(0006,006);
INSERT INTO PDLavoro VALUES(0007,001);
INSERT INTO PDLavoro VALUES(0008,003);
INSERT INTO PDLavoro VALUES(0009,006);
INSERT INTO PDLavoro VALUES(0010,001);

INSERT INTO Assegnato VALUES(0001,001);
INSERT INTO Assegnato VALUES(0009,006);
INSERT INTO Assegnato VALUES(0007,001);

INSERT INTO Libero VALUES(0002,006);
INSERT INTO Libero VALUES(0008,003);

INSERT INTO Prenotabile VALUES(0003,003);
INSERT INTO Prenotabile VALUES(0004,001);
INSERT INTO Prenotabile VALUES(0005,003);
INSERT INTO Prenotabile VALUES(0006,006);
INSERT INTO Prenotabile VALUES(0010,001);

INSERT INTO Assegnazione VALUES(02,'ASDFNH92L010K','ZXCVBN12D563A',0001,001);
INSERT INTO Assegnazione VALUES(03,'ASDFNH92L010K','ZXCVBN12D563A',0001,001);
INSERT INTO Assegnazione VALUES(04,'ERTYUI23L543D','POIUYT23S543S',0007,001);
INSERT INTO Assegnazione VALUES(07,'ASDFNH92L010K','GHCXBA45D163A',0009,006);
INSERT INTO Assegnazione VALUES(08,'ASDFNH92L010K','GHCXBA45D163A',0009,006);
INSERT INTO Assegnazione VALUES(10,'ERTYUI23L543D','POIUYT23S543S',0007,001);
INSERT INTO Assegnazione VALUES(11,'ERTYUI23L543D','GHCXBA45D163A',0009,006);
INSERT INTO Assegnazione VALUES(12,'ASDFNH92L010K','GHCXBA45D163A',0001,001);
INSERT INTO Assegnazione VALUES(12,'ERTYUI23L543D','POIUYT23S543S',0007,001);

INSERT INTO Prenotazione VALUES('24/01/2013','14:00',0003,'ASDFNH92L010K',003);
INSERT INTO Prenotazione VALUES('24/01/2013','14:00',0004,'ERTYUI23L543D',001);
INSERT INTO Prenotazione VALUES('30/01/2013','09:00',0005,'GHCXBA45D163A',003);
INSERT INTO Prenotazione VALUES('01/02/2013','10:00',0003,'QWEASD12G234C',003);
INSERT INTO Prenotazione VALUES('09/02/2013','11:00',0006,'ASDFNH92L010K',006);
INSERT INTO Prenotazione VALUES('10/02/2013','14:00',0006,'ERTYUI23L543D',006);
INSERT INTO Prenotazione VALUES('16/03/2013','18:00',0003,'GHCXBA45D163A',003);
INSERT INTO Prenotazione VALUES('01/04/2013','17:00',0010,'ASDFNH92L010K',001);
INSERT INTO Prenotazione VALUES('09/04/2013','13:00',0005,'SDFRTY45L871H',003);


INSERT INTO Accede VALUES(0002,'ASDFNH92L010K','14:00',006);
INSERT INTO Accede VALUES(0008,'SDFRTY45L871H','16:00',003);
INSERT INTO Accede VALUES(0002,'QWEASD12G234C','17:00',006);
INSERT INTO Accede VALUES(0008,'ERTYUI23L543D','11:00',003);

INSERT INTO Risorsa VALUES(1,0001,'Windows Office',001);
INSERT INTO Risorsa VALUES(2,0004,'Stampante',001);
INSERT INTO Risorsa VALUES(3,0006,'shell',006);
INSERT INTO Risorsa VALUES(4,0001,'Campo minato',001);
INSERT INTO Risorsa VALUES(5,0006,'Scanner',006);
INSERT INTO Risorsa VALUES(6,0002,'Beuta',006);
INSERT INTO Risorsa VALUES(7,0001,'Microscopio',001);
INSERT INTO Risorsa VALUES(8,0009,'Pipetta',006);
INSERT INTO Risorsa VALUES(9,0001,'Dinamometro',001);
INSERT INTO Risorsa VALUES(10,0010,'Vetrino',001);

INSERT INTO DistribuzionePR VALUES(100,2011,1,'KJHDSA92T242S');
INSERT INTO DistribuzionePR VALUES(112,2014,2,'LKJHGF12A094D');
INSERT INTO DistribuzionePR VALUES(112,2014,1,'LKJHGF12A094D');
INSERT INTO DistribuzionePR VALUES(112,2014,9,'KJHDSA92T242S');
INSERT INTO DistribuzionePR VALUES(100,2011,2,'LKJHGF12A094D');
INSERT INTO DistribuzionePR VALUES(110,2010,1,'LKJHGF12A094D');
INSERT INTO DistribuzionePR VALUES(310,2013,9,'LKJHGF12A094D');
INSERT INTO DistribuzionePR VALUES(110,2010,5,'KJHDSA92T242S');
INSERT INTO DistribuzionePR VALUES(310,2013,7,'KJHDSA92T242S');


INSERT INTO Tipologia VALUES(100,'14:00','16:00');
INSERT INTO Tipologia VALUES(101,'8:00','19:00');
INSERT INTO Tipologia VALUES(102,'10:00','12:00');
INSERT INTO Tipologia VALUES(103,'9:00','20:00');
INSERT INTO Tipologia VALUES(104,'14:00','18:00');
INSERT INTO Tipologia VALUES(105,'8:30','12:00');

INSERT INTO Permesso VALUES('ASDFNH92L010K',100,001);
INSERT INTO Permesso VALUES('SDFRTY45L871H',102,008);
INSERT INTO Permesso VALUES('ZXCVBN12D563A',105,004);
INSERT INTO Permesso VALUES('ZXCVBN12D563A',104,008);
INSERT INTO Permesso VALUES('ASDFNH92L010K',103,009);
INSERT INTO Permesso VALUES('ERTYUI23L543D',102,001);
INSERT INTO Permesso VALUES('KJHDSA92T242S',101,003);
INSERT INTO Permesso VALUES('POIUYT23S543S',100,001);

INSERT INTO Accesso VALUES('24/01/2013','15:00',21340,001);
INSERT INTO Accesso VALUES('02/02/2013','10:00',22354,008);
INSERT INTO Accesso VALUES('10/02/2013','11:00',12002,004);
INSERT INTO Accesso VALUES('15/02/2013','15:00',12002,008);
INSERT INTO Accesso VALUES('01/03/2013','19:00',21340,009);
