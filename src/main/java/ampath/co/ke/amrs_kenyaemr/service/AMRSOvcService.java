package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSDefaulterTracing;
import ampath.co.ke.amrs_kenyaemr.models.AMRSOvc;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSDefaulterTracingRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSOvcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ovc")
public class AMRSOvcService {

    private AMRSOvcRepository amrsOvcRepository;

    @Autowired
    public AMRSOvcService(AMRSOvcRepository amrsOvcRepository) {
        this.amrsOvcRepository = amrsOvcRepository;
    }
    public List<AMRSOvc> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsOvcRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSOvc> findFirstByOrderByIdDesc() {
        return amrsOvcRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSOvc> findByResponseCodeIsNull() {
        return amrsOvcRepository.findByResponseCodeIsNull();
    }

    public List<AMRSOvc> findByPatientId(String pid) {
        return amrsOvcRepository.findByPatientId(pid);
    }

    public AMRSOvc save(AMRSOvc dataset) {
        return amrsOvcRepository.save(dataset);
    }
}
