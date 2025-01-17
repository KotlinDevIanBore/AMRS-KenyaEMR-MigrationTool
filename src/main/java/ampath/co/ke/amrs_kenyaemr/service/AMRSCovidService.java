package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSCovid;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSCovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrscovidservice")
public class AMRSCovidService {
    private final AMRSCovidRepository amrsCovidRepository;
    @Autowired
    public AMRSCovidService(AMRSCovidRepository amrsCovidRepository){
        this.amrsCovidRepository = amrsCovidRepository;
    }

    public List<AMRSCovid> findall(){
        return amrsCovidRepository.findAll();
    }


    public List<AMRSCovid> findByResponseCodeIsNull(){
        return amrsCovidRepository.findByResponseCodeIsNull();
    }

    public List<AMRSCovid> findByEncounterId(String encounterId){
        return amrsCovidRepository.findByEncounterId(encounterId);
    }

    public List<AMRSCovid> findByVisitId(String visitId){
        return amrsCovidRepository.findByVisitId(visitId);
    }

    public AMRSCovid save (AMRSCovid amrsCovid){
        return amrsCovidRepository.save(amrsCovid);
    }

    public List<AMRSCovid> findByEncounterConceptAndPatient(String encounterId, String conceptId, String patientId) {
        return amrsCovidRepository.findByEncounterIdAndConceptIdAndPatientId(encounterId, conceptId, patientId);
    }
}
