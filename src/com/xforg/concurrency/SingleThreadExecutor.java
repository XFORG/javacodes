package com.xforg.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/4/25.
 */
public class SingleThreadExecutor {
    public static void main(String[] args){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for( int i = 0; i < 5 ;i++){
            exec.execute(new ListOff());
        }
        exec.shutdown();
    }
}
