import java.util.*;
// *************CLARIFICATION REGARDING USE OF NO SUBCLASSES*****************
// rather then relying on 3 subclasses to determine the type of a cell
// it is much easier to use a private constructor becuase it isn't rather difficult to determine the kind
// simple string parsing is needed, and using multiple fields to hold different information based off of the kind of the cell
// individual comments in private constructor, perhaps, clarifies this much better


// Spreadsheet Cells can be one of three different kinds:
// - Formulas always start with the = sign.  If the 0th character in
//   contents is a '=', use the method
//     FNode root = FNode.parseFormulaString(contents);
//   to create a formula tree of FNodes for later use.
// - Numbers can be parsed as doubles using the
//   Double.parseDouble(contents) method.  
// - Strings are anything else aside from Formulas and Numbers and
//   store only the contents given.
//
// Cells are largely immutable: once the contents of a cell are set,
// they do not change except to reflect movement of upstream dependent
// cells in a formula.  The value of a formula cell may change after
// if values change in its dependent cells. To make changes in a
// spreadsheet, one will typically create a new Cell with different
// contents using the method
//   newCell = Cell.make(contents);
//
// This class may contain nested static subclasses to implement the
// differnt kinds of cells.
public class Cell {
  protected  String strcontents; // contents of a string cell
  protected  Double val; // holds the value of a evaluated formula
  protected  String contents; // trimmed contents of a cell
  protected  String state; // determines where state in ERROR or in NOTERROR state
  protected String untouchedContents; // non trimmed contents of a cell
  protected FNode treeRoot; // tree version of formula passed
  
  // Factory method to create cells with the given contents linked to
  // the given spreadsheet.  The method is static so that one invokes it with:
  // 
  //   Cell c = Cell.make("=A21*2");
  //
  // The return value may be a subclass of Cell which is not possible
  // with constructors.  Call trim() on the contents string to remove
  // whitespace at the beginning and end.  If the contents is null or
  // empty, return null.  If contents is not valid, a RuntimeException
  // is generated.
  //
  // If the cell is a formula, it is not possible to evaluate its
  // formula during Cell.make() as other references to other cells
  // cannot be resolved.  The formula can only be reliably evaluated
  // after a call to cell.updateValue(cellMap) is made later.  Until
  // that time the cell should be in the ERROR state with
  // cell.isError() == true and displayString() == "ERROR" and
  // cell.numberValue() == null.
  private Cell(String contents){
    String g = contents.trim(); // contents are trimmed because it is easer in evaluating the contents
    this.untouchedContents = g; // untrimmed contents
    this.contents = g; // trimmed cntents
    try{
      Double x = Double.parseDouble(contents); // if we can parse the string into a double
      this.val = x; // then it is not a formula and a number cell
      this.state = "NOTERROR"; // number cells are never in ERROR
    }
    catch(NumberFormatException e){ // if parsing fails then
      if(g.charAt(0) == '='){ // check if 0th index has an '=', if so indicates it is a FORMULA
        this.state = "ERROR"; // formula has not been evaluated, so it has to be in ERROR
        this.treeRoot = FNode.parseFormulaString(contents);
      }
      else{ // if checks fail, then the cell is clearly a string
        this.state = "NOTERROR"; // strings are never in ERROR
        this.strcontents = contents;
      }
    }    
  }
  
  public static Cell make(String contents){ // make calls a private constructor which does all the work
    if(contents == null || contents.equals("")){ 
      return null;
    }
    Cell c = new Cell(contents);
    Map<String,Cell> emptycellMap = new HashMap<String,Cell>(); // empty cell map is created to evaluate a cell 
    c.updateValue(emptycellMap); // this is to check if its value can be determined rather quickly with a cell such as =13
    return c;
  }
  
  
  // Return the kind of the cell which is one of "string", "number",
  // or "formula".
  public String kind(){
    Double throwval;
    try{
      throwval = Double.parseDouble(contents); // try to parse the contents of a cell
      return "number"; // if it can be parsed then it is a number
    }
    catch(Exception e){ // otherwise check if 0th index has a '=', if so its a formula
      if(contents.charAt(0) == '='){
        return "formula"; 
      }
      else{ // otherwise its a string
        return "string"; 
      }
    }    
  }   
  
  
  // Returns whether the cell is currently in an error state. Cells
  // with kind() "string" and "number" are never in error.  Formula
  // cells are in error and show ERROR if their formula involves cells
  // which are blank or have kind "string" and therefore cannot be
  // used to calculate the value of the cell.
  
