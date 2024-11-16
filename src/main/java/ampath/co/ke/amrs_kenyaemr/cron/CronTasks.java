package ampath.co.ke.amrs_kenyaemr.cron;

import org.json.JSONException;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class CronTasks {
    @Scheduled(cron = "0 */1 * ? * *")
    public void ProcessUsers() throws JSONException, ParseException, SQLException, IOException {

    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void ProcessPatients() throws JSONException, ParseException, SQLException, IOException {

    }
    @Scheduled(cron = "0 */1 * ? * *")
    public void ProcessPrograms() throws JSONException, ParseException, SQLException, IOException {

    }
}