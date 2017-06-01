import java.util.*;
// Basic model for a spreadsheet.
public class Spreadsheet{
  protected Map<String,Cell> Cellmap; //  map that contains IDs to cell objects
  protected DAG dag; // dag that contains upstream/downstreams
  // Construct a new empty spreadsheet
  public Spreadsheet(){
    this.Cellmap = new HashMap<String,Cell>();
    this.dag = new DAG();
  }
  
  // Return a string representation of the spreadsheet. This should
  // show a table of the cells ids, values, and contents along with
  // the upstream and downstream links between cells. Ensure that
  // StringBuilder and iterators over various maps are used to
  // efficiently construct the string. The expected format is as
  // follows.
  //
  //     ID |  Value | Contents
  // -------+--------+---------------
  //     A1 |    5.0 | '5'
  //     D1 |    4.0 | '=4'
  //     C1 |  178.0 | '=22*A1 + 17*D1'
  //     B1 |     hi | 'hi'
  // 
  // Cell Dependencies
  // Upstream Links:
  //   C1 : [A1, D1]
  // Downstream Links:
  //   A1 : [C1]
  //   D1 : [C1]
  //
  public String toString(){
    StringBuilder strSheet = new StringBuilder(); // string to be returned
    strSheet.append("    ID |  Value | Contents\n");
    strSheet.append("-------+--------+---------------\n");
    for(String s : Cellmap.keySet()){ // loop through map
      try{
        Cellmap.get(s).updateValue(Cellmap); // update value of cells, if they have not been updated already
      } 
      catch(NullPointerException e){ 
      }
      strSheet.append(String.format("%"+6+"s",s)); // add spacing for ID
      strSheet.append(" |");
      strSheet.append(String.format("%"+7+"s",getCellDisplayString(s))); // add spacing for value
      strSheet.append(" | ");
      strSheet.append("'");
      strSheet.append(String.format("%"+1+"s",getCellContents(s))); // add spacing for contents
      strSheet.append("'");
      strSheet.append("\n");
    }
    strSheet.append("\n");
    strSheet.append("Cell Dependencies\n");
    strSheet.append(dag.toString()); // add the dag
    return strSheet.toString(); // return the spreadsheet in pretty format
  }
  
  // Produce a saveable string of the spreadsheet. A reasonable format
  // is each cell id and its contents on a line.  You may choose
  // whatever format you like so long as the spreadsheet can be
  // completely recreated using the fromSaveString(s) method.
  public String toSaveString(){ // create a string in a very simple format containing of two words per line
    StringBuilder b = new StringBuilder(); // string to be returned
    for(String s : Cellmap.keySet()){ // loop through Cellmap
      b.append(s); // append ID
      b.append(" "); // add space
      b.append(getCellContents(s)); // append its contents
      b.append("\n"); // new line, repeat
    }
    return b.toString();
  }
  
  // Load a spreadsheet from the given save string. Typical
  // implementations will creat an empty spreadsheet and repeatedly
  // read input from the provided string setting cells based on the
  // contents read.
  public static Spreadsheet fromSaveString(String s){
    Spreadsheet newsheet = new Spreadsheet(); // spreadsheet to be returned
    Scanner sc = new Scanner(s); // employs use of scanner to parse string
    while(sc.hasNextLine()){ // while there is a next line available
      if(sc.hasNext()){ // grab next string in current line
        newsheet.setCell(sc.next(), sc.nextLine()); // first parameter is ID, second is everything after ID
      }
      else{ // go to next line
        sc.nextLine();
      }
    }
    return newsheet;
  }
  
  // Check if a cell ID is well formatted.  It must match the regular
  // expression
  // 
  //  ^[A-Z]+[1-9][0-9]*$
  // 
  // to be well formatted. If the ID is not formatted correctly, throw
  // a RuntimeException.  The str.matches(..) method is useful for
  // this method.
  public static void verifyIDFormat(String id){
    if((id.matches("^[A-Z]+[1-9][0-9]*$")) == true){ // use of regular expression to check proper formatting of cell ID
      return; 
    }
    else{
      throw new RuntimeException("ID is not formatted correctly"); 
    }
  }
  
  // Retrive a string which should be displayed for the value of the
  // cell with the given ID. Return "" if the specified cell is empty.
  public String getCellDisplayString(String id){
    return Cellmap.get(id).displayString(); 
  }
  
  // Retrive a string which is the actual contents of the cell with
  // the given ID. Return "" if the specified cell is empty.
  public String getCellContents(String id){
    return Cellmap.get(id).contents(); 
  }
  
  // Delete the contents of the cell with the given ID. Update all
  // downstream cells of the change. If specified cell is empty, do
  // nothing.
  public void deleteCell(String id){
    Cellmap.remove(id); // remove cell from Cellmap
    notifyDownstreamOfChange(id); // notify cells to update value, may cause cells to be in ERROR state
    dag.remove(id); // remove id from dag
  }
  
  // Set the given cell with the given contents. If contents is "" or
  // null, delete the cell indicated.
  public void setCell(String id, String contents){
    if(contents == null || contents.equals("")){ // if contents is null or ""
      Cellmap.remove(id); // remove it from Cellmap and dag
      dag.remove(id);
      return;
    }
    verifyIDFormat(id); // check if cell to be added is formatted correctly
    Cellmap.remove(id); // remove ID from Cellmap
    Cell c = Cell.make(contents); // make a new cell with contents
    Set<String> ups = c.getUpstreamIDs(); // gather the cells upstreamIDs
    dag.add(id,ups); // add the ID to the dag
    Cellmap.put(id,c); // Cellmap the ID to the newly created cell
    notifyDownstreamOfChange(id); // notify other cells that they may have to update their value
  }
  
  // Notify all downstream cells of a change in the given cell.
  // Recursively notify subsequent cells. Guaranteed to terminate so
  // long as there are no cycles in cell dependencies.
  public void notifyDownstreamOfChange(String id){
    Set<String> downstreams = dag.getDownstreamLinks(id); // get downstream of the ID passed in
    for(String s : downstreams){ // loop through
      try{
        Cellmap.get(s).updateValue(Cellmap); // update value of cell
        notifyDownstreamOfChange(s); // recursively call itself, gather other downstreams and updating their value
      } 
      catch(NullPointerException e){
      }
    }
  }
}