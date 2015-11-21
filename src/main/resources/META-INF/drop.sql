alter table CambioResultado drop foreign key FK_hsp6ltalc2lkxxulbci23r77l;
alter table Competencia drop foreign key FK_68kjojs2gpt8thuf3q2dt3x4o;
alter table Competencia drop foreign key FK_ff6yjqt48v2iqyikb1fy10s3w;
alter table Competencia drop foreign key FK_btnvhe39i58bpyvlyber0da0u;
alter table Disponibilidad drop foreign key FK_7s2o872ftypt4ajh8mxr4o0gw;
alter table Disponibilidad drop foreign key FK_2qmqqhac889awdefo1d3h2c0k;
alter table Fecha drop foreign key FK_5hbam6evhu02ae59cw2egs88;
alter table Localidad drop foreign key FK_2elhvaur3hsux4tkpyya0mekg;
alter table LugarDeRealizacion drop foreign key FK_471gtymhrp4721a86gqkc0wpa;
alter table ModificacionParticipante drop foreign key FK_sr9j4n11m2kd2o54ykbeekiej;
alter table Participante drop foreign key FK_dl235snmpydepdpxas2inwy13;
alter table Partido drop foreign key FK_26kpipv12gftwxve110buockl;
alter table Partido drop foreign key FK_9xxwkld5n3vksvkxolhjiycwj;
alter table PartidosGanados drop foreign key FK_lkfctkyp62jkx90utu9218r9a;
alter table PartidosGanados drop foreign key FK_hi2mxwvjpoymoe70hvm1i80tf;
alter table PartidosLocales drop foreign key FK_94mxeg7yn7n9545nfhlgtirca;
alter table PartidosLocales drop foreign key FK_2esyll1ikisclijn3lfa8ivg8;
alter table PartidosVisitantes drop foreign key FK_o2yqj3wiof51sdls0gvmyj1ue;
alter table PartidosVisitantes drop foreign key FK_pdmhvqd2ijp2c7dak4yx8txrq;
alter table Provincia drop foreign key FK_kjsc0a5y86tbh0jbu4q4p0gq3;
alter table Resultado drop foreign key FK_p62xg1cad66r06x8n9x956eqf;
alter table Usuario drop foreign key FK_g27o6ognkovmov4hlj3gvqbxr;
alter table Usuario drop foreign key FK_lxj3pu3vu99recchqm4evud;
alter table se_practica_en drop foreign key FK_9ky8je0h2bxbwf6djs0ey6ncs;
alter table se_practica_en drop foreign key FK_18k1jbl4hjngx0xcl1845vjis;
drop table if exists CambioResultado;
drop table if exists Competencia;
drop table if exists Deporte;
drop table if exists Disponibilidad;
drop table if exists Fecha;
drop table if exists Fixture;
drop table if exists InicioSesion;
drop table if exists Localidad;
drop table if exists LugarDeRealizacion;
drop table if exists ModificacionParticipante;
drop table if exists Pais;
drop table if exists Participante;
drop table if exists Partido;
drop table if exists PartidosGanados;
drop table if exists PartidosLocales;
drop table if exists PartidosVisitantes;
drop table if exists Provincia;
drop table if exists Resultado;
drop table if exists Usuario;
drop table if exists se_practica_en;