/**
 * a 2-3+ tree. This is gonna store a lot of stuff.
 *
 * @author jbruzek, sucram20
 * @version 2014.10.14
 */
public class Tree {
    private TTNode root;
    private int depth;

    /**
     * initialize the tree
     */
    public Tree() {
        root = null;
        depth = 1;
    }

    /**
     * add a value into the tree calls the recursive insert method
     *
     * @param k
     *            the KVPair to insert
     * @return true is insert was successful
     */
    public boolean insert(KVPair k) {
        // if it's already in the tree
        if (search(root, k)) {
            return false;
        }

        if (root == null) {
            root = new LNode(k);
            return true;
        }
        else if (root.isLeaf()) {
            ((LNode) root).insert(k);
            rootFullCheck();
            return true;
        }
        else {
            add(root, k);
            return true;
        }
    }

    /**
     * insert a value into the tree
     *
     * @param k
     *            the KVPair to insert
     * @param node
     *            the current node in the tree
     * @return TTNode the parent of the inserted node
     */
    public TTNode add(TTNode node, KVPair k) {
        if (root == null) {
            root = new LNode(k);
            return node;
        }
        if (root.isLeaf()) {
            ((LNode) root).insert(k);
            rootFullCheck();
            return node;
        }
        // base case, we never go below the lowest level of INodes
        if (node.getChild(0).isLeaf()) {
            int i;
            if (k.compareTo(node.getKeyV(0)) < 0) {
                i = 0;
            }
            else if (node.getKeyV(1) == null
                    || k.compareTo(node.getKeyV(1)) < 0) {
                i = 1;
            }
            else {
                i = 2;
            }
            node.getChild(i).insert(k);
        }
        else {
            if (k.compareTo(node.getKeyV(0)) < 0) {
                this.add(node.getChild(0), k);
            }
            else if (node.getKeyV(1) == null
                    || k.compareTo(node.getKeyV(1)) < 0) {
                this.add(node.getChild(1), k);
            }
            else {
                this.add(node.getChild(2), k);
            }
        }

        // check to see if we filled any node
        for (int i = 0; i < 4; i++) {
            if (node.getChild(i) != null && node.getChild(i).isFull()) {
                splitUp(node, i);
            }
        }

        return null;
    }

    /**
     * split up a node
     *
     * @param node
     *            the node that is above the splitting node
     * @param index
     *            the index of the child to split
     */
    public void splitUp(TTNode node, int index) {
        TTNode child = node.getChild(index);
        if (node == root) {
            if (child.isLeaf()) {
                LNode temp = (LNode) child.split();
                for (int i = 3; i > index + 1; i--) {
                    root.setChild(i, root.getChild(i - 1));
                }
                root.insert(temp.getKeyV(0));
                //for merging purposes
                if (temp.getKeyV(0) == temp.getKeyV(1))
                {
                    temp.setKey(1, null);
                    temp.setRecs(1);
                }
                node.setChild(index + 1, temp);
            }
            else {
                INode temp = (INode) child.split();
                root.insert(temp.getKeyV(0));
                ((INode) root).promote(child, temp.getChild(1));
            }

            rootFullCheck();
        }
        else {
            if (child.isLeaf()) {
                LNode temp = (LNode) child.split();
                for (int i = 3; i > index + 1; i--) {
                    node.setChild(i, node.getChild(i - 1));
                }
                node.insert(temp.getKeyV(0));
                //For merging purposes
                if (temp.getKeyV(0) == temp.getKeyV(1))
                {
                    temp.setKey(1, null);
                    temp.setRecs(1);
                }
                node.setChild(index + 1, temp);

            }
            else {
                INode temp = (INode) child.split();
                node.insert(temp.getKeyV(0));
                ((INode) node).promote(child, temp.getChild(1));
            }
        }
    }

    /**
     * check if the root is full and split it
     */
    public void rootFullCheck() {
        if (root.isFull()) {
            if (root.isLeaf()) {
                INode newNode = new INode(root.getKeyV(1));
                TTNode temp = root.split();
                newNode.setChild(0, root);
                newNode.setChild(1, temp);
                root = newNode;
                depth++;
            }
            else {
                INode temp = (INode) root.split();
                temp.promote(root, temp.getChild(1));
                root = temp;
                depth++;
            }
        }
    }

