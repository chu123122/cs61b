package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{

    private BST<K,V> root;
    private int size;
    public BSTMap(){
        size=0;
    }
    @Override
    public void clear() {
        root=null;
        size=0;
    }

    @Override
    public boolean containsKey(K key) {
        if(root==null)return false;
        BST<K,V> check=root;
        while(check!=null){
            if(check.key.compareTo(key)>0){
                check=check.left;
            } else if (check.key.compareTo(key)<0) {
                check=check.right;
            }else{
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if(root==null)return null;
        BST<K,V> check=root;
        while(check!=null){
            int size=check.key.compareTo(key);
            if(size>0){
                check=check.left;
            } else if (size<0) {
                check=check.right;
            }else{
                return check.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        size++;
        if(root==null){
            root=new BST<>(key,value);
            return;
        }
        BST<K,V> check=root;
        while(check!=null){
            int size=check.key.compareTo(key);
            if(size>0){
                if(check.left==null){
                    check.left=new BST<>(key,value);
                    break;
                }
                check=check.left;
            } else if (size<0) {
                if(check.right==null){
                    check.right=new BST<>(key,value);
                    break;
                }
                check=check.right;
            }else{
                check.value=value;
                break;
            }
        }
    }

    public void printInOrder(){
        BST<K,V> check=root;
        while(check!=null){
            System.out.println(check.value);
            check=check.right;
        }
    }

    class BST<K extends Comparable<K>,V>{
        public K key;
        public V value;
        public BST<K,V> left;
        public BST<K,V> right;
        public BST( K key,V value){
            this.key=key;
            this.value=value;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set=new TreeSet<>();
        followAllNode(root,set);
        return set;
    }
    private void followAllNode(BST<K,V> node,Set<K> set){
        if(node==null)return;
        set.add(node.key);
        followAllNode(node.left,set);
        followAllNode(node.right,set);
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
