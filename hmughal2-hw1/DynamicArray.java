// a type of "super" class which contains methods as to access and change information within blocks
// to be used by dynamicqueue, relies on blocks and superblocks, and location class
public class DynamicArray<T> {
  protected Object[] arrayOfBlocks; // array to hold blocks
  protected final int DEFAULTCAPACITY = 4; // default size of array
  protected int sizeOfArrayOfBlocks; // number of Blocks in arrayOfBlocks
  protected int size; // number of elements in DynamicArray
  protected int numberOfEmptyDataBlocks; // number of current empty blocks
  protected int numberOfNonEmptyDataBlocks; // number of current non empty blocks
  protected int numberOfDataBlocks; // number of total current data blocks
  protected int indexOfLastNonEmptyDataBlock; // last current non empty block
  protected int indexOfLastDataBlock; // index of last block
  protected int numberOfSuperBlocks; // number of superblocks within the array
  protected SuperBlock lastSuperBlock; // right-most SuperBlock

  // Workhorse constructor. Initialize variables, create the array
  // and the last SuperBlock, which represents SB0.
  public DynamicArray(){
    size = 0;
    arrayOfBlocks = new Object[DEFAULTCAPACITY];
    numberOfDataBlocks = 1;
    numberOfEmptyDataBlocks = 1;
    numberOfNonEmptyDataBlocks = 0;
    indexOfLastNonEmptyDataBlock = -1;
    indexOfLastDataBlock = 0;
    numberOfSuperBlocks = 1;
    sizeOfArrayOfBlocks = 1;
    lastSuperBlock = new SuperBlock(0,1,1,1);
    arrayOfBlocks[indexOfLastDataBlock] = new Block<T>(0,lastSuperBlock.getMaxNumberOfElementsPerBlock());
  }
 
  // Returns the Location of element i, which is the index of the Block
  // and the position of i within that Block.
  // Throws IllegalArgumentException if index < 0 or index > size-1;
  // Target complexity: O(1)
  protected Location locate(int index){
    int r = index + 1;
    int k = (int)log2(r); // superblock 
    int p = 0; // numbers of blocks
    int b = 0;
    if((k % 2) == 0){ // if the superblock is even
      p = 2 * (((int)Math.pow(2,(k/2)))-1);
    }
    else{ // if the superblock is odd
      p = (2*((int)Math.pow(2,(k/2)-1))+((int)Math.pow(2,(k/2))));
    }
    int e = (int)Math.ceil(((double)k/(double)2));
    int mask = maskOfN(e);
    int locationE = r & mask; // location of element within block
    int s = k/2;
    int rshift = r >> e; // shift e number of places
    int floork = k/2;
    int kmask = maskOfN(s);
    b = rshift & kmask; // b is given by & of the shift and the mask
    return new Location((b+p),locationE);
  }

  // Returns the Block at position i in arrayOfBlocks.
  // Throws IllegalArgumentException if index < 0 or 
  // index > sizeOfArrayOfBlocks - 1;
  // Target complexity: O(1)
  @SuppressWarnings("unchecked")
  protected Block<T> getBlock(int index){
    if(index < 0){
     throw new IllegalArgumentException("no index available"); 
    }
    else if( index > sizeOfArrayOfBlocks - 1){
      throw new IllegalArgumentException("no index available"); 
    }
    return (Block<T>)arrayOfBlocks[index];
  }
     
  // Returns the element at position i in the DynamicArray.
  // Throws IllegalArgumentException if index < 0 or 
  // index > size -1;
  // Target complexity: O(1)
  public T get(int i){
        if(i < 0){
     throw new IllegalArgumentException("no index available"); 
    }
    else if(i > (size-1)){
      throw new IllegalArgumentException("no index available"); 
    }
    Location l = locate(i);
    return (T) getBlock(l.getBlockIndex()).getElement(l.getElementIndex());
  }

  // Sets the value at position i in DynamicArray to x.
  // Throws IllegalArgumentException if index < 0 or 
  // index > size -1;
  // Target complexity: O(1)
  public void set(int index, T x){
    Location l = locate(index);
    getBlock(l.getBlockIndex()).setElement(l.getElementIndex(), x);
    
  }

