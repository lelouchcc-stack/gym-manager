package com.lelcc.common;

public class UserContext {
    private final static ThreadLocal<Long> USER_HOLDER = new ThreadLocal<>();

    public static void set(Long user_id){
        USER_HOLDER.set(user_id);
    }
    public static Long get(){
        return USER_HOLDER.get();
    }
    public static void remove(){
        USER_HOLDER.remove();
    }
}
