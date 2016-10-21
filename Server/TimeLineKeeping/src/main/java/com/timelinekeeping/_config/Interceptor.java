package com.timelinekeeping._config;

import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.CustomerServiceModel;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by HienTQSE60896 on 10/10/2016.
 */
@Component
public class Interceptor extends HandlerInterceptorAdapter{

    @Autowired
    private EmotionServiceImpl emotionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor");
        String url = request.getRequestURI();
        if (url != null && url.contains(I_URI.API_EMOTION)){
            String accountID = request.getParameter(I_URI.PARAMETER_EMOTION_ACCOUNT_ID);
            if (ValidateUtil.isNotEmpty(accountID) && ValidateUtil.isNumeric(accountID)){
                Pair<String, String> customerSession = EmotionSession.getValue(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountID);
                if (customerSession == null || ValidateUtil.isEmpty(customerSession.getKey())) {
                    Long accountIdNum = ValidateUtil.parseNumber(accountID);
                    CustomerServiceModel customerServiceModel = emotionService.beginTransaction(accountIdNum);
                    EmotionSession.setValue(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountID , new Pair(customerServiceModel.getCustomerCode()));
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
