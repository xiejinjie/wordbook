package top.kcoder.wordbook.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CommonThreadPool
 *
 * @author xiejinjie
 * @date 2023/2/2
 */
public class CommonThreadPool {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public static void execute(Runnable command) {
        threadPool.execute(command);
    }

    public static <T> Future<T> submit(Callable<T> command) {
        return threadPool.submit(command);
    }

}
