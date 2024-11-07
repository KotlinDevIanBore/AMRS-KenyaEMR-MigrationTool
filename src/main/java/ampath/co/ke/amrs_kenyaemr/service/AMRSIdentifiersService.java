package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSIdentifiers;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSIdentifiersRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPatientsRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("identifierService")
public class AMRSIdentifiersService {
    Date nowDate = new Date();
    private AMRSIdentifiersRepository amrsIdentifiersRepository;
    @Autowired
    public AMRSIdentifiersService(AMRSIdentifiersRepository amrsIdentifiersRepository) {
        this.amrsIdentifiersRepository = amrsIdentifiersRepository;
    }
    public List<AMRSIdentifiers> getAll() {
        return amrsIdentifiersRepository.findAll();
    }

    public List<AMRSIdentifiers> getByPatientID(String patient_id) {
        return amrsIdentifiersRepository.findByPatientid(patient_id);
    }

    public List<AMRSIdentifiers> getPatientByLocation(String uuid, String location) {
        return amrsIdentifiersRepository.findByUuidAndParentlocationuuid(uuid, location);
    }
    public AMRSIdentifiers save(AMRSIdentifiers dataset) {
        return amrsIdentifiersRepository.save(dataset);
    }
}
