package com.timelinekeeping.api;

import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.UtilApps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lethanhtan on 9/9/16.
 */

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private Logger logger = Logger.getLogger(PersonController.class);

    @Autowired
    private PersonServiceMCSImpl personService;

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@RequestParam("groupId") String groupId,
                               @RequestParam("name") String name,
                               @RequestParam("description") String description) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + this.getClass().getEnclosingMethod().getName());
            BaseResponse response = personService.createPerson(groupId, name, description);
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    @RequestMapping(value = {"/add_face_url"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detect(@RequestParam("groupId") String groupId,
                               @RequestParam("personId") String personId,
                               @RequestParam("url") String urlImg) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + this.getClass().getEnclosingMethod().getName());
            BaseResponse response = personService.addFaceUrl(groupId, personId, urlImg);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/add_face_img"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detectimg(@RequestParam("groupId") String groupId,
                                  @RequestParam("personId") String personId,
                                  @RequestParam("img") MultipartFile img) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + this.getClass().getEnclosingMethod().getName());
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
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }



}

