/*
 * ̹����Ϸ
 * */

package com.chao;

import java.awt.*;
import javax.swing.*;

public class myTankGame extends JFrame
{
	MyPanel mp=null;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		myTankGame mtg=new myTankGame();


		
	}
	
	//���캯��
	public myTankGame()
	{
		mp=new MyPanel();
		
		this.add(mp);
		this.setSize(400,300);
		this.setLocation(360, 160);
		this.setVisible(true);
	}

}

//���
class MyPanel extends JPanel
{
	//����һ���ҵ�̹��
	Hero hero=null;
	
	//���캯��
	public MyPanel()
	{
		hero=new Hero(100,100);
	}
	
	//��дpaint����
	public void paint(Graphics g)
	{
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		//��̹��
		drawTank(hero.getX(),hero.getY(),g,0,0);
	}
	
	public void drawTank(int x,int y,Graphics g,int direct,int type)
	{
		switch(type)
		{
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		
		switch(direct)
		{
		//����
		case 0:
			g.fill3DRect(x, y, 5, 30,false);		
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y);
		}
	}
}

class Tank
{
	int x=0;//̹�˵ĺ�����
	int y=0;//̹�˵�������
	
	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

class Hero extends Tank
{
	public Hero(int x,int y)
	{
		super(x,y);
	}
}
