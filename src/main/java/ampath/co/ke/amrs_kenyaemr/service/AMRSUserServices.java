package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSLocations;
import ampath.co.ke.amrs_kenyaemr.models.AMRSUsers;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSUsersRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("amrsService")
public class AMRSUserServices {
    Date nowDate = new Date();
    private AMRSUsersRepository amrsUsersRepository;
    @Autowired
    public AMRSUserServices(AMRSUsersRepository amrsUsersRepository) {
        this.amrsUsersRepository = amrsUsersRepository;
    }
    public List<AMRSUsers> getAll() {
        return amrsUsersRepository.findAll();
    }

    public List<AMRSUsers> getUserByLocation(String uuid,String location) {
        return amrsUsersRepository.findByUuidAndAmrsLocation(uuid, location);
    }
    public AMRSUsers save(AMRSUsers dataset) {
        return amrsUsersRepository.save(dataset);
    }


}
