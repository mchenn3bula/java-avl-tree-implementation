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
