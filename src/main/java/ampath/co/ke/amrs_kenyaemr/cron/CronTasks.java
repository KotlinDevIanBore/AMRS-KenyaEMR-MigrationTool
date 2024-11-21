package ampath.co.ke.amrs_kenyaemr.cron;

import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.MigrateCareData;
import ampath.co.ke.amrs_kenyaemr.tasks.MigrateRegistration;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@Component
public class CronTasks {
    @Value("${spring.etl.username}")
    public  String username;
    @Value("${spring.etl.password}")
    public String password;
    @Value("${spring.etl.server}")
    public  String server;
    @Value("${spring.openmrs.url}")
    public  String OpenMRSURL;
    @Value("${spring.openmrs.auth}")
    public  String auth;
    @Autowired
    private LocationService locationsService;
    @Autowired
    private AMRSUserServices amrsUserServices;
    @Autowired
    private AMRSIdentifiersService amrsIdentifiersService;
    @Autowired
    private AMRSPatientServices amrsPatientServices;
    @Autowired
    private AMRSProgramService amrsProgramService;
    @Autowired
    private AMRSEnrollmentService amrsEnrollmentService;
    @Autowired
    private AMRSObsService amrsObsService;
    @Autowired
    private AMRSConceptMappingService amrsConceptMappingService;
    @Autowired
    private AMRSVisitService amrsVisitService;
    @Autowired
    private AMRSTriageService amrsTriageService;
    @Autowired
    private AMRSEncounterMappingService amrsEncounterMappingService;
    @Autowired
    private AMRSEncounterService amrsEncounterService;

    @Autowired
    private AMRSHIVEnrollmentService amrsHIVEnrollmentService;
    @Autowired
    private AMRSOrderService amrsOrderService;

    //@Scheduled(cron = "0 */1 * ? * *")
    public void ProcessUsers() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateRegistration.users(server,username,password,locationId,parentUuid, amrsUserServices,OpenMRSURL,auth);

    }

    //@Scheduled(cron = "0 */1 * ? * *")
    public void ProcessPatients() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateRegistration.patients(server,username,password,locationId,parentUuid,amrsPatientServices,amrsIdentifiersService,OpenMRSURL,auth);

    }
    //@Scheduled(cron = "0 */1 * ? * *")
    public void ProcessPrograms() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
      

        //HIVEnrollment
        MigrateCareData.hivenrollment(server,username,password,locationId,parentUuid, amrsHIVEnrollmentService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);

         MigrateCareData.triage(server,username,password,locationId,parentUuid,amrsTriageService,amrsPatientServices, amrsConceptMappingService,OpenMRSURL,auth);


    }
    // @Scheduled(cron = "0 */1 * ? * *")
    public void ProcessVisits() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.visits(server,username,password,locationId,parentUuid, amrsVisitService, amrsObsService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);
       // MigrateCareData.encounterMappings(server,username,password,locationId,parentUuid, amrsEncounterMappingService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);

    }
    //@Scheduled(cron = "0 */1 * ? * *")
    public void ProcessEncounterMapping() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.encounterMappings(server,username,password,locationId,parentUuid, amrsEncounterMappingService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);

    }

    // @Scheduled(cron = "0 */1 * ? * *")
    public void ProcessOrders() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.order(server,username,password,locationId,parentUuid, amrsOrderService, amrsPatientServices, amrsEncounterMappingService, amrsConceptMappingService, OpenMRSURL,auth);

    }

     @Scheduled(cron = "0 */1 * ? * *")
    public void ProcessEncounters() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.encounters(server,username,password,locationId,parentUuid, amrsEncounterService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);

    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void ProcessTriage() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.triage(server,username,password,locationId,parentUuid, amrsTriageService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);

    }
}
