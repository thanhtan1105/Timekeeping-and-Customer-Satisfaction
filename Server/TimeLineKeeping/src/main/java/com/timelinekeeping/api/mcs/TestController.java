package com.timelinekeeping.api.mcs;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.EmotionContentEntity;
import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.EmotionCustomerResponse;
import com.timelinekeeping.model.NotificationCheckInModel;
import com.timelinekeeping.repository.*;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private TimekeepingRepo timekeepingRepo;
    @Autowired
    private EmotionServiceImpl emotionService;


    @Autowired
    private CustomerServiceRepo customerServiceRepo;

    @Autowired
    private QuantityRepo quantityRepo;

    @Autowired
    private EmotionContentRepo emotionContentRepo;

    @RequestMapping("/list_account")
    public List<AccountModel> list(@RequestParam(value = "id", required = false) Long idDepartment) {

        List<AccountEntity> listEntity = accountRepo.findByDepartment(idDepartment);
        List<AccountModel> list = listEntity.stream().map(AccountModel::new).collect(Collectors.toList());
        return list;
    }

    @RequestMapping("/get_notify")
    public List<NotificationCheckInModel> getNotify(@RequestParam(value = "account_id", required = false) Long accountId) {
        List<NotificationEntity> lisNotify =  notificationRepo.findByAccountReceiveByDate(accountId);
        List<NotificationCheckInModel> listCheckIn = lisNotify.stream().map(NotificationCheckInModel::new).collect(Collectors.toList());
        return listCheckIn;
    }

    @RequestMapping("/get_employee_under_manager")
    public List<AccountModel> getEmployee(@RequestParam(value = "manager_id", required = false) Long managerId) {
        List<AccountEntity> lisNotify =  accountRepo.findByManager(managerId);
        List<AccountModel> listAccount = lisNotify.stream().map(AccountModel::new).collect(Collectors.toList());
        return listAccount;
    }

    @RequestMapping("/count_employee")
    public  List<List<Long>> countEmployee(@RequestParam(value = "year", required = false) Integer year,
                                                      @RequestParam(value = "month", required = false) Integer month) {

//        List<List<Long>> list = timekeepingRepo.countEmployeeTime(year, month);
        return null;
    }

    @RequestMapping("/count_Customer")
    public List<Object[]> countCustomer(@RequestParam(value = "year") Integer year,
                                        @RequestParam(value = "month") Integer month,
                                        @RequestParam(value = "day", defaultValue = IContanst.DEFAULT_INT) Integer day) {

//        List<List<Long>> list = timekeepingRepo.countEmployeeTime(year, month);
        return customerServiceRepo.reportCustomerByMonth(year, month, day);
    }
    @RequestMapping("/count_Customer_employee")
    public List<Object[]> countCustomerByEmployee(@RequestParam(value = "year") Integer year,
                                        @RequestParam(value = "month") Integer month,
                                        @RequestParam(value = "employee_id") Long employeeId) {

//        List<List<Long>> list = timekeepingRepo.countEmployeeTime(year, month);
        return customerServiceRepo.reportCustomerByMonthAndEmployee(year, month, employeeId);
    }

    @RequestMapping("/quantity_emotion")
    public List<String> quantityEmotion(@RequestParam(value = "value") Double value) {

        return quantityRepo.findQuantity(value);
    }


    @RequestMapping("/emotion_content")
    public Page<EmotionContentEntity> getEmotionContent(@RequestParam(value = "first") EEmotion first,
                                                        @RequestParam(value = "second", required = false) EEmotion second,
                                                        @RequestParam(value = "third", required = false) EEmotion third,
                                                        @RequestParam(value = "age", required = false) Double age,
                                                        @RequestParam(value = "gender", required = false) Gender gender) {

        return emotionContentRepo.getEmotionContent(first, second, third,age, gender, new PageRequest(IContanst.PAGE_PAGE_I, IContanst.PAGE_SIZE_CONTENT));
    }

}
