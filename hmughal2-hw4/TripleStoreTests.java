// Initial Release Thu Dec  1 00:06:51 EST 2016 
// HW 4 Final Tests: Runs RecordTests TripleStoreTests
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;


public class TripleStoreTests {
  public static void main(String args[])
  {
    org.junit.runner.JUnitCore.main("TripleStoreTests");
  } 
    
    
  @Test public void constructor_1(){
    TripleStore t = new TripleStore();
  }

  @Test public void add_1(){
    TripleStore t = new TripleStore();
    boolean b;
    b = t.add("Alf","ISA","alien");
    assertTrue("add should return true",b);
  }
  @Test public void add_2(){
    TripleStore t = new TripleStore();

    boolean b;
    b = t.add("Alf","ISA","alien");
    assertTrue("add should return true",b);
    b = t.add("Lucky","ISA","cat");
    assertTrue("add should return true",b);
    b = t.add("Lucky","ISA","cat");
    assertFalse("add should return false",b);
  }

  // Check if two records are equal based on each of their fields
  // being equal strings and each of their wild properties being equal
  public static boolean recordsEqual(Record r, Record t){
  //        System.out.println("r" + r.entity() + " t " + t.entity());
   //             System.out.println("r" + r.relation() + " t " + t.relation()); 
   //                System.out.println("r" + r.property() + " t " + t.property());
   //                   System.out.println("r" + r.wildcard + " t " + t.wildcard);
    return
      r.entity().equals(t.entity()) &&
      r.relation().equals(t.relation()) &&
     
      r.property().equals(t.property()) &&
        
      r.entityWild() == t.entityWild() &&
        
      r.relationWild() == t.relationWild() &&
      r.propertyWild() == t.propertyWild();
  }

  // Utility to check that the results of a query are correct.  An
  // array of matching records and an array of non-matching records is
  // passed in along with a query record. A TripleStore is created
  // with all given records and t.query(..) is called; the results are
  // checked to match the records expected in matches

  public void check_query(Record [] matches, Record [] extras, Record query){
    check_query(matches, extras, query, (new TripleStore()).getWild());
  }
  // Allows the default wild card to be changed
  public static void check_query(Record [] matches, Record [] extras, Record query, String wild){
    TripleStore t = new TripleStore();
    t.setWild(wild);
    boolean b; String msg;
    for(Record r : matches){
      b = t.add(r.entity(), r.relation(), r.property());
    }
    for(Record r : extras){
      b = t.add(r.entity(), r.relation(), r.property());
    }
    List<Record> results = t.query(query.entity(), query.relation(), query.property());
    for(int i=0; i<matches.length; i++){
      Record matchi = matches[i];
      boolean found = false;
      int j; Record reci;
      for(j=0; j<results.size(); j++){
 reci = results.get(j);
 if(recordsEqual(reci,matchi)){
   found = true;
   break;
 }
      }
      if(!found){
 msg = String.format("\nQuery Results missing %s\nQuery: %s\nWild: %s\nResults: %s\nDB:\n%s\n",
                            matchi,query,wild,results,t);
 fail(msg);
      }
    }
  }
  
  // See docs for check_query(..) to understand tests which use it

