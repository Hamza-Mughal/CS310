// queue representation of a dynamic array
// relies on dynamicarray
import java.util.*;
public class DynamicQueue<T> {
 protected DynamicArray<T> front; // the front of the dynamic array
 protected DynamicArray<T> rear; // the rear of the dyanmic array

 // Workhorse constructor. Initialize variables.
 public DynamicQueue(){
  this.front = new DynamicArray<T>();
  this.rear = new DynamicArray<T>();
 }
 // Return the “front” dynamic array of outgoing elements for final testing
// Target complexity: O(1)
 protected DynamicArray<T> getFront(){
  return this.front; 
 }
 
 // Return the “rear” dynamic array of incoming elements for final testing
// Target complexity: O(1)
 protected DynamicArray<T> getRear(){
  return this.rear; 
 }
 
 // Adds x to the rear of the queue
 // Target complexity: O(1)
 public void enqueue(T x){
  getRear().add(x);  // adds elements to rear
 }

 // Removes and returns the element at the front of the queue
 // Throws NoSuchElementException if this queue is empty.
 // Target complexity: O(n)
  @SuppressWarnings("unchecked")
 public T dequeue(){
   if(front.size == 0 && rear.size == 0){ // if both front and rear and empty, throw an exception
    throw new NoSuchElementException("Queue empty"); 
   }
   if(front.size == 0){ // if the front has no elements to return
     T[] elements = (T[])new Object[rear.size]; // create a new generic array to store elements temporarily
     for(int i = 0; i < rear.size; i++){ // loop and get the element within the rear
      elements[i] = getRear().get(i);
     }
     for(int i = elements.length-1; i >= 0; i--){ // loop and get the element from the array and add it to the front in reverse order
      getFront().add(elements[i]); 
     }
     this.rear = new DynamicArray<T>(); // now that we have gotten what we need from the rear, wipe it clean!
   }
   T x = getFront().get(front.size-1); //gets the element and returns it
   getFront().remove(); 
   return x;
 }

 // Returns true if the queue is empty
 public boolean isEmpty(){
   if(front.size != 0 || rear.size !=0){ // if both rear and front are empty then return false otherwise true
    return false; 
   }
   return true;
 }

 // Returns the size of the queue
 public int size(){
   int x = getFront().size + getRear().size; // adds size of both front and queue
  return x;
 }

 // Create a pretty representation of the DynamicQueue.
  // Example:
 // A B C D
 public String toString(){
   if(getRear().size != 0 && getFront().size != 0){ // if both dyanmicarray objects have elements
     String front_string = getFront().toString(); // use their toString representation
     front_string = front_string.substring(0, front_string.length()-1); // remove the "["
     String rear_string = getRear().toString(); // same as above
     rear_string = rear_string.substring(1); // remove the "]"
     String final_string = front_string + "," + rear_string; // add them together and 
     return final_string;
 }
   else{ // same as above except there is no "," to be added
     String front_string = getFront().toString();
     front_string = front_string.substring(0, front_string.length()-1);
     String rear_string = getRear().toString();
     rear_string = rear_string.substring(1);
     String final_string = front_string + rear_string;
     return final_string;     
   }
 }

 // Create a pretty representation of the DynamicQueue for debugging.
 // Example:
 // front.toString: A B 
 // rear.toString: C D
 protected String toStringForDebugging(){ // use the fields toString() method to add the strings
  String final_string = "";
  final_string+="front.toString : " + getFront().toString() + "\n";
  final_string+="rear.toString : " + getRear().toString() + "\n";
  return final_string;
 }
}
