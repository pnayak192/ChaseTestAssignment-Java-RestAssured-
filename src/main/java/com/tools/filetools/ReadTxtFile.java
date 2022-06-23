package com.tools.filetools;

import com.tools.config.LoggerControler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadTxtFile {
    static final LoggerControler logger = LoggerControler.getLogger(ReadTxtFile.class);
    static String path = System.getProperty("user.dir") + File.separator + "src/test/resources/jsonRequestPaylod"
            + File.separator;

    public static String readTxtFile(String fileName) {
        String lineTxt = null;
        StringBuffer stringBuffer = null;
        try {
            File file = new File(path + fileName);
            //Check if the file exists
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                stringBuffer = new StringBuffer();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lineTxt);
                }
                logger.info(stringBuffer);
                read.close();
            } else {
                logger.error("The specified file could not be found");
            }
        } catch (Exception e) {
            logger.error("Error reading file content");
            e.printStackTrace();
        }
        return String.valueOf(stringBuffer);
    }

}
