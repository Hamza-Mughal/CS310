// Initial Release Fri Nov 11 15:27:14 EST 2016 
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test; // fixes some compile problems with annotations

public class Test1{

public static Set<String> toSet(String... args){
    Set<String> set = new HashSet<String>();
    for(String s : args){
      set.add(s);
    }
    return set;
  }

  @Test(timeout=1000) public void dag_basic_add3(){
    String expectS, expectUpstream[], expectDownstream[];
    DAG dag = new DAG();
    dag.add("A1",toSet("B1","C1","D1"));
    dag.add("B1",toSet("C1","D1"));
    dag.getUpstreamLinks("A1");
    expectS =
      "Upstream Links:\n"+
      "  A1 : [D1, C1, B1]\n"+
      "  B1 : [D1, C1]\n"+
      "Downstream Links:\n"+
      "  D1 : [A1, B1]\n"+
      "  C1 : [A1, B1]\n"+
      "  B1 : [A1]\n"+
      "";
    expectUpstream = new String[]{
      "A1","[D1, C1, B1]",
      "B1","[D1, C1]",
    };
    expectDownstream = new String[]{
      "D1","[A1, B1]",
      "C1","[A1, B1]",
      "B1","[A1]",
    };
    

  }
}