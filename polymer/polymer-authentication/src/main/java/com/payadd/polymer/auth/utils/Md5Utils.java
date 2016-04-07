package com.payadd.polymer.auth.utils;


import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import java.io.UnsupportedEncodingException;

/**
 * Created by qjj on 15-11-1.
 */
public class Md5Utils {

    public static String md5(byte[] mesage)
    {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putBytes(mesage);
         String md5=hasher.hash().toString().toUpperCase();
        return md5;
    }
}
