package ampath.co.ke.amrs_kenyaemr.methods;

import ampath.co.ke.amrs_kenyaemr.models.AMRSMappings;
import ampath.co.ke.amrs_kenyaemr.service.AMRSMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AMRSConceptReader {
    @Autowired
    AMRSMappingService amrsMappingService;

    public String translater(String amrsConceptId) {
        String kenyaEmrConceptUuid = "";
        List<AMRSMappings> amrsMappingsList = amrsMappingService.findByAmrsConceptID(amrsConceptId);

        if(amrsMappingsList.size() > 0) {
            kenyaEmrConceptUuid = amrsMappingsList.get(0).getKenyaemrConceptUuid();
        }
        return kenyaEmrConceptUuid;
    }


}
