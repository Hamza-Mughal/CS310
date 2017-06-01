import java.util.*;

// Immutable.  Stores 3 strings referred to as entity, relation, and
// property. Each Record has a unique integer ID which is set on
// creation.  All records are made through the factory method
// Record.makeRecord(e,r,p).  Record which have some fields wild are
// created using Record.makeQuery(wild,e,r,p)
public class Record{
  // the record class does not rely on any subclasses
  // the notion of wildcards is supported through the use of a wildcard field which is used primarily in matches
  // and in the comparators. 
  // uses a simple field to check in .equals checks for wildcard fields
  private static int staticid; // static ID field used to determine next record ID
  private final String entity; //  entity field of a record
  private final String relation; // relation field of a record
  private final String property; // property field of a record
  private int classid2; // class level ID of current record
  private String wildcard; // wild card of the record 
  
  private Record(String entity, String relation, String property){ // constructor used by makeRecord
    this.entity = entity;
    this.relation = relation;
    this.property = property;
    staticid = staticid + 1;
    classid2 = staticid;
  }
  private Record(String wildcard, String entity, String relation, String property){ // constructor used by makeQuery
    this(entity,relation,property);
    this.wildcard = wildcard;
  }
  // Return the next ID that will be assigned to a Record on a call to
  // makeRecord() or makeQuery()
  public static int nextId(){
    return staticid + 1; // return the ID of the next record that will be created
  }
  
  // Return a stringy representation of the record. Each string should
  // be RIGHT justified in a field of 8 characters with whitespace
  // padding the left.  Java's String.format() is useful for padding
  // on the left.
  public String toString(){
    StringBuilder b = new StringBuilder();
    b.append((String.format("%8s ", entity())));
    b.append((String.format("%8s ", relation())));
    b.append((String.format("%8s ", property())));
    return b.toString();
  }
  
  // Return true if this Record matches the parameter record r and
  // false otherwise. Two records match if all their fields match.
  // Two fields match if the fields are identical or at least one of
  // the fields is wild.
  
// the matches records relies on 3 simple checks for each field
// it will check if the entity of both records are equal, otherwise it checks if either records entity is the wildcard
// if true, it checks if the relation of both records are equal, otherwise it checks if either records relation is the wildcard
// if true, it checks if the property of both records are equal, otherwise it checks if either records property is equal
// if all checks return true, then the records are equal
  public boolean matches(Record r){
    if(entity().equals(r.entity()) || entityWild() == true || r.entityWild() == true){
      if(relation().equals(r.relation()) || relationWild() == true || r.relationWild() == true){
        if(property().equals(r.property()) || propertyWild() == true || r.propertyWild() == true){
          return true; 
        }
      }
    }
    return false;
  }
  
  // Return this record's ID
  public int id(){
    return this.classid2; 
  }
  
  // Accessor methods to access the 3 main fields of the record:
  // entity, relation, and property.
  public String entity(){
    return this.entity; 
  }
// return relation field
  public String relation(){
    return this.relation; 
  }
// return property field
  public String property(){
    return this.property; 
  }
  
  // Returns true/false based on whether the the three fields are
  // fixed or wild.
  public boolean entityWild(){
    if(entity().equals(wildcard)){
      return true; 
    }
    return false;
  }
// returns true if relation field is equal to the wildcard field
  public boolean relationWild(){
    if(relation().equals(wildcard)){
      return true; 
    }
    return false; 
  }
// returns true if property field is equal to the wildcard field
  public boolean propertyWild(){
    if(property().equals(wildcard)){
      return true; 
    }
    return false; 
  }
  
  
  // Factory method to create a Record. No public constructor is
  // required.
  public static Record makeRecord(String entity, String relation, String property){
    if(entity == null || relation == null || property == null){
      throw new IllegalArgumentException("Cannot set a record to null!");
    }
    Record r = new Record(entity,relation,property); // call constructor to make record
    return r;
  }
  
  // Create a record that has some fields wild. Any field that is
  // equal to the first argument wild will be a wild card
  public static Record makeQuery(String wild, String entity, String relation, String property){
    if(wild == null || entity == null || relation == null || property == null){
      throw new IllegalArgumentException("Cannot set a record to null!");
    }    
    Record r = new Record(wild,entity,relation,property); // call 2nd constructor to make record which supports wildcards
    return r;
  }
  
