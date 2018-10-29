import java.util.List;
import java.util.Random;

public class Process
{
    public static final double LOC_REF_PROBABILITY = .70;
    public static final int PROCESS_PAGES = 10;
    public Page[] pages;
    public int currentPage;

    public Process(Pager pager)
    {
        List<Page> disk = pager.getDiskTable();

        currentPage = -1;
        pages = new Page[PROCESS_PAGES];
        for (int i = 0; i < PROCESS_PAGES; ++i)
        {
            pages[i] = new Page(i);
            disk.add(i, pages[i]);
        }
    }

    /**
     * Get the next page randomly. Use locality to place a 70% probability on the next page being within 1
     * page of the current page.
     * @return index of the next page to reference
     */
    public int getNextPageNumber()
    {
        Random r = new Random();

        // No references have been made yet -- select a random page [0, PROCESS_PAGES]
        if (currentPage == -1)
            currentPage = r.nextInt(PROCESS_PAGES);
        else
        {
            int delta = r.nextInt(PROCESS_PAGES);
            // Non-local reference - delta of |d| > 1
            if (delta >= PROCESS_PAGES * LOC_REF_PROBABILITY)
            {
                delta = r.nextInt(PROCESS_PAGES / 2 - 1) + 2; // range [2,5]
                if (r.nextDouble() > 0.50)
                    delta = -delta; // [2,5] or [-5, -2]
            }
            else // local reference - delta of [-1, 0, 1]
                delta = r.nextInt(3) - 1;

            currentPage = (currentPage + delta < 0)
                    ? PROCESS_PAGES - 1 + (currentPage +  delta)
                    : (currentPage + delta) % PROCESS_PAGES;
        }
        return currentPage;
    }
}