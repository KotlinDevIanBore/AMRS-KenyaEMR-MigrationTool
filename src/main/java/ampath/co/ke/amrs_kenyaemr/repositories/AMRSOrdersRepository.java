package ampath.co.ke.amrs_kenyaemr.repositories;
import ampath.co.ke.amrs_kenyaemr.models.AMRSOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AMRSOrdersRepository")
public interface AMRSOrdersRepository extends JpaRepository<AMRSOrders, Long> {

    /**
     *
     * @param uuid AMRS Order Uuid number
     * @return
     */
    List<AMRSOrders>  findByUuid(String uuid);

}
