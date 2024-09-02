package deque;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
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


        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<Double>();
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
    public void fillUpAndEmptyTest(){
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();

        ad.addFirst(4);
        ad.addFirst(2);
        ad.addLast(5);
        ad.addLast(10);
        ad.addFirst(76);
        ad.addFirst(15);
        ad.addFirst(55);
        ad.addFirst(87);

        int remove1=ad.removeLast();
        assertEquals(10,remove1);

        int remove2=ad.removeLast();
        assertEquals(5,remove2);

        int remove3=ad.removeFirst();
        assertEquals(87,remove3);

        int remove4=ad.removeFirst();
        assertEquals(55,remove4);

        int remove5=ad.removeLast();
        assertEquals(4,remove5);

        int remove6=ad.removeLast();
        assertEquals(2,remove6);

        int remove7=ad.removeFirst();
        assertEquals(15,remove7);

        int remove8=ad.removeLast();
        assertEquals(76,remove8);
    }

    @Test
    public void iteratorLLDequeTest(){
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
        int i=0;
        assertEquals(lld1.size(),target.size());
        for (int number:lld1) {
            int ta=target.get(i);
            assertEquals(number,ta);
            i++;
        }
    }
    @Test
    public void equalLLDequeTest(){
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

        assertTrue(lld1.equals(target));
        target.addLast(5);
        assertFalse(lld1.equals(target));
    }
}
