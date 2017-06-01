import java.util.*;
// Sparse Matrix class: 2D grid of linked nodes; elements which are
// the fillElem are do not have nodes present. Retains a dense array
// of row and column pointers to speed some operations and ease
// implementation.
//
// Target Space Complexity: O(E + R + C)
//   E: number of non-fill elements in the matrix
//   R: number of rows in the matrix
//   C: number of cols in the matrix
public class SparseMatrix<T>{
  private int row; // number of rows
  private int col; // number of cols
  private T fillElem; // the fillElem for the matrix
  protected ArrayList<Object> rowHeadArray; // contains row Head objects
  protected ArrayList<Object> colHeadArray; // contains col Head objects
  protected int elementCount; // number of elements added
  // Suggested internal class to represent Row and Column
  // Headers. Tracks indices of the col or row, pointer to the start
  // of row/column. This is a separat class from Node to enable
  // efficient changing of row numbers: all Nodes point to a row Head
  // and col Head to determine their own row/column. You may modify
  // this class as you see fit.
  private static class Head<T> {
    public int index;  // Index of row/col
    public Node<T> nodes; // Dummy node at start of row/column; add after first
    public Head(int i){
      this.index = i;
      this.nodes = new Node<T>(); // Headed lists with dummy node first node
    }
  }
  
  // Suggested class to store data. Contains single links to next
  // nodes to the right and down. Also contains links to the row and
  // col Heads which store its row# and col#.  You may modify this
  // class as you see fit.
  protected static class Node<Y>{
    public Head<Y> rowHead; // My row head
    public Head<Y> colHead; // My col head
    public Node<Y> right;       // Next Node to the right
    public Node<Y> down;        // Next node down
    public Y data; // Data associated with the node
  }
  
  // Constructor to create a SparseMatrix with the given number of
  // rows and columns with the given fillElem. The matri starts out
  // empty: all elements are presumed to be the fillElem.
  //
  // Target Complexity: O(R+C)
  //   R: number of rows in the matrix
  //   C: number of cols in the matrix
  public SparseMatrix(int r, int c, T fillElem){
    this.elementCount = 0;
    this.row = r;
    this.col = c;
    this.fillElem = fillElem;
    this.rowHeadArray = new ArrayList<Object>(row);
    this.colHeadArray = new ArrayList<Object>(col);
    for(int i = 0; i < row; i++){ // store row heads in array
      rowHeadArray.add(new Head<T>(i)); 
    }
    for(int i = 0; i < col; i++){ // store col heads in array
      colHeadArray.add(new Head<T>(i)); 
    }
    for(int i = 0; i < row; i++){ // assign row head right to dummy node
      getHeadRow(i).nodes.right = new Node<T>(); 
    }
    for(int i = 0; i < col; i++){ // assign col head down to dummy node
      getHeadCol(i).nodes.down = new Node<T>(); 
    }
  }
  
  
  
  // Constructor to create a 0 by 0 SparseMatrix with the given
  // fillElem
  public SparseMatrix(T fillElem){
    this(0, 0, fillElem);
  }
  @SuppressWarnings("unchecked")
  public Head<T> getHeadRow(int i){ // returns the Head of the row
    return (Head<T>)rowHeadArray.get(i); 
  }
  @SuppressWarnings("unchecked")
  public Head<T> getHeadCol(int i){ // returns the Head of the col
    return (Head<T>)colHeadArray.get(i); 
  }
  @SuppressWarnings("unchecked")
  public Node<T> getDummy(Head<T> h){ // grab the dummy node
    return h.nodes; 
  }
  @SuppressWarnings("unchecked")
  public Node<T> getRightNode(Node<T> n){ // grabs the current nodes right
    return n.right; 
  }
  @SuppressWarnings("unchecked")
  public Node<T> getDownNode(Node<T> n){ // grab the current nodes down
    return n.down; 
  }  
  
  
  public int elementCount(){ // returns current number of elements added
    return this.elementCount; 
  }
  
  // Return the number of rows in the Matrix which is the last indexed
  // row+1.
  // 
  // Target Complexity: O(1)
  public int rows(){
    return this.row;
  }
  
