package boundedscroll;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LinkedScrollTest {

    Scroll<String> ab_cd_10; // [A,B] [C, D]:10
    Scroll<String> ef_ghi_6; // [] [E,F,G,H,I]:6
    Scroll<String> empty; // [] []:0
    Scroll<String> x_10;
    Scroll<String> capacity_10;


    /**
     * the init of the LinkedScrollTest
     * @throws Exception
     */
    @org.junit.Before
    public void setUp() throws Exception {
        ab_cd_10 = new LinkedScroll<>(10);
        ef_ghi_6 = new LinkedScroll<>(10);
        empty = new LinkedScroll<>(0);
        x_10 =  new LinkedScroll<>(1);
        capacity_10 = new LinkedScroll<>(10);
        ab_cd_10.insert("A");
        ab_cd_10.insert("B");
        ab_cd_10.insert("C");
        ab_cd_10.insert("D");

        ef_ghi_6.insert("E");
        ef_ghi_6.insert("F");
        ef_ghi_6.insert("G");
        ef_ghi_6.insert("H");

    }

    /**
     * test of initSetup1
     */
    @Test
    public  void initSetup1(){
        assertEquals(0, ab_cd_10.leftLength());
        assertEquals(4, ab_cd_10.rightLength());
        assertEquals(10, ab_cd_10.capacity());
    }
    /**
     * test of insert
     */
    @Test
    public void insertTest(){
        ab_cd_10.insert("A");
        assertEquals(5, ab_cd_10.rightLength());
    }
    /**
     * test of insert2
     */
    @Test
    public void insert2Test(){
        ab_cd_10.insert("A");
        ab_cd_10.insert("B");
        assertEquals("B", ab_cd_10.getNext());
    }
    /**
     * test of insertNull
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInsertNullException(){
        String a  = null;
        ab_cd_10.insert(a);
        fail();
    }
    /**
     * test of InsertException
     */
    @Test(expected = IllegalStateException.class)
    public void testInsertException(){
        String a  = "X";
        empty.insert(a);
        fail();
    }

    /**
     *  test of Insert2Exception
     */
    @Test(expected = IllegalStateException.class)
    public void testInsert2Exception(){
        String a  = "X";
        String b  = "T";
        x_10.insert(a);
        x_10.insert(b);
        fail();
    }
    /**
     *  test of delete
     */
    @Test
    public void deleteTest(){

        assertEquals("D",ab_cd_10.delete());
        assertEquals("C",ab_cd_10.getNext());
    }

    /**
     *  test of DeleteException
     */
    @Test(expected = IllegalStateException.class)
    public void testDeleteException(){
        empty.delete();
        fail();
    }
    /**
     *  test of  advanceTest
     */
    @Test
    public void advanceTest(){
        ab_cd_10.advance();
        assertEquals(1, ab_cd_10.leftLength());
    }


    @Test
    public void retreatTest(){
        ab_cd_10.advance();
        ab_cd_10.retreat();
        assertEquals(0, ab_cd_10.leftLength());
    }

    @Test(expected = IllegalStateException.class)
    public void testRetreatException(){
        ab_cd_10.retreat();
        fail();
    }

    @Test
    public void resetTest(){
        ab_cd_10.reset();
        assertEquals(0, ab_cd_10.leftLength());
    }

    @Test
    public void advanceToEndTest(){
        ab_cd_10.advanceToEnd();
        assertEquals(4, ab_cd_10.leftLength());
    }

    @Test
    public void swapRightsTest(){
        assertEquals(0,x_10.rightLength());
        capacity_10.swapRights(x_10);
        assertEquals(0, capacity_10.rightLength());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSwapRightsException(){
        ab_cd_10.swapRights(empty);
        fail();
    }

    @Test
    public void newInstanceTest(){
        Scroll<String> item = ab_cd_10.newInstance();
        assertEquals(item.getClass(),ab_cd_10.getClass());
    }

    @Test(expected = IllegalStateException.class)
    public void testAdvanceException(){
        empty.advance();
        fail();
    }


}
