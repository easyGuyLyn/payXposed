package com.regus.pay.task;

/**
 * archar
 */
public class TaskQueneManager {


    private static TaskQueneManager mInstance;




    public static TaskQueneManager getInstance() {
        if (mInstance == null) {
            synchronized (TaskQueneManager.class) {
                if (mInstance == null) {
                    mInstance = new TaskQueneManager();
                }
            }
        }
        return mInstance;
    }








}
