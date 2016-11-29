package com.timelinekeeping.api.mcs;

import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.EmotionContentEntity;
import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.ConfigurationModel;
import com.timelinekeeping.model.NotificationCheckInModel;
import com.timelinekeeping.model.ReportCustomerEmotionQuery;
import com.timelinekeeping.repository.*;
import com.timelinekeeping.service.blackService.ConfigurationResponse;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.TimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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

    @Autowired
    private ConfigurationResponse configurationResponse;

    @RequestMapping("/list_account")
    public List<AccountModel> list(@RequestParam(value = "id", required = false) Long idDepartment) {

        List<AccountEntity> listEntity = accountRepo.findByDepartment(idDepartment);
        List<AccountModel> list = listEntity.stream().map(AccountModel::new).collect(Collectors.toList());
        return list;
    }

    @RequestMapping("/get_notify")
    public List<NotificationCheckInModel> getNotify(@RequestParam(value = "account_id", required = false) Long accountId) {
        List<NotificationEntity> lisNotify = notificationRepo.findByAccountReceiveByDate(accountId, null, null);
        List<NotificationCheckInModel> listCheckIn = lisNotify.stream().map(NotificationCheckInModel::new).collect(Collectors.toList());
        return listCheckIn;
    }

//    @RequestMapping("/get_employee_under_manager")
//    public List<AccountModel> getEmployee(@RequestParam(value = "manager_id", required = false) Long managerId) {
//        List<AccountEntity> lisNotify = accountRepo.findByManager(managerId);
//        List<AccountModel> listAccount = lisNotify.stream().map(AccountModel::new).collect(Collectors.toList());
//        return listAccount;
//    }

    @RequestMapping("/count_employee")
    public List<List<Long>> countEmployee(@RequestParam(value = "year", required = false) Integer year,
                                          @RequestParam(value = "month", required = false) Integer month) {

//        List<List<Long>> list = timekeepingRepo.countEmployeeTime(year, month);
        return null;
    }

    @RequestMapping("/count_Customer")
    public List<ReportCustomerEmotionQuery> countCustomer(@RequestParam(value = "year") Integer year,
                                                          @RequestParam(value = "month") Integer month,
                                                          @RequestParam(value = "day", defaultValue = IContanst.DEFAULT_INT) Integer day) {

//        List<List<Long>> list = timekeepingRepo.countEmployeeTime(year, month);


        Pair<Date, Date> datePair = null;
        if (day > 0) {
            datePair = TimeUtil.createDayBetween(new DateTime(year, month, day, 0, 0).toDate());
        } else {
            datePair = TimeUtil.createMonthBetween(new DateTime(year, month, 1, 0, 0).toDate());
        }
        //get report
        List<ReportCustomerEmotionQuery> listquery = customerServiceRepo.reportCustomerByMonth(datePair.getKey(), datePair.getValue());
        return listquery;
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

        return emotionContentRepo.getEmotionContent(first, second, third, age, gender, new PageRequest(IContanst.PAGE_PAGE_I, IContanst.PAGE_SIZE_CONTENT));
    }


    @RequestMapping("/push")
    public  Boolean pushNotification(@RequestParam(value = "time", defaultValue = "5") Long time){
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic NGEwMGZmMjItY2NkNy0xMWUzLTk5ZDUtMDAwYzI5NDBlNjJj");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"dbd7cdd6-9555-416b-bc08-21aa24164299\","
                    +   "\"include_player_ids\": [\"5ce02597-bf2b-4dda-b754-71a0f622a91f\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"ttl\": " + time +","
                    +   "\"contents\": {\"en\": \"English Message\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
        return true;
    }


    @RequestMapping("/config")
    public ConfigurationModel confic(){
        ConfigurationModel model = new ConfigurationModel();
        model.setSendSMS(configurationResponse.getSendSMS());
        model.setCheckinConfident(configurationResponse.getCheckinConfident());
        model.setEmailCompany(configurationResponse.getEmailCompany());
        model.setEmotionAccept(configurationResponse.getEmotionAccept());
        model.setEmotionAdvance(configurationResponse.getEmotionAdvance());
        model.setEmotionAdvanceConfidence(configurationResponse.getEmotionAdvanceConfidence());
        model.setEmotionAgeA(configurationResponse.getEmotionAgeA());
        model.setEmotionAgeB(configurationResponse.getEmotionAgeB());
        model.setTimeCheckinBegin(configurationResponse.getTimeCheckinBegin());
        model.setTimeCheckinEnd(configurationResponse.getTimeCheckinEnd());
        model.setTrainConfident(configurationResponse.getTrainConfident());
        return model;
    }
}