  // Return the number of cols in the Matrix which is the last indexed
  // col+1.
  // 
  // Target Complexity: O(1)
  public int cols(){
    return this.col; 
  }
  
  // Return the fill element with which this matrix was initialized
  // 
  // Target Complexity: O(1)
  public T getFillElem(){
    return this.fillElem; 
  }
  
  // Add an empty row on to the bottom of the matrix.
  // 
  // Target Complexity: O(1) amortized
  @SuppressWarnings("unchecked")
  public void addRow(){
    rowHeadArray.add(new Head<T>(row)); // add new Head to end of list
    row++; // # of total rows incremented
    getHeadRow(row-1).nodes.right = new Node(); // new dummy node set 
  }
  
  // Add an empty col on right side of the matrix.
  // 
  // Target Complexity: O(1) amortized
  @SuppressWarnings("unchecked")
  public void addCol(){
    colHeadArray.add(new Head<T>(col)); // add new Head to end of list
    col++;
    getHeadCol(col-1).nodes.down = new Node(); // new dummy node set
  }
  
  // Insert an empty row at position i. Later rows are "shifted down"
  // to a higher index.  Importantly, Nodes should not need
  // adjustments; only the Head indices should need alteration.
  //
  // Target Complexity: O(R)
  //   R: number of rows in the matrix
  @SuppressWarnings("unchecked")
  public void insertRow(int i){
    row++;
    rowHeadArray.add(i, new Head<T>(i)); // insert new Head at given index
    getHeadRow(i).nodes.right = new Node(); // new dummy set for new Head
    for(int x = i + 1; x < colHeadArray.size(); x++){ // everything after the newly set head has its index increased by one
      getHeadRow(x).index++; 
    }
    
    
  }
  
  // Insert an empty col at position i. Later cols are "shifted right"
  // to a higher index.  Importantly, Nodes should not need
  // adjustments; only the Head indices should need alteration.
  //
  // Target Complexity: O(C)
  //   C: number of cols in the matrix
  @SuppressWarnings("unchecked")
  public void insertCol(int i){
    col++;
    colHeadArray.add(i, new Head<T>(i)); // insert new Head in col array
    getHeadCol(i).nodes.down = new Node(); // set new dummy for Head
    for(int x = i + 1; x < colHeadArray.size(); x++){ // everything after new Head has its index increased by one
      getHeadCol(x).index++; 
    }
  }
  // Retrieve the element at position (i,j) in the matrix. If the
  // position is out of bounds, throw an IndexOutOfBoundsException
  // with an appropriate message. Otherwise, access the target row or
  // column and walk the list to locate the element. If no node for
  // the element exists, return the fillElem for this
  // matrix. Otherwise return the data found in the target node.
  //
  // Target Complexity: O(E)
  //   E: number of non-fill elements in the matrix
  @SuppressWarnings("unchecked")
  public T get(int i, int j){
    if(i > (rows()-1)){ // error checking code 
      throw new IndexOutOfBoundsException("Out of bounds!");
    }
    if(j > (cols()-1)){
      throw new IndexOutOfBoundsException("Out of bounds!"); 
    }
    Node temp = getHeadRow(i).nodes.right.right; // grab the dummys right
    for(int x = 0; x <= j; x++){
      if(temp == null){ // if dummy has no right, then no elements set beforehand
        return getFillElem(); 
      }
      else{
        if(temp.colHead.equals(getHeadCol(j))){ // if the head node we look at equals the head within the array
          return (T)temp.data; // then a match is found and we simply return its data
        }
        else{
          temp = getRightNode(temp); // otherwise keep searching through the next node
          continue;
        }
      }
    }
    return getFillElem(); // nothing found implies fillElement returned
  }
  
