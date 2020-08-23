package threadhomework;

import java.util.concurrent.Executor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.Inflater;

public class Calculate {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        String threadGroupName="calculate";
        ThreadGroup threadGroup=new ThreadGroup(threadGroupName);
//        Result result=new Result();
//        Searcher searcher=new Searcher(result);
//        Executor executor= new 
        ThreadPoolExecutor tpe=(ThreadPoolExecutor) Executors.newFixedThreadPool(6);
        List<Future<Long>> resultList=new ArrayList<Future<Long>>();
        int end=0;
        
        for (int i=0;i<6;i++) {
            int start=end;
            end+=100000000/6;
            if (i>1) {
                end+=1;
            }
            sumTask calculator=new sumTask(start, end);
            Future<Long> result=tpe.submit(calculator);
            resultList.add(result);
        }
        do {
            System.out.printf("Main: 已经完成多少个任务: %d\n",tpe.getCompletedTaskCount());
            for(int i=0;i<resultList.size();i++) {
                Future<Long> tmpFuture=resultList.get(i);
                System.out.printf("task %d is %s\n", i,tmpFuture.isDone());
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } while (tpe.getCompletedTaskCount()<resultList.size());
        long sum=0;
        for (int i=0;i<resultList.size();i++) {
            sum+=resultList.get(i).get();
        }
        System.out.println("sum="+sum);
    }
    
}
class sumTask implements Callable<Long>{
    private int start=0;
    private int end=0;
    sumTask(int start,int end) {
        this.start=start;
        this.end=end;
    }
    
    public Long call() throws Exception {
        long sum=0;
        for (long i=start;i<end;i++) {
            sum+=i;
        }
        Thread.sleep(new Random().nextInt(1000));
        return sum;
    }
}