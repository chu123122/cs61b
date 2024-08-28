package randomizedtest;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class TestThreeAddThreeRemove {

    public static void main(String[] args){
        AListNoResizing<Integer> AList=new AListNoResizing<>();
        AList.addLast(4);
        AList.addLast(5);
        AList.addLast(6);
        BuggyAList<Integer> BugAList=new BuggyAList<>();
        BugAList.addLast(4);
        BugAList.addLast(5);
        BugAList.addLast(6);

        assertEquals(AList.removeLast(),BugAList.removeLast());
        assertEquals(AList.removeLast(),BugAList.removeLast());
        assertEquals(AList.removeLast(),BugAList.removeLast());
    }
}
