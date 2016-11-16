package com.timelinekeeping.util;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.DepartmentModel;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
public class StoreFileUtils {


    private String path = AppConfigKeys.getInstance().getApplicationPropertyValue("file.store.path");


    public String storeFile(String nameFile, InputStream fileStore) {
        try {
            if (ValidateUtil.isEmpty(nameFile) || fileStore == null) {
                return null;
            }

            if (nameFile.indexOf(".") < 0) {
                nameFile += "." + IContanst.EXTENSION_FILE_IMAGE;
            }

            if (File.separatorChar != nameFile.charAt(0) && path.charAt(path.length() - 1) != File.separatorChar) {
                nameFile = File.separatorChar + nameFile;
            }

            String fileName = path + nameFile;
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            } else {
                if (file.exists()) {
                    file.delete();
                }
            }

            BufferedImage bufferedImage = ImageIO.read(fileStore);
            ImageIO.write(bufferedImage, IContanst.EXTENSION_FILE_IMAGE, file);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace());
            return null;
        }
    }

    public static void delete(String url) {
        if (url != null) {
            File file = new File(url);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static byte[] rotateImage(InputStream stream) {
        try {
            BufferedImage bufferedImage = ImageIO.read(stream);
            double rotationRequired = Math.toRadians(90);
            double locationX = bufferedImage.getHeight() / 2;
            double locationY = bufferedImage.getWidth() / 2;
            AffineTransform transform = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);

            AffineTransformOp affineTransformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            int maximun = (int) (Math.max(bufferedImage.getHeight(), bufferedImage.getWidth()) * 1.20);
            BufferedImage newImage = new BufferedImage(maximun, maximun, bufferedImage.getType());
            affineTransformOp.filter(bufferedImage, newImage);
//            g2d.drawImage(op.filter(image, null), drawLocationX, drawLocationY, null);


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(newImage, IContanst.EXTENSION_FILE_IMAGE, outputStream);
            byte[] byteImage = outputStream.toByteArray();
            return byteImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        try {
            AccountModel accountModel = new AccountModel();
            accountModel.setId(1l);
            accountModel.setUsername("quanghien");
            accountModel.setDepartment(new DepartmentModel(1l, "fpt_university"));
            FileInputStream fileInputStream = new FileInputStream("D:\\CP\\FILE\\1_humanresource\\4_quan\\1478255415074.jpg");

            String filename = ServiceUtils.createFolderTrain(accountModel);

            new StoreFileUtils().storeFile(filename, fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
