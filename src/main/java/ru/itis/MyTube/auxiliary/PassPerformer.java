package ru.itis.MyTube.auxiliary;

public class PassPerformer {
    private static final String salt = "&#!^(&$!";

    public static String hash(String password) {
        return MD5(password + salt);
    }

    private static String MD5(String md5) {
//        return Hex.encodeHexString(md5.getBytes());
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
