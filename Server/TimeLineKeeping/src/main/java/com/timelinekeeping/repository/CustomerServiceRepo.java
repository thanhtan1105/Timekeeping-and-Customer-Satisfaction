package com.timelinekeeping.repository;

import com.timelinekeeping.entity.CustomerServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Repository
public interface CustomerServiceRepo extends JpaRepository<CustomerServiceEntity, Long> {

    @Query(value = "SELECT c FROM CustomerServiceEntity c WHERE c.CustomerCode = :customerCode")
    public CustomerServiceEntity findByCustomerCode(@Param("customerCode") String customerCode);

    @Query(value = "SELECT c FROM CustomerServiceEntity c WHERE c.createBy.id = :create_by AND c.status = 0")
    public List<CustomerServiceEntity> getLastCustomerById(@Param("create_by") Long employeeId);

    @Query(value = "SELECT create_by, count(customer_code) as cusotmer_code, avg(grade) as grade " +
            "from customer_service WHERE  year(create_time) = :year AND month(create_time)=:month " +
            "AND (:day = -1 or day(create_time) = :day) Group by create_by;", nativeQuery = true)
    public List<Object[]> reportCustomerByMonth(@Param("year") Integer year,
                                                @Param("month") Integer month,
                                                @Param("day") Integer day);

    @Query(value = "select day(create_time) as day, count(customer_code) as totalCustomer, avg(grade) as grade " +
            "from customer_service where create_by = :employee_id and year(create_time)=:year and month(create_time)=:month group by day(create_time)", nativeQuery = true)
    public List<Object[]> reportCustomerByMonthAndEmployee(@Param("year") Integer year,
                                                           @Param("month") Integer month,
                                                           @Param("employee_id") Long employeeId);
}