  // Set element at position (i,j) to be x.
  // 
  // If x is the fillElem, throw an IllegalArgumentException with an
  // appropriate message.
  // 
  // If position (i,j) already has an element present, alter that
  // element.
  // 
  // Otherwise, allocate a new node and link it into any existing
  // nodes by traversing the appropriate row and column lists.
  //
  // This method automatically expands the size of the matrix via
  // repeated calls to addRow() and addCol() to ensure that position
  // (i,j) is available. It does not throw any
  // IndexOutOfBoundsExceptions.
  //
  // Target complexity: 
  // O(E) when (i,j) is in bounds
  // O(E+R+C) when (i,j) is out of bounds requiring expansion
  //   E: number of non-fill elements
  //   R: number of rows in the matrix
  //   C: number of cols in the matrix
  @SuppressWarnings("unchecked")
  public void set(int i, int j, T x){
    if(x.equals(getFillElem())){ // cant set to fillElem
      throw new IllegalArgumentException("Cant fill with the fill element!"); 
    }
    while(j >= cols()){ // expand matrix to accomodate the J entered
      addCol();
    }
    while(i >= rows()){ // expand matrix to accomodate the I entered
      addRow(); 
    }
    // code below searches for pre-existing nodes and simply changes it data
    Node temp = getHeadRow(i).nodes.right.right; // grab dummys right at index desired
    for(int g = 0; g <= j; g++){
      if(temp == null){ // break out if nothing within the row
        break; 
      }
      else{
        if(temp.colHead.equals(getHeadCol(j))){ // check if nodes pre-set head matches the column Head
          // System.out.println("Match found!");
          temp.data = x; // if so simply change data and return
          return;
        }
        else{ // otherwise keep looking 
          temp = getRightNode(temp);
          continue;
        }
      }
    }
    temp = getHeadRow(i).nodes.right.right; // reset to dummys right
    Node prev = null; // temps previous node if available
    while (temp != null){ // get the right most node in row if conditions not met
      if(temp.colHead.index > j){ // if current node has an index higher than J
        Node b =  getHeadRow(i).nodes.right.right; // grab the current node
        getHeadRow(i).nodes.right.right = new Node(); // assign dummy's right to a new node
        getHeadRow(i).nodes.right.right.data = x; // change its data
        getHeadRow(i).nodes.right.right.right = b; // and now assign the new nodes right to the previous right
        getHeadRow(i).nodes.right.right.colHead = getHeadCol(j); // link col head
        getHeadRow(i).nodes.right.right.rowHead = getHeadRow(i); // link row head
        elementCount++; // # of elements increased
        break;
      }
      else if (temp.colHead.index > j && prev != null){ // if  the current node is greater than J AND something is already linked to it then
        Node b = new Node(); // create new Node
        prev.right = b; // assign previous right to the new node
        prev.right.right = temp; // new node has its right set to current node
        prev.right.data = x; // assign data
        prev.right.colHead = getHeadCol(j); // assign head
        prev.right.rowHead = getHeadRow(i); // assign head
        elementCount++;
        break;
      }
      else if (temp.colHead.index < j && temp.right == null){ // if current node is less than J and the current node has no right set
        temp.right = new Node(); // assign currents right to a new node
        temp.right.colHead = getHeadCol(j); // assign head
        temp.right.rowHead = getHeadRow(i); // assign head
        temp.right.data = x; // assign data
        elementCount++;
        break;
      }
      // if we are inserting between two nodes
      else if(temp.colHead.index < j && temp.right != null && temp.right.colHead.index > j){
        Node b =  temp; // store temp
        Node c = temp.right; // gets temps right
        Node n = new Node(); 
        b.right = new Node(); // assign current nodes right to a new node
        b.right.data = x; // change new nodes data
        b.right.right = c; // assign new nodes right to C
        b.right.colHead = getHeadCol(j); // assign head
        b.right.rowHead = getHeadRow(i); // assign head
        elementCount++;
        break;
      }
      else{ // otherwise keep searching right
        temp = getRightNode(temp); 
      }
    }
    
    if(temp == null){ // if no node was set in code above, meaning dummys right was NULL
      Node n = new Node(); // create new doe
      n.data = x; // change its data
      n.rowHead = getHeadRow(i); // assign head
      n.colHead = getHeadCol(j); // assign head
      getHeadRow(i).nodes.right.right = n; // assign dummys right to this new node
      elementCount++;
    }
    // code below is to link nodes downwards after it has been set rightwards above
    temp = getHeadRow(0).nodes.right.right; // start at row 0
    int q = 0;
    Node temp2 = getHeadCol(j).nodes.down.down; // get dummys down at J
    for(int g = 0; g <= rows(); g++){
      if(temp == null){ // if nothing in current row, go to the new row
        q++;
      }
      else{
        if(temp.colHead.equals(getHeadCol(j))){ // if a match found among the ColHeads
          if(temp2 != null){ // if temp already has its down set
            temp2.down = temp; // simply assign temp2s down to temp
            temp2 = temp2.down;
            q++; // once match in row found, goes to next row
          }
          else{ // if temp2 doesnt have anything set
            getHeadCol(j).nodes.down.down = temp; // assign a new Down
            temp2 = getDownNode(getHeadCol(j).nodes.down);
            q++;            
          }
        }
        else{ // otherwise keeps searching right
          temp = getRightNode(temp); 
          continue;
        }
      }
      if(q>i){ // so no out of bounds occurs
        break; 
      }
      temp = getHeadRow(q).nodes.right.right; // new node in next row grabbed
    } 
  }
  // Set the element at position (i,j) to be the fill element.
  // Internally this should remove any node at that position by
  // unlinking it from row and column lists.  If no data exists at
  // (i,j), no changes are made to the matrix.  If (i,j) is out of
  // bounds, throw an IndexOutOfBoundsException with an appropriate
  // message.
  //
  // Target Complexity: O(E)
  //   E: number of non-fill elements in the matrix
  @SuppressWarnings("unchecked")
  public void setToFill(int i, int j){
    if(i > (rows()-1)){ // error checking
      throw new IndexOutOfBoundsException("Out of bounds!"); 
    }
    if(j > (cols()-1)){
      throw new IndexOutOfBoundsException("Out of bounds!"); 
    }
    Node temp = getHeadRow(i).nodes.right.right; // get dummys right
    boolean flag = false;
    for(int g = 0; g <= j; g++){
      if(temp == null){ // nothing to remove in row so just return
        return;
      }
      else{
        if(temp.colHead.equals(getHeadCol(j))){ // if match found, set flag to true and break out of loop
          flag = true;
          break;
        }
        else{ // otherwise keep searching in row
          temp = getRightNode(temp);
          continue;
        }
      }
    }
    if(flag == false){ // if flag is false, then element we want to remove hasnt been set beforehand
      return;
    }
    temp = getHeadRow(i).nodes.right.right; // get dummys right
    Node prev = null;
    while (temp != null){ // keep searching right until match found
      if(prev == null && temp.right == null && temp.colHead.index == j){ // if current node does not have any other links 
        getHeadRow(i).nodes.right.right = null; // simply set the dummys right to null
        elementCount--;
        return;
      }
      else if(prev == null && temp.right != null && temp.colHead.index == j){ // if current nodes has its right set and no previous node
        getHeadRow(i).nodes.right.right = temp.right; // assign dummys right to temps right
        elementCount--;
        return;
      }
      else if(prev != null && temp.right != null && temp.colHead.index == j){ // if removing a node between two Nodes
        prev.right = temp.right;  // assign previous right to temps right
        elementCount--;
        return;
      }
      else if(prev != null && temp.right == null && temp.colHead.index == j){ // if node has no right but it has a link
        prev.right = null;  //  simply assign  prevs right to null
        elementCount--;
        return;
      }
      else{ // otherwise keep searching through row
        prev = temp;
        temp = getRightNode(temp); 
      }
    }
    temp = getHeadCol(j).nodes.down.down; // grab dummys down
    prev = null;
    while(temp != null){ // code unlinks nodes from the colHead
      if(temp.rowHead.index == i && prev != null){
        Node n = temp.down;
        prev.down = n;
        break;
      }
      else{
        prev = temp;
        temp = getDownNode(temp);
      }
    }
  }
  