    /**
     * search the tree recursively to see if it contains a value
     *
     * @param node
     *            the root to search from
     * @param k
     *            the key to find
     * @return k exists in the tree
     */
    public boolean search(TTNode node, KVPair k) {
        if (node == null || k.key() == null) {
            return false;
        }
        if (root.getKeyV(0) == null) {
            return false;
        }
        if (k.compareTo(node.getKeyV(0)) == 0) {
            return true;
        }
        if (node.getKeyV(1) != null && k.compareTo(node.getKeyV(1)) == 0) {
            return true;
        }
        if (k.compareTo(node.getKeyV(0)) < 0) {
            return search(node.getChild(0), k);
        }
        else if (node.getKeyV(1) == null) {
            return search(node.getChild(1), k);
        }
        else if (k.compareTo(node.getKeyV(1)) < 0) {
            return (search(node.getChild(1), k));
        }
        else {
            return (search(node.getChild(2), k));
        }
    }
    /**
     * Deletes a KVPair from the tree
     * @param node the root
     * @param k the KVPair
     */
    public void delete(TTNode node, KVPair k)
    {
        if (!search(node, k)) {
            return;
        }
        if (root.getKeyV(0) == null) {
            return;
        }
        if (root.isLeaf())
        {
            if (root.getKeyV(0).compareTo(k) == 0)
            {
                if (root.getKeyV(1) == null)
                {
                    ((LNode)root).setKey(0, null);
                    root.setRecs(0);
                    root = null;
                }
                else
                {
                    ((LNode)root).setKey(0, root.getKeyV(1));
                    ((LNode)root).setKey(1, null);
                    root.setRecs(1);
                }
            }
            else if (root.getKeyV(1).compareTo(k) == 0)
            {
                ((LNode)root).setKey(1, null);
                root.setRecs(1);
            }
            return;
        }

        if (node.isLeaf())
        {
            if (node.getKeyV(0).compareTo(k) == 0 && node.getKeyV(1) != null)
            {
                //If the node containing the deleted value has 2 elements
                KVPair before = node.getKeyV(0);
                KVPair after = node.getKeyV(1);
                ((LNode) node).setKey(0, node.getKeyV(1));
                ((LNode) node).setKey(1, null);
                ((LNode) node).setRecs(1);
                changeINodes(root, before, after);
                return;
            }
            else if (node.getKeyV(1) != null
                    && node.getKeyV(1).compareTo(k) == 0)
            {
                ((LNode) node).setKey(1, null);
                ((LNode) node).setRecs(1);
                return;
            }


        }
        if ((!node.isLeaf()) && node.getChild(0).isLeaf())
        {
            if (node.getChild(0).getKeyV(0).compareTo(k) == 0)
            {
                LNode child = (LNode) node.getChild(0);
                borrow(node, child, k);
                return;

            }
            else if (node.getChild(1).getKeyV(0).compareTo(k) == 0)
            {
                LNode child = (LNode) node.getChild(1);
                borrow(node, child, k);
                return;
            }
            else if (node.getChild(2) != null &&
                node.getChild(2).getKeyV(0).compareTo(k) == 0)
            {
                LNode child = (LNode) node.getChild(2);
                borrow(node, child, k);
                return;
            }

        }
        if (k.compareTo(node.getKeyV(0)) < 0) {
            delete(node.getChild(0), k);
        }
        else if (node.getKeyV(1) == null) {
            delete(node.getChild(1), k);
        }
        else if (k.compareTo(node.getKeyV(1)) < 0) {
            delete(node.getChild(1), k);
        }
        else {
            delete(node.getChild(2), k);
        }
    }

    // ----------------------------------------------------------
    /**
     * Changes the parent nodes after deletion
     * @param node the current node
     * @param before the value being changed
     * @param after the new value
     */
    public void changeINodes(TTNode node, KVPair before, KVPair after)
    {
        if (node.isLeaf()) {
            return;
        }
        if (node.getKeyV(0) != null && node.getKeyV(0).compareTo(before) == 0)
        {
            ((INode) node).setKey(0, after);
            changeINodes(node, before, after);
        }
        if (node.getKeyV(1) != null && node.getKeyV(1).compareTo(before) == 0)
        {
            ((INode) node).setKey(1, after);
            changeINodes(node, before, after);
        }
        if (before.compareTo(node.getKeyV(0)) < 0) {
            changeINodes(node.getChild(0), before, after);
        }
        else if (node.getKeyV(1) == null) {
            changeINodes(node.getChild(1), before, after);
        }
        else if (before.compareTo(node.getKeyV(1)) < 0) {
            changeINodes(node.getChild(1), before, after);
        }
        else {
            changeINodes(node.getChild(2), before, after);
        }

    }