  // Allocates one more spaces in the DynamicArray. This may
  // require the creation of a Block and the last SuperBlock may change. 
  // Also, expandArray is called if the arrayOfBlocks is full when
  // a Block is created. 
  // Called by add.
  // Target complexity: O(1)
  protected void grow(){
    if(numberOfDataBlocks == arrayOfBlocks.length &&
  getBlock(indexOfLastDataBlock).size() == getBlock(indexOfLastDataBlock).getCapacity()){ 
     expandArray(); // once the number of blocks reaches the length of the array and the last block is actually full
    }               // we must expand 
    if(getBlock(indexOfLastDataBlock).size() == getBlock(indexOfLastDataBlock).getCapacity()){ // check if the last data block has reaches max size
      if(lastSuperBlock.currentNumberOfDataBlocks == lastSuperBlock.maxNumberOfDataBlocks){ // then check if the super block is full of blocks
        if((lastSuperBlock.getNumber() % 2) == 0){ // even to odd, will create a new superblock and a new block to add elements to
        SuperBlock t = new SuperBlock(lastSuperBlock.getNumber()+1, lastSuperBlock.getMaxNumberOfDataBlocks(),
                                        lastSuperBlock.getMaxNumberOfElementsPerBlock()*2, 0); // throwaway superblock made for assignment
        lastSuperBlock = t; 
        t = null; // throwaway object assigned to null for garbage collection
        numberOfSuperBlocks++; // certain fields are increased based off of actions taken
        indexOfLastDataBlock++;
        numberOfDataBlocks++;
        sizeOfArrayOfBlocks++;
        numberOfNonEmptyDataBlocks++;
        arrayOfBlocks[indexOfLastDataBlock] = new Block<T>(indexOfLastDataBlock,
                                                           lastSuperBlock.getMaxNumberOfElementsPerBlock());
        lastSuperBlock.incrementCurrentNumberOfDataBlocks();
        }
      else{
        if((lastSuperBlock.getNumber() % 2) == 1){ // same thing occurs as the above code except this block runs if the superblock is going odd to even
        SuperBlock t = new SuperBlock(lastSuperBlock.getNumber()+1, lastSuperBlock.getMaxNumberOfDataBlocks()*2,
                                        lastSuperBlock.getMaxNumberOfElementsPerBlock(), 0);
         lastSuperBlock = t;
         t = null;
         numberOfSuperBlocks++;
        indexOfLastDataBlock++;
        numberOfDataBlocks++;
        sizeOfArrayOfBlocks++;
        numberOfNonEmptyDataBlocks++;
        arrayOfBlocks[indexOfLastDataBlock] = new Block<T>(indexOfLastDataBlock,
                                                           lastSuperBlock.getMaxNumberOfElementsPerBlock());
        lastSuperBlock.incrementCurrentNumberOfDataBlocks();         
      }
      }
    }
      else{ // if the super block can still add blocks then creates a new block and increases certain fields to accodomate the change
        indexOfLastDataBlock++;
        arrayOfBlocks[indexOfLastDataBlock] = new Block<T>(indexOfLastDataBlock, 
                                                         lastSuperBlock.getMaxNumberOfElementsPerBlock());
        sizeOfArrayOfBlocks++;
        numberOfDataBlocks++;
        numberOfNonEmptyDataBlocks++;
        lastSuperBlock.incrementCurrentNumberOfDataBlocks();
      }
  }
    getBlock(indexOfLastDataBlock).grow();
    numberOfEmptyDataBlocks = 0;
    indexOfLastNonEmptyDataBlock = indexOfLastDataBlock;
    if(numberOfDataBlocks == 1){
     numberOfNonEmptyDataBlocks = 1; 
    }
  }

