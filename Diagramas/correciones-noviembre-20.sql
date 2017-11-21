ALTER TABLE profesor DROP COLUMN codigoProfesor;
ALTER TABLE profesor ADD COLUMN codigoProfesor VARCHAR(15) NOT NULL;

ALTER TABLE estudiante DROP COLUMN codigoEstudiante;
ALTER TABLE estudiante ADD COLUMN codigoEstudiante VARCHAR(15) NOT NULL;

ALTER TABLE anteproyecto MODIFY COLUMN tituloAnteproyecto VARCHAR(100) NOT NULL;