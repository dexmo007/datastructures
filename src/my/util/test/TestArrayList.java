import my.util.ArrayList;
import my.util.List;

/**
 * Created by Henrik on 12/4/2015.
 */
public class TestArrayList extends TestList {
    @Override
    public List<String> getList() {
        return new ArrayList<>("", 6);
    }
}
