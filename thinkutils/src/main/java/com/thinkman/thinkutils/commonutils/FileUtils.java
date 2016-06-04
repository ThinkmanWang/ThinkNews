package com.thinkman.thinkutils.commonutils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skins on 2016/1/21.
 */

public class FileUtils {

    public FileUtils() {
    }

    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if(!file.exists()) {
            return false;
        } else {
            return file.isFile()?deleteFile(fileName):deleteDirectory(fileName);
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if(file.isFile() && file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteDirectory(String dir) {
        if(!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }

        File dirFile = new File(dir);
        if(dirFile.exists() && dirFile.isDirectory()) {
            boolean flag = true;
            File[] files = dirFile.listFiles();

            for(int i = 0; i < files.length; ++i) {
                if(files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if(!flag) {
                        break;
                    }
                } else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if(!flag) {
                        break;
                    }
                }
            }

            if(!flag) {
                return false;
            } else if(dirFile.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int e = 0;
            boolean byteread = false;
            File oldfile = new File(oldPath);
            if(oldfile.exists()) {
                FileInputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];

                int byteread1;
                while((byteread1 = inStream.read(buffer)) != -1) {
                    e += byteread1;
                    fs.write(buffer, 0, byteread1);
                }

                inStream.close();
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    public static void newFolder(String folderPath) {
        try {
            String e = folderPath.toString();
            File myFilePath = new File(e);
            if(!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void newFile(String filePathAndName, String fileContent) {
        try {
            String e = filePathAndName.toString();
            File myFilePath = new File(e);
            if(!myFilePath.exists()) {
                myFilePath.createNewFile();
            }

            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            myFile.println(fileContent);
            resultFile.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static void delFile(String filePathAndName) {
        try {
            String e = filePathAndName.toString();
            File myDelFile = new File(e);
            myDelFile.delete();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void delFileAsync(String filePathAndName) {
        (new FileUtils.FileDeleter()).execute(new String[]{filePathAndName});
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String e = folderPath.toString();
            File myFilePath = new File(e);
            myFilePath.delete();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void delAllFile(String path) {
        File file = new File(path);
        if(file.exists()) {
            if(file.isDirectory()) {
                String[] tempList = file.list();
                File temp = null;

                for(int i = 0; i < tempList.length; ++i) {
                    if(path.endsWith(File.separator)) {
                        temp = new File(path + tempList[i]);
                    } else {
                        temp = new File(path + File.separator + tempList[i]);
                    }

                    if(temp.isFile()) {
                        temp.delete();
                    }

                    if(temp.isDirectory()) {
                        delAllFile(path + "/ " + tempList[i]);
                        delFolder(path + "/ " + tempList[i]);
                    }
                }

            }
        }
    }

    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs();
            File e = new File(oldPath);
            String[] file = e.list();
            File temp = null;

            for(int i = 0; i < file.length; ++i) {
                if(oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if(temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/ " + temp.getName().toString());
                    byte[] b = new byte[5120];

                    int len;
                    while((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

                if(temp.isDirectory()) {
                    copyFolder(oldPath + "/ " + file[i], newPath + "/ " + file[i]);
                }
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }

    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public static List<File> searchBySuffix(File f, List<File> fileList, String... suffix) {
        if(null == fileList) {
            fileList = new ArrayList();
        }

        if(f.isDirectory()) {
            File[] childs = f.listFiles();
            if(childs != null) {
                for(int i = 0; i < childs.length; ++i) {
                    if(childs[i].isDirectory()) {
                        searchBySuffix(childs[i], (List)fileList, suffix);
                    } else {
                        for(int j = 0; j < suffix.length; ++j) {
                            if(childs[i].getName().endsWith(suffix[j])) {
                                ((List)fileList).add(childs[i]);
                            }
                        }
                    }
                }
            }
        }

        return (List)fileList;
    }

    public static void writeString(File file, String content) {
        FileOutputStream os = null;

        try {
            byte[] e = content.getBytes("UTF-8");
            os = new FileOutputStream(file);
            os.write(e);
            os.flush();
        } catch (FileNotFoundException var14) {
            var14.printStackTrace();
        } catch (IOException var15) {
            var15.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }

                os = null;
            }

        }

    }

    public static void writeObject(Object obj, String objPath) {
        File file = new File(objPath);
        FileOutputStream os = null;
        ObjectOutputStream oos = null;

        try {
            os = new FileOutputStream(file);
            oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
        } catch (FileNotFoundException var16) {
            var16.printStackTrace();
        } catch (IOException var17) {
            var17.printStackTrace();
        } finally {
            try {
                if(oos != null) {
                    oos.close();
                }

                if(os != null) {
                    os.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

    }

    public static Object readObject(File file) {
        if(file.exists() && file.length() != 0L) {
            Object object = null;
            FileInputStream is = null;
            ObjectInputStream ois = null;

            try {
                is = new FileInputStream(file);
                ois = new ObjectInputStream(is);
                object = ois.readObject();
            } catch (FileNotFoundException var19) {
                var19.printStackTrace();
            } catch (StreamCorruptedException var20) {
                var20.printStackTrace();
            } catch (IOException var21) {
                var21.printStackTrace();
            } catch (ClassNotFoundException var22) {
                var22.printStackTrace();
            } finally {
                try {
                    if(ois != null) {
                        ois.close();
                    }

                    if(is != null) {
                        is.close();
                    }
                } catch (IOException var18) {
                    var18.printStackTrace();
                }

            }

            return object;
        } else {
            return null;
        }
    }

    public static String convertString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        String readline = "";

        try {
            BufferedReader ie = new BufferedReader(new InputStreamReader(is));

            while(ie.ready()) {
                readline = ie.readLine();
                sb.append(readline);
            }

            ie.close();
        } catch (IOException var4) {
            //Log.d("FileUtils", "converts failed.");
        }

        return sb.toString();
    }

    public static String formatSize(long length) {
        float SIZE_BT = 1024.0F;
        float SIZE_KB = SIZE_BT * 1024.0F;
        float SIZE_MB = SIZE_KB * 1024.0F;
        float SIZE_GB = SIZE_MB * 1024.0F;
        float SIZE_TB = SIZE_GB * 1024.0F;
        byte SACLE = 2;
        if(length >= 0L && (float)length < SIZE_BT) {
            return (double)Math.round((float)(length * 10L)) / 10.0D + "B";
        } else if((float)length >= SIZE_BT && (float)length < SIZE_KB) {
            return (double)Math.round((float)length / SIZE_BT * 10.0F) / 10.0D + "KB";
        } else if((float)length >= SIZE_KB && (float)length < SIZE_MB) {
            return (double)Math.round((float)length / SIZE_KB * 10.0F) / 10.0D + "MB";
        } else {
            BigDecimal longs;
            BigDecimal sizeMB;
            String result;
            if((float)length >= SIZE_MB && (float)length < SIZE_GB) {
                longs = new BigDecimal(Double.valueOf(length + "").toString());
                sizeMB = new BigDecimal(Double.valueOf(SIZE_MB + "").toString());
                result = longs.divide(sizeMB, SACLE, 4).toString();
                return result + "GB";
            } else {
                longs = new BigDecimal(Double.valueOf(length + "").toString());
                sizeMB = new BigDecimal(Double.valueOf(SIZE_GB + "").toString());
                result = longs.divide(sizeMB, SACLE, 4).toString();
                return result + "TB";
            }
        }
    }

    @SuppressLint({"DefaultLocale"})
    public static long formatSize(String sizeStr) {
        if(sizeStr != null && sizeStr.trim().length() > 0) {
            String unit = sizeStr.replaceAll("([1-9]+[0-9]*|0)(\\.[\\d]+)?", "");
            String size = sizeStr.substring(0, sizeStr.indexOf(unit));
            if(TextUtils.isEmpty(size)) {
                return -1L;
            } else {
                float s = Float.parseFloat(size);
                if("B".equals(unit.toLowerCase())) {
                    return (long)s;
                } else if(!"KB".equals(unit.toLowerCase()) && !"K".equals(unit.toLowerCase())) {
                    if(!"MB".equals(unit.toLowerCase()) && !"M".equals(unit.toLowerCase())) {
                        if(!"GB".equals(unit.toLowerCase()) && !"G".equals(unit.toLowerCase())) {
                            if(!"TB".equals(unit.toLowerCase()) && !"T".equals(unit.toLowerCase())) {
                                return -1L;
                            } else {
                                return (long)(s * 1024.0F * 1024.0F * 1024.0F * 1024.0F);
                            }
                        } else {
                            return (long)(s * 1024.0F * 1024.0F * 1024.0F);
                        }
                    } else {
                        return (long)(s * 1024.0F * 1024.0F);
                    }
                } else {
                    return (long)(s * 1024.0F);
                }
            }
        } else {
            return -1L;
        }
    }

    public static long getFolderSize(File file) throws Exception {
        long size = 0L;

        try {
            File[] e = file.listFiles();

            for(int i = 0; i < e.length; ++i) {
                if(e[i].isDirectory()) {
                    size += getFolderSize(e[i]);
                } else {
                    size += e[i].length();
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return size;
    }

    static class FileDeleter extends AsyncTask<String, Void, Void> {
        FileDeleter() {
        }

        protected Void doInBackground(String... params) {
            FileUtils.delFile(params[0]);
            return null;
        }
    }
}