  // Grows the DynamicArray by one space, increases the size of the 
  // DynamicArray, and sets the last element to x.  
  // Target complexity: O(1)
  public void add(T x){
     grow();
     getBlock(indexOfLastDataBlock).setElement((getBlock(indexOfLastDataBlock).size())-1,x);
     size++;
  }
    
    
    
 
  // Write a null value to the last element, shrinks the DynamicArray by one 
  // space, and decreases the size of the DynamicArray. A Block may be 
  // deleted and the last SuperBlock may change.
  // Also, shrinkArray is called if the arrayOfBlocks is less than or equal
  // to a quarter full when a Block is deleted. 
  // Throws IllegalStateException if the DynamicArray is empty when remove is
  // called.
  // Target complexity: O(1)
  public void remove(){
    if(size == 0){ // no elements to remove results in exception
     throw new IllegalStateException("Cannot remove when no elements present!");
    }
    getBlock(indexOfLastNonEmptyDataBlock).shrink(); // reduce the size of the block
    size--; // reduce number of elements currently
    if(sizeOfArrayOfBlocks >= 2){ // blocks are only removed if there are two empty adjacent blocks so we must check if the number of blocks is greater than 2
    if(getBlock(indexOfLastDataBlock).size() == 0 && getBlock(indexOfLastDataBlock-1).size() == 0){ // if current last data block and the one before it have its size both equal to 0
      if(lastSuperBlock.getCurrentNumberOfDataBlocks() == 1){ // checks if the last super block is at its last block
        if((lastSuperBlock.getNumber() % 2) == 0){ // even to odd super block
          arrayOfBlocks[indexOfLastDataBlock] = null; // set the last current block to null and decrement appropriate info
          indexOfLastDataBlock--;
          numberOfDataBlocks--;
          numberOfEmptyDataBlocks--;
          sizeOfArrayOfBlocks--;
          SuperBlock t = new SuperBlock(lastSuperBlock.getNumber()-1, lastSuperBlock.getMaxNumberOfDataBlocks()/2,
                                        lastSuperBlock.getMaxNumberOfElementsPerBlock(), lastSuperBlock.getMaxNumberOfDataBlocks()/2);
          // throwaway superblock to assign. new superblock calculated based off of exisitng super block
          lastSuperBlock = t;
          t = null;
          numberOfSuperBlocks--;
        }
        else{ // code same as above except odd to even
          arrayOfBlocks[indexOfLastDataBlock] = null;
          indexOfLastDataBlock--;
          numberOfDataBlocks--;
          numberOfEmptyDataBlocks--;
          sizeOfArrayOfBlocks--;
          SuperBlock t = new SuperBlock(lastSuperBlock.getNumber()-1, lastSuperBlock.getMaxNumberOfDataBlocks(),
                                        lastSuperBlock.getMaxNumberOfElementsPerBlock()/2, lastSuperBlock.getMaxNumberOfDataBlocks());
          lastSuperBlock = t;
          t = null;
          numberOfSuperBlocks--;      
        }
      }
      else{ // if the current super block can still have blocks removed then still assign last element to null but dont update any superblock info
        arrayOfBlocks[indexOfLastDataBlock] = null;
        indexOfLastDataBlock--;
        lastSuperBlock.decrementCurrentNumberOfDataBlocks();
        numberOfDataBlocks--;
      }
    }
    }
    if(getBlock(indexOfLastNonEmptyDataBlock).size() == 0){
     indexOfLastNonEmptyDataBlock--;
     numberOfEmptyDataBlocks++;
     numberOfNonEmptyDataBlocks--;
     if(sizeOfArrayOfBlocks < (arrayOfBlocks.length/2)){ // shrinks array if current array last than half full
      shrinkArray(); 
     }
    }
    
    }

  // Decreases the length of the arrayOfBlocks by half. Create a new
  // arrayOfBlocks and copy the Blocks from the old one to this new array.
  protected void shrinkArray(){
    int newCapacity = (arrayOfBlocks.length) / 2; // new size of new array which is half of current array
    Object[] copyArray = new Object[newCapacity]; // throwaway array to create deep copy
    for(int i = 0; i < copyArray.length; i++){ // loop through copying over elements
     copyArray[i] = arrayOfBlocks[i]; 
    }
    arrayOfBlocks = copyArray; // assignment of array to be used to throwaway array
    copyArray = null;
  }

  // Doubles the length of the arrayOfBlocks. Create a new
  // arrayOfBlocks and copy the Blocks from the old one to this new array.
  protected void expandArray(){
     int newCapacity = (arrayOfBlocks.length) * 2; // size of new array which is 2 times the current length
    Object[] copyArray = new Object[newCapacity]; // temp array to transfer objects to
    for(int i = 0; i < arrayOfBlocks.length; i++){ // loop through copying elements
     copyArray[i] = arrayOfBlocks[i]; 
    }
    arrayOfBlocks = copyArray; // assigns array to be used to temp array
    copyArray = null;
    
  }

