package prezentacja;

import java.awt.Color;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JList;

public class MiesiacList extends JList<String>
{
	private static final long serialVersionUID = 1045545719477279909L;
	
	MiesiacList(AbstractListModel<String> list) {
        super(list);
        setCellRenderer(new Renderer());
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}