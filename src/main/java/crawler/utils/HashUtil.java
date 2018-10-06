package crawler.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//pixiv-app校验请求头生成
public class HashUtil {
    public static String[] gethash() throws NoSuchAlgorithmException {
        SimpleDateFormat simpleDateFormat;
        String fortmat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ";
        simpleDateFormat = new SimpleDateFormat(fortmat, Locale.US);
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String seed = time + "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c";
        byte[] digest = md5.digest(seed.getBytes());
        StringBuilder hash = new StringBuilder();
        for (int r3 = 0; r3 < digest.length; r3++) {
            hash.append(String.format("%02x", Byte.valueOf(digest[r3])));
        }
        return new String[]{time, hash.toString()};
    }
}
