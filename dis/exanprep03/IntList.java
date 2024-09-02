public class IntList {
    public int first;
    public IntList rest;
    public IntList (int f, IntList r) {
        this.first = f;
        this.rest = r;
    }

    public static void evenOdd(IntList lst){
        if(lst==null||lst.rest==null){
            return;
        }
        IntList oddList = lst.rest;
        IntList second = lst.rest;

        while (lst.rest != null && oddList.rest != null) {
            lst.rest = lst.rest.rest;
            oddList.rest = oddList.rest.rest;
            lst = lst.rest;
            oddList = oddList.rest;
        }
        lst.rest = second;
    }
    public static void main(String[] args){
        IntList list1=new IntList(5,new IntList(11,new IntList(6,new IntList(9,new IntList(24,null)))));
        evenOdd(list1);
    }
}