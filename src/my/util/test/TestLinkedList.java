import my.util.LinkedList;
import my.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Henrik on 12/1/2015.
 */
public class TestLinkedList extends TestList{

    @Override
    public List<String> getList() {
        return new LinkedList<>();
    }
}
