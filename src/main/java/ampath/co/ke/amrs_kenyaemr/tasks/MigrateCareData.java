package ampath.co.ke.amrs_kenyaemr.tasks;

import ampath.co.ke.amrs_kenyaemr.models.*;
import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.payloads.*;
import org.json.JSONException;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public class MigrateCareData {
    public static void programs (String server, String username, String password, String locations, String parentUUID, AMRSProgramService amrsProgramService, AMRSPatientServices amrsPatientServices, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
       List<AMRSPrograms> amrsProgramss = amrsProgramService.findFirstByOrderByIdDesc();
        String sql="";
        if(amrsProgramss.size()>0){
          System.out.println("Latest program "+ amrsProgramss.get(0).getPatientId());
            String ppid = amrsProgramss.get(0).getAmrsPatientProgramID();
            sql = "select pp.patient_program_id, pp.patient_id, \n" +
                    "       p.name,\n" +
                    "       p.uuid program_uuid,\n" +
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
                    "       and l.uuid in (" + locations + ") and pp.patient_program_id>="+ppid +"  \n" + //and e. patient_id in ('1224605,1222698')
                    "       group by  pp.patient_id,p.concept_id  order by pp.patient_program_id asc limit 100";
            System.out.println("SQLs "+ sql);

        }else {

            sql = "select pp.patient_program_id, pp.patient_id, \n" +
                    "       p.name,\n" +
                    "       p.uuid program_uuid,\n" +
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
                    "       and l.uuid in (" + locations + ") \n" + //and e. patient_id in ('1224605,1222698')
                    "       group by  pp.patient_id,p.concept_id  order by pp.patient_program_id asc";
        }
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
            String patientProgramId = rs.getString("patient_program_id");

           // List<AMRSPrograms> patientsList = amrsProgramService.getprogramByLocation(rs.getString(2),parentUUID);
                List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(patientId);
                String person_id=patientId;
                List<AMRSPrograms> amrsPrograms = amrsProgramService.findByPatientIdAndProgramID(patientId, Integer.parseInt(programId));
               if(amrsPrograms.size()==0) {
                   String kenyaemr_progra_uuid = Mappers.programs(programUuid);
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
                   if(amrsPatients.size()>0) {
                       ae.setPatientKenyaemrUuid(amrsPatients.get(0).getKenyaemrpatientUUID());
                   }
                   ae.setKenyaemrProgramUuid(Mappers.programs(programUuid));
                   ae.setAmrsPatientProgramID(patientProgramId);
                   amrsProgramService.save(ae);
               }

            //Migate Programs
            CareOpenMRSPayload.programs(amrsProgramService,locations,parentUUID,url,auth);
            }

        }

    public static void encounters (String server, String username, String password, String locations, String parentUUID, AMRSEncounterService amrsEncounterService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {

        List<AMRSEncounters> amrsEncounters = amrsEncounterService.findFirstByOrderByIdDesc();
        String sql="select patient_id as person_id, \n" +
                "e.uuid as amrsUUID,\n" +
                "encounter_id as concept_id,\n" +
                "  case\n" +
                "  when e.encounter_type = 147 then 'e24209cc-0a1d-11eb-8f2a-bb245320c623'\n" +
                "  when e.encounter_type = 55 then 'b402d094-bff3-4b31-b167-82426b4e3e28'\n" +
                "  when e.encounter_type = 146 then 'e24209cc-0a1d-11eb-8f2a-bb245320c623'\n" +
                "  when e.encounter_type = 287 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 96 then 'b402d094-bff3-4b31-b167-82426b4e3e28'\n" +
                "  when e.encounter_type = 126 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type = 81 then '1495edf8-2df2-11e9-b210-d663bd873d93'\n" +
                "  when e.encounter_type = 70 then '70a0158e-98f3-400b-9c90-a13c84b72065'\n" +
                "  when e.encounter_type = 261 then '1495edf8-2df2-11e9-b210-d663bd873d93'\n" +
                "  when e.encounter_type = 247 then 'e24209cc-0a1d-11eb-8f2a-bb245320c623'\n" +
                "  when e.encounter_type = 211 then 'b402d094-bff3-4b31-b167-82426b4e3e28'\n" +
                "  when e.encounter_type = 210 then 'b402d094-bff3-4b31-b167-82426b4e3e28'\n" +
                "  when e.encounter_type = 131 then 'dfcbe5d0-1afb-48a0-8f1e-5e5988b11f15'\n" +
                "  when e.encounter_type = 212 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 260 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 86 then 'e24209cc-0a1d-11eb-8f2a-bb245320c623'\n" +
                "  when e.encounter_type = 257 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type = 272 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'\n" +
                "  when e.encounter_type = 276 then '1495edf8-2df2-11e9-b210-d663bd873d93'\n" +
                "  when e.encounter_type = 56 then '5c1ecaf1-ec25-46b7-9b5e-ee7fe44f03cf'\n" +
                "  when e.encounter_type = 224 then 'de1f9d67-b73e-4e1b-90d0-036166fc6995'\n" +
                "  when e.encounter_type = 213 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 275 then '84220f19-9071-4745-9045-3b2f8d3dc128'\n" +
                "  when e.encounter_type = 69 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type =110 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type = 270 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 23 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type = 22 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type = 280 then 'ec2a91e5-444a-4ca0-87f1-f71ddfaf57eb'\n" +
                "  when e.encounter_type = 1 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type = 14 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 14 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 16 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type = 16 then '160fcc03-4ff5-413f-b582-7e944a770bed'\n" +
                "  when e.encounter_type = 2 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 265 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'\n" +
                "  when e.encounter_type = 264 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 32 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'\n" +
                "  when encounter_type = 33 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 242 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 294 then '5021b1a1-e7f6-44b4-ba02-da2f2bcf8718'\n" +
                "  when e.encounter_type = 13 then 'e360f35f-e496-4f01-843b-e2894e278b5b'\n" +
                "  when e.encounter_type = 138 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 252 then '86709cfc-1490-11ec-82a8-0242ac130003'\n" +
                "  when e.encounter_type = 209 then '86709cfc-1490-11ec-82a8-0242ac130003'\n" +
                "  when e.encounter_type = 208 then '86709cfc-1490-11ec-82a8-0242ac130003'\n" +
                "  when e.encounter_type = 234 then 'bcc6da85-72f2-4291-b206-789b8186a021'\n" +
                "  when e.encounter_type = 233 then '415f5136-ca4a-49a8-8db3-f994187c3af6'\n" +
                "  when e.encounter_type = 31 then '2bdada65-4c72-4a48-8730-859890e25cee'\n" +
                "  when e.encounter_type = 127 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 144 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 153 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 181 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 182 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 248 then '7df67b83-1b84-4fe2-b1b7-794b4e9bfcc3'\n" +
                "  when e.encounter_type = 249 then '7dffc392-13e7-11e9-ab14-d663bd873d93'\n" +
                "  when e.encounter_type = 203 then '7df67b83-1b84-4fe2-b1b7-794b4e9bfcc3'\n" +
                "  when e.encounter_type = 186 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 19 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 20 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 26 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 17 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 18 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 279 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type = 157 then '2bdada65-4c72-4a48-8730-859890e25cee'\n" +
                "  when e.encounter_type = 250 then '9bc15e94-2794-11e8-b467-0ed5f89f718b'\n" +
                "  when e.encounter_type = 243 then '975ae894-7660-4224-b777-468c2e710a2a'\n" +
                "  when e.encounter_type = 43 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 132 then '975ae894-7660-4224-b777-468c2e710a2a'\n" +
                "  when e.encounter_type = 115 then '01894f88-dc73-42d4-97a3-0929118403fb'\n" +
                "  when e.encounter_type = 115 then '82169b8d-c945-4c41-be62-433dfd9d6c86'\n" +
                "  when e.encounter_type = 115 then '5feee3f1-aa16-4513-8bd0-5d9b27ef1208'\n" +
                "  when e.encounter_type = 115 then '415f5136-ca4a-49a8-8db3-f994187c3af6'\n" +
                "  when e.encounter_type = 115 then 'bcc6da85-72f2-4291-b206-789b8186a021'\n" +
                "  when e.encounter_type = 253 then 'bfbb5dc2-d3e6-41ea-ad86-101336e3e38f'\n" +
                "  when e.encounter_type = 129 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type = 110 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type = 251 then 'e1406e88-e9a9-11e8-9f32-f2801f1b9fd1'\n" +
                "  when e.encounter_type = 227 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type = 5 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type = 6 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type = 8 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type = 9 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type = 196 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 121 then 'e1406e88-e9a9-11e8-9f32-f2801f1b9fd1'\n" +
                "  when e.encounter_type = 7 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type = 273 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 274 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 237 then '5feee3f1-aa16-4513-8bd0-5d9b27ef1208'\n" +
                "  when e.encounter_type = 235 then '01894f88-dc73-42d4-97a3-0929118403fb'\n" +
                "  when e.encounter_type = 236 then '82169b8d-c945-4c41-be62-433dfd9d6c86'\n" +
                "  when e.encounter_type = 239 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 240 then '7c426cfc-3b47-4481-b55f-89860c21c7de'\n" +
                "  when e.encounter_type = 238 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'\n" +
                "  when e.encounter_type = 268 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 140 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type = 114 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 120 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 168 then '160fcc03-4ff5-413f-b582-7e944a770bed'\n" +
                "  when e.encounter_type = 284 then '162386c8-0464-11ea-9a9f-362b9e155667'\n" +
                "  when e.encounter_type = 285 then '162382b8-0464-11ea-9a9f-362b9e155667'\n" +
                "  when e.encounter_type = 283 then '16238574-0464-11ea-9a9f-362b9e155667'\n" +
                "  when e.encounter_type = 21 then '1495edf8-2df2-11e9-b210-d663bd873d93'\n" +
                "  when e.encounter_type = 220 then '5cf00d9e-09da-11ea-8d71-362b9e155667'\n" +
                "  when e.encounter_type = 214 then '5cf0124e-09da-11ea-8d71-362b9e155667'\n" +
                "  when e.encounter_type = 282 then 'ec2a91e5-444a-4ca0-87f1-f71ddfaf57eb'\n" +
                "  when e.encounter_type = 3 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type = 15 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type = 80 then '160fcc03-4ff5-413f-b582-7e944a770bed'\n" +
                "  when e.encounter_type = 4 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 67 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 162 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 10 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 44 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 125 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 11 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 47 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 46 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 266 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 267 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type = 111 then 'e1406e88-e9a9-11e8-9f32-f2801f1b9fd1'\n" +
                "  when e.encounter_type = 34 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type =133 then '706a8b12-c4ce-40e4-aec3-258b989bf6d3'\n" +
                "  when e.encounter_type = 263 then 'c4a2be28-6673-4c36-b886-ea89b0a42116'\n" +
                "  when e.encounter_type = 263 then '706a8b12-c4ce-40e4-aec3-258b989bf6d3'\n" +
                "  when e.encounter_type = 262 then '291c0828-a216-11e9-a2a3-2a2ae2dbcce4'\n" +
                "  when e.encounter_type = 134 then 'c4a2be28-6673-4c36-b886-ea89b0a42116'\n" +
                "  when e.encounter_type = 117 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 176 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 116 then '2bdada65-4c72-4a48-8730-859890e25cee'\n" +
                "  when e.encounter_type = 119 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 221 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type = 158 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 281 then 'ec2a91e5-444a-4ca0-87f1-f71ddfaf57eb'\n" +
                "  when e.encounter_type = 105 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type = 106 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 163 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type = 137 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  end kenyaemr_encounter_uuid,\n" +
                "et.encounter_type_id,\n" +
                "et.name as encounterName,\n" +
                "e.location_id,\n" +
                "visit_id,\n" +
                "case \n" +
                " when e.encounter_type =110 then 6\n" +
                " when e.encounter_type = 270 then 8\n" +
                " when e.encounter_type = 23 then 303\n" +
                " when e.encounter_type = 22 then 303\n" +
                " when e.encounter_type = 280 then 302\n" +
                " when e.encounter_type = 1 then 7\n" +
                " when e.encounter_type = 14 then 21\n" +
                " when e.encounter_type = 14 then 8\n" +
                " when e.encounter_type = 16 then 6\n" +
                " when e.encounter_type = 16 then 252\n" +
                " when e.encounter_type = 2 then 8\n" +
                " when e.encounter_type = 265 then 14\n" +
                " when e.encounter_type = 264 then 15\n" +
                " when e.encounter_type = 32 then 14\n" +
                " when e.encounter_type = 33 then 15\n" +
                " when e.encounter_type = 242 then 21\n" +
                " when e.encounter_type = 294 then 73\n" +
                " when e.encounter_type = 13 then 300\n" +
                " when e.encounter_type = 138 then 8\n" +
                " when e.encounter_type = 252 then 243\n" +
                " when e.encounter_type = 209 then 243\n" +
                " when e.encounter_type = 208 then 243\n" +
                " when e.encounter_type = 234 then 10\n" +
                " when e.encounter_type = 233 then 9\n" +
                " when e.encounter_type = 31 then 2\n" +
                " when e.encounter_type = 127 then 8\n" +
                " when e.encounter_type = 144 then 8\n" +
                " when e.encounter_type = 153 then 8\n" +
                " when e.encounter_type = 181 then 21\n" +
                " when e.encounter_type = 182 then 21\n" +
                " when e.encounter_type = 248 then 24\n" +
                " when e.encounter_type = 249 then 29\n" +
                " when e.encounter_type = 203 then 24\n" +
                " when e.encounter_type = 186 then 21\n" +
                " when e.encounter_type = 19 then 21\n" +
                " when e.encounter_type = 20 then 21\n" +
                " when e.encounter_type = 26 then 21\n" +
                " when e.encounter_type = 17 then 21\n" +
                " when e.encounter_type = 18 then 21\n" +
                " when e.encounter_type = 279 then 303\n" +
                " when e.encounter_type = 157 then 2\n" +
                " when e.encounter_type = 250 then 28\n" +
                " when e.encounter_type = 243 then 22\n" +
                " when e.encounter_type = 43 then 8\n" +
                " when e.encounter_type = 132 then 22\n" +
                " when e.encounter_type = 115 then 11\n" +
                " when e.encounter_type = 115 then 12\n" +
                " when e.encounter_type = 115 then 13\n" +
                "  when e.encounter_type = 115 then 9\n" +
                " when e.encounter_type = 115 then 10\n" +
                " when e.encounter_type = 253 then 306\n" +
                " when e.encounter_type = 129 then 303\n" +
                " when e.encounter_type = 110 then 6\n" +
                " when e.encounter_type = 251 then 30\n" +
                " when e.encounter_type = 227 then 4\n" +
                " when e.encounter_type = 5 then 4\n" +
                " when e.encounter_type = 6 then 4\n" +
                " when e.encounter_type = 8 then 4\n" +
                " when e.encounter_type = 9 then 4\n" +
                " when e.encounter_type = 196 then 15\n" +
                " when e.encounter_type = 121 then 30\n" +
                " when e.encounter_type = 7 then 4\n" +
                " when e.encounter_type = 273 then 15\n" +
                " when e.encounter_type = 274 then 15\n" +
                " when e.encounter_type = 237 then 13\n" +
                " when e.encounter_type = 235 then 11\n" +
                " when e.encounter_type = 236 then 12\n" +
                " when e.encounter_type = 239 then 15\n" +
                " when e.encounter_type = 240 then 16\n" +
                " when e.encounter_type = 238 then 14\n" +
                " when e.encounter_type = 268 then 15\n" +
                " when e.encounter_type = 140 then 303\n" +
                " when e.encounter_type = 114 then 8\n" +
                " when e.encounter_type = 120 then 21\n" +
                " when e.encounter_type = 168 then 252\n" +
                " when e.encounter_type = 284 then 36\n" +
                " when e.encounter_type = 285 then 35\n" +
                " when e.encounter_type = 283 then 34\n" +
                " when e.encounter_type = 21 then 31\n" +
                " when e.encounter_type = 220 then 33\n" +
                " when e.encounter_type = 214 then 32\n" +
                " when e.encounter_type = 282 then 302\n" +
                " when e.encounter_type = 3 then 7\n" +
                " when e.encounter_type = 15 then 21\n" +
                " when e.encounter_type = 80 then 252\n" +
                " when e.encounter_type = 4 then 8\n" +
                " when e.encounter_type = 67 then 8\n" +
                " when e.encounter_type = 162 then 8\n" +
                " when e.encounter_type = 10 then 15\n" +
                " when e.encounter_type = 44 then 15\n" +
                " when e.encounter_type = 125 then 15\n" +
                " when e.encounter_type = 11 then 15\n" +
                " when e.encounter_type = 47 then 15\n" +
                " when e.encounter_type = 46 then 15\n" +
                " when e.encounter_type = 266 then 15\n" +
                " when e.encounter_type = 267 then 15\n" +
                " when e.encounter_type = 111 then 30\n" +
                " when e.encounter_type = 34 then 15\n" +
                " when e.encounter_type = 133 then 51\n" +
                " when e.encounter_type = 263 then 38\n" +
                " when e.encounter_type = 263 then 51\n" +
                " when e.encounter_type = 262 then 50\n" +
                " when e.encounter_type = 134 then 38\n" +
                " when e.encounter_type = 117 then 8\n" +
                " when e.encounter_type = 176 then 8\n" +
                " when e.encounter_type = 116 then 2\n" +
                " when e.encounter_type = 119 then 8\n" +
                " when e.encounter_type = 221 then 6\n" +
                " when e.encounter_type = 158 then 8\n" +
                " when e.encounter_type = 281 then 302\n" +
                " when e.encounter_type = 105 then 7\n" +
                " when e.encounter_type = 106 then 8\n" +
                " when e.encounter_type = 163 then 8\n" +
                " when e.encounter_type = 137 then 7\n" +
                " when e.encounter_type = 69 then 8\n" +
                " when e.encounter_type = 275 then 62\n" +
                " when e.encounter_type = 213 then 8\n" +
                " when e.encounter_type = 224 then 5\n" +
                " when e.encounter_type = 56 then 270\n" +
                " when e.encounter_type = 276 then 31\n" +
                " when e.encounter_type = 272 then 14\n" +
                " when e.encounter_type = 257 then 7\n" +
                " when e.encounter_type = 86 then 247\n" +
                " when e.encounter_type = 260 then 8\n" +
                " when e.encounter_type = 212 then 8\n" +
                " when e.encounter_type = 131 then 277\n" +
                " when e.encounter_type = 211 then 278\n" +
                " when e.encounter_type = 210 then 278\n" +
                " when e.encounter_type = 147 then 247\n" +
                " when e.encounter_type = 261 then 31\n" +
                " when e.encounter_type = 70 then 255\n" +
                " when e.encounter_type = 81 then 31\n" +
                " when e.encounter_type = 126 then 7\n" +
                " when e.encounter_type = 96 then 278\n" +
                " when e.encounter_type = 287 then 8\n" +
                " when e.encounter_type = 146 then 247\n" +
                " when e.encounter_type = 55 then 278\n" +
                "  end kenyaem_encounter_id,\n" +
                " e.creator,\n" +
                " encounter_datetime as obs_datetime,\n" +
                " e.encounter_type,\n" +
                " form_id,\n" +
                "  e.voided\n" +
                " \n" +
                " from amrs.encounter e\n" +
                " inner join amrs.encounter_type et on  e.encounter_type=et.encounter_type_id\n" +
                " where e.voided =0\n" +
                " order by e.encounter_id asc " +
                "limit 10;";


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
            //String amrsUUID = rs.getString("person_id");
            String encounterId = rs.getString("encounter_id");
            String encounterTypeId = rs.getString("encounter_type_id");
            String encounterName = rs.getString("encounterName");
            String locationId = rs.getString("location_id");
            String visitId = rs.getString("visit_id");
            String kenyaemrEncounterTypeId = rs.getString("kenyaem_encounter_id");
            String kenyaemrEncounterUuid = rs.getString("kenyaemr_encounter_uuid");

            List<AMRSEncounters> amrsEncountersList = amrsEncounterService.findByPatientIdAndEncounterId(patientId,encounterId);
            if(amrsEncountersList.isEmpty()){

                AMRSEncounters ae = new AMRSEncounters();
                ae.setPatientId(patientId);
                ae.setEncounterId(encounterId);
                ae.setEncounterTypeId(encounterTypeId);
                ae.setEncounterName(encounterName);
                ae.setLocationId(locationId);
                ae.setVisitId(visitId);
                ae.setKenyaemrEncounterTypeId(kenyaemrEncounterTypeId);
                ae.setKenyaemrEncounterTypeUuid(kenyaemrEncounterUuid);
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


    public static void visits (String server, String username, String password, String locations, String parentUUID, AMRSVisitService amrsVisitService, AMRSObsService amrsObsService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql="";
        List<AMRSVisits> amrsVisitsList = amrsVisitService.findFirstByOrderByIdDesc();
            if(amrsVisitsList.size()>0){
                sql = "select v.visit_id,\n" +
                        "       e.patient_id,\n" +
                        "\t     visit_type_id,\n" +
                        "       date_started,\n" +
                        "       e.location_id,\n" +
                        "\t     date_stopped,\n" +
                        "       encounter_type,\n" +
                        "       v.voided \n" +
                        "              from amrs.visit v\n" +
                        "              inner join  amrs.encounter e on e.visit_id = v.visit_id\n" +
                        "              inner join amrs.location l on l.location_id=e.location_id\n" +
                        "              where l.uuid in (" + locations + ") and v.visit_id>"+ amrsVisitsList.get(0).getVisitId() +"\n" +
                        "              and v.voided=0\n" +
                        "              group by  v.visit_id order by v.visit_id asc ";
            }else {

                sql = "select v.visit_id,\n" +
                        "       e.patient_id,\n" +
                        "\t     visit_type_id,\n" +
                        "       date_started,\n" +
                        "       e.location_id,\n" +
                        "\t     date_stopped,\n" +
                        "       encounter_type,\n" +
                        "       v.voided \n" +
                        "              from amrs.visit v\n" +
                        "              inner join  amrs.encounter e on e.visit_id = v.visit_id\n" +
                        "              inner join amrs.location l on l.location_id=e.location_id\n" +
                        "              where l.uuid in (" + locations + ")\n" +
                        "              and v.voided=0 \n" +
                        "              group by  v.visit_id order by v.visit_id asc ";
            }

        System.out.println("locations " + locations + " parentUUID " + parentUUID);
        System.out.println("locations " +sql);
        Connection con = DriverManager.getConnection(server, username, password);
        int x = 0;
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        x = rs.getRow();
        rs.beforeFirst();
        while (rs.next()) {

            String visitId = rs.getString("visit_id");
            String patientId = rs.getString("patient_id");
            String visitTypeId = rs.getString("visit_type_id");
            String dateStarted = rs.getString("date_started");
            String dateStopped = rs.getString("date_stopped");
            String locationId = rs.getString("location_id");
            String voided= rs.getString("voided");

            System.out.println("Visit_Id "+visitId);

            List<AMRSVisits> av = amrsVisitService.findByVisitID(visitId);

            if(av.size()==0) {
                List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(patientId);
               String kenyaemrpid ="Client Not Migrated";
                AMRSVisits avv = new AMRSVisits();
                if(amrsPatients.size()>0){
                    kenyaemrpid = amrsPatients.get(0).getKenyaemrpatientUUID();
                    avv.setResponseCode("400");
                }

                avv.setVisitId(visitId);
                avv.setDateStarted(dateStarted);
                avv.setPatientId(patientId);
                avv.setVisitType(visitTypeId);
                avv.setKenyaemrPatientUuid(kenyaemrpid);
                avv.setDateStop(dateStopped);
                avv.setVoided(voided);
                avv.setLocationId(locationId);
                amrsVisitService.save(avv);
            }

            VisitsPayload.visits(amrsVisitService, amrsPatientServices, auth, url);


        }
    }

    public static void order (String server, String username, String password, String locations, String parentUUID, AMRSOrderService amrsOrderService, AMRSPatientServices amrsPatientServices, AMRSEncounterMappingService amrsEncounterMappingService, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql ="SELECT o.*, et.encounter_type_id, UUID() AS migration_uuid, NULL AS kenyaemr_order_uuid,\n" +
                " NULL AS kenyaemr_order_id FROM amrs.orders o \n" +
                " inner join amrs.encounter e on (e.encounter_id = o.encounter_id)\n" +
                " inner join amrs.encounter_type et on (et.encounter_type_id = e.encounter_type)\n" +
                " where  o.order_reason order by o.date_created ASC limit 100";
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
            String orderId = rs.getString("order_id");
            String orderTypeId = rs.getString("order_type_id");
            String orderer = rs.getString("orderer");
            String encounterId = rs.getString("encounter_id");
            String instructions = rs.getString("instructions");
            String conceptId = rs.getString("concept_id");
            String amrsOrderUuid = rs.getString("uuid");
            String amrsEncounterTypeId = rs.getString("encounter_type_id");
            String justification = rs.getString("order_reason");
            String careSetting = rs.getString("care_setting");
            String order_reason_non_coded = rs.getString("order_reason_non_coded");
            String urgency = rs.getString("urgency");
            String orderNumber = rs.getString("order_number");
            String orderAction = rs.getString("order_action");

            List<AMRSOrders> amrsOrders = amrsOrderService.findByUuid(amrsOrderUuid);
            if(amrsOrders.isEmpty()){
                String kenyaemr_uuid="";
                AMRSOrders ao = new AMRSOrders();
                List<AMRSEncountersMapping> ac = amrsEncounterMappingService.getByAmrsID(amrsEncounterTypeId);
                String kenyaemr_encounter_id;
                if(!ac.isEmpty()) {
                    kenyaemr_encounter_id = ac.get(0).getKenyaemrEncounterTypeUuid();
                }else{
                    kenyaemr_encounter_id="";

                }
                    ao.setConceptId(Integer.valueOf(conceptId));
                    ao.setPatientId(patientId);
                    ao.setOrderId(Integer.valueOf(orderId));
                    ao.setOrderer(orderer);
                    ao.setOrderTypeId(Integer.valueOf(orderTypeId));
                    ao.setEncounterId(Integer.valueOf(encounterId));
                    ao.setKenyaemrOrderUuid(kenyaemr_encounter_id);
                    ao.setInstructions(instructions);
                    ao.setOrderReasonNonCoded(order_reason_non_coded);
                    ao.setUrgency(urgency);
                    ao.setOrderReason(justification);
                    ao.setOrderNumber(orderNumber);
                    ao.setOrderAction(orderAction);
                    ao.setCareSetting(careSetting);
                    List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(patientId);
                    String kenyaemr_patient_uuid = "";
                    if (!amrsPatients.isEmpty()) {
                        kenyaemr_patient_uuid = amrsPatients.get(0).getKenyaemrpatientUUID();
                    } else {
                        kenyaemr_patient_uuid = "Not Found"; // add logic for missing patientkenyaemr_patient_uuid
                    }
                    String justificationcode ="";
                    if(!justification.isEmpty()){
                        List<AMRSConceptMapper> amrsConceptMapper = amrsConceptMappingService.findByAmrsConceptID(justification);
                        if(!amrsConceptMapper.isEmpty()){
                            justificationcode = amrsConceptMapper.get(0).getKenyaemrConceptUUID();

                        }
                    }

                    System.out.println("Patient " + kenyaemr_patient_uuid + " concept " + conceptId + " kenyaemr_concept_id " + kenyaemr_uuid +" justification" + justification +"Kenyaemr justicafiation "+ justificationcode);



               amrsOrderService.save(ao);

            }
            // orders
            // OrdersPayload.orders(amrsOrderService, amrsPatientServices, auth, url);
        }
    }
  public static void triage (String server, String username, String password, String locations, String parentUUID, AMRSTriageService amrsTriageService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
    String sql = "select\n" +
      "\tl.uuid as location_uuid,\n" +
      "\to.person_id,\n" +
      "\te.encounter_id,\n" +
      "\te.encounter_datetime,\n" +
      "\te.encounter_type,\n" +
      "\to.concept_id,\n" +
      "\tcn.name as concept_name,\n" +
      "\to.obs_datetime,\n" +
      "\tCOALESCE(o.value_coded, o.value_datetime, o.value_numeric, o.value_text) as value,\n" +
      "\tcd.name as value_type,\n" +
      "\tc.datatype_id,\n" +
      "\tet.name as encounterName,\n" +
      "\t\"Triage\" as Category\n" +
      "from\n" +
      "\tamrs.obs o\n" +
      "inner join amrs.encounter e on\n" +
      "\t(o.encounter_id = e.encounter_id)\n" +
      "inner join amrs.person p on\n" +
      "\tp.person_id = o.person_id\n" +
      "inner join amrs.encounter_type et on\n" +
      "\tet.encounter_type_id = e.encounter_type\n" +
      "inner join amrs.location l \n" +
      "\ton\n" +
      "\tl.location_id = o.location_id\n" +
      "inner join amrs.concept_name cn on\n" +
      "\tcn.concept_id = o.concept_id\n" +
      "inner join amrs.concept c on\n" +
      "\tc.concept_id = o.concept_id\n" +
      "inner join amrs.concept_datatype cd on\n" +
      "\tcd.concept_datatype_id = c.datatype_id\n" +
      "where\n" +
      "\te.encounter_type in (110)\n" +
      "\tand o.concept_id in (5088, 5085, 5086, 5087, 5092, 5090, 5089, 980, 1342)\n" +
      "\tand e.location_id in (339)\n" +
      "\tand e.voided = 0\n" +
      "\tand p.voided = 0\n" +
      "\tand cd.name <> 'N/A'\n" +
      "limit 10;";
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
      String conceptId = rs.getString("concept_id");
      String encounterID = rs.getString("encounter_id");
      String locationUuid = rs.getString("location_uuid");
      String encounterDatetime = rs.getString("encounter_datetime");
      String encounterType = rs.getString("encounter_type");
      String conceptName = rs.getString("concept_name");
      String obsDatetime = rs.getString("obs_datetime");
      String value = rs.getString("value");
      String valueType = rs.getString("value_type");
      String datatypeId = rs.getString("datatype_id");
      String encounterName = rs.getString("encounterName");
      String category = rs.getString("Category");


      List<AMRSTriage> amrsTriageList = amrsTriageService.findByPatientIdAndEncounterIdAndConceptId(patientId,encounterID,conceptId);
      if(amrsTriageList.isEmpty()){
        AMRSTriage at = new AMRSTriage();
        at.setPatientId(patientId);
        at.setConceptId(conceptId);
        at.setLocationUuid(locationUuid);
        at.setEncounterDateTime(encounterDatetime);
        at.setEncounterType(encounterType);
        at.setConceptName(conceptName);
        at.setObsDateTime(obsDatetime);
        at.setConceptValue(value);
        at.setValueDataType(valueType);
        at.setDataTypeId(datatypeId);
        at.setEncounterName(encounterName);
        at.setCategory(category);
        at.setKenyaemrConceptUuid(String.valueOf(amrsConceptMappingService.findByAmrsConceptID(conceptId)));
       if(datatypeId.equals("1")||datatypeId.equals("2")){
         at.setKenyaemrValue(value);
       }else {
         at.setKenyaemrValue(String.valueOf(amrsConceptMappingService.findByAmrsConceptID(value)));
       }

       amrsTriageService.save(at);
       // CareOpenMRSPayload.triage(amrsTriageService, parentUUID, locations, auth, url);


      }

      System.out.println("Patient_id" + patientId);
    }
  }
    public static void encounterMappings (String server, String username, String password, String locations, String parentUUID, AMRSEncounterMappingService amrsEncounterMappingService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql="select \n" +
                " encounter_type_id,\n" +
                " case \n" +
                " when e.encounter_type_id =110 then 6\n" +
                " when e.encounter_type_id = 270 then 8\n" +
                " when e.encounter_type_id = 23 then 303\n" +
                " when e.encounter_type_id = 22 then 303\n" +
                " when e.encounter_type_id = 280 then 302\n" +
                " when e.encounter_type_id = 1 then 7\n" +
                " when e.encounter_type_id = 14 then 21\n" +
                " when e.encounter_type_id = 14 then 8\n" +
                " when e.encounter_type_id = 16 then 6\n" +
                " when e.encounter_type_id = 16 then 252\n" +
                " when e.encounter_type_id = 2 then 8\n" +
                " when e.encounter_type_id = 265 then 14\n" +
                " when e.encounter_type_id = 264 then 15\n" +
                " when e.encounter_type_id = 32 then 14\n" +
                " when e.encounter_type_id = 33 then 15\n" +
                " when e.encounter_type_id = 242 then 21\n" +
                " when e.encounter_type_id = 294 then 73\n" +
                " when e.encounter_type_id = 13 then 300\n" +
                " when e.encounter_type_id = 138 then 8\n" +
                " when e.encounter_type_id = 252 then 243\n" +
                " when e.encounter_type_id = 209 then 243\n" +
                " when e.encounter_type_id = 208 then 243\n" +
                " when e.encounter_type_id = 234 then 10\n" +
                " when e.encounter_type_id = 233 then 9\n" +
                " when e.encounter_type_id = 31 then 2\n" +
                " when e.encounter_type_id = 127 then 8\n" +
                " when e.encounter_type_id = 144 then 8\n" +
                " when e.encounter_type_id = 153 then 8\n" +
                " when e.encounter_type_id = 181 then 21\n" +
                " when e.encounter_type_id = 182 then 21\n" +
                " when e.encounter_type_id = 248 then 24\n" +
                " when e.encounter_type_id = 249 then 29\n" +
                " when e.encounter_type_id = 203 then 24\n" +
                " when e.encounter_type_id = 186 then 21\n" +
                " when e.encounter_type_id = 19 then 21\n" +
                " when e.encounter_type_id = 20 then 21\n" +
                " when e.encounter_type_id = 26 then 21\n" +
                " when e.encounter_type_id = 17 then 21\n" +
                " when e.encounter_type_id = 18 then 21\n" +
                " when e.encounter_type_id = 279 then 303\n" +
                " when e.encounter_type_id = 157 then 2\n" +
                " when e.encounter_type_id = 250 then 28\n" +
                " when e.encounter_type_id = 243 then 22\n" +
                " when e.encounter_type_id = 43 then 8\n" +
                " when e.encounter_type_id = 132 then 22\n" +
                " when e.encounter_type_id = 115 then 11\n" +
                " when e.encounter_type_id = 115 then 12\n" +
                " when e.encounter_type_id = 115 then 13\n" +
                " when e.encounter_type_id = 115 then 9\n" +
                " when e.encounter_type_id = 115 then 10\n" +
                " when e.encounter_type_id = 253 then 306\n" +
                " when e.encounter_type_id = 129 then 303\n" +
                " when e.encounter_type_id = 110 then 6\n" +
                " when e.encounter_type_id = 251 then 30\n" +
                " when e.encounter_type_id = 227 then 4\n" +
                " when e.encounter_type_id = 5 then 4\n" +
                " when e.encounter_type_id = 6 then 4\n" +
                " when e.encounter_type_id = 8 then 4\n" +
                " when e.encounter_type_id = 9 then 4\n" +
                " when e.encounter_type_id = 196 then 15\n" +
                " when e.encounter_type_id = 121 then 30\n" +
                " when e.encounter_type_id = 7 then 4\n" +
                " when e.encounter_type_id = 273 then 15\n" +
                " when e.encounter_type_id = 274 then 15\n" +
                " when e.encounter_type_id = 237 then 13\n" +
                " when e.encounter_type_id = 235 then 11\n" +
                " when e.encounter_type_id = 236 then 12\n" +
                " when e.encounter_type_id = 239 then 15\n" +
                " when e.encounter_type_id = 240 then 16\n" +
                " when e.encounter_type_id = 238 then 14\n" +
                " when e.encounter_type_id = 268 then 15\n" +
                " when e.encounter_type_id = 140 then 303\n" +
                " when e.encounter_type_id = 114 then 8\n" +
                " when e.encounter_type_id = 120 then 21\n" +
                " when e.encounter_type_id = 168 then 252\n" +
                " when e.encounter_type_id = 284 then 36\n" +
                " when e.encounter_type_id = 285 then 35\n" +
                " when e.encounter_type_id = 283 then 34\n" +
                " when e.encounter_type_id = 21 then 31\n" +
                " when e.encounter_type_id = 220 then 33\n" +
                " when e.encounter_type_id = 214 then 32\n" +
                " when e.encounter_type_id = 282 then 302\n" +
                " when e.encounter_type_id = 3 then 7\n" +
                " when e.encounter_type_id = 15 then 21\n" +
                " when e.encounter_type_id = 80 then 252\n" +
                " when e.encounter_type_id = 4 then 8\n" +
                " when e.encounter_type_id = 67 then 8\n" +
                " when e.encounter_type_id = 162 then 8\n" +
                " when e.encounter_type_id = 10 then 15\n" +
                " when e.encounter_type_id = 44 then 15\n" +
                " when e.encounter_type_id = 125 then 15\n" +
                " when e.encounter_type_id = 11 then 15\n" +
                " when e.encounter_type_id = 47 then 15\n" +
                " when e.encounter_type_id = 46 then 15\n" +
                " when e.encounter_type_id = 266 then 15\n" +
                " when e.encounter_type_id = 267 then 15\n" +
                " when e.encounter_type_id = 111 then 30\n" +
                " when e.encounter_type_id = 34 then 15\n" +
                " when e.encounter_type_id = 133 then 51\n" +
                " when e.encounter_type_id = 263 then 38\n" +
                " when e.encounter_type_id = 263 then 51\n" +
                " when e.encounter_type_id = 262 then 50\n" +
                " when e.encounter_type_id = 134 then 38\n" +
                " when e.encounter_type_id = 117 then 8\n" +
                " when e.encounter_type_id = 176 then 8\n" +
                " when e.encounter_type_id = 116 then 2\n" +
                " when e.encounter_type_id = 119 then 8\n" +
                " when e.encounter_type_id = 221 then 6\n" +
                " when e.encounter_type_id = 158 then 8\n" +
                " when e.encounter_type_id = 281 then 302\n" +
                " when e.encounter_type_id = 105 then 7\n" +
                " when e.encounter_type_id = 106 then 8\n" +
                " when e.encounter_type_id = 163 then 8\n" +
                " when e.encounter_type_id = 137 then 7\n" +
                "  end kenyaem_encounter_id\n" +
                " ,\n" +
                "  case \n" +
                "  when e.encounter_type_id =110 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type_id = 270 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 23 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type_id = 22 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type_id = 280 then 'ec2a91e5-444a-4ca0-87f1-f71ddfaf57eb'\n" +
                "  when e.encounter_type_id = 1 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type_id = 14 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 14 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 16 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type_id = 16 then '160fcc03-4ff5-413f-b582-7e944a770bed'\n" +
                "  when e.encounter_type_id = 2 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 265 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'\n" +
                "  when e.encounter_type_id = 264 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 32 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'\n" +
                "  when encounter_type_id = 33 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 242 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 294 then '5021b1a1-e7f6-44b4-ba02-da2f2bcf8718'\n" +
                "  when e.encounter_type_id = 13 then 'e360f35f-e496-4f01-843b-e2894e278b5b'\n" +
                "  when e.encounter_type_id = 138 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 252 then '86709cfc-1490-11ec-82a8-0242ac130003'\n" +
                "  when e.encounter_type_id = 209 then '86709cfc-1490-11ec-82a8-0242ac130003'\n" +
                "  when e.encounter_type_id = 208 then '86709cfc-1490-11ec-82a8-0242ac130003'\n" +
                "  when e.encounter_type_id = 234 then 'bcc6da85-72f2-4291-b206-789b8186a021'\n" +
                "  when e.encounter_type_id = 233 then '415f5136-ca4a-49a8-8db3-f994187c3af6'\n" +
                "  when e.encounter_type_id = 31 then '2bdada65-4c72-4a48-8730-859890e25cee'\n" +
                "  when e.encounter_type_id = 127 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 144 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 153 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 181 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 182 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 248 then '7df67b83-1b84-4fe2-b1b7-794b4e9bfcc3'\n" +
                "  when e.encounter_type_id = 249 then '7dffc392-13e7-11e9-ab14-d663bd873d93'\n" +
                "  when e.encounter_type_id = 203 then '7df67b83-1b84-4fe2-b1b7-794b4e9bfcc3'\n" +
                "  when e.encounter_type_id = 186 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 19 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 20 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 26 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 17 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 18 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 279 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type_id = 157 then '2bdada65-4c72-4a48-8730-859890e25cee'\n" +
                "  when e.encounter_type_id = 250 then '9bc15e94-2794-11e8-b467-0ed5f89f718b'\n" +
                "  when e.encounter_type_id = 243 then '975ae894-7660-4224-b777-468c2e710a2a'\n" +
                "  when e.encounter_type_id = 43 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 132 then '975ae894-7660-4224-b777-468c2e710a2a'\n" +
                "  when e.encounter_type_id = 115 then '01894f88-dc73-42d4-97a3-0929118403fb'\n" +
                "  when e.encounter_type_id = 115 then '82169b8d-c945-4c41-be62-433dfd9d6c86'\n" +
                "  when e.encounter_type_id = 115 then '5feee3f1-aa16-4513-8bd0-5d9b27ef1208'\n" +
                "  when e.encounter_type_id = 115 then '415f5136-ca4a-49a8-8db3-f994187c3af6'\n" +
                "  when e.encounter_type_id = 115 then 'bcc6da85-72f2-4291-b206-789b8186a021'\n" +
                "  when e.encounter_type_id = 253 then 'bfbb5dc2-d3e6-41ea-ad86-101336e3e38f'\n" +
                "  when e.encounter_type_id = 129 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type_id = 110 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type_id = 251 then 'e1406e88-e9a9-11e8-9f32-f2801f1b9fd1'\n" +
                "  when e.encounter_type_id = 227 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type_id = 5 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type_id = 6 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type_id = 8 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type_id = 9 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type_id = 196 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 121 then 'e1406e88-e9a9-11e8-9f32-f2801f1b9fd1'\n" +
                "  when e.encounter_type_id = 7 then '17a381d1-7e29-406a-b782-aa903b963c28'\n" +
                "  when e.encounter_type_id = 273 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 274 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 237 then '5feee3f1-aa16-4513-8bd0-5d9b27ef1208'\n" +
                "  when e.encounter_type_id = 235 then '01894f88-dc73-42d4-97a3-0929118403fb'\n" +
                "  when e.encounter_type_id = 236 then '82169b8d-c945-4c41-be62-433dfd9d6c86'\n" +
                "  when e.encounter_type_id = 239 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 240 then '7c426cfc-3b47-4481-b55f-89860c21c7de'\n" +
                "  when e.encounter_type_id = 238 then '3ee036d8-7c13-4393-b5d6-036f2fe45126'\n" +
                "  when e.encounter_type_id = 268 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 140 then '54df6991-13de-4efc-a1a9-2d5ac1b72ff8'\n" +
                "  when e.encounter_type_id = 114 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 120 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 168 then '160fcc03-4ff5-413f-b582-7e944a770bed'\n" +
                "  when e.encounter_type_id = 284 then '162386c8-0464-11ea-9a9f-362b9e155667'\n" +
                "  when e.encounter_type_id = 285 then '162382b8-0464-11ea-9a9f-362b9e155667'\n" +
                "  when e.encounter_type_id = 283 then '16238574-0464-11ea-9a9f-362b9e155667'\n" +
                "  when e.encounter_type_id = 21 then '1495edf8-2df2-11e9-b210-d663bd873d93'\n" +
                "  when e.encounter_type_id = 220 then '5cf00d9e-09da-11ea-8d71-362b9e155667'\n" +
                "  when e.encounter_type_id = 214 then '5cf0124e-09da-11ea-8d71-362b9e155667'\n" +
                "  when e.encounter_type_id = 282 then 'ec2a91e5-444a-4ca0-87f1-f71ddfaf57eb'\n" +
                "  when e.encounter_type_id = 3 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type_id = 15 then 'e87aa2ad-6886-422e-9dfd-064e3bfe3aad'\n" +
                "  when e.encounter_type_id = 80 then '160fcc03-4ff5-413f-b582-7e944a770bed'\n" +
                "  when e.encounter_type_id = 4 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 67 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 162 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 10 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 44 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 125 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 11 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 47 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 46 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 266 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 267 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id = 111 then 'e1406e88-e9a9-11e8-9f32-f2801f1b9fd1'\n" +
                "  when e.encounter_type_id = 34 then 'c6d09e05-1f25-4164-8860-9f32c5a02df0'\n" +
                "  when e.encounter_type_id =133 then '706a8b12-c4ce-40e4-aec3-258b989bf6d3'\n" +
                "  when e.encounter_type_id = 263 then 'c4a2be28-6673-4c36-b886-ea89b0a42116'\n" +
                "  when e.encounter_type_id = 263 then '706a8b12-c4ce-40e4-aec3-258b989bf6d3'\n" +
                "  when e.encounter_type_id = 262 then '291c0828-a216-11e9-a2a3-2a2ae2dbcce4'\n" +
                "  when e.encounter_type_id = 134 then 'c4a2be28-6673-4c36-b886-ea89b0a42116'\n" +
                "  when e.encounter_type_id = 117 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 176 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 116 then '2bdada65-4c72-4a48-8730-859890e25cee'\n" +
                "  when e.encounter_type_id = 119 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 221 then 'd1059fb9-a079-4feb-a749-eedd709ae542'\n" +
                "  when e.encounter_type_id = 158 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 281 then 'ec2a91e5-444a-4ca0-87f1-f71ddfaf57eb'\n" +
                "  when e.encounter_type_id = 105 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  when e.encounter_type_id = 106 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 163 then 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "  when e.encounter_type_id = 137 then 'de78a6be-bfc5-4634-adc3-5f1a280455cc'\n" +
                "  end kenyaem_encounter_uuid\n" +
                " from  amrs.encounter_type e\n" +
                " -- order by e.encounter_id desc";

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
            String encounterTypeId = rs.getString("encounter_type_id");
            String kenyaemEncounterId = rs.getString("kenyaem_encounter_id");
            String kenyaemEncounterUuid = rs.getString("kenyaem_encounter_uuid");

            List<AMRSEncountersMapping> amList = amrsEncounterMappingService.getByAmrsID(encounterTypeId);
            if(amList.size()>0){
                AMRSEncountersMapping am = amList.get(0);
                am.setAmrsEncounterTypeId(encounterTypeId);
                am.setKenyaemrEncounterTypeId(kenyaemEncounterId);
                am.setKenyaemrEncounterTypeUuid(kenyaemEncounterUuid);
                amrsEncounterMappingService.save(am);
            }else {

                AMRSEncountersMapping am = new AMRSEncountersMapping();
                am.setAmrsEncounterTypeId(encounterTypeId);
                am.setKenyaemrEncounterTypeId(kenyaemEncounterId);
                am.setKenyaemrEncounterTypeUuid(kenyaemEncounterUuid);
                amrsEncounterMappingService.save(am);
            }

        }


    }


    public static void hivenrollment (String server, String username, String password, String locations, String parentUUID, AMRSHIVEnrollmentService amrsHIVEnrollmentService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql = "select\n" +
                "\to.person_id,\n" +
                "\te.encounter_id,\n" +
                "\te.encounter_datetime,\n" +
                "\te.encounter_type,\n" +
                "\to.concept_id,\n" +
                "    cn.name as conceptName,\n" +
                "\to.obs_datetime,\n" +
                "\tCOALESCE(o.value_coded, o.value_datetime, o.value_numeric, o.value_text) as value,\n" +
                "\tcd.name as value_type,\n" +
                "\tc.datatype_id,\n" +
                "\tet.name as encounterName,\n" +
                "\t\"HIV Enrollment\" as Category\n" +
                "from\n" +
                "\tamrs.obs o\n" +
                "inner join amrs.encounter e on\n" +
                "\t(o.encounter_id = e.encounter_id)\n" +
                "inner join amrs.encounter_type et on\n" +
                "\tet.encounter_type_id = e.encounter_type\n" +
                "inner join amrs.concept c on\n" +
                "\tc.concept_id = o.concept_id\n" +
                "    inner join amrs.concept_name cn on\n" +
                "\to.concept_id = cn.concept_id\n" +
                "inner join amrs.concept_datatype cd on\n" +
                "\tcd.concept_datatype_id = c.datatype_id\n" +
                "where \n" +
                "\te.encounter_type in(1,3,24,32,105,137,135,136,265,266) and \n" +
                "    o.concept_id in (9203,8287,1224,1633)\n" +
                "\tand e.location_id in (2)\n" +
                "\tand e.voided = 0\n" +
                "\tand cd.name <> 'N/A' \n" +
                "order by\n" +
                "\to.person_id asc \n" +
                "    limit 10;";
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
            String conceptId = rs.getString("concept_id");
            String encounterID = rs.getString("encounter_id");
            String locationUuid = rs.getString("location_uuid");
            String encounterDatetime = rs.getString("encounter_datetime");
            String encounterType = rs.getString("encounter_type");
            String conceptName = rs.getString("concept_name");
            String obsDatetime = rs.getString("obs_datetime");
            String value = rs.getString("value");
            String valueType = rs.getString("value_type");
            String datatypeId = rs.getString("datatype_id");
            String encounterName = rs.getString("encounterName");
            String category = rs.getString("Category");


            List<AMRSHIVEnrollment> amrshivEnrollmentList = amrsHIVEnrollmentService.findByPatientIdAndEncounterIdAndConceptId(patientId,encounterID,conceptId);
            if(amrshivEnrollmentList.isEmpty()){
                AMRSHIVEnrollment ahe = new AMRSHIVEnrollment();
                ahe.setPatientId(patientId);
                ahe.setConceptId(conceptId);
                ahe.setLocationUuid(locationUuid);
                ahe.setEncounterDateTime(encounterDatetime);
                ahe.setEncounterType(encounterType);
                ahe.setConceptName(conceptName);
                ahe.setObsDateTime(obsDatetime);
                ahe.setConceptValue(value);
                ahe.setValueDataType(valueType);
                ahe.setDataTypeId(datatypeId);
                ahe.setEncounterName(encounterName);
                ahe.setCategory(category);
                ahe.setKenyaemrConceptUuid(String.valueOf(amrsConceptMappingService.findByAmrsConceptID(conceptId)));
                if(datatypeId.equals("1")||datatypeId.equals("2")){
                    ahe.setKenyaemrValue(value);
                }else {
                    ahe.setKenyaemrValue(String.valueOf(amrsConceptMappingService.findByAmrsConceptID(value)));
                }

                amrsHIVEnrollmentService.save(ahe);
                 //CareOpenMRSPayload.hivenrollment(amrsHIVEnrollmentService, parentUUID, locations, auth, url);


            }

            System.out.println("Patient_id" + patientId);
        }
    }








}

