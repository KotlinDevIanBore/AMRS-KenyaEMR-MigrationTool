package ampath.co.ke.amrs_kenyaemr.methods;

import ampath.co.ke.amrs_kenyaemr.models.AMRSFormsMapper;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMappings;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.service.AMRSFormsMappingService;
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
    AMRSFormsMappingService amrsFormsMappingService;

    @Autowired
    AMRSPatientServices amrsPatientServices;


    public String translater(String amrsConceptId) {
        String kenyaEmrConceptUuid = "";
        List<AMRSMappings> amrsMappingsList = amrsMappingService.findByAmrsConceptID(amrsConceptId);

        if(amrsMappingsList.size() > 0) {
           if (amrsMappingsList.size() > 1)
           {System.out.println("Translated more than once : " + amrsConceptId);}

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

    public String formtranslater(String formid) {
        String kenyaEmrFormUuid = "";
        List<AMRSFormsMapper> amrsFormsMappers = amrsFormsMappingService.findByAmrsFormId(formid);

        if(amrsFormsMappers.size() > 0) {
            if (amrsFormsMappers.size() > 1)
            {System.out.println("Translated more than once form : " + formid);}

            kenyaEmrFormUuid = amrsFormsMappers.get(0).getKenyaemrFormUuid();
        }
        return kenyaEmrFormUuid;
    }


}
