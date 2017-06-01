// HW2 Milestone Tests for SparseMatrix
//
// 30 Tests total which cover:
// - Constructors for 3 and 1 argument
// - Calls to accessors: rows(), cols(), getFillElem(), elementCount()
// - Basic toString()
// - addRow() and addCol() 
// - insertRow(i) and insertCol(i)
// - single set(i,j,x) element in bounds
// - single set(i,j,x) followed by insertRow(i) and insertCol(j)
// - single set(i,j,x) out of bounds
// 
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class HW2MilestoneTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("HW2MilestoneTests");
  } 

  public static String stringDiff(String expect, String actual){
    String e=expect, a=actual;
    int lineStart = 0;
    int lineNum = 1;
    int minLen = e.length() < a.length() ? e.length() : a.length();
    for(int i=0; i<minLen; i++){
      if(e.charAt(i) != a.charAt(i)){
        StringBuilder err = new StringBuilder();
        err.append(String.format("\nDifference at line %d char %d:\n",lineNum,i-lineStart));
        // err.append("        ");
        for(int j=lineStart; j<i; j++){
          err.append(' ');
        }
        err.append('v');
        err.append('\n');
        for(int j=lineStart; j<e.length() && e.charAt(j)!='\n'; j++){
          err.append(e.charAt(j));
        }
        err.append(": EXPECT\n");
        for(int j=lineStart; j<a.length() && a.charAt(j)!='\n'; j++){
          err.append(a.charAt(j));
        }
        err.append(": ACTUAL\n");
        for(int j=lineStart; j<i; j++){
          err.append(' ');
        }
        err.append('^');
        err.append('\n');
        return err.toString();
      }
      else if(e.charAt(i) == '\n'){
        lineStart = i+1;
        lineNum++;
      }
    }
    if(e.length() < a.length()){ // Different lengths
      int eLines=0, aLines=0;
      for(int i=0; i<e.length(); i++){
        eLines += (e.charAt(i)=='\n') ? 1 : 0;
      }
      for(int i=0; i<a.length(); i++){
        aLines += (a.charAt(i)=='\n') ? 1 : 0;
      }
      String eWS = e.replaceAll("\n","\\\\n\n").replaceAll(" ","~");
      String aWS = a.replaceAll("\n","\\\\n\n").replaceAll(" ","~");
      String err =
        String.format("Length difference:\nExpect: %d lines %d chars\nActual: %d lines and %d chars\n",
                      eLines,e.length(),aLines,a.length()) +
        "With visible whitespace: newline as \\n, space as underscore ~\n"+
        format2Columns("EXPECT:\n"+eWS,"ACTUAL:\n"+aWS," | ");
      return err;
    }
    return null;                // No differences
  }

  // Append strings as columns using space as the divider
  public static String format2Columns(String left, String right, String divider){
    return appendColumns(new String[]{left,right},divider);
  }

  // Append string as columns using the provided divider between lines
  public static String appendColumns(String all[], String divider){
    // Fill up allCols[i] will be an array of each line in all[i]
    String allCols[][] = new String[all.length][]; 
    int colWidth;                              // Width of a longest line in this col
    int maxLine = 0;                           // Max # of lines in any col
    String formats[] = new String[all.length]; // Formats for each column
    for(int col=0; col<all.length; col++){
      allCols[col] = all[col].split("\n"); // Fill allCols[i] with lines if all[i]
      maxLine = maxLine < allCols[col].length ? allCols[col].length : maxLine;
      colWidth = 1;                        // Can't have %0s formats so start at 1
      for(int row=0; row<allCols[col].length; row++){ // Find longest line
        int len = allCols[col][row].length(); // Find max line width for allCols[i]
        colWidth = len > colWidth ? len : colWidth;
      }
      String div = col < all.length-1 ? divider : "\n";
      formats[col] = String.format("%%-%ds%s",colWidth,div);
    }
    // Now have width/format for each column and max # of rows. Build
    // up columns next to each other based on this info.

    StringBuilder sb = new StringBuilder();
    for(int line=0; line<maxLine; line++){   // Work through lines
      for(int col=0; col<all.length; col++){ // Append each colum
        String fill = "";                    // Col may not have a row as its too short
        if(line < allCols[col].length){      // 
          fill = allCols[col][line];         // Col does have a row so use it as a filler
        }
        sb.append(String.format(formats[col],fill));
      }
    }
    return sb.toString();
  }

  // Generate a string of info about a Sparse Matrix
  public static<T> String matrixInfo(SparseMatrix<T> s){
    StringBuilder sb = new StringBuilder("SparseMatrix\n");
    sb.append("Rows:         "); sb.append(s.rows());         sb.append('\n');
    sb.append("Cols:         "); sb.append(s.cols());         sb.append('\n');
    sb.append("ElementCount: "); sb.append(s.elementCount()); sb.append('\n');
    sb.append("fillElement:  "); sb.append(s.getFillElem());  sb.append('\n');
    sb.append("toString():\n");
    sb.append(s.toString());
    return sb.toString();
  }

  public static<T> void checkMatrix(String expect, SparseMatrix<T> x){
    String actual = matrixInfo(x);
    String diff = stringDiff(expect,actual);
    if(diff != null){
      String msg =
        String.format("%s\n%s\nACTUAL debugString():%s\n==ACTUAL==\n%s",
                      diff,
                      format2Columns("EXPECT MATRIX\n"+expect,
                                     "ACTUAL MATRIX\n"+actual,
                                     " | "),
                      x.debugString(),
                      matrixInfo(x));
      fail(msg);
    }
  }

  // r by c constructor exists
  @Test(timeout=1000) public void constructor_exists1(){
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,4,0.0);
  }  

  // 0 by 0 constructor exists
  @Test(timeout=1000) public void constructor_exists2(){
    SparseMatrix<String> x = new SparseMatrix<String>("-");
  }  

  // Check that each matrix can have its own rows/cols
  @Test(timeout=1000) public void get_rows_cols0(){
    SparseMatrix<String>       x = new SparseMatrix<String>("-");
    SparseMatrix<Integer>      y = new SparseMatrix<Integer>(4,5,0);
    SparseMatrix<java.io.File> z = new SparseMatrix<java.io.File>(11,2,new java.io.File("."));

    assertEquals(0,x.rows());
    assertEquals(0,x.cols());

    assertEquals(4,y.rows());
    assertEquals(5,y.cols());

    assertEquals(11,z.rows());
    assertEquals(2, z.cols());
  }  


  // Check that elementCount is correct for empties
  @Test(timeout=1000) public void elementCount_empty0(){
    java.io.File fillFile = new java.io.File(".");
    SparseMatrix<String>       x        = new SparseMatrix<String>("-");
    SparseMatrix<Integer>      y        = new SparseMatrix<Integer>(4,5,0);
    SparseMatrix<java.io.File> z        = new SparseMatrix<java.io.File>(11,2,fillFile);

    assertEquals(0,x.elementCount());
    assertEquals(0,y.elementCount());
    assertEquals(0,z.elementCount());
  }  


  // Check that each matrix can have its fillElem
  @Test(timeout=1000) public void get_fillElem0(){
    java.io.File fillFile = new java.io.File(".");
    SparseMatrix<String>       x        = new SparseMatrix<String>("-");
    SparseMatrix<Integer>      y        = new SparseMatrix<Integer>(4,5,0);
    SparseMatrix<java.io.File> z        = new SparseMatrix<java.io.File>(11,2,fillFile);

    assertEquals(new String("-"),x.getFillElem());
    assertEquals(new Integer(0), y.getFillElem());
    assertEquals(fillFile,       z.getFillElem());
  }  

  // Check basic get in an empty matrix
  @Test(timeout=1000) public void get_empty1(){
    SparseMatrix<String> x = new SparseMatrix<String>(2,4,"-");
    assertEquals("-",x.get(0,0));
    assertEquals("-",x.get(0,2));
    assertEquals("-",x.get(1,3)); // lower right element
  }

  @Test(timeout=1000) public void get_empty2(){
    SparseMatrix<Double> x = new SparseMatrix<Double>(9,7,5.5);
    assertEquals(new Double(5.5),x.get(2,2));
    assertEquals(new Double(5.5),x.get(0,6));
    assertEquals(new Double(5.5),x.get(8,0));
    assertEquals(new Double(5.5),x.get(8,6)); // lower right element
  }

  // Test out of bounds gets in empties
  @Test(timeout=1000) public void get_empty_out_of_bounds1(){
    boolean thrown;
    thrown = false;
    SparseMatrix<String> x = new SparseMatrix<String>(2,4,"-");
    try{
      x.get(0,4);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Out of bounds get should throw an exception");
    }
    thrown = false;
    try{
      x.get(3,1);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Out of bounds get should throw an exception");
    }
    try{
      x.get(5,9);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Out of bounds get should throw an exception");
    }
  }
  @Test(timeout=1000) public void get_empty_out_of_bounds2(){
    boolean thrown;
    thrown = false;
    SparseMatrix<Double> x = new SparseMatrix<Double>(9,7,5.5);
    try{
      x.get(0,7);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Out of bounds get should throw an exception");
    }
    thrown = false;
    try{
      x.get(1,8);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Out of bounds get should throw an exception");
    }
    try{
      x.get(9,6);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Out of bounds get should throw an exception");
    }
  }

  // add rows/cols in empty matrix
  @Test(timeout=1000) public void add_row_col_empty1(){
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,3,5.5);
    x.addRow();
    assertEquals(3,x.rows());
    assertEquals(3,x.cols());
    x.addRow();
    assertEquals(4,x.rows());
    assertEquals(3,x.cols());
    x.addCol();
    x.addCol();
    x.addCol();
    x.addRow();
    assertEquals(5,x.rows());
    assertEquals(6,x.cols());
    assertEquals(0,x.elementCount());
  }
  @Test(timeout=1000) public void add_row_col_empty2(){
    SparseMatrix<String> x = new SparseMatrix<String>("-");
    assertEquals(0,x.rows());
    assertEquals(0,x.cols());
    x.addCol();
    x.addCol();
    x.addCol();
    assertEquals(0,x.rows());
    assertEquals(3,x.cols());
    x.addRow();
    assertEquals(1,x.rows());
    assertEquals(3,x.cols());
    assertEquals(0,x.elementCount());
  }
 
  // insert rows/cols in empty matrix
  @Test(timeout=1000) public void insert_row_col_empty1(){
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,3,5.5);
    x.insertRow(0);             // Insert at 0
    assertEquals(3,x.rows());
    assertEquals(3,x.cols());
    x.insertRow(1);             // Insert middle
    assertEquals(4,x.rows());
    assertEquals(3,x.cols());
    x.insertRow(4);             // Insert at end
    assertEquals(5,x.rows());
    assertEquals(3,x.cols());
    x.insertRow(2);             // middle row
    x.insertCol(1);             // middle col
    x.insertCol(4);             // end col
    assertEquals(6,x.rows());
    assertEquals(5,x.cols());
    assertEquals(0,x.elementCount());
  }

  @Test(timeout=1000) public void basic_toString1(){
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,3,5.5);
    String expect =
      "  5.5   5.5   5.5 \n"+
      "  5.5   5.5   5.5 \n"+
      "";
    String actual = x.toString();
    String diff = stringDiff(expect,actual);
    if(diff != null){
      String msg =
        String.format("%s\n%s\nACTUAL debugString():%s",
                      diff,
                      format2Columns("EXPECT MATRIX\n"+expect,
                                     "ACTUAL MATRIX\n"+actual,
                                     " | "),
                      x.debugString());
      fail(msg);
    }
  }
  @Test(timeout=1000) public void basic_toString2(){
    SparseMatrix<String> x = new SparseMatrix<String>(5,2,"12345");
    String expect =
      "12345 12345 \n"+
      "12345 12345 \n"+
      "12345 12345 \n"+
      "12345 12345 \n"+
      "12345 12345 \n"+
      "";
    String actual = x.toString();
    String diff = stringDiff(expect,actual);
    if(diff != null){
      String msg =
        String.format("%s\n%s\nACTUAL debugString():%s",
                      diff,
                      format2Columns("EXPECT MATRIX\n"+expect,
                                     "ACTUAL MATRIX\n"+actual,
                                     " | "),
                      x.debugString());
      fail(msg);
    }
  }
  @Test(timeout=1000) public void basic_toString3(){
    SparseMatrix<String> x = new SparseMatrix<String>(5,7,"abcde");
    String expect =
      "abcde abcde abcde abcde abcde abcde abcde \n"+
      "abcde abcde abcde abcde abcde abcde abcde \n"+
      "abcde abcde abcde abcde abcde abcde abcde \n"+
      "abcde abcde abcde abcde abcde abcde abcde \n"+
      "abcde abcde abcde abcde abcde abcde abcde \n"+
      "";
    String actual = x.toString();
    String diff = stringDiff(expect,actual);
    if(diff != null){
      String msg =
        String.format("%s\n%s\nACTUAL debugString():%s",
                      diff,
                      format2Columns("EXPECT MATRIX\n"+expect,
                                     "ACTUAL MATRIX\n"+actual,
                                     " | "),
                      x.debugString());
      fail(msg);
    }
  }
  ////////////////////////////////////////////////////////////////////////////////
  // Remaining tests presume that toString() returns correctly
  // formatted results
  ////////////////////////////////////////////////////////////////////////////////

  // More robust constructor with all info + toString()
  @Test(timeout=1000) public void construct_2_4_00(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,4,0.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         2\n"+
      "Cols:         4\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void construct_2_4_10(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,4,1.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         2\n"+
      "Cols:         4\n"+
      "ElementCount: 0\n"+
      "fillElement:  1.0\n"+
      "toString():\n"+
      "  1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0 \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void construct_4_1_hi(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,1,"hi");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         1\n"+
      "ElementCount: 0\n"+
      "fillElement:  hi\n"+
      "toString():\n"+
      "   hi \n"+
      "   hi \n"+
      "   hi \n"+
      "   hi \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void construct_5_6_hi(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(5,6,"hi");
    expect =
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         6\n"+
      "ElementCount: 0\n"+
      "fillElement:  hi\n"+
      "toString():\n"+
      "   hi    hi    hi    hi    hi    hi \n"+
      "   hi    hi    hi    hi    hi    hi \n"+
      "   hi    hi    hi    hi    hi    hi \n"+
      "   hi    hi    hi    hi    hi    hi \n"+
      "   hi    hi    hi    hi    hi    hi \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_get_1_1(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,1,0.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         1\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0 \n"+
      "";
    checkMatrix(expect,x);
    x.set(0,0,1.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         1\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  1.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(1.0),x.get(0,0));
  }
  @Test(timeout=1000) public void set_get_2_4_0_0(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,4,0.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         2\n"+
      "Cols:         4\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    x.set(0,0,1.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         2\n"+
      "Cols:         4\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  1.0   0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(1.0),x.get(0,0));
    assertEquals(new Double(0.0),x.get(1,0));
    assertEquals(new Double(0.0),x.get(0,1));
    assertEquals(new Double(0.0),x.get(1,2));
  }
  @Test(timeout=1000) public void set_get_2_4_1_2(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(2,4,0.0);
    x.set(1,2,4.56);
    expect =
      "SparseMatrix\n"+
      "Rows:         2\n"+
      "Cols:         4\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "  0.0   0.0  4.56   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(4.56),x.get(1,2));
    assertEquals(new Double(0.0),x.get(1,0));
    assertEquals(new Double(0.0),x.get(0,1));
    assertEquals(new Double(0.0),x.get(1,3));
    assertEquals(new Double(0.0),x.get(0,0));
  }
  @Test(timeout=1000) public void set_get_6_2_3_0(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,2,"-----");
    x.set(3,0,"X");
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         2\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "    X ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("X",    x.get(3,0));
    assertEquals("-----",x.get(1,0));
    assertEquals("-----",x.get(0,1));
    assertEquals("-----",x.get(3,1));
    assertEquals("-----",x.get(5,1));
  }
  @Test(timeout=1000) public void set_get_add_row_col1(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,2,"-----");
    x.set(3,0,"X");
    x.addRow();
    x.addRow();
    x.addCol();
    expect =
      "SparseMatrix\n"+
      "Rows:         8\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "    X ----- ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("X",    x.get(3,0));
    assertEquals("-----",x.get(7,0));
    assertEquals("-----",x.get(0,2));
    assertEquals("-----",x.get(6,1));
    assertEquals("-----",x.get(7,2));
  }
  @Test(timeout=1000) public void set_get_insert_row_col1(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,2,"-----");
    x.set(3,0,"X");             
    x.insertRow(0);             // insert above X
    expect =
      "SparseMatrix\n"+
      "Rows:         7\n"+
      "Cols:         2\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "    X ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("X",    x.get(4,0));
    assertEquals("-----",x.get(3,0));
    assertEquals("-----",x.get(6,0));
    assertEquals("-----",x.get(0,1));
    assertEquals("-----",x.get(6,1));
  }
  @Test(timeout=1000) public void set_get_insert_row_col2(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,2,"-----");
    x.set(3,0,"X");
    x.insertRow(4);             // insert below X
    expect =
      "SparseMatrix\n"+
      "Rows:         7\n"+
      "Cols:         2\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "    X ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("X",    x.get(3,0));
    assertEquals("-----",x.get(4,0));
    assertEquals("-----",x.get(6,0));
    assertEquals("-----",x.get(0,1));
    assertEquals("-----",x.get(6,1));
  }
  @Test(timeout=1000) public void set_get_insert_row_col3(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,2,"-----");
    x.set(3,0,"X");
    x.insertCol(0);             // insert left of X
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "-----     X ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("X",    x.get(3,1));
    assertEquals("-----",x.get(3,0));
    assertEquals("-----",x.get(0,2));
    assertEquals("-----",x.get(2,1));
    assertEquals("-----",x.get(5,2));
  }
  @Test(timeout=1000) public void set_get_insert_row_col4(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,2,"-----");
    x.set(3,0,"X");
    x.insertCol(1);             // insert right of X
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "    X ----- ----- \n"+
      "----- ----- ----- \n"+
      "----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("X",    x.get(3,0));
    assertEquals("-----",x.get(3,1));
    assertEquals("-----",x.get(0,2));
    assertEquals("-----",x.get(2,1));
    assertEquals("-----",x.get(5,2));
  }
  @Test(timeout=1000) public void set_get_insert_row_col5(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,2,"-----");
    x.set(3,0,"X");
    x.insertCol(1);             // insert right of X
    x.insertRow(3);             // insert above X
    x.insertCol(0);             // insert left of X
    x.insertRow(6);             // insert below X
    expect =
      "SparseMatrix\n"+
      "Rows:         8\n"+
      "Cols:         4\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- \n"+
      "----- ----- ----- ----- \n"+
      "----- ----- ----- ----- \n"+
      "----- ----- ----- ----- \n"+
      "-----     X ----- ----- \n"+
      "----- ----- ----- ----- \n"+
      "----- ----- ----- ----- \n"+
      "----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("X",    x.get(4,1));
    assertEquals("-----",x.get(3,1));
    assertEquals("-----",x.get(3,0));
    assertEquals("-----",x.get(2,3));
    assertEquals("-----",x.get(7,3));
  }
  @Test(timeout=1000) public void set_expands1(){
    String expect;
    SparseMatrix<Integer> x = new SparseMatrix<Integer>(2,2,0);
    expect =
      "SparseMatrix\n"+
      "Rows:         2\n"+
      "Cols:         2\n"+
      "ElementCount: 0\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "    0     0 \n"+
      "    0     0 \n"+
      "";
    checkMatrix(expect,x);
    x.set(3,5,111);
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         6\n"+
      "ElementCount: 1\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0   111 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Integer(111),x.get(3,5));
    assertEquals(new Integer(  0),x.get(2,5));
  }

}