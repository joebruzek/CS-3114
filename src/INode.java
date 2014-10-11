
// -------------------------------------------------------------------------
/**
 *  implements each inner node in the tree
 *
 *  @author mcstewart
 *  @version Oct 10, 2014
 */
public class INode extends TTNode<MemHandle>
{
    // ----------------------------------------------------------
    /**
     * New inner node object
     */
    public INode()
    {
        this.keys = new MemHandle[3];
    }
    // ----------------------------------------------------------
    /**
     * Create a new INode object with a left value.
     * @param data
     */
    public INode(MemHandle data)
    {
        this.keys = new MemHandle[3];
        this.keys[0] = data;
        this.recs = 1;
    }



    @Override
    public int search(MemHandle k)
    {
        for (int i = 0; i < this.numRecs(); i++)
        {
            if (this.getKey(i) == null) {
                return -1;
            }
            if( this.getKey(i).compareTo(k) == 0) return i;
        }
        return -1;
    }

    @Override
    public TTNode<MemHandle> split()
    {
        MemHandle mid;
        MemHandle max;
        MemHandle min;
        min = this.getKey(0);
        mid = this.getKey(1);
        max = this.getKey(2);
        INode newNode = new INode(mid);
        INode rightSplit = new INode(max);

        this.setKey(1, null);
        this.setKey(2, null);
        return newNode;
    }


    @Override
    public boolean isLeaf()
    {
        return false;
    }

    public void insert(MemHandle k)
    {
        int index = 0;
        while (index < this.numRecs() && this.getKey(index).compareTo(k) < 0)
            ++index;

        // move space for the new key
        for (int i = this.numRecs() - 1; i >= index; --i) {
            this.setKey(i + 1, this.getKey(i));
        }

        this.setKey(index, k);
        this.recs++;
    }

    public void promote(TTNode<MemHandle> node, TTNode<MemHandle> newNode)
    {
//        this.insert((MemHandle) node.getKey(0));
//        if (this.getChild(0) == node)
//        {
//            if (this.getChild(1) != null)
//            {
//                TTNode<MemHandle> temp = this.getChild(1);
//                this.setChild(1, newNode);
//                promote(this.getChild(1), temp);
//
//            }
//        }
//        else if (this.getChild(1) == node)
//        {
//
//        }
//        else
//        {
//
//        }
        int index = 0;
        while (index < this.children.length && this.getChild(index).getKey(index).compareTo(node.getKey(0)) != 0)
            index++;
        for (int i = this.children.length - 1; i > index; i--)
        {
            this.setChild(i, this.getChild(i - 1));
        }
        this.setChild(index + 1, newNode);
        this.insert(newNode.getKey(0));
    }

}
