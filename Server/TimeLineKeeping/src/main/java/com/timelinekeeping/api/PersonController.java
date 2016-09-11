package com.timelinekeeping.api;

import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.FaceServiceMCS;
import com.timelinekeeping.service.PersonServiceMCS;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.UtilApps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lethanhtan on 9/9/16.
 */

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private Logger logger = Logger.getLogger(PersonController.class);

    @Autowired
    private PersonServiceMCS personService;

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@RequestParam("groupId") String groupId,
                               @RequestParam("name") String name,
                               @RequestParam("description") String description) {
        try {


//            BaseResponse response = personService.createPerson(groupId, name, description);
            BaseResponse response = new BaseResponse();

            logger.info("RESPONSE: " +this.getClass().toString() +": " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }


    @RequestMapping(value = {"/add_face_url"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detect(@RequestParam("groupId") String groupId,
                               @RequestParam("personId") String personId,
                               @RequestParam("url") String urlImg) {
        try {
//            BaseResponse response = personService.addFaceUrl(groupId, personId, urlImg);
            BaseResponse response = new BaseResponse();
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = {"/add_face_img"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detectimg(@RequestParam("groupId") String groupId,
                                  @RequestParam("personId") String personId,
                                  @RequestParam("img") MultipartFile img) {
        try {
            BaseResponse response = null;
            if (UtilApps.isImageFile(img.getInputStream())) {
//                response = personService.addFaceImg(groupId, personId, img.getInputStream());
                logger.info("RESPONSE: " + JsonUtil.toJson(response));
            }else {
                response = new BaseResponse();
                response.setSuccess(false);
                response.setMessage("File not image format.");
            }
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }



}

