package ampath.co.ke.amrs_kenyaemr.service;

import ampath.co.ke.amrs_kenyaemr.repositories.AMRSOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amrsorderservice")
public class AMRSOrderService {
    private AMRSOrdersRepository amrsOrdersRepository;
    @Autowired
    public AMRSOrderService(AMRSOrdersRepository   amrsOrdersRepository ) {
        this.amrsOrdersRepository = amrsOrdersRepository;
    }
}