  // Returns the size of the DynamicArray which is the number of elements that
  // have been added to it with the add(x) method but not removed.  The size 
  // does not correspond to the capacity of the array.
  public int size(){
   return size;
  }

  // Returns the log base 2 of n
  protected static double log2(int n) {
 return (Math.log(n) / Math.log(2));
  }

  // Returns a mask of N 1 bits; this code is provided below and can be used 
  // as is
  protected int maskOfN(int N) {
      int POW2ToN = 1 << N; // left shift 1 N places; e.g., 1 << 2 = 100 = 4
      int mask = POW2ToN - 1; // subtract 1; e.g., 1002 – 12 = 0112 = 3
      // Integer.toString(mask,2); // a String with the bits of mask
      return mask;
  }


  // Create a pretty representation of the DynamicArray. This method should
  // return string formatted similarly to ArrayList
  // Examples: [], [X],  [A, B, C, D]
  // 
  // Target Complexity: O(N)
  //   N: number of elements in the DynamicArray
  public String toString(){
    StringBuilder b = new StringBuilder();
    for(int i = 0; i < numberOfDataBlocks; i++){ // append elements using blocks toString() 
      if(arrayOfBlocks[i] == null){  
       continue; 
      }
     b.append(getBlock(i).toString()); // will not include commas however
    }
   char[] elements = new char[b.length()]; // create new array to store individual chars
   for(int z = 0; z < elements.length; z++){
    elements[z] =  b.charAt(z);
   }
   StringBuilder finalString = new StringBuilder(); // string to be resulted that includes commas in between
   finalString.append("[ ");
   for(int g = 0; g < elements.length; g++){
     finalString.append(elements[g]);
     finalString.append(",");
   }
   finalString.deleteCharAt(finalString.length()-1); // removal of extra comma at the end
   finalString.append("]");
   return finalString.toString(); 
  }

  // Create a pretty representation of the DynamicArray for debugging
  // Example: 
  // DynamicArray: A B 
  // numberOfDataBlocks: 2
  // numberOfEmptyDataBlocks: 0
  // numberOfNonEmptyDataBlocks: 2
  // indexOfLastNonEmptyDataBlock: 1
  // indexOfLastDataBlock: 1
  // numberOfSuperBlocks: 2
  // lastSuperBlock: SB1
  // Block0: A 
  // - capacity: 1 size: 1
  // Block1: B 
  // - capacity: 2 size: 1
  // SB1:
  // - maxNumberOfDataBlocks: 1
  // - numberOfElementsPerBlock: 2
  // - currentNumberOfDataBlocks: 1

  protected String toStringForDebugging(){
    String x = "DynamicArray:";
    for(int i = 0; i < sizeOfArrayOfBlocks; i++){ // loop through everyblock and appending its contents
     x+=getBlock(i).toString(); 
    }
    // adds fields of the class to a string representation for easy debugging
    x+="\n" + "numberOfDataBlocks : " + numberOfDataBlocks + "\n" + "numberOfEmptyDataBlocks : " +
      numberOfEmptyDataBlocks + "\n" + "numberOfNonEmptyDataBlocks : " + numberOfNonEmptyDataBlocks + "\n" +
      "indexOfLastNonEmptyDataBlock : " + indexOfLastNonEmptyDataBlock + "\n" + "indexOfLastDataBlock : " + 
      indexOfLastDataBlock + "\n" + "numberOfSuperBlocks : " + numberOfSuperBlocks + "\n" + "lastSuperBlock : SB" +
      lastSuperBlock.getNumber() + "\n";
    for(int i = 0; i < numberOfDataBlocks; i++){
      if(arrayOfBlocks[i]==null){continue;}
     x+="Block" + getBlock(i).getNumber() + ": "  + "\n" + getBlock(i).toStringForDebugging() + "\n";
    }
    x+="SB" + lastSuperBlock.getNumber() + ":" + "\n" + lastSuperBlock.toStringForDebugging();
   return x;
  }
}
