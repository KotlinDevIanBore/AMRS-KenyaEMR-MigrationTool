package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSTbScreening;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSTbScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrstbscreeningservice")
public class AMRSTbScreeningService {
    private final AMRSTbScreeningRepository amrsTbScreeningRepository;
    @Autowired
    public AMRSTbScreeningService(AMRSTbScreeningRepository amrsTbScreeningRepository){
        this.amrsTbScreeningRepository = amrsTbScreeningRepository;
    }

    public List<AMRSTbScreening> findall(){
        return amrsTbScreeningRepository.findAll();
    }


    public List<AMRSTbScreening> findByResponseCodeIsNull(){
        return amrsTbScreeningRepository.findByResponseCodeIsNull();
    }

    public List<AMRSTbScreening> findByEncounterId(String encounterId){
        return amrsTbScreeningRepository.findByEncounterId(encounterId);
    }

    public List<AMRSTbScreening> findByVisitId(String visitId){
        return amrsTbScreeningRepository.findByVisitId(visitId);
    }

    public AMRSTbScreening save (AMRSTbScreening amrsOtzActivity){
        return amrsTbScreeningRepository.save(amrsOtzActivity);
    }

    public List<AMRSTbScreening> findByEncounterConceptAndPatient(String encounterId, String conceptId, String patientId) {
        return amrsTbScreeningRepository.findByEncounterIdAndConceptIdAndPatientId(encounterId, conceptId, patientId);
    }

}
