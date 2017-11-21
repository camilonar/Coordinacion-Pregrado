DROP TABLE formatoa;
 DROP TABLE formatob; 
 DROP TABLE formatoc;
  DROP TABLE estudiante_anteproyecto;
  DROP TABLE codirector; 
  DROP TABLE anteproyecto; 
  ALTER TABLE profesor MODIFY COLUMN profesor.idProfesor INT AUTO_INCREMENT;
   ALTER TABLE profesor ADD COLUMN codigoProfesor VARCHAR(15);

CREATE TABLE IF NOT EXISTS anteproyecto (
  idAnteproyecto INT NOT NULL auto_increment ,
  tituloAnteproyecto VARCHAR(100) NOT NULL, 
  fechaAnteproyecto DATE NOT NULL,
  programaAnteproyecto INT NOT NULL,
  directorAnteproyecto INT NOT NULL, 
  PRIMARY KEY (idAnteproyecto), INDEX fk_anteproyecto_programa1_idx (programaAnteproyecto ASC),
  INDEX fk_anteproyecto_profesor1_idx (directorAnteproyecto ASC), 
  CONSTRAINT fk_anteproyecto_programa1 FOREIGN KEY (programaAnteproyecto) 
   REFERENCES programa (idPrograma) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_anteproyecto_profesor1 FOREIGN KEY (directorAnteproyecto)
   REFERENCES profesor (idProfesor) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = InnoDB;

CREATE TABLE estudiante_anteproyecto ( 
  estudiante int(11) NOT NULL, 
  anteproyecto int(11) NOT NULL, 
  PRIMARY KEY (estudiante,anteproyecto), 
  KEY fk_estudiante_anteproyecto_estudiante1_idx (estudiante), 
  KEY fk_estudiante_anteproyecto_anteproyecto1_idx (anteproyecto), 
  CONSTRAINT fk_estudiante_anteproyecto_anteproyecto1 FOREIGN KEY (anteproyecto) 
  REFERENCES anteproyecto (idAnteproyecto) ON DELETE NO ACTION ON UPDATE NO ACTION, 
  CONSTRAINT fk_estudiante_anteproyecto_estudiante1 FOREIGN KEY (estudiante) 
  REFERENCES estudiante (idEstudiante) ON DELETE NO ACTION ON UPDATE NO ACTION 
 )
 ENGINE=InnoDB DEFAULT CHARSET=utf8; 

 CREATE TABLE IF NOT EXISTS formatoA ( 
 	idFormatoA INT NOT NULL auto_increment, 
 	claveFormatoA VARCHAR(45) NOT NULL, 
 	anteproyectoFormatoA INT NOT NULL, 
 	PRIMARY KEY (idFormatoA), INDEX fk_formatoA_anteproyecto1_idx (anteproyectoFormatoA ASC), 
 	CONSTRAINT fk_formatoA_anteproyecto1 FOREIGN KEY (anteproyectoFormatoA) 
 	REFERENCES anteproyecto (idAnteproyecto) ON DELETE NO ACTION ON UPDATE NO ACTION
)
 ENGINE = InnoDB;
 CREATE TABLE IF NOT EXISTS formatoB (
  idFormatoB INT NOT NULL auto_increment,
  claveFormatoB VARCHAR(45) NULL, 
  anteproyectoFormatoB INT NOT NULL, 
  PRIMARY KEY (idFormatoB), INDEX fk_formatoB_anteproyecto1_idx (anteproyectoFormatoB ASC), 
  CONSTRAINT fk_formatoB_anteproyecto1 FOREIGN KEY (anteproyectoFormatoB) 
  REFERENCES anteproyecto (idAnteproyecto) ON DELETE NO ACTION ON UPDATE NO ACTION
  ) 
 ENGINE = InnoDB;
  CREATE TABLE IF NOT EXISTS formatoC (
   idFormatoC INT NOT NULL auto_increment, 
   claveFormatoC VARCHAR(45) NULL,
   anteproyectoFormatoC INT NOT NULL, 
   PRIMARY KEY (idFormatoC), 
   INDEX fk_formatoC_anteproyecto1_idx (anteproyectoFormatoC ASC), 
   CONSTRAINT fk_formatoC_anteproyecto1 FOREIGN KEY (anteproyectoFormatoC) 
   REFERENCES anteproyecto (idAnteproyecto) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB;

  