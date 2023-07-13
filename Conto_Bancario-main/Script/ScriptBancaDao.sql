 -- ScriptBancaDao
 
CREATE SCHEMA bancaDao ;
create table correntisti (id int primary key auto_increment,nome varchar(20), cognome varchar(20), citta varchar(20), nazione varchar(20), telefono varchar(10));
create table conti (id int primary key auto_increment, id_tipo  int, id_correntista int, data_apertura date, saldo double, data_chiusura date);
create table tipo_conti (id int primary key auto_increment, tipo  varchar(20), tasso int);
create table movimenti (id int primary key auto_increment, id_conto int, saldo_precedente double, saldo_successivo double, data_movimento date, tipo_movimento varchar(45), importo double);
insert into tipo_conti values (1, 'conto corrente',5), (2,'conto deposito',10), (3, 'conto investimento',null);
ALTER TABLE `banca`.`movimenti` 
ADD INDEX `movimento_conto_idx` (`id_conto` ASC) VISIBLE;
;
ALTER TABLE `banca`.`movimenti` 
ADD CONSTRAINT `movimento_conto`
  FOREIGN KEY (`id_conto`)
  REFERENCES `banca`.`conti` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `banca`.`conti` 
ADD INDEX `correntista_idx` (`id_correntista` ASC) VISIBLE;
;
ALTER TABLE `banca`.`conti` 
ADD CONSTRAINT `correntista`
  FOREIGN KEY (`id_correntista`)
  REFERENCES `banca`.`correntisti` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;