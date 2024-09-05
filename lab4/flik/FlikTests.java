package flik;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FlikTests {
    @Test
    public void randomTest1(){
        for (int i=0 ;i<=100000;i++){
            int x= StdRandom.uniform(0, 100);
            int y=StdRandom.uniform(0, 100);
            if(x==y){
                Assert.assertTrue(Flik.isSameNumber(x,y));
            }else{
                Assert.assertFalse(Flik.isSameNumber(x,y));
            }
        }

    }

    @Test
    public void randomTest2(){
        int j=0;
        for (int i=0 ;i<=100000;i++,j++){
            Assert.assertTrue(Flik.isSameNumber(i,j));
        }

    }
}
