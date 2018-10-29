import java.util.Random;

public class RandomPickPager extends Pager
{
    @Override
    public int getEvictionIndex()
    {
        return new Random().nextInt(MEMORY_FRAMES);
    }
}