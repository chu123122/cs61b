package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void getADTestOne() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addFirst(7);
        lld1.addFirst(54);
        lld1.addFirst(22);
        lld1.addLast(9);
        lld1.addLast(19);
        int check1 = lld1.get(0);
        assertEquals(22, check1);
        int check2 = lld1.removeFirst();
        assertEquals(22, check2);
        int check3 = lld1.get(1);
        assertEquals(7, check3);
    }

    @Test
    public void getADTestTwo() {
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(12);
        ad.addFirst(5);
        ad.addLast(77);
        ad.addLast(17);
        ad.addLast(33);
        ad.addFirst(85);

        int test = ad.get(0);
        Assert.assertEquals(85, test);
        test = ad.get(1);
        Assert.assertEquals(5, test);
        test = ad.get(2);
        Assert.assertEquals(12, test);
        test = ad.get(3);
        Assert.assertEquals(77, test);
        test = ad.get(4);
        Assert.assertEquals(17, test);
        test = ad.get(5);
        Assert.assertEquals(33, test);
    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        ArrayDeque<String> lld1 = new ArrayDeque<String>();
        ArrayDeque<Double> lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    public void fillUpAndEmptyTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();

        ad.addFirst(4);
        ad.addFirst(2);
        ad.addLast(5);
        ad.addLast(10);
        ad.addFirst(76);
        ad.addFirst(15);
        ad.addFirst(55);
        ad.addFirst(87);

        int remove1 = ad.removeLast();
        assertEquals(10, remove1);

        int remove2 = ad.removeLast();
        assertEquals(5, remove2);

        int remove3 = ad.removeFirst();
        assertEquals(87, remove3);

        int remove4 = ad.removeFirst();
        assertEquals(55, remove4);

        int remove5 = ad.removeLast();
        assertEquals(4, remove5);

        int remove6 = ad.removeLast();
        assertEquals(2, remove6);

        int remove7 = ad.removeFirst();
        assertEquals(15, remove7);

        int remove8 = ad.removeLast();
        assertEquals(76, remove8);
    }

    @Test
    public void iteratorLLDequeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addFirst(5);
        lld1.addFirst(7);
        lld1.addLast(98);
        lld1.addLast(11);
        ArrayDeque<Integer> target = new ArrayDeque<Integer>();
        target.addLast(7);
        target.addLast(5);
        target.addLast(98);
        target.addLast(11);
        int i = 0;
        assertEquals(lld1.size(), target.size());
        for (int number : lld1) {
            int ta = target.get(i);
            assertEquals(number, ta);
            i++;
        }
    }

    @Test
    public void equalLLDequeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addFirst(5);
        lld1.addFirst(7);
        lld1.addLast(98);
        lld1.addLast(11);
        Deque<Integer> target = new ArrayDeque<Integer>();
        target.addLast(7);
        target.addLast(5);
        target.addLast(98);
        target.addLast(11);

        assertTrue(lld1.equals(target));
        target.addLast(5);
        assertFalse(lld1.equals(target));
    }

    @Test
    public void equalADTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addFirst(5);
        lld1.addFirst(7);
        lld1.addLast(98);
        lld1.addLast(11);
        Deque<Integer> target = new LinkedListDeque<>();
        target.addLast(7);
        target.addLast(5);
        target.addLast(98);
        target.addLast(11);

        assertTrue(lld1.equals(target));
        target.addLast(5);
        assertFalse(lld1.equals(target));
    }

    @Test
    public void addAndRemoveRandomTest() {
        Deque<Integer> ad = new ArrayDeque<>();
        Deque<Integer> lld = new LinkedListDeque<>();
        ad.addLast(40);
        lld.addLast(40);

        ad.addLast(65);
        lld.addLast(65);

        ad.removeFirst();
        lld.removeFirst();

        ad.removeFirst();
        lld.removeFirst();

        ad.addLast(40);
        lld.addLast(40);

        ad.addLast(40);
        lld.addLast(40);

        ad.addLast(65);
        lld.addLast(65);
        Assert.assertTrue(lld.equals(ad));
        Assert.assertTrue(ad.equals(lld));
    }

    @Test
    public void ResizeADTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addFirst(3);
        ad.addLast(4);
        ad.addFirst(5);
        ad.addFirst(6);
        ad.addLast(7);
        ad.addLast(8);
        ad.addLast(9);

        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();

        ad.addLast(1);
        ad.addLast(2);
        ad.addFirst(3);
        ad.addLast(4);
        ad.addFirst(5);
        ad.addFirst(6);
        ad.addLast(7);
        ad.addLast(8);
        ad.addLast(9);

        int test1 = ad.get(0);
        int test2 = ad.get(5);
        int test3 = ad.get(3);
        Assert.assertEquals(6, test1);
        Assert.assertEquals(4, test2);
        Assert.assertEquals(1, test3);
    }

    @Test
    public void randomTest() {
        long seed = 1725450026615L; // 使用之前记录的种子值
        Random random = new Random(seed);
        System.out.println("Seed: " + seed);
        Deque<Integer> ad = new ArrayDeque<>();
        Deque<Integer> lld = new LinkedListDeque<>();
        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(7);

            if (index == 0) {
                int number = StdRandom.uniform(0, 100);
                ad.addFirst(number);
                lld.addFirst(number);
                System.out.println("AddFirst:" + number);
            } else if (index == 1) {
                int number = StdRandom.uniform(0, 100);
                ad.addLast(number);
                lld.addLast(number);
                System.out.println("AddLast:" + number);
            } else if (index == 2) {
                if (ad.size() == 0 || lld.size() == 0) continue;
                int number1 = ad.removeLast();
                int number2 = lld.removeLast();
                Assert.assertEquals(number1, number2);
                System.out.println("RemoveLast:" + number1);
            } else if (index == 3) {
                if (ad.size() == 0 || lld.size() == 0) continue;
                int number1 = ad.removeFirst();
                int number2 = lld.removeFirst();
                Assert.assertEquals(number1, number2);
                System.out.println("RemoveFirst:" + number1);
            } else if (index == 4) {
                int number1 = ad.size();
                int number2 = lld.size();
                assertEquals(number1, number2);
            } else if (index == 5) {
                boolean test1 = ad.equals(lld);
                boolean test2 = lld.equals(ad);
                Assert.assertTrue(test1);
                Assert.assertTrue(test2);
            } else {
                if (ad.size() == 0 || lld.size() == 0) continue;
                int number1 = ad.size();
                int number2 = lld.size();
                Assert.assertEquals(number1, number2);

                int number = StdRandom.uniform(0, ad.size());

                int number3 = ad.get(number);
                int number4 = lld.get(number);
                Assert.assertEquals(number3, number4);
            }
        }
    }
}
