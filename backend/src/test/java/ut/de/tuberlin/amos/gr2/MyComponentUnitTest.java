package ut.de.tuberlin.amos.gr2;

import org.junit.Test;
import de.tuberlin.amos.gr2.api.MyPluginComponent;
import de.tuberlin.amos.gr2.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}