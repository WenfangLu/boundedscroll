package boundedscroll;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ScrollIteratorTest {

    Scroll<String> ab_cd_6; // [A,B] [C, D]:6
    Scroll<String> zero; // [A,B] [C, D]:6
    ScrollIterator scroll;
    ScrollIterator emptyScroll; // [] []

    /**
     * the init of the ScrollIteratorTest
     * @throws Exception the error of the setUp
     */
    @org.junit.Before
    public void setUp() throws Exception {
        ab_cd_6 = new StackScroll<>(6);
        zero = new StackScroll<>(0);
        ab_cd_6.insert("D");
        ab_cd_6.insert("C");
        ab_cd_6.insert("B");
        ab_cd_6.insert("A");
        ab_cd_6.reset();
        ab_cd_6.advance();
        ab_cd_6.advance();
        scroll = new ScrollIterator<String>(ab_cd_6);
        emptyScroll = new ScrollIterator<String>(zero);

    }

    /**
     * the test of initSetup
     */
    @Test
    public void initSetup(){
        assertEquals(false,emptyScroll.hasNext());
        assertEquals(false,emptyScroll.hasPrevious());
    }
    /**
     * the test of hasNextTest
     */
    @Test
    public void hasNextTest(){
        assertEquals( true, scroll.hasNext());
    }
    /**
     * the test of nextTest
     */
    @Test
    public void nextTest(){
        assertEquals("C",scroll.next());
    }
    /**
     * the test of NextException
     */
    @Test(expected = NoSuchElementException.class)
    public void testNextException(){
        emptyScroll.next();
        fail();
    }
    /**
     * the test of hasPrevious
     */
    @Test
    public void hasPreviousTest(){
        assertEquals(true,scroll.hasPrevious());
    }

    /**
     * the test of previous
     */
    @Test
    public void previousTest(){
        assertEquals("B",scroll.previous());
    }
    /**
     * the test of nextIndex
     */
    @Test
    public void nextIndexTest(){
        assertEquals(2,scroll.nextIndex());
    }

    /**
     * the test of previousIndex
     */
    @Test
    public void previousIndexTest(){
        assertEquals(1,scroll.previousIndex());
    }

    /**
     * the test of set
     */
    @Test(expected = UnsupportedOperationException.class)
    public void setException(){
        String e = "test";
        emptyScroll.set(e);
        fail();
    }
    /**
     * the test of add
     */
    @Test(expected = UnsupportedOperationException.class)
    public void addException(){
        String e = "test";
        emptyScroll.add(e);
        fail();
    }

    /**
     * the test of remove
     */
    @Test(expected = UnsupportedOperationException.class)
    public void removeException(){
        String e = "test";
        emptyScroll.remove();
        fail();
    }


}
