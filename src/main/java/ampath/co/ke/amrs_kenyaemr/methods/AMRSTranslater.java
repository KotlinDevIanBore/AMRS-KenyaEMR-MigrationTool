package ampath.co.ke.amrs_kenyaemr.methods;

import ampath.co.ke.amrs_kenyaemr.models.AMRSMappings;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.service.AMRSMappingService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AMRSTranslater {
    @Autowired
    AMRSMappingService amrsMappingService;

    @Autowired
    AMRSPatientServices amrsPatientServices;


    public String translater(String amrsConceptId) {
        String kenyaEmrConceptUuid = "";
        List<AMRSMappings> amrsMappingsList = amrsMappingService.findByAmrsConceptID(amrsConceptId);

        if(amrsMappingsList.size() > 0) {
            kenyaEmrConceptUuid = amrsMappingsList.get(0).getKenyaemrConceptUuid();
        }
        return kenyaEmrConceptUuid;
    }

    public String KenyaemrPatientUuid(String patientId) {
        String kenyaEmrPatientUuid = "";
        List<AMRSPatients> amrsPatients = amrsPatientServices.getByPatientID(patientId);

        if(amrsPatients.size() > 0) {
            kenyaEmrPatientUuid = amrsPatients.get(0).getKenyaemrpatientUUID();
        }
        return kenyaEmrPatientUuid;
    }


}
