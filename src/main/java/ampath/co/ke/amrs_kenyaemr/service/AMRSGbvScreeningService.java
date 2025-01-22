package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSGBVScreening;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSGbvScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("amrsgbvscreeningservice")
public class AMRSGbvScreeningService {
    private final AMRSGbvScreeningRepository amrsGbvScreeningRepository;

    @Autowired
    public AMRSGbvScreeningService(AMRSGbvScreeningRepository amrsGbvScreeningRepository) {
        this.amrsGbvScreeningRepository = amrsGbvScreeningRepository;
    }

    public List<AMRSGBVScreening> findall() {
        return amrsGbvScreeningRepository.findAll();
    }


    public List<AMRSGBVScreening> findByResponseCodeIsNull() {
        return amrsGbvScreeningRepository.findByResponseCodeIsNull();
    }

    public List<AMRSGBVScreening> findByEncounterId(String encounterId) {
        return amrsGbvScreeningRepository.findByEncounterId(encounterId);
    }

    public List<AMRSGBVScreening> findByVisitId(String visitId) {
        return amrsGbvScreeningRepository.findByVisitId(visitId);
    }

    public AMRSGBVScreening save(AMRSGBVScreening amrsgbvScreening) {
        return amrsGbvScreeningRepository.save(amrsgbvScreening);
    }

    public List<AMRSGBVScreening> findByEncounterConceptAndPatient(String encounterId, String conceptId, String patientId) {
        return amrsGbvScreeningRepository.findByEncounterIdAndConceptIdAndPatientId(encounterId, conceptId, patientId);
    }
}
