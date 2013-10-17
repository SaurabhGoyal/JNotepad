import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

public class Notepad
{

	JFrame j;
	JPanel p,p1;
	JTextArea textArea;
	JMenuBar mb;
	JMenu mfile, medit, mview, mformat, mhelp;
	JMenuItem miopen, minew, misave, miexit;
	JMenuItem micut, micopy, mipaste, midelete;
	JMenuItem mifont, miwrap;
	JMenuItem miabout;
	JScrollPane sp;
	JButton b;
	File file=null;
	String clipboard="";
	int cursorindex=0;
	
	private Notepad()
	{
		j=new JFrame("Untitled Document - NOTEPAD(made in java)");
		j.setLocation(40,40);
		
		p=new JPanel(new BorderLayout());
		p.setBackground(new Color(250,250,250));
		
		p1=new JPanel();
		textArea=new JTextArea();
		
		sp=new JScrollPane(textArea);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		p.add(sp);
		j.getContentPane().add(p);
		
		mb=new JMenuBar();
		
		mfile=new JMenu("File");
		medit=new JMenu("Edit");
		mview=new JMenu("View");
		mformat=new JMenu("Format");
		mhelp=new JMenu("Help");
		
		mb.add(mfile);
		mb.add(medit);
		mb.add(mview);
		mb.add(mformat);
		mb.add(mhelp);
		
		miopen=new JMenuItem("Open");
		minew=new JMenuItem("New");
		misave=new JMenuItem("Save");
		miexit=new JMenuItem("Exit");
		
		micut=new JMenuItem("Cut");
		micopy=new JMenuItem("Copy");
		mipaste=new JMenuItem("Paste");
		midelete=new JMenuItem("Delete");
		
		mifont=new JMenuItem("Font");
		miwrap=new JMenuItem("Wrap");
		
		miabout=new JMenuItem("About");
		
		miopen.addActionListener(new OpenListener());
		minew.addActionListener(new NewListener());
		misave.addActionListener(new SaveListener());
//		miexit.addActionListener(this);
		
		micut.addActionListener(new CutListener());
		micopy.addActionListener(new CopyListener());
		mipaste.addActionListener(new PasteListener());
		midelete.addActionListener(new DeleteListener());
		
		mifont.addActionListener(new FontListener());
		miwrap.addActionListener(new WrapListener());
		
		mfile.add(minew);
		mfile.add(miopen);
		mfile.add(misave);
		mfile.add(miexit);
		
		medit.add(micut);
		medit.add(micopy);
		medit.add(mipaste);
		medit.add(midelete);
		
		mformat.add(mifont);
		mformat.add(miwrap);
		
		mhelp.add(miabout);
		
		j.setJMenuBar(mb);
		j.setSize(600,400);
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	class OpenListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			file=open();			
		}
		private File open()
		{
			JFileChooser fc=new JFileChooser();
			int opt=fc.showOpenDialog(j);
			File file=null;
			if(opt==JFileChooser.APPROVE_OPTION)
			{
				try
				{
				file=fc.getSelectedFile();
				String s="",s1="";
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				while((s1=br.readLine())!=null)
					s+=s1+"\n";
				br.close();
				textArea.setText(s);
				}catch(Exception e1){System.out.println(e1);}
			}
			else;
			System.out.println(file);
			j.setTitle(file.getName());
			return file;
		}
		
	}
	
	class NewListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			newdoc(file);
		}
		private void newdoc(File file)
		{
			//do nothing
		}
		
	}
	
	class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			save(file);
		}
		private void save(File file)
		{
			System.out.println(file);
			String s=textArea.getText();
			try
			{
				if(file==null)
				{
					System.out.println("I am here");
					FileOutputStream fout=new FileOutputStream("D:\\Doc1.txt");
					fout.write(s.getBytes());
					fout.close();
				}
				else
				{
					BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
					bw.write(s);
					bw.close();			
				}
			}catch(Exception e2){System.out.println(e2);}	
		}
	}
	
	class CutListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clipboard=cut();
		}
		private String cut()
		{
			cursorindex=textArea.getCaretPosition();
			String s=textArea.getSelectedText();
			if(s!=null)
				textArea.setText(textArea.getText().replaceAll(s,""));
			if(cursorindex>=0)
				textArea.setCaretPosition(cursorindex);
			return s;
		}
	}	
	
	class CopyListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clipboard=copy();
		}
		private String copy()
		{
			cursorindex=textArea.getCaretPosition();
			String s=textArea.getSelectedText();
			return s;
		}
	}	
	
	class PasteListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			paste();
		}
		private void paste()
		{
			cursorindex=textArea.getCaretPosition();
			textArea.setCaretPosition(cursorindex);
			String str=textArea.getText();
			if(clipboard!=null)
				textArea.insert(clipboard, cursorindex);
//			setText(str.substring(0,cursorindex)+clipboard+str.substring(cursorindex,str.length()));
			textArea.setCaretPosition(cursorindex+clipboard.length());
		}
	}
	class DeleteListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			delete();
		}
		private void delete()
		{
			cursorindex=textArea.getCaretPosition();
			String s=textArea.getSelectedText();
			if(s!=null)
				textArea.setText(textArea.getText().replaceAll(s,""));
			textArea.setCaretPosition(cursorindex);
		}
	}
	
	
	//fontchooser not working...
	class FontListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			FontChooser1 jfc=new FontChooser1(textArea.getFont());
			textArea.setFont(jfc.choosenfont);
//			if(.hasFocus())
//			{	
//				System.out.println("/n"+jfc.choosenfont);
//				ta.setFont(jfc.choosenfont);
//			}
		}	
	}

	class WrapListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			textArea.setLineWrap(true);
		}
	}
	
	public static void main(String[] args) {
		Notepad np=new Notepad();
		np.j.setVisible(true);		
	}

}
