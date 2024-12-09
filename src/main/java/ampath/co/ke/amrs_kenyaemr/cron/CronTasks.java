package ampath.co.ke.amrs_kenyaemr.cron;

import ampath.co.ke.amrs_kenyaemr.methods.AMRSTranslater;
import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.MigrateCareData;
import ampath.co.ke.amrs_kenyaemr.tasks.MigrateRegistration;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    private AMRSFormsMappingService formsMappingService;
    @Autowired
    private AMRSEncounterFormsMappingService amrsEncounterFormsMappingService;
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
    @Autowired
    private AMRSPersonAtrributesService amrsPersonAtrributesService;
    @Autowired
    private AMRSRegimenSwitchService amrsRegimenSwitchService;
    @Autowired
    private AMRSPatientStatusService amrsPatientStatusService;

    @Autowired
    private AMRSGreenCardService amrstcaService;

    @Autowired
    private AMRSMappingService amrsMappingService;

    @Autowired
    private AMRSPatientRelationshipService amrsPatientRelationshipService;

    @Autowired
    private AMRSTranslater amrsTranslater;

    @Autowired
    private AMRSOrdersResultsService amrsOrdersResultsService;


    @Value("${mapping.endpoint:http://localhost:8082/mappings/concepts}")
    private String mappingEndpoint;

    private final RestTemplate restTemplate = new RestTemplate();

    // @Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void callEndpoint() {
        try {
            String response = restTemplate.getForObject(mappingEndpoint, String.class);
            System.out.println("Endpoint response: " + response);
        } catch (Exception e) {
            System.err.println("Error calling the endpoint: " + e.getMessage());
        }
    }

   // @Scheduled(cron = "0 */1 * ? * *")
   //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000) // Every 30 minutes
   public void ProcessUsers() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateRegistration.users(server,username,password,locationId,parentUuid, amrsUserServices,OpenMRSURL,auth);

    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000) // Every 30 minutes
    public void ProcessPatients() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateRegistration.patients(server,username,password,locationId,parentUuid,amrsPatientServices,amrsIdentifiersService,amrsPersonAtrributesService,amrsPatientStatusService,OpenMRSURL,auth);

    }

    @Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000) // Every 30 minutes
    public void ProcessPatientRelationShips() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateRegistration.patient_relationship(server,username,password,locationId,parentUuid,amrsPatientRelationshipService, amrsTranslater,OpenMRSURL,auth);
        }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void civilStatus() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.patientStatus(server, username, password, locationId,parentUuid, amrsPatientStatusService, amrsConceptMappingService, amrsPatientServices, OpenMRSURL, auth);
    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
   public void ProcessPrograms() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.programs(server,username,password,locationId,parentUuid, amrsProgramService, amrsPatientServices, OpenMRSURL,auth);
    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void ProcessVisits() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.visits(server,username,password,locationId,parentUuid, amrsVisitService, amrsObsService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);
    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void ProcessEncounters() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.encounters(server,username,password,locationId,parentUuid, amrsEncounterService, amrsPatientServices, amrsVisitService, OpenMRSURL,auth);

    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void ProcessTriage() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.triage(server,username,password,locationId,parentUuid, amrsTriageService, amrsPatientServices, amrsEncounterService,amrsConceptMappingService,amrsVisitService ,OpenMRSURL,auth);

    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void ProcessOrders() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.order(server,username,password,locationId,parentUuid, amrsOrderService, amrsPatientServices, amrsEncounterMappingService, amrsConceptMappingService,amrsEncounterService, amrsMappingService, OpenMRSURL,auth);

    }


    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void HIVEnrollments() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.hivenrollment(server,username,password,locationId,parentUuid, amrsHIVEnrollmentService, amrsPatientServices, amrsTranslater, OpenMRSURL,auth);
    }


    //@Scheduled(cron = "0 */1 * ? * *")
    public void ProcessEncounterMapping() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.encounterMappings(server,username,password,locationId,parentUuid, amrsEncounterMappingService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);

    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void ProcessProgramSwitches() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.DrugSwitches(server, username, password, locationId,parentUuid, amrsRegimenSwitchService, amrsTranslater, amrsPatientServices,OpenMRSURL, auth);
    }

  //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
  public void ProcessProgramEnrollments() throws JSONException, ParseException, SQLException, IOException {
    String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
    String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
    MigrateCareData.programEnrollments(server, username, password, locationId,parentUuid, amrsEnrollmentService, amrsEncounterService, amrsConceptMappingService, OpenMRSURL, auth);
  }


    //@Scheduled(initialDelay = 0, fixedRate = 50 * 60 * 1000)
    public void processGreenCard() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.processGreenCard(server,username,password,locationId,parentUuid, amrstcaService, amrsPatientServices, amrsEncounterMappingService, amrsMappingService,amrsEncounterService, OpenMRSURL,auth);
    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void ProcessFormMappings() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.formsMappings(server,username,password,locationId,parentUuid, formsMappingService, amrsPatientServices, null, OpenMRSURL,auth);
    }

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void EncounterFormsMappings() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.EncounterFormsMappings(server,username,password,locationId,parentUuid, amrsEncounterFormsMappingService, amrsPatientServices, null, OpenMRSURL,auth);
    }
  //  MCX

    // obs

    //@Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000)
    public void ProcessObs() throws JSONException, ParseException, SQLException, IOException {
        String locationId="'8cad59c8-7f88-4964-aa9e-908f417f70b2','08feb14c-1352-11df-a1f1-0026b9348838','65bdb112-a254-4cf9-a5a7-29dce997312d','8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        String parentUuid="'8cad59c8-7f88-4964-aa9e-908f417f70b2'";
        MigrateCareData.newObs(server,username,password,locationId,parentUuid, amrsObsService,  amrsTranslater,amrsPatientServices,amrsEncounterService ,OpenMRSURL,auth);
    }

}
