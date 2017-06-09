package cn.zhiao.baselib.utils.kit;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zhiao.baselib.R;
import cn.zhiao.baselib.app.BaseApplication;
import cn.zhiao.baselib.utils.log.XLog;

/**
 * Created by on 2016/11/28.
 * 关于Package相关信息获取工具类
 */

public class Kits {

    public static class Package {
        /**
         * 获取版本号
         *
         * @param context
         * @return
         */
        public static int getVersionCode(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionCode;
        }

        /**
         * 获取当前版本
         *
         * @param context
         * @return
         */
        public static String getVersionName(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionName;
        }

        /**
         * 安装App
         *
         * @param context
         * @param filePath
         * @return
         */
        public static boolean installNormal(Context context, String filePath) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            java.io.File file = new java.io.File(filePath);
            if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
                return false;
            }

            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * 卸载App
         *
         * @param context
         * @param packageName
         * @return
         */
        public static boolean uninstallNormal(Context context, String packageName) {
            if (packageName == null || packageName.length() == 0) {
                return false;
            }

            Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder().append("package:")
                    .append(packageName).toString()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }

        /**
         * 判断是否是系统App
         *
         * @param context
         * @param packageName 包名
         * @return
         */
        public static boolean isSystemApplication(Context context, String packageName) {
            if (context == null) {
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || packageName == null || packageName.length() == 0) {
                return false;
            }

            try {
                ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
                return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 判断某个包名是否运行在顶层
         *
         * @param context
         * @param packageName
         * @return
         */
        public static Boolean isTopActivity(Context context, String packageName) {
            if (context == null || TextUtils.isEmpty(packageName)) {
                return null;
            }

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo == null || tasksInfo.isEmpty()) {
                return null;
            }
            try {
                return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 获取Meta-Data
         *
         * @param context
         * @param key
         * @return
         */
        public static String getAppMetaData(Context context, String key) {
            if (context == null || TextUtils.isEmpty(key)) {
                return null;
            }
            String resultData = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return resultData;
        }

        /**
         * 判断当前应用是否运行在后台
         *
         * @param context
         * @return
         */
        public static boolean isApplicationInBackground(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
            return false;
        }
    }


    public static class Dimens {
        /**
         * The context.
         */
        private static Context mContext = BaseApplication.getInstance();

        public static float dpToPx(float dp) {
            return dp * mContext.getResources().getDisplayMetrics().density;
        }

        public static float pxToDp(float px) {
            return px / mContext.getResources().getDisplayMetrics().density;
        }

        public static int dpToPxInt(float dp) {
            return (int) (dpToPx(dp) + 0.5f);
        }

        public static int pxToDpCeilInt(float px) {
            return (int) (pxToDp(px) + 0.5f);
        }
        /**
         * dp转 px.
         *
         * @param value the value
         * @return the int
         */
        public static int dp2px(float value) {
            final float scale = mContext.getResources().getDisplayMetrics().densityDpi;
            return (int) (value * (scale / 160) + 0.5f);
        }

        /**
         * dp转 px.
         *
         * @param value   the value
         * @param context the context
         * @return the int
         */
        public static int dp2px(float value, Context context) {
            final float scale = context.getResources().getDisplayMetrics().densityDpi;
            return (int) (value * (scale / 160) + 0.5f);
        }

        /**
         * px转dp.
         *
         * @param value the value
         * @return the int
         */
        public static int px2dp(float value) {
            final float scale = mContext.getResources().getDisplayMetrics().densityDpi;
            return (int) ((value * 160) / scale + 0.5f);
        }

        /**
         * px转dp.
         *
         * @param value   the value
         * @param context the context
         * @return the int
         */
        public static int px2dp(float value, Context context) {
            final float scale = context.getResources().getDisplayMetrics().densityDpi;
            return (int) ((value * 160) / scale + 0.5f);
        }

        /**
         * sp转px.
         *
         * @param value the value
         * @return the int
         */
        public static int sp2px(float value) {
            Resources r;
            if (mContext == null) {
                r = Resources.getSystem();
            } else {
                r = mContext.getResources();
            }
            float spvalue = value * r.getDisplayMetrics().scaledDensity;
            return (int) (spvalue + 0.5f);
        }

        /**
         * sp转px.
         *
         * @param value   the value
         * @param context the context
         * @return the int
         */
        public static int sp2px(float value, Context context) {
            Resources r;
            if (context == null) {
                r = Resources.getSystem();
            } else {
                r = context.getResources();
            }
            float spvalue = value * r.getDisplayMetrics().scaledDensity;
            return (int) (spvalue + 0.5f);
        }

        /**
         * px转sp.
         *
         * @param value the value
         * @return the int
         */
        public static int px2sp(float value) {
            final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
            return (int) (value / scale + 0.5f);
        }

        /**
         * px转sp.
         *
         * @param value   the value
         * @param context the context
         * @return the int
         */
        public static int px2sp(float value, Context context) {
            final float scale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (value / scale + 0.5f);
        }

        /**
         *          * 将px值转换为sp值，保证文字大小不变
         *          * 
         *          * @param pxValue
         *          * @param fontScale
         *          *            （DisplayMetrics类中属性scaledDensity）
         *          * @return
         *          
         */
        public static int px2sp(Context context, float pxValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }
        /**
         * dip to px
         *
         * @param value
         * @return
         */
        public static int dipTopx(int value) {
            float m = Resources.getSystem().getDisplayMetrics().density;
            return Math.round(value * m);
        }

    }


    public static class Random {
        public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS = "0123456789";
        public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

        public static String getRandomNumbersAndLetters(int length) {
            return getRandom(NUMBERS_AND_LETTERS, length);
        }

        public static String getRandomNumbers(int length) {
            return getRandom(NUMBERS, length);
        }

        public static String getRandomLetters(int length) {
            return getRandom(LETTERS, length);
        }

        public static String getRandomCapitalLetters(int length) {
            return getRandom(CAPITAL_LETTERS, length);
        }

        public static String getRandomLowerCaseLetters(int length) {
            return getRandom(LOWER_CASE_LETTERS, length);
        }

        public static String getRandom(String source, int length) {
            return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
        }

        public static String getRandom(char[] sourceChar, int length) {
            if (sourceChar == null || sourceChar.length == 0 || length < 0) {
                return null;
            }

            StringBuilder str = new StringBuilder(length);
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < length; i++) {
                str.append(sourceChar[random.nextInt(sourceChar.length)]);
            }
            return str.toString();
        }

        public static int getRandom(int max) {
            return getRandom(0, max);
        }

        public static int getRandom(int min, int max) {
            if (min > max) {
                return 0;
            }
            if (min == max) {
                return min;
            }
            return min + new java.util.Random().nextInt(max - min);
        }
    }

    public static class File {
        public final static String FILE_EXTENSION_SEPARATOR = ".";

        /**
         * read file
         *
         * @param filePath
         * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static StringBuilder readFile(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            StringBuilder fileContent = new StringBuilder("");
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param content
         * @param append   is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if content is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, String content, boolean append) {
            if (TextUtils.isEmpty(content)) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                fileWriter.write(content);
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file
         *
         * @param filePath
         * @param contentList
         * @param append      is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if contentList is empty, true otherwise
         * @throws RuntimeException if an error occurs while operator FileWriter
         */
        public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
            if (contentList == null || contentList.isEmpty()) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                int i = 0;
                for (String line : contentList) {
                    if (i++ > 0) {
                        fileWriter.write("\r\n");
                    }
                    fileWriter.write(line);
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file, the string will be written to the begin of the file
         *
         * @param filePath
         * @param content
         * @return
         */
        public static boolean writeFile(String filePath, String content) {
            return writeFile(filePath, content, false);
        }

        /**
         * write file, the string list will be written to the begin of the file
         *
         * @param filePath
         * @param contentList
         * @return
         */
        public static boolean writeFile(String filePath, List<String> contentList) {
            return writeFile(filePath, contentList, false);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param filePath
         * @param stream
         * @return
         * @see {@link #writeFile(String, InputStream, boolean)}
         */
        public static boolean writeFile(String filePath, InputStream stream) {
            return writeFile(filePath, stream, false);
        }

        /**
         * write file
         *
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(String filePath, InputStream stream, boolean append) {
            return writeFile(filePath != null ? new java.io.File(filePath) : null, stream, append);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param file
         * @param stream
         * @return
         * @see {@link #writeFile(java.io.File, InputStream, boolean)}
         */
        public static boolean writeFile(java.io.File file, InputStream stream) {
            return writeFile(file, stream, false);
        }

        /**
         * write file
         *
         * @param file   the file to be opened for writing.
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean writeFile(java.io.File file, InputStream stream, boolean append) {
            OutputStream o = null;
            try {
                makeDirs(file.getAbsolutePath());
                o = new FileOutputStream(file, append);
                byte data[] = new byte[1024];
                int length = -1;
                while ((length = stream.read(data)) != -1) {
                    o.write(data, 0, length);
                }
                o.flush();
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(o);
                IO.close(stream);
            }
        }

        /**
         * move file
         *
         * @param sourceFilePath
         * @param destFilePath
         */
        public static void moveFile(String sourceFilePath, String destFilePath) {
            if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
                throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
            }
            moveFile(new java.io.File(sourceFilePath), new java.io.File(destFilePath));
        }

        /**
         * move file
         *
         * @param srcFile
         * @param destFile
         */
        public static void moveFile(java.io.File srcFile, java.io.File destFile) {
            boolean rename = srcFile.renameTo(destFile);
            if (!rename) {
                copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
                deleteFile(srcFile.getAbsolutePath());
            }
        }

        /**
         * copy file
         *
         * @param sourceFilePath
         * @param destFilePath
         * @return
         * @throws RuntimeException if an error occurs while operator FileOutputStream
         */
        public static boolean copyFile(String sourceFilePath, String destFilePath) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            }
            return writeFile(destFilePath, inputStream);
        }

        /**
         * read file to string list, a element of list is a line
         *
         * @param filePath
         * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
         * @return if file not exist, return null, else return content of file
         * @throws RuntimeException if an error occurs while operator BufferedReader
         */
        public static List<String> readFileToList(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            List<String> fileContent = new ArrayList<String>();
            if (file == null || !file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * get file name from path, not include suffix
         * <p/>
         * <pre>
         *      getFileNameWithoutExtension(null)               =   null
         *      getFileNameWithoutExtension("")                 =   ""
         *      getFileNameWithoutExtension("   ")              =   "   "
         *      getFileNameWithoutExtension("abc")              =   "abc"
         *      getFileNameWithoutExtension("a.mp3")            =   "a"
         *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
         *      getFileNameWithoutExtension("c:\\")              =   ""
         *      getFileNameWithoutExtension("c:\\a")             =   "a"
         *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
         *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
         *      getFileNameWithoutExtension("/home/admin")      =   "admin"
         *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
         * </pre>
         *
         * @param filePath
         * @return file name from path, not include suffix
         * @see
         */
        public static String getFileNameWithoutExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (filePosi == -1) {
                return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
            }
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1);
            }
            return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
        }

        /**
         * get file name from path, include suffix
         * <p/>
         * <pre>
         *      getFileName(null)               =   null
         *      getFileName("")                 =   ""
         *      getFileName("   ")              =   "   "
         *      getFileName("a.mp3")            =   "a.mp3"
         *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
         *      getFileName("abc")              =   "abc"
         *      getFileName("c:\\")              =   ""
         *      getFileName("c:\\a")             =   "a"
         *      getFileName("c:\\a.b")           =   "a.b"
         *      getFileName("c:a.txt\\a")        =   "a"
         *      getFileName("/home/admin")      =   "admin"
         *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
         * </pre>
         *
         * @param filePath
         * @return file name from path, include suffix
         */
        public static String getFileName(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
        }

        /**
         * get folder name from path
         * <p/>
         * <pre>
         *      getFolderName(null)               =   null
         *      getFolderName("")                 =   ""
         *      getFolderName("   ")              =   ""
         *      getFolderName("a.mp3")            =   ""
         *      getFolderName("a.b.rmvb")         =   ""
         *      getFolderName("abc")              =   ""
         *      getFolderName("c:\\")              =   "c:"
         *      getFolderName("c:\\a")             =   "c:"
         *      getFolderName("c:\\a.b")           =   "c:"
         *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
         *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
         *      getFolderName("/home/admin")      =   "/home"
         *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFolderName(String filePath) {

            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
        }

        /**
         * get suffix of file from path
         * <p/>
         * <pre>
         *      getFileExtension(null)               =   ""
         *      getFileExtension("")                 =   ""
         *      getFileExtension("   ")              =   "   "
         *      getFileExtension("a.mp3")            =   "mp3"
         *      getFileExtension("a.b.rmvb")         =   "rmvb"
         *      getFileExtension("abc")              =   ""
         *      getFileExtension("c:\\")              =   ""
         *      getFileExtension("c:\\a")             =   ""
         *      getFileExtension("c:\\a.b")           =   "b"
         *      getFileExtension("c:a.txt\\a")        =   ""
         *      getFileExtension("/home/admin")      =   ""
         *      getFileExtension("/home/admin/a.txt/b")  =   ""
         *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFileExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (extenPosi == -1) {
                return "";
            }
            return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
        }

        /**
         * Creates the directory named by the trailing filename of this file, including the complete directory path required
         * to create this directory. <br/>
         * <br/>
         * <ul>
         * <strong>Attentions:</strong>
         * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
         * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
         * </ul>
         *
         * @param filePath
         * @return true if the necessary directories have been created or the target directory already exists, false one of
         * the directories can not be created.
         * <ul>
         * <li>if {@link File#getFolderName(String)} return null, return false</li>
         * <li>if target directory already exists, return true</li>
         * </ul>
         */
        public static boolean makeDirs(String filePath) {
            String folderName = getFolderName(filePath);
            if (TextUtils.isEmpty(folderName)) {
                return false;
            }

            java.io.File folder = new java.io.File(folderName);
            return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
        }

        /**
         * @param filePath
         * @return
         * @see #makeDirs(String)
         */
        public static boolean makeFolders(String filePath) {
            return makeDirs(filePath);
        }

        /**
         * Indicates if this file represents a file on the underlying file system.
         *
         * @param filePath
         * @return
         */
        public static boolean isFileExist(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return false;
            }

            java.io.File file = new java.io.File(filePath);
            return (file.exists() && file.isFile());
        }

