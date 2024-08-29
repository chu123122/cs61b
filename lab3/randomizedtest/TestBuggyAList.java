package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    public static void main(String[] args){
        randomizedTest();
    }
    public static void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer>  T=new BuggyAList<>();

        int N = 5000;
        for(int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                T.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size1=T.size();
                Assert.assertEquals(size,size1);
            } else if (operationNumber==2) {
                //getLast
                if(L.size()==0)continue;
                int last=L.getLast();
                int last1= T.getLast();
                Assert.assertEquals(last,last1);
            } else if (operationNumber==4) {
                //removeLast
                if(L.size()==0)continue;
                int last=L.removeLast();
                int last1=T.removeLast();
                Assert.assertEquals(last,last1);
            }
        }
    }
}
