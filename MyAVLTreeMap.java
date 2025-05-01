package project3;


import net.datastructures.*;
import java.util.Comparator;



public class MyAVLTreeMap<K,V> extends TreeMap<K,V> {
	
  /** Constructs an empty map using the natural ordering of keys. */
  public MyAVLTreeMap() { super(); }

  /**
   * Constructs an empty map using the given comparator to order keys.
   * @param comp comparator defining the order of keys in the map
   */
  public MyAVLTreeMap(Comparator<K> comp) { super(comp); }

  /** Returns the height of the given tree position. */
  protected int height(Position<Entry<K,V>> p) {
    return tree.getAux(p);
  }

  /** Recomputes the height of the given position based on its children's heights. */
  protected void recomputeHeight(Position<Entry<K,V>> p) {
    tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
  }

  /** Returns whether a position has balance factor between -1 and 1 inclusive. */
  protected boolean isBalanced(Position<Entry<K,V>> p) {
    return Math.abs(height(left(p)) - height(right(p))) <= 1;
  }

  /** Returns a child of p with height no smaller than that of the other child. */
  protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p) {
    if (height(left(p)) > height(right(p))) return left(p);     // clear winner
    if (height(left(p)) < height(right(p))) return right(p);    // clear winner
    // equal height children; break tie while matching parent's orientation
    if (isRoot(p)) return left(p);                 // choice is irrelevant
    if (p == left(parent(p))) return left(p);      // return aligned child
    else return right(p);
  }

  /**
   * Utility used to rebalance after an insert or removal operation. This traverses the
   * path upward from p, performing a trinode restructuring when imbalance is found,
   * continuing until balance is restored.
   */
  protected void rebalance(Position<Entry<K,V>> p) {
    int oldHeight, newHeight;
    do {
      oldHeight = height(p);                       // not yet recalculated if internal
      if (!isBalanced(p)) {                        // imbalance detected
        // perform trinode restructuring, setting p to resulting root,
        // and recompute new local heights after the restructuring
        p = restructure(tallerChild(tallerChild(p)));
        recomputeHeight(left(p));
        recomputeHeight(right(p));
      }
      recomputeHeight(p);
      newHeight = height(p);
      p = parent(p);
    } while (oldHeight != newHeight && p != null);
  }

  /** Overrides the TreeMap rebalancing hook that is called after an insertion. */
  @Override
  protected void rebalanceInsert(Position<Entry<K,V>> p) {
    rebalance(p);
  }

  /** Overrides the TreeMap rebalancing hook that is called after a deletion. */
  @Override
  protected void rebalanceDelete(Position<Entry<K,V>> p) {
    if (!isRoot(p))
      rebalance(parent(p));
  }

  /** Ensure that current tree structure is valid AVL (for debug use only). */
  private boolean sanityCheck() {
    for (Position<Entry<K,V>> p : tree.positions()) {
      if (isInternal(p)) {
        if (p.getElement() == null)
          System.out.println("VIOLATION: Internal node has null entry");
        else if (height(p) != 1 + Math.max(height(left(p)), height(right(p)))) {
          System.out.println("VIOLATION: AVL unbalanced node with key " + p.getElement().getKey());
          dump();
          return false;
        }
      }
    }
    return true;
  }
  
  private class Trunk {
      private Trunk prev;
      private String s;
   
      Trunk(Trunk prev, String s) {
          this.prev = prev;
          this.s = s;
      }
  }
  
  public void showTrunks(Trunk p)
  {
	  //base case
      if (p == null) {
          return;
      }
      //print the previous trunk of the tree and then the current tree
      showTrunks(p.prev);
      System.out.print(p.s);
  }

  public void printTree(Position<Entry<K,V>> p, Trunk prev, boolean isLeft)
  {
	  //base case
      if (p.getElement() == null) {
          return;
      }
      //create the indentation for the subtrees
      String prev_str = "     ";
      Trunk trunk = new Trunk(prev, prev_str);
      
      //right tree
      printTree(tree.right(p), trunk, true);
      
      //the case for the root node
      if (prev == null) {
          trunk.s = "----";
      }
      //the case for the left node
      else if (isLeft) {
          trunk.s = "+---";
          prev_str = "    |";
      }
      //the case for the right node
      else {
          trunk.s = "+---";
          prev.s = prev_str;
      }
      //print trunk
      showTrunks(trunk);
      //print data
      System.out.println(p.getElement().getKey());
      //if the previous "trunk" or string is empty, then draw the horizontal line
      if (prev != null) {
          prev.s = prev_str;
      }
      trunk.s = "    |";
      //left tree
      printTree(tree.left(p), trunk, false);
  }

  private String[][] createBoard() {
	  int height = height(tree.root());
	  int depth =  4 * height;
	  int weidth = (int) Math.pow(2, height - 1);
	  for (int i = height - 1; i >=0; i--) {
		  weidth += (int) Math.pow(2, i);
	  }
	  String[][] board = new String[depth][weidth];
	  return board;
  }
  
  private void fillBoard(Position<Entry<K,V>> p, int level, int loc, String[][] b) {
		  b[level][loc - 1] = (String) p.getElement().getKey();
		  if (tree.left(p).getElement() != null) {
			  b[level + 1][loc - 1] = "|";
			  b[level + 2][loc - 1] = "+";
			  for (int i = loc - 1; i >= loc - getLoc(p); i--) {
				  try {
					  b[level + 2][i - 1] = "-"; 
				  } catch (ArrayIndexOutOfBoundsException ex) {}
				  
			  }
			  fillBoard(tree.left(p),level + 4,loc - getLoc(p),b);
			  b[level + 3][loc - getLoc(p) - 1] = "|";
		  }
		  if (tree.right(p).getElement() != null) {
			  b[level + 1][loc - 1] = "|";
			  b[level + 2][loc - 1] = "+";
			  for (int i = loc + 1; i <= loc + getLoc(p); i++) {
				  b[level + 2][i - 1] = "-";
			  }
			  fillBoard(tree.right(p),level + 4,loc + getLoc(p),b);
			  b[level + 3][loc + getLoc(p) - 1] = "|";
		  }
  }
  
  private int getLoc(Position<Entry<K,V>> p) {

	  return (int) Math.pow(2,height(p) - 2) + height(p) - 2;
  }
  
  // driver method
  public void printTree() {
	  // Put your code to print AVL tree here
	  
	  //Horizontal Print
	  System.out.println("Horizontal Tree:");
	  printTree(tree.root(), null, false);
	  System.out.println();
	  
	  //Vertical Print
	  System.out.println("Vertical Tree:");
	  String[][] board = createBoard();
	  int height = height(tree.root());
	  int weidth = (int) Math.pow(2, height - 1);
	  for (int i = height - 1; i >=0; i--) {
		  weidth += (int) Math.pow(2, i);
	  }
	  fillBoard(tree.root(), 0, ((int)weidth + 1) / 2,board);
	  for (int i = 0; i < board.length; i++) {
		  for (int j = 0; j < board[i].length; j++) {
			  if (board[i][j] != null) {
				  System.out.print(board[i][j]);
			  } else {
				  System.out.print(" ");
			  }
		  }
		  System.out.println("");
	  }
  }
}