  public boolean isError(){ // no actual determination of whether a cell should be in error is determined in this method
    if(state.equals("ERROR")){ // all ERROR setting occurs in the private constructor and updateValue
      return true; // strings and number cells are never in ERROR whereas a formula may or may not be in error
    }             // a formula's state cannot be determined until a call to updateValue is made by the user
    return false;
  }
  
  // Produce a string to display the contents of the cell.  For kind()
  // "string" and "number", this method returns the original contents
  // of the cell.  For formula cells which are in error, return the
  // string "ERROR".  Formula cells which are not in error return a
  // string of their numeric value with 1 decimal digit of accuracy
  // which is easiest to produce with the String.format() method.
  //
  // Target Complexity: O(1)
  // Avoid repeated formula evaluation by traversing the formula tree
  // only in updateFormulaValue()
  
  public String displayString(){
    if(this.kind().equals("number")){ // if cells kind is number, simply return the parsed string with 1 decimal point
      String result = String.format("%.1f", Double.parseDouble(contents));
      return result; 
    }
    else if(this.kind().equals("string")){ // if kind is string, return the contents
      return contents; 
    }
    if(isError() == false){ // if checks above fail, then its a formula, however we must check if its in an error state
      double x = (double)Math.round(val * 10d) / 10d; // if not, then compute calculation to get a certain degree of precision 
      String temp = ""+x; // convert val to string and return it
      return temp; 
    }
    return "ERROR"; // all checks failed, so cell is in error state. may need call to updateValue
  }
  
  // Return the numeric value of this cell.  If the cell is kind
  // "number", this is the double value of its contents.  For kind
  // "formula", it is the evaluated value of the formula.  For kind
  // "string" return null.
  //
  // Target Complexity: O(1)
  // Avoid repeated formula evaluation by traversing the formula tree
  // only in updateFormulaValue()
  
  
  public Double numberValue(){
    if(this.kind().equals("number")){ // number cell has the double value returned of parsed content
      return val; 
    }
    else if(this.kind().equals("string")){ // strings have no number value so return null
      return null; 
    }

    if(isError() == true){ // formulas in error have no number value YET, so return null for now
      return null; 
    }
    return val; // formula isnt in error, so its value can be safely returned
  }
  
  // Return the raw contents of the cell. For kind() "number" and
  // "string", this is the original contents entered into the cell.
  // For kind() "formula", this is the text of the formula.
  //
  // Target Complexity: O(1)
  
  public String contents(){ 
    return untouchedContents; // the raw contents of a cell returned
  }
  
  // Update the value of the cell value. If the cell is not a formula
  // (string and number), do nothing.  Formulas should re-evaluate the
  // stored formula tree to determine a numeric value.  This method
  // may be called when the cell is initially created to give it a
  // numeric value in which case an empty map should be used.
  // Whenever an upstream cell changes value, the housing spreadsheet
  // will call this method to recompute the numeric value to reflect
  // the change.  This method should not raise any exceptions if there
  // are problems evaluating the formula due to other unusable cells.
  // It should set the state of this cell to be in error so that a
  // call to isError() returns true.  If the cell formula is
  // successfully evaluated, isError() should return false.
  //
  // Target Complexity: 
  //   O(1) for "number" and "string" cells
  //   O(T) for "formula" nodes where T is the number of nodes in the
  //        formula tree
  
  // all work for this method is relegated to evalFormulatree
  public void updateValue(Map<String,Cell> cellMap){
    if(this.kind().equals("number") || this.kind().equals("string")){ // check immediately if cell is a number or string
      return;  // no work needs to be done for cells of this kind
    }
    
    try{
      val = evalFormulaTree(treeRoot,cellMap); // call eval formula tree with the tree of the formula and the cellMap
      contents = "="+Double.toString(val); // if a successful return is made, tack it onto contents
      state = "NOTERROR"; // cell is no longer in ERROR state
    }
    catch (EvalFormulaException e){ // evaluating formula caused problems perhaps because of missing cells
      state="ERROR"; // so cell put into ERROR state
    }
    catch (Exception e){ // evaluating formula caused problems perhaps because of missing cells
      state="ERROR"; // so cell put into ERROR state
    }
  }
  
  // A simple class to reflect problems evaluating a formula tree.
  public static class EvalFormulaException extends RuntimeException{
    
    public EvalFormulaException(String msg){
      super(msg); 
    }
    
  }
  