  // Create a display version of the sparse matrix. Each element
  // (including fills) is shown in a grid of elements. Each element
  // will be in a field of width 5 characters with a space after
  // it. Using String.format("%5s ",el) is useful to create the
  // string.  Each row is on its own line.
  //
  // Example:
  // SparseMatrix<Double> x = new SparseMatrix<Double>(5,4, 0.0); 
  // x.set(1,1, 1.0);
  // x.set(2,1, 2.0);
  // x.set(0,3, 3.0);
  // System.out.println(x);
  //   0.0   0.0   0.0   3.0 
  //   0.0   1.0   0.0   0.0 
  //   0.0   2.0   0.0   0.0 
  //   0.0   0.0   0.0   0.0 
  //   0.0   0.0   0.0   0.0 
  //
  // Target Complexity: O(R*C)
  //   R: number of rows in the matrix
  //   C: number of cols in the matrix
  //   E: number of non-fill elements in the matrix
  // Note: repeated calls to get(i,j) will not adhere to this
  // complexity
  @SuppressWarnings("unchecked")
  public String toString(){
    StringBuilder b = new StringBuilder();
    if(elementCount() == 0){ // if matrix has NOTHING SET
      for(int i = 0; i < rows(); i++){ // loop through and set everything to fillElem with 5 spaces
        for(int j = 0; j< cols(); j++){
          b.append(String.format("%5s ",getFillElem()));
        }
        b.append("\n");
      }
    }
    else{ // if there is something set
      for(int i = 0; i < rows(); i++){
        Node temp = getHeadRow(i).nodes.right.right; // get dummys right at index
        for(int j = 0; j < cols(); j++){
          if(temp == null){ // if nothing within the row, append fillElements
            b.append(String.format("%5s ",getFillElem()));
          }
          else{ // otherwise if colHeads match
            if(temp.colHead.equals(getHeadCol(j))){
              b.append(String.format("%5s ",temp.data)); // append the nodes data
              temp = getRightNode(temp); // go right by one
            }
            
            else{ // otherwise append fillElem
              b.append(String.format("%5s ",getFillElem()));
            }
          }
        }
        b.append("\n");
      }
    }
    return b.toString();
  }
  
