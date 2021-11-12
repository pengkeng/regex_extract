package utils.multithread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static utils.multithread.MultiBaseBean.*;

/**
 * @author pqc
 * @ClassName: MultiThreadUtils.java
 * @Description: 多线程工具类
 */
@SuppressWarnings({"all"})
public class MultiThreadUtils<T, V> {

    private static Logger logger = Logger.getLogger(String.valueOf(MultiThreadUtils.class));


    // 线程个数，如不赋值，默认为5
    private static int threadCount = 5;
    // 具体业务任务
    private ITask<T, V> task;

    /**
     * 初始化线程池和线程个数<BR>
     */
    public static MultiThreadUtils newInstance(int Count) {
        MultiThreadUtils instance = new MultiThreadUtils();
        threadCount = Count;
        return instance;
    }

    public static MultiThreadUtils newInstance() {
        MultiThreadUtils instance = new MultiThreadUtils();
        return instance;
    }

    /**
     * 多线程分批执行list中的任务<BR>
     * 方法名：execute<BR>
     */
    public void execute(List<T> data, ITask<T, V> task) {
        ThreadPoolExecutor threadpool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
        int length = data.size();
        for (int i = 0; i < length; i++) {
            T subData = data.get(i);
            HandleCallable execute = new HandleCallable<T, V>(subData, task);
            Future<V> future = threadpool.submit(execute);
        }
        threadpool.shutdown();
        long temp = System.currentTimeMillis();
        while (true) {
            if (threadpool.isTerminated()) {
                Logger.getGlobal().info("任务结束！");
                break;
            }
        }
    }
}