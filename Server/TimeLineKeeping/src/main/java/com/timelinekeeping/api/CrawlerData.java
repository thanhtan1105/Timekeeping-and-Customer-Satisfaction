package com.timelinekeeping.api;

import com.timelinekeeping.api.mcs.CharUtils;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.modelMCS.PersonInformation;
import com.timelinekeeping.util.HTTPClientUtil;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ServiceUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.jboss.netty.util.CharsetUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by lethanhtan on 11/18/16.
 */
public class CrawlerData {

//    public static void main(String[] args) {
//        try {
//            int i = 30;
//            FileWriter writer = new FileWriter("/Users/lethanhtan/Desktop/LoziData.txt");
//            BufferedWriter bufferedWriter = new BufferedWriter(writer);
//            for (int j = 0; j < 24; j++) {
//                while (true) {
//                    String character = CharUtils.ASCIIToChar(j + 97) + "";
//                    String url = "http://latte.lozi.vn/v1.2/search/users?q=" + character + "&lat=-1&lng=-1&r=" + i + "&skip=" + i;
//                    HttpClient httpclient = HttpClients.createDefault();
//                    HttpRequestBase request = new HttpGet(url);
//                    HttpResponse response = null;
//
//                    response = httpclient.execute(request);
//                    HttpEntity entity = response.getEntity();
//                    String dataResponse = ServiceUtils.getDataResponse(entity);
//                    System.out.println(dataResponse);
//
//                    bufferedWriter.write("\n" + dataResponse);
//
//
//                    i = i + 30;
//                    if (i == 3000) {
//                        break;
//                    }
//                }
//            }
//            bufferedWriter.close();
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

}
