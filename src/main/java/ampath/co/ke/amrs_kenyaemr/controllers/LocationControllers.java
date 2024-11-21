package ampath.co.ke.amrs_kenyaemr.controllers;

import ampath.co.ke.amrs_kenyaemr.models.AMRSLocations;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import ampath.co.ke.amrs_kenyaemr.service.*;
import ampath.co.ke.amrs_kenyaemr.tasks.MigrateCareData;
import ampath.co.ke.amrs_kenyaemr.tasks.MigrateRegistration;
import jakarta.servlet.http.HttpSession;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
@RequestMapping("/locations")
public class LocationControllers {

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
    private AMRSOrderService amrsOrderService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView Locations(){
      //  System.out.println("url " + server + " pass " + username + "Password " + password);
        ModelAndView modelAndView = new ModelAndView();
        List<AMRSLocations> locationsList = locationsService.getAll();

        List<AMRSLocations> distinctSortedLocations = locationsList.stream()
                .filter(location -> location.getParentlocationName() != null) // Exclude null parentlocationName
                .collect(Collectors.groupingBy(AMRSLocations::getParentlocationName))
                .values()
                .stream()
                .map(locationGroup -> locationGroup.get(0)) // Take the first entry for each parentlocationName
                .sorted((l1, l2) -> l1.getParentlocationName().compareToIgnoreCase(l2.getParentlocationName())) // Sort by parentlocationName
                .collect(Collectors.toList());


        /*int x = locationsService.getParents().size();
        List<?> listLocations = locationsService.getParents();
        List<AMRSLocations> amrsLocationsList = new ArrayList<>();
        for(int y=0;y<x;y++){

            List<AMRSLocations> amrsLocations = locationsService.findByParentUUID(listLocations.get(y).toString(),1);

           amrsLocationsList.add(amrsLocations.get(0));

            String uuid = listLocations.get(y).toString();
          System.out.println("Locations uuid "+ uuid);
        }
        */
       // System.out.println("Locations Sizes "+ locationsList.size() +" distinc "+distinctSortedLocations.size());
        modelAndView.addObject("parents",distinctSortedLocations);
        modelAndView.setViewName("index");
        return modelAndView;

    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ModelAndView encounters(HttpSession session) throws IOException, SQLException {
       // if (session.getAttribute("user") != null) {
            ModelAndView modelAndView = new ModelAndView();
            Connection con = DriverManager.getConnection(server, username, password);
            int x = 0;
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
           // ResultSet rs = stmt.executeQuery("SELECT location_id,location_name,uuid,parent_location,parent_uuid,parent_name,id FROM amrs_etl.location; ");
       // ResultSet rs = stmt.executeQuery("SELECT location_id,location_name,uuid,case when parent_location is null then location_id else parent_location end as parent_location  ,case when parent_location is null then uuid else parent_uuid end parent_uuid ,case when parent_name is null then location_name else parent_name end as location_name,id FROM amrs_etl.location;");
         ResultSet rs=stmt.executeQuery(" select location_id,name,description,parent_location" +
                 " from amrs.location where location_id in(select distinct(parent_location)\n" +
                 " from amrs.location l where l.parent_location is not null)\n" +
                 " order by name asc");
            rs.last();
            x = rs.getRow();
            rs.beforeFirst();
            while (rs.next()) {
                AMRSLocations locations = locationsService.findByChildUUID(rs.getString(3));
                if (locations == null) {
                    AMRSLocations ae = new AMRSLocations();
                    ae.setId(Integer.valueOf(rs.getString(7)));
                    ae.setChildlocationId(rs.getString(1));
                    ae.setChildlocationName(rs.getString(2));
                    ae.setCuuid(rs.getString(3));
                    ae.setParentlocationId(rs.getString(4));
                    ae.setPuuid(rs.getString(5));
                    ae.setParentlocationName(rs.getString(6));
                    ae.setStatus(1);
                    locationsService.save(ae);
                    System.out.println("test here");
                }else{
                    AMRSLocations ae = locations;
                    ae.setStatus(1);
                    locationsService.save(ae);
                }

            }
            List<AMRSLocations> afyastatErrorsList = locationsService.getAll();
            System.out.println("Size yake ndo hii " + afyastatErrorsList.size() );
            modelAndView.addObject("All Locations", afyastatErrorsList);
            modelAndView.setViewName("index");
        return modelAndView;

      /*  } else {
            return "Login is required";// ModelAndView("redirect:/auth/login");
        }
        */

    }
    @RequestMapping(value = "/modules/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public List<AMRSLocations> modules(@PathVariable(name = "uuid") String uuid){
        List<AMRSLocations> listLocations = locationsService.findByParentUUID(uuid,1);
        return listLocations;

    }
    @RequestMapping(value = "/migrate/{uuid}/{puuid}", method = RequestMethod.GET)
    @ResponseBody
    public String migrate(@PathVariable(name = "uuid") String locationIdd,
                          @PathVariable(name = "puuid") String parentUuid) throws SQLException, JSONException, ParseException, IOException {

        String locationId = "'"+locationIdd+"'";
       //  MigrateRegistration.users(server,username,password,locationId,parentUuid, amrsUserServices,OpenMRSURL,auth);
        //Patient Registration & identifiers
        // MigrateRegistration.patients(server,username,password,locationId,parentUuid,amrsPatientServices,amrsIdentifiersService,OpenMRSURL,auth);
        //Relationships
        //Programs
      //   MigrateCareData.programs(server,username,password,locationId,parentUuid,amrsProgramService,amrsPatientServices,OpenMRSURL,auth);
         //Enrollments
        // MigrateCareData.enrollments(server,username,password,locationId,parentUuid,amrsEnrollmentService,amrsPatientServices,OpenMRSURL,auth);
        // Orders
        //MigrateCareData.order(server, username, password, locationId, parentUuid, amrsOrderService, amrsPatientServices,amrsConceptMappingService, OpenMRSURL, auth);
        //Encounters
        //MigrateCareData.encounters(server,username,password,locationId,parentUuid,amrsEncounterService,amrsPatientServices, amrsConceptMappingService,OpenMRSURL,auth);

        //visits
         MigrateCareData.visits(server,username,password,locationId,parentUuid, amrsVisitService, amrsObsService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);
        //Triage
      MigrateCareData.triage(server,username,password,locationId,parentUuid, amrsTriageService, amrsPatientServices, amrsConceptMappingService, OpenMRSURL,auth);

        System.out.println("AMRS Locations "+locationId);

        return locationId;

    }
}
