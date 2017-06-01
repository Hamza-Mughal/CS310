// Thu Dec  1 16:33:35 EST 2016 Update to fix bug in doCompare(..) 
// Initial Release Thu Dec  1 00:06:51 EST 2016 
// HW 4 Final Tests: Runs RecordTests TripleStoreTests
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class RecordTests {

  /*Main method runs tests in this file*/ 
  public static void main(String args[])
  {
    org.junit.runner.JUnitCore.main("RecordTests");
  } 

  // Test whether making records throws proper exceptions
  @Test(expected=IllegalArgumentException.class)
  public void exceptions_1(){
    Record r = Record.makeRecord(null,"ISA","alien");
  }    
  @Test(expected=IllegalArgumentException.class)
  public void exceptions_2(){
    Record r = Record.makeRecord("Alf","ISA",null);
  }    
  @Test(expected=IllegalArgumentException.class)
  public void exceptions_3(){
    Record r = Record.makeRecord(null,null,null);
  }    

  // Test whether making queries throws proper exceptions
  @Test(expected=IllegalArgumentException.class)
  public void query_exception_1(){
    Record r = Record.makeQuery("*",null,"ISA","alien");
  }    
  @Test(expected=IllegalArgumentException.class)
  public void query_exception_2(){
    Record r = Record.makeQuery("*","Alf","*",null);
  }    
  @Test(expected=IllegalArgumentException.class)
  public void query_exception_3(){
    Record r = Record.makeQuery(null,"Alf","*","alien");
  }    
  @Test(expected=IllegalArgumentException.class)
  public void query_exception_4(){
    Record r = Record.makeQuery(null,null,null,null);
  }    


  // Test whether wild cards are initialized properly
  @Test public void wild_1(){
    Record r = Record.makeRecord("Alf","ISA","alien");
    assertFalse(r.entityWild());
    assertFalse(r.relationWild());
    assertFalse(r.propertyWild());
  }    
  @Test public void wild_2(){
    Record r = Record.makeRecord("Alf","*","*");
    assertFalse(r.entityWild());
    assertFalse(r.relationWild());
    assertFalse(r.propertyWild());
  }    
  @Test public void wild_3(){
    Record r = Record.makeQuery("*","Alf","ISA","alien");
    assertFalse(r.entityWild());
    assertFalse(r.relationWild());
    assertFalse(r.propertyWild());
  }    
  @Test public void wild_4(){
    Record r = Record.makeQuery("*","*","ISA","*");
    assertTrue(r.entityWild());
    assertFalse(r.relationWild());
    assertTrue(r.propertyWild());
  }    
  @Test public void wild_5(){
    Record r = Record.makeQuery("wild","*","ISA","*");
    assertFalse(r.entityWild());
    assertFalse(r.relationWild());
    assertFalse(r.propertyWild());
  }    
  @Test public void wild_6(){
    Record r = Record.makeQuery("wild","*","WILD","wild");
    assertFalse(r.entityWild());
    assertFalse(r.relationWild());
    assertTrue(r.propertyWild());
  }    


  // Test whether records/queries match or not properly
  @Test public void matches_1(){
    Record r = Record.makeRecord("Alf","ISA","alien");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_2(){
    Record r = Record.makeQuery("*","Alf","ISA","*");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_3(){
    Record r = Record.makeQuery("Wild","Wild","ISA","alien");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_4(){
    Record r = Record.makeQuery("*","Alf","*","alien");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_5(){
    Record r = Record.makeQuery("*","Alf","*","*");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_6(){
    Record r = Record.makeQuery("crazy","crazy","ISA","crazy");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_7(){
    Record r = Record.makeQuery("*","*","*","alien");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_8(){
    Record r = Record.makeQuery("*","*","*","*");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_9(){
    Record r = Record.makeQuery("wild","*","*","*");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertFalse(r.matches(q));
    assertFalse(q.matches(r));
  }    
  @Test public void matches_10(){
    Record r = Record.makeQuery("*","Alf","ISA","alien");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_11(){
    Record r = Record.makeQuery("wild","Lucky","ISA","cat");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertFalse(r.matches(q));
    assertFalse(q.matches(r));
  }    
  @Test public void matches_12(){
    Record r = Record.makeQuery("wild","wild","ISA","cat");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertFalse(r.matches(q));
    assertFalse(q.matches(r));
  }    
  @Test public void matches_13(){
    Record r = Record.makeQuery("wild","wild","ISA","wild");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertTrue(r.matches(q));
    assertTrue(q.matches(r));
  }    
  @Test public void matches_14(){
    Record r = Record.makeQuery("wild","wild","EATS","wild");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertFalse(r.matches(q));
    assertFalse(q.matches(r));
  }    
  // Case of strings matters
  @Test public void matches_15(){
    Record r = Record.makeQuery("wild","wild","EATS","wild");
    Record q = Record.makeRecord("Alf","eats","cat");
    assertFalse(r.matches(q));
    assertFalse(q.matches(r));
  }    

  // Utility to do a comparison with a Comparator and evaluate if the
  // result matches the expected sign
  public static void
    doCompare(Record small, Record big, 
       Comparator<Record> comp, int sign){
    int cmp = comp.compare(small,big);
    if((sign > 0 && cmp <= 0) ||
       (sign < 0 && cmp >= 0) ||
       (sign ==0 && cmp != 0)){
      String msg = String.format("%d: [%s] should be smaller than [%s]",
     cmp,small,big);
      fail(msg);
    }

    cmp = comp.compare(big,small);
    if((-sign > 0 && cmp <= 0) ||
       (-sign < 0 && cmp >= 0) ||
       (-sign ==0 && cmp != 0)){
      String msg = String.format("%d: [%s] should be bigger than [%s]",
     cmp,big,small);
      fail(msg);
    }

  }    

  // Run tests on comparators
  @Test public void ERPCompare_1(){
    doCompare(Record.makeQuery("*","*","*","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.ERPCompare, -1);
  }
  @Test public void ERPCompare_2(){
    doCompare(Record.makeQuery("*","Alf","*","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.ERPCompare, -1);
  }
  @Test public void ERPCompare_3(){
    doCompare(Record.makeQuery("*","Alf","ISA","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.ERPCompare, -1);
  }
  @Test public void ERPCompare_4(){
    doCompare(Record.makeQuery("*","Alf","EATS","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.ERPCompare, -1);
  }
  @Test public void ERPCompare_5(){
    doCompare(Record.makeRecord("Alf","ISA","alien"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.ERPCompare, 0);
  }

  @Test public void RPECompare_1(){
    doCompare(Record.makeQuery("*","*","*","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.RPECompare, -1);
  }
  @Test public void RPECompare_2(){
    doCompare(Record.makeQuery("*","Alf","*","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.RPECompare, -1);
  }
  @Test public void RPECompare_3(){
    doCompare(Record.makeQuery("*","Alf","ISA","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.RPECompare, -1);
  }
  @Test public void RPECompare_4(){
    doCompare(Record.makeQuery("*","Alf","EATS","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.RPECompare, -1);
  }
  @Test public void RPECompare_5(){
    doCompare(Record.makeRecord("Alf","ISA","alien"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.RPECompare, 0);
  }
  @Test public void RPECompare_6(){
    doCompare(Record.makeQuery("*","*","ISA","*"),
       Record.makeRecord("Alf","ISA","alien"),
       Record.RPECompare, -1);
  }

  @Test public void PERCompare_1(){
    doCompare(Record.makeQuery("*","*","*","*"),
         Record.makeRecord("Alf","ISA","alien"),
         Record.PERCompare, -1);
  }
  @Test public void PERCompare_2(){
    doCompare(Record.makeQuery("*","Alf","*","*"),
         Record.makeRecord("Alf","ISA","alien"),
         Record.PERCompare, -1);
  }
  @Test public void PERCompare_3(){
    doCompare(Record.makeQuery("wild","Alf","ISA","wild"),
         Record.makeRecord("Alf","ISA","alien"),
         Record.PERCompare, -1);
  }
  @Test public void PERCompare_4(){
    doCompare(Record.makeQuery("*","Alf","*","alien"),
         Record.makeRecord("Alf","ISA","alien"),
         Record.PERCompare, -1);
  }
  @Test public void PERCompare_5(){
    doCompare(Record.makeRecord("Alf","ISA","alien"),
         Record.makeRecord("Alf","ISA","alien"),
         Record.PERCompare, 0);
  }
  @Test public void PERCompare_6(){
    doCompare(Record.makeRecord("Lucky","LIKESTO","purr"),
         Record.makeQuery("*","Lucky","*","purr"),
         Record.PERCompare, +1);
  }

  public Record [] makeRecords(String [][] a){
    Record [] recs = new Record[a.length];
    for(int i=0; i<a.length; i++){
      String [] rec = a[i];
      if(rec.length==3){
        recs[i] = Record.makeRecord(rec[0],rec[1],rec[2]);
      }
      else{
        recs[i] = Record.makeQuery(rec[3], rec[0],rec[1],rec[2]);
      }
    }
    return recs;
  }

  public Random rand = new Random(123456789);
  // Randomize the contents of an array based on repeated swaps
  public void randomize(Object [] a, int nIters){
    for(int iter=0; iter<nIters; iter++){
      int i = rand.nextInt(a.length);
      int j = rand.nextInt(a.length);
      Object tmp = a[i];
      a[i] = a[j];
      a[j] = tmp;
    }
  }

  // Test comparators by sorting arrays of records and check that the
  // ordering of the array matched the expectation
  @Test public void sort_test_1(){
    String [][] expectS = {
      {     "Alf",     "EATS",        "cat"},
      {     "Alf",     "EATS",    "veggies"},
      {     "Alf",      "ISA",      "alien"},
      {   "Lucky",     "EATS",    "catfood"},
      {   "Lucky",      "ISA",        "cat"},
      {   "Lucky",  "LIKESTO",       "purr"},
      {    "Lynn",     "EATS",    "veggies"},
      {    "Lynn",      "ISA",      "human"},
      {  "Willie",      "ISA",      "human"},
    };
    Record [] expect = makeRecords(expectS);
    Record [] actual = Arrays.copyOf(expect,expect.length);
    randomize(actual,50);
    Arrays.sort(actual, Record.ERPCompare);
    String msg = String.format("\nExpect Sort Order: %s\nActual Sort Order: %s",
                               Arrays.toString(expect),Arrays.toString(actual));
    assertTrue(msg,Arrays.deepEquals(expect,actual));
  }

  @Test public void sort_test_2(){
    String [][] expectS = {
      {    "WILD",  "LIKESTO",       "purr", "WILD"},
      {     "Alf",     "EATS",          "*", "*"},
      {     "Alf",     "EATS",    "veggies"},
      {     "Alf",      "ISA",      "alien"},
      {   "Lucky",        "*",          "*", "*"},
      {   "Lucky",     "EATS",    "catfood"},
      {   "Lucky",      "ISA",        "cat"},
      {   "Lucky",  "LIKESTO",       "purr"},
      {    "Lynn",     "EATS",    "veggies"},
      {    "Lynn",      "ISA",      "human"},
      {  "Willie",      "ISA",      "human"},
    };
    Record [] expect = makeRecords(expectS);
    Record [] actual = Arrays.copyOf(expect,expect.length);
    randomize(actual,50);
    Arrays.sort(actual, Record.ERPCompare);
    String msg = String.format("\nExpect Sort Order: %s\nActual Sort Order: %s",
                               Arrays.toString(expect),Arrays.toString(actual));
    assertTrue(msg,Arrays.deepEquals(expect,actual));
  }

  @Test public void sort_test_3(){
    String [][] expectS = {
      {   "Lucky",        "*",          "*", "*"},
      {     "Alf",     "EATS",          "*", "*"},
      {   "Lucky",     "EATS",    "catfood"},
      {     "Alf",     "EATS",    "veggies"},
      {    "Lynn",     "EATS",    "veggies"},
      {     "Alf",      "ISA",      "alien"},
      {   "Lucky",      "ISA",        "cat"},
      {    "Lynn",      "ISA",      "human"},
      {  "Willie",      "ISA",      "human"},
      {    "WILD",  "LIKESTO",       "purr", "WILD"},
      {   "Lucky",  "LIKESTO",       "purr"},
    };
    Record [] expect = makeRecords(expectS);
    Record [] actual = Arrays.copyOf(expect,expect.length);
    randomize(actual,50);
    Arrays.sort(actual, Record.RPECompare);
    String msg = String.format("\nExpect Sort Order: %s\nActual Sort Order: %s",
                               Arrays.toString(expect),Arrays.toString(actual));
    assertTrue(msg,Arrays.deepEquals(expect,actual));
  }

  @Test public void sort_test_4(){
    String [][] expectS = {
      {     "Alf",     "EATS",          "*", "*"},
      {   "Lucky",        "*",          "*", "*"},
      {    "WILD",  "LIKESTO",          "*", "WILD"},
      {     "Alf",      "ISA",      "alien"},
      {   "Lucky",      "ISA",        "cat"},
      {   "Lucky",     "EATS",    "catfood"},
      {    "Lynn",      "ISA",      "human"},
      {  "Willie",      "ISA",      "human"},
      {    "WILD",  "LIKESTO",       "purr", "WILD"},
      {   "Lucky",  "LIKESTO",       "purr"},
      {     "Alf",      "XYZ",    "veggies", "XYZ"},
      {     "Alf",     "EATS",    "veggies"},
      {    "Lynn",     "EATS",    "veggies"},
    };
    Record [] expect = makeRecords(expectS);
    Record [] actual = Arrays.copyOf(expect,expect.length);
    randomize(actual,50);
    Arrays.sort(actual, Record.PERCompare);
    String msg = String.format("\nExpect Sort Order: %s\nActual Sort Order: %s",
                               Arrays.toString(expect),Arrays.toString(actual));
    assertTrue(msg,Arrays.deepEquals(expect,actual));
  }

  // Check if ids created are unique
  @Test public void id_not_equal_1(){
    Record r = Record.makeRecord("Alf","ISA","alien");
    Record q = Record.makeRecord("Lucky","ISA","cat");
    assertFalse(r.id()==q.id());
  }    
  @Test public void id_not_equal_2(){
    Record r = Record.makeRecord("Alf","ISA","alien");
    Record q = Record.makeRecord("Alf","ISA","alien");
    assertFalse(r.id()==q.id());
  }    
  @Test public void id_not_equal_3(){
    Record r = Record.makeRecord("Alf","ISA","alien");
    Record q = Record.makeQuery("*","Alf","ISA","alien");
    assertFalse(r.id()==q.id());
  }    


  @Test public void toString_1(){
    Record r = Record.makeRecord("A","B","C");
    String expect = "       A        B        C ";
    assertEquals(expect,r.toString());
  }    
  @Test public void toString_2(){
    Record r = Record.makeRecord("Alf","ISA","alien");
    String expect = "     Alf      ISA    alien ";
    assertEquals(expect,r.toString());
  }    
  @Test public void toString_3(){
    Record r = Record.makeRecord("123456","12345678","1234");
    String expect = "  123456 12345678     1234 ";
    assertEquals(expect,r.toString());
  }    
  @Test public void toString_4(){
    Record r = Record.makeRecord("1234567890","123456789","123456789012");
    String expect = "1234567890 123456789 123456789012 ";
    assertEquals(expect,r.toString());
  }    

}