
package com.jc.cz_index.common.thread;

import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("threadHelper")
public class ThreadHelper {
    /** logger */
    private static final Logger log = Logger.getLogger(ThreadHelper.class);
    @Autowired
    private TaskExecutor        taskExecutor;



    /**
     * @param taskExecutor
     *            the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }



    /**
     * 通过回调执行业务方法
     * 
     * @param callback
     *            回调接口
     */
    public Future<?> execute(ThreadCallback callback) {
        TaskRunable tr = new TaskRunable(taskExecutor, callback);
        try {
            return taskExecutor.submit(tr);
        } catch (InterruptedException e) {
            log.error("Run thread failed.", e);
            return null;
        }
    }



    /**
     * 通过回调调度业务方法
     * 
     * @param interval
     *            调度间隔时间
     * @param callback
     *            回调接口
     */
    public ScheduleThread execute(long interval, ThreadCallback callback) {
        ScheduleThread st = new ScheduleThread(callback, interval);
        st.start();
        return st;
    }

    /**
     * 任务执行线程
     * 
     * @author
     * @version $Id: ThreadHelper.java 2011-11-1 下午11:16:03 $
     */
    protected class TaskRunable extends AbstractRunable {
        ThreadCallback callback;



        /**
         * @param exec
         */
        protected TaskRunable(TaskExecutor exec, ThreadCallback callback) {
            super(exec);
            this.callback = callback;
        }



        @Override
        protected void execute() {
            callback.run();
        }
    }

    /**
     * 任务调度线程
     * 
     * @author
     * @version $Id: ThreadHelper.java 2011-11-1 下午11:32:29 $
     */
    protected class ScheduleThread extends Thread {
        private boolean stopThread = false;
        private long    interval   = 1000 * 5; // every 5 seconds

        ThreadCallback  callback;



        protected ScheduleThread(ThreadCallback callback, long interval) {
            this.callback = callback;
            this.interval = interval;
        }



        /**
         * sets stop variable and interupts any wait
         */
        public void stopThread() {
            this.stopThread = true;
            this.interrupt();
        }



        /**
         * Start the thread.
         */
        public void run() {
            while (!this.stopThread) {
                try {
                    Thread.sleep(interval);
                    callback.run();
                } catch (InterruptedException e) {
                    if (e instanceof java.lang.InterruptedException)
                        log.info("ScheduleThread stop !");
                    else
                        log.error("ScheduleThread error !", e);
                    break;
                }
            }
        }
    }
}
