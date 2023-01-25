package boundedscroll;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * a test for ListScroll
 */
public class ListScrollTest {

    Scroll<String> ab_cd_10; // [A,B] [C, D]:6
    Scroll<String> ef_ghi_6; // [] [E,F,G,H,I]:6
    Scroll<String> emptyScroll; // [] []

    /**
     * the init of ListScrollTest
     * @throws Exception the error of setUp()
     */
    @Before
    public void setUp() throws Exception {
        ab_cd_10 = new ListScroll<>(10);
        ef_ghi_6 = new ListScroll<>(6);
        emptyScroll = new ListScroll<>(0);

        ab_cd_10.insert("D");
        ab_cd_10.insert("C");
        ab_cd_10.insert("B");
        ab_cd_10.insert("A");
        ab_cd_10.reset();
        ab_cd_10.advance();
        ab_cd_10.advance();

        ef_ghi_6.insert("I");
        ef_ghi_6.insert("G");
        ef_ghi_6.insert("H");
        ef_ghi_6.insert("F");
        ef_ghi_6.insert("E");
    }

    /**
     * the test of initSetup1
     */
    @Test
    public void initSetup1(){
        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(2, ab_cd_10.rightLength());
        assertEquals(10, ab_cd_10.capacity());
    }
    /**
     * the test of initSetup2
     */
    @Test
    public void initSetup2(){
        assertEquals("C", ab_cd_10.getNext());
        assertEquals("B", ab_cd_10.getPrevious());
    }

    /**
     * the test of getNext
     */
    @Test
    public void getNextTest(){
        assertEquals("C", ab_cd_10.getNext());
    }

    /**
     * the test of swapRights
     */
    @Test
    public void swapRightsTest(){

        ab_cd_10.swapRights(ef_ghi_6);
        assertEquals( 5,ab_cd_10.rightLength());

    }

    /**
     *  the test of advanceToEnd
     */
    @Test
    public void advanceToEndTest(){
        ab_cd_10.advanceToEnd();
        assertEquals( 0,ab_cd_10.rightLength());
    }

    /**
     *   the test of advance
     */
    @Test
    public void  advanceTest(){
        ab_cd_10.advance();
        assertEquals( 1,ab_cd_10.rightLength());
    }

    /**
     * a test of retreat
     */
    @Test
    public void  retreatTest(){
        ab_cd_10.retreat();
        assertEquals( 3,ab_cd_10.rightLength());
    }

    /**
     * a test of reset
     */
    @Test
    public void  resetTest(){
        ab_cd_10.reset();
        assertEquals( 4,ab_cd_10.rightLength());
    }
    /**
     * a test of delete
     */
    @Test
    public void  deleteTest(){
        ab_cd_10.delete();
        assertEquals( 1,ab_cd_10.rightLength());
    }

    /**
     * a test of AdvanceException
     */
    @Test(expected = IllegalStateException.class)
    public void testAdvanceException(){
        ab_cd_10.advanceToEnd();
        ab_cd_10.advance();
        fail();
    }
    /**
     * a test of DeleteException
     */
    @Test(expected = IllegalStateException.class)
    public void testDeleteException(){
        ab_cd_10.advanceToEnd();
        ab_cd_10.delete();
        fail();
    }

    /**
     *   a test of InsertNull
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInsertNullException(){
        String a  = null;
        ab_cd_10.insert(a);
        fail();
    }

    /**
     *   a test of InsertException
     */
    @Test(expected = IllegalStateException.class)
    public void testInsertException(){
        String a  = "A";
        emptyScroll.insert(a);
        fail();
    }

    /**
     *   a test of SwapRights2
     */
    @Test(expected = IllegalStateException.class)
    public void testSwapRights2Exception(){
        String a  = "A";
        StackScroll<String> ab_cd_4 = new StackScroll<>(4);
        ab_cd_4.insert("1");
        ab_cd_4.insert("2");
        ab_cd_4.insert("3");
        ab_cd_4.insert("5");
        ab_cd_4.reset();
        ab_cd_4.advance();
        ab_cd_4.advance();
        ab_cd_4.swapRights(emptyScroll);
        fail();
    }
    /**
     *   a test of RetreatException
     */
    @Test(expected = IllegalStateException.class)
    public void testRetreatException(){
        emptyScroll.retreat();
        fail();
    }

    /**
     *  a test for newInstance
     */
    @Test
    public void newInstanceTest(){
        Scroll<String> item = ab_cd_10.newInstance();
        assertEquals(item.getClass(),ab_cd_10.getClass());
    }

}
