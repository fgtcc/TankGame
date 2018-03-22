/*
 * 坦克游戏
 * */

package com.chao;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

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
		
		//注册监听
		this.addKeyListener(mp);
		
		this.setSize(400,300);
		this.setLocation(360, 160);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

//面板
class MyPanel extends JPanel implements KeyListener
{
	//定义一个我的坦克
	Hero hero=null;
	
	//定义敌方坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	int enSize=3;//敌方坦克数量
	
	//构造函数
	public MyPanel()
	{
		hero=new Hero(100,100);
		
		//初始化敌方坦克
		for(int i=0;i<enSize;i++)
		{
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(2);//设置敌方坦克方向向下
			ets.add(et);
		}
	}
	
	//重写paint方法
	public void paint(Graphics g)
	{
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		//画出玩家坦克
		this.drawTank(hero.getX(),hero.getY(),g,this.hero.getDirect(),1);
		//画出地方坦克
		for(int i=0;i<ets.size();i++)
		{
			this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).direct, ets.get(i).color);
		}
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
			break;
		//向右
		case 1:
			g.fill3DRect(x, y, 30, 5,false);		
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		//向下
		case 2:
			g.fill3DRect(x, y, 5, 30,false);		
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		//向左
		case 3:
			g.fill3DRect(x, y, 30, 5,false);		
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x , y+10);
			break;
		}
	}

	
	//键盘上的A、S、D、W控制坦克的移动
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//设置玩家坦克方向
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			//向上
			this.hero.setDirect(0);
			this.hero.MoveUp();
		}
		else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			//向右
			this.hero.setDirect(1);
			this.hero.MoveRight();
		}
		else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			//向下
			this.hero.setDirect(2);
			this.hero.MoveDown();
		}
		else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			//向左
			this.hero.setDirect(3);
			this.hero.MoveLeft();
		}
		
		//重绘窗口
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

