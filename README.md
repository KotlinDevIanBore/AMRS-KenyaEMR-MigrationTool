# AMRS-KenyaEMR-MigrationTool

Create .env file on your root application with the following valiables

To run the application

mvn spring-boot:run

mvn clean spring-boot:run

use openmrs;
call create_etl_tables();
call sp_first_time_setup();
Sort red ribbon;
set foreign_key_checks=0;

sudo lsof -i :8082   
kill -9 58959
