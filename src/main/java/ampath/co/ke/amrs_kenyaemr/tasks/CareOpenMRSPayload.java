package ampath.co.ke.amrs_kenyaemr.tasks;

import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.service.AMRSProgramService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CareOpenMRSPayload {
    public static void programs(AMRSProgramService amrsProgramService, String url, String auth ) throws JSONException {
      // List<AMRSPrograms> amrsProgramsList = amrsProgramService.getprogramByLocation();
      /*  JSONObject jsonProgram = new JSONObject();
        for(int x=0;x<amrsProgramsList.size();x++) {

            String programms = Mappers.programs(amrsProgramsList.get(x).getUuid());

            AMRSPrograms ap = amrsProgramsList.get(x);
            jsonProgram.put("patient", ap.getPatientkenyaemruuid());
            jsonProgram.put("program", programms);
            jsonProgram.put("dateEnrolled", ap.getDateenrolled());
            jsonProgram.put("dateCompleted", ap.getDatecompleted());
        }
        */
    }
}
