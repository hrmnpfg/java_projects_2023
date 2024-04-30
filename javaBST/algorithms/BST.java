package algorithms;

public class BST <T extends Comparable<T>> implements Dictionary<T>
{
	// I messed up a bit when it comes to which functions are in which class
	// so two functionalities are implemented in the Node class and the rest in BST
	private class Node <E extends Comparable<E>>
	{
		Node<E> left, right;
		E value;
		
		private E searchRec(E element)
		{
			if (this.value == null) return null;
			int a = element.compareTo(this.value);
			
			if (a == 0)
			{
				return this.value;
			}
			else if (a < 0)
			{
				if (this.left != null)
					return this.left.searchRec(element);
			}
			else if (a > 0)
			{
				if (this.right != null)
					return this.right.searchRec(element);
			}
			return null;
		}
		
		private E findMinValue() 
	    {
	        while (this.left != null) 
	        {
	            return this.left.findMinValue();
	        }
	        return this.value;
	    }

		public String getTree() {
			StringBuilder t = new StringBuilder(this.value.toString());
			t.append(";(");
			if (this.left != null) t.append(this.left.getTree());
			t.append(");(");
			if (this.right != null) t.append(this.right.getTree());		
			t.append(')');
		    return  t.toString();
		}

	}
	
	int size;
	
	private Node<T> root;
	
	@Override
	public T search(T element)
	{
		if (root == null) throw new IllegalStateException("Drzewo jest puste");
		return root.searchRec(element);
	}
	
	@Override
	public void insert(T element) {
		if (element == null) throw new IllegalArgumentException("Nie można wstawić pustego elementu");
		size++;
		root = insertRec(root, element);
	}
	
	private Node<T> insertRec(Node<T> node, T element) {
        if (node == null) {
            node = new Node<>();
            node.value = element;
            return node;
        }

        if (element.compareTo(node.value) < 0) {
            node.left = insertRec(node.left, element);
        } else if (element.compareTo(node.value) > 0) {
            node.right = insertRec(node.right, element);
        }

        return node;
    }
	
	@Override
	public void remove(T element) {
		if (root == null) throw new IllegalStateException("Drzewo jest puste");
		
		if (root.searchRec(element) == null) return;
		
		size--;
        root = removeRecursive(root, element);
    }

    private Node<T> removeRecursive(Node<T> node, T element) {
        if (node == null) {
            return null;
        }

        if (element.compareTo(node.value) < 0) {
            node.left = removeRecursive(node.left, element);
        } else if (element.compareTo(node.value) > 0) {
            node.right = removeRecursive(node.right, element);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            node.value = node.right.findMinValue();

            node.right = removeRecursive(node.right, node.value);
        }

        return node;
    }
	
    @Override
    public T min() {
    	if (root == null) throw new IllegalStateException("Drzewo jest puste");
        return root.findMinValue();
    }

    @Override
    public T max() 
    {
    	if (root == null) throw new IllegalStateException("Drzewo jest puste");
        Node<T> a = this.root;
        while (a.right != null)
        {
        	a = a.right;
        }
    	
    	return a.value;
    }
    
    public int getSize()
    {
    	return this.size;
    }
    
    public void clear()
    {
    	size = 0;
    	root = null;
    }
    
    public String getTree()
    {
    	return root.getTree();
    }
}
