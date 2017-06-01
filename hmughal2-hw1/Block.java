// class which holds the actual element array
// used by dyanamicarray
public class Block<T> {
 protected final int number; // Block number, as in Block1
 protected final T[] arrayOfElements; // Holds actual elements

 // Number of elements that can be stored in this block;
 // this is equal to arrayOfElements.length
 protected final int capacity;

 // Number of spaces that have been allocated for storing elements;
 // initially 0. size <= capacity
 protected int size;

 // Workhorse constructor. Initialize variables and create array.
 @SuppressWarnings("unchecked")
 public Block(int number, int capacity){
  this.number = number;
  this.capacity = capacity;
  this.size = 0;
  this.arrayOfElements =  (T[])new Object[capacity];
 }
 
  // Returns Number
 public int getNumber(){
  return this.number; 
 }
 
 // Returns capacity
 public int getCapacity(){
  return this.capacity; 
 }

 // Returns size
 public int size(){
  return this.size;
 }

 // Increase the space allocated for storing elements. Increases 
 // size.
 public void grow(){
  size++;
 }

 // Set the last element to null and decrease the space allocated 
     // for storing elements. Decreases size.
 public void shrink(){
   arrayOfElements[size-1] = null;
   size--;
 }

 // Returns the element at position index in arrayOfElements.
 public T getElement(int index){
  return (T) arrayOfElements[index]; 
 }

   // Sets the value at position i in arrayOfElements to x.
 public void setElement(int i, T x){
   this.arrayOfElements[i] =  x;
 }

     // Create a pretty representation of the Block.
     // Example: 
     // A 
 public String toString(){
  String elements = "";
  for (int i = 0; i < capacity; i++){ // loop through the array, adding the element while skipping nulls
    if(arrayOfElements[i] != null){
     elements+=arrayOfElements[i]; 
    }
  }
  return elements;
 }

     // Create a pretty representation of the Block for debugging.
     // Example: 
     // A
     // - capacity=1 size=1  
 protected String toStringForDebugging(){
   String elements = "";
  for (int i = 0; i < capacity; i++){ // loop through the array, adding the element while skipping nulls
    if(arrayOfElements[i] != null){
     elements+=arrayOfElements[i]; 
    }
  }
  elements+="- capacity= " + this.capacity + " size= " + this.size;
  return elements;
 }

}
