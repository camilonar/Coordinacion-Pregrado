--Ejecutar este script si se ha ejecutado un script sobre bd antes del 08 de octubre 
ALTER TABLE programa DROP FOREIGN KEY fk_programa_facultad1;
ALTER TABLE programa DROP COLUMN idfacultad;
DROP TABLE facultad;
