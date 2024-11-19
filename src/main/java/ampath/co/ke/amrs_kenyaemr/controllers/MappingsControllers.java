package ampath.co.ke.amrs_kenyaemr.controllers;

import ampath.co.ke.amrs_kenyaemr.models.AMRSConceptMapper;
import ampath.co.ke.amrs_kenyaemr.service.AMRSConceptMappingService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.sql.SQLException;

@Controller
@Transactional
@RequestMapping("/mappings")
public class MappingsControllers {
    @Autowired
    AMRSConceptMappingService amrsConceptMappingService;
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ModelAndView encounters(HttpSession session) throws IOException, SQLException {
       // String filePath = "data.csv"; // Path to your CSV file
        ClassPathResource resource = new ClassPathResource("concept_mapping.csv");
        Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        try{
        // Parse and process the CSV
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord());
        for (CSVRecord record : csvParser) {
            String amrsParent = record.get("amrs_parent");
            String amrsLabel = record.get("amrs_label");
            String amrsConcept = record.get("amrs_concept");
            String amrsConceptId = record.get("amrs_concept_id");
            String concept_type = record.get("concept_type");
            String CIEL = record.get("CIEL");
            String kemr_parent = record.get("kemr_parent");
            String kemr_label = record.get("kemr_label");
            String kemr_concept = record.get("kemr_concept");
            String kemr_concept_id = record.get("kemr_concept_id");

            // Process the record (e.g., print it)
           // System.out.println("ID: " + amrsParent );
                    //" Name: " + name + ", Age: " + age + ", Country: " + country);
            AMRSConceptMapper ac = new AMRSConceptMapper();
            ac.setAmrsParent(amrsParent);
            ac.setAmrsLabel(amrsLabel);
            ac.setAmrsConceptUUID(amrsConcept);
            ac.setAmrsConceptID(amrsConceptId);
            ac.setAmrsConceptType(concept_type);
            ac.setCIEL(CIEL);
            ac.setKenyaemrParent(kemr_parent);
            ac.setKenyaemrLabel(kemr_label);
            ac.setKenyaemrConceptUUID(kemr_concept);
            ac.setKenyaemrConceptID(kemr_concept_id);
            amrsConceptMappingService.save(ac);

        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }
}