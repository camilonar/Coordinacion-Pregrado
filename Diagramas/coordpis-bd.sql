-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 19-10-2017 a las 23:21:28
-- Versión del servidor: 5.7.11
-- Versión de PHP: 5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `coordpis-bd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargo`
--

CREATE TABLE `cargo` (
  `CARID` bigint(20) NOT NULL,
  `CARNOMBRE` varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cargo`
--

INSERT INTO `cargo` (`CARID`, `CARNOMBRE`) VALUES
(1, 'Docente'),
(2, 'Administrativo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `id_departamento` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`id_departamento`, `nombre`) VALUES
(1, 'Dpto Matemáticas'),
(2, 'Dpto Física'),
(3, 'Humanidades'),
(4, 'Dpto de Educación Física, Recreación y Deporte'),
(5, 'Contables'),
(6, 'Derecho'),
(7, 'Dpto Telemática'),
(8, 'Dpto Sistemas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE `grupo` (
  `GRUID` varchar(20) NOT NULL,
  `GRUDESCRIPCION` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`GRUID`, `GRUDESCRIPCION`) VALUES
('1', 'administrador'),
('2', 'coordinador'),
('3', 'jefe');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia`
--

CREATE TABLE `materia` (
  `id_materia` int(11) NOT NULL,
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
  `id_departamento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `materia`
--

INSERT INTO `materia` (`id_materia`, `semestre`, `codigo_materia`, `nombre_materia`, `creditos`, `intensidad_horaria`, `numero_estudiantes`, `grupos_solicitados`, `grupos_cancelados`, `grupos_ofertados`, `grupos_fusionados`, `grupos_nuevos`, `id_departamento`) VALUES
(1, 'I', '306-1/MAT102', 'Cálculo I', 4, 4, 0, 0, 0, 0, 0, 0, 1),
(2, 'II', '307/MAT221', 'Álgebra Lineal', 4, 4, 0, 0, 0, 0, 0, 0, 1),
(3, 'II', '311-1/MAT201', 'Cálculo II', 4, 4, 0, 0, 0, 0, 0, 0, 1),
(4, 'III', '316-1/MAT202', 'Cálculo III', 4, 4, 0, 0, 0, 0, 0, 0, 1),
(5, 'IV', '322-1/MAT242', 'Ecuaciones Diferenciales Ordinarias', 4, 4, 0, 0, 0, 0, 0, 0, 1),
(6, 'VI', 'MAT131', 'Estadística y probabilidad', 4, 4, 0, 0, 0, 0, 0, 0, 1),
(7, 'II', '312-1/FIS112', 'Mecánica', 3, 4, 0, 0, 0, 0, 0, 0, 2),
(8, 'III', '317-1/FIS211', 'Electromagnetismo', 3, 4, 0, 0, 0, 0, 0, 0, 2),
(9, 'II', '318-1/FIS112L', 'Laboratorio de Mecánica', 1, 2, 0, 0, 0, 0, 0, 0, 2),
(10, 'IV', '323-1/FIS212', 'Vibraciones y Ondas', 3, 4, 0, 0, 0, 0, 0, 0, 2),
(11, 'III', '329-1/FIS211L', 'Laboratorio de Electromagnetismo', 1, 2, 0, 0, 0, 0, 0, 0, 2),
(12, 'I', 'EDP141', 'Lectura y Escritura', 2, 4, 0, 0, 0, 0, 0, 0, 3),
(13, 'I', 'EFISH1', 'Electiva FISH I', 2, 4, 0, 0, 0, 0, 0, 0, 3),
(14, 'II', 'EFISH2', 'Electiva FISH II', 2, 4, 0, 0, 0, 0, 0, 0, 3),
(15, 'III', 'EFISH3', 'Electiva FISH III', 2, 4, 0, 0, 0, 0, 0, 0, 3),
(16, 'X', '313-9', 'Ética', 2, 4, 0, 0, 0, 0, 0, 0, 3),
(17, 'C', 'Cualquier Semestre', 'Actividad Física Formativa', 0, 2, 0, 0, 0, 0, 0, 0, 4),
(18, 'VIII', '338', 'Investigación de Operaciones', 4, 4, 0, 0, 0, 0, 0, 0, 5),
(19, 'IX', '339', 'Fundamentos de Economia', 3, 4, 0, 0, 0, 0, 0, 0, 5),
(20, 'IX', '344', 'Gestión Empresarial', 3, 4, 0, 0, 0, 0, 0, 0, 5),
(21, 'IX', '351', 'Legislación Laboral', 1, 2, 0, 0, 0, 0, 0, 0, 6),
(22, 'V', 'SIS502', 'Arquitectura Computacional', 4, 6, 0, 0, 0, 0, 0, 0, 7),
(23, 'VIII', 'TLM815', 'Redes', 4, 6, 0, 0, 0, 0, 0, 0, 7),
(24, 'I', 'SIS0103', 'Introducción a la Ingenieria de sistemas', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(25, 'I', 'SIS0101', 'Introducción a la Informática', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(26, 'I', 'SIS0102', 'Laboratorio de Introducción a la Informatica', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(27, 'II', 'SIS201', 'Progamación OO', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(28, 'II', 'SIS201-L', 'Laboratorio Programación OO', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(29, 'III', 'SIS301', 'Estructuras de Datos I', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(30, 'III', 'SIS301L', 'Laboratorio de Estructuras de Datos I', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(31, 'IV', 'SIS401', 'Estructuras de Datos II', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(32, 'IV', 'SIS401L', 'Laboratorio de Estructuras de Datos II', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(33, 'IV', 'SIS402', 'Bases de datos I', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(34, 'IV', 'SIS402L', 'Laboratorio de Bases de Datos I', NULL, 2, 0, 0, 0, 0, 0, 0, 8),
(35, 'V', '328-1', 'Análisis Numérico', 4, 4, 0, 0, 0, 0, 0, 0, 8),
(36, 'V', 'SIS501', 'Teoría de la Computación', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(37, 'V', 'SIS503', 'Ingeniería de Software I', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(38, 'V', 'SIS503L', 'Labotario de Ingeniería de Software I', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(39, 'V', 'SIS504', 'Bases de datos II', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(40, 'V', 'SIS504L', 'Laboratorio de Bases de Datos II', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(41, 'VI', 'SIS602', 'Ingeniería de Software II', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(42, 'VI', 'SIS602L', 'Laboratorio de Ingeniería de Software II', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(43, 'VI', 'SIS603', 'Sistemas Operativos', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(44, 'VI', 'SIS603L', 'Laboratorio de Sistemas Operativos', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(45, 'VI', 'SIS601', 'Estructuras de Lenguajes', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(46, 'VI', 'SIS601L', 'Laboratorio de Estructuras de Lenguajes', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(47, 'VII', 'SIS702', 'Sistemas Distribuidos', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(48, 'VII', 'SIS702L', 'Laboratorio de Sistemas Distribuidos', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(49, 'VII', 'SIS703', 'Ingeniería de Software III', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(50, 'VII', 'SIS703L', 'Laboratorio de Ingeniería de Software III', 1, 2, 0, 0, 0, 0, 0, 0, 8),
(51, 'VII', 'SIS801', 'Metodología de la Investigación', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(52, 'VII', 'SIS706', 'Teoría y Dinámica de Sistemas', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(53, 'VII', '331', 'Inteligencia Artificial', 3, 6, 0, 0, 0, 0, 0, 0, 8),
(54, 'VIII', 'SIS803', 'Calidad de Software', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(55, 'VIII', 'SIS802', 'Proyecto I', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(56, 'IX', 'SIS902', 'Proyecto II', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(57, 'IX', '345', 'Gestión de Proyectos Informáticos', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(58, 'VIII', NULL, 'Electiva I', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(59, 'VIII', NULL, 'Electiva II', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(60, 'IX', NULL, 'Electiva III', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(61, 'IX', NULL, 'Electiva IV', 3, 4, 0, 0, 0, 0, 0, 0, 8),
(62, 'X', NULL, 'Electiva V', 3, 4, 0, 0, 0, 0, 0, 0, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `programa`
--

CREATE TABLE `programa` (
  `idPrograma` int(11) NOT NULL,
  `nombrePrograma` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `programa`
--

INSERT INTO `programa` (`idPrograma`, `nombrePrograma`) VALUES
(1, 'Administración de Empresas'),
(2, 'Antropología'),
(3, 'Artes Plásticas'),
(4, 'Biología'),
(5, 'Ciencia Política'),
(6, 'Comunicación Social'),
(7, 'Contaduría Pública'),
(8, 'Derecho'),
(9, 'Diseño Gráfico'),
(10, 'Dirección de Banda'),
(11, 'Economía'),
(12, 'Enfermería'),
(13, 'Filosofía'),
(14, 'Fisioterapia'),
(15, 'Fonoaudiología'),
(16, 'Geografía'),
(17, 'Geotecnología'),
(18, 'Historia'),
(19, 'Ingeniería Agroindustrial'),
(20, 'Ingeniería Agropecuaria'),
(21, 'Ingeniería Ambiental'),
(22, 'Ingeniería Civil'),
(23, 'Ingeniería Electrónica y Telecomunicaciones'),
(24, 'Ingeniería Forestal'),
(25, 'Ingeniería Física'),
(26, 'Ingeniería de Sistemas'),
(27, 'Ingeniería en Automática Industrial'),
(28, 'Lenguas Modernas'),
(29, 'Licenciatura en Educación Básica con Énfasis en Ciencias Naturales y Educación Ambiental'),
(30, 'Licenciatura en Educación Básica con Énfasis en Educación Artística'),
(31, 'Licenciatura en Educación Básica con Énfasis en Educación Física, Recreación y Deportes'),
(32, 'Licenciatura en Educación Básica con Énfasis en Lengua Castellana e Inglés'),
(33, 'Licenciatura en Español y Literatura'),
(34, 'Licenciatura en Etnoeducación'),
(35, 'Licenciatura en Lingüística y Semiótica (Santander de Quilichao)'),
(36, 'Licenciatura en Matemáticas'),
(37, 'Licenciatura en Música'),
(38, 'Música Instrumental'),
(39, 'Matemáticas'),
(40, 'Medicina'),
(41, 'Química'),
(42, 'Tecnología Agroindustrial'),
(43, 'Tecnología en Administración Financiera'),
(44, 'Tecnología en Telemática'),
(45, 'Turismo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `USUID` bigint(20) NOT NULL,
  `CARID` bigint(20) DEFAULT NULL,
  `USUFECHANACIMIENTO` datetime NOT NULL,
  `USUNOMBRES` varchar(75) NOT NULL,
  `USUAPELLIDOS` varchar(75) NOT NULL,
  `USUGENERO` char(1) NOT NULL,
  `USUNOMBREUSUARIO` varchar(75) NOT NULL,
  `USUCONTRASENA` varchar(250) NOT NULL,
  `USUEMAIL` varchar(150) NOT NULL,
  `USUFOTO` longblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`USUID`, `CARID`, `USUFECHANACIMIENTO`, `USUNOMBRES`, `USUAPELLIDOS`, `USUGENERO`, `USUNOMBREUSUARIO`, `USUCONTRASENA`, `USUEMAIL`, `USUFOTO`) VALUES
(1, 1, '1981-01-05 00:00:00', 'Pablo Augusto', 'Magé', 'M', 'pmage', '6d49a8bdd35ee851b3eb9e53668c43f2696c42eae6775a0e7f03c8022aa4a3f6', 'pmage@unicauca.edu.co', NULL),
(2, 2, '1980-02-05 00:00:00', 'Francisco José', 'Pino Correa', 'M', 'fpino', '452b889d10df869834152618463e1c07ce88001a40c9fff5d4fdf300c65684c6', 'fpino@unicauca.edu.co', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuariogrupo`
--

CREATE TABLE `usuariogrupo` (
  `GRUID` varchar(20) NOT NULL,
  `USUID` bigint(20) NOT NULL,
  `USUNOMBREUSUARIO` varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuariogrupo`
--

INSERT INTO `usuariogrupo` (`GRUID`, `USUID`, `USUNOMBREUSUARIO`) VALUES
('2', 1, 'pmage'),
('3', 2, 'fpino');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_departamento`
--

CREATE TABLE `usuario_departamento` (
  `idUsuario` bigint(20) NOT NULL,
  `idDepartamento` int(11) NOT NULL,
  `nombreUsuario` varchar(75) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario_departamento`
--

INSERT INTO `usuario_departamento` (`idUsuario`, `idDepartamento`, `nombreUsuario`) VALUES
(2, 8, 'fpino');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_programa`
--

CREATE TABLE `usuario_programa` (
  `idUsuario` bigint(20) NOT NULL,
  `idPrograma` int(11) NOT NULL,
  `nombreUsuario` varchar(75) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario_programa`
--

INSERT INTO `usuario_programa` (`idUsuario`, `idPrograma`, `nombreUsuario`) VALUES
(1, 26, 'pmage');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cargo`
--
ALTER TABLE `cargo`
  ADD PRIMARY KEY (`CARID`);

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`id_departamento`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`GRUID`);

--
-- Indices de la tabla `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`id_materia`),
  ADD KEY `fk_curso_departamento_idx` (`id_departamento`);

--
-- Indices de la tabla `programa`
--
ALTER TABLE `programa`
  ADD PRIMARY KEY (`idPrograma`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`USUID`),
  ADD KEY `FK_USUARIOCARGO` (`CARID`);

--
-- Indices de la tabla `usuariogrupo`
--
ALTER TABLE `usuariogrupo`
  ADD PRIMARY KEY (`GRUID`,`USUID`),
  ADD KEY `FK_USUARIO_USUID` (`USUID`),
  ADD KEY `FK_GRUPOS_GRUID` (`GRUID`);

--
-- Indices de la tabla `usuario_departamento`
--
ALTER TABLE `usuario_departamento`
  ADD PRIMARY KEY (`idUsuario`,`idDepartamento`),
  ADD KEY `fk_usuario_departamento_usuario1_idx` (`idUsuario`),
  ADD KEY `fk_usuario_departamento_departamento1_idx` (`idDepartamento`);

--
-- Indices de la tabla `usuario_programa`
--
ALTER TABLE `usuario_programa`
  ADD PRIMARY KEY (`idUsuario`,`idPrograma`),
  ADD KEY `fk_usuario_programa_usuario1_idx` (`idUsuario`),
  ADD KEY `fk_usuario_programa_programa1_idx` (`idPrograma`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cargo`
--
ALTER TABLE `cargo`
  MODIFY `CARID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `id_departamento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT de la tabla `materia`
--
ALTER TABLE `materia`
  MODIFY `id_materia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT de la tabla `programa`
--
ALTER TABLE `programa`
  MODIFY `idPrograma` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `USUID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `materia`
--
ALTER TABLE `materia`
  ADD CONSTRAINT `fk_curso_departamento` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id_departamento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK_USUARIOCARGO` FOREIGN KEY (`CARID`) REFERENCES `cargo` (`CARID`);

--
-- Filtros para la tabla `usuariogrupo`
--
ALTER TABLE `usuariogrupo`
  ADD CONSTRAINT `FK_GRUPOS_GRUID` FOREIGN KEY (`GRUID`) REFERENCES `grupo` (`GRUID`),
  ADD CONSTRAINT `FK_USUARIO_USUID` FOREIGN KEY (`USUID`) REFERENCES `usuario` (`USUID`);

--
-- Filtros para la tabla `usuario_departamento`
--
ALTER TABLE `usuario_departamento`
  ADD CONSTRAINT `fk_usuario_departamento_departamento1` FOREIGN KEY (`idDepartamento`) REFERENCES `departamento` (`id_departamento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuario_departamento_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`USUID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario_programa`
--
ALTER TABLE `usuario_programa`
  ADD CONSTRAINT `fk_usuario_programa_programa1` FOREIGN KEY (`idPrograma`) REFERENCES `programa` (`idPrograma`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuario_programa_usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`USUID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
