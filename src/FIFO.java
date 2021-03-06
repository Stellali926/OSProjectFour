public class FIFO extends Pager
{
    @Override
    public int getEvictionIndex()
    {
        int processNumber = getMemoryOrdered().poll().number;
        int i = 0;
        for (Page p : getMemoryTable())
        {
            if (p.number == processNumber)
                return i;
            ++i;
        }
        return -1; // this should never happen
    }
}
