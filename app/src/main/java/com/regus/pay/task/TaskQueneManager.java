package com.regus.pay.task;

import com.regus.pay.task.tasks.BaseTask;
import com.regus.pay.task.tasks.ProductQrTask;
import com.regus.pay.task.tasks.QueryAndUploadTradeTask;
import com.regus.pay.task.tasks.UploadTradeMsgTask;
import com.regus.pay.util.XLogUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * archar
 */
public class TaskQueneManager {

    private BlockingQueue<BaseTask> mBlockingQueueTasks = new LinkedBlockingQueue();

    private BlockingTaskThread mBlockingTaskThread = new BlockingTaskThread(mBlockingQueueTasks);


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

    public TaskQueneManager() {
        mBlockingTaskThread.start();
    }

    /**
     * 新任务 入列
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T extends BaseTask> int addTaskToQuene(T t) {
        if (!mBlockingQueueTasks.contains(t)) {

            String taskInfo = "";
            if (t instanceof ProductQrTask) {
                taskInfo = "加入一条 生产二维码 任务";
            } else if (t instanceof QueryAndUploadTradeTask) {
                taskInfo = "加入一条 查询且上传订单 任务";
            } else if (t instanceof UploadTradeMsgTask) {
                taskInfo = "加入一条 上传订单 任务";
            }

            mBlockingQueueTasks.add(t);

            XLogUtil.log(taskInfo + "  当前的队列中任务的数量: " + mBlockingQueueTasks.size());

        }
        return mBlockingQueueTasks.size();
    }


    /**
     * 队顶的任务应该结束
     */
    public void endTopTask() {
        BaseTask task = mBlockingQueueTasks.peek();
        if (task != null) {
            task.setLastBillFinish();
        }
    }


    /**
     * 返回队顶的任务
     */

    public BaseTask getTopTask() {
        return mBlockingQueueTasks.peek();
    }


    /**
     * 任务线程是否 跑着
     *
     * @return
     */
    public boolean isTaskRun() {
        return mBlockingTaskThread != null && mBlockingTaskThread.isLoop();
    }


}