        /**
         * Indicates if this file represents a directory on the underlying file system.
         *
         * @param directoryPath
         * @return
         */
        public static boolean isFolderExist(String directoryPath) {
            if (TextUtils.isEmpty(directoryPath)) {
                return false;
            }

            java.io.File dire = new java.io.File(directoryPath);
            return (dire.exists() && dire.isDirectory());
        }

        /**
         * delete file or directory
         * <ul>
         * <li>if path is null or empty, return true</li>
         * <li>if path not exist, return true</li>
         * <li>if path exist, delete recursion. return true</li>
         * <ul>
         *
         * @param path
         * @return
         */
        public static boolean deleteFile(String path) {
            if (TextUtils.isEmpty(path)) {
                return true;
            }

            java.io.File file = new java.io.File(path);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                return file.delete();
            }
            if (!file.isDirectory()) {
                return false;
            }
            for (java.io.File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
            return file.delete();
        }

        /**
         * get file size
         * <ul>
         * <li>if path is null or empty, return -1</li>
         * <li>if path exist and it is a file, return file size, else return -1</li>
         * <ul>
         *
         * @param path
         * @return returns the length of this file in bytes. returns -1 if the file does not exist.
         */
        public static long getFileSize(String path) {
            if (TextUtils.isEmpty(path)) {
                return -1;
            }

            java.io.File file = new java.io.File(path);
            return (file.exists() && file.isFile() ? file.length() : -1);
        }

