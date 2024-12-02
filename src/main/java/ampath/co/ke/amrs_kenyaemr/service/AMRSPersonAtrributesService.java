package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSIdentifiers;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatientAttributes;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSIdentifiersRepository;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSPersonAttributesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("personAttributesService")
public class AMRSPersonAtrributesService {
    Date nowDate = new Date();
    private AMRSPersonAttributesRepository amrsPersonAttributesRepository;
    @Autowired
    public AMRSPersonAtrributesService(AMRSPersonAttributesRepository amrsPersonAttributesRepository) {
        this.amrsPersonAttributesRepository = amrsPersonAttributesRepository;
    }
    public List<AMRSPatientAttributes> getAll() {
        return amrsPersonAttributesRepository.findAll();
    }

    public List<AMRSPatientAttributes> getByPatientID(String patient_id) {
        return amrsPersonAttributesRepository.findByPatientId(patient_id);
    }
    public List<AMRSPatientAttributes> getByPatientIDAndPType(String patient_id,String ptype) {
        return amrsPersonAttributesRepository.findByPatientIdAndPersonAttributeTypeId(patient_id,ptype);

    }

    public AMRSPatientAttributes save(AMRSPatientAttributes dataset) {
        return amrsPersonAttributesRepository.save(dataset);
    }
}
