import java.util.*;
public class DAG{
  protected Map<String,Set<String>> upStreamMap; // upstreamMap which maps strings to sets
  protected Map<String,Set<String>> downStreamMap; // downstreamMap which maps strings to sets
  // Construct an empty DAG
  public DAG(){
    this.upStreamMap = new HashMap<String,Set<String>>();
    this.downStreamMap = new HashMap<String,Set<String>>();
  }
  // Produce a string representaton of the DAG which shows the
  // upstream and downstream links in the graph.  The format should be
  // as follows:
  //
  // Upstream Links:
  //   A1 : [E1, F2, C1]
  //   C1 : [E1, F2]
  //  BB8 : [D1, C1, RU37]
  // RU37 : [E1]
  // Downstream Links:
  //   E1 : [A1, C1, RU37]
  //   F2 : [A1, C1]
  //   D1 : [BB8]
  // RU37 : [BB8]
  //   C1 : [A1, BB8]
  public String toString(){
    String s = "Upstream Links:\n"; // create string with first line
    for(String key : upStreamMap.keySet()){ // loop through the upstream keys
      Set<String> smap = new HashSet<String>(); // assign the set that key is mapped to
      smap = upStreamMap.get(key);
      List<String> list = new ArrayList<String>(smap); //  convert set to list
      if(list.isEmpty() == true){ // if list is empty, do not add it to the string
        continue;
      } 
      s+=String.format("%4s",key); // otherwise add key to string
      s+=" : "; // set nice spacing
      s+=list + "\n"; // and finally add the whole list
    }
    s+="Downstream Links:\n"; // add downstream string
    for(String key : downStreamMap.keySet()){ // loop through the downstream keys
      Set<String> smap = new HashSet<String>(); // assign the set that key is mapped to
      smap = downStreamMap.get(key);
      List<String> list = new ArrayList<String>(smap); //  convert set to list
      if(list.isEmpty() == true){ // if list is empty, do not add it to the string
        continue;
      }
      s+=String.format("%4s",key); // otherwise add key to string
      s+=" : ";
      s+=list + "\n"; // and finally add the whole list
    }  
    return s;
  }
  
  // Return the upstream links associated with the given ID.  If there
  // are no links associated with ID, return the empty set.
  //
  // TARGET COMPLEXITY: O(1)
  public Set<String> getUpstreamLinks(String id){
    if(upStreamMap.containsKey(id)){ // returns set string mapped to if map contains the key
      return upStreamMap.get(id);
    }
    Set<String> empty = new HashSet<String>(); // else return an empty set
    return empty;
  }
  
  // Return the downstream links associated with the given ID.  If
  // there are no links associated with ID, return the empty set.
  //
  // TARGET COMPLEXITY: O(1)
  public Set<String> getDownstreamLinks(String id){
    if(downStreamMap.containsKey(id)){  // returns set string mapped to if map contains the key
      return downStreamMap.get(id);
    }
    Set<String> empty = new HashSet<String>(); // else return an empty set
    return empty;
  }
  
  // Class representing a cycle that is detected on adding to the
  // DAG. Raised in checkForCycles(..) and add(..).
  public static class CycleException extends RuntimeException{
    public CycleException(String msg){
      super(msg);
    }
  }
  
  // Add a node to the DAG with the provided set of upstream links.
  // Add the new node to the downstream links of each upstream node.
  // If the upstreamIDs argument is either null or empty, remove the
  // node with the given ID.
  //
  // After adding the new node, check whether it has created any
  // cycles through use of the checkForCycles() method.  If a cycle is
  // created, revert the DAG back to its original form so it appears
  // there is no change and raise a CycleException with a message
  // showing the cycle that would have resulted from the addition.
  // 
  // TARGET RUNTIME COMPLEXITY: O(N + L)
  // MEMORY OVERHEAD: O(P)
  //   N : number of nodes in the DAG
  //   L : number of upstream links in the DAG
  //   P : longest path in the DAG starting from node id
  public void add(String id, Set<String> upstreamIDs){
    Set<String> oldupLinks = getUpstreamLinks(id); // current upstream links associated with ID
    remove(id); // clear upstream links
    upStreamMap.put(id, upstreamIDs); // map id to upstreamID in hashmap
    for(String s : upstreamIDs){ // look through upstreamIDs
      if(downStreamMap.containsKey(s)){ // check to see if adding element to current key in hashMap
        Set<String> h = downStreamMap.get(s); // add a new value to set
        (h).add(id);
        continue;
      }
      downStreamMap.put(s, new HashSet<String>()); // otherwise we create a new entry in hash map
      downStreamMap.get(s).add(id); // and add the ID to it
    }
    List<String> path = new ArrayList<String>(); // list containing id we added to DAG
    path.add(id);
    boolean flag = checkForCycles(upStreamMap, path); // check if cycle was created
    if(flag == true){ // if cycle was created
      add(id, oldupLinks); // recursively all add single layer deep to get back old DAG
      throw new CycleException(path.toString()); // then throw  exception of list that caused cycle
    }
  }
  
  
  
  
  // Determine if there is a cycle in the graph represented in the
  // links map.  List curPath is the current path through the graph,
  // the last element of which is the current location in the graph.
  // This method should do a recursive depth-first traversal of the
  // graph visiting each neighbor of the current element. Each
  // neighbor should be checked to see if it equals the first element
  // in curPath in which case there is a cycle.
  //
  // This method should return true if a cycle is found and curPath
  // should be left to contain the cycle that is found.  Return false
  // if no cycles exist and leave the contents of curPath as they were
  // originally.
  //
  // The method should be used during add(..) which will initialize
  // curPath to the new node being added and use the upstream links as
  // the links passed in.
  public static boolean checkForCycles(Map<String, Set<String>> links, List<String> curPath){
    String lastNode = curPath.get(curPath.size()-1); // get last element from path
    Set<String> neighbors = links.get(lastNode); // get set of neighbors associated with path
    if(neighbors == null || neighbors.isEmpty() == true){ //  if set is empty or null, return false as no cycle detected
      return false;
    }
    for(String g : neighbors){ // loop through set
      curPath.add(g); // add it path
      if(curPath.get(0).equals(g)){ // if first elemt in path equals g, then cycle was found and we return true
        return true; 
      }
      boolean flag = checkForCycles(links, curPath); // else recursively call itself visiting neighbors
      if(flag == true){ // if flag is true, cycle was found
        return true; 
      }
      curPath.remove(curPath.size()-1); // remove last element from path
    }
    return false; // no cycle found after exploring everything
  }
  
  // Remove the given id by eliminating it from the downstream links
  // of other ids and eliminating its upstream links.  If the ID has
  // no upstream dependencies, do nothing.
  //
  // TARGET COMPLEXITY: O(L_i)
  //   L_i : number of upstream links node id has
  public void remove(String id){
    if(getUpstreamLinks(id).isEmpty() == true){ // if set asspciated with id is empty, return as nothing to remove
      return; 
    }
    upStreamMap.remove(id); // remove id from  upstream
    for (String key : downStreamMap.keySet()){ // loop through downstream
      if(downStreamMap.get(key).contains(id)){ // if downstream set contains id
        downStreamMap.get(key).remove(id);  // simply remove it from the set
      }
    }
  }
}