    /**
     * Borrows a value from the next or previous node.
     * @param node the node
     * @param child leaf node with the deleted value
     * @param k the deleted pair
     */
    public void borrow(TTNode node, LNode child, KVPair k)
    {
        if (node.getChild(0) == child.previous() &&
            child.previous().numRecs() == 2)
        {
            KVPair before = child.getKeyV(0);
            KVPair after = child.previous().getKeyV(1);
            child.setKey(0, after);
            child.previous().setKey(1, null);
            child.previous().setRecs(1);
            changeINodes(root, before, after);
            return;
        }
        else if(node.getChild(1) == child.previous() &&
            child.previous().numRecs() == 2)
        {
            KVPair before = child.getKeyV(0);
            KVPair after = child.previous().getKeyV(1);
            child.setKey(0, after);
            child.previous().setKey(1, null);
            child.previous().setRecs(1);
            changeINodes(root, before, after);
            return;
        }
        else if(node.getChild(1) == child.next() &&
            child.next().numRecs() == 2)
        {
            KVPair before = child.getKeyV(0);
            KVPair after = child.next().getKeyV(0);
            KVPair after2 = child.next().getKeyV(1);
            child.setKey(0, after);
            child.next().setKey(0, after2);
            child.next().setKey(1, null);
            child.next().setRecs(1);
            changeINodes(root, after, after2);
            changeINodes(root, before, after);
            return;
        }
        else if(child.next() != null &&
            node.getChild(2) == child.next() && child.next().numRecs() == 2)
        {
            KVPair before = child.getKeyV(0);
            KVPair after = child.next().getKeyV(0);
            KVPair after2 = child.next().getKeyV(1);
            child.setKey(0, after);
            child.next().setKey(0, after2);
            child.next().setKey(1, null);
            child.next().setRecs(1);
            changeINodes(root, after, after2);
            changeINodes(root, before, after);
            return;
        }
        else if(node.numRecs() == 2)
        {
            deleteThirdChild(node, k);
        }
        else
        {
            //must merge
            if (node.getChild(0) == child)
            {
                node.setChild(0, null);
                ((INode) node).setKey(0, null);
                KVPair save = node.getChild(1).getKeyV(0);
                if (root == node)
                {
                    root = node.getChild(1);
                    return;
                }
                merge(root, save);
            }
            else
            {
                node.setChild(1, null);
                ((INode) node).setKey(0, null);
                KVPair save = node.getChild(0).getKeyV(0);
                if (root == node)
                {
                    root = node.getChild(0);
                    return;
                }
                merge(root, save);
            }
            return;
        }
    }

    /**
     * Deletes one of three children if borrowing is not possible
     * @param node the parent node of the removed node
     * @param k the deleted value
     */
    public void deleteThirdChild(TTNode node, KVPair k)
    {
        if (node.getChild(0).getKeyV(0).compareTo(k) == 0 &&
            node.getChild(0).getKeyV(1) == null)
        {
            KVPair before = node.getChild(0).getKeyV(0);
            KVPair after = node.getChild(1).getKeyV(0);
            ((INode) node).setKey(0, node.getChild(2).getKeyV(0));
            ((INode) node).setKey(1, null);
            ((LNode) node.getChild(1)).setPrevious(
                    ((LNode) node.getChild(0)).previous());
            if (((LNode) node.getChild(0)).previous() != null) {
                ((LNode) node.getChild(0)).previous().setNext(
                        ((LNode) node.getChild(1)));
            }
            node.setChild(0, node.getChild(1));
            node.setChild(1, node.getChild(2));
            node.setChild(2, null);
            node.setRecs(1);
            changeINodes(root, before, after);
            return;
        }
        else if (node.getChild(1).getKeyV(0).compareTo(k) == 0 &&
            node.getChild(1).getKeyV(1) == null)
        {
            KVPair before = node.getChild(1).getKeyV(0);
            KVPair after = node.getChild(2).getKeyV(0);
            ((INode) node).setKey(0, node.getChild(2).getKeyV(0));
            ((INode) node).setKey(1, null);
            ((LNode) node.getChild(2)).setPrevious(
                    ((LNode) node.getChild(1)).previous());
            ((LNode) node.getChild(1)).previous().setNext(
                    ((LNode) node.getChild(2)));
            node.setChild(1, node.getChild(2));
            node.setChild(2, null);
            node.setRecs(1);
            changeINodes(root, before, after);
            return;
        }
        else if (node.getChild(2).getKeyV(0).compareTo(k) == 0 &&
            node.getChild(2).getKeyV(1) == null)
        {
            KVPair before = node.getChild(2).getKeyV(0);
            ((INode) node).setKey(1, null);
            ((LNode) node.getChild(1)).setNext(
                    ((LNode) node.getChild(2)).next());
            if (((LNode) node.getChild(2)).next() != null) {
                ((LNode) node.getChild(2)).next().setPrevious(
                        ((LNode) node.getChild(1)));
            }
            node.setChild(2, null);
            node.setRecs(1);
            changeINodes(root, before, null);
            return;
        }
    }

