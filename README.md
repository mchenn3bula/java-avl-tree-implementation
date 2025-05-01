# Java AVL Tree Implementation 🌳

## Overview 📊
This Java project implements a self-balancing AVL (Adelson-Velsky and Landis) Tree data structure. The implementation includes visualization capabilities that display both horizontal and vertical tree representations in the console.

## Project Features 🌟
- **Custom AVL Tree Implementation:** Extends the base TreeMap to create a self-balancing binary search tree
- **Auto-Balancing:** Implements rotation algorithms to maintain tree balance after insertions and deletions
- **Visualization Tools:** Features two different methods for tree visualization:
  - Horizontal tree display with branches
  - Vertical tree display with connections

![AVL Tree Visualization Example](tree_visualization.png)

## Sample Inputs 🔄
The program tests the AVL tree implementation with various input strings:
- "DBACEFG"
- "DACBEFMLGHJK"
- "JABCDEFISN"
- "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
- "HELLO, HOW IS YOUR DAY?"
- "SUMMER"

## Technical Implementation ⚙️
### Key Components:
- **MyAVLTreeMap.java:** The core AVL tree implementation with:
  - Height tracking and recomputation
  - Balance checking
  - Tree restructuring for rebalancing
  - Multiple visualization methods
- **ProgProject4.java:** Driver class with test cases

### AVL Tree Operations:
- **Height Management:**
  ```java
  protected int height(Position<Entry<K,V>> p) {
    return tree.getAux(p);
  }
  
  protected void recomputeHeight(Position<Entry<K,V>> p) {
    tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));
  }
  ```

- **Balance Checking:**
  ```java
  protected boolean isBalanced(Position<Entry<K,V>> p) {
    return Math.abs(height(left(p)) - height(right(p))) <= 1;
  }
  ```

- **Rebalancing Algorithm:**
  ```java
  protected void rebalance(Position<Entry<K,V>> p) {
    int oldHeight, newHeight;
    do {
      oldHeight = height(p);
      if (!isBalanced(p)) {
        p = restructure(tallerChild(tallerChild(p)));
        recomputeHeight(left(p));
        recomputeHeight(right(p));
      }
      recomputeHeight(p);
      newHeight = height(p);
      p = parent(p);
    } while (oldHeight != newHeight && p != null);
  }
  ```

## Visualization Methods 🖥️
The project implements two creative ways to visualize AVL trees:

### Horizontal Tree Display
Uses a recursive approach with a custom "Trunk" class to represent branches:
```
Input of DBACEFG
Horizontal Tree:
    +---G
----F
    |
+---E
|    
D----C
|    
+---B
    |    
    +---A
```

### Vertical Tree Display
Creates a 2D array to represent the tree structure with proper spacing:
```
    D    
   / \   
  B   F   
 /   / \  
A   E   G 
           
```

## Skills Demonstrated 💪
- Advanced data structure implementation
- Self-balancing binary search tree algorithms
- Recursion and tree traversal
- Creative visualization of complex data structures
- Object-oriented design
- Depth understanding of time and space complexity