  // Required but may simply return "".  This method will be called
  // and the string it produces will be reported when tests fail to
  // aid in viewing the internal state of the SparseMatrix for
  // debugging.
  public String debugString(){
    return toString(); 
  }
  
  // Produce a List of all elements as triplets of (i,j,data). The
  // List may be any kind (ArrayList/LinkedList) and in any order.
  //
  // Target Complexity: O(R + E)
  //   R: number of rows in the matrix
  //   E: number of non-fill elements in the matrix
  // Note: repeated calls to get(i,j) will not adhere to this
  // complexity
  @SuppressWarnings("unchecked")
  public List<Triple<Integer,Integer,T>> allElements(){
    ArrayList<Triple<Integer,Integer,T>> l = new ArrayList<Triple<Integer,Integer,T>>(); // arraylist to hold triples
    int x = 0;
    Node n = getHeadRow(x).nodes.right.right; // get dummy right at row 0
    for(int i = 0; i < rows();){
      if(n == null){ // if nothing within the row
        i++; // increment check
        if(i != rows()){ // get a new node in next row
          n = getHeadRow(i).nodes.right.right;
        }
      }
      else{ // else node exists and simply add it to the arraylist as a Triple
        l.add(new Triple(n.rowHead.index, n.colHead.index, n.data)); 
        //    System.out.println("Element node found at " + n.rowHead.index + " col " + n.colHead.index + "data " + n.data);
        n = getRightNode(n); // get right node
      }
      
    }
    return l; 
  }
  
