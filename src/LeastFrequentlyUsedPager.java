public class LeastFrequentlyUsedPager extends Pager {
    @Override
    public int getEvictionIndex() {
        int processNumber = getMemoryOrdered().peek().number;
        int i = 0;
        for (Page p : getMemoryTable())
        {
            if (p.number == processNumber) {
                for (int res = 3; res >= 0; res--) {
                    if (i + 1 != res && i != res && i - 1 != res) {
                        return res;
                    }
                }
            }

            ++i;
        }
        return -1; // this should never happen
    }
}