  @Test public void query_1(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record query = Record.makeQuery("*","Alf","ISA","alien");
    check_query(matches,extras,query);
  }
  @Test public void query_2(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Alf","EATS","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record query = Record.makeQuery("*","Alf","*","*");
    check_query(matches,extras,query);
  }
  @Test public void query_3(){
    Record matches[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Alf","EATS","cat"),
    };
    Record query = Record.makeQuery("*","Lucky","ISA","*");
    check_query(matches,extras,query);
  }
  @Test public void query_4(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","EATS","cat"),
    };
    Record query = Record.makeQuery("*","*","ISA","alien");
    check_query(matches,extras,query);
  }
  @Test public void query_5(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
    };
    Record query = Record.makeQuery("*","*","ISA","*");
    check_query(matches,extras,query);
  }
  @Test public void query_6(){
    Record matches[] = new Record[]{
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("*","*","ISA","*");
    check_query(matches,extras,query);
  }
  @Test public void query_7(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
    };
    Record query = Record.makeQuery("*","*","*","cat");
    check_query(matches,extras,query);
  }
  @Test public void query_8(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lucky","EATS","catfood"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
    };
    Record query = Record.makeQuery("*","*","*","cat");
    check_query(matches,extras,query);
  }
  @Test public void query_9(){
    Record matches[] = new Record[]{
      Record.makeRecord("A","I","X"),
      Record.makeRecord("A","I","Y"),
      Record.makeRecord("A","I","Z"),
      Record.makeRecord("A","J","X"),
      Record.makeRecord("A","J","Y"),
      Record.makeRecord("A","J","Z"),
      Record.makeRecord("A","K","X"),
      Record.makeRecord("A","K","Y"),
      Record.makeRecord("A","K","Z"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("B","I","X"),
      Record.makeRecord("B","I","Y"),
      Record.makeRecord("B","I","Z"),
      Record.makeRecord("B","J","X"),
      Record.makeRecord("B","J","Y"),
      Record.makeRecord("B","J","Z"),
      Record.makeRecord("B","K","X"),
      Record.makeRecord("B","K","Y"),
      Record.makeRecord("B","K","Z"),
      Record.makeRecord("C","I","X"),
      Record.makeRecord("C","I","Y"),
      Record.makeRecord("C","I","Z"),
      Record.makeRecord("C","J","X"),
      Record.makeRecord("C","J","Y"),
      Record.makeRecord("C","J","Z"),
      Record.makeRecord("C","K","X"),
      Record.makeRecord("C","K","Y"),
      Record.makeRecord("C","K","Z"),
    };
    Record query = Record.makeQuery("#","A","#","#");
    check_query(matches,extras,query,"#");
  }
  @Test public void query_10(){
    Record matches[] = new Record[]{
      Record.makeRecord("C","J","X"),
      Record.makeRecord("C","J","Y"),
      Record.makeRecord("C","J","Z"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("A","I","X"),
      Record.makeRecord("A","I","Y"),
      Record.makeRecord("A","I","Z"),
      Record.makeRecord("A","J","X"),
      Record.makeRecord("A","J","Y"),
      Record.makeRecord("A","J","Z"),
      Record.makeRecord("A","K","X"),
      Record.makeRecord("A","K","Y"),
      Record.makeRecord("A","K","Z"),
      Record.makeRecord("B","I","X"),
      Record.makeRecord("B","I","Y"),
      Record.makeRecord("B","I","Z"),
      Record.makeRecord("B","J","X"),
      Record.makeRecord("B","J","Y"),
      Record.makeRecord("B","J","Z"),
      Record.makeRecord("B","K","X"),
      Record.makeRecord("B","K","Y"),
      Record.makeRecord("B","K","Z"),
      Record.makeRecord("C","I","X"),
      Record.makeRecord("C","I","Y"),
      Record.makeRecord("C","I","Z"),
      Record.makeRecord("C","K","X"),
      Record.makeRecord("C","K","Y"),
      Record.makeRecord("C","K","Z"),
    };
    Record query = Record.makeQuery("#","C","J","#");
    check_query(matches,extras,query,"#");
  }
  @Test public void query_11(){
    Record matches[] = new Record[]{
      Record.makeRecord("C","K","Z"),
      Record.makeRecord("A","K","Z"),
      Record.makeRecord("B","K","Z"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("A","I","X"),
      Record.makeRecord("A","I","Y"),
      Record.makeRecord("A","I","Z"),
      Record.makeRecord("A","J","X"),
      Record.makeRecord("A","J","Y"),
      Record.makeRecord("A","J","Z"),
      Record.makeRecord("A","K","X"),
      Record.makeRecord("A","K","Y"),
      Record.makeRecord("B","I","X"),
      Record.makeRecord("B","I","Y"),
      Record.makeRecord("B","I","Z"),
      Record.makeRecord("B","J","X"),
      Record.makeRecord("B","J","Y"),
      Record.makeRecord("B","J","Z"),
      Record.makeRecord("B","K","X"),
      Record.makeRecord("B","K","Y"),
      Record.makeRecord("C","I","X"),
      Record.makeRecord("C","I","Y"),
      Record.makeRecord("C","I","Z"),
      Record.makeRecord("C","J","X"),
      Record.makeRecord("C","J","Y"),
      Record.makeRecord("C","J","Z"),
      Record.makeRecord("C","K","X"),
      Record.makeRecord("C","K","Y"),
    };
    Record query = Record.makeQuery("wild","wild","K","Z");
    check_query(matches,extras,query,"wild");
  }
  @Test public void query_12(){
    Record matches[] = new Record[]{
      Record.makeRecord("A","I","X"),
      Record.makeRecord("A","I","Y"),
      Record.makeRecord("A","I","Z"),
      Record.makeRecord("B","I","X"),
      Record.makeRecord("B","I","Y"),
      Record.makeRecord("B","I","Z"),
      Record.makeRecord("C","I","X"),
      Record.makeRecord("C","I","Y"),
      Record.makeRecord("C","I","Z"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("C","K","Z"),
      Record.makeRecord("A","K","Z"),
      Record.makeRecord("B","K","Z"),
      Record.makeRecord("A","J","X"),
      Record.makeRecord("A","J","Y"),
      Record.makeRecord("A","J","Z"),
      Record.makeRecord("A","K","X"),
      Record.makeRecord("A","K","Y"),
      Record.makeRecord("B","J","X"),
      Record.makeRecord("B","J","Y"),
      Record.makeRecord("B","J","Z"),
      Record.makeRecord("B","K","X"),
      Record.makeRecord("B","K","Y"),
      Record.makeRecord("C","J","X"),
      Record.makeRecord("C","J","Y"),
      Record.makeRecord("C","J","Z"),
      Record.makeRecord("C","K","X"),
      Record.makeRecord("C","K","Y"),
    };
    Record query = Record.makeQuery("wild","wild","I","wild");
    check_query(matches,extras,query,"wild");
  }
  @Test public void query_13(){
    Record matches[] = new Record[]{
      Record.makeRecord("C","K","Y"),
      Record.makeRecord("C","J","Y"),
      Record.makeRecord("C","I","Y"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("A","I","X"),
      Record.makeRecord("A","I","Y"),
      Record.makeRecord("A","I","Z"),
      Record.makeRecord("A","J","X"),
      Record.makeRecord("A","J","Y"),
      Record.makeRecord("A","J","Z"),
      Record.makeRecord("A","K","X"),
      Record.makeRecord("A","K","Y"),
      Record.makeRecord("A","K","Z"),
      Record.makeRecord("B","I","X"),
      Record.makeRecord("B","I","Y"),
      Record.makeRecord("B","I","Z"),
      Record.makeRecord("B","J","X"),
      Record.makeRecord("B","J","Y"),
      Record.makeRecord("B","J","Z"),
      Record.makeRecord("B","K","X"),
      Record.makeRecord("B","K","Y"),
      Record.makeRecord("B","K","Z"),
      Record.makeRecord("C","I","X"),
      Record.makeRecord("C","I","Z"),
      Record.makeRecord("C","J","X"),
      Record.makeRecord("C","J","Z"),
      Record.makeRecord("C","K","X"),
      Record.makeRecord("C","K","Z"),
    };
    Record query = Record.makeQuery("!","C","!","Y");
    check_query(matches,extras,query,"!");
  }
  @Test public void query_14(){
    Record matches[] = new Record[]{
      Record.makeRecord("C","I","Z"),
      Record.makeRecord("C","K","Z"),
      Record.makeRecord("A","I","Z"),
      Record.makeRecord("A","K","Z"),
      Record.makeRecord("B","I","Z"),
      Record.makeRecord("B","J","Z"),
      Record.makeRecord("B","K","Z"),
      Record.makeRecord("C","J","Z"),
      Record.makeRecord("A","J","Z"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("A","I","X"),
      Record.makeRecord("A","I","Y"),
      Record.makeRecord("A","J","X"),
      Record.makeRecord("A","J","Y"),
      Record.makeRecord("A","K","X"),
      Record.makeRecord("A","K","Y"),
      Record.makeRecord("B","I","X"),
      Record.makeRecord("B","I","Y"),
      Record.makeRecord("B","J","X"),
      Record.makeRecord("B","J","Y"),
      Record.makeRecord("B","K","X"),
      Record.makeRecord("B","K","Y"),
      Record.makeRecord("C","I","X"),
      Record.makeRecord("C","I","Y"),
      Record.makeRecord("C","J","X"),
      Record.makeRecord("C","J","Y"),
      Record.makeRecord("C","K","X"),
      Record.makeRecord("C","K","Y"),
    };
    Record query = Record.makeQuery("!","!","!","Z");
    check_query(matches,extras,query,"!");
  }


  // Similar to check_query(..) except that the given query is used
  // with t.remove() and the TripleStore is checked that all the
  // matches[] records are removed and only the extras[] records
  // remain.
  public static void check_remove(Record [] matches, Record [] extras, Record query){
    check_remove(matches, extras, query,(new TripleStore()).getWild());
  }

  // Utility to check that the results of a remove are correct
  public static void check_remove(Record [] matches, Record [] extras, Record query, String wild){
    TripleStore t = new TripleStore();
    t.setWild(wild);
    boolean b; String msg;
    for(Record r : matches){
      b = t.add(r.entity(), r.relation(), r.property());
    }
    for(Record r : extras){
      b = t.add(r.entity(), r.relation(), r.property());
    }

    int nrm = t.remove(query.entity(), query.relation(), query.property());
    msg = String.format("Should have removed %d items, got %d\nt.wild() = %s\n%s", matches.length, nrm,t.getWild(),t);
    assertEquals(msg, matches.length, nrm);
    List<Record> remaining = t.query(wild,wild,wild);
    msg = String.format("Remaining should have %d items, got %d\nt.wild() = %s\n%s ", extras.length, remaining.size(),t.getWild(),t);
    assertEquals(msg, extras.length, remaining.size());
    for(int i=0; i<extras.length; i++){
      Record extrai = extras[i];
      boolean found = false;
      int j; Record remaini;
      for(j=0; j<remaining.size(); j++){
 remaini = remaining.get(j);
 if(recordsEqual(remaini,extrai)){
   found = true;
   break;
 }
      }
      if(!found){
 msg = String.format("Remaining missing %s\nDB:\n%s\n", extrai,t);
 fail(msg);
      }
      remaini = remaining.get(j);
      msg = String.format("Remaining wrong:\nExpect: %s\nGot   : %s\n",
     extrai,remaini);
      assertTrue(msg,recordsEqual(remaini,extrai));
    }
  }

  // See check_remove(..) to see how these tests work

  @Test public void remove_1(){
    Record matches[] = new Record[]{
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("*","*","ISA","*");
    check_remove(matches,extras,query);
  }
  @Test public void remove_2(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Alf","EATS","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("*","Alf","*","*");
    check_remove(matches,extras,query);
  }
  @Test public void remove_3(){
    Record matches[] = new Record[]{
      Record.makeRecord("Alf","ISA","alien"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("*","Alf","ISA","alien");
    check_remove(matches,extras,query);
  }
  @Test public void remove_4(){
    Record matches[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","EATS","cat"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("*","*","*","cat");
    check_remove(matches,extras,query);
  }
  @Test public void remove_5(){
    Record matches[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record extras[] = new Record[]{
    };
    Record query = Record.makeQuery("*","*","*","*");
    check_remove(matches,extras,query);
  }
  @Test public void remove_6(){
    Record matches[] = new Record[]{
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("&","*","*","*");
    check_remove(matches,extras,query,"&");
  }
  @Test public void remove_7(){
    Record matches[] = new Record[]{
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Willie","ISA","human"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("&","&","ISA","human");
    check_remove(matches,extras,query,"&");
  }
  @Test public void remove_8(){
    Record matches[] = new Record[]{
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Alf","EATS","veggies"),
      Record.makeRecord("Carrot","ISA","veggies"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("&","&","&","veggies");
    check_remove(matches,extras,query,"&");
  }

  @Test public void wild_card_1(){
    Record matches[] = new Record[]{
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Willie","ISA","*"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","ISA","*"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("*","*","ISA","*");
    check_query(matches,extras,query,"*");
  }
  @Test public void wild_card_2(){
    Record matches[] = new Record[]{
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Willie","ISA","*"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","ISA","*"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
    };
    Record query = Record.makeQuery("wild","wild","ISA","wild");
    check_query(matches,extras,query,"wild");
  }
  @Test public void wild_card_3(){
    Record matches[] = new Record[]{
      Record.makeRecord("Willie","ISA","*"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","ISA","*"),
    };
    Record query = Record.makeQuery("wild","Willie","ISA","*");
    check_query(matches,extras,query,"wild");
  }
  @Test public void wild_card_4(){
    Record matches[] = new Record[]{
      Record.makeRecord("Willie","ISA","*"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("wild","ISA","hockeyteam"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","ISA","*"),
    };
    Record extras[] = new Record[]{
    };
    Record query = Record.makeQuery("wild","wild","wild","wild");
    check_query(matches,extras,query,"wild");
  }
  @Test public void wild_card_5(){
    Record matches[] = new Record[]{
      Record.makeRecord("wild","ISA","hockeyteam"),
    };
    Record extras[] = new Record[]{
      Record.makeRecord("Willie","ISA","*"),
      Record.makeRecord("Willie","ISA","human"),
      Record.makeRecord("Alf","ISA","alien"),
      Record.makeRecord("Alf","EATS","cat"),
      Record.makeRecord("Lynn","EATS","veggies"),
      Record.makeRecord("Lucky","LIKESTO","purr"),
      Record.makeRecord("Lynn","ISA","human"),
      Record.makeRecord("Lucky","ISA","cat"),
      Record.makeRecord("Alf","ISA","*"),
    };
    Record query = Record.makeQuery("*","wild","*","*");
    check_query(matches,extras,query,"*");
  }

  
  // Ensure that nulls cannot be added to the TripleStore which should
  // throw an IllegalArgumentException if null is added
  @Test public void no_nulls1(){
    TripleStore t = new TripleStore();
    boolean b;
    try{
      b = t.add(null,"ISA","alien");
    }
    catch(IllegalArgumentException e){
      return;
    }
    fail("Should throw IllegalArgumentException when trying to add nulls to TripleStore");
  }
  @Test public void no_nulls2(){
    TripleStore t = new TripleStore();
    try{
      t.query("*",null,"alien");
    }
    catch(IllegalArgumentException e){
      return;
    }
    fail("Should throw IllegalArgumentException when trying to query nulls with TripleStore");
  }
  @Test public void no_nulls3(){
    TripleStore t = new TripleStore();
    try{
      t.remove("*","*",null);
    }
    catch(IllegalArgumentException e){
      return;
    }
    fail("Should throw IllegalArgumentException when trying to remove nulls with TripleStore");
  }

  // Test to string method is formatted properly
  @Test public void toString_1(){
    TripleStore t = new TripleStore();
    assertTrue(t.add("A","I","X"));
    assertTrue(t.add("A","I","Y"));
    assertTrue(t.add("A","I","Z"));
    assertTrue(t.add("A","J","X"));
    assertTrue(t.add("A","J","Y"));
    assertTrue(t.add("A","J","Z"));
    assertTrue(t.add("A","K","X"));
    assertTrue(t.add("A","K","Y"));
    assertTrue(t.add("A","K","Z"));
    String expect =
      "       A        I        X \n"+
      "       A        I        Y \n"+
      "       A        I        Z \n"+
      "       A        J        X \n"+
      "       A        J        Y \n"+
      "       A        J        Z \n"+
      "       A        K        X \n"+
      "       A        K        Y \n"+
      "       A        K        Z \n"+
      "";
    String msg = String.format("\nExpect:\n%sActual:\n%s",expect,t.toString());
    assertEquals(msg,expect,t.toString());
  }
  @Test public void toString_2(){
    TripleStore t = new TripleStore();
    t.add("Willie","ISA","human");
    t.add("Alf","ISA","alien");
    t.add("Alf","EATS","cat");
    t.add("Lynn","EATS","veggies");
    t.add("Lucky","LIKESTO","purr");
    t.add("Lynn","ISA","human");
    t.add("Lucky","ISA","cat");
    t.add("Alf","ISA","*");
    String expect =
      "     Alf     EATS      cat \n"+
      "     Alf      ISA        * \n"+
      "     Alf      ISA    alien \n"+
      "   Lucky      ISA      cat \n"+
      "   Lucky  LIKESTO     purr \n"+
      "    Lynn     EATS  veggies \n"+
      "    Lynn      ISA    human \n"+
      "  Willie      ISA    human \n"+
      "";
    String msg = String.format("\nExpect:\n%sActual:\n%s",expect,t.toString());
    assertEquals(msg,expect,t.toString());
  }
  @Test public void toString_3(){
    TripleStore t = new TripleStore();
    t.add("12345678","12345678","12345678");
    t.add("1","12","1234");
    t.add("1234567","123","123456");
    t.add("123456789","1234567890","123456789012");
    String expect =
      "       1       12     1234 \n"+
      " 1234567      123   123456 \n"+
      "12345678 12345678 12345678 \n"+
      "123456789 1234567890 123456789012 \n"+
      "";
    String msg = String.format("\nExpect:\n%sActual:\n%s",expect,t.toString());
    assertEquals(msg,expect,t.toString());
  }


  // Utility to assert two record lists are equal
  public void assertRecordListsEqual(List<Record> x, List<Record> y){
    Iterator<Record> xi = x.iterator();
    Iterator<Record> yi = y.iterator();
    boolean matching = true;
    while(xi.hasNext()){
      if(!yi.hasNext()){
        break;
      }
      Record xr = xi.next();
      Record yr = yi.next();
      if(!recordsEqual(xr,yr)){
        matching = false;
        break;
      }
    }
    if(!matching || xi.hasNext() || yi.hasNext()){
      String msg = String.format("\nRecord lists not equal:\nExpect: %s\nActual: %s",x,y);
      fail(msg);
    }
  }

  // Make sure that triple stores operate independently of one another
  @Test public void independence_1(){
    TripleStore t1 = new TripleStore();
    TripleStore t2 = new TripleStore();
    List<Record> results1, results2, expect;
    int nrm1, nrm2;

    t1.add("wild","ISA","hockeyteam");
    t1.add("Willie","ISA","*");
    t1.add("Willie","ISA","human");
    t1.add("Alf","ISA","alien");
    t1.add("Alf","EATS","cat");
    t1.add("Lynn","EATS","veggies");
    t1.add("Lucky","LIKESTO","purr");
    t1.add("Lynn","ISA","human");
    t1.add("Lucky","ISA","cat");
    t1.add("Alf","ISA","*");

    t2.add("wild","ISA","hockeyteam");
    t2.add("Willie","ISA","*");
    t2.add("Willie","ISA","human");
    t2.add("Alf","ISA","alien");
    t2.add("Alf","EATS","cat");

    t2.setWild("#");

    t2.add("Lynn","EATS","veggies");
    t2.add("Lucky","LIKESTO","purr");
    t2.add("Lynn","ISA","human");
    t2.add("Lucky","ISA","cat");
    t2.add("Alf","ISA","*");
    
    results1 = t1.query("*","*","*");
    results2 = t2.query("#","#","#");
    assertRecordListsEqual(results1,results2);

    nrm1 = t1.remove("Alf","ISA","*");
    assertEquals(2,nrm1);

    nrm2 = t2.remove("Alf","ISA","*");
    assertEquals(1,nrm2);
    
    nrm2 = t2.remove("Alf","ISA","alien");
    assertEquals(1,nrm2);

    results1 = t1.query("*","*","*");
    results2 = t2.query("#","#","#");
    assertRecordListsEqual(results1,results2);

    nrm1 = t1.remove("*","*","human");
    assertEquals(2,nrm1);

    nrm2 = t2.remove("*","*","human");
    assertEquals(0,nrm2);

    t1.setWild("Lucky");
    nrm1 = t1.remove("Lucky","Lucky","cat");
    assertEquals(2,nrm1);
    
    results1 = t1.query("Lucky","Lucky","Lucky");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("Lucky","LIKESTO","purr"),
        Record.makeRecord("Lynn","EATS","veggies"),
        Record.makeRecord("Willie","ISA","*"),
        Record.makeRecord("wild","ISA","hockeyteam"),
      });
    Collections.sort(results1,Record.ERPCompare);
    assertRecordListsEqual(expect,results1);

    nrm2 = t2.remove("Lucky","Lucky","cat");
    assertEquals(0,nrm2);

    results2 = t2.query("#","#","#");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("Alf","EATS","cat"),
        Record.makeRecord("Lucky","ISA","cat"),
        Record.makeRecord("Lucky","LIKESTO","purr"),
        Record.makeRecord("Lynn","EATS","veggies"),
        Record.makeRecord("Lynn","ISA","human"),
        Record.makeRecord("Willie","ISA","*"),
        Record.makeRecord("Willie","ISA","human"),
        Record.makeRecord("wild","ISA","hockeyteam"),
      });
    Collections.sort(results2,Record.ERPCompare);
    assertRecordListsEqual(expect,results2);
  }

  @Test public void stress_1(){
    int nrm;     List<Record> actual, expect;
    TripleStore t = new TripleStore();
    t.setWild("*");
    t.add("wild","ISA","hockeyteam");
    t.add("Willie","ISA","*");
    t.add("Willie","ISA","human");
    t.add("Alf","ISA","alien");
    t.add("Alf","EATS","cat");
    t.add("Lynn","EATS","meat");
    t.add("Lucky","LIKESTO","purr");
    t.add("Lynn","ISA","human");
    t.add("Lynn","PETS","cat");
    t.add("Lucky","ISA","cat");
    t.add("*","ISA","asterisk");
    t.add("Lynn","EATS","veggies");
    t.add("Alf","ISA","*");

    nrm = t.remove("*","*","human");
    assertEquals(2,nrm);

    t.setWild("X");
    actual = t.query("Lynn","X","X");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("Lynn","PETS","cat"),
        Record.makeRecord("Lynn","EATS","meat"),
        Record.makeRecord("Lynn","EATS","veggies"),
      });
    Collections.sort(actual,Record.PERCompare);
    assertRecordListsEqual(expect,actual);

    actual = t.query("X","ISA","asterisk");
    expect = Arrays.asList(new Record[]{Record.makeRecord("*","ISA","asterisk")});
    assertRecordListsEqual(expect,actual);

    actual = t.query("X","ISA","*");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("Alf","ISA","*"),
        Record.makeRecord("Willie","ISA","*"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    t.add("Lynn","ISA","human");
    t.add("Alf","MOCKS","human");
    t.add("X","MARKS","a spot");
    t.add("F","MARKS","failure");

    actual = t.query("X","MARKS","X");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("X","MARKS","a spot"),
        Record.makeRecord("F","MARKS","failure"),
      });
    Collections.sort(actual,Record.PERCompare);
    assertRecordListsEqual(expect,actual);
    
    t.setWild("*");
    actual = t.query("X","MARKS","*");
    expect = Arrays.asList(new Record[]{Record.makeRecord("X","MARKS","a spot"),});
    assertRecordListsEqual(expect,actual);
    
    t.setWild("WILD");
    actual = t.query("WILD","MOCKS","WILD");
    expect = Arrays.asList(new Record[]{Record.makeRecord("Alf","MOCKS","human"),});
    assertRecordListsEqual(expect,actual);

    actual = t.query("*","EATS","*");
    expect = Arrays.asList(new Record[]{});
    assertRecordListsEqual(expect,actual);

    t.add("Alf","EATS","veggies");
    
    actual = t.query("WILD","EATS","WILD");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("Alf","EATS","cat"),
        Record.makeRecord("Lynn","EATS","meat"),
        Record.makeRecord("Alf","EATS","veggies"),
        Record.makeRecord("Lynn","EATS","veggies"),
      });
    Collections.sort(actual,Record.RPECompare);
    assertRecordListsEqual(expect,actual);

    nrm = t.remove("Lynn","WILD","WILD");
    assertEquals(4,nrm);

    t.add("Willie","EATS","apples");
    
    t.setWild("*");
    actual = t.query("*","EATS","*");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("Willie","EATS","apples"),
        Record.makeRecord("Alf","EATS","cat"),
        Record.makeRecord("Alf","EATS","veggies"),
      });
    Collections.sort(actual,Record.PERCompare);
    assertRecordListsEqual(expect,actual);

    nrm = t.remove("*","MARKS","*");
    assertEquals(2,nrm);
    nrm = t.remove("Alf","*","*");
    assertEquals(5,nrm);
    nrm = t.remove("*","*","cat");
    assertEquals(1,nrm);
    
    actual = t.query("*","*","*");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("Willie","EATS","apples"),
        Record.makeRecord("Willie","ISA","*"),
        Record.makeRecord("*","ISA","asterisk"),
        Record.makeRecord("wild","ISA","hockeyteam"),
        Record.makeRecord("Lucky","LIKESTO","purr"),
      });
    Collections.sort(actual,Record.RPECompare);
    assertRecordListsEqual(expect,actual);
  }

  @Test public void stress_2(){
    int nrm;     List<Record> actual, expect;
    TripleStore t = new TripleStore();
    assertTrue(t.add("A","I","X"));
    assertTrue(t.add("A","I","Y"));
    assertTrue(t.add("A","I","Z"));
    assertTrue(t.add("A","J","X"));
    assertTrue(t.add("A","J","Y"));
    assertTrue(t.add("A","J","Z"));
    assertTrue(t.add("A","K","X"));
    assertTrue(t.add("A","K","Y"));
    assertTrue(t.add("A","K","Z"));

    assertTrue(t.add("B","I","X"));
    assertTrue(t.add("B","I","Y"));
    assertTrue(t.add("B","I","Z"));
    assertTrue(t.add("B","J","X"));
    assertTrue(t.add("B","J","Y"));
    assertTrue(t.add("B","J","Z"));
    assertTrue(t.add("B","K","X"));
    assertTrue(t.add("B","K","Y"));
    assertTrue(t.add("B","K","Z"));

    assertFalse(t.add("A","I","X"));
    assertFalse(t.add("A","I","Y"));
    assertFalse(t.add("A","I","Z"));
    assertFalse(t.add("A","J","X"));
    assertFalse(t.add("A","J","Y"));
    assertFalse(t.add("A","J","Z"));
    assertFalse(t.add("A","K","X"));
    assertFalse(t.add("A","K","Y"));
    assertFalse(t.add("A","K","Z"));

    assertTrue(t.add("C","I","X"));
    assertTrue(t.add("C","I","Y"));
    assertTrue(t.add("C","I","Z"));
    assertTrue(t.add("C","J","X"));
    assertTrue(t.add("C","J","Y"));
    assertTrue(t.add("C","J","Z"));
    assertTrue(t.add("C","K","X"));
    assertTrue(t.add("C","K","Y"));
    assertTrue(t.add("C","K","Z"));

    assertFalse(t.add("B","I","X"));
    assertFalse(t.add("B","I","Y"));
    assertFalse(t.add("B","I","Z"));
    assertFalse(t.add("B","J","X"));
    assertFalse(t.add("B","J","Y"));
    assertFalse(t.add("B","J","Z"));
    assertFalse(t.add("B","K","X"));
    assertFalse(t.add("B","K","Y"));
    assertFalse(t.add("B","K","Z"));

    actual = t.query("*","K","*");    
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","K","X"),
        Record.makeRecord("A","K","Y"),
        Record.makeRecord("A","K","Z"),
        Record.makeRecord("B","K","X"),
        Record.makeRecord("B","K","Y"),
        Record.makeRecord("B","K","Z"),
        Record.makeRecord("C","K","X"),
        Record.makeRecord("C","K","Y"),
        Record.makeRecord("C","K","Z"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    nrm = t.remove("*","K","*");
    assertEquals(9,nrm);

    actual = t.query("C","*","Z");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("C","I","Z"),
        Record.makeRecord("C","J","Z"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);
    
    actual = t.query("B","*","*");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("B","I","X"),
        Record.makeRecord("B","I","Y"),
        Record.makeRecord("B","I","Z"),
        Record.makeRecord("B","J","X"),
        Record.makeRecord("B","J","Y"),
        Record.makeRecord("B","J","Z"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    assertTrue(t.add("B","K","X"));
    assertTrue(t.add("B","K","Y"));
    assertTrue(t.add("B","K","Z"));

    actual = t.query("*","J","Z");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","J","Z"),
        Record.makeRecord("B","J","Z"),
        Record.makeRecord("C","J","Z"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);
    
    nrm = t.remove("*","*","Z");
    assertEquals(7,nrm);

    actual = t.query("*","J","Z");
    expect = Arrays.asList(new Record[]{
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    actual = t.query("B","K","*");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("B","K","X"),
        Record.makeRecord("B","K","Y"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    assertFalse(t.add("A","J","X"));
    assertTrue(t.add("A","J","Z"));
  }

  @Test public void stress_3(){
    int nrm;     List<Record> actual, expect;
    String msg, expectS, actualS;
    TripleStore t = new TripleStore();
    assertTrue(t.add("A","I","X"));
    assertTrue(t.add("A","I","Y"));
    assertTrue(t.add("A","I","Z"));
    assertTrue(t.add("A","J","X"));
    assertTrue(t.add("A","J","Y"));
    assertTrue(t.add("A","J","Z"));
    assertTrue(t.add("A","K","X"));
    assertTrue(t.add("A","K","Y"));
    assertTrue(t.add("A","K","Z"));


    assertTrue(t.add("B","I","X"));
    assertTrue(t.add("B","I","Y"));
    assertTrue(t.add("B","I","Z"));
    assertTrue(t.add("B","J","X"));
    assertTrue(t.add("B","J","Y"));
    assertTrue(t.add("B","J","Z"));
    assertTrue(t.add("B","K","X"));
    assertTrue(t.add("B","K","Y"));
    assertTrue(t.add("B","K","Z"));

    assertTrue(t.add("C","I","X"));
    assertTrue(t.add("C","I","Y"));
    assertTrue(t.add("C","I","Z"));
    assertTrue(t.add("C","J","X"));
    assertTrue(t.add("C","J","Y"));
    assertTrue(t.add("C","J","Z"));
    assertTrue(t.add("C","K","X"));
    assertTrue(t.add("C","K","Y"));
    assertTrue(t.add("C","K","Z"));


    t.setWild("A");
    TripleStore t2 = new TripleStore();
    for(Record r : t.query("A","J","Z")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,t2);
      assertTrue(msg, t2.add(r.entity(),r.relation(),r.property()));
    }

    expectS =
"       A        J        Z \n"+
"       B        J        Z \n"+
"       C        J        Z \n"+
"";
    actualS = t2.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    t2.remove("B","*","*");
    expectS =
"       A        J        Z \n"+
"       C        J        Z \n"+
"";
    actualS = t2.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    t.remove("A","A","Z");      // A is still wild card in t
    t.setWild("WILD");
    t.remove("WILD","J","WILD");
    
    expectS =
"       A        I        X \n"+
"       A        I        Y \n"+
"       A        K        X \n"+
"       A        K        Y \n"+
"       B        I        X \n"+
"       B        I        Y \n"+
"       B        K        X \n"+
"       B        K        Y \n"+
"       C        I        X \n"+
"       C        I        Y \n"+
"       C        K        X \n"+
"       C        K        Y \n"+
      "";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);
    
    t2.setWild("%");
    for(Record r : t2.query("%","J","Z")){
      t.add(r.entity(),r.relation(),r.property());
    }

    expectS =
"       A        I        X \n"+
"       A        I        Y \n"+
"       A        J        Z \n"+
"       A        K        X \n"+
"       A        K        Y \n"+
"       B        I        X \n"+
"       B        I        Y \n"+
"       B        K        X \n"+
"       B        K        Y \n"+
"       C        I        X \n"+
"       C        I        Y \n"+
"       C        J        Z \n"+
"       C        K        X \n"+
"       C        K        Y \n"+
      "";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);
    
    actual = t.query("*","K","*"); // t.getWild() is WILD
    expect = Arrays.asList(new Record[]{
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    t.setWild("@");
    actual = t.query("A","@","@");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","I","X"),
        Record.makeRecord("A","I","Y"),
        Record.makeRecord("A","J","Z"),
        Record.makeRecord("A","K","X"),
        Record.makeRecord("A","K","Y"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    for(Record r : t.query("@","@","X")){
      t2.add(r.entity(),r.relation(),r.property());
    }

    expectS =
"       A        I        X \n"+
"       A        J        Z \n"+
"       A        K        X \n"+
"       B        I        X \n"+
"       B        K        X \n"+
"       C        I        X \n"+
"       C        J        Z \n"+
"       C        K        X \n"+
      "";
    actualS = t2.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    actual = t2.query("C","%","X");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("C","I","X"),
        Record.makeRecord("C","K","X"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);
  }
  
  @Test public void case_matters_1(){
    int nrm;     List<Record> actual, expect;
    String msg, expectS, actualS;
    TripleStore t = new TripleStore();
    assertTrue(t.add("A","E","R"));
    assertTrue(t.add("A","E","r"));
    assertTrue(t.add("A","e","R"));
    assertTrue(t.add("A","e","r"));
    assertTrue(t.add("a","E","R"));
    assertTrue(t.add("a","E","r"));
    assertTrue(t.add("a","e","R"));
    assertTrue(t.add("a","e","r"));

    expectS =
"       A        E        R \n"+
"       A        E        r \n"+
"       A        e        R \n"+
"       A        e        r \n"+
"       a        E        R \n"+
"       a        E        r \n"+
"       a        e        R \n"+
"       a        e        r \n"+
"";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    actual = t.query("*","e","R");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","e","R"),
        Record.makeRecord("a","e","R"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    actual = t.query("*","*","r");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","E","r"),
        Record.makeRecord("A","e","r"),
        Record.makeRecord("a","E","r"),
        Record.makeRecord("a","e","r"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);
  }

  @Test public void multiple_ts_1(){
    int nrm;     List<Record> actual, expect;
    String msg, expectS, actualS;
    TripleStore ta = new TripleStore();
    assertTrue(ta.add("A","I","X"));
    assertTrue(ta.add("A","I","Y"));
    assertTrue(ta.add("A","I","Z"));
    assertTrue(ta.add("A","J","X"));
    assertTrue(ta.add("A","J","Y"));
    assertTrue(ta.add("A","J","Z"));

    TripleStore tb = new TripleStore();
    assertTrue(tb.add("B","I","X"));
    assertTrue(tb.add("B","I","Y"));
    assertTrue(tb.add("B","I","Z"));
    assertTrue(tb.add("B","J","X"));
    assertTrue(tb.add("B","J","Y"));
    assertTrue(tb.add("B","J","Z"));

    TripleStore t1 = new TripleStore();
    ta.setWild("%");
    tb.setWild("&");
    for(Record r : ta.query("A","%","X")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,t1);
      assertTrue(msg, t1.add(r.entity(),r.relation(),r.property()));
    }
    for(Record r : tb.query("&","&","Z")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,t1);
      assertTrue(msg, t1.add(r.entity(),r.relation(),r.property()));
    }

    expectS =
"       A        I        X \n"+
"       A        J        X \n"+
"       B        I        Z \n"+
"       B        J        Z \n"+
"";
    actualS = t1.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    actual = t1.query("*","J","*"); // wild for t1 is *
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","J","X"),
        Record.makeRecord("B","J","Z"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    t1.setWild("#");
    for(Record r : t1.query("B","#","#")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,ta);
      assertTrue(msg, ta.add(r.entity(),r.relation(),r.property()));
    }
    for(Record r : t1.query("B","#","#")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,tb);
      assertFalse(msg, tb.add(r.entity(),r.relation(),r.property()));
    }
  }

  @Test public void multiple_ts_2(){
    int nrm;     List<Record> actual, expect;
    String msg, expectS, actualS;
    TripleStore ta = new TripleStore();
    assertTrue(ta.add("A","I","X"));
    assertTrue(ta.add("A","I","Y"));
    assertTrue(ta.add("A","I","Z"));
    assertTrue(ta.add("A","J","X"));
    assertTrue(ta.add("A","J","Y"));
    assertTrue(ta.add("A","J","Z"));
    assertTrue(ta.add("A","K","X"));
    assertTrue(ta.add("A","K","Y"));
    assertTrue(ta.add("A","K","Z"));

    TripleStore tb = new TripleStore();
    assertTrue(tb.add("B","I","X"));
    assertTrue(tb.add("B","I","Y"));
    assertTrue(tb.add("B","I","Z"));
    assertTrue(tb.add("B","J","X"));
    assertTrue(tb.add("B","J","Y"));
    assertTrue(tb.add("B","J","Z"));
    assertTrue(tb.add("B","K","X"));
    assertTrue(tb.add("B","K","Y"));
    assertTrue(tb.add("B","K","Z"));

    TripleStore tc = new TripleStore();
    assertTrue(tc.add("C","I","X"));
    assertTrue(tc.add("C","I","Y"));
    assertTrue(tc.add("C","I","Z"));
    assertTrue(tc.add("C","J","X"));
    assertTrue(tc.add("C","J","Y"));
    assertTrue(tc.add("C","J","Z"));
    assertTrue(tc.add("C","K","X"));
    assertTrue(tc.add("C","K","Y"));
    assertTrue(tc.add("C","K","Z"));

    TripleStore t1 = new TripleStore();
    t1.setWild("1");
    for(Record r : ta.query("A","*","X")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,t1);
      assertTrue(msg, t1.add(r.entity(),r.relation(),r.property()));
    }
    for(Record r : tb.query("B","J","Z")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,t1);
      assertTrue(msg, t1.add(r.entity(),r.relation(),r.property()));
    }
    for(Record r : tc.query("*","K","*")){
      msg = String.format("add %s should be true for triplestore:\n%s",r,t1);
      assertTrue(msg, t1.add(r.entity(),r.relation(),r.property()));
    }

    expectS =
"       A        I        X \n"+
"       A        J        X \n"+
"       A        K        X \n"+
"       B        J        Z \n"+
"       C        K        X \n"+
"       C        K        Y \n"+
"       C        K        Z \n"+
"";
    actualS = t1.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    actual = t1.query("1","K","X"); // wild for t1 is 1
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","K","X"),
        Record.makeRecord("C","K","X"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    ta.remove("1","1","X");      // wild for ta is *
    expectS =
"       A        I        X \n"+
"       A        I        Y \n"+
"       A        I        Z \n"+
"       A        J        X \n"+
"       A        J        Y \n"+
"       A        J        Z \n"+
"       A        K        X \n"+
"       A        K        Y \n"+
"       A        K        Z \n"+
"";
    actualS = ta.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    ta.remove("*","*","X");      // wild for ta is *
    expectS =
"       A        I        Y \n"+
"       A        I        Z \n"+
"       A        J        Y \n"+
"       A        J        Z \n"+
"       A        K        Y \n"+
"       A        K        Z \n"+
"";
    actualS = ta.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    actual = ta.query("*","K","Y");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("A","K","Y"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);
  }

  @Test public void wild_n_crazy1(){
    List<Record> actual, expect;
    String msg, expectS, actualS;

    TripleStore t = new TripleStore();
    t.add("Alf","ISA","*");
    t.add("*","ISA","cat");
    t.add("*","EATS","*");
    t.add("Lucky","*","cat");
    expectS =
"       *     EATS        * \n"+
"       *      ISA      cat \n"+
"     Alf      ISA        * \n"+
"   Lucky        *      cat \n"+
"";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);
    
    // Check to ensure no records in the triplestore are wild
    for(Record r : t.query("*","*","*")){
      if(r.entityWild() || r.relationWild() || r.propertyWild()){
        msg = String.format("\nRecord %s with wild pattern %s %s %s in TripleStore:\n%s\n",
                            r,r.entityWild(),r.relationWild(),r.propertyWild(),t);
        fail(msg);
      }
    }

    actual = t.query("*","*","cat");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("*","ISA","cat"),
        Record.makeRecord("Lucky","*","cat"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);
    
    t.setWild("WILD");
    actual = t.query("WILD","WILD","cat");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("*","ISA","cat"),
        Record.makeRecord("Lucky","*","cat"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);

    actual = t.query("*","WILD","WILD");
    expect = Arrays.asList(new Record[]{
        Record.makeRecord("*","EATS","*"),
        Record.makeRecord("*","ISA","cat"),
      });
    Collections.sort(actual,Record.ERPCompare);
    assertRecordListsEqual(expect,actual);
  }

  // Another stress test focusing on wild card changes
  @Test public void wild_n_crazy2(){
    List<Record> actual, expect;
    String msg, expectS, actualS;

    TripleStore t = new TripleStore();
    t.add("Alf","ISA","*");
    t.add("*","ISA","cat");
    t.add("*","EATS","*");
    t.add("Lucky","*","cat");
    expectS =
"       *     EATS        * \n"+
"       *      ISA      cat \n"+
"     Alf      ISA        * \n"+
"   Lucky        *      cat \n"+
"";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);
    
    t.setWild("&");
    t.remove("*","&","cat");
    expectS =
"       *     EATS        * \n"+
"     Alf      ISA        * \n"+
"   Lucky        *      cat \n"+
"";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    t.remove("&","ISA","&");
    expectS =
"       *     EATS        * \n"+
"   Lucky        *      cat \n"+
"";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    t.add("Lucky","&","Alf");
    t.add("Alf","&","Lucky");
    expectS =
"       *     EATS        * \n"+
"     Alf        &    Lucky \n"+
"   Lucky        &      Alf \n"+
"   Lucky        *      cat \n"+
"";
    actualS = t.toString();
    msg = String.format("\nExpect:\n%sActual:\n%s",expectS,actualS);
    assertEquals(msg, expectS, actualS);

    // Check to ensure no records in the triplestore are wild
    for(Record r : t.query("*","*","*")){
      if(r.entityWild() || r.relationWild() || r.propertyWild()){
        msg = String.format("\nRecord %s with wild pattern %s %s %s in TripleStore:\n%s\n",
                            r,r.entityWild(),r.relationWild(),r.propertyWild(),t);
        fail(msg);
      }
    }

  }

}