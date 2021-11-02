package utils.multithread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;


/**
 * @author pqc
 * @ClassName: HandleCallable.java
 * @Description: 线程调用
 */
public class HandleCallable<T, V> implements Callable<V> {

    private T data;
    private ITask<T, V> task;

    public HandleCallable(T data, ITask<T, V> task) {
        this.data = data;
        this.task = task;
    }


    @Override
    public V call() {
        task.execute(data);
        return null;
    }

}