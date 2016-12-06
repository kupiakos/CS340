package shared.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PairUtils<A, B> {
    private final A first;
    private final B second;

    public PairUtils(A left, B right) {
        this.first = left;
        this.second = right;
    }

    public static <A, B> List<PairUtils<A, B>> zip(List<A> as, List<B> bs) {
        Iterator<A> it1 = as.iterator();
        Iterator<B> it2 = bs.iterator();
        List<PairUtils<A, B>> result = new ArrayList<>();
        while (it1.hasNext() && it2.hasNext()) {
            result.add(new PairUtils<A, B>(it1.next(), it2.next()));
        }
        return result;
    }

    public A first() {
        return first;
    }

    public B second() {
        return second;
    }

    public String toString() {
        return "{" + first + "," + second + "}";
    }

}