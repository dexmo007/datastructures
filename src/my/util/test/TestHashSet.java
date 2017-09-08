import my.util.HashSet;
import my.util.Set;

/**
 * Created by Henrik on 12/8/2015.
 */
public class TestHashSet extends TestSet{
    @Override
    public Set<String> getSet() {
        return new HashSet<String>();
    }
}
