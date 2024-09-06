package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void randomTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> lld = new ArrayDequeSolution<>();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            int index = StdRandom.uniform(0, 6);
            if (index == 0) {
                Integer number = StdRandom.uniform(0, 100);
                ad.addFirst(number);
                lld.addFirst(number);
                message.append("addFirst" + "(" + number + ")" + "\n");
            } else if (index == 1) {
                Integer number = StdRandom.uniform(0, 100);
                ad.addLast(number);
                lld.addLast(number);
                message.append("addLast" + "(" + number + ")" + "\n");
            } else if (index == 2) {
                if (ad.size() == 0 || lld.size() == 0) {
                    continue;
                }

                Integer number1 = ad.removeLast();
                Integer number2 = lld.removeLast();
                message.append("removeLast():" + number1 + "\n");
                Assert.assertEquals(message.toString(), number1, number2);
            } else if (index == 3) {
                if (ad.size() == 0 || lld.size() == 0) {
                    continue;
                }

                Integer number1 = ad.removeFirst();
                Integer number2 = lld.removeFirst();
                message.append("removeFirst():" + number1 + "\n");
                Assert.assertEquals(message.toString(), number1, number2);
            } else if (index == 4) {
                int number1 = ad.size();
                int number2 = lld.size();
                message.append("size():" + number1 + "\n");
                Assert.assertEquals(message.toString(), number1, number2);
            } else {
                if (ad.size() == 0 || lld.size() == 0) continue;
                int number1 = ad.size();
                int number2 = lld.size();
                message.append("size():" + number1 + "\n");
                Assert.assertEquals(message.toString(), number1, number2);

                int number = edu.princeton.cs.algs4.StdRandom.uniform(0, ad.size());

                int number3 = ad.get(number);
                int number4 = lld.get(number);
                message.append("get" + "(" + number + "):" + number3 + "\n");
                Assert.assertEquals(message.toString(), number3, number4);
            }
        }


    }
}
