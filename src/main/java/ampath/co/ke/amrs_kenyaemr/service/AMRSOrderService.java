package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.models.AMRSObs;
import ampath.co.ke.amrs_kenyaemr.models.AMRSOrders;
import ampath.co.ke.amrs_kenyaemr.models.AMRSPrograms;
import ampath.co.ke.amrs_kenyaemr.repositories.AMRSOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("amrsorderservice")
public class AMRSOrderService {
    private AMRSOrdersRepository amrsOrdersRepository;
    @Autowired
    public AMRSOrderService(AMRSOrdersRepository   amrsOrdersRepository ) {
        this.amrsOrdersRepository = amrsOrdersRepository;
    }
    public List<AMRSOrders> getAll() {
        return amrsOrdersRepository.findAll();
    }

    public List<AMRSOrders> findByUuid(String uuid) {
        return amrsOrdersRepository.findByUuid(uuid);
    }
    public AMRSOrders save(AMRSOrders dataset) {
        return amrsOrdersRepository.save(dataset);
    }

    public List<AMRSOrders> findByResponseCodeIsNull(){
       return amrsOrdersRepository.findByResponseCodeIsNull();
    }
}
