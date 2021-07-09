package mtg.judge.ipgtree.POJO;

import java.util.ArrayList;

public class TreeIterator<T> {

    private Tree<T> tree;
    private ArrayList<Integer> index;

    public TreeIterator(Tree<T> tree) {
        this.tree = tree.getRoot();
    }

    public Tree<T> next()
    {
        if(!tree.isLeaf())
        {
            tree = tree.getChild(0);
            return tree;
        }
        int index;
        while(tree.getParent() != null) {
            index = tree.getParent().getChildren().indexOf(tree);
            if (tree.getParent().existChild(index + 1)) {
                tree = tree.getParent().getChild(index + 1);
                return tree;
            }
            tree = tree.getParent();
        }
        return null;
    }

    public void reset()
    {
        tree = tree.getRoot();
    }
}