        /**
         * Try to return the absolute file path from the given Uri
         *
         * @param context
         * @param uri
         * @return the file path or null
         */
        public static String getRealFilePath(final Context context, final Uri uri) {
            if (null == uri) return null;
            final String scheme = uri.getScheme();
            String data = null;
            if (scheme == null)
                data = uri.getPath();
            else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                data = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
            return data;
        }
    }

    public static class IO {
        /**
         * 关闭流
         *
         * @param closeable
         */
        public static void close(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    public static class Date {
        private static SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
        private static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
        private static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
        private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        private static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
        private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

        /**
         * 年月日[2015-07-28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmd(long timeInMills) {
            return ymd.format(new java.util.Date(timeInMills));
        }

        /**
         * 年月日[2015.07.28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmdDot(long timeInMills) {
            return ymdDot.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhms(long timeInMills) {
            return ymdhms.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhmsS(long timeInMills) {
            return ymdhmss.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhm(long timeInMills) {
            return ymdhm.format(new java.util.Date(timeInMills));
        }

        public static String getHm(long timeInMills) {
            return hm.format(new java.util.Date(timeInMills));
        }

        public static String getMd(long timeInMills) {
            return md.format(new java.util.Date(timeInMills));
        }

        public static String getMdhm(long timeInMills) {
            return mdhm.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink(long timeInMills) {
            return mdhmLink.format(new java.util.Date(timeInMills));
        }

        public static String getM(long timeInMills) {
            return m.format(new java.util.Date(timeInMills));
        }

        public static String getD(long timeInMills) {
            return d.format(new java.util.Date(timeInMills));
        }

        /**
         * 是否是今天
         *
         * @param timeInMills
         * @return
         */
        public static boolean isToday(long timeInMills) {
            String dest = getYmd(timeInMills);
            String now = getYmd(Calendar.getInstance().getTimeInMillis());
            return dest.equals(now);
        }

        /**
         * 是否是同一天
         *
         * @param aMills
         * @param bMills
         * @return
         */
        public static boolean isSameDay(long aMills, long bMills) {
            String aDay = getYmd(aMills);
            String bDay = getYmd(bMills);
            return aDay.equals(bDay);
        }

        /**
         * 获取年份
         *
         * @param mills
         * @return
         */
        public static int getYear(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.YEAR);
        }

        /**
         * 获取月份
         *
         * @param mills
         * @return
         */
        public static int getMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.MONTH) + 1;
        }


        /**
         * 获取月份的天数
         *
         * @param mills
         * @return
         */
        public static int getDaysInMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            switch (month) {
                case Calendar.JANUARY:
                case Calendar.MARCH:
                case Calendar.MAY:
                case Calendar.JULY:
                case Calendar.AUGUST:
                case Calendar.OCTOBER:
                case Calendar.DECEMBER:
                    return 31;
                case Calendar.APRIL:
                case Calendar.JUNE:
                case Calendar.SEPTEMBER:
                case Calendar.NOVEMBER:
                    return 30;
                case Calendar.FEBRUARY:
                    return (year % 4 == 0) ? 29 : 28;
                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }


        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         *
         * @param mills
         * @return
         */
        public static int getWeek(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        /**
         * 获取当月第一天的时间（毫秒值）
         *
         * @param mills
         * @return
         */
        public static long getFirstOfMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            return calendar.getTimeInMillis();
        }

    }


    public static class NetWork {
        public static final String NETWORK_TYPE_WIFI = "wifi";
        public static final String NETWORK_TYPE_3G = "eg";
        public static final String NETWORK_TYPE_2G = "2g";
        public static final String NETWORK_TYPE_WAP = "wap";
        public static final String NETWORK_TYPE_UNKNOWN = "unknown";
        public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

        public static int getNetworkType(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
            return networkInfo == null ? -1 : networkInfo.getType();
        }

        public static String getNetworkTypeName(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo;
            String type = NETWORK_TYPE_DISCONNECT;
            if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
                return type;
            }

            if (networkInfo.isConnected()) {
                String typeName = networkInfo.getTypeName();
                if ("WIFI".equalsIgnoreCase(typeName)) {
                    type = NETWORK_TYPE_WIFI;
                } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                    String proxyHost = android.net.Proxy.getDefaultHost();
                    type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
                            : NETWORK_TYPE_WAP;
                } else {
                    type = NETWORK_TYPE_UNKNOWN;
                }
            }
            return type;
        }

        private static boolean isFastMobileNetwork(Context context) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return false;
            }

            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    return true;
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    return true;
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true;
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return false;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true;
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return false;
                default:
                    return false;
            }
        }
        /**
         * 判断当前网络是否可用
         *
         * @return
         */
        public static boolean isNetworkConnected(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if (mConnectivityManager == null) {
                    return false;
                }
                NetworkInfo[] infos = mConnectivityManager.getAllNetworkInfo();
                if (infos != null) {
                    for (NetworkInfo info : infos) {
                        if (info.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        /**
         * 判断当前wifi是否可用
         *
         * @return
         */
        public static boolean isWifiConnected(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable();
                }
            }
            return false;
        }

        /**
         * 判断MOBILE网络是否可用
         *
         * @return
         */
        public boolean isMobileConnected(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mMobileNetworkInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable();
                }
            }
            return false;
        }

        /**
         * 获取当前网络连接的类型信息 1 wifi 2 移动网络 -1 无网络
         *
         * @return
         */
        public int getConnectedType(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager
                        .getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                    return mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI ? 1
                            : 2;
                }
            }
            return -1;
        }
    }

    public static class Empty {

        public static boolean check(Object obj) {
            return obj == null;
        }

        public static boolean check(List list) {
            return list == null || list.isEmpty();
        }

        public static boolean check(Object[] array) {
            return array == null || array.length == 0;
        }

        public static boolean check(String str) {
            return str == null || "".equals(str);
        }


    }
    /**
     * 农历操作工具类
     */
    public static class LunarUtils {
        private int year;
        private int month;
        private int day;
        private boolean leap;
        final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        final static long[] lunarInfo = new long[]
                {0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
                        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
                        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
                        0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
                        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
                        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
                        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
                        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
                        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
                        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
                        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
                        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
                        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
                        0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
                        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};

        //====== 传回农历 y年的总天数
        private static int yearDays(int y) {
            int i, sum = 348;
            for (i = 0x8000; i > 0x8; i >>= 1) {
                if ((lunarInfo[y - 1900] & i) != 0) sum += 1;
            }
            return (sum + leapDays(y));
        }

        //====== 传回农历 y年闰月的天数
        private static int leapDays(int y) {
            if (leapMonth(y) != 0) {
                if ((lunarInfo[y - 1900] & 0x10000) != 0)
                    return 30;
                else
                    return 29;
            } else
                return 0;
        }

        //====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
        private static int leapMonth(int y) {
            return (int) (lunarInfo[y - 1900] & 0xf);
        }

        //====== 传回农历 y年m月的总天数
        private static int monthDays(int y, int m) {
            if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
                return 29;
            else
                return 30;
        }

        //====== 传回农历 y年的生肖
        final public String animalsYear() {
            final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
            return Animals[(year - 4) % 12];
        }

        //====== 传入 月日的offset 传回干支, 0=甲子
        private static String cyclicalm(int num) {
            final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
            final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
            return (Gan[num % 10] + Zhi[num % 12]);
        }

        //====== 传入 offset 传回干支, 0=甲子
        public String cyclical() {
            int num = year - 1900 + 36;
            return (cyclicalm(num));
        }

        public LunarUtils(java.util.Date date) {
            @SuppressWarnings("unused") int yearCyl, monCyl, dayCyl;
            int leapMonth = 0;
            java.util.Date baseDate = new java.util.Date();
            try {
                baseDate = chineseDateFormat.parse("1900-1-31");
            } catch (ParseException e) {
                e.printStackTrace(); //To change body of catch statement use Options | File Templates.
            }

            //求出和1900年1月31日相差的天数
            int offset = (int) ((date.getTime() - baseDate.getTime()) / 86400000L);
            dayCyl = offset + 40;
            monCyl = 14;

            //用offset减去每农历年的天数
            // 计算当天是农历第几天
            //i最终结果是农历的年份
            //offset是当年的第几天
            int iYear, daysOfYear = 0;
            for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
                daysOfYear = yearDays(iYear);
                offset -= daysOfYear;
                monCyl += 12;
            }
            if (offset < 0) {
                offset += daysOfYear;
                iYear--;
                monCyl -= 12;
            }
            //农历年份
            year = iYear;

            yearCyl = iYear - 1864;
            leapMonth = leapMonth(iYear); //闰哪个月,1-12
            leap = false;

            //用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
            int iMonth, daysOfMonth = 0;
            for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
                //闰月
                if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                    --iMonth;
                    leap = true;
                    daysOfMonth = leapDays(year);
                } else
                    daysOfMonth = monthDays(year, iMonth);

                offset -= daysOfMonth;
                //解除闰月
                if (leap && iMonth == (leapMonth + 1)) leap = false;
                if (!leap) monCyl++;
            }
            //offset为0时，并且刚才计算的月份是闰月，要校正
            if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
                if (leap) {
                    leap = false;
                } else {
                    leap = true;
                    --iMonth;
                    --monCyl;
                }
            }
            //offset小于0时，也要校正
            if (offset < 0) {
                offset += daysOfMonth;
                --iMonth;
                --monCyl;
            }
            month = iMonth;
            day = offset + 1;
        }

        public static String getChinaDayString(int day) {
            String chineseTen[] = {"初", "十", "廿", "三"};
            int n = day % 10 == 0 ? 9 : day % 10 - 1;
            if (day > 30)
                return "";
            if (day == 10)
                return "初十";
            else
                return chineseTen[day / 10] + chineseNumber[n];
        }

        public String toString() {
//        return year + "年" + (leap ? "闰" : "") + chineseNumber[month - 1] + "月" + getChinaDayString(day);
            return month + "-" + day;
        }
    }

    /**
     * 号码验证工具类
     * @author Ht
     *
     */
    public static class NumberUtils {
        /**
         * 中国移动拥有号码段为:139,138,137,136,135,134,159,158,157(3G),151,150,188(3G),187(3G
         * );13个号段 中国联通拥有号码段为:130,131,132,156(3G),186(3G),185(3G);6个号段
         * 中国电信拥有号码段为:133,153,189(3G),180(3G);4个号码段
         */
        private static String mRegMobileStr = "^1(([3][456789])|([5][01789])|([8][78]))[0-9]{8}$";
        private static String mRegMobile3GStr = "^((157)|(18[78]))[0-9]{8}$";
        private static String mRegUnicomStr = "^1(([3][012])|([5][6])|([8][56]))[0-9]{8}$";
        private static String mRegUnicom3GStr = "^((156)|(18[56]))[0-9]{8}$";
        private static String mRegTelecomStr = "^1(([3][3])|([5][3])|([8][09]))[0-9]{8}$";
        private static String mRegTelocom3GStr = "^(18[09])[0-9]{8}$";
        private static String mRegPhoneString = "^(?:13\\d|15\\d)\\d{5}(\\d{3}|\\*{3})$";

        private String mobile = "";
        private int facilitatorType = 0;
        private boolean isLawful = false;
        private boolean is3G = false;

        public NumberUtils(String mobile)
        {
            this.setMobile(mobile);
        }

        public void setMobile(String mobile) {
            if (mobile == null) {
                return;
            }
            /** */
            /** 第一步判断中国移动 */
            if (mobile.matches(NumberUtils.mRegMobileStr)) {
                this.mobile = mobile;
                this.setFacilitatorType(0);
                this.setLawful(true);
                if (mobile.matches(NumberUtils.mRegMobile3GStr)) {
                    this.setIs3G(true);
                }
            }
            /** */
            /** 第二步判断中国联通 */
            else if (mobile.matches(NumberUtils.mRegUnicomStr)) {
                this.mobile = mobile;
                this.setFacilitatorType(1);
                this.setLawful(true);
                if (mobile.matches(NumberUtils.mRegUnicom3GStr)) {
                    this.setIs3G(true);
                }
            }
            /** */
            /** 第三步判断中国电信 */
            else if (mobile.matches(NumberUtils.mRegTelecomStr)) {
                this.mobile = mobile;
                this.setFacilitatorType(2);
                this.setLawful(true);
                if (mobile.matches(NumberUtils.mRegTelocom3GStr)) {
                    this.setIs3G(true);
                }
            }
            /** */
            /** 第四步判断座机 */
            if (mobile.matches(NumberUtils.mRegPhoneString)) {
                this.mobile = mobile;
                this.setFacilitatorType(0);
                this.setLawful(true);
                if (mobile.matches(NumberUtils.mRegMobile3GStr)) {
                    this.setIs3G(true);
                }
            }
        }

        public String getMobile()
        {
            return mobile;
        }

        public int getFacilitatorType()
        {
            return facilitatorType;
        }

        public boolean isLawful()
        {
            return isLawful;
        }

        public boolean isIs3G()
        {
            return is3G;
        }

        private void setFacilitatorType(int facilitatorType) {
            this.facilitatorType = facilitatorType;
        }

        private void setLawful(boolean isLawful)
        {
            this.isLawful = isLawful;
        }

        private void setIs3G(boolean is3G)
        {
            this.is3G = is3G;
        }

    }

    public static class ScreenUtils{
        /**
         * The context.
         */
        private static Context mContext = BaseApplication.getInstance();


        /**
         * 获取屏幕宽度和高度，单位为px
         *
         * @param context
         * @return
         */
        public static Point getScreenMetrics (Context context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_screen = dm.widthPixels;
            int h_screen = dm.heightPixels;
            XLog.d("手机屏幕", "Screen---Width = " + w_screen + " Height = " + h_screen + " densityDpi = " + dm.densityDpi + " density = " + dm.density);
            return new Point(w_screen, h_screen);
        }

        /**
         * 获取屏幕宽度
         *
         * @return
         */
        public static int getWindowWidth() {
            WindowManager wm = (WindowManager) mContext.getSystemService(
                    Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();

            return width;
        }

        /**
         * 获取屏幕高度
         *
         * @return
         */
        public static int getWindowHeight() {
            WindowManager wm = (WindowManager) mContext.getSystemService(
                    Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();

            return height;
        }

        /**
         * 取导航栏高度
         * @return
         */
        public static int getNavigationBarHeight(Context ctx) {
            int result = 0;
            int resourceId = ctx.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = ctx.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        /**
         * 取状态栏高度
         * @return
         */
        public static int getStatusBarHeight(Context ctx) {
            int result = 0;
            int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = ctx.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
    }

    public static class Email{
        /**
         * 判断email格式是否正确
         *
         * @param email
         * @return
         */
        public static boolean isEmail(String email) {
            String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(email);
            return m.matches();
        }
        /**
         * check the email address is valid or not.
         *
         * @param email pass email id in string
         * @return true when its valid otherwise false
         */
        public static boolean isEmailIdValid(String email) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);

            if (matcher.matches())
                return true;
            else
                return false;
        }
    }


    public static class Language{
        /**
         * 根据系统语言判断是否为中国
         *
         * @return
         */
        public static boolean isZh() {
            Locale locale = BaseApplication.getInstance().getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            if (language.startsWith("zh")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static class Format{
        /**
         * 格式化价格
         *
         * @param price
         * @return .**
         */
        public static String formatPriceNumber(float price) {
            NumberFormat nf = new DecimalFormat("0.00");
            return nf.format(price);
        }



        /**
         * 格式化收益格式
         *
         * @param value：收益
         *
         * @return
         */
        public static String formatWithDigital(String value) {
            DecimalFormat df = new DecimalFormat("###,##0.00");
            return df.format(Double.parseDouble(value));
        }

        /**
         * 格式化收益格式
         *
         * @param value ：收益
         * @return
         */
        public static String formatWithString(String value) {
            if (value.equals("+0.00")) {
                return value;
            }
            if (value.equals("+0.0")) {
                return "+0.00";
            }
            if (value.equals("+0")) {
                return "+0.00";
            }
            String prefix = value.substring(0, 1);
            value = value.substring(1);
            DecimalFormat df = new DecimalFormat("###,##0.00");
            return prefix + df.format(Double.parseDouble(value));
        }
    }

    public static class Storage{
        /**
         * 参数 is_removable为false时得到的是内置sd卡路径，为true则为外置sd卡路径
         *
         * @param mContext
         * @param is_removale
         * @return
         */
        private static String getStoragePathFromRemovale(Context mContext, boolean is_removale) {

            StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
            Class<?> storageVolumeClazz = null;
            try {
                storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
                Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
                Method getPath = storageVolumeClazz.getMethod("getPath");
                Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
                Object result = getVolumeList.invoke(mStorageManager);
                final int length = Array.getLength(result);
                System.out.println("----length---" + length);
                for (int i = 0; i < length; i++) {
                    Object storageVolumeElement = Array.get(result, i);
                    String path = (String) getPath.invoke(storageVolumeElement);
                    boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                    if (is_removale == removable) {
                        return path;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static class Json{
        private static final String GSON_FORMAT = "yyyy-MM-dd HH:mm:ss";
        /**
         * 验证json合法性
         *
         * @param jsonContent
         * @return
         */
        public static boolean isJsonFormat(String jsonContent) {
            try {
                new JsonParser().parse(jsonContent);
                return true;
            } catch (JsonParseException e) {
                return false;
            }
        }
        /**
         * 获取gson对象
         *
         * @return
         */
        public static Gson getGson() {
            Gson mGson = null;
            if (mGson == null) {
                mGson = new GsonBuilder().setDateFormat(GSON_FORMAT).create(); // 创建gson对象，并设置日期格式
            }

            return mGson;
        }

    }
    public static class HardWare{
        /**
         * 调用震动器
         *
         * @param context      调用该方法的Context
         * @param milliseconds 震动的时长，单位是毫秒
         */
        public static void vibrate(final Context context, long milliseconds) {
            Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(milliseconds);
        }

        /**
         * 调用震动器
         *
         * @param context  调用该方法的Context
         * @param pattern  自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
         * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
         */
        public static void vibrate(final Context context, long[] pattern, boolean isRepeat) {
            Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(pattern, isRepeat ? 1 : -1);
        }

        /**
         * 播放音乐
         *
         * @param context
         */
        public static void playMusic(Context context) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.beep);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        }

        /**
         * 获取联系人电话
         *
         * @param cursor
         * @return
         */
        private String getContactPhone(Context context, Cursor cursor) {

            int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int phoneNum = cursor.getInt(phoneColumn);
            String phoneResult = "";
            //System.out.print(phoneNum);
            if (phoneNum > 0) {
                // 获得联系人的ID号
                int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String contactId = cursor.getString(idColumn);
                // 获得联系人的电话号码的cursor;
                Cursor phones = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);
                //int phoneCount = phones.getCount();
                //allPhoneNum = new ArrayList<String>(phoneCount);
                if (phones.moveToFirst()) {
                    // 遍历所有的电话号码
                    for (; !phones.isAfterLast(); phones.moveToNext()) {
                        int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                        int phone_type = phones.getInt(typeindex);
                        String phoneNumber = phones.getString(index);
                        switch (phone_type) {
                            case 2:
                                phoneResult = phoneNumber;
                                break;
                        }
                        //allPhoneNum.add(phoneNumber);
                    }
                    if (!phones.isClosed()) {
                        phones.close();
                    }
                }
            }
            return phoneResult;
        }


        public static boolean hasSoftKeys(Context ctx){
            boolean hasSoftwareKeys;
            WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
                Display d = manager.getDefaultDisplay();

                DisplayMetrics realDisplayMetrics = new DisplayMetrics();
                d.getRealMetrics(realDisplayMetrics);

                int realHeight = realDisplayMetrics.heightPixels;
                int realWidth = realDisplayMetrics.widthPixels;

                DisplayMetrics displayMetrics = new DisplayMetrics();
                d.getMetrics(displayMetrics);

                int displayHeight = displayMetrics.heightPixels;
                int displayWidth = displayMetrics.widthPixels;

                hasSoftwareKeys =  (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
            }else{
                boolean hasMenuKey = ViewConfiguration.get(ctx).hasPermanentMenuKey();
                boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
                hasSoftwareKeys = !hasMenuKey && !hasBackKey;
            }
            return hasSoftwareKeys;
        }
    }
    /**
     * 比较versionName true则需要更新
     *
     * @param localVersion
     * @param serverVersion
     * @return
     */
    public static boolean compareVersionName(String localVersion,
                                             String serverVersion) {
        if (serverVersion.equals("")) {
            return false;
        }

        String[] local = localVersion.split(Pattern.quote("."));
        String[] server = serverVersion.split(Pattern.quote("."));
        int localVersionFirst = Integer.parseInt(local[0]);
        int localVersionSecond = Integer.parseInt(local[1]);
        int localVersionThird = Integer.parseInt(local[2]);
        int serverVersionFirst = Integer.parseInt(server[0]);
        int serverVersionSecond = Integer.parseInt(server[1]);
        int serverVersionThird = Integer.parseInt(server[2]);
        if (serverVersionFirst > localVersionFirst
                || (serverVersionFirst == localVersionFirst && serverVersionSecond > localVersionSecond)
                || (serverVersionFirst == localVersionFirst
                && serverVersionSecond == localVersionSecond && serverVersionThird > localVersionThird)) {
            return true;
        }
        return false;

    }

}
