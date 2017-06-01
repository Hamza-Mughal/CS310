// contains information about blocks
// used by dyanamicarray
public class SuperBlock {
 protected int number;  // as in S0, S1, etc.
protected int maxNumberOfDataBlocks; 
 // number of elements per Block
 protected int maxNumberOfElementsPerBlock; 
 // current number of Blocks in this SuperBlock
 protected int currentNumberOfDataBlocks;

 // Workhorse constructor. Initialize variables.
 public SuperBlock(int number, int maxNumberOfDataBlocks,
                   int maxNumberOfElementsPerBlock, int currentNumberOfDataBlocks){
  this.number = number;
  this.maxNumberOfDataBlocks = maxNumberOfDataBlocks;
  this.maxNumberOfElementsPerBlock = maxNumberOfElementsPerBlock;
  this.currentNumberOfDataBlocks = currentNumberOfDataBlocks;
 }

 // Returns number.
 public int getNumber(){
  return this.number; 
 }

 // Returns maxNumberOfDataBlocks
 public int getMaxNumberOfDataBlocks(){
  return this.maxNumberOfDataBlocks;
 }

 // Returns maxNumberOfElementsPerBlock
 public int getMaxNumberOfElementsPerBlock(){
  return this.maxNumberOfElementsPerBlock; 
 }

 // Returns currentNumberOfDataBlocks 
 public int getCurrentNumberOfDataBlocks(){
  return this.currentNumberOfDataBlocks; 
 }

 // Increments CurrentNumberOfDataBlocks
 public void incrementCurrentNumberOfDataBlocks(){
   this.currentNumberOfDataBlocks++;
 }

 // Decrements currentNumberOfDataBlocks
 public void decrementCurrentNumberOfDataBlocks(){
   this.currentNumberOfDataBlocks--;
 }

 // Create a pretty representation of the SuperBlock for debugging.
 // Example:
 //   - maxNumberOfDataBlocks:2 
 //   - numberOfElementsPerBlock:2
 //   - currentNumberOfDataBlocks:1
 protected String toStringForDebugging(){
   return "- maxNumberOfDataBlocks: " + this.maxNumberOfDataBlocks + "\n" + "- maxNumberOfElementsPerBlock: " 
     + this.maxNumberOfElementsPerBlock + "\n" + "- currentNumberOfDataBlocks: " + this.currentNumberOfDataBlocks + "\n";
 }
}