    /**
     * Merges node on delete
     * @param node the current node
     * @param save the remaining value from the node being merged
     */
    public void merge(TTNode node, KVPair save)
    {
        if (node.getChild(0).getKeyV(0) == null)
        {
            TTNode grandParent = node;
            //save the demoted value
            KVPair save2 = grandParent.getKeyV(0);
            ((INode)node).setKey(0, grandParent.getKeyV(1));
            ((INode)node).setKey(1, null);
            node.setRecs(1);
            TTNode parent = node.getChild(1);
            //change the children
            node.setChild(0, node.getChild(1));
            node.setChild(1, node.getChild(2));
            node.setChild(2, null);
            if (node.getKeyV(0) == null)
            {
                if (root == node)
                {
                    root = parent;
                }
                else
                {
                    node.setChild(0, parent.getChild(0));
                    node.setChild(1, parent.getChild(1));
                    node.setChild(2, parent.getChild(2));
                    ((INode) node).setKey(0, parent.getKeyV(0));
                    ((INode) node).setKey(1, parent.getKeyV(1));
                    node.setRecs(parent.numRecs());
                }
            }
            add(root, save);
            add(root, save2);

            return;
        }
        else if (node.getChild(1).getKeyV(0) == null &&
            node.getChild(2) == null)
        {
            //save the demoted value
            ((INode)node).setKey(0, null);
            TTNode parent = node.getChild(0);
            //change the children
            node.setChild(1, null);
            node.setRecs(1);
            if (node.getKeyV(0) == null)
            {
                if (root == node)
                {
                    root = parent;
                }
                else
                {
                    node.setChild(0, parent.getChild(0));
                    node.setChild(1, parent.getChild(1));
                    node.setChild(2, parent.getChild(2));
                    ((INode) node).setKey(0, parent.getKeyV(0));
                    ((INode) node).setKey(1, parent.getKeyV(1));
                    node.setRecs(parent.numRecs());
                }
            }
            add(root, save);
            add(root, save);

            return;
        }
        else if (node.getChild(1).getKeyV(0) == null)
        {
            TTNode grandParent = node;
            //save the demoted value
            //KVPair save2 = grandParent.getKeyV(0);
            ((INode)node).setKey(0, grandParent.getKeyV(1));
            ((INode)node).setKey(1, null);
            node.setRecs(1);
            TTNode parent = node.getChild(0);
            //change the children
            node.setChild(1, node.getChild(2));
            node.setChild(2, null);
            //TTNode child = parent.getChild(0);
            if (node.getKeyV(0) == null)
            {
                if (root == node)
                {
                    root = parent;
                }
                else
                {
                    node.setChild(0, parent.getChild(0));
                    node.setChild(1, parent.getChild(1));
                    node.setChild(2, parent.getChild(2));
                    ((INode) node).setKey(0, parent.getKeyV(0));
                    ((INode) node).setKey(1, parent.getKeyV(1));
                    node.setRecs(parent.numRecs());
                }
            }
            add(root, save);
            add(root, save);

            return;
        }
        else if (node.getChild(2) != null &&
            node.getChild(2).getKeyV(0) == null)
        {
            //save the demoted value
            ((INode)node).setKey(1, null);
            node.setRecs(1);
            TTNode parent = node.getChild(1);
            //change the children
            node.setChild(2, null);
            //TTNode child = parent.getChild(0);
            if (node.getKeyV(0) == null)
            {
                if (root == node)
                {
                    root = parent;
                }
                else
                {
                    node.setChild(0, parent.getChild(0));
                    node.setChild(1, parent.getChild(1));
                    node.setChild(2, parent.getChild(2));
                    ((INode) node).setKey(0, parent.getKeyV(0));
                    ((INode) node).setKey(1, parent.getKeyV(1));
                    node.setRecs(parent.numRecs());
                }
            }
            add(root, save);
            add(root, save);

            return;
        }
        if (save.compareTo(node.getKeyV(0)) < 0) {
            merge(node.getChild(0), save);
        }
        else if (node.getKeyV(1) == null) {
            merge(node.getChild(1), save);
        }
        else if (save.compareTo(node.getKeyV(1)) < 0) {
            merge(node.getChild(1), save);
        }
        else {
            merge(node.getChild(2), save);
        }
    }

