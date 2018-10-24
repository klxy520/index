
package com.jc.cz_index.common.thread;

public abstract class AbstractRunable implements Runnable {

    private TaskExecutor exec;



    public AbstractRunable(TaskExecutor exec) {
        this.exec = exec;
    }



    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            execute();
        } finally {
            exec.getSemaphore().release();
        }
    }



    protected abstract void execute();
}
