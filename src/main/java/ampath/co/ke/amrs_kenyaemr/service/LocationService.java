package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSLocations;
import ampath.co.ke.amrs_kenyaemr.repositories.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("locationService")
public class LocationService {
    Date nowDate = new Date();
    private LocationsRepository locationsRepository;
    @Autowired
    public LocationService(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }
    public List<AMRSLocations> getAll() {
        return locationsRepository.findAll();
    }
    public List<AMRSLocations> findByParentUUID(String puuid,Integer status) {
        return locationsRepository.findByPuuidAndStatus(puuid,status);
    }
    public AMRSLocations findByChildUUID(String cuuid) {
        return locationsRepository.findByCuuid(cuuid);
    }
    public AMRSLocations findByLocationId(String locationId) {
        return locationsRepository.findByChildlocationId(locationId);
    }
    public List<AMRSLocations> findByLocationIdIn(List<String> locationId) {
        return locationsRepository.findByChildlocationIdIn(locationId);
    }
    public List<AMRSLocations> findByParentLocation(String p,String c) {
        return locationsRepository.findByParentlocationIdOrChildlocationId(p,c);
    }
    public AMRSLocations save(AMRSLocations abstracts) {
        return locationsRepository.save(abstracts);
    }
    public List<?> getParents() {
        return locationsRepository.getParentLoactions();
    }

}

