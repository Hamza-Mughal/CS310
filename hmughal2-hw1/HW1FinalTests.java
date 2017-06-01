// CHANGELOG: 
// Fri Sep 23 10:09:25 EDT 2016 : Altered da.getArrayOfBlocks() to just da.arrayOfBlocks

/* To run them on the command line, make sure that the junit-310.jar
   is in the project directory.
 
   demo$ javac -cp .:junit-cs310.jar *.java     # compile everything
   demo$ java  -cp .:junit-cs310.jar HW1FinalTests   # run tests
 
   On Windows replace : with ; (colon with semicolon)
   demo$ javac -cp .;junit-cs310.jar *.java     # compile everything
   demo$ java  -cp .;junit-cs310.jar HW1FinalTests   # run tests
*/

import org.junit.*;
import org.junit.Test; // fixes some compile problems with annotations
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

public class HW1FinalTests{
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main("HW1FinalTests");
  }
  
  // @Test(timeout=1000,expected=SomeException.class)

  public static void failFmt(String fmt, Object... args){
    fail(String.format(fmt,args));
  }

  static String printArray(Object[] array) {
    StringBuilder B = new StringBuilder();
    B.append("[ ");
    for(Object o: array)
      B.append(o + " ");
    B.append("]");
    return B.toString();
  }

  // start Milestone Tests

  // Location_Tests

  @Test(timeout=1000) public void Location_construct1() {
    // Check construct Location
    Location l1 = new Location(1,0);
  
    if(! (l1.blockIndex==1 && l1.elementIndex == 0)){
      failFmt("Location constructor:\n"+
              "Expect: + l1.blockIndex==1 l1.elementIndex==0\n" +
              "Actual: l1.blockIndex==" + l1.blockIndex + " l1.elementIndex==" + l1.elementIndex);
    }
  }

  @Test(timeout=1000) public void Location_getBlockIndex1(){
    // test getBlockIndex()
    Location l1 = new Location(1,0);
  
    if(l1.getBlockIndex()!=1){
      failFmt("Location getBlockIndex:\n"+
              "Expect: l1.getBlockIndex==1\n" +
              "Actual: l1.getBlockIndex==" + l1.getBlockIndex());
    }
  }

  @Test(timeout=1000) public void Location_getElementIndex1(){
    // test getElementIndex()
    Location l1 = new Location(1,0);
  
    if(l1.getElementIndex()!=0){
      failFmt("Location getElementIndex:\n"+
              "Expect: l1.getElementIndex==0\n" +
              "Actual: l1.getElementIndex==" + l1.getElementIndex());
    }
  }

  @Test(timeout=1000) public void Location_toStringForDebugging1(){
    // test toStringForDebugging()
    // output expected to be:
    // blockIndex:2 elementIndex:1
    // Before we check the output, we stip away the white space, resulting in
    // blockIndex:2elementIndex:1
    // So this ignores minor spacing differences between the actual and expected Strins
    Location l1 = new Location(1,0);
    String result = l1.toStringForDebugging().replaceAll("\\s+",""); // For example, "A B" becomes "AB", so does "A   B"
  
    if(!(result.equals("blockIndex:1elementIndex:0"))){
      failFmt("Location toStringForDebugging:\n"+
              "Expect: l1.toStringForDebugging = blockIndex:1 elementIndex:0\n" +
              "Actual: l1.toStringForDebugging = " + l1.toStringForDebugging());
    }
  }


  // SuperBlock_Tests


  @Test(timeout=1000) public void SuperBlock_construct1() {
    // Check construct SuperBlock
    SuperBlock s1 = new SuperBlock(1,2,4,0);
  
    if(! (s1.number==1 && s1.maxNumberOfDataBlocks == 2 && s1.maxNumberOfElementsPerBlock==4 && s1.currentNumberOfDataBlocks == 0)){
      failFmt("SuperBlock constructor:\n"+
              "Expect:s1.number==1  s1.maxNumberOfDataBlocks==2  s1.maxNumberOfElementsPerBlock==4  s1.currentNumberOfDataBlocks == 0" +
              "Actual: s1.number==" + s1.number + " s1.maxNumberOfDataBlocks==" + s1.maxNumberOfDataBlocks
              + " s1.maxNumberOfElementsPerBlock==" + s1.maxNumberOfElementsPerBlock
              + " s1.currentNumberOfDataBlocks==" + s1.currentNumberOfDataBlocks);
    }
  }

  @Test(timeout=1000) public void SuperBlock_getNumber1(){
    // test getNumber()
    SuperBlock s1 = new SuperBlock(1,2,4,0);
  
    if(s1.getNumber()!=1){
      failFmt("SuperBlock getNumber:\n"+
              "Expect: +s1.getNumber==1\n" +
              "Actual: s1.getNumber==" + s1.getNumber());
    }
  }

  @Test(timeout=1000) public void SuperBlock_getMaxNumberOfDataBlocks1(){
    // test getMaxNumberOfDataBlocks()
    SuperBlock s1 = new SuperBlock(1,2,4,0);
  
    if(s1.getMaxNumberOfDataBlocks()!=2){
      failFmt("SuperBlock getMaxNumberOfDataBlocks:\n"+
              "Expect: +s1.getMaxNumberOfDataBlocks==2\n" +
              "Actual: s1.getMaxNumberOfDataBlocks==" + s1.getMaxNumberOfDataBlocks());
    }
  }

  @Test(timeout=1000) public void SuperBlock_getMaxNumberOfElementsPerBlock1(){
    // test getMaxNumberOfElementsPerBlock()
    SuperBlock s1 = new SuperBlock(1,2,4,0);
  
    if(s1.getMaxNumberOfElementsPerBlock()!=4){
      failFmt("SuperBlock getMaxNumberOfElementsPerBlock:\n"+
              "Expect: +s1.getMaxNumberOfElementsPerBlock==4\n" +
              "Actual: s1.getMaxNumberOfElementsPerBlock==" + s1.getMaxNumberOfElementsPerBlock());
    }
  }

  @Test(timeout=1000) public void SuperBlock_getCurrentNumberOfDataBlocks1(){
    // test getCurrentNumberOfDataBlocks()
    SuperBlock s1 = new SuperBlock(1,2,4,0);
  
    if(s1.getCurrentNumberOfDataBlocks()!=0){
      failFmt("SuperBlock getCurrentNumberOfDataBlocks:\n"+
              "Expect: s1.getCurrentNumberOfDataBlocks==0\n" +
              "Actual: s1.getCurrentNumberOfDataBlocks==" + s1.getCurrentNumberOfDataBlocks());
    }
  }

  @Test(timeout=1000) public void SuperBlock_incrementCurrentNumberOfDataBlocks1(){
    // test incrementCurrentNumberOfDataBlocks()
    SuperBlock s1 = new SuperBlock(1,2,4,0);
    s1.incrementCurrentNumberOfDataBlocks();
  
    if(s1.getCurrentNumberOfDataBlocks()!=1){
      failFmt("SuperBlock incrementCurrentNumberOfDataBlocks:\n"+
              "Expect: +s1.getCurrentNumberOfDataBlocks==1\n" +
              "Actual: s1.getCurrentNumberOfDataBlocks==" + s1.getCurrentNumberOfDataBlocks());
    }
  }

  @Test(timeout=1000) public void SuperBlock_decrementCurrentNumberOfDataBlocks1(){
    // test decrementCurrentNumberOfDataBlocks()
    SuperBlock s1 = new SuperBlock(1,2,4,1);
    s1.decrementCurrentNumberOfDataBlocks();
  
    if(s1.getCurrentNumberOfDataBlocks()!=0){
      failFmt("SuperBlock decrementCurrentNumberOfDataBlocks:\n"+
              "Expect: +s1.getCurrentNumberOfDataBlocks==0\n" +
              "Actual: s1.getCurrentNumberOfDataBlocks==" + s1.getCurrentNumberOfDataBlocks());
    }
  }

  @Test(timeout=1000) public void SuperBlock_toStringForDebugging1(){
    // test toStringForDebugging()
    // output expected to be:
    // - maxNumberOfDataBlocks:2    
    // - maxNumberOfElementsPerBlock:4
    // - currentNumberOfDataBlocks:1
    // Before we check the output, we stip away the white space, resulting in
    // -maxNumberOfDataBlocks:2-maxNumberOfElementsPerBlock:4-currentNumberOfDataBlocks:1
    // So this ignores minor spacing differences between the actual and expected Strings

    SuperBlock s1 = new SuperBlock(1,2,4,1);
    String result = s1.toStringForDebugging().replaceAll("\\s+",""); // For example, "A B" becomes "AB", so does "A   B"
  
    if(!(result.equals("-maxNumberOfDataBlocks:2-maxNumberOfElementsPerBlock:4-currentNumberOfDataBlocks:1"))){
      failFmt("SuperBlock toStringForDebugging:\n"+
              "Expect: s1.toStringForDebugging = -maxNumberOfDataBlocks:2-maxNumberOfElementsPerBlock:4-currentNumberOfDataBlocks:1\n" +
              "Actual: l1.toStringForDebugging = " + result);
    }
  }


  // Block_Tests

  @Test(timeout=1000) public void Block_construct1() {
    // Check construct Block
    Block<String> b1 = new Block<String>(1,2);
  
    if(! (b1.number==1 && b1.capacity == 2 && b1.size==0)){
      failFmt("Block constructor:\n"+
              "Expect:b1.number==1 b1.capacity == 2 b1.size==0" +
              "Actual: b1.number==" + b1.number + " b1.capacity==" + b1.capacity
              + " b1.size==" + b1.size);
    }
  }

  @Test(timeout=1000) public void Block_getNumber1(){
    // test getCapacity()
    Block<String> b1 = new Block<String>(1,2);
  
    if(b1.getNumber()!=1){
      failFmt("Block getNumber:\n"+
              "Expect: +b1.getNumber==1\n" +
              "Actual: b1.getNumber==" + b1.getNumber());
    }
  }


  @Test(timeout=1000) public void Block_getCapacity1(){
    // test getCapacity()
    Block<String> b1 = new Block<String>(1,2);
  
    if(b1.getCapacity()!=2){
      failFmt("Block getCapacity:\n"+
              "Expect: +b1.getCapacity==2\n" +
              "Actual: b1.getCapacity==" + b1.getCapacity());
    }
  }

  @Test(timeout=1000) public void Block_grow1(){
    // test grow()
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
  
    if(b1.size()!=1){
      failFmt("Block grow:\n"+
              "Expect: +b1.size==1\n" +
              "Actual: b1.size==" + b1.size());
    }
  }

  @Test(timeout=1000) public void Block_grow2(){
    // test grow()
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.grow();
  
    if(b1.size()!=2){
      failFmt("Block grow:\n"+
              "Expect: +b1.size==2\n" +
              "Actual: b1.size==" + b1.size());
    }
  }

  @Test(timeout=1000) public void Block_size1(){
    // test size()
    Block<String> b1 = new Block<String>(1,2);
  
    if(b1.size()!=0){
      failFmt("Block size:\n"+
              "Expect: +b1.size==0\n" +
              "Actual: b1.size==" + b1.size());
    }
  }

  @Test(timeout=1000) public void Block_shrink1(){
    // test shrink()
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.shrink();
  
    if(b1.size()!=0){
      failFmt("Block shrink:\n"+
              "Expect: +b1.size==0\n" +
              "Actual: b1.size==" + b1.size());
    }
  }

  @Test(timeout=1000) public void Block_shrink2(){
    // test shrink()
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.grow();
    b1.shrink();
    b1.shrink();
  
    if(b1.size()!=0){
      failFmt("Block shrink:\n"+
              "Expect: +b1.size==0\n" +
              "Actual: b1.size==" + b1.size());
    }
  }

  @Test(timeout=1000) public void Block_get_setElement1(){
    // test get/setElement()
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.setElement(0,"A");
    String result = b1.getElement(0);
  
    if(!(result.equals("A"))){
      failFmt("Block setElement:\n"+
              "Expect: +b1.getElement(0)==A\n" +
              "Actual: b1.getElement(0)==" + b1.getElement(0));
    }
  }

  @Test(timeout=1000) public void Block_get_setElement2(){
    // test get/setElement()
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.grow();
    b1.setElement(0,"A");
    b1.setElement(1,"B");
    String result = b1.getElement(1);
  
    if(!(result.equals("B"))){
      failFmt("Block setElement:\n"+
              "Expect: +b1.getElement(1)==B\n" +
              "Actual: b1.getElement(1)==" + b1.getElement(1));
    }
  }

  @Test(timeout=1000) public void Block_shrink3(){
    // test shrink() sets last element to null
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.grow();
    b1.setElement(0,"A");
    b1.setElement(1,"B");
    b1.shrink(); // set Element in position 1 to null
    b1.shrink(); //set Element in position 0 to null
  
    if(b1.size()!=0){
      failFmt("Block shrink:\n"+
              "Expect: +b1.size==0\n" +
              "Actual: b1.size==" + b1.size());
    }
    Object [] arrayOfElements = b1.arrayOfElements;
    if(arrayOfElements[1]!=null){
      failFmt("Block shrink:\n"+
              "Expect: b1.arrayOfElements[1]==null" +
              "Actual: b1.arrayOfElements[1]==" + (b1.arrayOfElements[1]));
    }
    if(arrayOfElements[0]!=null){
      failFmt("Block shrink:\n"+
              "Expect: b1.arrayOfElements[0]==null" +
              "Actual: b1.arrayOfElements[0]==" + (b1.arrayOfElements[0]));
    }
  }

  @Test(timeout=1000) public void Block_toString1(){
    // test toString()
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.setElement(0,"A");
    String result = b1.toString().replaceAll("\\s+","");
  
    if(!(result.equals("A"))){
      failFmt("Block toString:\n"+
              "Expect: +b1.toString()==A\n" +
              "Actual: b1.toString()==" + b1.toString());
    }
  }

  @Test(timeout=1000) public void Block_toString2(){
    // test toString()
    // output expected to be:
    // A B
    // Before we check the output, we stip away the white space, resulting in
    // AB
    // So this ignores  spacing differences between "A B" and "A   B" etc

    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.grow();
    b1.setElement(0,"A");
    b1.setElement(1,"B");
    String result = b1.toString().replaceAll("\\s+",""); // "A B" becomes "AB", so does "A   B"
  
    if(!(result.equals("AB"))){
      failFmt("Block toString:\n"+
              "Expect: +b1.toString()==AB\n" +
              "Actual: b1.toString()==" + b1.toString());
    }
  }

  @Test(timeout=1000) public void Block_toStringForDebugging1(){
    // test toStringForDebugging()
    // output expected to be:
    // AB
    // -capacity=2 size=2
    // Before we check the output, we stip away the white space, resulting in
    // AB-capacity=2size=2
    // So this ignores minor spacing differences between expected and actual result
   
    Block<String> b1 = new Block<String>(1,2);
    b1.grow();
    b1.grow();
    b1.setElement(0,"A");
    b1.setElement(1,"B");
    String result = b1.toStringForDebugging().replaceAll("\\s+",""); 
    // Strips spaces, for example
    // "A B" becomes "AB", so does "A   B"
  
    if(!(result.equals("AB-capacity=2size=2"))){
      failFmt("Block toStringForDebugging:\n"+
              "Expect: +b1.toStringForDebugging()==AB-capacity=2size=2\n" +
              "Actual: b1.toStringForDebugging()==" + result);
    }
  }

  // DynamicArray_Tests

  @Test(timeout=1000) public void DynamicArray_construct1() {
    // Check construct DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
  
    if(! (d1.sizeOfArrayOfBlocks==1 && d1.size == 0 && d1.numberOfEmptyDataBlocks==1
          && d1.numberOfNonEmptyDataBlocks==0 && d1.numberOfDataBlocks == 1 && d1.indexOfLastNonEmptyDataBlock==-1
          && d1.indexOfLastDataBlock==0 && d1.numberOfSuperBlocks == 1 && d1.lastSuperBlock.getNumber()==0)){
      failFmt("DynamicArray constructor:\n"+
              "Expect: d1.sizeOfArrayOfBlocks==1 d1.size == 0 d1.numberOfEmptyDataBlocks==1 " +
              "d1.numberOfNonEmptyDataBlocks==0  d1.numberOfDataBlocks == 1  d1.indexOfLastNonEmptyDataBlock==-1 " +
              "d1.indexOfLastDataBlock==0  d1.numberOfSuperBlocks == 1  d1.lastSuperBlock.getNumber==0" +
              "Actual: d1.sizeOfArrayOfBlocks== " + d1.sizeOfArrayOfBlocks + " d1.size == " + d1.size + " d1.numberOfEmptyDataBlocks==" +
              d1.numberOfEmptyDataBlocks + " d1.numberOfNonEmptyDataBlocks== " + d1.numberOfNonEmptyDataBlocks + " d1.numberOfDataBlocks== " + d1.numberOfDataBlocks + " d1.indexOfLastNonEmptyDataBlock==" +
              d1.indexOfLastNonEmptyDataBlock + " d1.indexOfLastDataBlock==" + d1.indexOfLastDataBlock + " d1.numberOfSuperBlocks==" + d1.numberOfSuperBlocks + " d1.lastSuperBlock.getNumber()" + 
              d1.lastSuperBlock.getNumber());
    }
  }

  @Test(timeout=1000) public void DynamicArray_getBlock1(){
    // test getBlock()
    DynamicArray<String> d1 = new DynamicArray<String>();
    Block<String> b = d1.getBlock(0);

    if(b.getNumber()!=0){
      failFmt("DynamicArray getBlock:\n"+
              "Expect: +b.getNumber==0\n" +
              "Actual: b.getNumber==" + b.getNumber());
    }
 
    if(b.getCapacity()!=1){
      failFmt("DynamicArray getBlock:\n"+
              "Expect: +b.getCapacity==1\n" +
              "Actual: b.getCapacity==" + b.getCapacity());
    }
    if(b.size()!=0){
      failFmt("DynamicArray getBlock:\n"+
              "Expect: +b.size==0n" +
              "Actual: b.size==" + b.size());
    }
  }

 
  @Test(timeout=1000) public void DynamicArray_size1(){
    // test size()
    DynamicArray<String> d1 = new DynamicArray<String>();
  
    if(d1.size()!=0){
      failFmt("DynamicArray size:\n"+
              "Expect: +d1.size==0\n" +
              "Actual: d1.size==" + d1.size());
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow1(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
 
    Block<String> b = d1.getBlock(0);

    if(b.getNumber()!=0){
      failFmt("DynamicArray grow:\n"+
              "Expect: +b.getNumber==0\n" +
              "Actual: b.getNumber==" + b.getNumber());
    }
 
    if(b.getCapacity()!=1){
      failFmt("DynamicArray grow:\n"+
              "Expect: +b.getCapacity==1\n" +
              "Actual: b.getCapacity==" + b.getCapacity());
    }
    if(b.size()!=1){
      failFmt("DynamicArray grow:\n"+
              "Expect: +b.size==1\n" +
              "Actual: b.size==" + b.size());
    }
  }

  // end Milestone Tests

  //DynamicArray_add

  @Test(timeout=1000) public void DynamicArray_add1(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A"); // grow one space in Block0

    String X = (String) ((Block)d1.arrayOfBlocks[0]).getElement(0);
    if(!(X.equals("A"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==A" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add2(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");

    String X = (String) ((Block)d1.arrayOfBlocks[1]).getElement(0);
    if(!(X.equals("B"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==B" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add3(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");

    String X = (String) ((Block)d1.arrayOfBlocks[1]).getElement(1);
    if(!(X.equals("C"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==C" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add4(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");

    String X = (String) ((Block)d1.arrayOfBlocks[2]).getElement(0);
    if(!(X.equals("D"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==D" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add5(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");

    String X = (String) ((Block)d1.arrayOfBlocks[2]).getElement(1);
    if(!(X.equals("E"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==E" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add6(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");

    String X = (String) ((Block)d1.arrayOfBlocks[3]).getElement(0);
    if(!(X.equals("F"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==E" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add7(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");

    String X = (String) ((Block)d1.arrayOfBlocks[3]).getElement(1);
    if(!(X.equals("G"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==G" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add8(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");

    String X = (String) ((Block)d1.arrayOfBlocks[4]).getElement(0);
    if(!(X.equals("H"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==H" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add9(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");

    String X = (String) ((Block)d1.arrayOfBlocks[4]).getElement(1);
    if(!(X.equals("I"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==I" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add10(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");
    d1.add("J");

    String X = (String) ((Block)d1.arrayOfBlocks[4]).getElement(2);
    if(!(X.equals("J"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==J" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add11(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");
    d1.add("J");
    d1.add("K");

    String X = (String) ((Block)d1.arrayOfBlocks[4]).getElement(3);
    if(!(X.equals("K"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==K" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add12(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");
    d1.add("J");
    d1.add("K");
    d1.add("L");
    d1.add("M");
    d1.add("N");
    d1.add("O");

    String X = (String) ((Block)d1.arrayOfBlocks[5]).getElement(3);
    if(!(X.equals("O"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==O" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add13(){
    // test add()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");
    d1.add("J");
    d1.add("K");
    d1.add("L");
    d1.add("M");
    d1.add("N");
    d1.add("O");
    d1.add("P");

    String X = (String) ((Block)d1.arrayOfBlocks[6]).getElement(0);
    if(!(X.equals("P"))) {
      failFmt("DynamicArray add:\n"+
              "Expect: X==P" + "\n" +
              "Actual: X==" + X);
    }
  }

  // DynamicArray expandArray

  @Test(timeout=1000) public void DynamicArray_ExpandArray1(){
    // test expandArray()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    int capacity = d1.arrayOfBlocks.length;
    if(capacity != 4) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: capacity=4" + "\n" +
              "Actual: capacity==" + capacity);
    }

    d1.add("H");

    capacity = d1.arrayOfBlocks.length;
    if(capacity != 8) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: capacity=8" + "\n" +
              "Actual: capacity==" + capacity);
    }
    String a = (String) (((Block) d1.arrayOfBlocks[0]).arrayOfElements[0]);
    if(!a.equals("A")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: a equals A" + "\n" +
              "Actual: a equals " + a);
    }
    String b = (String) (((Block) d1.arrayOfBlocks[1]).arrayOfElements[0]);
    if(!b.equals("B")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: b equals B" + "\n" +
              "Actual: b equals " + b);
    }
    String c = (String) (((Block) d1.arrayOfBlocks[1]).arrayOfElements[1]);
    if(!c.equals("C")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: c equals C" + "\n" +
              "Actual: c equals " + c);
    }
    String d = (String) (((Block) d1.arrayOfBlocks[2]).arrayOfElements[0]);
    if(!d.equals("D")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: d equals D" + "\n" +
              "Actual: d equals " + d);
    }
    String e = (String) (((Block) d1.arrayOfBlocks[2]).arrayOfElements[1]);
    if(!e.equals("E")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: e equals E" + "\n" +
              "Actual: e equals " + e);
    }
    String f = (String) (((Block) d1.arrayOfBlocks[3]).arrayOfElements[0]);
    if(!f.equals("F")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: f equals F" + "\n" +
              "Actual: f equals " + f);
    }
    String g = (String) (((Block) d1.arrayOfBlocks[3]).arrayOfElements[1]);
    if(!g.equals("G")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: g equals G" + "\n" +
              "Actual: g equals " + g);
    }
    String h = (String) (((Block) d1.arrayOfBlocks[4]).arrayOfElements[0]);
    if(!h.equals("H")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: h equals H" + "\n" +
              "Actual: h equals " + h);
    }
  }

  // DynamicArray shrinkArray

  @Test(timeout=1000) public void DynamicArray_ShrinkArray1(){
    // test expandArray()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    int capacity1 = d1.arrayOfBlocks.length;
    if(capacity1 != 8) {
      failFmt("DynamicArray ShrinkArray:\n"+
              "Expect: capacity1=8" + "\n" +
              "Actual: capacity1==" + capacity1);
    }
    d1.remove(); // remove H
    int capacity2 = d1.arrayOfBlocks.length;
    if(capacity2 != 8) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: capacity2=8" + "\n" +
              "Actual: capacity2==" + capacity2);
    }
    d1.remove(); // remove G
    d1.remove(); // remore F
    d1.remove(); // remove E
    d1.remove(); // remove D
    int capacity3 = d1.arrayOfBlocks.length;
    if(capacity3 != 8) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: capacity3=8" + "\n" +
              "Actual: capacity3==" + capacity3);
    }
    d1.remove(); // remove C
    d1.remove(); // remove B
    int capacity4 = d1.arrayOfBlocks.length;
    if(capacity4 != 4) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: capacity4=4" + "\n" +
              "Actual: capacity4==" + capacity4);
    }

    String a = (String) (((Block) d1.arrayOfBlocks[0]).arrayOfElements[0]);
    if(!a.equals("A")) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: a equals A" + "\n" +
              "Actual: a equals " + a);
    }
    String b = (String) (((Block) d1.arrayOfBlocks[1]).arrayOfElements[0]);
    if(b != null) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: b == null" + "\n" +
              "Actual: b == " + b);
    }
    String c = (String) (((Block) d1.arrayOfBlocks[1]).arrayOfElements[1]);
    if(c != null) {
      failFmt("DynamicArray expandArray:\n"+
              "Expect: c == null" + "\n" +
              "Actual: c == " + c);
    }
  }

  // DynamicArray Get

  @Test(timeout=1000) public void DynamicArray_get1(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    String X = d1.get(0);

    if(!(X.equals("A"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==A" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get2(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    String X = d1.get(1);

    if(!(X.equals("B"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==B" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get3(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    String X = d1.get(2);

    if(!(X.equals("C"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==C" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get4(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    String X = d1.get(3);

    if(!(X.equals("D"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==D" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get5(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    String X = d1.get(4);

    if(!(X.equals("E"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==E" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get6(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    String X = d1.get(5);

    if(!(X.equals("F"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==F" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get7(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    String X = d1.get(6);

    if(!(X.equals("G"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==G" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get8(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    String X = d1.get(1);

    if(!(X.equals("B"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==B" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get9(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    String X = d1.get(4);

    if(!(X.equals("E"))) {
      failFmt("DynamicArray get:\n"+
              "Expect: X==E" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_get10(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    boolean failed = false;
    try {
      String X = d1.get(-1);
    } catch (IllegalArgumentException  e) {
      failed = true;
    }

    if(!failed){
      failFmt("DynamicArray get:\n"+
              "Expect: IllegalArgumentException" + "\n" +
              "Actual: no IllegalArgumentException "+"\n" + "");
    }
  }

  @Test(timeout=1000) public void DynamicArray_get11(){
    // test get()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    boolean failed = false;
    try {
      String X = d1.get(7);
    } catch (IllegalArgumentException  e) {
      failed = true;
    }

    if(!failed){
      failFmt("DynamicArray get:\n"+
              "Expect: IllegalArgumentException" + "\n" +
              "Actual: no IllegalArgumentException "+"\n" + "");
    }
  }

  //DynamicArray getBlock

  @Test(timeout=1000) public void DynamicArray_GetBlock1(){
    // test getBlock()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A"); // grow one space in Block0

    Block<String> b = d1.getBlock(0);
    int blockNumber = b.getNumber();

    if(blockNumber!=0) {
      failFmt("DynamicArray getBlock:\n"+
              "Expect: blockNumber == 0" + "\n" +
              "Actual: blockNumber == " + blockNumber);
    }
  }

  @Test(timeout=1000) public void DynamicArray_GetBlock2(){
    // test getBlock()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A"); 
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");

    Block<String> b = d1.getBlock(2);
    int blockNumber = b.getNumber();

    if(blockNumber!=2) {
      failFmt("DynamicArray getBlock:\n"+
              "Expect: blockNumber == 2" + "\n" +
              "Actual: blockNumber == " + blockNumber);
    }
  }

  //DynamicArray grow

  @Test(timeout=1000) public void DynamicArray_grow2(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    Block b0 = d1.getBlock(0);

    if (!(
          d1.numberOfDataBlocks == 1 &&
          d1.sizeOfArrayOfBlocks == 1 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 1 && 
          d1.indexOfLastNonEmptyDataBlock == 0 &&
          d1.indexOfLastDataBlock == 0 &&
          d1.numberOfSuperBlocks == 1 &&  
          d1.lastSuperBlock.getNumber() == 0 &&
          b0.getNumber() == 0 &&
          b0.getCapacity() == 1 &&
          b0.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 1 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 1\n" +
              "d1.sizeOfArrayOfBlocks == 1\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 1\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 0\n" +
              "d1.indexOfLastDataBlock == 0\n" +
              "d1.numberOfSuperBlocks == 1\n" +
              "d1.lastSuperBlock.getNumber == 0\n" +
              "b0.getNumber() == 0\n" +
              "b0.getCapacity() == 1\n" +
              "b0.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 1\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b0.getNumber() == " + b0.getNumber() + "\n" + 
              "b0.getCapacity() == " +  b0.getCapacity() + "\n" + 
              "b0.size() == " +  b0.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow3(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    Block b1 = d1.getBlock(1);

    if (!(
          d1.numberOfDataBlocks == 2 &&
          d1.sizeOfArrayOfBlocks == 2 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 2 && 
          d1.indexOfLastNonEmptyDataBlock == 1 &&
          d1.indexOfLastDataBlock == 1 &&
          d1.numberOfSuperBlocks == 2 &&  
          d1.lastSuperBlock.getNumber() == 1 &&
          b1.getNumber() == 1 &&
          b1.getCapacity() == 2 &&
          b1.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 2\n" +
              "d1.sizeOfArrayOfBlocks == 2\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 2\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 1\n" +
              "d1.indexOfLastDataBlock == 1\n" +
              "d1.numberOfSuperBlocks == 2\n" +
              "d1.lastSuperBlock.getNumber == 1\n" +
              "b1.getNumber() == 1\n" +
              "b1.getCapacity() == 2\n" +
              "b1.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b1.getNumber() == " + b1.getNumber() + "\n" + 
              "b1.getCapacity() == " +  b1.getCapacity() + "\n" + 
              "b1.size() == " +  b1.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow4(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    Block b1 = d1.getBlock(1);

    if (!(
          d1.numberOfDataBlocks == 2 &&
          d1.sizeOfArrayOfBlocks == 2 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 2 && 
          d1.indexOfLastNonEmptyDataBlock == 1 &&
          d1.indexOfLastDataBlock == 1 &&
          d1.numberOfSuperBlocks == 2 &&  
          d1.lastSuperBlock.getNumber() == 1 &&
          b1.getNumber() == 1 &&
          b1.getCapacity() == 2 &&
          b1.size() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 2\n" +
              "d1.sizeOfArrayOfBlocks == 2\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 2\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 1\n" +
              "d1.indexOfLastDataBlock == 1\n" +
              "d1.numberOfSuperBlocks == 2\n" +
              "d1.lastSuperBlock.getNumber == 1\n" +
              "b1.getNumber() == 1\n" +
              "b1.getCapacity() == 2\n" +
              "b1.size() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b1.getNumber() == " + b1.getNumber() + "\n" + 
              "b1.getCapacity() == " +  b1.getCapacity() + "\n" + 
              "b1.size() == " +  b1.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }



  @Test(timeout=1000) public void DynamicArray_grow5(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    Block b2 = d1.getBlock(2);

    if (!(
          d1.numberOfDataBlocks == 3 &&
          d1.sizeOfArrayOfBlocks == 3 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 3 && 
          d1.indexOfLastNonEmptyDataBlock == 2 &&
          d1.indexOfLastDataBlock == 2 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b2.getNumber() == 2 &&
          b2.getCapacity() == 2 &&
          b2.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 3\n" +
              "d1.sizeOfArrayOfBlocks == 3\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 3\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 2\n" +
              "d1.indexOfLastDataBlock == 2\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b2.getNumber() == 2\n" +
              "b2.getCapacity() == 2\n" +
              "b2.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b2.getNumber() == " + b2.getNumber() + "\n" + 
              "b2.getCapacity() == " +  b2.getCapacity() + "\n" + 
              "b2.size() == " +  b2.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow6(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    d1.grow(); // grow second space in Block2
    Block b2 = d1.getBlock(2);

    if (!(
          d1.numberOfDataBlocks == 3 &&
          d1.sizeOfArrayOfBlocks == 3 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 3 && 
          d1.indexOfLastNonEmptyDataBlock == 2 &&
          d1.indexOfLastDataBlock == 2 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b2.getNumber() == 2 &&
          b2.getCapacity() == 2 &&
          b2.size() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 3\n" +
              "d1.sizeOfArrayOfBlocks == 3\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 3\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 2\n" +
              "d1.indexOfLastDataBlock == 2\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b2.getNumber() == 2\n" +
              "b2.getCapacity() == 2\n" +
              "b2.size() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b2.getNumber() == " + b2.getNumber() + "\n" + 
              "b2.getCapacity() == " +  b2.getCapacity() + "\n" + 
              "b2.size() == " +  b2.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow7(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    d1.grow(); // grow second space in Block2
    d1.grow(); // create Block3 and grow one space in Block3
    Block b3 = d1.getBlock(3);

    if (!(
          d1.numberOfDataBlocks == 4 &&
          d1.sizeOfArrayOfBlocks == 4 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 4 && 
          d1.indexOfLastNonEmptyDataBlock == 3 &&
          d1.indexOfLastDataBlock == 3 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b3.getNumber() == 3 &&
          b3.getCapacity() == 2 &&
          b3.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 4\n" +
              "d1.sizeOfArrayOfBlocks == 4\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 4\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 3\n" +
              "d1.indexOfLastDataBlock == 3\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b3.getNumber() == 3\n" +
              "b3.getCapacity() == 2\n" +
              "b3.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b3.getNumber() == " + b3.getNumber() + "\n" + 
              "b3.getCapacity() == " +  b3.getCapacity() + "\n" + 
              "b3.size() == " +  b3.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow8(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    d1.grow(); // grow second space in Block2
    d1.grow(); // create Block3 and grow one space in Block3
    d1.grow(); // grow second space in Block3
    Block b3 = d1.getBlock(3);

    if (!(
          d1.numberOfDataBlocks == 4 &&
          d1.sizeOfArrayOfBlocks == 4 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 4 && 
          d1.indexOfLastNonEmptyDataBlock == 3 &&
          d1.indexOfLastDataBlock == 3 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b3.getNumber() == 3 &&
          b3.getCapacity() == 2 &&
          b3.size() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 4\n" +
              "d1.sizeOfArrayOfBlocks == 4\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 4\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 3\n" +
              "d1.indexOfLastDataBlock == 3\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b3.getNumber() == 3\n" +
              "b3.getCapacity() == 2\n" +
              "b3.size() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b3.getNumber() == " + b3.getNumber() + "\n" + 
              "b3.getCapacity() == " +  b3.getCapacity() + "\n" + 
              "b3.size() == " +  b3.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow9(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    d1.grow(); // grow second space in Block2
    d1.grow(); // create Block3 and grow one space in Block3
    d1.grow(); // grow second space in Block3
    d1.grow(); // create Block4 and grow one space in Block4
    Block b4 = d1.getBlock(4);

    if (!(
          d1.numberOfDataBlocks == 5 &&
          d1.sizeOfArrayOfBlocks == 5 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 5 && 
          d1.indexOfLastNonEmptyDataBlock == 4 &&
          d1.indexOfLastDataBlock == 4 &&
          d1.numberOfSuperBlocks == 4 &&  
          d1.lastSuperBlock.getNumber() == 3 &&
          b4.getNumber() == 4 &&
          b4.getCapacity() == 4 &&
          b4.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 5\n" +
              "d1.sizeOfArrayOfBlocks == 5\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 5\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 4\n" +
              "d1.indexOfLastDataBlock == 4\n" +
              "d1.numberOfSuperBlocks == 4\n" +
              "d1.lastSuperBlock.getNumber == 3\n" +
              "b4.getNumber() == 4\n" +
              "b4.getCapacity() == 4\n" +
              "b4.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b4.getNumber() == " + b4.getNumber() + "\n" + 
              "b4.getCapacity() == " +  b4.getCapacity() + "\n" + 
              "b4.size() == " +  b4.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow10(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    d1.grow(); // grow second space in Block2
    d1.grow(); // create Block3 and grow one space in Block3
    d1.grow(); // grow second space in Block3
    d1.grow(); // create Block4 and grow one space in Block4
    d1.grow(); // grow second space in Block4
    d1.grow(); // grow third space in Block4
    d1.grow(); // grow fourth space in Block4
    Block b4 = d1.getBlock(4);

    if (!(
          d1.numberOfDataBlocks == 5 &&
          d1.sizeOfArrayOfBlocks == 5 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 5 && 
          d1.indexOfLastNonEmptyDataBlock == 4 &&
          d1.indexOfLastDataBlock == 4 &&
          d1.numberOfSuperBlocks == 4 &&  
          d1.lastSuperBlock.getNumber() == 3 &&
          b4.getNumber() == 4 &&
          b4.getCapacity() == 4 &&
          b4.size() == 4 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 5\n" +
              "d1.sizeOfArrayOfBlocks == 5\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 5\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 4\n" +
              "d1.indexOfLastDataBlock == 4\n" +
              "d1.numberOfSuperBlocks == 4\n" +
              "d1.lastSuperBlock.getNumber == 3\n" +
              "b4.getNumber() == 4\n" +
              "b4.getCapacity() == 4\n" +
              "b4.size() == 4\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b4.getNumber() == " + b4.getNumber() + "\n" + 
              "b4.getCapacity() == " +  b4.getCapacity() + "\n" + 
              "b4.size() == " +  b4.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow11(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    d1.grow(); // grow second space in Block2
    d1.grow(); // create Block3 and grow one space in Block3
    d1.grow(); // grow second space in Block3
    d1.grow(); // create Block4 and grow one space in Block4
    d1.grow(); // grow second space in Block4
    d1.grow(); // grow third space in Block4
    d1.grow(); // grow fourth space in Block4
    d1.grow(); // create Block5 and grow one space in Block5
    Block b5 = d1.getBlock(5);

    if (!(
          d1.numberOfDataBlocks == 6 &&
          d1.sizeOfArrayOfBlocks == 6 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 6 && 
          d1.indexOfLastNonEmptyDataBlock == 5 &&
          d1.indexOfLastDataBlock == 5 &&
          d1.numberOfSuperBlocks == 4 &&  
          d1.lastSuperBlock.getNumber() == 3 &&
          b5.getNumber() == 5 &&
          b5.getCapacity() == 4 &&
          b5.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 6\n" +
              "d1.sizeOfArrayOfBlocks == 6\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 6\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 5\n" +
              "d1.indexOfLastDataBlock == 5\n" +
              "d1.numberOfSuperBlocks == 4\n" +
              "d1.lastSuperBlock.getNumber == 3\n" +
              "b5.getNumber() == 5\n" +
              "b5.getCapacity() == 4\n" +
              "b5.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b5.getNumber() == " + b5.getNumber() + "\n" + 
              "b5.getCapacity() == " +  b5.getCapacity() + "\n" + 
              "b5.size() == " +  b5.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_grow12(){
    // test grow() on empty DynamicArray
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.grow(); // grow one space in Block0
    d1.grow(); // create Block1 and grow one space in Block1
    d1.grow(); // grow second space in Block1
    d1.grow(); // create Block2 and grow one space in Block2
    d1.grow(); // grow second space in Block2
    d1.grow(); // create Block3 and grow one space in Block3
    d1.grow(); // grow second space in Block3
    d1.grow(); // create Block4 and grow one space in Block4
    d1.grow(); // grow second space in Block4
    d1.grow(); // grow third space in Block4
    d1.grow(); // grow fourth space in Block4
    d1.grow(); // create Block5 and grow one space in Block5
    d1.grow(); // grow second space in Block5
    d1.grow(); // grow third space in Block5
    d1.grow(); // grow fourth space in Block5
    d1.grow(); // create Block6 and grow one space in Block6
    Block b6 = d1.getBlock(6);

    if (!(
          d1.numberOfDataBlocks == 7 &&
          d1.sizeOfArrayOfBlocks == 7 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 7 && 
          d1.indexOfLastNonEmptyDataBlock == 6 &&
          d1.indexOfLastDataBlock == 6 &&
          d1.numberOfSuperBlocks == 5 &&  
          d1.lastSuperBlock.getNumber() == 4 &&
          b6.getNumber() == 6 &&
          b6.getCapacity() == 4 &&
          b6.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 4 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 7\n" +
              "d1.sizeOfArrayOfBlocks == 7\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 7\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 6\n" +
              "d1.indexOfLastDataBlock == 6\n" +
              "d1.numberOfSuperBlocks == 5\n" +
              "d1.lastSuperBlock.getNumber == 4\n" +
              "b6.getNumber() == 6\n" +
              "b6.getCapacity() == 4\n" +
              "b6.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 4\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 4\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b6.getNumber() == " + b6.getNumber() + "\n" + 
              "b6.getCapacity() == " +  b6.getCapacity() + "\n" + 
              "b6.size() == " +  b6.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  //DynamicArray locate

  @Test(timeout=1000) public void DynamicArray_locate1(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(0);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==0\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate2(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(1); 
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=1){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==1\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate3(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(2);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=1){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==1\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=1){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=1\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate4(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(3); 
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=2){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==2\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate5(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(4); 
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=2){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==2\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=1){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=1\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate6(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(5);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=3){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==3\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate7(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(6);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=3){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==3\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=1){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=1\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate8(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(7); 
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=4){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==4\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate9(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(8);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=4){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==0\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=1){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=1\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate10(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(9);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=4){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==4\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=2){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=2\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate11(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(10);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=4){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==4\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=3){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=3\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate12(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(11);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=5){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==5\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: +elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate13(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(15);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=6){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==6\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=0){
      failFmt("DynamicArray locate:\n"+
              "Expect: elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate410(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    Location L = d1.locate(410);
 
    int blockIndex = L.getBlockIndex();

    if(blockIndex!=39){
      failFmt("DynamicArray locate:\n"+
              "Expect: +blockIndex==6\n" +
              "Actual: blockIndex=="+blockIndex);
    }

    int elementIndex = L.getElementIndex();
 
    if(elementIndex!=11){
      failFmt("DynamicArray locate:\n"+
              "Expect: elementIndex=0\n" +
              "Actual: elementIndex=="+elementIndex);
    }
  }

  @Test(timeout=1000) public void DynamicArray_locate913(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    int index = 913;
    int expectBlockIndex   = 58;
    int expectElementIndex = 18;

    Location L = d1.locate(index);
    int blockIndex = L.getBlockIndex();
    int elementIndex = L.getElementIndex();

    String msg =
      String.format("locate(%d):\n",index)+
      String.format("Expect Block #: %d\nExpect Elem  #: %d\n",expectBlockIndex,expectElementIndex) +
      String.format("Actual Block #: %d\nActual Elem  #: %d\n",blockIndex,elementIndex);
    assertTrue(msg, blockIndex==expectBlockIndex && elementIndex==expectElementIndex);
  }

  @Test(timeout=1000) public void DynamicArray_locate5123(){
    // test locate
    DynamicArray<String> d1 = new DynamicArray<String>();
    int index = 5123;
    int expectBlockIndex   = 142;
    int expectElementIndex = 4;

    Location L = d1.locate(index);
    int blockIndex = L.getBlockIndex();
    int elementIndex = L.getElementIndex();

    String msg =
      String.format("locate(%d):\n",index)+
      String.format("Expect Block #: %d\nExpect Elem  #: %d\n",expectBlockIndex,expectElementIndex) +
      String.format("Actual Block #: %d\nActual Elem  #: %d\n",blockIndex,elementIndex);
    assertTrue(msg, blockIndex==expectBlockIndex && elementIndex==expectElementIndex);
  }


  // DynamicArray remove

  @Test(timeout=1000) public void DynamicArray_remove1(){
    // test remove
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A"); 
    d1.remove();
    Block b0 = d1.getBlock(0);


    if (!(
          d1.numberOfDataBlocks == 1 &&
          d1.sizeOfArrayOfBlocks == 1 &&
          d1.numberOfEmptyDataBlocks == 1 &&
          d1.numberOfNonEmptyDataBlocks == 0 && 
          d1.indexOfLastNonEmptyDataBlock == -1 &&
          d1.indexOfLastDataBlock == 0 &&
          d1.numberOfSuperBlocks == 1 &&  
          d1.lastSuperBlock.getNumber() == 0 &&
          b0.getNumber() == 0 &&
          b0.getCapacity() == 1 &&
          b0.size() == 0 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 1 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray remove:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 1\n" +
              "d1.sizeOfArrayOfBlocks == 1\n" + 
              "d1.numberOfEmptyDataBlocks == 1\n" + 
              "d1.numberOfNonEmptyDataBlocks == 0\n" + 
              "d1.indexOfLastNonEmptyDataBlock == -1\n" +
              "d1.indexOfLastDataBlock == 0\n" +
              "d1.numberOfSuperBlocks == 1\n" +
              "d1.lastSuperBlock.getNumber == 0\n" +
              "b0.getNumber() == 0\n" +
              "b0.getCapacity() == 1\n" +
              "b0.size() == 0\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 1\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b0.getNumber() == " + b0.getNumber() + "\n" + 
              "b0.getCapacity() == " +  b0.getCapacity() + "\n" + 
              "b0.size() == " +  b0.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove2(){
    // test remove 
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.remove();
    Block b2 = d1.getBlock(2);

    if (!(
          d1.numberOfDataBlocks == 3 &&
          d1.sizeOfArrayOfBlocks == 3 &&
          d1.numberOfEmptyDataBlocks == 0 &&
          d1.numberOfNonEmptyDataBlocks == 3 && 
          d1.indexOfLastNonEmptyDataBlock == 2 &&
          d1.indexOfLastDataBlock == 2 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b2.getNumber() == 2 &&
          b2.getCapacity() == 2 &&
          b2.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray remove:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 3\n" +
              "d1.sizeOfArrayOfBlocks == 3\n" + 
              "d1.numberOfEmptyDataBlocks == 0\n" + 
              "d1.numberOfNonEmptyDataBlocks == 3\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 2\n" +
              "d1.indexOfLastDataBlock == 2\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b2.getNumber() == 2\n" +
              "b2.getCapacity() == 2\n" +
              "b2.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b2.getNumber() == " + b2.getNumber() + "\n" + 
              "b2.getCapacity() == " +  b2.getCapacity() + "\n" + 
              "b2.size() == " +  b2.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove3(){
    // test remove
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.remove();
    d1.remove();
    Block b2 = d1.getBlock(2);

    if (!(
          d1.numberOfDataBlocks == 3 &&
          d1.sizeOfArrayOfBlocks == 3 &&
          d1.numberOfEmptyDataBlocks == 1 &&
          d1.numberOfNonEmptyDataBlocks == 2 && 
          d1.indexOfLastNonEmptyDataBlock == 1 &&
          d1.indexOfLastDataBlock == 2 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b2.getNumber() == 2 &&
          b2.getCapacity() == 2 &&
          b2.size() == 0 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray remove:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 3\n" +
              "d1.sizeOfArrayOfBlocks == 3\n" + 
              "d1.numberOfEmptyDataBlocks == 1\n" + 
              "d1.numberOfNonEmptyDataBlocks == 2\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 1\n" +
              "d1.indexOfLastDataBlock == 2\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b2.getNumber() == 2\n" +
              "b2.getCapacity() == 2\n" +
              "b2.size() == 0\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b2.getNumber() == " + b2.getNumber() + "\n" + 
              "b2.getCapacity() == " +  b2.getCapacity() + "\n" + 
              "b2.size() == " +  b2.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove4(){
    // test remove
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.remove();
    d1.remove();
    d1.remove();
    Block b2 = d1.getBlock(2);
    Block b1 = d1.getBlock(1);

    if (!(
          d1.numberOfDataBlocks == 3 &&
          d1.sizeOfArrayOfBlocks == 3 &&
          d1.numberOfEmptyDataBlocks == 1 &&
          d1.numberOfNonEmptyDataBlocks == 2 && 
          d1.indexOfLastNonEmptyDataBlock == 1 &&
          d1.indexOfLastDataBlock == 2 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b2.getNumber() == 2 &&
          b2.getCapacity() == 2 &&
          b2.size() == 0 &&
          b1.getNumber() == 1 &&
          b1.getCapacity() == 2 &&
          b1.size() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray remove:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 3\n" +
              "d1.sizeOfArrayOfBlocks == 3\n" + 
              "d1.numberOfEmptyDataBlocks == 1\n" + 
              "d1.numberOfNonEmptyDataBlocks == 2\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 1\n" +
              "d1.indexOfLastDataBlock == 2\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b2.getNumber() == 2\n" +
              "b2.getCapacity() == 2\n" +
              "b2.size() == 0\n" +
              "b1.getNumber() == 1\n" +
              "b1.getCapacity() == 2\n" +
              "b1.size() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b2.getNumber() == " + b2.getNumber() + "\n" + 
              "b2.getCapacity() == " +  b2.getCapacity() + "\n" + 
              "b2.size() == " +  b2.size() + "\n" + 
              "b1.getNumber() == " + b1.getNumber() + "\n" + 
              "b1.getCapacity() == " +  b1.getCapacity() + "\n" + 
              "b1.size() == " +  b1.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove5(){
    // test remove
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.remove();
    d1.remove();
    d1.remove();
    d1.remove();
    Block b1 = d1.getBlock(1);

    if (!(
          d1.numberOfDataBlocks == 2 &&
          d1.sizeOfArrayOfBlocks == 2 &&
          d1.numberOfEmptyDataBlocks == 1 &&
          d1.numberOfNonEmptyDataBlocks == 1 && 
          d1.indexOfLastNonEmptyDataBlock == 0 &&
          d1.indexOfLastDataBlock == 1 &&
          d1.numberOfSuperBlocks == 2 &&  
          d1.lastSuperBlock.getNumber() == 1 &&
          b1.getNumber() == 1 &&
          b1.getCapacity() == 2 &&
          b1.size() == 0 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1
          )) {
      failFmt("DynamicArray remove:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 2\n" +
              "d1.sizeOfArrayOfBlocks == 2\n" + 
              "d1.numberOfEmptyDataBlocks == 1\n" + 
              "d1.numberOfNonEmptyDataBlocks == 1\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 0\n" +
              "d1.indexOfLastDataBlock == 1\n" +
              "d1.numberOfSuperBlocks == 2\n" +
              "d1.lastSuperBlock.getNumber == 1\n" +
              "b1.getNumber() == 1\n" +
              "b1.getCapacity() == 2\n" +
              "b1.size() == 0\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 1\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 1\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b1.getNumber() == " + b1.getNumber() + "\n" + 
              "b1.getCapacity() == " +  b1.getCapacity() + "\n" + 
              "b1.size() == " +  b1.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove6(){
    // test remove
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.remove();
    Block b3 = d1.getBlock(3);
    Block b2 = d1.getBlock(2);

    if (!(
          d1.numberOfDataBlocks == 4 &&
          d1.sizeOfArrayOfBlocks == 4 &&
          d1.numberOfEmptyDataBlocks == 1 &&
          d1.numberOfNonEmptyDataBlocks == 3 && 
          d1.indexOfLastNonEmptyDataBlock == 2 &&
          d1.indexOfLastDataBlock == 3 &&
          d1.numberOfSuperBlocks == 3 &&  
          d1.lastSuperBlock.getNumber() == 2 &&
          b3.getNumber() == 3 &&
          b3.getCapacity() == 2 &&
          b3.size() == 0 &&
          b2.getNumber() == 2 &&
          b2.getCapacity() == 2 &&
          b2.size() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2 &&
          d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2 &&
          d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2
          )) {
      failFmt("DynamicArray grow:\n"+
              "Expect:\n" +
              "d1.numberOfDataBlocks == 4\n" +
              "d1.sizeOfArrayOfBlocks == 4\n" + 
              "d1.numberOfEmptyDataBlocks == 1\n" + 
              "d1.numberOfNonEmptyDataBlocks == 3\n" + 
              "d1.indexOfLastNonEmptyDataBlock == 2\n" +
              "d1.indexOfLastDataBlock == 3\n" +
              "d1.numberOfSuperBlocks == 3\n" +
              "d1.lastSuperBlock.getNumber == 2\n" +
              "b3.getNumber() == 3\n" +
              "b3.getCapacity() == 2\n" +
              "b3.size() == 0\n" +
              "b2.getNumber() == 2\n" +
              "b2.getCapacity() == 2\n" +
              "b2.size() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == 2\n" +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == 2\n" +
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == 2\n" +
              "Actual:\n" +
              "d1.numberOfDataBlocks == " + d1.numberOfDataBlocks + "\n" +
              "d1.sizeOfArrayOfBlocks == " + d1.sizeOfArrayOfBlocks + "\n" +
              "d1.numberOfEmptyDataBlocks == " + d1.numberOfEmptyDataBlocks + "\n" +
              "d1.numberOfNonEmptyDataBlocks == " +  d1.numberOfNonEmptyDataBlocks + "\n"  +
              "d1.indexOfLastNonEmptyDataBlock == " +  d1.indexOfLastNonEmptyDataBlock + "\n" + 
              "d1.indexOfLastDataBlock == " +  d1.indexOfLastDataBlock + "\n" + 
              "d1.numberOfSuperBlocks == " +  d1.numberOfSuperBlocks + "\n" + 
              "d1.lastSuperBlock.getNumber == " + d1.lastSuperBlock.getNumber() + "\n" + 
              "b3.getNumber() == " + b3.getNumber() + "\n" + 
              "b3.getCapacity() == " +  b3.getCapacity() + "\n" + 
              "b3.size() == " +  b3.size() + "\n" + 
              "b2.getNumber() == " + b2.getNumber() + "\n" + 
              "b2.getCapacity() == " +  b2.getCapacity() + "\n" + 
              "b2.size() == " +  b2.size() + "\n" + 
              "d1.lastSuperBlock.getMaxNumberOfDataBlocks() == " + d1.lastSuperBlock.getMaxNumberOfDataBlocks()  + "\n"  +
              "d1.lastSuperBlock.getMaxNumberOfElementsPerBlock() == " + d1.lastSuperBlock.getMaxNumberOfElementsPerBlock()  + "\n" + 
              "d1.lastSuperBlock.getCurrentNumberOfDataBlocks() == " +  d1.lastSuperBlock.getCurrentNumberOfDataBlocks()  + "\n" 
              );
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove7(){
    // test remove()
    DynamicArray<String> d1 = new DynamicArray<String>();

    boolean failed = false;
    try {
      d1.remove();
    } catch (IllegalStateException  e) {
      failed = true;
    }

    if(!failed){
      failFmt("DynamicArray remove:\n"+
              "Expect: IllegalStateException" + "\n" +
              "Actual: no IllegalStateException "+"\n" + "");
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove8(){
    // test remove()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.remove();   // remove "A", now empty
    boolean failed = false;
    try {
      d1.remove();
    } catch (IllegalStateException  e) {
      failed = true;
    }

    if(!failed){
      failFmt("DynamicArray remove:\n"+
              "Expect: IllegalStateException" + "\n" +
              "Actual: no IllegalStateException "+"\n" + "");
    }
  }

  // DynamicArray set

  @Test(timeout=1000) public void DynamicArray_set1(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.set(0,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[0]).getElement(0);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set2(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.set(1,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[1]).getElement(0);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set3(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.set(2,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[1]).getElement(1);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==C" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set4(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.set(3,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[2]).getElement(0);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set5(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.set(4,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[2]).getElement(1);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set6(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.set(5,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[3]).getElement(0);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set7(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.set(6,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[3]).getElement(1);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set8(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.set(7,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[4]).getElement(0);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set9(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");
    d1.set(1,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[1]).getElement(0);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set10(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");
    d1.set(5,"Z");

    String X = (String) ((Block)d1.arrayOfBlocks[3]).getElement(0);
    if(!(X.equals("Z"))) {
      failFmt("DynamicArray set:\n"+
              "Expect: X==Z" + "\n" +
              "Actual: X==" + X);
    }
  }

  @Test(timeout=1000) public void DynamicArray_set11(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    boolean failed = false;
    try {
      d1.set(-1,"Z");
    } catch (IllegalArgumentException  e) {
      failed = true;
    }

    if(!failed){
      failFmt("DynamicArray set:\n"+
              "Expect: IllegalArgumentException" + "\n" +
              "Actual: no IllegalArgumentException "+"\n" + "");
    }
  }

  @Test(timeout=1000) public void DynamicArray_set12(){
    // test set()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    boolean failed = false;
    try {
      d1.set(7,"Z");
    } catch (IllegalArgumentException  e) {
      failed = true;
    }

    if(!failed){
      failFmt("DynamicArray set:\n"+
              "Expect: IllegalArgumentException" + "\n" +
              "Actual: no IllegalArgumentException "+"\n" + "");
    }
  }

  //DynamicArray size

  @Test(timeout=1000) public void DynamicArray_size2(){
    // test size()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A"); // grow one space in Block0

    int size = d1.size();
    if(size != 1) {
      failFmt("DynamicArray size:\n"+
              "Expect: size == 1" + "\n" +
              "Actual: size == " + size);
    }
  }



  @Test(timeout=1000) public void DynamicArray_size3(){
    // test size()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    d1.add("D");
    d1.add("E");
    d1.add("F");
    d1.add("G");
    d1.add("H");
    d1.add("I");
    d1.add("J");
    d1.add("K");
    d1.add("L");
    d1.add("M");
    d1.add("N");
    d1.add("O");
    d1.add("P");

    int size = d1.size();
    if(size != 16) {
      failFmt("DynamicArray size:\n"+
              "Expect: size == 16" + "\n" +
              "Actual: size == " + size);
    }
  }

  //DynamicArray toString

  @Test(timeout=1000) public void DynamicArray_toString1(){
    // test toString()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A"); // grow one space in Block0

    String result = d1.toString().replaceAll("\\s+","");
  
    if(!(result.equals("[A]"))){
      failFmt(" DynamicArray toString:\n"+
              "Expect: +d1.toString()==[A]\n" +
              "Actual: d1.toString()==" + d1.toString());
    }
  }

  @Test(timeout=1000) public void DynamicArray_toString2(){
    // test toString()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");

    String result = d1.toString().replaceAll("\\s+","");
  
    if(!(result.equals("[A,B]"))){
      failFmt(" DynamicArray toString:\n"+
              "Expect: +d1.toString()==[A, B]\n" +
              "Actual: d1.toString()==" + d1.toString());
    }
  }

  @Test(timeout=1000) public void DynamicArray_toString3(){
    // test toString()
    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");

    String result = d1.toString().replaceAll("\\s+","");
  
    if(!(result.equals("[A,B,C]"))){
      failFmt(" DynamicArray toString:\n"+
              "Expect: +d1.toString()==[A, B, C]\n" +
              "Actual: d1.toString()==" + d1.toString());
    }
  }

  //DynamicArray toStringForDebugging

  @Test(timeout=1000) public void DynamicArray_toStringForDebugging1(){
    // test toStringForDebugging()

    //Actual output should look like:
    //DynamicArray: A B C 
    //numberOfDataBlocks: 2
    //numberOfEmptyDataBlocks: 0
    //numberOfNonEmptyDataBlocks: 2
    //indexOfLastNonEmptyDataBlock: 1
    //indexOfLastDataBlock: 1
    //numberOfSuperBlocks: 2
    //lastSuperBlock: SB1
    //Block0: A 
    //- capacity=1 size=1
    //Block1: B C 
    //- capacity=2 size=2
    //SB1:
    //- maxNumberOfDataBlocks: 1
    //- maxNumberOfElementsPerBlock: 2
    //- currentNumberOfDataBlocks: 1
    //

    // We remove all white space from the output before we check it.

    DynamicArray<String> d1 = new DynamicArray<String>();
    d1.add("A");
    d1.add("B");
    d1.add("C");
    String result = d1.toStringForDebugging().replaceAll("\\s+",""); // remove all white space
    String expect =
      "DynamicArray:ABCnumberOfDataBlocks:2numberOfEmptyDataBlocks:0numberOfNonEmptyDataBlocks:2indexOfLastNonEmptyDataBlock:1"
      + "indexOfLastDataBlock:1numberOfSuperBlocks:2lastSuperBlock:SB1Block0:A-capacity=1size=1Block1:BC"
      + "-capacity=2size=2SB1:-maxNumberOfDataBlocks:1-maxNumberOfElementsPerBlock:2-currentNumberOfDataBlocks:1";
  
    if(!(result.equals(expect))){
      failFmt(" DynamicArray toStringForDebugging_Tests:\n"+
              "Expect: d1.toStringForDebugging_Tests()==" + expect + "\n" +
              "Actual: d1.toStringForDebugging_Tests()==" + result);
    }
  }

  // DynamicQueue

  @Test(timeout=1000) public void DynamicQueue_construct1() {
    // Check construct DynamicQueue
    DynamicQueue<String> q1 = new DynamicQueue<String>();

    if(q1.getFront().size()!=0){
      failFmt("DynamicQueue constructor:\n"+
              "Expect: q1.getFront().size()==0\n" +
              "Actual: q1.getFront().size()==" + q1.getFront().size());
    }

    if(q1.getRear().size()!=0){
      failFmt("DynamicQueue constructor:\n"+
              "Expect: +q1.getRear().size()==0\n" +
              "Actual: q1.getRear().size()==" + q1.getFront().size());
    }
  
  }

  @Test(timeout=1000) public void DynamicQueue_enqueue1() {
    // Check enqueue
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");

    if(q1.getRear().size()!=1){
      failFmt("DynamicQueue enqueue:\n"+
              "Expect: q1.getRear().size()==1\n" +
              "Actual: q1.getRear().size()==" + q1.getRear().size());
    }

    if(!(q1.getRear().get(0).equals("A"))){
      failFmt("DynamicQueue enqueue:\n"+
              "Expect: q1.getRear().get(0) equals A\n" +
              "Actual: q1.getRear().get(0) equals " + q1.getRear().get(0));
    }
  
  }

  @Test(timeout=1000) public void DynamicQueue_enqueue2() {
    // Check enqueue
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");

    if(q1.getRear().size()!=2){
      failFmt("DynamicQueue enqueue:\n"+
              "Expect: q1.getRear().size()==2\n" +
              "Actual: q1.getRear().size()==" + q1.getRear().size());
    }
  }

  @Test(timeout=1000) public void DynamicQueue_dequeue1() {
    // Check dequeue
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    String A = q1.dequeue();

    if(!(A.equals("A"))){
      failFmt("DynamicQueue dequeue:\n"+
              "Expect: A equals A\n" +
              "Actual: A is " + A);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_dequeue2() {
    // Check dequeue
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");
    String A = q1.dequeue();

    if(q1.getRear().size()!=0){
      failFmt("DynamicQueue dequeue:\n"+
              "Expect: q1.getRear().size()==0\n" +
              "Actual: q1.getRear().size()==" + q1.getRear().size());
    }

    if(q1.getFront().size()!=1){
      failFmt("DynamicQueue dequeue:\n"+
              "Expect: q1.getFront().size()==1\n" +
              "Actual: q1.getFront().size()==" + q1.getFront().size());
    }

    if(!(A.equals("A"))){
      failFmt("DynamicQueue dequeue:\n"+
              "Expect: A equals A\n" +
              "Actual: A is " + A);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_dequeue3() {
    // Check dequeue
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");
    String A = q1.dequeue();
    q1.enqueue("C");

    if(q1.getRear().size()!=1){
      failFmt("DynamicQueue dequeue:\n"+
              "Expect: q1.getRear().size()==1\n" +
              "Actual: q1.getRear().size()==" + q1.getRear().size());
    }

    if(q1.getFront().size()!=1){
      failFmt("DynamicQueue dequeue:\n"+
              "Expect: q1.getFront().size()==1\n" +
              "Actual: q1.getFront().size()==" + q1.getFront().size());
    }

    if(!(A.equals("A"))){
      failFmt("DynamicQueue dequeue:\n"+
              "Expect: A equals A\n" +
              "Actual: A is " + A);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_dequeue4() {
    // Check dequeue
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    String A = q1.dequeue();

    boolean failed = false;
    try {
      q1.dequeue();
    } catch (NoSuchElementException  e) {
      failed = true;
    }

    if(!failed){
      failFmt("DynamicQueue deque:\n"+
              "Expect: NoSuchElementException" + "\n" +
              "Actual: no NoSuchElementException "+"\n" + "");
    }
  }

  @Test(timeout=1000) public void DynamicQueue_size1() {
    // Check size
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    int size = q1.size();

    if(size != 0){
      failFmt("DynamicQueue size:\n"+
              "Expect: size==0\n" +
              "Actual: size==" + size);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_size2() {
    // Check size
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    int size = q1.size();

    if(size != 1){
      failFmt("DynamicQueue size:\n"+
              "Expect: size==1\n" +
              "Actual: size==" + size);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_size3() {
    // Check size
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");
    String A = q1.dequeue();
    q1.enqueue("C");
    int size = q1.size();

    if(size != 2){
      failFmt("DynamicQueue size:\n"+
              "Expect: size==2\n" +
              "Actual: size==" + size);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_isEmpty1() {
    // Check isEmpty
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    boolean isEmpty = q1.isEmpty();

    if(isEmpty != true){
      failFmt("DynamicQueue isEmpty:\n"+
              "Expect: isEmpty==true\n" +
              "Actual: isEmpty==" + isEmpty);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_isEmpty2() {
    // Check isEmpty
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    boolean isEmpty = q1.isEmpty();

    if(isEmpty != false){
      failFmt("DynamicQueue isEmpty:\n"+
              "Expect: isEmpty==false\n" +
              "Actual: isEmpty==" + isEmpty);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_toString1() {
    // Check toString
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");
    String AB = q1.toString().replaceAll("\\s+","");

    if(!(AB.equals("[A,B]"))){
      failFmt("DynamicQueue toString:\n"+
              "Expect: toString equals [A, B]\n" +
              "Actual: toString equals " + AB);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_toString2() {
    // Check toString
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");
    String A = q1.dequeue();
    q1.enqueue("C");
    String BC = q1.toString().replaceAll("\\s+","");

    if(!(BC.equals("[B,C]"))){
      failFmt("DynamicQueue toString:\n"+
              "Expect: toString equals [B, C]\n" +
              "Actual: toString equals " + BC);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_toStringForDebugging1() {
    // Check toStringForDebugging
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");
    String debug = q1.toStringForDebugging().replaceAll("\\s+","");

    if(!(debug.equals("front.toString:[]rear.toString:[A,B]"))){
      failFmt("DynamicQueue toStringForDebugging:\n"+
              "Expect: toString equals front.toString:[]rear.toString:[A,B]\n" +
              "Actual: toString equals " + debug);
    }
  }

  @Test(timeout=1000) public void DynamicQueue_toStringForDebugging2() {
    // Check toStringForDebugging
    DynamicQueue<String> q1 = new DynamicQueue<String>();
    q1.enqueue("A");
    q1.enqueue("B");
    String A = q1.dequeue();
    q1.enqueue("C");
    String debug = q1.toStringForDebugging().replaceAll("\\s+","");

    if(!(debug.equals("front.toString:[B]rear.toString:[C]"))){
      failFmt("DynamicQueue toStringForDebugging:\n"+
              "Expect: toString equals front.toString:[B]rear.toString:[C]\n" +
              "Actual: toString equals " + debug);
    }
  }

  @Test(timeout=1000) public void DynamicArray_add_stress1() {
    DynamicArray<Integer> da = new DynamicArray<Integer>();
    for(int i=0; i<100; i++){
      da.add(i);
    }
    String expect, actual, msg;
    assertEquals("Size mismatch",100,da.size());
    assertEquals("#Blocks mismatch",19,da.numberOfDataBlocks);
    expect =
      "- maxNumberOfDataBlocks: 8\n"+
      "- maxNumberOfElementsPerBlock: 8\n"+
      "- currentNumberOfDataBlocks: 5\n"+
      "";
    actual = da.lastSuperBlock.toStringForDebugging();
    msg = String.format("lastSuperBlock wrong:\nEXPECT:\n%sACTUAL:\n%s\n",expect,actual);
    assertEquals(msg,expect,actual);
  }

  @Test(timeout=1000) public void DynamicArray_get_set_stress1() {
    String msg;
    DynamicArray<Integer> da = new DynamicArray<Integer>();
    for(int i=0; i<100; i++){
      da.add(i);
    }
    for(int i=0; i<100; i++){
      da.set(i, i*10);
    }
    for(int i=0; i<100; i++){
      int actualI = da.get(i);
      msg = String.format("mismatch: get(%d)",i);
      assertEquals(msg,i*10,actualI);
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove_stress1() {
    DynamicArray<Integer> da = new DynamicArray<Integer>();
    for(int i=0; i<100; i++){
      da.add(i);
    }
    for(int i=0; i<20; i++){
      da.remove();
    }
    String expect, actual, msg;
    assertEquals("Size mismatch",80,da.size());
    assertEquals("#Blocks mismatch",18,da.numberOfDataBlocks);
    expect =
      "- maxNumberOfDataBlocks: 8\n"+
      "- maxNumberOfElementsPerBlock: 8\n"+
      "- currentNumberOfDataBlocks: 4\n"+
      "";
    actual = da.lastSuperBlock.toStringForDebugging();
    msg = String.format("lastSuperBlock wrong:\nEXPECT:\n%sACTUAL:\n%s\n",expect,actual);
    assertEquals(msg,expect,actual);
  }

  @Test(timeout=1000) public void DynamicArray_add_stress2() {
    DynamicArray<Integer> da = new DynamicArray<Integer>();
    for(int i=0; i<2500; i++){
      da.add(i);
    }
    String expect, actual, msg;
    assertEquals("Size mismatch",2500,da.size());
    assertEquals("#Blocks mismatch",102,da.numberOfDataBlocks);
    expect =
      "- maxNumberOfDataBlocks: 32\n"+
      "- maxNumberOfElementsPerBlock: 64\n"+
      "- currentNumberOfDataBlocks: 8\n"+
      "";
    actual = da.lastSuperBlock.toStringForDebugging();
    msg = String.format("lastSuperBlock wrong:\nEXPECT:\n%sACTUAL:\n%s\n",expect,actual);
    assertEquals(msg,expect,actual);
  }

  @Test(timeout=1000) public void DynamicArray_get_set_stress2() {
    String msg;
    DynamicArray<Integer> da = new DynamicArray<Integer>();
    for(int i=0; i<2500; i++){
      da.add(i);
    }
    for(int i=0; i<2500; i++){
      da.set(i, i*10);
    }
    for(int i=0; i<2500; i++){
      int actualI = da.get(i);
      msg = String.format("mismatch: get(%d)",i);
      assertEquals(msg,i*10,actualI);
    }
  }

  @Test(timeout=1000) public void DynamicArray_remove_stress2() {
    DynamicArray<Integer> da = new DynamicArray<Integer>();
    for(int i=0; i<2500; i++){
      da.add(i);
    }
    for(int i=0; i<100; i++){
      da.remove();
    }
    String expect, actual, msg;
    assertEquals("Size mismatch",2400,da.size());
    assertEquals("#Blocks mismatch",101,da.numberOfDataBlocks);
    expect =
      "- maxNumberOfDataBlocks: 32\n"+
      "- maxNumberOfElementsPerBlock: 64\n"+
      "- currentNumberOfDataBlocks: 7\n"+
      "";
    actual = da.lastSuperBlock.toStringForDebugging();
    msg = String.format("lastSuperBlock wrong:\nEXPECT:\n%sACTUAL:\n%s\n",expect,actual);
    assertEquals(msg,expect,actual);
    for(int i=0; i<500; i++){
      da.remove();
    }
    assertEquals("Size mismatch",1900,da.size());
    assertEquals("#Blocks mismatch",91,da.numberOfDataBlocks);
    expect =
      "- maxNumberOfDataBlocks: 32\n"+
      "- maxNumberOfElementsPerBlock: 32\n"+
      "- currentNumberOfDataBlocks: 29\n"+
      "";
    actual = da.lastSuperBlock.toStringForDebugging();
    msg = String.format("lastSuperBlock wrong:\nEXPECT:\n%sACTUAL:\n%s\n",expect,actual);
    assertEquals(msg,expect,actual);
  }
}
