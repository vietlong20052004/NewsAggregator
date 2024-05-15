package vietlong.app.utils;

import java.io.File;

public class FileExistChecker {
    public static boolean checkFileExist(String directoryPath, String fileName){
        File file = new File(directoryPath, fileName);

        return file.exists();
    }
}
