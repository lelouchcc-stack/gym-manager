package com.lelcc.common;

import cn.hutool.crypto.digest.BCrypt;

public class GenPwd {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
    }
}
