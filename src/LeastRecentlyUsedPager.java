public class LeastRecentlyUsedPager extends Pager
{
    @Override
    public int getEvictionIndex()
    {
        int oldest = -1;
        int index = 0;
        int i = 0;
        for (Page p : getMemoryTable())
        {   // found an older process so acquire its index
            if (oldest == -1 || p.lastReferenced < oldest)
            {
                oldest = p.lastReferenced;
                index = i;
            }
            ++i;
        }
        return index;
    }
}