  // Add two sparse matrices of Doubles together in an elementwise
  // fashion and produce another SparseMatrix as the result.  The fill
  // element of the resulting matrix is the sum of the two fill
  // elements from matrices x and y.  If the sum of any two elements
  // in the matrices equals the resulting fill element, that should
  // not occupy a node in the resulting sparse matrix.
  // 
  // Matrices must have the same size (rows,cols) to be added. Throw
  // and IllegalArgumentException with an appropriate message if not.
  //
  // Target Complexity: O(R + C + Ex + Ey)
  //   R: Number of rows in matrices x and y
  //   C: Number of cols in matrices x and y
  //   Ex, Ey: Number of non-fill elements in matrix x and y
  // Memory constraint: O(1)
  //   The memory constraint does not count the size of x, y, or the
  //   result matrix which is returned.
  
  
  // the code below meets the Target Complexity: O(R + C + Ex + Ey) because it is using a linear scan through
  // both matrices row-wise and add elements in constant time to F. once there are no more nodes in a row, it goes to the next row
  // and repeats. no calls to set are called.
  @SuppressWarnings("unchecked")
  public static SparseMatrix<Double> addFast(SparseMatrix<Double> x, SparseMatrix<Double> y){
    if(x.rows() != y.rows() || x.cols() != y.cols()){ // error checking
      throw new IllegalArgumentException("Cannot add different sized matrices together!"); 
    }
    double newFillElem = x.getFillElem() + y.getFillElem(); // new Fillelem to be used
    int row = x.rows(); // # of rows
    int col = x.cols(); // # of cols
    SparseMatrix<Double> f = new SparseMatrix<Double>(row,col, newFillElem); // new matrix to hold nodes
    //nested loop to iterate through the matrices
    for(int i = 0; i < row; i++){
      Node temp = x.getHeadRow(i).nodes.right.right; // get dummy right at 0 in X MATRIX
      Node temp2 = y.getHeadRow(i).nodes.right.right; // get dummy right at 0 in Y MATRIX
      Node temp3 = f.getHeadRow(i).nodes.right; // dummy in F MATRIX
      for(int g = 0 ; g < col; g++){
        if(temp != null && temp2 != null && temp.colHead.index == temp2.colHead.index){ // if the nodes have same index in X AND Y
          temp3.right = new Node(); // then their data is added up and a new node set in F
          if(((double)temp.data + (double)temp2.data) == newFillElem){ // ensure fillElem not added
            temp = x.getRightNode(temp);
            temp2 = y.getRightNode(temp2);
            continue;
          }
          temp3.right.data = (double)temp.data + (double)temp2.data;
          temp3.right.colHead = f.getHeadCol(temp.colHead.index);
          temp3.right.rowHead = f.getHeadRow(i);
          temp = x.getRightNode(temp); // gets right nodes in matrices
          temp2 = y.getRightNode(temp2);
          temp3 = f.getRightNode(temp3);
          f.elementCount++;
        }
        else if(temp != null && temp2 != null && temp.colHead.index < temp2.colHead.index){ // X NODE < y NODE
          temp3.right = new Node(); // new node with X data set in F so X can catch up to Y
          temp3.right.data = temp.data;
          temp3.right.colHead = f.getHeadCol(temp.colHead.index);
          temp3.right.rowHead = f.getHeadRow(temp.rowHead.index);
          temp = x.getRightNode(temp);
          temp3 = f.getRightNode(temp3);
          f.elementCount++;
        }
        else if(temp != null && temp2 != null && temp2.colHead.index < temp.colHead.index){ // y NODE < x NODE
          temp3.right = new Node(); // new node with Y DATA set in F so Y can catch up to X
          temp3.right.data = temp2.data;
          temp3.right.colHead = f.getHeadCol(temp2.colHead.index);
          temp3.right.rowHead = f.getHeadRow(temp2.rowHead.index);
          temp2 = y.getRightNode(temp2);
          temp3 = f.getRightNode(temp3);
          f.elementCount++;
        }
        else if(temp == null && temp2 != null){ // if X node isnt available, but Y IS
          temp3.right = new Node(); // set new node in F with Y nodes DATA
          temp3.right.data = temp2.data;
          temp3.right.colHead = f.getHeadCol(temp2.colHead.index);
          temp3.right.rowHead = f.getHeadRow(temp2.rowHead.index);
          temp2 = y.getRightNode(temp2);
          temp3 = f.getRightNode(temp3);   
          f.elementCount++;
        }
        else if(temp2 == null && temp != null){ // if Y node isnt available, but X IS
          temp3.right = new Node(); // set new node in F with x nodes DATA
          temp3.right.data = temp.data;
          temp3.right.colHead = f.getHeadCol(temp.colHead.index);
          temp3.right.rowHead = f.getHeadRow(temp.rowHead.index);
          temp = x.getRightNode(temp);
          temp3 = f.getRightNode(temp3);
          f.elementCount++;
        }
        else if(temp == null && temp2 == null){ // if no nodes within row, break so loop starts at next row
          break; 
        }
      }
    }
    
    return f;
  }
  
  public static void main(String args[]){
  }
  
}
