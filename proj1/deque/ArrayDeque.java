package deque;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ArrayDeque<Item> implements Iterable<Item>{
    public Item[] items;
    public int size;
    public int nextFirst;
    public int nextLast;

    public ArrayDeque(){
        items=(Item[])new Object[8];
        size=0;
        nextFirst=7;
        nextLast=0;
    }
    public Item get(int index){
        if(index>size||index<0)return null;
        return items[index];
    }
    public void printDeque(){
        for (Item item:this) {
            System.out.println(item);
        }
        System.out.println();
    }
    public void addFirst(Item item){
        if(size>= items.length-1) ResizeArgsBigger();
        items[nextFirst]=item;
        nextFirst--;
        size++;
    }
    public void addLast(Item item){
        if(size>= items.length-1) ResizeArgsBigger();
        items[nextLast]=item;
        nextLast++;
        size++;
    }

    public Item removeFirst(){
        if(size==0) return null;
        if (size<=items.length/4&&size>=8) ResizeArgsSmaller();
        int removeNumber=switchNumber(nextFirst+1);

        Item remove= items[removeNumber];
        items[removeNumber]=null;
        size--;
        nextFirst++;
        return remove;
    }
    public Item removeLast(){
        if(size==0) return null;
        if (size<=items.length/4&&size>=8) ResizeArgsSmaller();
        int removeNumber=switchNumber(nextLast-1);

        Item remove= items[removeNumber];
        items[removeNumber]=null;
        size--;
        nextLast--;
        return remove;
    }
    private int switchNumber(int number){
       if(number<0){
           number=items.length+number;
           while (items[number]==null){
               number--;
           }
       }
       else if(number>=items.length){
           number=number-items.length;
           while (items[number]==null){
               number++;
           }
       }
       return number;
    }
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    private void ResizeArgsBigger(){
        float multiplier=2f;
        int total=(int)(items.length*multiplier);
        Item[] newItems=(Item[])new Object[total];
        for(int i=0;i<items.length;i++){
            newItems[i]=items[i];
        }
        items=newItems;
    }
    private void ResizeArgsSmaller(){
        float multiplier=0.5f;
        int total=(int)(items.length*multiplier);
        Item[] newItems=(Item[])new Object[total];
        for(int i=0;i<total;i++){
            newItems[i]=items[i];
        }
        items=newItems;
    }


    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int firstNumber=switchNumber(nextFirst+1);
            @Override
            public boolean hasNext() {
                return items[firstNumber] != null;
            }

            @Override
            public Item next() {
                Item next=items[firstNumber];
                firstNumber=switchNumber(firstNumber+1);
                return next;
            }
        };
    }
    @Override
    public boolean equals(Object o){
        if(o==null)return false;
        if(!(o instanceof ArrayDeque))return false;
        if(this.size()!=((ArrayDeque<?>) o).size())return false;
        ArrayDeque<?> check=((ArrayDeque<?>) o);
        for (int i=nextFirst+1;i<nextLast;i=switchNumber(i+1)){
            if(items[i]!=check.items[i])return false;
        }
        return true;
    }
}
