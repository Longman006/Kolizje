package pl.edu.pw.fi.szypula.tomasz;

import java.util.*;

/**
 * Created by longman on 04.05.17.
 */
public class Pair<T> implements Iterable<T>  {
    private T first, second;
    private int current = 0;
    public Pair(T t1, T t2){
        first =t1;
        second =t2;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair<?> pair = (Pair<?>) o;

        return getFirst().equals(pair.getFirst()) || getFirst().equals(pair.getSecond()) && getSecond().equals(pair.getSecond()) || getSecond().equals(pair.getFirst());
    }
    @Override
    public int hashCode() {
        return getFirst().hashCode()*getSecond().hashCode();
    }


    public Iterator<T> iterator(){
       return new Iterator<T>() {
            private int current = 0;
            @Override
            public boolean hasNext() {
                return current<2;
            }

            @Override
            public T next() {
                current++;
                if(current==1){
                    return getFirst();
                }
                return getSecond();

            }
        };
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
