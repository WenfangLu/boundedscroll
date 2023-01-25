package boundedscroll;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class StackScrollTest {

    Scroll<String> ab_cd_10; // [A,B] [C,D]:10
    Scroll<String> ef_ghi_6; // [] [E,F,G,H,I]:6
    Scroll<String> emptyScroll; // [] []
    Scroll<String> ab_cd_10_copy;
    Scroll<String> all;

    /**
     * the init of StackScrollTest
     * @throws Exception the error of setUp
     */
    @org.junit.Before
    public void setUp() throws Exception {
        ab_cd_10 = new StackScroll<>(10);
        ef_ghi_6 = new StackScroll<>(6);
        emptyScroll = new StackScroll<>(0);
        all = new ListScroll<>(3);

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

        ab_cd_10_copy = new StackScroll<>(10);
        ab_cd_10_copy.insert("D");
        ab_cd_10_copy.insert("C");
        ab_cd_10_copy.insert("B");
        ab_cd_10_copy.insert("A");
        ab_cd_10_copy.reset();
        ab_cd_10_copy.advance();
        ab_cd_10_copy.advance();


        all.insert("3");
        all.insert("2");
        all.insert("1");
        all.reset();
        all.advance();

    }

    /**
     * a test of initSetup1
     */
    @Test
    public  void initSetup1(){
        assertEquals(2, ab_cd_10.leftLength());
        assertEquals(2, ab_cd_10.rightLength());
        assertEquals(5, ef_ghi_6.rightLength());
        assertEquals(0, ef_ghi_6.leftLength());
        assertEquals(10, ab_cd_10.capacity());
    }
    /**
     * a test of getNext
     */
    @Test
    public void getNextTest(){
        assertEquals("C", ab_cd_10.getNext());
    }

    /**
     * a test of swapRights
     */
    @Test
    public void swapRightsTest(){

        ab_cd_10.swapRights(ef_ghi_6);
        assertEquals( 5,ab_cd_10.rightLength());

    }

    /**
     * a test of advanceToEnd
     */
    @Test
    public void advanceToEndTest(){
        ab_cd_10.advanceToEnd();
        assertEquals( 0,ab_cd_10.rightLength());
    }

    /**
     * a test of advance
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
     * a test of RetreatException
     */
    @Test(expected = IllegalStateException.class)
    public void testRetreatException(){
        ab_cd_10.reset();
        ab_cd_10.retreat();
        fail();
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
     * a test of AdvanceToEndException
     */
    @Test(expected = IllegalStateException.class)
    public void testAdvanceToEndException(){
        emptyScroll.advanceToEnd();
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
     * a test of InsertNull
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInsertNullException(){
        String a  = null;
        ab_cd_10.insert(a);
        fail();
    }

    /**
     * a test of InsertException
     */
    @Test(expected = IllegalStateException.class)
    public void testInsertException(){
        String a  = "A";
        emptyScroll.insert(a);
        fail();
    }

    /**
     * a test of SwapRightsNullException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSwapRightsNullException(){

        ab_cd_10.swapRights(null);
        fail();
    }

    /**
     * a test of SwapRightsCapacityException
     */
    @Test(expected = IllegalStateException.class)
    public void testSwapRightsCapacityException(){
        ab_cd_10.retreat();
        all.swapRights(ab_cd_10);
        fail();
    }
    /**
     * a test of newInstance
     */
    @Test
    public void newInstanceTest(){
        Scroll<String> item = ab_cd_10.newInstance();
        assertEquals(item.getClass(),ab_cd_10.getClass());
    }

    /**
     *  a test of hashCode
     */
    @Test
    public void hashCodeTest(){

        assertEquals(ab_cd_10.hashCode(), ab_cd_10_copy.hashCode());
    }

    /**
     *  a test of listIterator
     */
    @Test
    public void listIteratorTest(){
        assertTrue(ab_cd_10.listIterator() instanceof ScrollIterator);
    }
    /**
     *  a test of equalsLeftLengthDiff
     */
    @Test
    public void equalsLeftLengthDiffTest(){
        ab_cd_10.insert("T");
        ab_cd_10.advance();
        assertEquals(false, ab_cd_10.equals(ab_cd_10_copy));
    }

    /**
     * a test of equalsRightLengthDiff
     */
    @Test
    public void equalsRightLengthDiffTest(){
        ab_cd_10.insert("T");
        assertEquals(false, ab_cd_10.equals(ab_cd_10_copy));
    }
    /**
     * a test of equalsAnotherScrollDiffCapacity
     */
    @Test
    public void equalsAnotherScrollDiffCapacityTest(){
        assertEquals(false, ab_cd_10.equals(ef_ghi_6));
    }

    /**
     * a test of equalsNull
     */
    @Test
    public void equalsNullTest(){
        assertFalse(ab_cd_10.equals(null));
    }

    /**
     * a test of equalsNonScroll
     */
    @Test
    public void equalsNonScrollTest(){

        assertEquals(false, ab_cd_10.equals("String"));
    }

    /**
     * a test of equalsSelf
     */
    @Test
    public void equalsSelfTest(){

        assertEquals(true, ab_cd_10_copy.equals(ab_cd_10_copy));
    }

    /**
     * a test of splice
     */
    @Test
    public void spliceTest(){
        Scroll<String> scrollWithCapacity10 = new ListScroll<>(10);
        scrollWithCapacity10.insert("D");
        scrollWithCapacity10.insert("C");
        scrollWithCapacity10.insert("B");
        scrollWithCapacity10.insert("A");
        all.reset();
        scrollWithCapacity10.advance();
        scrollWithCapacity10.advance();
        scrollWithCapacity10.splice(all);
        assertEquals("3", scrollWithCapacity10.getPrevious());
        assertEquals(4, scrollWithCapacity10.leftLength());
        assertEquals(0, all.rightLength());
        assertEquals(0, all.leftLength());
    }

    /**
     * a test of getPrevious
     */
    @Test
    public void getPreviousTest(){
        assertEquals("B",ab_cd_10.getPrevious());
    }

    /**
     * a test of getPreviousException
     */
    @Test(expected = IllegalStateException.class)
    public void testGetPreviousException(){
        ab_cd_10.reset();
        ab_cd_10.getPrevious();
        fail();
    }
    /**
     * a test of  replace
     */
    @Test
    public void replaceTest(){

        assertEquals("C", ab_cd_10.replace("T"));
    }
    /**
     * a test of  ReplaceException
     */
    @Test(expected = IllegalStateException.class)
    public void testReplaceException(){
        emptyScroll.replace("E");
        fail();
    }
    /**
     * a test of  SpliceThatLengthException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSpliceThatLengthException(){
        Scroll<String> thatLeftLength = new StackScroll<String>(10);
        thatLeftLength.insert("1");
        thatLeftLength.advance();
        emptyScroll.splice(thatLeftLength);
        fail();
    }

    /**
     * a test of SpliceNullException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSpliceNullException(){
        emptyScroll.splice(null);
        fail();
    }

    /**
     *  a test of SpliceOverCapacityException
     */
    @Test(expected = IllegalStateException.class)
    public void testSpliceOverCapacityException(){
        Scroll<String> overCapacity = new StackScroll<String>(10);
        overCapacity.insert("1");
        emptyScroll.splice(overCapacity);
        fail();
    }

    /**
     *   a test of SpliceRightLengthException
     */
    @Test
    public void testSpliceRightLengthException(){
        Scroll<String> rightLengthZero = new StackScroll<String>(3);
        emptyScroll.splice(rightLengthZero);
    }

    /**
     * a test of reverse
     */
    @Test
    public void reverseTest(){
        ef_ghi_6.reverse();
        assertEquals(5,  ef_ghi_6.leftLength());
    }
    /**
     * a test of toString
     */
    @Test
    public void toStringTest(){
        ab_cd_10_copy.advance();
        assertEquals("[AB][CD]:10",ab_cd_10.toString());
        assertEquals("[][]:0", emptyScroll.toString());
        assertEquals("[ABC][D]:10", ab_cd_10_copy.toString());

    }
    /**
     * a test of Iterator
     */
    @Test
    public void  testIterator(){
        assertTrue(ab_cd_10.iterator() instanceof ScrollIterator);
    }

    /**
     * a test of GetNextException
     */
    @Test(expected = IllegalStateException.class)
    public void testGetNextException(){
       emptyScroll.getNext();
       fail();
    }

    /**
     * a test of EqualsLengthElem
     */
    @Test
    public void testEqualsLengthElem(){
        StackScroll<String> ab_cd_10_test = new StackScroll<>(10);
        ab_cd_10_test.insert("D");
        ab_cd_10_test.insert("C");
        ab_cd_10_test.insert("B");
        ab_cd_10_test.insert("A");
        ab_cd_10_test.reset();
        ab_cd_10_test.advance();
        assertFalse(ab_cd_10.equals(ab_cd_10_test));
        ab_cd_10_test.advance();
        ab_cd_10_test.delete();
        assertFalse(ab_cd_10.equals(ab_cd_10_test));
        ab_cd_10_test.insert("T");
        assertFalse(ab_cd_10.equals(ab_cd_10_test));
    }

    /**
     * test equals() of abstract scroll
     * Same length with different elements
     */
    @Test
    public void testEqualsLengthDiffElem(){
        StackScroll<String> ab_cd_10_test = new StackScroll<>(10);
        ab_cd_10_test.insert("D");
        ab_cd_10_test.insert("G");
        ab_cd_10_test.insert("B");
        ab_cd_10_test.insert("Q");
        ab_cd_10_test.reset();
        ab_cd_10_test.advance();
        assertFalse(ab_cd_10.equals(ab_cd_10_test));
        ab_cd_10_test.advance();
        ab_cd_10_test.delete();
        assertFalse(ab_cd_10.equals(ab_cd_10_test));
        ab_cd_10_test.insert("T");
        assertFalse(ab_cd_10.equals(ab_cd_10_test));
    }

    /**
     * test equals() of abstract scroll
     */
    @Test
    public void testEqualsAllThings() {
        assertFalse(ab_cd_10.equals(null));
        assertTrue(ab_cd_10.equals(ab_cd_10));
        assertFalse(ab_cd_10.equals("[A,      B][C, D]:6"));
        Scroll<String> test = new ListScroll<>(10);
        test.insert("A");
        test.insert("B");
        test.insert("C");
        test.insert("D");
        test.reset();
        test.advance();
        test.advance();
        assertTrue(test.rightLength() == 2);
        test.retreat();
        assertTrue(test.rightLength() == 3);
        assertFalse(ab_cd_10.equals(test));
        test.delete();
        assertFalse(ab_cd_10.equals(test));
        Scroll<String> test8 = new ListScroll<>(8);
        test8.insert("D");
        test8.insert("C");
        test8.insert("B");
        test8.insert("A");
        test8.reset();
        test8.advance();
        test8.advance();
        assertFalse(ab_cd_10.equals(test8));
        assertFalse(emptyScroll.equals(all));
        all.reset();
        all.delete();
        all.delete();
        all.delete();
        assertFalse(emptyScroll.equals(all));
    }














}