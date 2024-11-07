package ampath.co.ke.amrs_kenyaemr.tasks;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import ampath.co.ke.amrs_kenyaemr.service.AMRSProgramService;
import org.json.JSONException;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class MigrateCareData {
    public static void programs (String server, String username, String password, String locations, String parentUUID, AMRSProgramService amrsProgramService, AMRSPatientServices amrsPatientServices, String url, String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql="select  pp.patient_id, p.name,\n" +
                "pp.uuid,\n" +
                "pp.location_id,\n" +
                "p.concept_id,\n" +
                "pp.date_enrolled,\n" +
                "pp.date_completed\n" +
                "from amrs.patient_program pp\n" +
                "inner join amrs.program p on p.program_id=pp.program_id\n" +
                "where pp.patient_id=1080061";
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
            List<AMRSPrograms> patientsList = amrsProgramService.getprogramByLocation(rs.getString(2),parentUUID);
            if (patientsList.size()==0) {

                AMRSPatients amrsPatients = amrsPatientServices.getByPatientID(patientsList.get(0).getPatientid());

                String person_id=rs.getString(1);
                AMRSPrograms ae = new AMRSPrograms();
                ae.setPatientid(person_id);
                ae.setParentlocationuuid(parentUUID);
                ae.setLocationid(rs.getString(4));
                ae.setUuid(rs.getString(3));
                ae.setConceptid(rs.getString(5));
                ae.setProgramname(rs.getString(2));
                ae.setDateenrolled(rs.getString(6));
                ae.setDatecompleted(rs.getString(7));
                ae.setKenyaemruuid(amrsPatients.getKenyaemrpatientUUID());
                amrsProgramService.save(ae);
            }

            }

        }

}
