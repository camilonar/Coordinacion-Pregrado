CREATE TABLE `departamento` (
  `id_departamento` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_departamento`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `materia` (
  `id_materia` int(11) NOT NULL AUTO_INCREMENT,
  `semestre` varchar(4) DEFAULT NULL,
  `codigo_materia` varchar(30) DEFAULT NULL,
  `nombre_materia` varchar(70) NOT NULL,
  `creditos` int(11) DEFAULT NULL,
  `intensidad_horaria` int(11) DEFAULT NULL,
  `numero_estudiantes` int(11) DEFAULT NULL,
  `grupos_solicitados` int(11) DEFAULT NULL,
  `grupos_cancelados` int(11) DEFAULT NULL,
  `grupos_ofertados` int(11) DEFAULT NULL,
  `grupos_fusionados` int(11) DEFAULT NULL,
  `grupos_nuevos` int(11) DEFAULT NULL,
  `id_departamento` int(11) NOT NULL,
  PRIMARY KEY (`id_materia`),
  KEY `fk_curso_departamento_idx` (`id_departamento`),
  CONSTRAINT `fk_curso_departamento` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id_departamento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

CREATE TABLE `cargo` (
  `CARID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARNOMBRE` varchar(75) NOT NULL,
  PRIMARY KEY (`CARID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `grupo` (
  `GRUID` varchar(20) NOT NULL,
  `GRUDESCRIPCION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`GRUID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `usuario` (
  `USUID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CARID` bigint(20) DEFAULT NULL,
  `USUFECHANACIMIENTO` datetime NOT NULL,
  `USUNOMBRES` varchar(75) NOT NULL,
  `USUAPELLIDOS` varchar(75) NOT NULL,
  `USUGENERO` char(1) NOT NULL,
  `USUNOMBREUSUARIO` varchar(75) NOT NULL,
  `USUCONTRASENA` varchar(250) NOT NULL,
  `USUEMAIL` varchar(150) NOT NULL,
  `USUFOTO` longblob,
  PRIMARY KEY (`USUID`),
  KEY `FK_USUARIOCARGO` (`CARID`),
  CONSTRAINT `FK_USUARIOCARGO` FOREIGN KEY (`CARID`) REFERENCES `cargo` (`CARID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `usuariogrupo` (
  `GRUID` varchar(20) NOT NULL,
  `USUID` bigint(20) NOT NULL,
  `USUNOMBREUSUARIO` varchar(75) NOT NULL,
  PRIMARY KEY (`GRUID`,`USUID`),
  KEY `FK_USUARIO_USUID` (`USUID`),
  KEY `FK_GRUPOS_GRUID` (`GRUID`),
  CONSTRAINT `FK_GRUPOS_GRUID` FOREIGN KEY (`GRUID`) REFERENCES `grupo` (`GRUID`),
  CONSTRAINT `FK_USUARIO_USUID` FOREIGN KEY (`USUID`) REFERENCES `usuario` (`USUID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `departamento`(`id_departamento`,`nombre`) values (1,'Dpto Matemáticas');
insert into `departamento`(`id_departamento`,`nombre`) values (2,'Dpto Física');
insert into `departamento`(`id_departamento`,`nombre`) values (3,'Humanidades');
insert into `departamento`(`id_departamento`,`nombre`) values (4,'Dpto de Educación Física, Recreación y Deporte');
insert into `departamento`(`id_departamento`,`nombre`) values (5,'Contables');
insert into `departamento`(`id_departamento`,`nombre`) values (6,'Derecho');
insert into `departamento`(`id_departamento`,`nombre`) values (7,'Dpto Telemática');
insert into `departamento`(`id_departamento`,`nombre`) values (8,'Dpto Sistemas');

insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (1,'I','306-1/MAT102','Cálculo I',4,4,0,0,0,0,0,0,1);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (2,'II','307/MAT221','Álgebra Lineal',4,4,0,0,0,0,0,0,1);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (3,'II','311-1/MAT201','Cálculo II',4,4,0,0,0,0,0,0,1);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (4,'III','316-1/MAT202','Cálculo III',4,4,0,0,0,0,0,0,1);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (5,'IV','322-1/MAT242','Ecuaciones Diferenciales Ordinarias',4,4,0,0,0,0,0,0,1);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (6,'VI','MAT131','Estadística y probabilidad',4,4,0,0,0,0,0,0,1);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (7,'II','312-1/FIS112','Mecánica',3,4,0,0,0,0,0,0,2);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (8,'III','317-1/FIS211','Electromagnetismo',3,4,0,0,0,0,0,0,2);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (9,'II','318-1/FIS112L','Laboratorio de Mecánica',1,2,0,0,0,0,0,0,2);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (10,'IV','323-1/FIS212','Vibraciones y Ondas',3,4,0,0,0,0,0,0,2);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (11,'III','329-1/FIS211L','Laboratorio de Electromagnetismo',1,2,0,0,0,0,0,0,2);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (12,'I','EDP141','Lectura y Escritura',2,4,0,0,0,0,0,0,3);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (13,'I','EFISH1','Electiva FISH I',2,4,0,0,0,0,0,0,3);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (14,'II','EFISH2','Electiva FISH II',2,4,0,0,0,0,0,0,3);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (15,'III','EFISH3','Electiva FISH III',2,4,0,0,0,0,0,0,3);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (16,'X','313-9','Ética',2,4,0,0,0,0,0,0,3);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (17,'C','Cualquier Semestre','Actividad Física Formativa',0,2,0,0,0,0,0,0,4);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (18,'VIII','338','Investigación de Operaciones',4,4,0,0,0,0,0,0,5);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (19,'IX','339','Fundamentos de Economia',3,4,0,0,0,0,0,0,5);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (20,'IX','344','Gestión Empresarial',3,4,0,0,0,0,0,0,5);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (21,'IX','351','Legislación Laboral',1,2,0,0,0,0,0,0,6);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (22,'V','SIS502','Arquitectura Computacional',4,6,0,0,0,0,0,0,7);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (23,'VIII','TLM815','Redes',4,6,0,0,0,0,0,0,7);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (24,'I','SIS0103','Introducción a la Ingenieria de sistemas',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (25,'I','SIS0101','Introducción a la Informática',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (26,'I','SIS0102','Laboratorio de Introducción a la Informatica',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (27,'II','SIS201','Progamación OO',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (28,'II','SIS201-L','Laboratorio Programación OO',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (29,'III','SIS301','Estructuras de Datos I',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (30,'III','SIS301L','Laboratorio de Estructuras de Datos I',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (31,'IV','SIS401','Estructuras de Datos II',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (32,'IV','SIS401L','Laboratorio de Estructuras de Datos II',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (33,'IV','SIS402','Bases de datos I',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (34,'IV','SIS402L','Laboratorio de Bases de Datos I',null,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (35,'V','328-1','Análisis Numérico',4,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (36,'V','SIS501','Teoría de la Computación',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (37,'V','SIS503','Ingeniería de Software I',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (38,'V','SIS503L','Labotario de Ingeniería de Software I',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (39,'V','SIS504','Bases de datos II',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (40,'V','SIS504L','Laboratorio de Bases de Datos II',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (41,'VI','SIS602','Ingeniería de Software II',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (42,'VI','SIS602L','Laboratorio de Ingeniería de Software II',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (43,'VI','SIS603','Sistemas Operativos',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (44,'VI','SIS603L','Laboratorio de Sistemas Operativos',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (45,'VI','SIS601','Estructuras de Lenguajes',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (46,'VI','SIS601L','Laboratorio de Estructuras de Lenguajes',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (47,'VII','SIS702','Sistemas Distribuidos',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (48,'VII','SIS702L','Laboratorio de Sistemas Distribuidos',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (49,'VII','SIS703','Ingeniería de Software III',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (50,'VII','SIS703L','Laboratorio de Ingeniería de Software III',1,2,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (51,'VII','SIS801','Metodología de la Investigación',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (52,'VII','SIS706','Teoría y Dinámica de Sistemas',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (53,'VII','331','Inteligencia Artificial',3,6,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (54,'VIII','SIS803','Calidad de Software',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (55,'VIII','SIS802','Proyecto I',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (56,'IX','SIS902','Proyecto II',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (57,'IX','345','Gestión de Proyectos Informáticos',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (58,'VIII',null,'Electiva I',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (59,'VIII',null,'Electiva II',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (60,'IX',null,'Electiva III',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (61,'IX',null,'Electiva IV',3,4,0,0,0,0,0,0,8);
insert into `materia`(`id_materia`,`semestre`,`codigo_materia`,`nombre_materia`,`creditos`,`intensidad_horaria`,`numero_estudiantes`,`grupos_solicitados`,`grupos_cancelados`,`grupos_ofertados`,`grupos_fusionados`,`grupos_nuevos`,`id_departamento`) values (62,'X',null,'Electiva V',3,4,0,0,0,0,0,0,8);

insert into `cargo`(`CARID`,`CARNOMBRE`) values (1,'Docente');
insert into `cargo`(`CARID`,`CARNOMBRE`) values (2,'Administrativo');

insert into `grupo`(`GRUID`,`GRUDESCRIPCION`) values ('1','administrador');
insert into `grupo`(`GRUID`,`GRUDESCRIPCION`) values ('2','coordinador');
insert into `grupo`(`GRUID`,`GRUDESCRIPCION`) values ('3','jefe');

insert into `usuario`(`USUID`,`CARID`,`USUFECHANACIMIENTO`,`USUNOMBRES`,`USUAPELLIDOS`,`USUGENERO`,`USUNOMBREUSUARIO`,`USUCONTRASENA`,`USUEMAIL`,`USUFOTO`) values (1,1,'1981-01-05 00:00:00','Pablo Augusto','Magé','M','pmage','6d49a8bdd35ee851b3eb9e53668c43f2696c42eae6775a0e7f03c8022aa4a3f6','pmage@unicauca.edu.co',null);
insert into `usuario`(`USUID`,`CARID`,`USUFECHANACIMIENTO`,`USUNOMBRES`,`USUAPELLIDOS`,`USUGENERO`,`USUNOMBREUSUARIO`,`USUCONTRASENA`,`USUEMAIL`,`USUFOTO`) values (2,2,'1980-02-05 00:00:00','Francisco José','Pino Correa','M','fpino','452b889d10df869834152618463e1c07ce88001a40c9fff5d4fdf300c65684c6','fpino@unicauca.edu.co',null);

insert into `usuariogrupo`(`GRUID`,`USUID`,`USUNOMBREUSUARIO`) values ('2',1,'pmage');
insert into `usuariogrupo`(`GRUID`,`USUID`,`USUNOMBREUSUARIO`) values ('3',2,'fpino');