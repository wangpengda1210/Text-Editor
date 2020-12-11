import java.util.Iterator;

class Range implements Iterable<Long> {

    private long fromInclusive;
    private long toExclusive;

    public Range(long from, long to) {
        this.fromInclusive = from;
        this.toExclusive = to;
    }

    @Override
    public Iterator<Long> iterator() {
        // write your code here
        return new Iterator<Long>() {

            long current = fromInclusive - 1;

            @Override
            public boolean hasNext() {
                return current < toExclusive - 1;
            }

            @Override
            public Long next() {
                current++;
                return current;
            }
        };
    }
}