package com.sds.stream;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor extends JFrame {
	JTextArea ta;
	JMenuBar bar;
	JLabel l;
	StringBuffer sb;
	JPanel p_center,p_lable,p_ta;
	JScrollPane scroll;
	JOptionPane dia;
	JFileChooser chooser;
	FileReader reader;
	BufferedReader buffr;
	JMenu m_file,m_edit,m_view,m_find,m_doc,m_pro,m_tool,m_brow,m_win,m_help;
	JMenuItem mi_new,mi_open,mi_close,mi_save,mi_saveas,mi_exit;
	String curr_path;
	FileWriter writer;
	BufferedWriter buffw;
	String a;
	public Editor() {		
		
		init();
		mi_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ta==null){
					func_new();
				}else{
					dia.showMessageDialog(getParent(), "이미 존재하는 파일이 있습니다.");
				}
			}
		});
		mi_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					func_open();
			}
		});
		mi_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ta!=null){
					func_close();
					ta=null;
				}else{
					dia.showMessageDialog(getParent(), "종료할 파일이 없습니다.");
				}
			}
		});
		mi_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(ta!=null){
					func_save();
				}else{
					dia.showMessageDialog(getParent(), "열린 파일이 없습니다.");
				}
			}
		});
		mi_saveas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ta!=null){
					func_saveas();
				}else{
					dia.showMessageDialog(getParent(), "열린 파일이 없습니다.");
				}
			}
		});
		mi_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		menuAdd();	
		
		p_lable.setPreferredSize(new Dimension(20, 600));
		p_center.setLayout(new BorderLayout());
		p_ta.setBackground(Color.white);
		p_center.add(p_lable,BorderLayout.WEST);
		p_center.add(p_ta,BorderLayout.CENTER);
		
		add(bar,BorderLayout.NORTH);
		add(scroll);
				
		setSize(800,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
	}
	public void func_new(){
		ta=new JTextArea();
		ta.setPreferredSize(new Dimension(1000,600));
		ta.setBackground(Color.cyan);
		
		ta.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		p_ta.add(ta);
		for(int i=0;i<15;i++){
			l=new JLabel(Integer.toString(i+1));
			p_lable.add(l);
			
		}
		p_ta.updateUI();
	}
	public void func_close(){
		int result=dia.showConfirmDialog(getParent(), "기존의 파일을 종료 하시겠습니까");
		if(result==JOptionPane.OK_OPTION){
			p_ta.remove(ta);
			curr_path="";
			p_ta.updateUI();
		}

	}
	public void func_open(){
		if(ta==null){
			func_new();
		}
		int result=chooser.showOpenDialog(getParent());
		if(result==JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			curr_path=file.getPath();
			try {
				reader=new FileReader(file);
				buffr=new BufferedReader(reader);
				String data=null;
				ta.setText("");
				while((data=buffr.readLine())!=null){
					ta.append(data+"\n");
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				if(buffr!=null){
					try {
						buffr.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	public void func_save(){
		String[] data=ta.getText().split("\n");
		try {
			writer=new FileWriter(curr_path);
			buffw=new BufferedWriter(writer);
			for(int i=0;i<data.length;i++){
				buffw.write(data[i]+"\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			if(buffw!=null){
				try {
					buffw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	public void func_saveas(){
		int result=chooser.showSaveDialog(getParent());
		if(result==JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			String[] data=ta.getText().split("\n");
			try {
				writer=new FileWriter(file);
				buffw=new BufferedWriter(writer);
				for(int i=0;i<data.length;i++){
					buffw.write(data[i]+"\n");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				if(buffw!=null){
					try {
						buffw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	public void init(){
		
		bar=new JMenuBar();
		p_center=new JPanel();
		p_lable=new JPanel();
		p_ta=new JPanel();
		bar=new JMenuBar();
		m_file=new JMenu("File");
		m_edit=new JMenu("Edit");
		m_view=new JMenu("View");
		m_find=new JMenu("Find");
		m_doc=new JMenu("Document");
		m_pro=new JMenu("Project");
		m_tool=new JMenu("Tool");
		m_brow=new JMenu("Browser");
		m_win=new JMenu("Window");
		m_help=new JMenu("Help");
		p_center=new JPanel();
		p_lable=new JPanel();
		p_ta=new JPanel();
		bar=new JMenuBar();
		mi_new=new JMenuItem("새로운 파일");
		mi_open=new JMenuItem("열기");
		mi_close=new JMenuItem("닫기");
		mi_save=new JMenuItem("저장");
		mi_saveas=new JMenuItem("새이름으로 저장");
		mi_exit=new JMenuItem("종료");
		scroll=new JScrollPane(p_center);
		chooser=new JFileChooser();
		sb=new StringBuffer();

	}
	public void menuAdd(){
		m_file.add(mi_new);
		m_file.add(mi_open);
		m_file.add(mi_close);
		m_file.addSeparator();
		m_file.add(mi_save);
		m_file.add(mi_saveas);
		m_file.addSeparator();
		m_file.add(mi_exit);		
		
		bar.add(m_file);
		bar.add(m_edit);
		bar.add(m_view);
		bar.add(m_find);
		bar.add(m_doc);
		bar.add(m_pro);
		bar.add(m_tool);
		bar.add(m_brow);
		bar.add(m_win);
		bar.add(m_help);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Editor();
	}

}






















