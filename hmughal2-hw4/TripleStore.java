import java.util.*;
import java.util.TreeSet;
// Three-column database that supports query, add, and remove in
// logarithmic time.
public class TripleStore{
  protected TreeSet<Record> ERPtree; // ERP tree
  protected TreeSet<Record> RPEtree; // RPE tree
  protected TreeSet<Record> PERtree; // PER tree
  protected String wild;
  // Create an empty TripleStore. Initializes storage trees
  public TripleStore(){ // constructor creates the tree trees and sets default wildcard to *
    this.ERPtree = new TreeSet<Record>(Record.ERPCompare);
    this.RPEtree = new TreeSet<Record>(Record.RPECompare); 
    this.PERtree = new TreeSet<Record>(Record.PERCompare);
    this.wild = "*"; // default wildcard
  }
  
  // Access the current wild card string for this TripleStore which
  // may be used to match multiple records during a query() or
  // remove() calll
  public String getWild(){
    return this.wild; 
  }
  
  // Set the current wild card string for this TripleStore
  public void setWild(String w){
    this.wild = w; 
  }
  
  // Ensure that a record is present in the TripleStore by adding it
  // if necessary.  Returns true if the addition is made, false if the
  // Record was not added because it was a duplicate of an existing
  // entry. A Record with any fields may be added to the TripleStore
  // including a Record with fields that are equal to the
  // TripleStore's current wild card. Throws an
  // IllegalArgumentException if any argument is null.
  // 
  // Target Complexity: O(log N)
  // N: number of records in the TripleStore
  public boolean add(String entity, String relation, String property){
    Record r = Record.makeRecord(entity, relation, property); // create a record
    return ERPtree.add(r) && RPEtree.add(r) && PERtree.add(r); // add record to all three trees, and return true if add was successful
    // meaning that there were no duplicates within the tree(s)
  }
  
  // Return a List of the Records that match the given query. If no
  // Records match, the returned list should be empty. If a String
  // matching the TripleStore's current wild card is used for one of
  // the fields of the query, multiple Records may be returned in the
  // match.  An appropriate tree must be selected and searched
  // correctly in order to meet the target complexity. Throws an
  // IllegalArgumentException if any argument is null.
  // 
  // TARGET COMPLEXITY: O(K + log N) 
  // K: the number of matching records 
  // N: the number of records in the triplestore.
  public List<Record> query(String entity, String relation, String property){
    if(entity == null || relation == null || property == null){ // check for null
      throw new IllegalArgumentException("Cannot query a null"); 
    }
    ArrayList<Record> l = new ArrayList<Record>(); // create list to hold queried records
    Record r = Record.makeQuery(wild, entity, relation, property); // create record to query
    // code below chooses correct tree based off of condition of the fields
    if(property.equals(wild) && relation.equals(wild)){ // P and R equal wild
      SortedSet<Record> s = ERPtree.tailSet(r); // get subset of tree
      Iterator<Record> iterator = s.iterator(); // iterate through tree
      while(iterator.hasNext()){
        Record j = iterator.next(); 
        if(j.matches(r)){ // if record in tree matches query we want
          l.add(j); // add it
        }
        else{
          break; // otherwise break as nothing left to query
        } 
      }    
    }
    else if(property.equals(wild) && entity.equals(wild)){ // P and E equal wild
      SortedSet<Record> s = RPEtree.tailSet(r); // get subset of tree
      Iterator<Record> iterator = s.iterator(); // iterate through tree
      while(iterator.hasNext()){
        Record j = iterator.next();
        if(j.matches(r)){ // if record in tree matches query we want
          l.add(j); // add it
        }
        else{
          break; // otherwise break as nothing left to query
        }
      }
    }
    else if(relation.equals(wild) && entity.equals(wild)){ // R and E equal wild
      SortedSet<Record> s = PERtree.tailSet(r); // get subset of tree
      Iterator<Record> iterator = s.iterator(); // iterate through tree
      while(iterator.hasNext()){
        Record j = iterator.next();
        if(j.matches(r)){ // if record in tree matches query we want
          l.add(j); // add it
        }
        else{
          break; // otherwise break as nothing left to query
        }
      }   
    }
    else if(property.equals(wild)){ // P equals wild
      SortedSet<Record> s = ERPtree.tailSet(r); // get subset of tree
      Iterator<Record> iterator = s.iterator(); // iterate through tree
      while(iterator.hasNext()){ 
        Record j = iterator.next();
        if(j.matches(r)){ // if record in tree matches query we want
          l.add(j); // add it
        }
        else{
          break; // otherwise break as nothing left to query
        }
      }    
    }
    else if(entity.equals(wild)){ // E equals wild
      SortedSet<Record> s = RPEtree.tailSet(r); // get subset of tree
      Iterator<Record> iterator = s.iterator(); // iterate through tree
      while(iterator.hasNext()){
        Record j = iterator.next();
        if(j.matches(r)){ // if record in tree matches query we want
          l.add(j); // add it
        }
        else{
          break; // otherwise break as nothing left to query
        }
      }    
    }
    else if(relation.equals(wild)){ // R equals wild
      SortedSet<Record> s = PERtree.tailSet(r); // get subset of tree
      Iterator<Record> iterator = s.iterator(); // iterate through tree
      while(iterator.hasNext()){
        Record j = iterator.next();
        if(j.matches(r)){ // if record in tree matches query we want
          l.add(j); // add it
        }
        else{
          break; // otherwise break as nothing left to query
        }
      }    
    }
    else{ // if none of the fields are wildcard, then we use the regular ERP tree
      SortedSet<Record> s = ERPtree.tailSet(r); // get subset of tree
      Iterator<Record> iterator = s.iterator(); // iterate through tree
      Record j = iterator.next();
      if(j.matches(r)){ // there can only be one match of the tree we got the subset of, and it is the root if it exists
        l.add(r);
      }
    }
    
    return l;
  }
  
  // Remove elements from the TripleStore that match the parameter
  // query. If no Records match, no Records are removed.  Any of the
  // fields given may be the TripleStore's current wild card which may
  // lead to multiple Records bein matched and removed. Return the
  // number of records that are removed from the TripleStore. Throws
  // an IllegalArgumentException if any argument is null.
  // 
  // TARGET COMPLEXITY: O(K * log N)
  // K: the number of matching records 
  // N: the number of records in the triplestore.
  public int remove(String e, String r, String p){
    int x = 0;
    if( e == null || r == null || p == null){
      throw new IllegalArgumentException("Cannot remove null"); 
    }
    List<Record> k = query(e,r,p); // gather list of items to remove
    Iterator<Record> l = k.iterator(); // create iterator
    while(l.hasNext()){
      x++; // increment number of elements removed
     Record b= l.next(); // record to remove
     ERPtree.remove(b); // remove record from all three trees
     RPEtree.remove(b);
     PERtree.remove(b);
    }
    return x; // return count of removed elements
  }
  
  // Produce a String representation of the TripleStore. Each Record
  // is formatted with its toString() method on its own line. Records
  // must be shown sorted by Entity, Relation, Property in the
  // returned String. 
  // 
  // TARGET COMPLEXITY: O(N)
  // N: the number of records stored in the TripleStore
  public String toString(){
    StringBuilder b = new StringBuilder(); // use of stringbuilder 
    Iterator iterator = ERPtree.iterator();
    while(iterator.hasNext()){ // iterate through tree, calling each record toString()
      b.append(iterator.next().toString());
      b.append("\n");
    }
    return b.toString(); 
  }
  
}