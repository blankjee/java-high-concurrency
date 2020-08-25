package com.blankjee.code.parallelsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author blankjee
 * @Date 2020/8/24 10:26
 */
public class ParallelSearchDemo {
    static int[] arr = {
            1,3,4,5,6,7,8,2,88,766,54,4342,42,4,534,534,645,6457,765,65,35,23,2,43,54,54,6566,5,
    };

    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int THREAD_NUM = 4;
    static AtomicInteger result = new AtomicInteger(-1);

    public static int search(int searchValue, int beginPos, int endPos) {
        int i = 0;
        for (i = beginPos; i < endPos; i++) {
            if (result.get() >= 0) {
                return result.get();
            }
            if (arr[i] == searchValue) {
                if (!result.compareAndSet(-1, i)) {
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static class SearchTask implements Callable {
        int begin, end, searchValue;
        public SearchTask(int searchValue, int begin, int end) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }
        @Override
        public Object call() throws Exception {
            int re = search(searchValue, begin, end);
            return re;
        }
    }

    public static int pSearch(int searchValue) throws ExecutionException, InterruptedException {
        int subArrSize = arr.length / THREAD_NUM + 1;
        List<Future<Integer>> re = new ArrayList<>();
        for (int i = 0; i < arr.length; i += subArrSize) {
            int end = i + subArrSize;
            if (end > arr.length)  end = arr.length;
            re.add(pool.submit(new SearchTask(searchValue, i, end)));
        }
        for (Future<Integer> future : re) {
            if (future.get() >= 0) {
                return future.get();
            }
        }
        return -1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(pSearch(2));
    }
}
