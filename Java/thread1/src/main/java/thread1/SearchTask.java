package thread1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SearchTask implements Callable<Integer>{
    private int start,end,target;
    private List<Integer> list=new ArrayList<Integer>();
    public SearchTask(List<Integer> list,int start,int end,int target) {
        this.start=start;
        this.end=end;
        this.target=target;
        this.list.addAll(list);
    }

    @Override
    public Integer call() throws Exception{
        // TODO Auto-generated method stub
        int res=0;
        for (int i=this.start;i<this.end;i++) {
            if(list.get(i)==this.target) {
                res++;
            }
        }
        return res;
    }

}