  // Recursively evaluate the formula tree rooted at the given
  // node. Return the computed value.  Use the given map to retrieve
  // the number value of cells which appear in the formula.  If any
  // cell ID in the formula is unusable (blank, error, string), this
  // method raises an EvalFormulaException.  
  // 
  // This method is public and static to allow for testing independent
  // of any individual cell but should be used in the
  // updateFormulaValue() method to allow individual cells to compute
  // their formula values.
  //
  // Inspect the FNode and TokenType classes to gain insight into what
  // information is available in FNodes to inspect during the
  // post-order traversal for evaluation.
  // 
  // Target Complexity: O(T) 
  //   T: the number of nodes in the formula tree
  
  // majority of work relegated to a small helper method postOrder 
  public static Double evalFormulaTree(FNode node, Map<String,Cell> cellMap){
    if(node.type == TokenType.CellID){ // if node passed in is a CELL
      Cell temp = cellMap.get(node.data); // get it from the cell map
      if(temp == null){ // if nothing there, throw an exception
        throw new  EvalFormulaException("Not possible");
      }
      if(node.type == TokenType.Number){ // if the cell is a number cell
        return Double.parseDouble(node.data);  // we can simply just parse its data and return it saving work
      }
      
    }
    return postOrder(node, cellMap); // call the helper method that does all the work
    
    
  }
  public static double postOrder(FNode treeRoot, Map<String,Cell> cellMap){
    double valLeft = 0; // contains val of left tree 
    double valRight = 0; // contains val of right tree 
    double valLeftRight = 0; // contains val of  s + g 
    if(treeRoot != null){ // traverse tree until dead end found
      valLeft = postOrder(treeRoot.left, cellMap); // recursively go down the left
      valRight = postOrder(treeRoot.right, cellMap); // recursively go down the right
      if(treeRoot.type == TokenType.Number){ // if Number type found,
        return Double.parseDouble(treeRoot.data); // simply return parsed string it holds
      }
      if(treeRoot.type == TokenType.CellID){ // if FNode is a cell
        
        Cell temp = cellMap.get(treeRoot.data); // grab its data
        
        if(temp == null){ // if it has no data, we cannot evaluate it further so throw an exception
          throw new  EvalFormulaException("Not possible");
        }
        if(temp.kind().equals("string")){ // if its data is a string, we cannot evaluate a formula as it makes no sense
          throw new  EvalFormulaException("Not possible");
        }
        if(temp.kind().equals("formula")){ // if the data is another formula....
          temp.updateValue(cellMap); // recursively call updateValue until the cells value is evaluated
        }
        
        return temp.numberValue(); // return number value of the cell
      }
      if(treeRoot.data.equals("+")){ // after going left and right, if parent is a +
        return(valLeft+valRight);  // simply add the contents of left and right
      }
      else if(treeRoot.data.equals("-")){ // after going left and right, if parent is a -
        return(valLeft-valRight); // simply subtract the contents of left and right
      }
      else if(treeRoot.data.equals("*")){ // after going left and right, if parent is a *
        return(valLeft*valRight); // simply multiply the contents of left and right
      }
      else if(treeRoot.data.equals("/")){ // after going left and right, if parent is a /
        return(valLeft/valRight); // simply divide the contents of left and right
      }
      else if(treeRoot.data.equals("negate")){ // after going left and right, if parent is negate
        return(-valLeft+valRight); // simply negate left trees contents and add with the right
      }
      return(valLeft+Double.parseDouble(treeRoot.data)); 
    }
    
    return(valLeftRight);
  }
  
  // Return a set of upstream cells from this cell. Cells of kind
  // "string" and "number" return an empty set.  Formula cells are
  // dependent on the contents of any cell whose ID appears in the
  // formula and returns all such ids in a set.  For formula cells,
  // this method should call a recursive helper method to traverse the
  // formula tree and accumulate a set of ids in the formula tree.
  // 
  // Target Complexity: O(1)
  // Avoid repeated formula evaluation by traversing the formula tree
  // only in updateFormulaValue()
  
  // all work relegated to a helper method
  public Set<String> getUpstreamIDs(){
    HashSet<String> upIDs = new HashSet<String>();
    if(this.kind().equals("number") || this.kind().equals("string")){ // number and string DO NOT HAVE upstreamIDs
      return upIDs; // empty set returned
    }
    return recursiveIDs(treeRoot,upIDs); // call helper method
  }
  public Set<String> recursiveIDs(FNode t, HashSet<String> upIDs){
    if(t != null){ // traverse tree
      recursiveIDs(t.left, upIDs); // go left
      recursiveIDs(t.right, upIDs); // go right
      if(t.type == TokenType.CellID){ // if type is a CELL
        upIDs.add(t.data); // add it to set
      }
    }
    return(upIDs);
  }
}