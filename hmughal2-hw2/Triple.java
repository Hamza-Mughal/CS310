// Provided class to convey triples of objects. For compareTo(other)
// presumes that each of the constituent objects are also comparable.
public class Triple<A,B,C> implements Comparable<Triple<A,B,C>>
{
  public final A a;             // first elemtn of the triple
  public final B b;             // second element of the triple
  public final C c;             // third element of the triple

  // Construct a triple with the specified elements
  public Triple(A a, B b, C c){
    this.a = a; this.b = b; this.c = c;
  }

  // Check if two triples are equal
  public boolean equals(Object o){
    if(this == o){
      return true;
    }
    if(!(o instanceof Triple)){
      return false;
    }
    Triple that = (Triple) o;
    return
      this.a.equals(that.a) && 
      this.b.equals(that.b) && 
      this.c.equals(that.c);
  }

  @SuppressWarnings("unchecked")
  // Compare two triples; if some of the constituent elements are not
  // Comparable, this method may raise a runtime exception.
  public int compareTo(Triple<A,B,C> that){
    int diff;
    diff = ((Comparable<A>) this.a).compareTo(that.a);
    if(diff!=0){ return diff; }
    diff = ((Comparable<B>) this.b).compareTo(that.b);
    if(diff!=0){ return diff; }
    diff = ((Comparable<C>) this.c).compareTo(that.c);
    return diff;
  }
    
  // Pretty printed representation of the triple
  public String toString(){
    return String.format("(%s, %s, %s)", this.a,this.b,this.c);
  }      
}