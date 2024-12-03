package ampath.co.ke.amrs_kenyaemr.tasks;

import ampath.co.ke.amrs_kenyaemr.methods.AMRSConceptReader;
import ampath.co.ke.amrs_kenyaemr.models.*;
import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.payloads.*;
import org.json.JSONException;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MigrateCareData {


    public static void programs(String server, String username, String password, String locations, String parentUUID, AMRSProgramService amrsProgramService, AMRSPatientServices amrsPatientServices, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        List<AMRSPrograms> amrsProgramss = amrsProgramService.findFirstByOrderByIdDesc();
        List<AMRSPatients> amrsPatientsList = amrsPatientServices.getAll();
        String pidss ="";
        for(int y=0;y<amrsPatientsList.size();y++){
            pidss += amrsPatientsList.get(y).getPersonId()+",";
        }

        String pid = pidss.substring(0, pidss.length() - 1);
        System.out.println("PtientIDs "+ pid);

        String sql = "";
        if (amrsProgramss.size() > 0) {
            System.out.println("Latest program " + amrsProgramss.get(0).getPatientId());
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
                    "       and l.uuid in (" + locations + ") and pp.patient_program_id>=" + ppid + " and e.patient_id in ("+ pid +")  \n" + //and e. patient_id in ('1224605,1222698')
                    "       group by  pp.patient_id,p.concept_id  order by pp.patient_program_id asc";
           // System.out.println("SQLs " + sql);

        } else {
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
                    "       and l.uuid in (" + locations + ") and e.patient_id in ("+ pid +") \n" + //and e. patient_id in ('1224605,1222698')
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
            String person_id = patientId;
            List<AMRSPrograms> amrsPrograms = amrsProgramService.findByPatientIdAndProgramID(patientId, Integer.parseInt(programId));
            if (amrsPrograms.size() == 0) {
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
                if (amrsPatients.size() > 0) {
                    ae.setPatientKenyaemrUuid(amrsPatients.get(0).getKenyaemrpatientUUID());
                }
                ae.setKenyaemrProgramUuid(Mappers.programs(programUuid));
                ae.setAmrsPatientProgramID(patientProgramId);
                amrsProgramService.save(ae);
            }

            //Migate Programs
            CareOpenMRSPayload.programs(amrsProgramService, locations, parentUUID, url, auth);

        }

        //Migate Programs
        CareOpenMRSPayload.programs(amrsProgramService, locations, parentUUID, url, auth);
    }


    public static void encounters(String server, String username, String password, String locations, String parentUUID, AMRSEncounterService amrsEncounterService, AMRSPatientServices amrsPatientServices, AMRSVisitService amrsVisitService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {

        List<AMRSEncounters> amrsEncounters = amrsEncounterService.findFirstByOrderByIdDesc();

        List<AMRSPatients> amrsPatientsList = amrsPatientServices.getAll();
        String pidss ="";
        for(int y=0;y<amrsPatientsList.size();y++){
            pidss += amrsPatientsList.get(y).getPersonId()+",";
        }
        String pid = pidss.substring(0, pidss.length() - 1);
        System.out.println("PtientIDs "+ pid);

        String sql = "";
        if (amrsEncounters.size() > 0) {
            String EncounterID = amrsEncounters.get(0).getEncounterId();

            sql = "select patient_id as person_id, \n" +
                    " e.uuid as amrs_encounter_uuid,\n" +
                    " e.encounter_id ,\n" +
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
                    "e.visit_id,\n" +
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
                    " e.encounter_datetime ,\n" +
                    " e.encounter_type,\n" +
                    " form_id,\n" +
                    "  e.voided\n" +
                    " from amrs.encounter e\n" +
                    " inner join amrs.encounter_type et on  e.encounter_type=et.encounter_type_id \n" +
                    " inner join amrs.location l on  e.location_id=l.location_id\n" +
                    " where e.voided =0 and l.uuid in ("+ locations  +") and e.encounter_id>" + EncounterID + " and e.patient_id in ("+ pid +")  \n" +
                    " order by e.encounter_id asc ";


        } else {

            sql = "select patient_id as person_id, \n" +
                    " e.uuid as amrs_encounter_uuid,\n" +
                    " encounter_id ,\n" +
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
                    "e.visit_id,\n" +
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
                    " e.encounter_datetime ,\n" +
                    " e.encounter_type,\n" +
                    " form_id,\n" +
                    "  e.voided\n" +
                    " from amrs.encounter e\n" +
                    " inner join amrs.encounter_type et on  e.encounter_type=et.encounter_type_id\n" +
                    " inner join amrs.location l on  e.location_id=l.location_id\n" +
                    " where e.voided =0 and l.uuid in ("+ locations  +") and e.patient_id in ("+ pid +")  \n" +
                    " order by e.encounter_id asc "
                    ;
        }
        // System.out.println("Sql "+ sql);

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
            String amrsEncounterUuid = rs.getString("amrs_encounter_uuid");
            String encounterId = rs.getString("encounter_id");
            String encounterTypeId = rs.getString("encounter_type_id");
            String encounterDateTime = rs.getString("encounter_datetime");
            String encounterName = rs.getString("encounterName");
            String locationId = rs.getString("location_id");
            String visitId = rs.getString("visit_id");
            String kenyaemrEncounterTypeId = rs.getString("kenyaem_encounter_id");
            String kenyaemrEncounterUuid = rs.getString("kenyaemr_encounter_uuid");

            System.out.println("patientId " + patientId + " EncouterID " + encounterId + "encounterTypeId " + encounterTypeId + " encounterName " + encounterName + " locationId " + locationId + " visitId " + visitId);

            List<AMRSEncounters> amrsEncountersList = amrsEncounterService.findByPatientIdAndEncounterId(patientId, encounterId);
            if (amrsEncountersList.size() == 0) {
                AMRSEncounters ae = new AMRSEncounters();
                ae.setPatientId(patientId);
                ae.setEncounterId(encounterId);
                ae.setEncounterTypeId(encounterTypeId);
                ae.setEncounterName(encounterName);
                ae.setLocationId(locationId);
                ae.setVisitId(visitId);
                ae.setEncounterDateTime(encounterDateTime);
                ae.setKenyaemrEncounterTypeId(kenyaemrEncounterTypeId);
                ae.setKenyaemrEncounterTypeUuid(kenyaemrEncounterUuid);
                amrsEncounterService.save(ae);
            }
        }

         EncountersPayload.encounters(amrsEncounterService, amrsPatientServices, amrsVisitService,url,auth);

    }

    public static void enrollments(String server, String username, String password, String locations, String parentUUID, AMRSEnrollmentService amrsEnrollmentService, AMRSPatientServices amrsPatientServices, String url, String auth) throws SQLException, JSONException, ParseException, IOException {


        String sql = "select person_id,encounter_id,enrollment_date,hiv_start_date,death_date,transfer_out,transfer_out_date from etl.flat_hiv_summary_v15b  \n" +
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
             String encounterDatetime = rs.getString("encounter_datetime");
             String formId = rs.getString("form_id");
             String formName = rs.getString("Form_Name");
             String patientType= rs.getString("Patient_Type");
             String entryPoint = rs.getString("Entry_point");
             String tiFacility = rs.getString("TI_Facility");
             String dateFirstEnrolledInCare = rs.getString("Date_first_enrolled_in_care");
             String transferInDate = rs.getString("Transfer_in_date");
             String dateStartedArtAtTransferringFacility = rs.getString("");
             String dateConfirmedHivPositive = rs.getString("");
             String facilityConfirmedHivPositive = rs.getString("");
             String baselineArvUse = rs.getString("");
             String purposeOfBaselineArvUse = rs.getString("");
             String baselineArvRegimen = rs.getString("");
             String baselineArvRegimenLine = rs.getString("");
             String baselineArvDateLastUsed = rs.getString("");
             String baselineWhoStage = rs.getString("");
             String baselineCd4Results = rs.getString("");
             String baselineCd4Date = rs.getString("");
             String baselineVlResults = rs.getString("");
             String baselineVlDate = rs.getString("");
             String baselineVlLdlResults = rs.getString("");
             String baselineVlLdlDate = rs.getString("");
             String baselineHbvInfected = rs.getString("");
             String baselineTbInfected = rs.getString("");
             String baselinePregnant = rs.getString("");
             String baselineBreastFeeding = rs.getString("");
             String baselineWeight = rs.getString("");
             String baselineHeight = rs.getString("");
             String baselineBMI = rs.getString("");
             String nameOfTreatmentSupporter = rs.getString("");
             String relationshipOfTreatmentSupporter = rs.getString("");
             String treatmentSupporterTelephone = rs.getString("");
             String treatmentSupporterAddress = rs.getString("");

            List<AMRSEnrollments> enrollmentsList = amrsEnrollmentService.getByPatientID(patientId);
            if (enrollmentsList.size() == 0) {
                AMRSEnrollments ae = new AMRSEnrollments();
                ae.setPatientId(patientId);
                ae.setEncounterId(encounterId);
                ae.setEncounterDatetime(encounterDatetime);
                ae.setFormId(formId);
                ae.setFormName(formName);
                ae.setPatientType(patientType);
                ae.setEntryPoint(entryPoint);
                ae.setTiFacility(tiFacility);
                ae.setDateFirstEnrolledInCare(dateFirstEnrolledInCare);
                ae.setTransferInDate(transferInDate);
                ae.setDateStartedArtAtTransferringFacility(dateStartedArtAtTransferringFacility);
                ae.setDateConfirmedHivPositive(dateConfirmedHivPositive);
                ae.setFacilityConfirmedHivPositive(facilityConfirmedHivPositive);
                ae.setBaselineArvUse(baselineArvUse);
                ae.setPurposeOfBaselineArvUse(purposeOfBaselineArvUse);
                ae.setBaselineArvRegimen(baselineArvRegimen);
                ae.setBaselineArvRegimenLine(baselineArvRegimenLine);
                ae.setBaselineArvDateLastUsed(baselineArvDateLastUsed);
                ae.setBaselineWhoStage(baselineWhoStage);
                ae.setBaselineCd4Results(baselineCd4Results);
                ae.setBaselineCd4Date(baselineCd4Date);
                ae.setBaselineVlResults(baselineVlResults);
                ae.setBaselineVlDate(baselineVlDate);
                ae.setBaselineVlLdlResults(baselineVlLdlResults);
                ae.setBaselineVlLdlDate(baselineVlLdlDate);
                ae.setBaselineHbvInfected(baselineHbvInfected);
                ae.setBaselineTbInfected(baselineTbInfected);
                ae.setBaselinePregnant(baselinePregnant);
                ae.setBaselineBreastFeeding(baselineBreastFeeding);
                ae.setBaselineWeight(baselineWeight);
                ae.setBaselineHeight(baselineHeight);
                ae.setBaselineBMI(baselineBMI);
                ae.setNameOfTreatmentSupporter(nameOfTreatmentSupporter);
                ae.setRelationshipOfTreatmentSupporter(relationshipOfTreatmentSupporter);
                ae.setTreatmentSupporterTelephone(treatmentSupporterTelephone);
                ae.setTreatmentSupporterAddress(treatmentSupporterAddress);

                amrsEnrollmentService.save(ae);

            }



        }

        EnrollmentsPayload.encounters(url, auth);
    }


    public static void visits(String server, String username, String password, String locations, String parentUUID, AMRSVisitService amrsVisitService, AMRSObsService amrsObsService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql = "";
        List<AMRSVisits> amrsVisitsList = amrsVisitService.findFirstByOrderByIdDesc();
        List<AMRSPatients> amrsPatientsList = amrsPatientServices.getAll();
        String pidss ="";
        for(int y=0;y<amrsPatientsList.size();y++){
            pidss += amrsPatientsList.get(y).getPersonId()+",";
        }
        String pid = pidss.substring(0, pidss.length() - 1);
        System.out.println("PtientIDs "+ pid);

        if (amrsVisitsList.size() > 0) {
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
                    "              where l.uuid in (" + locations + ") and v.visit_id>" + amrsVisitsList.get(0).getVisitId() + "  and e.patient_id in ("+ pid +") \n " +
                    "              and v.voided=0\n" +
                    "              group by  v.visit_id order by v.visit_id asc ";
        } else {


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
                    "              where l.uuid in (" + locations + ") and e.patient_id in ("+ pid +")\n" +
                    "              and v.voided=0 \n" +
                    "              group by  v.visit_id order by v.visit_id asc ";
        }


        System.out.println("locations " + locations + " parentUUID " + parentUUID);
        System.out.println("locations " + sql);
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
            String voided = rs.getString("voided");

            System.out.println("Visit_Id " + visitId);

            List<AMRSVisits> av = amrsVisitService.findByVisitID(visitId);

            if (av.size() == 0) {
                List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(patientId);
                String kenyaemrpid = "Client Not Migrated";
                AMRSVisits avv = new AMRSVisits();
                if (amrsPatients.size() > 0) {
                    kenyaemrpid = amrsPatients.get(0).getKenyaemrpatientUUID();
                   // avv.setResponseCode("400");
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


    public static void order(String server, String username, String password, String locations, String parentUUID, AMRSOrderService amrsOrderService, AMRSPatientServices amrsPatientServices, AMRSEncounterMappingService amrsEncounterMappingService, AMRSConceptMappingService amrsConceptMappingService,AMRSEncounterService amrsEncounterService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {

        List<AMRSPatients> amrsPatientsList = amrsPatientServices.getAll();
        String pidss ="";
        for(int y=0;y<amrsPatientsList.size();y++){
            pidss += amrsPatientsList.get(y).getPersonId()+",";
        }
        String pid = pidss.substring(0, pidss.length() - 1);


        List<Integer> numbers = Arrays.asList(
                1171851, 1180830, 1167167, 1187468, 1210762, 1211677, 1205381, 1178556, 1177079, 1177856,
                1198638, 1211883, 1191727, 1191862, 1176796, 1210716, 1212684, 1223669, 1182300, 1188506,
                765546, 1187467, 1207817, 1212603, 1216267, 1225187, 1140933, 1185368, 1177985, 1189238,
                1191232, 1199830, 1170791, 1174464, 1206185, 1176830, 1182705, 1209127, 1177104, 1177467,
                1184252, 1192270, 1204250, 1212823, 1193179, 1177270, 1191005, 1198509, 1167355, 1178369,
                1184092, 1189326, 1191369, 1203354, 1203531, 1209140, 1226657, 1172517, 1186701, 1195760,
                1169969, 1178748, 1206865, 1215595, 1180696, 1186078, 1195200, 1177704, 1212906, 1209159,
                1202124, 1205268, 1208071, 1211667, 1212173, 1220342, 1176467, 1178456, 1176379, 1177933,
                1179157, 1185422, 1198117, 1203972, 1211635, 1185861, 1188709, 1192374, 1194786, 1200228,
                1212351, 1222698, 198492, 1178019, 1187425, 1176820, 1170115, 1175708, 1188938, 827082,
                1151769, 1152148, 1157527, 1161436, 1208219, 1217264, 1205146, 1021232, 1188422, 1204743,
                1215598, 1199094, 1206409, 1185638, 1214044, 1185495, 1201438, 1171131, 1176867, 1198019,
                1166252, 1223395, 1186135, 1169125, 1218971, 1184391, 1216914, 1153475, 1153527, 1153618,
                1153684, 1153703, 1153725, 1153811, 1153922, 1153931, 1166345, 1168996, 1174041, 1174884,
                1177493, 1180808, 1182235, 1182433, 1187031, 1188132, 1190631, 1192399, 1193816, 1196144,
                1196454, 1197444, 1199916, 1201312, 1202111, 1203658, 1207799, 1207926, 1207939, 1209301,
                1226630
        );


//
//        List<Integer> numbers = Arrays.asList(
//                1153703
//        );

        String pist = numbers.toString();
        String result = pist.substring(1, pist.length() - 1);

System.out.println("Patient Id "+ pid);
        String sql = "SELECT o.*, et.encounter_type_id, UUID() AS migration_uuid, NULL AS kenyaemr_order_uuid,\n" +
                " NULL AS kenyaemr_order_id FROM amrs.orders o \n" +
                " inner join amrs.encounter e on (e.encounter_id = o.encounter_id)\n" +
                " inner join amrs.encounter_type et on (et.encounter_type_id = e.encounter_type)\n" +
                " where  e.patient_id in ("+ result +" ) order by o.date_created ASC "; //

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
            if (amrsOrders.isEmpty()) {
                String kenyaemr_uuid = "";
                AMRSOrders ao = new AMRSOrders();
                List<AMRSEncountersMapping> ac = amrsEncounterMappingService.getByAmrsID(amrsEncounterTypeId);
                String kenyaemr_encounter_id;
                if (!ac.isEmpty()) {
                    kenyaemr_encounter_id = ac.get(0).getKenyaemrEncounterTypeUuid();
                } else {
                    kenyaemr_encounter_id = "";
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
                    ao.setKenyaemrPatientUuid(kenyaemr_patient_uuid);
                } else {
                    kenyaemr_patient_uuid = "Not Found"; // add logic for missing patientkenyaemr_patient_uuid
                }

                String kenyaEmrEncounterUuid="";
                List<AMRSEncounters> amrsEncounters = amrsEncounterService.findByEncounterId(encounterId);
                if(!amrsEncounters.isEmpty()){
                    kenyaEmrEncounterUuid = amrsEncounters.get(0).getKenyaemrEncounterUuid();
                    ao.setKenyaEmrEncounterUuid(kenyaEmrEncounterUuid);
                }
                String justificationcode = "";
                if(justification==null) {
                    justification="11666";
                }
                    if (!justification.isEmpty()) {
                        List<AMRSConceptMapper> amrsConceptMapper = amrsConceptMappingService.findByAmrsConceptID(justification);
                        if (!amrsConceptMapper.isEmpty()) {
                            justificationcode = amrsConceptMapper.get(0).getKenyaemrConceptUUID();

                        }
                    }



//                System.out.println("Patient " + kenyaemr_patient_uuid + " concept " + conceptId + " kenyaemr_concept_id " + kenyaemr_uuid + " justification" + justification + "Kenyaemr justicafiation " + justificationcode);


                amrsOrderService.save(ao);

            }
            // orders
             OrdersPayload.orders(amrsOrderService, amrsPatientServices,  url,auth);

        }
    }

    public static void triage(String server, String username, String password, String locations, String parentUUID, AMRSTriageService amrsTriageService, AMRSPatientServices amrsPatientServices,AMRSEncounterService amrsEncounterService ,AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {

        String prevEncounterID = null; // Declare the variable
        List<AMRSTriage> amrsTriages = amrsTriageService.findFirstByOrderByIdDesc();
        if (amrsTriages != null && !amrsTriages.isEmpty()) {
            prevEncounterID = amrsTriages.get(0).getEncounterId();
        }

        List<AMRSPatients> amrsPatientsList = amrsPatientServices.getAll();
        String pidss ="";
        for(int y=0;y<amrsPatientsList.size();y++){
            pidss += amrsPatientsList.get(y).getPersonId()+",";
        }
        String pid = pidss.substring(0, pidss.length() - 1);
        System.out.println("PtientIDs "+ pid);

        String sql = "";
        if (amrsTriages == null || amrsTriages.isEmpty()) {
            sql = "WITH cte_vitals_concepts as (\n" +
                    "SELECT \n" +
                    "    concept_id, uuid\n" +
                    "FROM\n" +
                    "    amrs.concept\n" +
                    "WHERE\n" +
                    "    uuid IN (\n" +
                    "\t\t'a8a65d5a-1350-11df-a1f1-0026b9348838', \n" +
                    "        'a8a65e36-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a65f12-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a6f71a-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a65fee-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a660ca-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a6619c-1350-11df-a1f1-0026b9348838',\n" +
                    "        '5099d8a8-36c1-4574-a568-9bc49c15c08c',\n" +
                    "        '507f48e7-26fc-490b-a521-35d7c5aa8e9f',\n" +
                    "        'a8a66354-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a89c60c0-1350-11df-a1f1-0026b9348838',\n" +
                    "        '9061e5d5-8478-4d16-be44-bfec05b6705a',\n" +
                    "        'a89c6188-1350-11df-a1f1-0026b9348838')\n" +
                    ")\n" +
                    "\n" +
                    "SELECT \n" +
                    "o.person_id,\n" +
                    "o.encounter_id, \n" +
                    "e.encounter_datetime,\n" +
                    "e.visit_id,\n" +
                    "o.location_id,\n" +
                    "max(o.obs_datetime) as obs_datetime,\n" +
                    "max(case when o.concept_id = 9816 then o.value_numeric end) as height_age_zscore,\n" +
                    "max(case when o.concept_id = 8238 then o.value_numeric end) as weight_height_zscore,\n" +
                    "max(case when o.concept_id = 8239 then o.value_numeric end) as bmi_age_zscore,\n" +
                    "max(case when o.concept_id = 1342 then o.value_numeric end) as bmi,\n" +
                    "max(case when o.concept_id = 1343 then o.value_numeric end) as muac_mm,\n" +
                    "max(case when o.concept_id = 5085 then o.value_numeric end) as systolic_bp,\n" +
                    "max(case when o.concept_id = 5086 then o.value_numeric end) as diastolic_bp,\n" +
                    "max(case when o.concept_id = 5087 then o.value_numeric end) as pulse,\n" +
                    "max(case when o.concept_id = 5088 then o.value_numeric end) as temperature,\n" +
                    "max(case when o.concept_id = 5092 then o.value_numeric end) as spo2,\n" +
                    "max(case when o.concept_id = 5242 then o.value_numeric end) as rr,\n" +
                    "max(case when o.concept_id = 5089 then o.value_numeric end) as weight,\n" +
                    "max(case when o.concept_id = 5090 then o.value_numeric end) as height\n" +
                    "FROM amrs.obs o \n" +
                    "INNER JOIN amrs.encounter e using(encounter_id)\n" +
                    "INNER JOIN amrs.location l on l.location_id = o.location_id \n" +
                    "WHERE o.concept_id IN (SELECT concept_id FROM cte_vitals_concepts) \n" +
                    "AND l.uuid IN("+ locations +") \n" +
                    " AND o.person_id in ( "+ pid +" )\n" +
                    "GROUP BY o.person_id, o.encounter_id limit 10";
        } else {
            sql = "WITH cte_vitals_concepts as (\n" +
                    "SELECT \n" +
                    "    concept_id, uuid\n" +
                    "FROM\n" +
                    "    amrs.concept\n" +
                    "WHERE\n" +
                    "    uuid IN (\n" +
                    "\t\t'a8a65d5a-1350-11df-a1f1-0026b9348838', \n" +
                    "        'a8a65e36-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a65f12-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a6f71a-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a65fee-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a660ca-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a8a6619c-1350-11df-a1f1-0026b9348838',\n" +
                    "        '5099d8a8-36c1-4574-a568-9bc49c15c08c',\n" +
                    "        '507f48e7-26fc-490b-a521-35d7c5aa8e9f',\n" +
                    "        'a8a66354-1350-11df-a1f1-0026b9348838',\n" +
                    "        'a89c60c0-1350-11df-a1f1-0026b9348838',\n" +
                    "        '9061e5d5-8478-4d16-be44-bfec05b6705a',\n" +
                    "        'a89c6188-1350-11df-a1f1-0026b9348838')\n" +
                    ")\n" +
                    "\n" +
                    "SELECT \n" +
                    "o.person_id,\n" +
                    "o.encounter_id, \n" +
                    "e.encounter_datetime,\n" +
                    "e.visit_id,\n" +
                    "o.location_id,\n" +
                    "max(o.obs_datetime) as obs_datetime,\n" +
                    "max(case when o.concept_id = 9816 then o.value_numeric end) as height_age_zscore,\n" +
                    "max(case when o.concept_id = 8238 then o.value_numeric end) as weight_height_zscore,\n" +
                    "max(case when o.concept_id = 8239 then o.value_numeric end) as bmi_age_zscore,\n" +
                    "max(case when o.concept_id = 1342 then o.value_numeric end) as bmi,\n" +
                    "max(case when o.concept_id = 1343 then o.value_numeric end) as muac_mm,\n" +
                    "max(case when o.concept_id = 5085 then o.value_numeric end) as systolic_bp,\n" +
                    "max(case when o.concept_id = 5086 then o.value_numeric end) as diastolic_bp,\n" +
                    "max(case when o.concept_id = 5087 then o.value_numeric end) as pulse,\n" +
                    "max(case when o.concept_id = 5088 then o.value_numeric end) as temperature,\n" +
                    "max(case when o.concept_id = 5092 then o.value_numeric end) as spo2,\n" +
                    "max(case when o.concept_id = 5242 then o.value_numeric end) as rr,\n" +
                    "max(case when o.concept_id = 5089 then o.value_numeric end) as weight,\n" +
                    "max(case when o.concept_id = 5090 then o.value_numeric end) as height\n" +
                    "FROM amrs.obs o \n" +
                    "INNER JOIN amrs.encounter e using(encounter_id)\n" +
                    "INNER JOIN amrs.location l on l.location_id = o.location_id \n" +
                    "WHERE o.concept_id IN (SELECT concept_id FROM cte_vitals_concepts) \n" +
                    " AND l.uuid IN ("+ locations +") \n" +
                    " AND o.person_id  in ("+ pid +") \n" +
                    " AND o.encounter_id > "+ prevEncounterID +"  \n" +
                    "GROUP BY o.person_id, o.encounter_id limit 10";
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
             String patientId = rs.getString("person_id");
             String encounterID = rs.getString("encounter_id");
             String encounterDateTime = rs.getString("encounter_datetime");
             String visitId = rs.getString("visit_id");
             String locationId = rs.getString("location_id");
             String obsDateTime = rs.getString("obs_datetime");
             String heightAgeZscore = rs.getString("height_age_zscore");
             String weightHeightZscore = rs.getString("weight_height_zscore");
             String bmiAgeZscore = rs.getString("bmi_age_zscore");
             String bmi = rs.getString("bmi");
             String muacMm = rs.getString("muac_mm");
             String systolicBp = rs.getString("systolic_bp");
             String diastolicBp = rs.getString("diastolic_bp");
             String pulse = rs.getString("pulse");
             String temperature = rs.getString("temperature");
             String spo2 = rs.getString("spo2");
             String rr = rs.getString("rr");
             String weight = rs.getString("weight");
             String height = rs.getString("height");

            List<AMRSTriage> amrsTriageList = amrsTriageService.findByPatientIdAndEncounterId(patientId, encounterID);
            if (amrsTriageList.isEmpty()) {
                AMRSTriage at = new AMRSTriage();
                at.setPatientId(patientId);
                at.setEncounterDateTime(encounterDateTime);
                at.setVisitId(visitId);
                at.setEncounterId(encounterID);
                at.setLocationId(locationId);
                at.setObsDateTime(obsDateTime);
                at.setHeightAgeZscore(heightAgeZscore);
                at.setWeightHeightZscore(weightHeightZscore);
                at.setBmiAgeZscore(bmiAgeZscore);
                at.setBmi(bmi);
                at.setMuacMm(muacMm);
                at.setSystolicBp(systolicBp);
                at.setDiastolicBp(diastolicBp);
                at.setPulse(pulse);
                at.setTemperature(temperature);
                at.setSpo2(spo2);
                at.setRr(rr);
                at.setWeight(weight);
                at.setHeight(height);
                at.setKenyaemrFormUuid("37f6bd8d-586a-4169-95fa-5781f987fe62");
                amrsTriageService.save(at);
                 CareOpenMRSPayload.triage(amrsTriageService, amrsPatientServices,amrsEncounterService,url,auth);

            }

            System.out.println("Patient_id" + patientId);
        }
    }

    public static void encounterMappings(String server, String username, String password, String locations, String parentUUID, AMRSEncounterMappingService amrsEncounterMappingService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql = "select \n" +
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
            if (amList.size() > 0) {
                AMRSEncountersMapping am = amList.get(0);
                am.setAmrsEncounterTypeId(encounterTypeId);
                am.setKenyaemrEncounterTypeId(kenyaemEncounterId);
                am.setKenyaemrEncounterTypeUuid(kenyaemEncounterUuid);
                amrsEncounterMappingService.save(am);
            } else {

                AMRSEncountersMapping am = new AMRSEncountersMapping();
                am.setAmrsEncounterTypeId(encounterTypeId);
                am.setKenyaemrEncounterTypeId(kenyaemEncounterId);
                am.setKenyaemrEncounterTypeUuid(kenyaemEncounterUuid);
                amrsEncounterMappingService.save(am);
            }

        }


    }

    public static void hivenrollment(String server, String username, String password, String locations, String parentUUID, AMRSHIVEnrollmentService amrsHIVEnrollmentService, AMRSPatientServices amrsPatientServices, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql = "";
        List<AMRSHIVEnrollment> amrshivEnrollmentLists = amrsHIVEnrollmentService.findFirstByOrderByIdDesc();

        String nextEncounterID = "";
        if (amrshivEnrollmentLists.size() == 0) {

            sql = "SELECT \n" +
                    "    o.person_id,\n" +
                    "    e.encounter_id,\n" +
                    "    e.encounter_datetime,\n" +
                    "    e.encounter_type,\n" +
                    "    l.uuid AS location_uuid,\n" +
                    "    o.concept_id,\n" +
                    "    cn.name AS concept_name,\n" +
                    "    o.obs_datetime,\n" +
                    "    COALESCE(o.value_coded,\n" +
                    "            o.value_datetime,\n" +
                    "            o.value_numeric,\n" +
                    "            o.value_text) AS value,\n" +
                    "    cd.name AS value_type,\n" +
                    "    c.datatype_id,\n" +
                    "    et.name AS encounterName,\n" +
                    "    e.creator AS provider_id,\n" +
                    "    'HIV Enrollment' AS Category\n" +
                    "FROM\n" +
                    "    amrs.obs o\n" +
                    "        INNER JOIN\n" +
                    "    amrs.encounter e ON (o.encounter_id = e.encounter_id)\n" +
                    "        INNER JOIN\n" +
                    "    amrs.encounter_type et ON et.encounter_type_id = e.encounter_type\n" +
                    "        INNER JOIN\n" +
                    "    amrs.concept c ON c.concept_id = o.concept_id\n" +
                    "        INNER JOIN\n" +
                    "    amrs.concept_name cn ON o.concept_id = cn.concept_id\n" +
                    "        INNER JOIN\n" +
                    "    amrs.concept_datatype cd ON cd.concept_datatype_id = c.datatype_id\n" +
                    "        INNER JOIN\n" +
                    "    amrs.location l ON e.location_id = l.location_id\n" +
                    "WHERE\n" +
                    "    e.encounter_type IN (1 , 3, 24, 32, 105, 137, 135, 136, 265, 266)\n" +
                    "        AND o.concept_id IN (8287, 1224,  1724, 10792, 6750, 6749, 1915, 10747, 10748, 7013, 1499, 9203, 6748, 5356, 1633, 2155, 966, 1088, 2056, 5090, 1343, 5629, 1174, 10653, 10873, 10872, 10741)\n" +
                    "        AND e.location_id IN (2)\n" +
                    "        AND e.voided = 0\n" +
                    "        AND cd.name <> 'N/A'\n" +
                    "ORDER BY o.encounter_id ASC\n" +
                    "LIMIT 10;\n";
        } else {
            System.out.println("List" + amrshivEnrollmentLists);
            nextEncounterID = amrshivEnrollmentLists.get(0).getEncounterID();
            sql = "SELECT \n" +
                    "    o.person_id,\n" +
                    "    e.encounter_id,\n" +
                    "    e.encounter_datetime,\n" +
                    "    e.encounter_type,\n" +
                    "    l.uuid AS location_uuid,\n" +
                    "    o.concept_id,\n" +
                    "    cn.name AS concept_name,\n" +
                    "    o.obs_datetime,\n" +
                    "    COALESCE(o.value_coded,\n" +
                    "            o.value_datetime,\n" +
                    "            o.value_numeric,\n" +
                    "            o.value_text) AS value,\n" +
                    "    cd.name AS value_type,\n" +
                    "    c.datatype_id,\n" +
                    "    et.name AS encounterName,\n" +
                    "    e.creator AS provider_id,\n" +
                    "    'HIV Enrollment' AS Category\n" +
                    "FROM\n" +
                    "    amrs.obs o\n" +
                    "        INNER JOIN\n" +
                    "    amrs.encounter e ON (o.encounter_id = e.encounter_id)\n" +
                    "        INNER JOIN\n" +
                    "    amrs.encounter_type et ON et.encounter_type_id = e.encounter_type\n" +
                    "        INNER JOIN\n" +
                    "    amrs.concept c ON c.concept_id = o.concept_id\n" +
                    "        INNER JOIN\n" +
                    "    amrs.concept_name cn ON o.concept_id = cn.concept_id\n" +
                    "        INNER JOIN\n" +
                    "    amrs.concept_datatype cd ON cd.concept_datatype_id = c.datatype_id\n" +
                    "        INNER JOIN\n" +
                    "    amrs.location l ON e.location_id = l.location_id\n" +
                    "WHERE\n" +
                    "    e.encounter_type IN (1 , 3, 24, 32, 105, 137, 135, 136, 265, 266)\n" +
                    "        AND o.concept_id IN (8287, 1224,  1724, 10792, 6750, 6749, 1915, 10747, 10748, 7013, 1499, 9203, 6748, 5356, 1633, 2155, 966, 1088, 2056, 5090, 1343, 5629, 1174, 10653, 10873, 10872, 10741)\n" +
                    "        AND e.location_id IN (2)\n" +
                    "        AND e.voided = 0\n" +
                    "AND e.encounter_id > " + nextEncounterID + "\n" +
                    "        AND cd.name <> 'N/A'\n" +
                    "ORDER BY o.encounter_id ASC\n" +
                    "LIMIT 10;";
        }
        System.out.println("sqlHivEnrollment" + sql);
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
            String provider = rs.getString("provider_id");
            String encounterName = rs.getString("encounterName");
            String category = rs.getString("Category");


            List<AMRSHIVEnrollment> amrshivEnrollmentList = amrsHIVEnrollmentService.findByPatientIdAndEncounterIDAndConceptId(patientId, encounterID, conceptId);
            if (amrshivEnrollmentList.isEmpty()) {
                AMRSHIVEnrollment ahe = new AMRSHIVEnrollment();
                ahe.setPatientId(patientId);
                ahe.setConceptId(conceptId);
                ahe.setLocationUuid(locationUuid);
                ahe.setEncounterID(encounterID);
                ahe.setEncounterDateTime(encounterDatetime);
                ahe.setEncounterType(encounterType);
                ahe.setConceptName(conceptName);
                ahe.setObsDateTime(obsDatetime);
                ahe.setConceptValue(value);
                ahe.setValueDataType(valueType);
                ahe.setDataTypeId(datatypeId);
                ahe.setProvider(provider);
                ahe.setEncounterName(encounterName);
                ahe.setCategory(category);
                ahe.setKenyaemrConceptUuid(String.valueOf(amrsConceptMappingService.findByAmrsConceptID(conceptId)));
                if (datatypeId.equals("1") || datatypeId.equals("2")) {
                    ahe.setKenyaemrValue(value);
                } else {
                    ahe.setKenyaemrValue(String.valueOf(amrsConceptMappingService.findByAmrsConceptID(value)));
                }
                System.out.println("Tumefika Hapa!!!" + parentUUID);
                amrsHIVEnrollmentService.save(ahe);
                //CareOpenMRSPayload.hivenrollment(amrsHIVEnrollmentService, parentUUID, locations, auth, url);


            }

            System.out.println("Patient_id" + patientId);
        }
    }

    public static void obs(String server, String username, String password, String locations, String parentUUID, AMRSObsService amrsObsService, AMRSPatientServices amrsPatientServices, AMRSMappingService amrsMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {

        List<AMRSPatients> amrsPatientsList = amrsPatientServices.getAll();
        String pidss ="";
        for(int y=0;y<amrsPatientsList.size();y++){
            pidss += amrsPatientsList.get(y).getPersonId()+",";
        }
        String pid = pidss.substring(0, pidss.length() - 1);
        System.out.println("Patient Id "+ pid);
        String sql = "SELECT \n" +
                "    o.obs_id,\n" +
                "    o.concept_id,\n" +
                "    o.obs_datetime,\n" +
                "    o.encounter_id,\n" +
                "         CASE \n" +
                "        WHEN o.value_numeric IS NOT NULL THEN 'Value Numeric'\n" +
                "        WHEN o.value_coded IS NOT NULL THEN 'Value Coded'\n" +
                "        WHEN o.value_text IS NOT NULL THEN 'Value Text'\n" +
                "        ELSE 'null'\n" +
                "    END AS value_type,\n" +
                "       CASE \n" +
                "        WHEN o.value_numeric IS NOT NULL THEN o.value_numeric\n" +
                "        WHEN o.value_coded IS NOT NULL THEN o.value_coded\n" +
                "        WHEN o.value_text IS NOT NULL THEN o.value_text\n" +
                "         WHEN o.value_datetime IS NOT NULL THEN o.value_datetime\n" +
                "        ELSE 'null'\n" +
                "    END AS value,\n" +
                "    e.encounter_datetime,\n" +
                "    et.name AS encounter_type,\n" +
                "    COALESCE(cn_answer.name, '') as value_coded_name,\n" +
                "    COALESCE(drug.name, '') as drug_name\n" +
                "FROM \n" +
                "    amrs.obs o\n" +
                "    INNER JOIN amrs.concept_name cn ON o.concept_id = cn.concept_id \n" +
                "        AND cn.locale = 'en' \n" +
                "        -- AND cn.concept_name_type = 'FULLY_SPECIFIED'\n" +
                "    LEFT JOIN amrs.encounter e ON o.encounter_id = e.encounter_id\n" +
                "    LEFT JOIN amrs.encounter_type et ON e.encounter_type = et.encounter_type_id\n" +
                "    LEFT JOIN amrs.location l ON e.location_id = l.location_id\n" +
                "    LEFT JOIN amrs.concept_name cn_answer ON o.value_coded = cn_answer.concept_id \n" +
                "        AND cn_answer.locale = 'en' \n" +
                "        -- AND cn_answer.concept_name_type = 'FULLY_SPECIFIED'\n" +
                "    LEFT JOIN amrs.drug ON o.value_drug = drug.drug_id\n" +
                "WHERE \n" +
                "     o.person_id in ("+pid+")\n" +
                "    AND o.voided = 0\n" +
                "ORDER BY \n" +
                "    o.obs_datetime DESC";

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
            String obsDatetime = rs.getString("obs_datetime");
            String conceptId = rs.getString("concept_id");
            String encounterId = rs.getString("encounter_id");
            String value = rs.getString("value");
            String valueType = rs.getString("value_type");
            String encounterType = rs.getString("encounter_type");
            String encounterDatetime = rs.getString("encounter_datetime");


            List<AMRSObs> amrsObsList = amrsObsService.findByPatientIdAndEncounterIDAndConceptId(pid, encounterId, conceptId);
            if (amrsObsList.isEmpty()) {
               AMRSObs ao = new AMRSObs();
               ao.setConceptId(conceptId);
               ao.setEncounterID(encounterId);
               ao.setValueType(valueType);
               ao.setEncounterType(encounterType);
               ao.setEncounterDatetime(encounterDatetime);
               ao.setObsDatetime(obsDatetime);
               ao.setDataType(value);

               System.out.println("Amrs obs: " + ao );
                amrsObsService.save(ao);
            }
        }

//        EncountersPayload.encounters(amrsEncounterService, amrsPatientServices, amrsVisitService,url,auth);

    }


    public static void programSwitches(String server, String username, String password, String locations, String parentUUID, AMRSRegimenSwitchService amrsRegimenSwitchService, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {

        AMRSConceptReader amrsConceptReader = new AMRSConceptReader();

        String sql = "";
        List<AMRSRegimenSwitch> amrsRegimenSwitchList = amrsRegimenSwitchService.findFirstByOrderByIdDesc();
        String nextEncounterID = "";
        if (amrsRegimenSwitchList.isEmpty()) {

            sql = "SELECT patient_id,MIN(regimen_data.enc_id) AS Encounter_ID, \n" +
                    "concept_id,value_coded,Encounter_Date,GROUP_CONCAT(concept_name SEPARATOR \",\") as Regimen,Reason_for_Change FROM \n" +
                    "(\n" +
                    "\tSELECT o.person_id as patient_id,o.encounter_id as enc_id,o.concept_id,o.value_coded,o.voided,e.encounter_datetime AS Encounter_Date, cn.name as concept_name  \n" +
                    "\t\tfrom amrs.obs o\n" +
                    "\t\tINNER JOIN amrs.concept_name cn ON o.value_coded=cn.concept_id and cn.locale='en' and cn.concept_name_type='FULLY_SPECIFIED' \n" +
                    "\t\tINNER JOIN amrs.encounter e ON e.encounter_id=o.encounter_id and e.voided=0 \n" +
                    "\twhere o.concept_id=1088 and o.voided=0 \n" +
                    "    and o.location_id in (339)\n" +
                    "    and o.person_id in(704258,1171851,1180830,1167167)\n" +
                    "\tGROUP BY patient_id, o.value_coded \n" +
                    ")\n" +
                    " as regimen_data\n" +
                    " LEFT OUTER JOIN (\n" +
                    "\t SELECT encounter_id,cn.name AS Reason_for_Change\n" +
                    "\t\t FROM amrs.obs o \n" +
                    "\t\t INNER JOIN amrs.concept_name cn ON o.value_coded=cn.concept_id and o.voided=0 \n" +
                    "\t AND o.concept_id in (1252, 1262, 1266,1269) and cn.locale='en'  and cn.concept_name_type='FULLY_SPECIFIED' \n" +
                    "\t GROUP BY encounter_id \n" +
                    " ) as reg_fail \n" +
                    " ON regimen_data.enc_id=reg_fail.encounter_id\n" +
                    " \n" +
                    " GROUP BY enc_id, patient_id ORDER BY patient_id ASC,Encounter_Date ASC \n";
        } else {
            System.out.println("List" + amrsRegimenSwitchList);
//            nextEncounterID = amrsRegimenSwitchList.get(0).getEncounterID();
            sql = "SELECT patient_id,MIN(regimen_data.enc_id) AS Encounter_ID, \n" +
                    "concept_id,value_coded,Encounter_Date,GROUP_CONCAT(concept_name SEPARATOR \",\") as Regimen,Reason_for_Change FROM \n" +
                    "(\n" +
                    "\tSELECT o.person_id as patient_id,o.encounter_id as enc_id,o.concept_id,o.value_coded,o.voided,e.encounter_datetime AS Encounter_Date, cn.name as concept_name  \n" +
                    "\t\tfrom amrs.obs o\n" +
                    "\t\tINNER JOIN amrs.concept_name cn ON o.value_coded=cn.concept_id and cn.locale='en' and cn.concept_name_type='FULLY_SPECIFIED' \n" +
                    "\t\tINNER JOIN amrs.encounter e ON e.encounter_id=o.encounter_id and e.voided=0 \n" +
                    "\twhere o.concept_id=1088 and o.voided=0 \n" +
                    "    and o.location_id in (339)\n" +
                    "    and o.person_id in(704258,1171851,1180830,1167167)\n" +
                    "\tGROUP BY patient_id, o.value_coded \n" +
                    ")\n" +
                    " as regimen_data\n" +
                    " LEFT OUTER JOIN (\n" +
                    "\t SELECT encounter_id,cn.name AS Reason_for_Change\n" +
                    "\t\t FROM amrs.obs o \n" +
                    "\t\t INNER JOIN amrs.concept_name cn ON o.value_coded=cn.concept_id and o.voided=0 \n" +
                    "\t AND o.concept_id in (1252, 1262, 1266,1269) and cn.locale='en'  and cn.concept_name_type='FULLY_SPECIFIED' \n" +
                    "\t GROUP BY encounter_id \n" +
                    " ) as reg_fail \n" +
                    " ON regimen_data.enc_id=reg_fail.encounter_id\n" +
                    " \n" +
                    " GROUP BY enc_id, patient_id ORDER BY patient_id ASC,Encounter_Date ASC \n";
        }
        System.out.println("regimenSwitchList" + sql);
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
            String encounterId = rs.getString("Encounter_ID");
            String conceptId = rs.getString("concept_id");
            String valueCoded = rs.getString("value_coded");
            String encounterDatetime = rs.getString("Encounter_Date");
            String regimen = rs.getString("Regimen");
            String reasonForChange = rs.getString("Reason_for_Change");
            //String kenyaemrEncounterUuid = rs.getString("");
            //String kenyaemrConceptUuid = rs.getString("");
            //String kenyaemrValue = rs.getString("");




            if (amrsRegimenSwitchList.isEmpty()) {
                AMRSRegimenSwitch ar = new AMRSRegimenSwitch();
                ar.setPatientId(patientId);
                ar.setEncounterId(encounterId);
                ar.setConceptId(conceptId);
                ar.setValueCoded(valueCoded);
                ar.setEncounterDatetime(encounterDatetime);
                ar.setRegimen(regimen);
                ar.setReasonForChange(reasonForChange);
//                ar.setKenyaemrEncounterUuid();
                ar.setKenyaemrConceptUuid(amrsConceptReader.translater(conceptId));
                ar.setKenyaemrValue(amrsConceptReader.translater(valueCoded));

                ar.setKenyaemrConceptUuid(String.valueOf(amrsConceptMappingService.findByAmrsConceptID(conceptId)));

                System.out.println("Tumefika Hapa!!!" + parentUUID);
                amrsRegimenSwitchService.save(ar);
//                CareOpenMRSPayload.amrsProgramSwitch(amrsRegimenSwitchService, parentUUID, locations, auth, url);


            }

            System.out.println("Patient_id" + patientId);
        }


}


public static void patientStatus(String server, String username, String password, String locations, String parentUUID, AMRSPatientStatusService amrsPatientStatusService, AMRSConceptMappingService amrsConceptMappingService, String url, String auth) throws SQLException, JSONException, ParseException, IOException {


    String sql = "";
    List<AMRSPatientStatus> amrsCivilStatusList = amrsPatientStatusService.findFirstByOrderByIdDesc();
    String nextEncounterID = "";
    if (amrsCivilStatusList.isEmpty()) {

        sql = "SELECT \n" +
                "    pa.person_id,\n" +
                "    pt.person_attribute_type_id,\n" +
                "    case when pt.person_attribute_type_id =5 then '1054'\n" +
                "    when pt.person_attribute_type_id =42 then '1542'\n" +
                "    when pt.person_attribute_type_id =73 then '1712'\n" +
                "    end kenyaemr_concept,\n" +
                "    pt.name,\n" +
                "    pa.value\n" +
                "    \n" +
                "FROM\n" +
                "    amrs.person_attribute pa\n" +
                "        INNER JOIN\n" +
                "    amrs.person_attribute_type pt ON pa.person_attribute_type_id = pt.person_attribute_type_id and pt.person_attribute_type_id in(42,73,5)\n" +
                "    inner join amrs.concept c on c.concept_id = pa.value\n" +
                "WHERE\n" +
                "     pa.person_id IN (1220891,1191232, 1199830, 1170791, 1174464, 1206185)\n" +
                "        AND \n" +
                "        pa.voided = 0";
    } else {
        System.out.println("List" + amrsCivilStatusList);
//            nextEncounterID = amrs.get(0).getEncounterID();
        sql = "SELECT \n" +
                "    pa.person_id,\n" +
                "    pt.person_attribute_type_id,\n" +
                "    case when pt.person_attribute_type_id =5 then '1054'\n" +
                "    when pt.person_attribute_type_id =42 then '1542'\n" +
                "    when pt.person_attribute_type_id =73 then '1712'\n" +
                "    end kenyaemr_concept,\n" +
                "    pt.name,\n" +
                "    pa.value\n" +
                "    \n" +
                "FROM\n" +
                "    amrs.person_attribute pa\n" +
                "        INNER JOIN\n" +
                "    amrs.person_attribute_type pt ON pa.person_attribute_type_id = pt.person_attribute_type_id and pt.person_attribute_type_id in(42,73,5)\n" +
                "    inner join amrs.concept c on c.concept_id = pa.value\n" +
                "WHERE\n" +
                "     pa.person_id IN (1220891,1191232, 1199830, 1170791, 1174464, 1206185)\n" +
                "        AND \n" +
                "        pa.voided = 0";
    }
    System.out.println("regimenSwitchList" + sql);
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
        String personId = rs.getString("person_id");
        String personAttributeTypeId = rs.getString("person_attribute_type_id");
        String kenyaEmrConcept = rs.getString("kenyaemr_concept");
        String name = rs.getString("name");
        String value = rs.getString("value");


        if (amrsCivilStatusList.isEmpty()) {
            AMRSPatientStatus cs = new AMRSPatientStatus();
            cs.setPersonId(personId);
            cs.setPersonAttributeTypeId(personAttributeTypeId);
            cs.setKenyaEmrConcept(kenyaEmrConcept);
            cs.setName(name);
            cs.setValue(value);


            System.out.println("Tumefika Hapa!!!" + parentUUID);
            amrsPatientStatusService.save(cs);
            CareOpenMRSPayload.patientStatus(amrsPatientStatusService, parentUUID, locations, auth, url);

        }

        System.out.println("Patient_id" + personId);
    }


}

}