  // Comparators that compare Records based on different orderings of
  // their fields. The names of the Comparators correspond to the
  // order in which they compare fields: ERPCompare compares Entity
  // (E), then Relation (R), then property (P). Likewise for
  // RPECompare and PER compare.
  public static final Comparator<Record> ERPCompare = new Comparator<Record>(){ 
    public int compare(Record r1, Record r2){ // this comparator compares in the order of ERP
      int x = 0;
      x = r1.entity().compareTo(r2.entity()); // first compares the entity
      if(r1.entityWild() == true){ // if r1 has wildcard entity, it is automatically less than
        x = -1; 
      }
      else if(r2.entityWild() == true){ // if r2 has wildcard entity, it is automatically greater than
        x = 1;
      }
      if(x == 0){ // if entity compare gave 0 then compare the relations
        x = r1.relation().compareTo(r2.relation());
        if(r1.relationWild() == true){ // if r1 has wildcard relation, it is automatically less than
          x = -1; 
        }
        else if(r2.relationWild() == true){ // if r2 has wildcard relation, it is automatically less than
          x = 1;
        }
        if(x == 0){ // if relation compare gave 0, then compare the property
          x = r1.property().compareTo(r2.property());
          if(r1.propertyWild() == true){ // if r1 has wildcard property, it is automatically less than
            x = -1; 
          }
          else if(r2.propertyWild() == true){ // if r1 has wildcard property, it is automatically less than
            x = 1;
          }
        }
      }
      
      return x;
    }
  };
  
  public static final Comparator<Record> RPECompare = new Comparator<Record>(){
    public int compare(Record r1, Record r2){ // this comparator compares in the RPE order
      int x = 0;
      x = r1.relation().compareTo(r2.relation()); // first compares the relation
      if(r1.relationWild() == true){ // if r1 has wildcard relation, it is automatically less than
        x = -1; 
      }
      else if(r2.relationWild() == true){ // if r2 has wildcard relation, it is automatically less than
        x = 1;
      }
      if(x == 0){  // if relation compare gave 0, then compare the property
        x = r1.property().compareTo(r2.property());
        if(r1.propertyWild() == true){ // if r1 has wildcard property, it is automatically less than
          x = -1; 
        }
        else if(r2.propertyWild() == true){ // if r2 has wildcard property, it is automatically less than
          x = 1;
        }
        if(x == 0){ // if property compare gave 0, then compare the entity
          x = r1.entity().compareTo(r2.entity());
          if(r1.entityWild() == true){ // if r1 has wildcard entity, it is automatically less than
            x = -1; 
          }
          else if(r2.entityWild() == true){ // if r2 has wildcard entity, it is automatically less than
            x = 1;
          }
        }
      }
      return x;
    }
  };
  
  public static final Comparator<Record> PERCompare = new Comparator<Record>(){
    public int compare(Record r1, Record r2){ // this comparator compares in the PER order
      int x = 0;
      x = r1.property().compareTo(r2.property()); // first compares the property
      if(r1.propertyWild() == true){ // if r1 has wildcard property, it is automatically less than
        x = -1; 
      }
      else if(r2.propertyWild() == true){ // if r2 has wildcard property, it is automatically less than
        x = 1;
      }
      if(x == 0){ // if property compare gave 0, then compare the entity
        x = r1.entity().compareTo(r2.entity());
        if(r1.entityWild() == true){ // if r1 has wildcard entity, it is automatically less than
          x = -1; 
        }
        else if(r2.entityWild() == true){ // if r2 has wildcard entity, it is automatically less than
          x = 1;
        }
        if(x == 0){ // if entity compare gave 0, then compare the relation
          x = r1.relation().compareTo(r2.relation());
          if(r1.relationWild() == true){ // if r1 has wildcard relation, it is automatically less than
            x = -1; 
          }
          else if(r2.relationWild() == true){ // if r2 has wildcard relation, it is automatically less than
            x = 1;
          }
        }
      }
      return x;
    }       
  };
  
}