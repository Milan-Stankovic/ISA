create table admin (tip varchar(255) not null, id bigint not null, primary key (id))
create table admin_mesta (admin_id bigint not null, mesta_id bigint not null)
create table bodovna_skala (id bigint generated by default as identity, bronze_popust integer not null, bronze_treshold integer not null, datum time, gold_popust integer not null, gold_treshold integer not null, silver_popust integer not null, silver_treshold integer not null, primary key (id))
create table cenovnik_sedista (id bigint generated by default as identity, doplata integer not null, tip varchar(255), sala_id bigint, primary key (id))
create table dogadjaj (id bigint generated by default as identity, broj_ocena integer not null, donosi_bodova integer not null, glumci_str varchar(255), naziv varchar(255), opis varchar(255), prosecna_ocena float not null, reziser varchar(255), slika varchar(255), status varchar(255), trajanje integer not null, zanr varchar(255), mesto_odrzavanja_id bigint, primary key (id))
create table dogadjaj_prikazuje_se (dogadjaj_id bigint not null, prikazuje_se_id bigint not null)
create table encryption (id bigint generated by default as identity, encrypted_pass binary(255) not null, korisnikid bigint not null, salt binary(255) not null, primary key (id))
create table glumac (id bigint generated by default as identity, ime varchar(255), prezime varchar(255), primary key (id))
create table karta (id bigint generated by default as identity, puna_cena integer not null, vreme_odrzavanja timestamp, pozoriste_bioskop_id bigint, sediste_id bigint, primary key (id))
create table korisnik (id bigint generated by default as identity, broj_telefona varchar(255), email varchar(255) not null, grad varchar(255), ime varchar(255), password varchar(255) not null, prezime varchar(255), status varchar(255) not null, user_name varchar(255) not null, primary key (id))
create table obavestenje (id bigint generated by default as identity, deleted boolean not null, prima varchar(255), tekst varchar(255), ponuda_id bigint, primary key (id))
create table polovan_rekvizit (id bigint generated by default as identity, kraj timestamp, naziv varchar(255), opis varchar(255), slika varchar(255), status varchar(255), postavio_id bigint, primary key (id))
create table polovan_rekvizit_licitacija (polovan_rekvizit_id bigint not null, licitacija_id bigint not null)
create table ponuda (id bigint generated by default as identity, ponudio varchar(255), prihvaceno boolean not null, suma double not null, obavestenje_id bigint, rekvizit_id bigint, primary key (id))
create table poziv (id bigint generated by default as identity, ocena_ambijenta integer not null, ocena_filma integer not null, ocenjeno boolean not null, pozvan boolean not null, status varchar(255) not null, karta_id bigint, osoba_id bigint, rezervacija_id bigint, primary key (id))
create table pozoriste_bioskop (id bigint generated by default as identity, adresa varchar(255), broj_ocena integer not null, naziv varchar(255), opis varchar(255), prosecna_ocena float not null, tip varchar(255), url_mape varchar(2084), primary key (id))
create table pozoriste_bioskop_admini (pozoriste_bioskop_id bigint not null, admini_id bigint not null)
create table pozoriste_bioskop_projekcije (pozoriste_bioskop_id bigint not null, projekcije_id bigint not null)
create table pozoriste_bioskop_sale (pozoriste_bioskop_id bigint not null, sale_id bigint not null)
create table prijatelj (id bigint generated by default as identity, status varchar(255), posiljalac_id bigint, primalac_id bigint, primary key (id))
create table projekcija (id bigint generated by default as identity, aktivna boolean not null, cena double not null, vreme timestamp not null, dogadjaj_id bigint not null, sala_id bigint not null, primary key (id))
create table projekcija_rezervacije (projekcija_id bigint not null, rezervacije_id bigint not null)
create table projekcija_zauzeta_sedista (projekcija_id bigint not null, zauzeta_sedista_id bigint not null)
create table registrovani_korisnik (bodovi integer not null, id bigint not null, primary key (id))
create table registrovani_korisnik_ponude (registrovani_korisnik_id bigint not null, ponude_id bigint not null)
create table registrovani_korisnik_pozivi (registrovani_korisnik_id bigint not null, pozivi_id bigint not null)
create table registrovani_korisnik_prijatelji (registrovani_korisnik_id bigint not null, prijatelji_id bigint not null)
create table registrovani_korisnik_rekviziti (registrovani_korisnik_id bigint not null, rekviziti_id bigint not null)
create table registrovani_korisnik_rezervacije (registrovani_korisnik_id bigint not null, rezervacije_id bigint not null)
create table rezervacija (id bigint generated by default as identity, popust integer not null, projekcija_id bigint not null, rezervisao_id bigint, primary key (id))
create table rezervacija_urezervaciji (rezervacija_id bigint not null, urezervaciji_id bigint not null)
create table rezervacija_rekvizita (id bigint generated by default as identity, datum time, reseno boolean not null, izvrsio_id bigint, primary key (id))
create table sala (id bigint generated by default as identity, br_red integer not null, br_sedista integer not null, ime varchar(255), ustanova_id bigint, primary key (id))
create table sala_sedista (sala_id bigint not null, sedista_id bigint not null)
create table sediste (id bigint generated by default as identity, broj integer not null, red integer not null, tip_sedista varchar(255), primary key (id))
create table zvanican_rekvizit (id bigint generated by default as identity, aktivan boolean not null, cena double not null, naziv varchar(255), opis varchar(255), slika varchar(255), postavio_id bigint, preuzeti_id bigint, primary key (id))
create table zvanican_rekvizit_rezervacije (zvanican_rekvizit_id bigint not null, rezervacije_id bigint not null)
alter table dogadjaj_prikazuje_se add constraint UK_rs0orqut7oqgtuqyvdeqsw1mn unique (prikazuje_se_id)
alter table encryption add constraint UK_9048nwg3uffnc40l9sd97ireg unique (korisnikid)
alter table korisnik add constraint UK_87tbhltaua2a6k6jrdfl1kqap unique (email)
alter table polovan_rekvizit_licitacija add constraint UK_47kjx73smfxj0q9o40ve9ndd7 unique (licitacija_id)
alter table pozoriste_bioskop_projekcije add constraint UK_itbnqrcqguxyov4pntk1x6o5s unique (projekcije_id)
alter table pozoriste_bioskop_sale add constraint UK_n1687qjui3ks269611avxdcro unique (sale_id)
alter table registrovani_korisnik_ponude add constraint UK_jv507vkap89d3l0muc6qtbx9a unique (ponude_id)
alter table registrovani_korisnik_pozivi add constraint UK_rq9bsk8udd0li3bxoxss4v4lq unique (pozivi_id)
alter table registrovani_korisnik_rekviziti add constraint UK_qadwxmksfa69qyj3dsc0be613 unique (rekviziti_id)
alter table rezervacija_urezervaciji add constraint UK_giec8pan83ty65tqjyi4v0441 unique (urezervaciji_id)
alter table sala_sedista add constraint UK_b5l4b76is5r6r38wtnlpklo3r unique (sedista_id)
alter table zvanican_rekvizit_rezervacije add constraint UK_n3sgqft6d2ltvvo3uuoifn58n unique (rezervacije_id)
alter table admin add constraint FKkvq52k2uwukgidts6ayoj1is0 foreign key (id) references korisnik
alter table admin_mesta add constraint FK26eobr5k98htsbvmu1fxyd1bl foreign key (mesta_id) references pozoriste_bioskop
alter table admin_mesta add constraint FK332usi0c6d88xghg3q69sw3hj foreign key (admin_id) references admin
alter table cenovnik_sedista add constraint FK9l0ld3csc6g283fmo06f95hsa foreign key (sala_id) references sala
alter table dogadjaj add constraint FKfwdxbvhv74qfpj2kdmyjdricm foreign key (mesto_odrzavanja_id) references pozoriste_bioskop
alter table dogadjaj_prikazuje_se add constraint FKl3dpx99f9yfp6jnqjrcyqul4r foreign key (prikazuje_se_id) references projekcija
alter table dogadjaj_prikazuje_se add constraint FKidmysrtvu0pq4btwan7121nvp foreign key (dogadjaj_id) references dogadjaj
alter table karta add constraint FK1l2lssaa3nc9sne411unbvka3 foreign key (pozoriste_bioskop_id) references pozoriste_bioskop
alter table karta add constraint FKls9vcs1mfc5he843aaa2ixhr6 foreign key (sediste_id) references sediste
alter table obavestenje add constraint FKox3umey6y64epclo87fcf7tbf foreign key (ponuda_id) references ponuda
alter table polovan_rekvizit add constraint FK3b5uhag0jon1b4cmeiwnvysyy foreign key (postavio_id) references korisnik
alter table polovan_rekvizit_licitacija add constraint FKfi4gsvc2x5bin98hdrxv61qtt foreign key (licitacija_id) references ponuda
alter table polovan_rekvizit_licitacija add constraint FKm1rjby8ag5v55546ph2y1ioht foreign key (polovan_rekvizit_id) references polovan_rekvizit
alter table ponuda add constraint FK3jio1x3m9a9e4bf5bdqt2et9h foreign key (obavestenje_id) references obavestenje
alter table ponuda add constraint FK26qr1dibyba3l0eiu3yrua09k foreign key (rekvizit_id) references polovan_rekvizit
alter table poziv add constraint FKn80aa1a47a55kc1ntbxok22y6 foreign key (karta_id) references karta
alter table poziv add constraint FKs3jva011dr9uwpc6bb4yj775l foreign key (osoba_id) references registrovani_korisnik
alter table poziv add constraint FKe2jms382kx897b5ai28au3n0j foreign key (rezervacija_id) references rezervacija
alter table pozoriste_bioskop_admini add constraint FK5p9jeuc8m68cou9d2tj7j1oss foreign key (admini_id) references admin
alter table pozoriste_bioskop_admini add constraint FKpsm9qei7qwv49563uhuur5age foreign key (pozoriste_bioskop_id) references pozoriste_bioskop
alter table pozoriste_bioskop_projekcije add constraint FKno0782nq1hr4m5w0752d9q9v0 foreign key (projekcije_id) references projekcija
alter table pozoriste_bioskop_projekcije add constraint FK1s81dxutnuatc1umhmnkvcxf3 foreign key (pozoriste_bioskop_id) references pozoriste_bioskop
alter table pozoriste_bioskop_sale add constraint FKlcjdmcngnomgwqt2sw9fibejd foreign key (sale_id) references sala
alter table pozoriste_bioskop_sale add constraint FKja1yy857iqt48vi9772otya9h foreign key (pozoriste_bioskop_id) references pozoriste_bioskop
alter table prijatelj add constraint FKkmjek4r3vfwuwq6tb14qsc1q7 foreign key (posiljalac_id) references registrovani_korisnik
alter table prijatelj add constraint FKmh3to1hqcf1tfymycbx02alyi foreign key (primalac_id) references registrovani_korisnik
alter table projekcija add constraint FK1nxg4hohvwq88wxqemb8ei6x foreign key (dogadjaj_id) references dogadjaj
alter table projekcija add constraint FK4fk9plx6lt30cvex5q8b943sh foreign key (sala_id) references sala
alter table projekcija_rezervacije add constraint FKn06ogttdt5yniqwop6u1d6dhv foreign key (rezervacije_id) references rezervacija
alter table projekcija_rezervacije add constraint FK9cus9vtygb9vswsqep7qkn69f foreign key (projekcija_id) references projekcija
alter table projekcija_zauzeta_sedista add constraint FKrs0fnhpvon355fo5kpd4ti6fi foreign key (zauzeta_sedista_id) references sediste
alter table projekcija_zauzeta_sedista add constraint FK2496k8u6nts2u9q4pttwhb0hd foreign key (projekcija_id) references projekcija
alter table registrovani_korisnik add constraint FKjxh4aofub8xh781q28yysmlp6 foreign key (id) references korisnik
alter table registrovani_korisnik_ponude add constraint FK5fqw11a17r8sclv3k0ptgihfo foreign key (ponude_id) references ponuda
alter table registrovani_korisnik_ponude add constraint FKsr1g912pcjbhu8qwutlu5weto foreign key (registrovani_korisnik_id) references registrovani_korisnik
alter table registrovani_korisnik_pozivi add constraint FKsphcl1th9gy8f8b29h9cgb4a foreign key (pozivi_id) references poziv
alter table registrovani_korisnik_pozivi add constraint FKpdo3nniraidkqot87lpc5m3kb foreign key (registrovani_korisnik_id) references registrovani_korisnik
alter table registrovani_korisnik_prijatelji add constraint FKl9ngxlqth01cxx86kr3y6ifkb foreign key (prijatelji_id) references prijatelj
alter table registrovani_korisnik_prijatelji add constraint FKp8vyj7r8m6jjjak7v3pu7oa7e foreign key (registrovani_korisnik_id) references registrovani_korisnik
alter table registrovani_korisnik_rekviziti add constraint FKkw9wvovixbnvpvs1fm4jidrw4 foreign key (rekviziti_id) references polovan_rekvizit
alter table registrovani_korisnik_rekviziti add constraint FKnyex86bi670lul67o3m76ldcc foreign key (registrovani_korisnik_id) references registrovani_korisnik
alter table registrovani_korisnik_rezervacije add constraint FKgm2au8umyrst12scx22rmcl4v foreign key (rezervacije_id) references rezervacija
alter table registrovani_korisnik_rezervacije add constraint FK8ad8m1osysu9d0jctwv13x634 foreign key (registrovani_korisnik_id) references registrovani_korisnik
alter table rezervacija add constraint FK7bqx4mfg6lj8u7phpcy2mrpm8 foreign key (projekcija_id) references projekcija
alter table rezervacija add constraint FK2cpge9jtmj1fx6jkpphaase3v foreign key (rezervisao_id) references registrovani_korisnik
alter table rezervacija_urezervaciji add constraint FKf44hj2psjfaf1mtuqm8ht80la foreign key (urezervaciji_id) references poziv
alter table rezervacija_urezervaciji add constraint FKonvjpvc98yi7t3c2ewyvbwk2 foreign key (rezervacija_id) references rezervacija
alter table rezervacija_rekvizita add constraint FK1ticr1s11gutt025ofq96b5kh foreign key (izvrsio_id) references korisnik
alter table sala add constraint FK4q4j29es7b9bq6646wrc4031a foreign key (ustanova_id) references pozoriste_bioskop
alter table sala_sedista add constraint FKb8mbdc4488a9471frlvbo0uhg foreign key (sedista_id) references sediste
alter table sala_sedista add constraint FKnrt2t6jlqfumkc04vsn31lvjq foreign key (sala_id) references sala
alter table zvanican_rekvizit add constraint FKc2m50ugr4gw0ucpidmowb5q0y foreign key (postavio_id) references admin
alter table zvanican_rekvizit add constraint FKpa89xa8vdu170fa5yn7vsel0y foreign key (preuzeti_id) references pozoriste_bioskop
alter table zvanican_rekvizit_rezervacije add constraint FKao3taak1biedcppe549xc7kct foreign key (rezervacije_id) references rezervacija_rekvizita
alter table zvanican_rekvizit_rezervacije add constraint FKmcifdtt660djjq30ticfo9yxg foreign key (zvanican_rekvizit_id) references zvanican_rekvizit