    /**
     * print the tree
     */
    public void printTree() {
        System.out.println("Printing 2-3 tree:");
        print(root, 0);
    }

    /**
     * recursive print method
     *
     * @param node
     *            the node you're on
     * @param d
     *            the depth of the node
     */
    public void print(TTNode node, int d) {
        // base case
        if (node == null) {
            return;
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < d * 2; i++) {
            s.append(" ");
        }
        String space = "";
        for (int i = 0; i < node.numRecs(); i++) {
            if (node.getKeyV(i) != null) {
                s.append(space);
                s.append(node.getKeyV(i).key().getPosition());
                s.append(" ");
                s.append(node.getKeyV(i).value().getPosition());
                space = " ";
            }
        }

        System.out.println(s.toString());

        print(node.getChild(0), d + 1);
        print(node.getChild(1), d + 1);
        print(node.getChild(2), d + 1);
    }

    /**
     * Gets the root node of the tree
     *
     * @return the root node
     */
    public TTNode getRoot() {
        return this.root;
    }

    /**
     * Gets the height of the tree
     *
     * @return returns the height
     */
    public int depth() {
        return depth;
    }

    /**
     * go to the first LNode in the tree
     *
     * @param node
     *            the root for this subtree
     * @return the first LNode, null if tree is empty
     */
    public LNode getToFirst(TTNode node) {
        // base case
        if (node == null) {
            return null;
        }
        if (node.isLeaf()) {
            return (LNode) node;
        }

        return getToFirst(node.getChild(0));
    }

    /**
     * get the number of KVPairs in the leaves of the tree
     *
     * @return the number of KVPairs in the tree
     */
    public int numElements() {
        if (root == null) {
            return 0;
        }
        int results = 0;
        LNode temp = getToFirst(root);

        do {
            results += temp.numRecs();
            temp = temp.next();
        } while (temp != null);

        return results;
    }

    /**
     * find all the values in the tree that match a key
     *
     * @param k
     *            the MemHandle key
     * @return a memhandle array
     */
    public MemHandle[] find(MemHandle k) {
        MemHandle[] results = new MemHandle[numElements()];
        int nums = 0;

        LNode temp = getToFirst(root);

        do {
            for (int i = 0; i < temp.numRecs(); i++) {
                if (temp.getKeyV(i).key().compareTo(k) == 0) {
                    results[nums] = temp.getKeyV(i).value();
                    nums++;
                }
            }
            temp = temp.next();
        } while (temp != null);

        // make the array the size of the results returned
        MemHandle[] finals = new MemHandle[nums];
        for (int i = 0; i < nums; i++) {
            finals[i] = results[i];
        }

        return finals;
    }

    /**
     * find all the keys in the tree that match a value
     *
     * @param k
     *            the MemHandle key
     * @return a memhandle array
     */
    public MemHandle[] inverseFind(MemHandle k) {
        MemHandle[] results = new MemHandle[numElements()];
        int nums = 0;

        LNode temp = getToFirst(root);

        do {
            for (int i = 0; i < temp.numRecs(); i++) {
                if (temp.getKeyV(i).value().compareTo(k) == 0) {
                    results[nums] = temp.getKeyV(i).key();
                    nums++;
                }
            }
            temp = temp.next();
        } while (temp != null);

        // make the array the size of the results returned
        MemHandle[] finals = new MemHandle[nums];
        for (int i = 0; i < nums; i++) {
            finals[i] = results[i];
        }

        return finals;
    }

    /**
     * set root for testing purposes
     *
     * @param r
     *            the new root
     */
    public void setRoot(TTNode r) {
        root = r;
    }

    /**
     * find if there is any instance of a handle left in the tree
     * @param m the MemHandle to search
     * @return exists or nah
     */
    public boolean exists(MemHandle m) {
        LNode temp = getToFirst(root);
        if (temp == null) {
            return false;
        }

        do {
            for (int i = 0; i < temp.numRecs(); i++) {
                if (temp.getKeyV(i).key().compareTo(m) == 0
                        || temp.getKeyV(i).value().compareTo(m) == 0) {
                    return true;
                }
            }
            temp = temp.next();
        } while (temp != null);

        return false;
    }

}
