package ampath.co.ke.amrs_kenyaemr.tasks;

import ampath.co.ke.amrs_kenyaemr.models.*;
import ampath.co.ke.amrs_kenyaemr.service.AMRSIdentifiersService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPersonAtrributesService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSUserServices;
import ampath.co.ke.amrs_kenyaemr.tasks.payloads.RegisterOpenMRSPayload;
import jakarta.persistence.Column;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class MigrateRegistration {

    @Autowired
    AMRSPatientServices amrsPatientServices;
    public static void users(String server, String username, String password, String locations, String parentUUID,AMRSUserServices amrsUserServices,String url,String auth) throws SQLException, JSONException, ParseException, IOException {
        String sql ="";
        List<AMRSUsers> amrsVisitsList = amrsUserServices.findFirstByOrderByIdDesc();
        if(amrsVisitsList.size()>0) {
            String visitId= amrsVisitsList.get(0).getUser_id();
             sql = "select \n" +
                    "                u.uuid,\n" +
                    "                u.user_id,\n" +
                    "                u.system_id,\n" +
                    "                u.username,\n" +
                    "                pn.given_name,\n" +
                    "                pn.family_name,\n" +
                    "                pn.middle_name,\n" +
                    "                p.gender,\n" +
                    "                p.birthdate,\n" +
                    "                pa.address1,\n" +
                    "                pa.county_district,\n" +
                    "                pa.address4,\n" +
                    "                pa.address5,\n" +
                    "                pa.address6,\n" +
                    "                p.dead,\n" +
                    "                p.birthdate_estimated \n" +
                    "                from amrs.encounter e \n" +
                    "                inner join amrs.users u on e.creator =u.user_id\n" +
                    "                inner join amrs.person p on p.person_id=u.person_id\n" +
                    "                inner join amrs.person_name pn on pn.person_id=p.person_id\n" +
                    "                inner join amrs.person_address pa on pa.person_id=p.person_id\n" +
                    "                where location_id in (" + locations + ") and u.user_id >"+ visitId +"  \n" +
                    "                group by u.user_id\n" +
                    "                order by u.user_id asc";
        }else{
            sql = "select \n" +
                    "                u.uuid,\n" +
                    "                u.user_id,\n" +
                    "                u.system_id,\n" +
                    "                u.username,\n" +
                    "                pn.given_name,\n" +
                    "                pn.family_name,\n" +
                    "                pn.middle_name,\n" +
                    "                p.gender,\n" +
                    "                p.birthdate,\n" +
                    "                pa.address1,\n" +
                    "                pa.county_district,\n" +
                    "                pa.address4,\n" +
                    "                pa.address5,\n" +
                    "                pa.address6,\n" +
                    "                p.dead,\n" +
                    "                p.birthdate_estimated \n" +
                    "                from amrs.encounter e \n" +
                    "                inner join amrs.users u on e.creator =u.user_id\n" +
                    "                inner join amrs.person p on p.person_id=u.person_id\n" +
                    "                inner join amrs.person_name pn on pn.person_id=p.person_id\n" +
                    "                inner join amrs.person_address pa on pa.person_id=p.person_id\n" +
                    "                where location_id in (" + locations + ") \n" +
                    "                group by u.user_id\n" +
                    "                order by u.user_id asc";
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
            System.out.println("User id "+ rs.getString(1));
            List<AMRSUsers> afyastatErrors = amrsUserServices.getUserByLocation(rs.getString(1),parentUUID);
            if (afyastatErrors.size()==0) {
                AMRSUsers ae = new AMRSUsers();
                ae.setUuid(rs.getString(1));
                ae.setUser_id(rs.getString(2));
                ae.setSystem_id(rs.getString(3));
                ae.setUsername(rs.getString(4));
                ae.setGiven_name(rs.getString(5));
                ae.setFamily_name(rs.getString(6));
                ae.setMiddle_name(rs.getString(7));
                ae.setGender(rs.getString(8));
                ae.setBirthdate(rs.getString(9));
                ae.setAddress1(rs.getString(10));
                ae.setCounty_district(rs.getString(11));
                ae.setAddress4(rs.getString(12));
                ae.setAddress5(rs.getString(13));
                ae.setAddress6(rs.getString(14));
                ae.setDead(rs.getString(15));
                ae.setBirthdate_estimate(rs.getString(16));
                ae.setAmrsLocation(parentUUID);
                amrsUserServices.save(ae);

                //Migate user
                RegisterOpenMRSPayload.users(ae,amrsUserServices,url,auth);


            }
//con.close();
        }
    }
    //Patients
    public static void patients (String server, String username, String password, String locations, String parentUUID, AMRSPatientServices amrsPatientServices, AMRSIdentifiersService amrsIdentifiersService,AMRSPersonAtrributesService amrsPersonAtrributesService,String url,String auth) throws SQLException, JSONException, ParseException, IOException {
      List<AMRSPatients> patientsListt = amrsPatientServices.findFirstByOrderByIdDesc();
        String sql="";
     if(patientsListt.size()==0){
         sql = "select  \n" +
                 "                   p.uuid, \n" +
                 "                   p.person_id, \n" +
                 "                   pn.given_name, \n" +
                 "                   pn.family_name, \n" +
                 "                   pn.middle_name, \n" +
                 "                   p.gender, \n" +
                 "                   p.birthdate, \n" +
                 "                   pa.address1, \n" +
                 "                   pa.county_district, \n" +
                 "                   pa.address4, \n" +
                 "                   pa.address5, \n" +
                 "                   pa.address6, \n" +
                 "                   p.dead, \n" +
                 "                   p.cause_of_death,\n" +
                 "                   p.death_date,\n" +
                 "                   case \n" +
                 "  when   p.cause_of_death = 16 then 142412\n" +
                 "  when   p.cause_of_death = 43 then 114100\n" +
                 "  when   p.cause_of_death = 58 then 112141\n" +
                 "  when   p.cause_of_death = 60 then 115835\n" +
                 "  when   p.cause_of_death = 84 then 84\n" +
                 "  when   p.cause_of_death = 86 then 86\n" +
                 "  when   p.cause_of_death = 102 then 102\n" +
                 "  when   p.cause_of_death = 123 then 116128\n" +
                 "  when   p.cause_of_death = 148 then 112234\n" +
                 "  when   p.cause_of_death = 507 then 507\n" +
                 "  when   p.cause_of_death = 903 then 117399\n" +
                 "  when   p.cause_of_death = 1067 then 1067\n" +
                 "  when   p.cause_of_death = 1107 then 1107\n" +
                 "  when   p.cause_of_death = 1571 then 125561\n" +
                 "  when   p.cause_of_death = 1593 then 159\n" +
                 "  when   p.cause_of_death = 2375 then 137296\n" +
                 "  when   p.cause_of_death = 5041 then 5041\n" +
                 "  when   p.cause_of_death = 5547 then 119975\n" +
                 "  when   p.cause_of_death = 5622 then 5622\n" +
                 "  when   p.cause_of_death = 6483 then 139444\n" +
                 "  when   p.cause_of_death = 7257 then 134612\n" +
                 "  when   p.cause_of_death = 7971 then 145717\n" +
                 "  when   p.cause_of_death = 10366 then 133814\n" +
                 "  when   p.cause_of_death = 12038 then 155762 \n" +
                 "  end as kmr_concept_id,\n" +
                 "                   case\n" +
                 "  when   p.cause_of_death = 16 then '142412AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 43 then '114100AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 58 then '112141AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 60 then '115835AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 84 then '84AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 86 then '86AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 102 then '102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 123 then '116128AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 148 then '112234AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 507 then '507AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 903 then '117399AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1067 then '1067AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1107 then '1107AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1571 then '125561AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1593 then '159AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 2375 then '137296AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 5041 then '5041AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 5547 then '119975AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 5622 then '5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 6483 then '139444AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 7257 then '134612AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 7971 then '145717AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 10366 then '133814AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' --\n" +
                 "  when   p.cause_of_death = 12038 then '155762AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  end as kmr_concept_uuid ,    \n" +
                 "                   p.birthdate_estimated, \n" +
                 "                   p.voided, \n" +
                 "                   l.location_id, \n" +
                 "                   l.name location_name, \n" +
                 "                   pa.address1 county,  \n" +
                 "                   pa.address2 sub_county, \n" +
                 "                   pa.city_village, \n" +
                 "                   pa.state_province,  \n" +
                 "                   pa.county_district, \n" +
                 "                   pa.address3 landmark \n" +
                 "                   from amrs.encounter e  \n" +
                 "                   inner join amrs.patient pt on e.patient_id =pt.patient_id \n" +
                 "                   inner join amrs.person p on p.person_id=pt.patient_id \n" +
                 "                   inner join amrs.person_name pn on pn.person_id=p.person_id \n" +
                 "                   inner join amrs.person_address pa on pa.person_id=p.person_id and pa.preferred=1 \n" +
                 "                   inner join amrs.location l on e.location_id=l.location_id \n" +
                 "                   where l.uuid in ( "+ locations +" )  and p.person_id in (1171851,\n" +
                 "1180830,\n" +
                 "1167167,\n" +
                 "1187468,\n" +
                 "1210762,\n" +
                 "1211677,\n" +
                 "1205381,\n" +
                 "1178556,\n" +
                 "1177079,\n" +
                 "1177856,\n" +
                 "1198638,\n" +
                 "1211883,\n" +
                 "1191727,\n" +
                 "1191862,\n" +
                 "1176796,\n" +
                 "1210716,\n" +
                 "1212684,\n" +
                 "1223669,\n" +
                 "1182300,\n" +
                 "1188506,\n" +
                 "765546,\n" +
                 "1187467,\n" +
                 "1207817,\n" +
                 "1212603,\n" +
                 "1216267,\n" +
                 "1225187,\n" +
                 "1140933,\n" +
                 "1185368,\n" +
                 "1177985,\n" +
                 "1189238,\n" +
                 "1191232,\n" +
                 "1199830,\n" +
                 "1170791,\n" +
                 "1174464,\n" +
                 "1206185,\n" +
                 "1176830,\n" +
                 "1182705,\n" +
                 "1209127,\n" +
                 "1177104,\n" +
                 "1177467,\n" +
                 "1184252,\n" +
                 "1192270,\n" +
                 "1204250,\n" +
                 "1212823,\n" +
                 "1193179,\n" +
                 "1177270,\n" +
                 "1191005,\n" +
                 "1198509,\n" +
                 "1167355,\n" +
                 "1178369,\n" +
                 "1184092,\n" +
                 "1189326,\n" +
                 "1191369,\n" +
                 "1203354,\n" +
                 "1203531,\n" +
                 "1209140,\n" +
                 "1226657,\n" +
                 "1172517,\n" +
                 "1186701,\n" +
                 "1195760,\n" +
                 "1169969,\n" +
                 "1178748,\n" +
                 "1206865,\n" +
                 "1215595,\n" +
                 "1180696,\n" +
                 "1186078,\n" +
                 "1195200,\n" +
                 "1177704,\n" +
                 "1212906,\n" +
                 "1209159,\n" +
                 "1202124,\n" +
                 "1205268,\n" +
                 "1208071,\n" +
                 "1211667,\n" +
                 "1212173,\n" +
                 "1220342,\n" +
                 "1176467,\n" +
                 "1178456,\n" +
                 "1176379,\n" +
                 "1177933,\n" +
                 "1179157,\n" +
                 "1185422,\n" +
                 "1198117,\n" +
                 "1203972,\n" +
                 "1211635,\n" +
                 "1185861,\n" +
                 "1188709,\n" +
                 "1192374,\n" +
                 "1194786,\n" +
                 "1200228,\n" +
                 "1212351,\n" +
                 "1222698,\n" +
                 "198492,\n" +
                 "1178019,\n" +
                 "1187425,\n" +
                 "1176820,\n" +
                 "1170115,\n" +
                 "1175708,\n" +
                 "1188938,\n" +
                 "827082) -- and p.person_id >  + pid +    \n" +
                 "                   group by pt.patient_id \n" +
                 "                   order by e.patient_id ";
        // System.out.println("SQL ID is "+ sql);
     }else {
         String pid = patientsListt.get(0).getPersonId();
         System.out.println("Person ID is "+ pid);
         System.out.println("SQL ID is "+ sql);

      /*   sql = "select  \n" +
                 "                   p.uuid, \n" +
                 "                   p.person_id, \n" +
                 "                   pn.given_name, \n" +
                 "                   pn.family_name, \n" +
                 "                   pn.middle_name, \n" +
                 "                   p.gender, \n" +
                 "                   p.birthdate, \n" +
                 "                   pa.address1, \n" +
                 "                   pa.county_district, \n" +
                 "                   pa.address4, \n" +
                 "                   pa.address5, \n" +
                 "                   pa.address6, \n" +
                 "                   p.dead, \n" +
                 "                   p.cause_of_death,\n" +
                 "                   p.death_date,\n" +
                 "                   case \n" +
                 "  when   p.cause_of_death = 16 then 142412\n" +
                 "  when   p.cause_of_death = 43 then 114100\n" +
                 "  when   p.cause_of_death = 58 then 112141\n" +
                 "  when   p.cause_of_death = 60 then 115835\n" +
                 "  when   p.cause_of_death = 84 then 84\n" +
                 "  when   p.cause_of_death = 86 then 86\n" +
                 "  when   p.cause_of_death = 102 then 102\n" +
                 "  when   p.cause_of_death = 123 then 116128\n" +
                 "  when   p.cause_of_death = 148 then 112234\n" +
                 "  when   p.cause_of_death = 507 then 507\n" +
                 "  when   p.cause_of_death = 903 then 117399\n" +
                 "  when   p.cause_of_death = 1067 then 1067\n" +
                 "  when   p.cause_of_death = 1107 then 1107\n" +
                 "  -- when   p.cause_of_death = 1548 then --\n" +
                 "  when   p.cause_of_death = 1571 then 125561\n" +
                 "  -- when   p.cause_of_death = 1572 then --\n" +
                 "  when   p.cause_of_death = 1593 then 159\n" +
                 "  when   p.cause_of_death = 2375 then 137296\n" +
                 "  when   p.cause_of_death = 5041 then 5041\n" +
                 "  when   p.cause_of_death = 5547 then 119975\n" +
                 "  when   p.cause_of_death = 5622 then 5622\n" +
                 "  when   p.cause_of_death = 6483 then 139444\n" +
                 "  when   p.cause_of_death = 7257 then 134612\n" +
                 "  when   p.cause_of_death = 7971 then 145717\n" +
                 "  -- when   p.cause_of_death = 10363 then \n" +
                 "  -- when   p.cause_of_death = 10364 then \n" +
                 "  -- when   p.cause_of_death = 10365 then \n" +
                 "  when   p.cause_of_death = 10366 then 133814\n" +
                 "  -- when   p.cause_of_death = 10367 then \n" +
                 "  -- when   p.cause_of_death = 10654 then\n" +
                 "  when   p.cause_of_death = 12038 then 155762 \n" +
                 "  end as kmr_concept_id,\n" +
                 "                   case\n" +
                 "  when   p.cause_of_death = 16 then '142412AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 43 then '114100AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 58 then '112141AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 60 then '115835AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 84 then '84AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 86 then '86AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 102 then '102AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 123 then '116128AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 148 then '112234AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 507 then '507AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 903 then '117399AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1067 then '1067AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1107 then '1107AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1571 then '125561AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 1593 then '159AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 2375 then '137296AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 5041 then '5041AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 5547 then '119975AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 5622 then '5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 6483 then '139444AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 7257 then '134612AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 7971 then '145717AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  when   p.cause_of_death = 10366 then '133814AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' --\n" +
                 "  when   p.cause_of_death = 12038 then '155762AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'\n" +
                 "  end as kmr_concept_uuid ,    \n" +
                 "                   p.birthdate_estimated, \n" +
                 "                   p.voided, \n" +
                 "                   l.location_id, \n" +
                 "                   l.name location_name, \n" +
                 "                   pa.address1 county,  \n" +
                 "                   pa.address2 sub_county, \n" +
                 "                   pa.city_village, \n" +
                 "                   pa.state_province,  \n" +
                 "                   pa.county_district, \n" +
                 "                   pa.address3 landmark \n" +
                 "                   from amrs.encounter e  \n" +
                 "                   inner join amrs.patient pt on e.patient_id =pt.patient_id \n" +
                 "                   inner join amrs.person p on p.person_id=pt.patient_id \n" +
                 "                   inner join amrs.person_name pn on pn.person_id=p.person_id \n" +
                 "                   inner join amrs.person_address pa on pa.person_id=p.person_id and pa.preferred=1 \n" +
                 "                   inner join amrs.location l on e.location_id=l.location_id \n" +
                 "where l.uuid in (" + locations + ") and ppp.person_id >"+ pid +"\n" +
                 "group by pt.patient_id\n" +
                 "order by e.patient_id asc limit 10";
         */
     }

        System.out.println("locations " + locations + " parentUUID " + parentUUID);
        //System.out.println("SQL "+ sql);
        Connection con = DriverManager.getConnection(server, username, password);
        int x = 0;
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(sql);
        rs.last();
        x = rs.getRow();
        rs.beforeFirst();
        while (rs.next()) {

            System.out.println("User id "+ rs.getString(1));
            List<AMRSPatients> patientsList = amrsPatientServices.getPatientByLocation(rs.getString("person_id"),parentUUID);
            if (patientsList.size()==0) {

                String person_id=rs.getString("person_id");
                AMRSPatients ae = new AMRSPatients();
                ae.setUuid(rs.getString("uuid"));
                ae.setPersonId(rs.getString("person_id"));
                ae.setGiven_name(rs.getString("given_name"));
                ae.setFamily_name(rs.getString("family_name"));
                ae.setMiddle_name(rs.getString("middle_name"));
                ae.setGender(rs.getString("gender"));
                ae.setBirthdate(rs.getString("birthdate"));
                ae.setAddress1(rs.getString("address1"));//county
                ae.setCounty_district(rs.getString("county_district"));
                ae.setAddress4(rs.getString("address4")); //
                ae.setAddress5(rs.getString("address5")); //
                ae.setAddress6(rs.getString("address6")); //
                ae.setCounty(rs.getString("county"));
                ae.setSubcounty(rs.getString("sub_county"));
                ae.setLandmark(rs.getString("landmark"));
                ae.setVillage(rs.getString("city_village"));
                ae.setDead(rs.getString("dead"));
                ae.setCauseOfDead(rs.getString("cause_of_death"));
                ae.setBirthdate_estimated(rs.getString("birthdate_estimated"));
                ae.setKenyaemrCauseOfDead(rs.getString("kmr_concept_id"));
                ae.setKenyaemrCauseOfDeadUuid(rs.getString("kmr_concept_uuid"));
                ae.setVoided(rs.getString("voided"));
                ae.setLocation_id(rs.getString("location_id"));
                ae.setCityVillage(rs.getString("city_village"));
                ae.setAddress2(rs.getString("Sub_county"));//Sub_county;
                ae.setAddress3(rs.getString("landmark"));//landmark;
                ae.setCounty_district(rs.getString("county_district"));
                ae.setStateProvince(rs.getString("state_province"));
                ae.setParentlocationuuid(parentUUID);
                amrsPatientServices.save(ae);

                String identifiersSQl = "select pi.patient_id,\n" +
                        "pit.uuid identifer_uuid," +
                        "pi.identifier,pi.preferred,pi.voided,pi.location_id\n" +
                        "from amrs.patient_identifier pi\n" +
                        "join amrs.patient_identifier_type pit on pi.identifier_type=pit.patient_identifier_type_id\n" +
                        "inner join amrs.person p on p.person_id =pi.patient_id\n" +
                        "where pi.voided=0 and pi.patient_id="+ rs.getString("person_id") +"\n" +
                        "order by pi.patient_id desc";
                //identifers
                Statement stmtID = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet rsID = stmtID.executeQuery(identifiersSQl);
                rsID.last();
                x = rsID.getRow();
                rsID.beforeFirst();
                while (rsID.next()) {
                    List<AMRSIdentifiers> identifiers = amrsIdentifiersService.getPatientByLocation(rsID.getString("patient_id"), parentUUID);
                    if (identifiers.size() == 0) {
                        AMRSIdentifiers iden = new AMRSIdentifiers();
                        String pref ="";
                        if(rsID.getString(4).equals("0")){
                            pref="false";
                        }else{
                            pref="true";
                        }
                        String identifer="";
                        if(rsID.getString(2).equals("f2d6ff1a-8440-4d35-a150-1d4b5a930c5e")){
                            identifer = rsID.getString(3).replace("-", "");

                        }else{
                            identifer = rsID.getString(3);
                        }
                        iden.setPatientid(rsID.getString(1));
                        iden.setUuid(rsID.getString(2));
                        iden.setIdentifier(identifer);
                        iden.setPreferred(pref);
                        iden.setVoided(rsID.getString(5));
                        iden.setLocation(rsID.getString(6));
                        iden.setKenyaemr_uuid(Mappers.identifers(rsID.getString(2)));
                        iden.setParentlocationuuid(parentUUID);

                        amrsIdentifiersService.save(iden);

                    }
                    //end of identifers
                    //Person Attributes
                    String pattreibutesSQl=
                                    "select  pa.person_id,\n" +
                                    "pt.person_attribute_type_id,\n" +
                                    "pt.name,\n" +
                                    "pa.value,\n" +
                                    "case when pt.person_attribute_type_id=10 then 'b2c38640-2603-4629-aebd-3b54f33f1e3a' -- Telephone\n" +
                                    "when pt.person_attribute_type_id =12 then '830bef6d-b01f-449d-9f8d-ac0fede8dbd3'\n" +
                                    "when pt.person_attribute_type_id =25 then '342a1d39-c541-4b29-8818-930916f4c2dc' -- contact\n" +
                                    "when pt.person_attribute_type_id =0 then '7cf22bec-d90a-46ad-9f48-035952261294' -- Kin Address\n" +
                                    " when pt.person_attribute_type_id =0 then '94614350-84c8-41e0-ac29-86bc107069be' -- alternative phone\n" +
                                    "else null end as kenyaemruuid\n" +
                                    " from amrs.person_attribute pa\n" +
                                    " inner join amrs.person_attribute_type pt on pa.person_attribute_type_id = pt.person_attribute_type_id\n" +
                                    "where pa.person_id  in ("+ rs.getString("person_id")  +") and pa.voided=0";
                    Statement stmtPA = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    ResultSet rsPA = stmtPA.executeQuery(pattreibutesSQl);
                    rsPA.last();
                    x = rsPA.getRow();
                    rsPA.beforeFirst();
                    while (rsPA.next()) {
                        List<AMRSPatientAttributes> patientAttributes = amrsPersonAtrributesService.getByPatientIDAndPType(rsPA.getString("person_id"),rsPA.getString("person_attribute_type_id"));
                        if(patientAttributes.size()==0){
                            AMRSPatientAttributes apt = new AMRSPatientAttributes();
                            apt.setPatientId(rsPA.getString("person_id"));
                            apt.setPersonAttributeTypeId(rsPA.getString("person_attribute_type_id"));
                            apt.setPersonAttributeName(rsPA.getString("name"));
                            apt.setPersonAttributeValue(rsPA.getString("value"));
                            apt.setKenyaemrAttributeUuid(rsPA.getString("kenyaemruuid"));
                            amrsPersonAtrributesService.save(apt);
                        }

                    }
                        //End of Person Attributes

                }
                //Migate Patient
               RegisterOpenMRSPayload.patient(ae,amrsPatientServices,amrsIdentifiersService,amrsPersonAtrributesService ,url,auth);

            }

        }
        con.close();
    }
}
