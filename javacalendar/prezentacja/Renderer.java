package prezentacja;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class Renderer extends JLabel implements ListCellRenderer<String>
{
	private static final long serialVersionUID = 1L;
	
	Renderer()
	{
		
	}
	
	@Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
            boolean isSelected, boolean cellHasFocus) {
		String[] values = value.split(" ");
        setText(values[1] + " " +values[2] + ", " + values[0]);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        if (values[0].equals("Niedziela")) {
            setForeground(Color.RED);
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}