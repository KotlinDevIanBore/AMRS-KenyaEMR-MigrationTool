package ampath.co.ke.amrs_kenyaemr.methods;

import ampath.co.ke.amrs_kenyaemr.models.AMRSLocations;
import ampath.co.ke.amrs_kenyaemr.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Controller
public class AMRSLocation {
    //@Value("${default_amrs_Location}")
    public  String locationId="404"; // 404 Kapsoya // 339 Mosoriot
   // @Value("${default_kenyaemr_Location}")
    public  String KenyaEMRlocationUuid="47d77697-8675-4966-a2ab-45f72b9113ad"; // 47d77697-8675-4966-a2ab-45f72b9113ad Kapsoya // 3e6261cc-ad5e-4834-b85d-af8b42a133e4 Mosoriot
   // public static int locationId = amrsLocation;
   // public static String KenyaEMRlocationUuid = KenyaEMRLocation //Kapsoya

    public  String getKenyaEMRLocationUuid() {
        return KenyaEMRlocationUuid;
    }
    public  String getLocationsId(LocationService locationService) {

        String location = locationId;
        System.out.println("Location Id is "+locationId +" Kenyaemr "+ KenyaEMRlocationUuid);
        List<String> locationName = new java.util.ArrayList<>(List.of());
        List<Integer> numbers = new java.util.ArrayList<>(List.of());
        AMRSLocations amrsLocations = locationService.findByLocationId(location);
        String parentlocationId = amrsLocations.getParentlocationId();
       // System.out.println("Location Id is "+locationId + " Parent locations is "+parentlocationId);
        List<AMRSLocations> amrsLocationsList = locationService.findByParentLocation(parentlocationId,parentlocationId);
        //System.out.println("Location Id is "+locationId + " Parent locations is "+parentlocationId+" Results "+amrsLocationsList.size());

        for(int i = 0; i < amrsLocationsList.size(); i++) {
            numbers.add(Integer.parseInt(amrsLocationsList.get(i).getChildlocationId()));
            locationName.add(amrsLocationsList.get(i).getChildlocationName());
        }
        String pist = numbers.toString();
        String result = pist.substring(1, pist.length() - 1);
        System.out.println("Location Id is "+locationId + " Parent locations is "+parentlocationId+" Location Names "+locationName);

        return result;
    }
    public  String getLocationsUuid(LocationService locationService) {
        String location = String.valueOf(locationId);
        List<String> numbers = new java.util.ArrayList<>(List.of());
        List<String> locationName = new java.util.ArrayList<>(List.of());
        AMRSLocations amrsLocations = locationService.findByLocationId(location);
        String parentlocationId = amrsLocations.getParentlocationId();
        List<AMRSLocations> amrsLocationsList = locationService.findByParentLocation(parentlocationId,parentlocationId);
        for(int i = 0; i < amrsLocationsList.size(); i++) {
            numbers.add("'"+amrsLocationsList.get(i).getCuuid()+"'");
            locationName.add(amrsLocationsList.get(i).getChildlocationName());
        }
        String pist = numbers.toString();
        String result = pist.substring(1, pist.length() - 1);
        System.out.println("Location Id is "+locationId + " Parent locations is "+parentlocationId+" Location Names "+locationName);
        return result;
    }

    public  Map<String, String> getLocations(LocationService locationService) {
    String location = String.valueOf(locationId);
    List<String> locationName = new java.util.ArrayList<>(List.of());
    List<Integer> numbers = new java.util.ArrayList<>(List.of());
    List<String> uuids = new java.util.ArrayList<>(List.of());

    AMRSLocations amrsLocations = locationService.findByLocationId(location);
    String parentlocationId = amrsLocations.getParentlocationId();
    List<AMRSLocations> amrsLocationsList = locationService.findByParentLocation(parentlocationId, parentlocationId);

    for (int i = 0; i < amrsLocationsList.size(); i++) {
        numbers.add(Integer.parseInt(amrsLocationsList.get(i).getChildlocationId()));
        uuids.add("'" + amrsLocationsList.get(i).getCuuid() + "'");
        locationName.add(amrsLocationsList.get(i).getChildlocationName());
    }

    String idList = numbers.toString();
    String uuidList = uuids.toString();

    String resultIds = idList.substring(1, idList.length() - 1);
    String resultUuids = uuidList.substring(1, uuidList.length() - 1);

    System.out.println("Location Id is " + locationId + " Parent locations is " + parentlocationId + " Location Names " + locationName);

    Map<String, String> result = new HashMap<>();
    result.put("ids", resultIds);
    result.put("uuids", resultUuids);

    return result;
}
}
