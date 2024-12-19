package ampath.co.ke.amrs_kenyaemr.service;


import ampath.co.ke.amrs_kenyaemr.models.AMRSDefaulterTracing;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSDefaulterTracingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("defaulterTracing")
public class AMRSDefaulterTracingService {


    private AMRSDefaulterTracingRepository amrsDefaulterTracingRepository;

    @Autowired
    public AMRSDefaulterTracingService(AMRSDefaulterTracingRepository amrsDefaulterTracingRepository) {
        this.amrsDefaulterTracingRepository = amrsDefaulterTracingRepository;
    }
    public List<AMRSDefaulterTracing> findByPatientIdAndEncounterIdAndConceptId(String pid, String eid, String cid) {
        return amrsDefaulterTracingRepository.findByPatientIdAndEncounterIdAndConceptId(pid,eid,cid);
    }

    public List<AMRSDefaulterTracing> findFirstByOrderByIdDesc() {
        return amrsDefaulterTracingRepository.findFirstByOrderByIdDesc();
    }

    public List<AMRSDefaulterTracing> findByResponseCodeIsNull() {
        return amrsDefaulterTracingRepository.findByResponseCodeIsNull();
    }

    public List<AMRSDefaulterTracing> findByPatientId(String pid) {
        return amrsDefaulterTracingRepository.findByPatientId(pid);
    }

    public AMRSDefaulterTracing save(AMRSDefaulterTracing dataset) {
        return amrsDefaulterTracingRepository.save(dataset);
    }
}
