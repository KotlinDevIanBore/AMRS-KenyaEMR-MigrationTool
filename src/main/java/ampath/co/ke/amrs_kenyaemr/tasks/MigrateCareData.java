package ampath.co.ke.amrs_kenyaemr.tasks;

import ampath.co.ke.amrs_kenyaemr.models.*;
import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.payloads.CareOpenMRSPayload;
import ampath.co.ke.amrs_kenyaemr.tasks.payloads.EncountersPayload;
import ampath.co.ke.amrs_kenyaemr.tasks.payloads.EnrollmentsPayload;
import org.json.JSONException;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public class MigrateCareData {
    public static void programs (String server, String username, String password, String locations, String parentUUID, AMRSProgramService amrsProgramService, AMRSPatientServices amrsPatientServices, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql="select  pp.patient_id, \n" +
                "       p.name,\n" +
                "       pp.uuid program_uuid,\n" +
                "       pp.location_id,\n" +
                "       l.uuid location_uuid,\n" +
                "       p.concept_id,\n" +
                "       pp.date_enrolled,\n" +
                "       pp.date_completed,\n" +
                "       p.program_id\n" +
                "       from amrs.patient_program pp\n" +
                "       inner join amrs.encounter e on e.patient_id=pp.patient_id\n" +
                "       inner join amrs.program p on p.program_id=pp.program_id\n" +
                "       inner join amrs.location l on l.location_id = e.location_id\n" +
                "       and l.uuid in ('"+ locations +"') \n" + //and e. patient_id in ('1224605,1222698')
                "       group by  pp.patient_id,p.concept_id  order by pp.patient_id desc limit 100";
        System.out.println("locations " + locations + " parentUUID " + parentUUID);
        Connection con = DriverManager.getConnection(server, username, password);
        int x = 0;
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        x = rs.getRow();
        rs.beforeFirst();
        while (rs.next()) {

            String patientId = rs.getString("patient_id");
            String locationId = rs.getString("location_id");
            String programName = rs.getString("name");
            String programUuid = rs.getString("program_uuid");
            String locationUuid = rs.getString("location_uuid");
            String conceptId = rs.getString("concept_id");
            String dateEnrolled = rs.getString("date_enrolled");
            String dateCompleted = rs.getString("date_completed");
            String programId = rs.getString("program_id");


           // List<AMRSPrograms> patientsList = amrsProgramService.getprogramByLocation(rs.getString(2),parentUUID);

            //if (patientsList.size()==0) {

                List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(patientId);

                String person_id=patientId;
                List<AMRSPrograms> amrsPrograms = amrsProgramService.findByPatientIdAndProgramID(patientId, Integer.parseInt(programId));
               if(amrsPrograms.size()==0) {
                   AMRSPrograms ae = new AMRSPrograms();
                   ae.setProgramUUID(programUuid);
                   ae.setPatientId(person_id);
                   ae.setParentLocationUuid(parentUUID);
                   ae.setLocationId(locationId);
                   ae.setUUID(String.valueOf(UUID.randomUUID()));
                   ae.setProgramID(Integer.parseInt(programId));
                   ae.setConceptId(conceptId);
                   ae.setProgramName(programName);
                   ae.setDateEnrolled(dateEnrolled);
                   ae.setDateCompleted(dateCompleted);
                   ae.setPatientKenyaemrUuid(amrsPatients.get(0).getKenyaemrpatientUUID());
                   //ae.setKenyaemruuid(amrsPatients.getKenyaemrpatientUUID());
                   amrsProgramService.save(ae);
                   // }
               }else{

               }

            //Migate Programs
            CareOpenMRSPayload.programs(amrsProgramService,locations,parentUUID,url,auth);
            }

        }

    public static void encounters (String server, String username, String password, String locations, String parentUUID, AMRSObsService amrsEncounterService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql="SELECT o.person_id,\n" +
                "\te.encounter_id,\n" +
                "    e.encounter_datetime,\n" +
                "    e.encounter_type,\n" +
                "    o.concept_id,\n" +
                "    o.obs_datetime,\n" +
                "CASE WHEN o.value_text IS NOT NULL THEN value_text\n" +
                "    WHEN o.value_coded IS NOT NULL THEN value_coded\n" +
                "    WHEN o.value_numeric IS NOT NULL THEN value_numeric\n" +
                "    WHEN o.value_datetime IS NOT NULL THEN value_datetime\n" +
                "END as value,\n" +
                "CASE WHEN o.value_text IS NOT NULL THEN 'text'\n" +
                "    WHEN o.value_coded IS NOT NULL THEN 'coded'\n" +
                "    WHEN o.value_numeric IS NOT NULL THEN 'numeric'\n" +
                "    WHEN o.value_datetime IS NOT NULL THEN 'date_time'\n" +
                "    else 'N/A'\n" +
                "END as value_type,\n" +
                "c.datatype_id,\n" +
                "et.name encounterName\n" +
                "FROM  amrs.encounter e \n" +
                "inner join  amrs.obs o on e.encounter_id=o.encounter_id\n" +
                "inner join  amrs.location l on e.location_id = l.location_id\n" +
                "inner join  amrs.concept c on o.concept_id= c.concept_id\n" +
                "inner join  amrs.encounter_type et on e.encounter_type =et.encounter_type_id\n\n" +
                "where e.encounter_type in (1,3, 32, 265, 266)\n" +
                "and e.location_id in(2,379, 339)\n" +
                "    AND person_id = 1225187\n" +
                "order by e.encounter_datetime desc;";
        System.out.println("locations " + locations + " parentUUID " + parentUUID);
        Connection con = DriverManager.getConnection(server, username, password);
        int x = 0;
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        x = rs.getRow();
        rs.beforeFirst();
        while (rs.next()) {
            String patientId = rs.getString("person_id");
            String encounterId = rs.getString("encounter_id");
            String encounterDatetime = rs.getString("encounter_datetime");
            String encounterType = rs.getString("encounter_type");
            String conceptId = rs.getString("concept_id");
            String obsDatetime = rs.getString("obs_datetime");
            String valueType = rs.getString("value_type");
            String value = rs.getString("value");
            String dtype = rs.getString("datatype_id");
            String encounterName = rs.getString("encounterName");


            List<AMRSObs> amrsEncountersList = amrsEncounterService.findByPatientIdAndEncounterIDAndConceptId(patientId,encounterId,conceptId);
            if(amrsEncountersList.size()==0){
                List<AMRSConceptMapper> cm = amrsConceptMappingService.findByAmrsConceptID(conceptId);
               String kenyaemr_uuid="";
               String kenyaemr_value="";
                if(cm.size()>0){
                    kenyaemr_uuid = cm.get(0).getKenyaemrConceptUUID();
                }
                if(valueType.equals("coded")) {
                    List<AMRSConceptMapper> cv = amrsConceptMappingService.findByAmrsConceptID(value);
                    if (cv.size() > 0) {
                        kenyaemr_value = cv.get(0).getKenyaemrConceptUUID();
                    }else{
                        String missingvalue= value;
                        System.out.println("Missing Value "+ missingvalue);
                    }
                }else{
                    kenyaemr_value =value;
                }

                AMRSObs ae = new AMRSObs();
                ae.setUUID(String.valueOf(UUID.randomUUID()));
                ae.setPatientId(patientId);
                ae.setEncounterID(encounterId);
                ae.setEncounterDatetime(encounterDatetime);
                ae.setEncounterType(encounterType);
                ae.setObsDatetime(obsDatetime);
                ae.setValueType(valueType);
                ae.setValue(value);
                ae.setConceptId(conceptId);
                ae.setKenyaemrconceptuuid(kenyaemr_uuid);
                ae.setKenyaemrvalue(kenyaemr_value);
                ae.setDataType(dtype);
                ae.setEncounterName(encounterName);
                amrsEncounterService.save(ae);

            }else{

            }


        }
       EncountersPayload.encounters(url,auth);
    }
    public static void enrollments (String server, String username, String password, String locations, String parentUUID, AMRSEnrollmentService amrsEnrollmentService, AMRSPatientServices amrsPatientServices, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql="select person_id,encounter_id,enrollment_date,hiv_start_date,death_date,transfer_out,transfer_out_date from etl.flat_hiv_summary_v15b  \n" +
                "where is_clinical_encounter = 1 and next_clinical_datetime_hiv is null\n" +
                "and location_id in (339,2,98,379)";
        System.out.println("locations " + locations + " parentUUID " + parentUUID);
        Connection con = DriverManager.getConnection(server, username, password);
        int x = 0;
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        x = rs.getRow();
        rs.beforeFirst();
        while (rs.next()) {
            String patientId = rs.getString("person_id");
            String encounterId = rs.getString("encounter_id");
            String edate = rs.getString("enrollment_date");
            String startDate = rs.getString("hiv_start_date");
            String to = rs.getString("transfer_out");
            String tod = rs.getString("transfer_out_date");
            String uuid = rs.getString("hiv_start_date");

            List<AMRSEnrollments> enrollmentsList = amrsEnrollmentService.getByPatientID(patientId);
            if(enrollmentsList.size()==0){
                AMRSEnrollments ae = new AMRSEnrollments();
                ae.setPersonId(patientId);
                ae.setEnrollmentDate(edate);
                ae.setFormID("");
                ae.setEncounterType("");

            }else{

            }


        }

            EnrollmentsPayload.encounters(url,auth);
    }

    public static void visits (String server, String username, String password, String locations, String parentUUID, AMRSObsService amrsEncounterService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
      String sql ="select v.visit_id,e.patient_id,visit_type_id,date_started,e.location_id,date_started,encounter_type,v.voided from amrs.visit v\n" +
              "inner join  amrs.encounter e on e.visit_id = v.visit_id\n" +
              "where e.location_id in (2,379,339) \n" +
              "group by  v.visit_id";
    }

    public static void order (String server, String username, String password, String locations, String parentUUID, AMRSOrderService amrsOrderService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql ="SELECT *, UUID() AS migration_uuid, NULL AS kenyaemr_order_uuid, NULL AS kenyaemr_order_id FROM amrs.orders where patient_id in (7870) and order_reason is not null limit 50";
        System.out.println("locations " + locations + " parentUUID " + parentUUID);
        Connection con = DriverManager.getConnection(server, username, password);
        int x = 0;
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        x = rs.getRow();
        rs.beforeFirst();
        while (rs.next()) {
            String patientId = rs.getString("patient_id");
           // String patientId = rs.getString(2);
            String conceptId = rs.getString("concept_id");
            String amrs_uuid = rs.getString("uuid");
            String justification = rs.getString("order_reason");

            List<AMRSOrders> amrsOrders = amrsOrderService.findByUuid(amrs_uuid);
            if(amrsOrders.size()==0){
                String kenyaemr_uuid="";
                AMRSOrders ao = new AMRSOrders();
                List<AMRSConceptMapper> ac = amrsConceptMappingService.findByAmrsConceptID(conceptId);
                if(ac.size()!=0) {
                    kenyaemr_uuid = ac.get(0).getKenyaemrConceptUUID();
                    ao.setConceptId(Integer.valueOf(conceptId));
                    ao.setPatientId(Integer.valueOf(patientId));
                    List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(patientId);
                    String kenyaemr_patirnt_uuid = "";
                    if (amrsPatients.size() != 0) {
                        kenyaemr_patirnt_uuid = amrsPatients.get(0).getKenyaemrpatientUUID();
                    } else {
                        kenyaemr_patirnt_uuid = "Not Found"; // add logic for missing patient
                    }
                    String justificationcode ="";
                    if(!justification.equals("")){
                        List<AMRSConceptMapper> amrsConceptMapper = amrsConceptMappingService.findByAmrsConceptID(justification);
                        if(amrsConceptMapper.size()>0){
                            justificationcode = amrsConceptMapper.get(0).getKenyaemrConceptUUID();

                        }
                    }

                    System.out.println("Patient " + kenyaemr_patirnt_uuid + " concept " + conceptId + " kenyaemr_concept_id " + kenyaemr_uuid +" justification" + justification +"Kenyaemr justicafiation "+ justificationcode);

                }else{
                    System.out.println("No mapping for  " + conceptId );


                }

              //  amrsOrderService.save(ao);

            }





        }
    }



    }
