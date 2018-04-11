create table admin (tip varchar(255) not null, id bigint not null, primary key (id))
create table admin_mesta (admin_id bigint not null, mesta_id bigint not null)
create table cenovnik_sedista (id bigint generated by default as identity, doplata integer not null, tip varchar(255), sala_id bigint, ustanova_id bigint, primary key (id))
create table dogadjaj (id bigint generated by default as identity, broj_ocena integer not null, donosi_bodova integer not null, naziv varchar(255), opis varchar(255), prosecna_ocena float not null, reziser varchar(255), trajanje integer not null, zanr varchar(255), primary key (id))
create table dogadjaj_glumci (dogadjaj_id bigint not null, glumci_id bigint not null)
create table glumac (id bigint generated by default as identity, ime varchar(255), prezime varchar(255), primary key (id))
create table karta (id bigint not null, puna_cena integer not null, vreme_odrzavanja timestamp, pozoriste_bioskop_id bigint, sediste_id bigint, primary key (id))
create table korisnik (id bigint generated by default as identity, broj_telefona varchar(255), email varchar(255) not null, ime varchar(255), password varchar(255) not null, prezime varchar(255), status varchar(255) not null, user_name varchar(255) not null, primary key (id))
create table ponuda (id bigint generated by default as identity, prihvaceno boolean, suma double not null, ponudio_id bigint, rekvizit_id bigint, primary key (id))
create table poziv (id bigint generated by default as identity, ocena_ambijenta integer not null, ocena_filma integer not null, pozvan boolean not null, status varchar(255) not null, karta_id bigint, osoba_id bigint, rezervacija_id bigint, primary key (id))
create table pozoriste_bioskop (id bigint generated by default as identity, adressa varchar(255), broj_ocena integer not null, bronze_popust integer not null, bronze_treshold integer not null, gold_popust integer not null, gold_treshold integer not null, naziv varchar(255), opis varchar(255), prosecna_ocena float not null, silver_popust integer not null, silver_treshold integer not null, tip varchar(255), url_mape varchar(255), primary key (id))
create table pozoriste_bioskop_admini (pozoriste_bioskop_id bigint not null, admini_id bigint not null)
create table pozoriste_bioskop_projekcije (pozoriste_bioskop_id bigint not null, projekcije_id bigint not null)
create table pozoriste_bioskop_sale (pozoriste_bioskop_id bigint not null, sale_id bigint not null)
create table prijatelj (id bigint generated by default as identity, status varchar(255), posiljalac_id bigint, primalac_id bigint, primary key (id))
create table projekcija (id bigint generated by default as identity, cena double not null, vreme timestamp not null, dogadjaj_id bigint not null, sala_id bigint not null, primary key (id))
create table projekcija_rezervacije (projekcija_id bigint not null, rezervacije_id bigint not null)
create table projekcija_zauzeta_sedista (projekcija_id bigint not null, zauzeta_sedista_id bigint not null)
create table registrovani_korisnik (bodovi integer not null, id bigint not null, primary key (id))
create table registrovani_korisnik_ponude (registrovani_korisnik_id bigint not null, ponude_id bigint not null)
create table registrovani_korisnik_pozivi (registrovani_korisnik_id bigint not null, pozivi_id bigint not null)
create table registrovani_korisnik_prijatelji (registrovani_korisnik_id bigint not null, prijatelji_id bigint not null)
create table registrovani_korisnik_rekviziti (registrovani_korisnik_id bigint not null, rekviziti_id bigint not null)
create table registrovani_korisnik_rezervacije (registrovani_korisnik_id bigint not null, rezervacije_id bigint not null)
create table rekvizit (id bigint generated by default as identity, kraj timestamp, status varchar(255), postavio_id bigint, preuzeti_id bigint, primary key (id))
create table rekvizit_licitacija (rekvizit_id bigint not null, licitacija_id bigint not null)
create table rezervacija (id bigint generated by default as identity, popust integer not null, projekcija_id bigint not null, rezervisao_id bigint, primary key (id))
create table rezervacija_urezervaciji (rezervacija_id bigint not null, urezervaciji_id bigint not null)
create table sala (id bigint generated by default as identity, br_red integer not null, br_sedista integer not null, ime varchar(255), ustanova_id bigint, primary key (id))
create table sala_sedista (sala_id bigint not null, sedista_id bigint not null)
create table sediste (id bigint generated by default as identity, broj integer not null, red integer not null, tip_sedista varchar(255), primary key (id))
alter table korisnik add constraint UK_87tbhltaua2a6k6jrdfl1kqap unique (email)
alter table pozoriste_bioskop_projekcije add constraint UK_itbnqrcqguxyov4pntk1x6o5s unique (projekcije_id)
alter table pozoriste_bioskop_sale add constraint UK_n1687qjui3ks269611avxdcro unique (sale_id)
alter table projekcija_rezervacije add constraint UK_40i1e9xdie6q0hl5siv7q4tl7 unique (rezervacije_id)
alter table registrovani_korisnik_ponude add constraint UK_jv507vkap89d3l0muc6qtbx9a unique (ponude_id)
alter table registrovani_korisnik_pozivi add constraint UK_rq9bsk8udd0li3bxoxss4v4lq unique (pozivi_id)
alter table registrovani_korisnik_prijatelji add constraint UK_9avcddsenmr7jelt272ojatov unique (prijatelji_id)
alter table registrovani_korisnik_rekviziti add constraint UK_qadwxmksfa69qyj3dsc0be613 unique (rekviziti_id)
alter table registrovani_korisnik_rezervacije add constraint UK_kfppkehk1fn4sn6s5v8qiwlud unique (rezervacije_id)
alter table rekvizit_licitacija add constraint UK_g7brmwxg015rugaivg4twu0en unique (licitacija_id)
alter table rezervacija_urezervaciji add constraint UK_giec8pan83ty65tqjyi4v0441 unique (urezervaciji_id)
alter table sala_sedista add constraint UK_b5l4b76is5r6r38wtnlpklo3r unique (sedista_id)
alter table admin add constraint FKkvq52k2uwukgidts6ayoj1is0 foreign key (id) references korisnik
alter table admin_mesta add constraint FK26eobr5k98htsbvmu1fxyd1bl foreign key (mesta_id) references pozoriste_bioskop
alter table admin_mesta add constraint FK332usi0c6d88xghg3q69sw3hj foreign key (admin_id) references admin
alter table cenovnik_sedista add constraint FK9l0ld3csc6g283fmo06f95hsa foreign key (sala_id) references sala
alter table cenovnik_sedista add constraint FKgawgk2r94y0ggvvk6ugw0gp1p foreign key (ustanova_id) references pozoriste_bioskop
alter table dogadjaj_glumci add constraint FKiata7pfobbxv7cht6lcbp2x45 foreign key (glumci_id) references glumac
alter table dogadjaj_glumci add constraint FKmbbhjycjii4440i3d35in48ew foreign key (dogadjaj_id) references dogadjaj
alter table karta add constraint FK1l2lssaa3nc9sne411unbvka3 foreign key (pozoriste_bioskop_id) references pozoriste_bioskop
alter table karta add constraint FKls9vcs1mfc5he843aaa2ixhr6 foreign key (sediste_id) references sediste
alter table ponuda add constraint FKinpyq5vh8tyr2edlamtogf0rt foreign key (ponudio_id) references korisnik
alter table ponuda add constraint FK3qt5rfeiwa6vyliri7dtp4t0n foreign key (rekvizit_id) references rekvizit
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
alter table registrovani_korisnik_rekviziti add constraint FKdkbn56q4afohecdcxat56djpn foreign key (rekviziti_id) references rekvizit
alter table registrovani_korisnik_rekviziti add constraint FKnyex86bi670lul67o3m76ldcc foreign key (registrovani_korisnik_id) references registrovani_korisnik
alter table registrovani_korisnik_rezervacije add constraint FKgm2au8umyrst12scx22rmcl4v foreign key (rezervacije_id) references rezervacija
alter table registrovani_korisnik_rezervacije add constraint FK8ad8m1osysu9d0jctwv13x634 foreign key (registrovani_korisnik_id) references registrovani_korisnik
alter table rekvizit add constraint FKey7895bt0voumftve9ueqdyp6 foreign key (postavio_id) references korisnik
alter table rekvizit add constraint FKse3oqjufsw382ccgmwicw0hb3 foreign key (preuzeti_id) references pozoriste_bioskop
alter table rekvizit_licitacija add constraint FKdwe6yr8p2yueyynby1j3k6x2p foreign key (licitacija_id) references ponuda
alter table rekvizit_licitacija add constraint FK8ifn084j5b21ckexxe9hymq9h foreign key (rekvizit_id) references rekvizit
alter table rezervacija add constraint FK7bqx4mfg6lj8u7phpcy2mrpm8 foreign key (projekcija_id) references projekcija
alter table rezervacija add constraint FK2cpge9jtmj1fx6jkpphaase3v foreign key (rezervisao_id) references registrovani_korisnik
alter table rezervacija_urezervaciji add constraint FKf44hj2psjfaf1mtuqm8ht80la foreign key (urezervaciji_id) references poziv
alter table rezervacija_urezervaciji add constraint FKonvjpvc98yi7t3c2ewyvbwk2 foreign key (rezervacija_id) references rezervacija
alter table sala add constraint FK4q4j29es7b9bq6646wrc4031a foreign key (ustanova_id) references pozoriste_bioskop
alter table sala_sedista add constraint FKb8mbdc4488a9471frlvbo0uhg foreign key (sedista_id) references sediste
alter table sala_sedista add constraint FKnrt2t6jlqfumkc04vsn31lvjq foreign key (sala_id) references sala
