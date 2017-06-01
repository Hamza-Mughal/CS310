// HW2 Final Tests for SparseMatrix
// Last update: Tue Oct 18 23:31:51 EDT 2016 
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test; // fixes some compile problems with annotations

public class HW2FinalTests {
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("HW2FinalTests");
  } 

  // Utility functions like checkMatrix() near the bottom of this file

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
  @Test(timeout=1000) public void set_expands2(){
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
    x.set(7,2,222);
    expect =
      "SparseMatrix\n"+
      "Rows:         8\n"+
      "Cols:         6\n"+
      "ElementCount: 2\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0   111 \n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0     0     0     0     0 \n"+
      "    0     0   222     0     0     0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Integer(111),x.get(3,5));
    assertEquals(new Integer(222),x.get(7,2));
    assertEquals(new Integer(  0),x.get(2,5));
  }
  @Test(timeout=1000) public void set_expands3(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>("-----");
    expect =
      "SparseMatrix\n"+
      "Rows:         0\n"+
      "Cols:         0\n"+
      "ElementCount: 0\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "";
    checkMatrix(expect,x);
    x.set(0,0,"AAA");
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         1\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "  AAA \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("AAA",x.get(0,0));
  }
  @Test(timeout=1000) public void set_expands4(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>("-----");
    expect =
      "SparseMatrix\n"+
      "Rows:         0\n"+
      "Cols:         0\n"+
      "ElementCount: 0\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "";
    checkMatrix(expect,x);
    x.set(0,0,"AAA");
    x.set(1,3,"BBB");
    x.set(4,1,"CCC");
    x.set(2,2,"DDD");
    expect =
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         4\n"+
      "ElementCount: 4\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "  AAA ----- ----- ----- \n"+
      "----- ----- -----   BBB \n"+
      "----- -----   DDD ----- \n"+
      "----- ----- ----- ----- \n"+
      "-----   CCC ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    assertEquals("AAA",x.get(0,0));
    assertEquals("BBB",x.get(1,3));
    assertEquals("CCC",x.get(4,1));
    assertEquals("DDD",x.get(2,2));
    assertEquals("-----",x.get(2,1));
    assertEquals("-----",x.get(2,3));
    assertEquals("-----",x.get(1,2));
    assertEquals("-----",x.get(4,3));
    assertEquals("-----",x.get(4,0));
  }

  // Left to right set
  @Test(timeout=1000) public void set_same_row1(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    x.set(0,1,1.0);
    x.set(0,3,2.0);
    x.set(0,5,3.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 3\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   1.0   0.0   2.0   0.0   3.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(0.0),x.get(0,0));
    assertEquals(new Double(1.0),x.get(0,1));
    assertEquals(new Double(0.0),x.get(0,2));
    assertEquals(new Double(2.0),x.get(0,3));
    assertEquals(new Double(0.0),x.get(0,4));
    assertEquals(new Double(3.0),x.get(0,5));
    assertEquals(new Double(0.0),x.get(0,6));
    assertEquals(new Double(0.0),x.get(0,7));
  }
  // Right to left set
  @Test(timeout=1000) public void set_same_row2(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    x.set(0,5,3.0);
    x.set(0,3,2.0);
    x.set(0,1,1.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 3\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   1.0   0.0   2.0   0.0   3.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(0.0),x.get(0,0));
    assertEquals(new Double(1.0),x.get(0,1));
    assertEquals(new Double(0.0),x.get(0,2));
    assertEquals(new Double(2.0),x.get(0,3));
    assertEquals(new Double(0.0),x.get(0,4));
    assertEquals(new Double(3.0),x.get(0,5));
    assertEquals(new Double(0.0),x.get(0,6));
    assertEquals(new Double(0.0),x.get(0,7));
  }
  // Insertion in middle
  @Test(timeout=1000) public void set_same_row3(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    x.set(0,5,3.0);
    x.set(0,1,1.0);
    x.set(0,3,2.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 3\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   1.0   0.0   2.0   0.0   3.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(0.0),x.get(0,0));
    assertEquals(new Double(1.0),x.get(0,1));
    assertEquals(new Double(0.0),x.get(0,2));
    assertEquals(new Double(2.0),x.get(0,3));
    assertEquals(new Double(0.0),x.get(0,4));
    assertEquals(new Double(3.0),x.get(0,5));
    assertEquals(new Double(0.0),x.get(0,6));
    assertEquals(new Double(0.0),x.get(0,7));
  }
  @Test(timeout=1000) public void set_same_row4(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    x.set(0,5,3.0);
    x.set(0,1,1.0);
    x.set(0,3,2.0);
    x.set(0,4,4.0);
    x.set(0,0,5.0);
    x.set(0,7,6.0);
    x.set(0,6,7.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 7\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  5.0   1.0   0.0   2.0   4.0   3.0   7.0   6.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(5.0),x.get(0,0));
    assertEquals(new Double(1.0),x.get(0,1));
    assertEquals(new Double(0.0),x.get(0,2));
    assertEquals(new Double(2.0),x.get(0,3));
    assertEquals(new Double(4.0),x.get(0,4));
    assertEquals(new Double(3.0),x.get(0,5));
    assertEquals(new Double(7.0),x.get(0,6));
    assertEquals(new Double(6.0),x.get(0,7));
  }

  @Test(timeout=1000) public void set_overwrites_existing1(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    x.set(0,1,1.0);
    x.set(0,1,2.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   2.0   0.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(0.0),x.get(0,0));
    assertEquals(new Double(2.0),x.get(0,1));
    assertEquals(new Double(0.0),x.get(0,2));
    assertEquals(new Double(0.0),x.get(0,3));
    assertEquals(new Double(0.0),x.get(0,4));
    assertEquals(new Double(0.0),x.get(0,5));
    assertEquals(new Double(0.0),x.get(0,6));
  }
  @Test(timeout=1000) public void set_overwrites_existing2(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    x.set(0,1,1.0);
    x.set(0,2,3.0);
    x.set(0,1,2.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 2\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   2.0   3.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(0.0),x.get(0,0));
    assertEquals(new Double(2.0),x.get(0,1));
    assertEquals(new Double(3.0),x.get(0,2));
    assertEquals(new Double(0.0),x.get(0,3));
    assertEquals(new Double(0.0),x.get(0,4));
    assertEquals(new Double(0.0),x.get(0,5));
    assertEquals(new Double(0.0),x.get(0,6));
  }
  @Test(timeout=1000) public void set_overwrites_existing3(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    x.set(0,1,1.0);
    x.set(0,2,3.0);
    x.set(0,1,2.0);
    x.set(0,2,4.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 2\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   2.0   4.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Double(0.0),x.get(0,0));
    assertEquals(new Double(2.0),x.get(0,1));
    assertEquals(new Double(4.0),x.get(0,2));
    assertEquals(new Double(0.0),x.get(0,3));
    assertEquals(new Double(0.0),x.get(0,4));
    assertEquals(new Double(0.0),x.get(0,5));
    assertEquals(new Double(0.0),x.get(0,6));
  }
  @Test(timeout=1000) public void set_overwrites_existing4(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(1,8,0.0);
    x.set(0,1,1.0);
    x.set(0,2,3.0);
    x.set(0,2,4.0);
    x.set(0,2,5.0);
    x.set(0,2,6.0);
    x.set(0,6,7.0);
    x.set(0,4,8.0);
    x.set(0,1,2.0);
    x.set(0,2,9.0);
    x.set(0,4,10.0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         8\n"+
      "ElementCount: 4\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   2.0   9.0   0.0  10.0   0.0   7.0   0.0 \n"+
      "";
    checkMatrix(expect,x);
  }
  // Multiple sets with overwrites dimensions
  @Test(timeout=1000) public void set_overwrites_existing5(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(0,1,"BB");
    x.set(0,2,"FF");
    x.set(0,3,"EE");
    x.set(3,0,"GG");
    x.set(1,3,"CC");
    x.set(1,1,"AA");
    x.set(2,3,"DD");
    x.set(2,0,"HH");
    x.set(3,2,"II");
    x.set(3,1,"JJ");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         4\n"+
      "ElementCount: 10\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----    BB    FF    EE \n"+
      "-----    AA -----    CC \n"+
      "   HH ----- -----    DD \n"+
      "   GG    JJ    II ----- \n"+
      "";
    checkMatrix(expect,x);
  }

  // Mild stress on set() method
  @Test(timeout=1000) public void set_inserts_expands_overwrites1(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(1,1,"-----");
    x.set(0,0,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(0,1,"BB");
    x.set(0,2,"FF");
    x.set(0,3,"EE");
    x.set(3,0,"GG");
    x.set(1,3,"CC");
    x.set(1,1,"AA");
    x.set(2,3,"DD");
    x.set(2,0,"HH");
    x.set(3,2,"II");
    x.set(3,1,"JJ");
    x.set(5,7,"KK");            // Expand
    x.set(3,7,"LL");            // Column inserts
    x.set(5,7,"MM");
    x.set(1,7,"NN");
    x.set(4,7,"OO");
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         8\n"+
      "ElementCount: 15\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "    A    BB    FF    EE ----- ----- ----- ----- \n"+
      "-----    AA -----    CC ----- ----- -----    NN \n"+
      "   HH ----- -----    DD ----- ----- ----- ----- \n"+
      "   GG    JJ    II ----- ----- ----- -----    LL \n"+
      "----- ----- ----- ----- ----- ----- -----    OO \n"+
      "----- ----- ----- ----- ----- ----- -----    MM \n"+
      "";
    checkMatrix(expect,x);
    x.set(5,5,"PP");            // Row inserts
    x.set(5,2,"QQ");
    x.set(5,6,"RR");
    x.set(5,3,"SS");
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         8\n"+
      "ElementCount: 19\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "    A    BB    FF    EE ----- ----- ----- ----- \n"+
      "-----    AA -----    CC ----- ----- -----    NN \n"+
      "   HH ----- -----    DD ----- ----- ----- ----- \n"+
      "   GG    JJ    II ----- ----- ----- -----    LL \n"+
      "----- ----- ----- ----- ----- ----- -----    OO \n"+
      "----- -----    QQ    SS -----    PP    RR    MM \n"+
      "";
    checkMatrix(expect,x);
  }

  @Test(timeout=1000) public void setToFill1(){
    String expect;
    SparseMatrix<Integer> x = new SparseMatrix<Integer>(1,1,0);
    x.setToFill(0,0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         1\n"+
      "ElementCount: 0\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "    0 \n"+
      "";
    checkMatrix(expect,x);
  }    
  @Test(timeout=1000) public void setToFill2(){
    String expect;
    SparseMatrix<Integer> x = new SparseMatrix<Integer>(1,1,0);
    x.set(0,0,11);
    x.setToFill(0,0);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         1\n"+
      "ElementCount: 0\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "    0 \n"+
      "";
    checkMatrix(expect,x);
  }    
  @Test(timeout=1000) public void setToFill3(){
    String expect;
    SparseMatrix<Integer> x = new SparseMatrix<Integer>(1,1,0);
    x.set(0,1,11);
    x.set(0,2,22);
    x.setToFill(0,2);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "    0    11     0 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Integer(0),x.get(0,0));
    assertEquals(new Integer(11),x.get(0,1));
    assertEquals(new Integer(0),x.get(0,2));
  }    
  @Test(timeout=1000) public void setToFill4(){
    String expect;
    SparseMatrix<Integer> x = new SparseMatrix<Integer>(1,1,0);
    x.set(0,1,11);
    x.set(0,2,22);
    x.setToFill(0,2);
    x.set(0,2,33);
    x.setToFill(0,1);
    x.set(0,0,44);
    expect =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         3\n"+
      "ElementCount: 2\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "   44     0    33 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Integer(44),x.get(0,0));
    assertEquals(new Integer(0),x.get(0,1));
    assertEquals(new Integer(33),x.get(0,2));
  }    
  @Test(timeout=1000) public void setToFill5(){
    String expect;
    SparseMatrix<Integer> x = new SparseMatrix<Integer>(1,1,0);
    x.set(1,0,11);
    x.set(2,0,22);
    x.setToFill(2,0);
    x.set(2,0,33);
    x.setToFill(1,0);
    x.set(0,0,44);
    expect =
      "SparseMatrix\n"+
      "Rows:         3\n"+
      "Cols:         1\n"+
      "ElementCount: 2\n"+
      "fillElement:  0\n"+
      "toString():\n"+
      "   44 \n"+
      "    0 \n"+
      "   33 \n"+
      "";
    checkMatrix(expect,x);
    assertEquals(new Integer(44),x.get(0,0));
    assertEquals(new Integer(0),x.get(1,0));
    assertEquals(new Integer(33),x.get(2,0));
  }    
  @Test(timeout=1000) public void setToFill6(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(1,2,"L");
    x.set(1,0,"M");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 13\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    x.setToFill(1,1);
    x.setToFill(2,4);
    x.setToFill(3,0);
    x.setToFill(1,2);
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 9\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M ----- -----     C ----- \n"+
      "    H     J -----     D ----- \n"+
      "----- -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void setToFill7(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(1,2,"L");
    x.set(1,0,"M");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 13\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    x.setToFill(1,1);
    x.setToFill(1,0);
    x.setToFill(1,2);
    x.setToFill(1,4);
    x.setToFill(1,3);
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 9\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "----- ----- ----- ----- ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void setToFill8(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(1,2,"L");
    x.set(1,0,"M");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 13\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    x.setToFill(3 ,2);
    x.setToFill(0 ,2);
    x.setToFill(2 ,2);
    x.setToFill(1 ,2);
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 10\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B -----     E ----- \n"+
      "    M     A -----     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert1(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,5,"-----");
    x.set(1,2,"A");             
    x.set(1,4,"B");             
    x.set(1,5,"C");             
    x.set(2,1,"D");             
    x.set(3,3,"E");             
    x.set(3,0,"F");             
    x.set(3,4,"G");             
    x.set(3,2,"H");             
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         6\n"+
      "ElementCount: 8\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- -----     A -----     B     C \n"+
      "-----     D ----- ----- ----- ----- \n"+
      "    F -----     H     E     G ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    x.insertRow(3);
    x.insertRow(3);
    expect =
      "SparseMatrix\n"+
      "Rows:         8\n"+
      "Cols:         6\n"+
      "ElementCount: 8\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- -----     A -----     B     C \n"+
      "-----     D ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "    F -----     H     E     G ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert2(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,5,"-----");
    x.set(1,2,"A");             
    x.set(1,4,"B");             
    x.set(1,5,"C");             
    x.set(2,1,"D");             
    x.set(3,3,"E");             
    x.set(3,0,"F");             
    x.set(3,4,"G");             
    x.set(3,2,"H");             
    x.insertRow(1);
    x.insertRow(3);
    expect =
      "SparseMatrix\n"+
      "Rows:         8\n"+
      "Cols:         6\n"+
      "ElementCount: 8\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- -----     A -----     B     C \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "-----     D ----- ----- ----- ----- \n"+
      "    F -----     H     E     G ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert3(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,5,"-----");
    x.set(1,2,"A");             
    x.set(1,4,"B");             
    x.set(1,5,"C");             
    x.set(2,1,"D");             
    x.set(3,3,"E");             
    x.set(3,0,"F");             
    x.set(3,4,"G");             
    x.set(3,2,"H");             
    x.insertCol(1);
    x.insertCol(4);
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         8\n"+
      "ElementCount: 8\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "----- ----- -----     A ----- -----     B     C \n"+
      "----- -----     D ----- ----- ----- ----- ----- \n"+
      "    F ----- -----     H -----     E     G ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert4(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,5,"-----");
    x.set(1,2,"A");             
    x.set(1,4,"B");             
    x.set(1,5,"C");             
    x.set(2,1,"D");             
    x.set(3,3,"E");             
    x.set(3,0,"F");             
    x.set(3,4,"G");             
    x.set(3,2,"H");             
    x.insertCol(1);
    x.insertRow(3);
    x.insertRow(1);
    x.insertCol(4);
    x.insertRow(2);
    expect =
      "SparseMatrix\n"+
      "Rows:         9\n"+
      "Cols:         8\n"+
      "ElementCount: 8\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "----- ----- -----     A ----- -----     B     C \n"+
      "----- -----     D ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "    F ----- -----     H -----     E     G ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert5(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(6,5,"-----");
    x.set(1,2,"A");             
    x.set(1,4,"B");             
    x.set(1,5,"C");             
    x.set(2,1,"D");             
    x.set(3,3,"E");             
    x.set(3,0,"F");             
    x.set(3,4,"G");             
    x.set(3,2,"H");             
    x.insertRow(3);
    x.insertRow(3);
    x.set(5,2,"HH");
    x.setToFill(5,3);
    expect =
      "SparseMatrix\n"+
      "Rows:         8\n"+
      "Cols:         6\n"+
      "ElementCount: 7\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- -----     A -----     B     C \n"+
      "-----     D ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "    F -----    HH -----     G ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert6(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(1,2,"L");
    x.set(1,0,"M");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 13\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    x.insertRow(2);
    x.insertRow(2);
    x.set(3,3,"NN");
    x.set(2,3,"OO");
    expect =
      "SparseMatrix\n"+
      "Rows:         6\n"+
      "Cols:         5\n"+
      "ElementCount: 15\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "----- ----- -----    OO ----- \n"+
      "----- ----- -----    NN ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert7(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(1,2,"L");
    x.set(1,0,"M");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 13\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    x.insertCol(1);
    x.insertCol(4);
    x.set(0,0,"NN");
    x.set(1,4,"OO");
    x.set(1,1,"PP");
    x.set(3,5,"QQ");
    x.set(2,4,"RR");
    x.set(2,3,"SS");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         7\n"+
      "ElementCount: 19\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "   NN -----     B     F -----     E ----- \n"+
      "    M    PP     A     L    OO     C ----- \n"+
      "    H -----     J    SS    RR     D     I \n"+
      "    G ----- -----     K -----    QQ ----- \n"+
      "";
    checkMatrix(expect,x);
  }
  @Test(timeout=1000) public void set_insert8(){
    String expect;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(1,2,"L");
    x.set(1,0,"M");
    expect =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 13\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    checkMatrix(expect,x);
    x.insertCol(1);
    x.insertCol(4);
    x.set(0,0,"NN");
    x.set(1,4,"OO");
    x.set(1,1,"PP");
    x.insertRow(4);
    x.set(3,5,"QQ");
    x.set(2,4,"RR");
    x.insertRow(2);
    x.set(2,3,"SS");
    x.insertRow(0);
    x.setToFill(2,3);
    expect =
      "SparseMatrix\n"+
      "Rows:         7\n"+
      "Cols:         7\n"+
      "ElementCount: 18\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- ----- ----- \n"+
      "   NN -----     B     F -----     E ----- \n"+
      "    M    PP     A -----    OO     C ----- \n"+
      "----- ----- -----    SS ----- ----- ----- \n"+
      "    H -----     J -----    RR     D     I \n"+
      "    G ----- -----     K -----    QQ ----- \n"+
      "----- ----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expect,x);
  }

  @Test(timeout=1000) public void all_elements1(){
    String expect, expectMat;
    SparseMatrix<String> x = new SparseMatrix<String>(1,1,"-----");
    x.set(0,0,"A");
    expectMat =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         1\n"+
      "ElementCount: 1\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "    A \n"+
      "";
    expect =
      "[(0, 0, A)]"+
      "";
    checkAllElements(expect,x);
  }
  @Test(timeout=1000) public void all_elements2(){
    String expect, expectMat;
    SparseMatrix<String> x = new SparseMatrix<String>(1,5,"-----");
    x.set(0,3,"C");
    x.set(0,0,"A");
    x.set(0,2,"B");
    x.set(0,4,"D");
    expectMat =
      "SparseMatrix\n"+
      "Rows:         1\n"+
      "Cols:         5\n"+
      "ElementCount: 4\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "    A -----     B     C     D \n"+
      "";
    expect =
      "[(0, 0, A), (0, 2, B), (0, 3, C), (0, 4, D)]"+
      "";
    checkAllElements(expect,x);
  }
  @Test(timeout=1000) public void all_elements3(){
    String expect, expectMat;
    SparseMatrix<String> x = new SparseMatrix<String>(5,1,"-----");
    x.set(3,0,"C");
    x.set(0,0,"A");
    x.set(2,0,"B");
    x.set(4,0,"D");
    expectMat =
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         1\n"+
      "ElementCount: 4\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "    A \n"+
      "----- \n"+
      "    B \n"+
      "    C \n"+
      "    D \n"+
      "";
    expect =
      "[(0, 0, A), (2, 0, B), (3, 0, C), (4, 0, D)]"+
      "";
    checkAllElements(expect,x);
  }
  @Test(timeout=1000) public void all_elements4(){
    String expect, expectMat;
    SparseMatrix<String> x = new SparseMatrix<String>(2,5,"-----");
    x.set(1,2,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(0,3,"D");
    x.set(0,4,"E");
    x.set(0,0,"F");
    x.set(1,2,"AA");
    x.set(0,2,"G");
    x.set(1,3,"CC");
    x.set(0,3,"GG");
    x.set(1,1,"H");
    expectMat =
      "SparseMatrix\n"+
      "Rows:         2\n"+
      "Cols:         5\n"+
      "ElementCount: 8\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "    F     B     G    GG     E \n"+
      "-----     H    AA    CC ----- \n"+
      "";
    expect =
      "[(0, 0, F), (0, 1, B), (0, 2, G), (0, 3, GG), (0, 4, E), (1, 1, H), (1, 2, AA), (1, 3, CC)]"+
      "";
    checkAllElements(expect,x);
  }
  @Test(timeout=1000) public void all_elements5(){
    String expect, expectMat;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(1,2,"L");
    x.set(1,0,"M");
    expectMat =
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         5\n"+
      "ElementCount: 13\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "-----     B     F     E ----- \n"+
      "    M     A     L     C ----- \n"+
      "    H     J -----     D     I \n"+
      "    G -----     K ----- ----- \n"+
      "";
    expect =
      "[(0, 1, B), (0, 2, F), (0, 3, E), (1, 0, M), (1, 1, A), (1, 2, L), (1, 3, C), (2, 0, H), (2, 1, J), (2, 3, D), (2, 4, I), (3, 0, G), (3, 2, K)]"+
      "";
    checkAllElements(expect,x);
  }

  @Test(timeout=1000) public void stress1(){
    String expectMat,expectEls;
    SparseMatrix<String> x = new SparseMatrix<String>(4,4,"-----");
    x.set(1,1,"A");
    x.set(0,1,"B");
    x.set(1,3,"C");
    x.set(2,3,"D");
    x.insertRow(0);
    x.set(0,3,"E");
    x.set(0,2,"F");
    x.insertCol(2);
    x.set(3,0,"G");
    x.set(2,0,"H");
    x.set(2,4,"I");
    x.insertCol(4);
    x.set(2,1,"J");
    x.set(3,2,"K");
    x.set(2,0,"HH");
    x.set(6,0,"X");
    x.setToFill(6,0);
    x.set(1,2,"L");
    x.set(1,0,"M");
    x.insertCol(2);
    x.setToFill(0,4);
    x.setToFill(2,2);
    x.set(3,2,"O");
    x.set(4,2,"P");
    x.set(5,6,"Q");
    x.setToFill(2,6);
    x.set(1,6,"R");
    x.set(2,5,"S");
    x.set(2,6,"T");
    expectMat =
      "SparseMatrix\n"+
      "Rows:         7\n"+
      "Cols:         7\n"+
      "ElementCount: 15\n"+
      "fillElement:  -----\n"+
      "toString():\n"+
      "----- ----- ----- ----- ----- -----     E \n"+
      "    M     B -----     L ----- -----     R \n"+
      "   HH     J ----- ----- -----     S     T \n"+
      "    G -----     O     K ----- -----     D \n"+
      "----- -----     P ----- ----- ----- ----- \n"+
      "----- ----- ----- ----- ----- -----     Q \n"+
      "----- ----- ----- ----- ----- ----- ----- \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 6, E), (1, 0, M), (1, 1, B), (1, 3, L), (1, 6, R), (2, 0, HH), (2, 1, J), (2, 5, S), (2, 6, T), (3, 0, G), (3, 2, O), (3, 3, K), (3, 6, D), (4, 2, P), (5, 6, Q)]"+
      "";
    checkAllElements(expectEls,x);
  }    
  @Test(timeout=1000) public void stress2(){
    String expectMat,expectEls;
    SparseMatrix<Pair<Integer,Integer>> x = new SparseMatrix<Pair<Integer,Integer>>(0,0,new Pair<Integer,Integer>(0,0));
    for(int i=0; i<10; i++){
      for(int j=0; j<7; j++){
        x.set(i,j,new Pair<Integer,Integer>(i,j));
      }
    }
    expectMat =
      "SparseMatrix\n"+
      "Rows:         10\n"+
      "Cols:         7\n"+
      "ElementCount: 70\n"+
      "fillElement:  (0,0)\n"+
      "toString():\n"+
      "(0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) \n"+
      "(1,0) (1,1) (1,2) (1,3) (1,4) (1,5) (1,6) \n"+
      "(2,0) (2,1) (2,2) (2,3) (2,4) (2,5) (2,6) \n"+
      "(3,0) (3,1) (3,2) (3,3) (3,4) (3,5) (3,6) \n"+
      "(4,0) (4,1) (4,2) (4,3) (4,4) (4,5) (4,6) \n"+
      "(5,0) (5,1) (5,2) (5,3) (5,4) (5,5) (5,6) \n"+
      "(6,0) (6,1) (6,2) (6,3) (6,4) (6,5) (6,6) \n"+
      "(7,0) (7,1) (7,2) (7,3) (7,4) (7,5) (7,6) \n"+
      "(8,0) (8,1) (8,2) (8,3) (8,4) (8,5) (8,6) \n"+
      "(9,0) (9,1) (9,2) (9,3) (9,4) (9,5) (9,6) \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 0, (0,0)), (0, 1, (0,1)), (0, 2, (0,2)), (0, 3, (0,3)), (0, 4, (0,4)), (0, 5, (0,5)), (0, 6, (0,6)), (1, 0, (1,0)), (1, 1, (1,1)), (1, 2, (1,2)), (1, 3, (1,3)), (1, 4, (1,4)), (1, 5, (1,5)), (1, 6, (1,6)), (2, 0, (2,0)), (2, 1, (2,1)), (2, 2, (2,2)), (2, 3, (2,3)), (2, 4, (2,4)), (2, 5, (2,5)), (2, 6, (2,6)), (3, 0, (3,0)), (3, 1, (3,1)), (3, 2, (3,2)), (3, 3, (3,3)), (3, 4, (3,4)), (3, 5, (3,5)), (3, 6, (3,6)), (4, 0, (4,0)), (4, 1, (4,1)), (4, 2, (4,2)), (4, 3, (4,3)), (4, 4, (4,4)), (4, 5, (4,5)), (4, 6, (4,6)), (5, 0, (5,0)), (5, 1, (5,1)), (5, 2, (5,2)), (5, 3, (5,3)), (5, 4, (5,4)), (5, 5, (5,5)), (5, 6, (5,6)), (6, 0, (6,0)), (6, 1, (6,1)), (6, 2, (6,2)), (6, 3, (6,3)), (6, 4, (6,4)), (6, 5, (6,5)), (6, 6, (6,6)), (7, 0, (7,0)), (7, 1, (7,1)), (7, 2, (7,2)), (7, 3, (7,3)), (7, 4, (7,4)), (7, 5, (7,5)), (7, 6, (7,6)), (8, 0, (8,0)), (8, 1, (8,1)), (8, 2, (8,2)), (8, 3, (8,3)), (8, 4, (8,4)), (8, 5, (8,5)), (8, 6, (8,6)), (9, 0, (9,0)), (9, 1, (9,1)), (9, 2, (9,2)), (9, 3, (9,3)), (9, 4, (9,4)), (9, 5, (9,5)), (9, 6, (9,6))]"+
      "";
    checkAllElements(expectEls,x);
  }
  @Test(timeout=1000) public void stress3(){
    String expectMat,expectEls;
    SparseMatrix<Pair<Integer,Integer>> x = new SparseMatrix<Pair<Integer,Integer>>(0,0,new Pair<Integer,Integer>(0,0));
    for(int i=0; i<10; i++){
      for(int j=0; j<7; j++){
        x.set(i,j,new Pair<Integer,Integer>(i,j));
      }
    }
    for(int i=0; i<10; i++){
      for(int j=0; j<7; j++){
        x.setToFill(i,j);
      }
    }
    expectMat =
      "SparseMatrix\n"+
      "Rows:         10\n"+
      "Cols:         7\n"+
      "ElementCount: 0\n"+
      "fillElement:  (0,0)\n"+
      "toString():\n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[]"+
      "";
    checkAllElements(expectEls,x);
  }
  // Randomized order of adding to create a dense mat
  @Test(timeout=1000) public void stress4(){
    String expectMat,expectEls;
    int [] els = {
      1,2, 4,6, 3,3, 4,0, 2,1, 5,0, 6,5, 5,2, 7,6, 2,5,
      3,0, 2,6, 3,5, 9,4, 1,4, 8,3, 6,6, 0,3, 2,2, 9,1,
      8,5, 3,6, 9,0, 8,4, 5,1, 3,1, 6,1, 6,4, 0,5, 4,4,
      7,3, 8,1, 0,4, 6,3, 6,0, 9,2, 7,2, 4,3, 2,4, 3,4,
      0,1, 8,0, 3,2, 1,6, 5,5, 0,6, 5,6, 1,5, 8,2, 2,0,
      7,0, 9,5, 4,1, 9,3, 2,3, 5,3, 9,6, 1,0, 6,2, 7,1,
      7,4, 0,0, 1,1, 5,4, 0,2, 4,5, 7,5, 4,2, 1,3, 8,6,
    };
    SparseMatrix<Pair<Integer,Integer>> x = new SparseMatrix<Pair<Integer,Integer>>(0,0,new Pair<Integer,Integer>(0,0));
    for(int k=0; k<els.length; k+=2){
      int i=els[k], j=els[k+1];
      x.set(i,j, new Pair<Integer,Integer>(i,j));
    }
    expectMat =
      "SparseMatrix\n"+
      "Rows:         10\n"+
      "Cols:         7\n"+
      "ElementCount: 70\n"+
      "fillElement:  (0,0)\n"+
      "toString():\n"+
      "(0,0) (0,1) (0,2) (0,3) (0,4) (0,5) (0,6) \n"+
      "(1,0) (1,1) (1,2) (1,3) (1,4) (1,5) (1,6) \n"+
      "(2,0) (2,1) (2,2) (2,3) (2,4) (2,5) (2,6) \n"+
      "(3,0) (3,1) (3,2) (3,3) (3,4) (3,5) (3,6) \n"+
      "(4,0) (4,1) (4,2) (4,3) (4,4) (4,5) (4,6) \n"+
      "(5,0) (5,1) (5,2) (5,3) (5,4) (5,5) (5,6) \n"+
      "(6,0) (6,1) (6,2) (6,3) (6,4) (6,5) (6,6) \n"+
      "(7,0) (7,1) (7,2) (7,3) (7,4) (7,5) (7,6) \n"+
      "(8,0) (8,1) (8,2) (8,3) (8,4) (8,5) (8,6) \n"+
      "(9,0) (9,1) (9,2) (9,3) (9,4) (9,5) (9,6) \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 0, (0,0)), (0, 1, (0,1)), (0, 2, (0,2)), (0, 3, (0,3)), (0, 4, (0,4)), (0, 5, (0,5)), (0, 6, (0,6)), (1, 0, (1,0)), (1, 1, (1,1)), (1, 2, (1,2)), (1, 3, (1,3)), (1, 4, (1,4)), (1, 5, (1,5)), (1, 6, (1,6)), (2, 0, (2,0)), (2, 1, (2,1)), (2, 2, (2,2)), (2, 3, (2,3)), (2, 4, (2,4)), (2, 5, (2,5)), (2, 6, (2,6)), (3, 0, (3,0)), (3, 1, (3,1)), (3, 2, (3,2)), (3, 3, (3,3)), (3, 4, (3,4)), (3, 5, (3,5)), (3, 6, (3,6)), (4, 0, (4,0)), (4, 1, (4,1)), (4, 2, (4,2)), (4, 3, (4,3)), (4, 4, (4,4)), (4, 5, (4,5)), (4, 6, (4,6)), (5, 0, (5,0)), (5, 1, (5,1)), (5, 2, (5,2)), (5, 3, (5,3)), (5, 4, (5,4)), (5, 5, (5,5)), (5, 6, (5,6)), (6, 0, (6,0)), (6, 1, (6,1)), (6, 2, (6,2)), (6, 3, (6,3)), (6, 4, (6,4)), (6, 5, (6,5)), (6, 6, (6,6)), (7, 0, (7,0)), (7, 1, (7,1)), (7, 2, (7,2)), (7, 3, (7,3)), (7, 4, (7,4)), (7, 5, (7,5)), (7, 6, (7,6)), (8, 0, (8,0)), (8, 1, (8,1)), (8, 2, (8,2)), (8, 3, (8,3)), (8, 4, (8,4)), (8, 5, (8,5)), (8, 6, (8,6)), (9, 0, (9,0)), (9, 1, (9,1)), (9, 2, (9,2)), (9, 3, (9,3)), (9, 4, (9,4)), (9, 5, (9,5)), (9, 6, (9,6))]"+
      "";
    checkAllElements(expectEls,x);
  }
  // Randomized order of adding to create a dense mat; random removes to eliminate all
  @Test(timeout=1000) public void stress5(){
    String expectMat,expectEls;
    int [] els = {
      1,2, 4,6, 3,3, 4,0, 2,1, 5,0, 6,5, 5,2, 7,6, 2,5,
      3,0, 2,6, 3,5, 9,4, 1,4, 8,3, 6,6, 0,3, 2,2, 9,1,
      8,5, 3,6, 9,0, 8,4, 5,1, 3,1, 6,1, 6,4, 0,5, 4,4,
      7,3, 8,1, 0,4, 6,3, 6,0, 9,2, 7,2, 4,3, 2,4, 3,4,
      0,1, 8,0, 3,2, 1,6, 5,5, 0,6, 5,6, 1,5, 8,2, 2,0,
      7,0, 9,5, 4,1, 9,3, 2,3, 5,3, 9,6, 1,0, 6,2, 7,1,
      7,4, 0,0, 1,1, 5,4, 0,2, 4,5, 7,5, 4,2, 1,3, 8,6,
    };
    SparseMatrix<Pair<Integer,Integer>> x = new SparseMatrix<Pair<Integer,Integer>>(0,0,new Pair<Integer,Integer>(0,0));
    for(int k=0; k<els.length; k+=2){
      int i=els[k], j=els[k+1];
      x.set(i,j, new Pair<Integer,Integer>(i,j));
    }
    int [] rem = {
      6,0, 6,3, 3,3, 9,1, 6,6, 6,4, 5,5, 7,4, 7,1, 5,1,
      0,6, 9,3, 4,3, 7,0, 9,2, 4,0, 9,4, 1,5, 1,4, 1,1,
      2,3, 8,5, 4,1, 7,6, 0,0, 4,6, 7,5, 4,5, 9,0, 5,0,
      0,3, 2,2, 5,3, 0,2, 5,6, 3,6, 6,1, 7,2, 9,5, 8,0,
      9,6, 0,5, 2,6, 7,3, 8,2, 3,5, 6,5, 1,0, 5,4, 5,2,
      0,4, 4,2, 2,4, 2,0, 1,2, 6,2, 3,0, 0,1, 8,6, 8,1,
      2,5, 2,1, 4,4, 3,4, 1,3, 1,6, 8,3, 3,2, 3,1, 8,4,
    };
    for(int k=0; k<rem.length; k+=2){
      int i=rem[k], j=rem[k+1];
      x.setToFill(i,j);
    }
    expectMat =
      "SparseMatrix\n"+
      "Rows:         10\n"+
      "Cols:         7\n"+
      "ElementCount: 0\n"+
      "fillElement:  (0,0)\n"+
      "toString():\n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "(0,0) (0,0) (0,0) (0,0) (0,0) (0,0) (0,0) \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[]"+
      "";
    checkAllElements(expectEls,x);
  }
  @Test(timeout=1000) public void stress6(){
    String expectMat,expectEls;
    int [] els = {
      6,0, 6,3, 3,3, 9,1, 6,6, 6,4, 5,5, 7,4, 7,1, 5,1,
      0,6, 9,3, 4,3, 7,0, 9,2, 4,0, 9,4, 1,5, 1,4, 1,1,
      2,3, 8,5, 4,1, 7,6, 0,0, 4,6, 7,5, 4,5, 9,0, 5,0,
      0,3, 2,2, 5,3, 0,2, 5,6, 3,6, 6,1, 7,2, 9,5, 8,0,
    };
    SparseMatrix<String> x = new SparseMatrix<String>("-");
    for(int k=0; k<els.length; k+=2){
      int i=els[k], j=els[k+1];
      x.set(i,j, ""+i+j);
    }
    int [] rem = {
      1,2, 4,6, 3,3, 4,0, 2,1, 5,0, 6,5, 5,2, 7,6, 2,5,
      3,0, 2,6, 3,5, 9,4, 1,4, 8,3, 6,6, 0,3, 2,2, 9,1,
    };
    for(int k=0; k<rem.length; k+=2){
      int i=rem[k], j=rem[k+1];
      x.setToFill(i,j);
    }
    expectMat =
      "SparseMatrix\n"+
      "Rows:         10\n"+
      "Cols:         7\n"+
      "ElementCount: 29\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "   00     -    02     -     -     -    06 \n"+
      "    -    11     -     -     -    15     - \n"+
      "    -     -     -    23     -     -     - \n"+
      "    -     -     -     -     -     -    36 \n"+
      "    -    41     -    43     -    45     - \n"+
      "    -    51     -    53     -    55    56 \n"+
      "   60    61     -    63    64     -     - \n"+
      "   70    71    72     -    74    75     - \n"+
      "   80     -     -     -     -    85     - \n"+
      "   90     -    92    93     -    95     - \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 0, 00), (0, 2, 02), (0, 6, 06), (1, 1, 11), (1, 5, 15), (2, 3, 23), (3, 6, 36), (4, 1, 41), (4, 3, 43), (4, 5, 45), (5, 1, 51), (5, 3, 53), (5, 5, 55), (5, 6, 56), (6, 0, 60), (6, 1, 61), (6, 3, 63), (6, 4, 64), (7, 0, 70), (7, 1, 71), (7, 2, 72), (7, 4, 74), (7, 5, 75), (8, 0, 80), (8, 5, 85), (9, 0, 90), (9, 2, 92), (9, 3, 93), (9, 5, 95)]"+
      "";
    checkAllElements(expectEls,x);
  }

  // Apply operations the given matrix; ops are grouped in pairs and
  // follow the convention 
  // 
  // +i,+j - set element
  // -1,+j - insert row j
  // -2,+j - insert colum j
  // -i,-j - set to fill i,j
  public static void applyOps(int [] ops, SparseMatrix<String> x){
    for(int k=0; k<ops.length; k+=2){
      int i=ops[k], j=ops[k+1];
      if(i>=0 && j>=0){
        x.set(i,j, ""+i+j);
      }
      else if(i<0 && j<0){
        x.setToFill(-i,-j);
      }
      else if(i==-1 && j>=0){
        x.insertRow(j);
      }
      else if(i==-2 && j>=0){
        x.insertCol(j);
      }
    }
  }    
  // Mix of set, setToFill, insertRow, insertCol
  @Test(timeout=1000) public void stress7(){
    String expectMat,expectEls;
    int [] ops = {
      9,4, 0,2, 2,3, 6,4, 1,0, 2,4, 3,3, 8,3, 3,0, 7,0, // sets
      4,3, 7,6, 8,2, 6,3, 3,6, 3,2, 2,5, 4,2, 6,0, 1,1,
      -1,3,                                   // insertRow
      -2,5,                                   // insertCol
      1,5, 0,5, 8,1, 9,6,                     // set
      -9,-4, -3,-6, -7,-6,                    // setToFill
      -2,1, -2,7,                             // insertCol
      1,3, 5,4, 5,0, 9,2, 9,3, 3,5,           // set
      5,1, 3,4,                               // set
      -5,-1, -3,-4,                           // setToFill
      9,1, 6,6, 0,1, 1,2, 6,1, 2,0, 8,4, 5,2, // sets
      -1,5,                                   // insertRow
    };
    SparseMatrix<String> x = new SparseMatrix<String>("-");
    applyOps(ops,x);
    expectMat =
      "SparseMatrix\n"+
      "Rows:         12\n"+
      "Cols:         10\n"+
      "ElementCount: 35\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "    -    01     -    02     -     -    05     -     -     - \n"+
      "   10     -    12    13     -     -    15     -     -     - \n"+
      "   20     -     -     -    23    24     -     -    25     - \n"+
      "    -     -     -     -     -    35     -     -     -     - \n"+
      "   30     -     -    32    33     -     -     -     -    36 \n"+
      "    -     -     -     -     -     -     -     -     -     - \n"+
      "   50     -    52    42    54     -     -     -     -     - \n"+
      "    -    61     -     -     -     -    66     -     -     - \n"+
      "   60     -     -     -    63    64     -     -     -     - \n"+
      "   70     -    81     -    84     -     -     -     -    76 \n"+
      "    -    91    92    93    83     -     -     -    96     - \n"+
      "    -     -     -     -     -    94     -     -     -     - \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 1, 01), (0, 3, 02), (0, 6, 05), (1, 0, 10), (1, 2, 12), (1, 3, 13), (1, 6, 15), (2, 0, 20), (2, 4, 23), (2, 5, 24), (2, 8, 25), (3, 5, 35), (4, 0, 30), (4, 3, 32), (4, 4, 33), (4, 9, 36), (6, 0, 50), (6, 2, 52), (6, 3, 42), (6, 4, 54), (7, 1, 61), (7, 6, 66), (8, 0, 60), (8, 4, 63), (8, 5, 64), (9, 0, 70), (9, 2, 81), (9, 4, 84), (9, 9, 76), (10, 1, 91), (10, 2, 92), (10, 3, 93), (10, 4, 83), (10, 8, 96), (11, 5, 94)]"+
      "";
    checkAllElements(expectEls,x);
  }
  // Stress two separate matrices simultaneously
  @Test(timeout=1000) public void stress8(){
    String expectMat,expectEls;
    SparseMatrix<String> x = new SparseMatrix<String>(0,0,"-");
    SparseMatrix<String> y = new SparseMatrix<String>(0,0,"-");
    int [] opsx = {
      9,4, 0,2, 2,3, 6,4, 1,0, 2,4, 3,3, 8,3, 3,0, 7,0, // sets
      4,3, 7,6, 8,2, 6,3, 3,6, 3,2, 2,5, 4,2, 6,0, 1,1,
      -1,3,                                   // insertRow
      -2,5,                                   // insertCol
      1,5, 0,5, 8,1, 9,6,                     // set
      -9,-4, -3,-6, -7,-6,                    // setToFill
      -2,1, -2,7,                             // insertCol
    };
    int [] opsy = {
      1,3, 5,4, 5,0, 9,2, 9,3, 3,5,           // set
      5,1, 3,4,                               // set
      -5,-1, -3,-4,                           // setToFill
      9,1, 6,6, 0,1, 1,2, 6,1, 2,0, 8,4, 5,2, // sets
      -1,5,                                   // insertRow
    };
    applyOps(opsx,x);           // alter x
    applyOps(opsy,y);           // alter y
    expectMat =                 // check x
      "SparseMatrix\n"+
      "Rows:         11\n"+
      "Cols:         10\n"+
      "ElementCount: 24\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "    -     -     -    02     -     -    05     -     -     - \n"+
      "   10     -    11     -     -     -    15     -     -     - \n"+
      "    -     -     -     -    23    24     -     -    25     - \n"+
      "    -     -     -     -     -     -     -     -     -     - \n"+
      "   30     -     -    32    33     -     -     -     -    36 \n"+
      "    -     -     -    42    43     -     -     -     -     - \n"+
      "    -     -     -     -     -     -     -     -     -     - \n"+
      "   60     -     -     -    63    64     -     -     -     - \n"+
      "   70     -    81     -     -     -     -     -     -    76 \n"+
      "    -     -     -    82    83     -     -     -    96     - \n"+
      "    -     -     -     -     -    94     -     -     -     - \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 3, 02), (0, 6, 05), (1, 0, 10), (1, 2, 11), (1, 6, 15), (2, 4, 23), (2, 5, 24), (2, 8, 25), (4, 0, 30), (4, 3, 32), (4, 4, 33), (4, 9, 36), (5, 3, 42), (5, 4, 43), (7, 0, 60), (7, 4, 63), (7, 5, 64), (8, 0, 70), (8, 2, 81), (8, 9, 76), (9, 3, 82), (9, 4, 83), (9, 8, 96), (10, 5, 94)]"+
      "";
    expectMat =                 // check y
      "SparseMatrix\n"+
      "Rows:         11\n"+
      "Cols:         7\n"+
      "ElementCount: 14\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "    -    01     -     -     -     -     - \n"+
      "    -     -    12    13     -     -     - \n"+
      "   20     -     -     -     -     -     - \n"+
      "    -     -     -     -     -    35     - \n"+
      "    -     -     -     -     -     -     - \n"+
      "    -     -     -     -     -     -     - \n"+
      "   50     -    52     -    54     -     - \n"+
      "    -    61     -     -     -     -    66 \n"+
      "    -     -     -     -     -     -     - \n"+
      "    -     -     -     -    84     -     - \n"+
      "    -    91    92    93     -     -     - \n"+
      "";
    checkMatrix(expectMat,y);
    expectEls =
      "[(0, 1, 01), (1, 2, 12), (1, 3, 13), (2, 0, 20), (3, 5, 35), (6, 0, 50), (6, 2, 52), (6, 4, 54), (7, 1, 61), (7, 6, 66), (9, 4, 84), (10, 1, 91), (10, 2, 92), (10, 3, 93)]"+
      "";
    checkAllElements(expectEls,y);
    /////////////////////////////////////////////////////////////////////// 
    applyOps(opsy,x);           // Alter x
    expectMat =                 // check y
      "SparseMatrix\n"+
      "Rows:         11\n"+
      "Cols:         7\n"+
      "ElementCount: 14\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "    -    01     -     -     -     -     - \n"+
      "    -     -    12    13     -     -     - \n"+
      "   20     -     -     -     -     -     - \n"+
      "    -     -     -     -     -    35     - \n"+
      "    -     -     -     -     -     -     - \n"+
      "    -     -     -     -     -     -     - \n"+
      "   50     -    52     -    54     -     - \n"+
      "    -    61     -     -     -     -    66 \n"+
      "    -     -     -     -     -     -     - \n"+
      "    -     -     -     -    84     -     - \n"+
      "    -    91    92    93     -     -     - \n"+
      "";
    checkMatrix(expectMat,y);
    expectEls =
      "[(0, 1, 01), (1, 2, 12), (1, 3, 13), (2, 0, 20), (3, 5, 35), (6, 0, 50), (6, 2, 52), (6, 4, 54), (7, 1, 61), (7, 6, 66), (9, 4, 84), (10, 1, 91), (10, 2, 92), (10, 3, 93)]"+
      "";
    checkAllElements(expectEls,y);
    expectMat =                 // check x
      "SparseMatrix\n"+
      "Rows:         12\n"+
      "Cols:         10\n"+
      "ElementCount: 35\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "    -    01     -    02     -     -    05     -     -     - \n"+
      "   10     -    12    13     -     -    15     -     -     - \n"+
      "   20     -     -     -    23    24     -     -    25     - \n"+
      "    -     -     -     -     -    35     -     -     -     - \n"+
      "   30     -     -    32    33     -     -     -     -    36 \n"+
      "    -     -     -     -     -     -     -     -     -     - \n"+
      "   50     -    52    42    54     -     -     -     -     - \n"+
      "    -    61     -     -     -     -    66     -     -     - \n"+
      "   60     -     -     -    63    64     -     -     -     - \n"+
      "   70     -    81     -    84     -     -     -     -    76 \n"+
      "    -    91    92    93    83     -     -     -    96     - \n"+
      "    -     -     -     -     -    94     -     -     -     - \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 1, 01), (0, 3, 02), (0, 6, 05), (1, 0, 10), (1, 2, 12), (1, 3, 13), (1, 6, 15), (2, 0, 20), (2, 4, 23), (2, 5, 24), (2, 8, 25), (3, 5, 35), (4, 0, 30), (4, 3, 32), (4, 4, 33), (4, 9, 36), (6, 0, 50), (6, 2, 52), (6, 3, 42), (6, 4, 54), (7, 1, 61), (7, 6, 66), (8, 0, 60), (8, 4, 63), (8, 5, 64), (9, 0, 70), (9, 2, 81), (9, 4, 84), (9, 9, 76), (10, 1, 91), (10, 2, 92), (10, 3, 93), (10, 4, 83), (10, 8, 96), (11, 5, 94)]"+
      "";
    checkAllElements(expectEls,x);
    ///////////////////////////////////////////////////////////////////////////////// 
    applyOps(opsx,y);           // Alter y
    expectMat =                 // check x
      "SparseMatrix\n"+
      "Rows:         12\n"+
      "Cols:         10\n"+
      "ElementCount: 35\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "    -    01     -    02     -     -    05     -     -     - \n"+
      "   10     -    12    13     -     -    15     -     -     - \n"+
      "   20     -     -     -    23    24     -     -    25     - \n"+
      "    -     -     -     -     -    35     -     -     -     - \n"+
      "   30     -     -    32    33     -     -     -     -    36 \n"+
      "    -     -     -     -     -     -     -     -     -     - \n"+
      "   50     -    52    42    54     -     -     -     -     - \n"+
      "    -    61     -     -     -     -    66     -     -     - \n"+
      "   60     -     -     -    63    64     -     -     -     - \n"+
      "   70     -    81     -    84     -     -     -     -    76 \n"+
      "    -    91    92    93    83     -     -     -    96     - \n"+
      "    -     -     -     -     -    94     -     -     -     - \n"+
      "";
    checkMatrix(expectMat,x);
    expectEls =
      "[(0, 1, 01), (0, 3, 02), (0, 6, 05), (1, 0, 10), (1, 2, 12), (1, 3, 13), (1, 6, 15), (2, 0, 20), (2, 4, 23), (2, 5, 24), (2, 8, 25), (3, 5, 35), (4, 0, 30), (4, 3, 32), (4, 4, 33), (4, 9, 36), (6, 0, 50), (6, 2, 52), (6, 3, 42), (6, 4, 54), (7, 1, 61), (7, 6, 66), (8, 0, 60), (8, 4, 63), (8, 5, 64), (9, 0, 70), (9, 2, 81), (9, 4, 84), (9, 9, 76), (10, 1, 91), (10, 2, 92), (10, 3, 93), (10, 4, 83), (10, 8, 96), (11, 5, 94)]"+
      "";
    checkAllElements(expectEls,x);
    expectMat =                 // check y
      "SparseMatrix\n"+
      "Rows:         12\n"+
      "Cols:         10\n"+
      "ElementCount: 33\n"+
      "fillElement:  -\n"+
      "toString():\n"+
      "    -     -    01    02     -     -    05     -     -     - \n"+
      "   10     -    11    12    13     -    15     -     -     - \n"+
      "   20     -     -     -    23    24     -     -    25     - \n"+
      "    -     -     -     -     -     -     -     -     -     - \n"+
      "   30     -     -    32    33     -     -     -    35    36 \n"+
      "    -     -     -    42    43     -     -     -     -     - \n"+
      "    -     -     -     -     -     -     -     -     -     - \n"+
      "   60     -     -    52    63    64     -     -     -     - \n"+
      "   70     -    81     -     -     -     -     -     -    76 \n"+
      "    -     -     -    82    83     -     -     -    96     - \n"+
      "    -     -     -     -     -    94     -     -     -     - \n"+
      "    -     -    91    92    93     -     -     -     -     - \n"+
      "";
    checkMatrix(expectMat,y);
    expectEls =
      "[(0, 2, 01), (0, 3, 02), (0, 6, 05), (1, 0, 10), (1, 2, 11), (1, 3, 12), (1, 4, 13), (1, 6, 15), (2, 0, 20), (2, 4, 23), (2, 5, 24), (2, 8, 25), (4, 0, 30), (4, 3, 32), (4, 4, 33), (4, 8, 35), (4, 9, 36), (5, 3, 42), (5, 4, 43), (7, 0, 60), (7, 3, 52), (7, 4, 63), (7, 5, 64), (8, 0, 70), (8, 2, 81), (8, 9, 76), (9, 3, 82), (9, 4, 83), (9, 8, 96), (10, 5, 94), (11, 2, 91), (11, 3, 92), (11, 4, 93)]"+
      "";
    checkAllElements(expectEls,y);
  }

  // Addition tests
  @Test(timeout=1000) public void add_wrong_size1(){
    String expect;
    boolean thrown;
    thrown = false;
    try{
      SparseMatrix<Double> x = new SparseMatrix<Double>(3,2,0.0);
      SparseMatrix<Double> y = new SparseMatrix<Double>(1,4,0.0);
      SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Adding matrices of differing dimensions should throw an exception");
    }
  }
  @Test(timeout=1000) public void add_wrong_size2(){
    String expect;
    boolean thrown;
    thrown = false;
    try{
      SparseMatrix<Double> x = new SparseMatrix<Double>(3,4,0.0);
      SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
      SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    }catch(Exception e){
      thrown=true;
    }
    if(!thrown){
      fail("Adding matrices of differing dimensions should throw an exception");
    }
  }
  
  @Test(timeout=1000) public void add1(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 0\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add2(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    x.set(0,0,1.0);
    y.set(0,0,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  1.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  2.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  3.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add3(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    x.set(1,0,1.0);
    y.set(1,2,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  1.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   2.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 2\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  1.0   0.0   2.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add4(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    x.set(1,2,1.0);
    y.set(1,0,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   1.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  2.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 2\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  2.0   0.0   1.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add5(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    x.set(3,1,1.0);
    y.set(1,1,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   1.0   0.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 1\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   2.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 2\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   2.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   1.0   0.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add6(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    x.set(0,1,1.0);
    x.set(1,1,1.0);
    x.set(3,1,1.0);
    y.set(2,0,2.0);
    y.set(2,1,2.0);
    y.set(2,2,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 3\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   1.0   0.0 \n"+
      "  0.0   1.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   1.0   0.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 3\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  2.0   2.0   2.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 6\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   1.0   0.0 \n"+
      "  0.0   1.0   0.0 \n"+
      "  2.0   2.0   2.0 \n"+
      "  0.0   1.0   0.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add7(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    x.set(0,1,1.0);
    x.set(1,1,1.0);
    x.set(3,1,1.0);
    x.set(0,0,1.0);
    x.set(1,2,1.0);
    x.set(3,2,1.0);
    y.set(2,0,2.0);
    y.set(2,1,2.0);
    y.set(2,2,2.0);
    y.set(3,0,2.0);
    y.set(3,1,2.0);
    y.set(3,2,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 6\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  1.0   1.0   0.0 \n"+
      "  0.0   1.0   1.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   1.0   1.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 6\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         3\n"+
      "ElementCount: 10\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  1.0   1.0   0.0 \n"+
      "  0.0   1.0   1.0 \n"+
      "  2.0   2.0   2.0 \n"+
      "  2.0   3.0   3.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  // Like add7 but alter matrices after adding to ensure independence
  @Test(timeout=1000) public void add8(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(4,3,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(4,3,0.0);
    x.set(0,1,1.0);
    x.set(1,1,1.0);
    x.set(3,1,1.0);
    x.set(0,0,1.0);
    x.set(1,2,1.0);
    x.set(3,2,1.0);
    y.set(2,0,2.0);
    y.set(2,1,2.0);
    y.set(2,2,2.0);
    y.set(3,0,2.0);
    y.set(3,1,2.0);
    y.set(3,2,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    x.addRow();
    y.insertCol(1);
    z.set(3,5,8.0);
    x.setToFill(3,1);
    z.addCol();
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         3\n"+
      "ElementCount: 5\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  1.0   1.0   0.0 \n"+
      "  0.0   1.0   1.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "  0.0   0.0   1.0 \n"+
      "  0.0   0.0   0.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         4\n"+
      "ElementCount: 6\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0 \n"+
      "  2.0   0.0   2.0   2.0 \n"+
      "  2.0   0.0   2.0   2.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         4\n"+
      "Cols:         7\n"+
      "ElementCount: 11\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  1.0   1.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "  0.0   1.0   1.0   0.0   0.0   0.0   0.0 \n"+
      "  2.0   2.0   2.0   0.0   0.0   0.0   0.0 \n"+
      "  2.0   3.0   3.0   0.0   0.0   8.0   0.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add_fills_nonzero1(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(5,7,1.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(5,7,2.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 0\n"+
      "fillElement:  1.0\n"+
      "toString():\n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 0\n"+
      "fillElement:  2.0\n"+
      "toString():\n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 0\n"+
      "fillElement:  3.0\n"+
      "toString():\n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  // Checks if summing to fill element avoids creating nodes
  @Test(timeout=1000) public void add_fills_nonzero2(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(5,7,1.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(5,7,2.0);
    x.set(1,1,2.0);
    y.set(1,1,1.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    // adding should eliminate 3.0 - no elements
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 1\n"+
      "fillElement:  1.0\n"+
      "toString():\n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   2.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 1\n"+
      "fillElement:  2.0\n"+
      "toString():\n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   1.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "  2.0   2.0   2.0   2.0   2.0   2.0   2.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 0\n"+
      "fillElement:  3.0\n"+
      "toString():\n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "  3.0   3.0   3.0   3.0   3.0   3.0   3.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add_stress1(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(5,7,0.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(5,7,0.0);
    // x
    x.set(1,2,1.0);
    x.set(1,3,2.0);
    x.set(1,5,3.0);
    x.set(0,2,4.0);
    x.set(0,6,5.0);
    x.set(4,2,6.0);
    x.set(4,6,7.0);
    x.set(3,1,8.0);
    x.set(3,2,9.0);
    x.set(3,3,10.0);
    x.set(3,4,11.0);
    x.set(3,5,12.0);
    x.set(3,6,13.0);
    // y
    y.set(1,0,1.0);
    y.set(1,3,2.0);
    y.set(0,5,3.0);
    y.set(0,4,4.0);
    y.set(0,1,5.0);
    y.set(3,5,6.0);
    y.set(4,1,8.0);
    y.set(4,2,9.0);
    y.set(4,3,10.0);
    y.set(4,4,11.0);
    y.set(4,5,12.0);
    y.set(4,6,13.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 13\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   0.0   4.0   0.0   0.0   0.0   5.0 \n"+
      "  0.0   0.0   1.0   2.0   0.0   3.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "  0.0   8.0   9.0  10.0  11.0  12.0  13.0 \n"+
      "  0.0   0.0   6.0   0.0   0.0   0.0   7.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 12\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   5.0   0.0   0.0   4.0   3.0   0.0 \n"+
      "  1.0   0.0   0.0   2.0   0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0   0.0   6.0   0.0 \n"+
      "  0.0   8.0   9.0  10.0  11.0  12.0  13.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 21\n"+
      "fillElement:  0.0\n"+
      "toString():\n"+
      "  0.0   5.0   4.0   0.0   4.0   3.0   5.0 \n"+
      "  1.0   0.0   1.0   4.0   0.0   3.0   0.0 \n"+
      "  0.0   0.0   0.0   0.0   0.0   0.0   0.0 \n"+
      "  0.0   8.0   9.0  10.0  11.0  18.0  13.0 \n"+
      "  0.0   8.0  15.0  10.0  11.0  12.0  20.0 \n"+
      "";
    checkMatrix(expect,z);    
  }
  @Test(timeout=1000) public void add_stress2(){
    String expect;
    SparseMatrix<Double> x = new SparseMatrix<Double>(5,7,4.0);
    SparseMatrix<Double> y = new SparseMatrix<Double>(5,7,1.0);
    // x
    x.set(1,2,1.0);
    x.set(1,3,2.0);
    x.set(1,5,3.0);
    x.set(0,2,-4.0);
    x.set(0,6,5.0);
    x.set(4,2,6.0);
    x.set(4,6,7.0);
    x.set(3,1,8.0);
    x.set(3,2,9.0);
    x.set(3,3,10.0);
    x.set(3,4,11.0);
    x.set(3,5,12.0);
    x.set(3,6,13.0);
    // y
    y.set(1,0,-1.0);
    y.set(1,3,2.0);
    y.set(0,5,3.0);
    y.set(0,4,4.0);
    y.set(0,1,5.0);
    y.set(3,5,6.0);
    y.set(4,1,8.0);
    y.set(4,2,9.0);
    y.set(4,3,10.0);
    y.set(4,4,11.0);
    y.set(4,5,12.0);
    y.set(4,6,13.0);
    SparseMatrix<Double> z = SparseMatrix.addFast(x,y);
    expect =                    // x
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 13\n"+
      "fillElement:  4.0\n"+
      "toString():\n"+
      "  4.0   4.0  -4.0   4.0   4.0   4.0   5.0 \n"+
      "  4.0   4.0   1.0   2.0   4.0   3.0   4.0 \n"+
      "  4.0   4.0   4.0   4.0   4.0   4.0   4.0 \n"+
      "  4.0   8.0   9.0  10.0  11.0  12.0  13.0 \n"+
      "  4.0   4.0   6.0   4.0   4.0   4.0   7.0 \n"+
      "";
    checkMatrix(expect,x);    
    expect =                    // y
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 12\n"+
      "fillElement:  1.0\n"+
      "toString():\n"+
      "  1.0   5.0   1.0   1.0   4.0   3.0   1.0 \n"+
      " -1.0   1.0   1.0   2.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   1.0   1.0 \n"+
      "  1.0   1.0   1.0   1.0   1.0   6.0   1.0 \n"+
      "  1.0   8.0   9.0  10.0  11.0  12.0  13.0 \n"+
      "";
    checkMatrix(expect,y);    
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 21\n"+
      "fillElement:  5.0\n"+
      "toString():\n"+
      "  5.0   9.0  -3.0   5.0   8.0   7.0   6.0 \n"+
      "  3.0   5.0   2.0   4.0   5.0   4.0   5.0 \n"+
      "  5.0   5.0   5.0   5.0   5.0   5.0   5.0 \n"+
      "  5.0   9.0  10.0  11.0  12.0  18.0  14.0 \n"+
      "  5.0  12.0  15.0  14.0  15.0  16.0  20.0 \n"+
      "";
    checkMatrix(expect,z);    
    SparseMatrix<Double> z2 = SparseMatrix.addFast(z,z);
    expect =                    // z
      "SparseMatrix\n"+
      "Rows:         5\n"+
      "Cols:         7\n"+
      "ElementCount: 21\n"+
      "fillElement:  10.0\n"+
      "toString():\n"+
      " 10.0  18.0  -6.0  10.0  16.0  14.0  12.0 \n"+
      "  6.0  10.0   4.0   8.0  10.0   8.0  10.0 \n"+
      " 10.0  10.0  10.0  10.0  10.0  10.0  10.0 \n"+
      " 10.0  18.0  20.0  22.0  24.0  36.0  28.0 \n"+
      " 10.0  24.0  30.0  28.0  30.0  32.0  40.0 \n"+
      "";
    checkMatrix(expect,z2);    
  }

  ////////////////////////////////////////////////////////////////////////////////
  // Utility functions and classes
  
  // simple pair function
  static class Pair<F,S>{
    F first; S second;
    public Pair(F f, S s){
      this.first=f; this.second=s;
    }
    public String toString(){
      return String.format("(%s,%s)",first,second);
    }
  }

  // compare two strings and show first line that 
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

  // Check whether a matrix matches the expected result
  public static<T> void checkMatrix(String expect, SparseMatrix<T> x){
    String actual = matrixInfo(x);
    String diff = stringDiff(expect,actual);
    if(diff != null){
      String msg =
        String.format("%s\n",diff)+
        String.format("%s\n",
                      format2Columns("EXPECT MATRIX\n"+expect,
                                     "ACTUAL MATRIX\n"+actual,
                                     " | "))+
        String.format("ACTUAL debugString():%s\n",x.debugString())+
        //        String.format("==ACTUAL==\n%s",matrixInfo(x))+
        "";
      fail(msg);
    }
  }

  // Check whether a allElements() method matrix matches the expected result
  public static<T> void checkAllElements(String expect, SparseMatrix<T> x){
    List<Triple<Integer,Integer,T>> actualList = x.allElements();
    Collections.sort(actualList);
    String actual = actualList.toString();
    String diff = stringDiff(expect,actual);
    if(diff != null){
      String msg =
        "allElements() incorrect\n" +
        String.format("EXPECT: %s\n",expect)+
        String.format("ACTUAL: %s\n",actual)+
        String.format("%s",diff)+
        String.format("%s\n",matrixInfo(x)) +
        String.format("DEBUG STRING:\n%s",x.debugString())+
        "";
      fail(msg);
    }
  }

}
