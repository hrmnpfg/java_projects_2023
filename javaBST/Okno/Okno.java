package Okno;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import algorithms.BST;

public class Okno extends JFrame
{
	private static final long serialVersionUID = 1L;

	private class Node extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		Node left;
		Node right;

		TitledBorder titledBorder;
		public Node()
		{
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			setLayout(new GridLayout(1,2));
		}
		
		public void setName(String s)
		{
			titledBorder = new TitledBorder(s);
	        setBorder(titledBorder);
		}
		
		void update()
		{
			if (left != null) 
				add(left);
			if (right != null) 
				add(right);
		}
	}
	
	private Node a;
	
	JButton dodaj    = new JButton("dodaj"); 
	JButton usuń     = new JButton("usuń");
	JButton szukaj   = new JButton("szukaj");
	JButton minimum  = new JButton("minimum");
	JButton maksimum = new JButton("maksimum");
	JButton rozmiar  = new JButton("rozmiar");
	JButton wyczyść  = new JButton("wyczyść");
	
	BST<String> tree = new BST<>();
	
	
	public Okno()
	{
		setTitle("BST");
		setSize(600, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		setLayout(new BorderLayout());
		add(buttons);
		buttons.add(dodaj); 
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(buttons, (Object) "Podaj napis do dodania", "Dodawanie napisu",
                        JOptionPane.OK_CANCEL_OPTION);
				if (input == null) return;
				tree.insert(input);
				update();
			}
		});
		buttons.add(usuń);
		usuń.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(buttons, (Object) "Podaj napis do usunięcia", "usuwanie napisu",
                        JOptionPane.OK_CANCEL_OPTION);
				if (input == null) return;
				try
				{
					tree.remove(input);
				}
				catch(IllegalStateException e)
				{
					JOptionPane.showMessageDialog(buttons, e.getMessage(), "Błąd",
	                        JOptionPane.OK_CANCEL_OPTION);
				}
				update();
			}
		});
		buttons.add(szukaj);
		szukaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(buttons, (Object) "Podaj napis do wyszukania", "Szukanie napisu",
                        JOptionPane.OK_CANCEL_OPTION);
				if (input == null) return;
				String a;
				try
				{
					a = tree.search(input);
				}
				catch(IllegalArgumentException e)
				{
					JOptionPane.showMessageDialog(buttons, e.getMessage(), "Błąd",
	                        JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				JOptionPane.showMessageDialog(buttons, "Wyszukany element: " + a, "Szukanie napisu",
                        JOptionPane.OK_CANCEL_OPTION);
			}
		});
		buttons.add(minimum);
		minimum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String a;
				try
				{
					a = tree.min();
				}
				catch(IllegalStateException e)
				{
					JOptionPane.showMessageDialog(buttons, e.getMessage(), "Błąd",
	                        JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				JOptionPane.showMessageDialog(buttons, "Najmniejszy element drzewa to " + a, "Najmniejszy element",
                        JOptionPane.OK_CANCEL_OPTION);
			}
		});
		buttons.add(maksimum);
		maksimum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String a;
				try
				{
					a = tree.max();
				}
				catch(IllegalStateException e)
				{
					JOptionPane.showMessageDialog(buttons, e.getMessage(), "Błąd",
	                        JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				JOptionPane.showMessageDialog(buttons, "Największy element drzewa to " + a, "Największy element",
                        JOptionPane.OK_CANCEL_OPTION);
				update();
			}
		});
		buttons.add(rozmiar);
		rozmiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(buttons, "rozmiar drzewa to " + tree.getSize(), "rozmiar",
                        JOptionPane.OK_CANCEL_OPTION);
				update();
			}
		});
		buttons.add(wyczyść);
		wyczyść.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (a!=null)remove(a);
				repaint();
				tree.clear();
				a = null;
			}
		});
		add(buttons, BorderLayout.NORTH);
		buttons.setVisible(true);
	}
	
	public void update()
	{
		if (a!=null)remove(a);
		a = null;
		if (tree.getSize() == 0) 
		{
			repaint();
			return;
		}
		String s = this.tree.getTree();
		String[] g = parse(s);
		buildTree(a, s);
		a.repaint();
	}
	
	public void wypełnij()
	{
		tree.insert("a");
		tree.insert("b");
		tree.insert("ó");
		tree.insert("c");
		tree.insert("6543");
		tree.insert("5");
		tree.insert("6nnnnnnnnnnnnn");
		tree.insert("87");
	}
	
	void buildTree(Node frame, String s)
	{
		if (frame == null)
		{
			a = new Node();
			add(a);
			frame = a;
		}
		String[] list = parse(s);
		frame.setName(list[0]);
		frame.left = new Node();
		if (list[1] != "")
		{
			buildTree(frame.left, list[1]);
		}
		frame.right = new Node();
		if (list[2] != "")
		{
			buildTree(frame.right, list[2]);
		}
		frame.update();
	}
	
	String[] parse(String input)
	{
		String[] result = new String[3];

        int depth = 0;
        int index = 0;
        StringBuilder currentSubstring = new StringBuilder();
		for (char c : input.toCharArray()) {
            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
            } else if (c == ';') {
                if (depth == 0) {
                    result[index] = currentSubstring.toString().trim();
                    index++;
                    currentSubstring.setLength(0);
                    continue;
                }
            }

            currentSubstring.append(c);
        }

        if (currentSubstring.length() > 0) {
            result[2] = currentSubstring.toString().trim();
        }
        result[1] = result[1].substring(1, result[1].length() - 1);
        result[2] = result[2].substring(1, result[2].length() - 1);
        return result;
	}
}