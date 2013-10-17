import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;


public class FontChooser1 {

	JFrame f;
	JPanel p1,p2;
	JComboBox<String> c1,c2,c3;
//	JSpinner spinner;
	JTextField tf;
	JButton b1,b2;
	Font font,choosenfont;
	String fontname;
	int fontsize;
	int fontstyle;
	
	public FontChooser1(Font font1)
	{	
		f=new JFrame("Choose Font");
		f.setSize(400,200);
		f.setLocation(80,80);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setLayout(new BoxLayout(f.getContentPane(),BoxLayout.Y_AXIS));
		
		font=font1;
		choosenfont=font1;
		fontstyle=font1.getStyle();
		fontsize=font1.getSize();
		
		p1=new JPanel();
		p2=new JPanel();
		f.getContentPane().add(p1);
		f.getContentPane().add(p1);
		
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontnamelist[]=ge.getAvailableFontFamilyNames();
		
		c1=new JComboBox<String>(fontnamelist);
		c1.addActionListener(new ComboListener());

		String fontstylelist[]={"Regular","Bold","Italic"};
		
		c2=new JComboBox<String>(fontstylelist);
		c2.addActionListener(new ComboListener());
		
		String fontsizelist[]=new String[36];
		int i=6,j=0;
		while(i<74)
		{
			fontsizelist[j]=String.valueOf(i);
			i+=2;
			j++;
		}
		
//		spinner=new JSpinner(new SpinnerNumberModel(fontsize,6,72,2));
//		spinner.setFocusable(false);
		c3=new JComboBox<String>(fontsizelist);
		c3.addActionListener(new ComboListener());
		
		p1.add(c1);
		p1.add(c2);
		p1.add(c3);
		
		tf=new JTextField("  ---AaBbCcXxYyZz---  ");
		tf.setPreferredSize(new Dimension(150,40));
		tf.setEditable(false);
		tf.setHighlighter(null);
		
		b1=new JButton("Ok");
		b2=new JButton("Cancel");
		
		b1.addActionListener(new OkListener());
		b2.addActionListener(new CancelListener());
		p2.add(tf);
		p2.add(b1);
		p2.add(b2);
		
		f.getContentPane().add(p1);
		f.getContentPane().add(p2);
		f.setVisible(true);
	
	}
	void chooseFont()
	{
		font=new Font(fontname,fontstyle,fontsize);
		choosenfont=font;	
	}
	
	class ComboListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==c1)
			{
				fontname=(String)c1.getSelectedItem();
				font=new Font(fontname,fontstyle,fontsize);
				tf.setFont(font);
			}
			if(e.getSource()==c2)
			{
				String str=(String)c2.getSelectedItem();
				if(str=="Regular")
					fontstyle=0;
				if(str=="Bold")
					fontstyle=1;
				if(str=="Italic")
				    fontstyle=2;
				font=new Font(fontname,fontstyle,fontsize);
				tf.setFont(font);
			}
			if(e.getSource()==c3)
			{
				fontsize=Integer.parseInt((String)c3.getSelectedItem());
				font=new Font(fontname,fontstyle,fontsize);
				tf.setFont(font);
			}	
		}
	}
		
	class OkListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			chooseFont();
			f.dispose();
		}
	}
	class CancelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			f.dispose();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
