/*
 * 坦克游戏
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
	
	//构造函数
	public myTankGame()
	{
		mp=new MyPanel();
		
		this.add(mp);
		this.setSize(400,300);
		this.setLocation(360, 160);
		this.setVisible(true);
	}

}

//面板
class MyPanel extends JPanel
{
	//定义一个我的坦克
	Hero hero=null;
	
	//构造函数
	public MyPanel()
	{
		hero=new Hero(100,100);
	}
	
	//重写paint方法
	public void paint(Graphics g)
	{
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		//画坦克
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
		//向上
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
	int x=0;//坦克的横坐标
	int y=0;//坦克的纵坐标
	
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
