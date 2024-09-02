package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    public class FindMaxMax<T> implements Comparator<T>{
        @Override
        public int compare(T o1, T o2) {
            if(o1.getClass()==o2.getClass()){
                if(o1 instanceof Integer){
                    return Integer.compare((Integer) o1,(Integer)o2);
                } else if (o1 instanceof String) {
                    return ((String)o1).compareTo((String) o2);
                }
            }
            return -1;
        }
    }
    @Test
    public void FindMaxMaxArrayTest() {
        FindMaxMax<Integer> comparator=new FindMaxMax<>();
        MaxArrayDeque<Integer> maxArrayDeque=new MaxArrayDeque<>(comparator);
        maxArrayDeque.addFirst(5);
        maxArrayDeque.addFirst(11);
        maxArrayDeque.addFirst(7);
        maxArrayDeque.addLast(99);
        maxArrayDeque.addLast(29);
        int max=maxArrayDeque.max();
        assertEquals(99,max);
    }

    public class FindMaxMin<T> implements Comparator<T>{
        @Override
        public int compare(T o1, T o2) {
            if(o1.getClass()==o2.getClass()){
                if(o1 instanceof Integer){
                    return Integer.compare((Integer) o2,(Integer)o1);
                } else if (o1 instanceof String) {
                    return ((String)o2).compareTo((String) o1);
                }
            }
            return -1;
        }
    }
    @Test
    public void FindMinMaxArrayTest() {
        FindMaxMin<Integer> comparator=new FindMaxMin<>();
        MaxArrayDeque<Integer> maxArrayDeque=new MaxArrayDeque<>(comparator);
        maxArrayDeque.addFirst(5);
        maxArrayDeque.addFirst(11);
        maxArrayDeque.addFirst(7);
        maxArrayDeque.addLast(99);
        maxArrayDeque.addLast(29);
        int min=maxArrayDeque.max();
        assertEquals(5,min);
    }
    @Test
    public void FindFiveMaxArrayTest() {
        FindMaxMin<String> comparator=new FindMaxMin<>();
        MaxArrayDeque<String> maxArrayDeque=new MaxArrayDeque<>(comparator);
        maxArrayDeque.addLast("apples");
        maxArrayDeque.addFirst("dog");
        maxArrayDeque.addLast("bad");
        maxArrayDeque.addFirst("so");
        maxArrayDeque.addFirst("winds");
        String max=maxArrayDeque.max();
        assertEquals("apples",max);
    }
    public class FindHaveA<T> implements Comparator<T>{
        @Override
        public int compare(T o1, T o2) {
            if(o1.getClass()==o2.getClass()){
                if(o1 instanceof Integer){
                    return Integer.compare((Integer) o2,(Integer)o1);
                } else if (o1 instanceof String) {
                    if(((String)o1).contains("a")&&((String)o2).contains("a"))
                        if(((String)o1).length()>((String)o2).length())
                            return 1;
                        else
                            return -1;
                    else if (((String)o1).contains("a")&&!((String)o2).contains("a"))
                        return 1;
                    else
                        return -1;
                }
            }
            return -1;
        }
    }
    @Test
    public void FindHaveAMaxArrayTest() {
        FindHaveA<String> comparator=new FindHaveA<>();
        MaxArrayDeque<String> maxArrayDeque=new MaxArrayDeque<>(comparator);
        maxArrayDeque.addLast("apple");
        maxArrayDeque.addFirst("dog");
        maxArrayDeque.addLast("goodness");
        maxArrayDeque.addFirst("so");
        maxArrayDeque.addFirst("winds");
        String max=maxArrayDeque.max();
        assertEquals("apple",max);
    }

}
