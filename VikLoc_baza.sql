-- =============================================================================
-- Diagram Name: ERA
-- Created on: 17.11.2017. 17:40:07
-- Diagram Version: 
-- =============================================================================


CREATE TABLE "korisnik" (
	"id" SERIAL NOT NULL,
	"ime" varchar(45),
	"prezime" varchar(45),
	"korime" varchar(45),
	"lozinka" varchar(45),
	PRIMARY KEY("id")
);




CREATE TABLE "artikl" (
	"id" SERIAL NOT NULL,
	"naziv" varchar(45),
	"opis" varchar(80),
	"kategorija" int4,
	"izradio" int4,
	PRIMARY KEY("id")
);




CREATE TABLE "kategorija" (
	"id" SERIAL NOT NULL,
	"naziv" varchar(45),
	"opis" varchar(80),
	"izradio" int4,
	PRIMARY KEY("id")
);




CREATE TABLE "pozicija" (
	"oznaka" varchar(45),
	"mapa" int4,
	"skladiste" int4,
	"artikl" int4
);




CREATE TABLE "skladiste" (
	"id" SERIAL NOT NULL,
	"naziv" varchar(45),
	"adresa" varchar(45),
	"opis" varchar(80),
	PRIMARY KEY("id")
);





ALTER TABLE "artikl" ADD CONSTRAINT "Ref_artikl_to_korisnik" FOREIGN KEY ("izradio")
	REFERENCES "korisnik"("id")
	MATCH SIMPLE
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	NOT DEFERRABLE;

ALTER TABLE "artikl" ADD CONSTRAINT "Ref_artikl_to_kategorija" FOREIGN KEY ("kategorija")
	REFERENCES "kategorija"("id")
	MATCH SIMPLE
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	NOT DEFERRABLE;

ALTER TABLE "kategorija" ADD CONSTRAINT "Ref_kategorija_to_korisnik" FOREIGN KEY ("izradio")
	REFERENCES "korisnik"("id")
	MATCH SIMPLE
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	NOT DEFERRABLE;

ALTER TABLE "pozicija" ADD CONSTRAINT "Ref_pozicija_to_skladiste" FOREIGN KEY ("skladiste")
	REFERENCES "skladiste"("id")
	MATCH SIMPLE
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	NOT DEFERRABLE;

ALTER TABLE "pozicija" ADD CONSTRAINT "Ref_pozicija_to_artikl" FOREIGN KEY ("artikl")
	REFERENCES "artikl"("id")
	MATCH SIMPLE
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	NOT DEFERRABLE;


