package com.regus.pay.task;

import com.regus.pay.task.tasks.BaseTask;

import java.util.concurrent.BlockingQueue;

public class BlockingTaskThread extends Thread {

    private BlockingQueue<BaseTask> blockingQueueTasks;
    private boolean isLoop = true;


    public BlockingTaskThread(BlockingQueue<BaseTask> blockingQueue) {
        blockingQueueTasks = blockingQueue;
    }


    public boolean isLoop() {
        return isLoop;
    }


    public void run() {
        while (isLoop) {
            BaseTask task = blockingQueueTasks.peek();
            if (task != null) {
                while (!task.isFinish()) {
                    if (!task.isDoing()) {
                        task.startTask();
                    }
                }
                blockingQueueTasks.remove();
            }
        }
    }

}
