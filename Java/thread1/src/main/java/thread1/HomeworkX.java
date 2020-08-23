package thread1;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class HomeworkX {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        int key=50;
        List<Integer> randomIntegers=new ArrayList<Integer>();
        for(int i=0;i<10000;i++) {
            randomIntegers.add(new Random().nextInt(100));
        }
        int res1=0;
        int res2=0;
        int res3=0;
        res1=serial(randomIntegers,key);
        res2=executorCalculate(randomIntegers, key);
        res3=forkjoin(randomIntegers, key);
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
    public static int serial(List<Integer> in,int key) {
        int res=0;
        for(int tmp:in) {
            if(tmp==key) {
                res++;
            }
        }
        return res;
    }
    public static int executorCalculate(List<Integer> in,int key) throws Exception {
        ThreadPoolExecutor TPE=(ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        int res=0;
        for(int i=1;i<=10;i++) {
            SearchTask searchTask=new SearchTask(in, 10000/10*(i-1), 10000/10*(i), key);
            Future<Integer> result=TPE.submit(searchTask);
            res+=result.get();
        }
        TPE.shutdown();
        return res;
    }
    public static int forkjoin(List<Integer> in,int key) throws Exception{
        ForkJoinPool pool=new ForkJoinPool(10);
        int res=0;
        for(int i=1;i<=10;i++) {
            SearchTask searchTask=new SearchTask(in, 10000/10*(i-1), 10000/10*(i), key);
            ForkJoinTask<Integer> result=pool.submit(searchTask);
            res+=result.get();
        }
        return res;
    }
}
