-- -----------------------------------------------------
-- Table `coordpis-bd`.`facultad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coordpis-bd`.`facultad` (
  `idfacultad` INT NOT NULL AUTO_INCREMENT,
  `facultadNombre` VARCHAR(200) NULL,
  PRIMARY KEY (`idfacultad`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coordpis-bd`.`programa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coordpis-bd`.`programa` (
  `idPrograma` INT NOT NULL AUTO_INCREMENT,
  `nombrePrograma` VARCHAR(200) NULL,
  `idfacultad` INT NOT NULL,
  PRIMARY KEY (`idPrograma`),
  INDEX `fk_programa_facultad1_idx` (`idfacultad` ASC),
  CONSTRAINT `fk_programa_facultad1`
    FOREIGN KEY (`idfacultad`)
    REFERENCES `coordpis-bd`.`facultad` (`idfacultad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coordpis-bd`.`usuario_programa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coordpis-bd`.`usuario_programa` (
  `idUsuario` BIGINT(20) NOT NULL,
  `idPrograma` INT NOT NULL,
  `nombreUsuario` VARCHAR(75) NULL,
  INDEX `fk_usuario_programa_usuario1_idx` (`idUsuario` ASC),
  INDEX `fk_usuario_programa_programa1_idx` (`idPrograma` ASC),
  PRIMARY KEY (`idUsuario`, `idPrograma`),
  CONSTRAINT `fk_usuario_programa_usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `coordpis-bd`.`usuario` (`USUID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_programa_programa1`
    FOREIGN KEY (`idPrograma`)
    REFERENCES `coordpis-bd`.`programa` (`idPrograma`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coordpis-bd`.`usuario_departamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coordpis-bd`.`usuario_departamento` (
  `idUsuario` BIGINT(20) NOT NULL,
  `idDepartamento` INT(11) NOT NULL,
  `nombreUsuario` VARCHAR(75) NULL,
  INDEX `fk_usuario_departamento_usuario1_idx` (`idUsuario` ASC),
  INDEX `fk_usuario_departamento_departamento1_idx` (`idDepartamento` ASC),
  PRIMARY KEY (`idUsuario`, `idDepartamento`),
  CONSTRAINT `fk_usuario_departamento_usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `coordpis-bd`.`usuario` (`USUID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_departamento_departamento1`
    FOREIGN KEY (`idDepartamento`)
    REFERENCES `coordpis-bd`.`departamento` (`id_departamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `coordpis-bd`.`usuario_programa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coordpis-bd`.`usuario_programa` (
  `idUsuario` BIGINT(20) NOT NULL,
  `idPrograma` INT NOT NULL,
  `nombreUsuario` VARCHAR(45) NULL,
  INDEX `fk_usuario_programa_usuario1_idx` (`idUsuario` ASC),
  INDEX `fk_usuario_programa_programa1_idx` (`idPrograma` ASC),
  PRIMARY KEY (`idUsuario`, `idPrograma`),
  CONSTRAINT `fk_usuario_programa_usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `coordpis-bd`.`usuario` (`USUID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_programa_programa1`
    FOREIGN KEY (`idPrograma`)
    REFERENCES `coordpis-bd`.`programa` (`idPrograma`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `coordpis-bd`.`usuario_departamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coordpis-bd`.`usuario_departamento` (
  `usuario_USUID` BIGINT(20) NOT NULL,
  `departamento_id_departamento` INT(11) NOT NULL,
  INDEX `fk_usuario_departamento_usuario1_idx` (`usuario_USUID` ASC),
  INDEX `fk_usuario_departamento_departamento1_idx` (`departamento_id_departamento` ASC),
  PRIMARY KEY (`usuario_USUID`, `departamento_id_departamento`),
  CONSTRAINT `fk_usuario_departamento_usuario1`
    FOREIGN KEY (`usuario_USUID`)
    REFERENCES `coordpis-bd`.`usuario` (`USUID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_departamento_departamento1`
    FOREIGN KEY (`departamento_id_departamento`)
    REFERENCES `coordpis-bd`.`departamento` (`id_departamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;




--
-- Dumping data for table `facultad`
--

INSERT INTO `facultad` VALUES (1,'Facultad de Artes'),(2,'Facultad de Ciencias Agrarias'),(3,'Facultad de Ciencias de la Salud'),(4,'Facultad de Ciencias Contables, Económicas y Administrativas'),(5,'Facultad de Ciencias Humanas y Sociales'),(6,'Facultad de Ciencias Naturales, Exactas y de la Educación'),(7,'Facultad de Derecho, Ciencias Políticas y Sociales'),(8,'Facultad de Ingeniería Civil'),(9,'Facultad de Ingeniería Electrónica y Telecomunicaciones');

-- Dump completed on 2017-02-25 23:03:20


INSERT INTO `programa` VALUES (1,'Administración de Empresas',4),(2,'Antropología',5),(3,'Artes Plásticas',1),(4,'Biología',6),(5,'Ciencia Política',7),(6,'Comunicación Social',7),(7,'Contaduría Pública',4),(8,'Derecho',7),(9,'Diseño Gráfico',1),(10,'Dirección de Banda',1),(11,'Economía',4),(12,'Enfermería',3),(13,'Filosofía',5),(14,'Fisioterapia',3),(15,'Fonoaudiología',3),(16,'Geografía',5),(17,'Geotecnología',8),(18,'Historia',5),(19,'Ingeniería Agroindustrial',2),(20,'Ingeniería Agropecuaria',2),(21,'Ingeniería Ambiental',8),(22,'Ingeniería Civil',8),(23,'Ingeniería Electrónica y Telecomunicaciones',9),(24,'Ingeniería Forestal',2),(25,'Ingeniería Física',6),(26,'Ingeniería de Sistemas',9),(27,'Ingeniería en Automática Industrial',9),(28,'Lenguas Modernas',5),(29,'Licenciatura en Educación Básica con Énfasis en Ciencias Naturales y Educación Ambiental',6),(30,'Licenciatura en Educación Básica con Énfasis en Educación Artística',6),(31,'Licenciatura en Educación Básica con Énfasis en Educación Física, Recreación y Deportes',6),(32,'Licenciatura en Educación Básica con Énfasis en Lengua Castellana e Inglés',6),(33,'Licenciatura en Español y Literatura',5),(34,'Licenciatura en Etnoeducación',5),(35,'Licenciatura en Lingüística y Semiótica (Santander de Quilichao)',5),(36,'Licenciatura en Matemáticas',6),(37,'Licenciatura en Música',1),(38,'Música Instrumental',1),(39,'Matemáticas',6),(40,'Medicina',3),(41,'Química',6),(42,'Tecnología Agroindustrial',2),(43,'Tecnología en Administración Financiera',4),(44,'Tecnología en Telemática',9),(45,'Turismo',4);

INSERT INTO `usuario_departamento` (`idUsuario`, `idDepartamento`,`nombreUsuario`) VALUES ('2', '8', 'fpino');
INSERT INTO `usuario_programa` (`idUsuario`, `idPrograma`,`nombreUsuario`) VALUES ('1', '26','pmage');