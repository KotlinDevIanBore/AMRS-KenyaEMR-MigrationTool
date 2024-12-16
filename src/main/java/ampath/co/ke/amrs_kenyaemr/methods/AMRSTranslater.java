package ampath.co.ke.amrs_kenyaemr.methods;

import ampath.co.ke.amrs_kenyaemr.models.AMRSFormsMapper;
import ampath.co.ke.amrs_kenyaemr.models.AMRSMappings;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPatients;
import ampath.co.ke.amrs_kenyaemr.models.AMRSVisits;
import ampath.co.ke.amrs_kenyaemr.service.AMRSFormsMappingService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSMappingService;
import ampath.co.ke.amrs_kenyaemr.service.AMRSPatientServices;
import ampath.co.ke.amrs_kenyaemr.service.AMRSVisitService;
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
    AMRSVisitService visitService;
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

    public String formtranslater(String formid, String encounterTypeId) {
        String kenyaEmrFormUuid = "";
//        List<AMRSFormsMapper> amrsFormsMappers = amrsFormsMappingService.findByAmrsFormId(formid);
        List<AMRSFormsMapper> amrsFormsMappers = amrsFormsMappingService.findByAmrsFormIdAndEncounterTypeId(formid, encounterTypeId);
        if(amrsFormsMappers.size() > 0) {
            if (amrsFormsMappers.size() > 1)
            {System.out.println("Translated more than once form : " + formid);}

            kenyaEmrFormUuid = amrsFormsMappers.get(0).getKenyaemrFormUuid();
        }
        return kenyaEmrFormUuid;
    }

    public String location(String armslocation) {
        String kenyaemrlocation = "3e6261cc-ad5e-4834-b85d-af8b42a133e4";
       /* List<AMRSMappings> amrsMappingsList = amrsMappingService.findByAmrsConceptID(amrsConceptId);

        if(amrsMappingsList.size() > 0) {
            if (amrsMappingsList.size() > 1)
            {System.out.println("Translated more than once : " + amrsConceptId);}

            kenyaEmrConceptUuid = amrsMappingsList.get(0).getKenyaemrConceptUuid();
        }
        */
        return kenyaemrlocation;
    }
    public String kenyaemrVisitUuid(String visitID) {
        String kenyaEmrVisitUuid = "";
        List<AMRSVisits> amrsVisits = visitService.findByVisitID(visitID);

        if(amrsVisits.size() > 0) {
            kenyaEmrVisitUuid = amrsVisits.get(0).getKenyaemrVisitUuid();
        }
        return kenyaEmrVisitUuid;
    }


}
