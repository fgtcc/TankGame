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
		//启动mp线程
		Thread t=new Thread(mp);
		t.start();	
		
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
class MyPanel extends JPanel implements KeyListener,Runnable
{
	
	Hero hero=null;//定义玩家坦克
	Vector<EnemyTank> ets=new Vector<EnemyTank>();//定义敌方坦克
	int enSize=3;//敌方坦克数量
	Vector<Bomb>bombs=new Vector<Bomb>();//定义爆炸集合
	
	
	//定义爆炸的图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	
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
			
			//启动敌方坦克线程
			Thread t_enemy=new Thread(et);
			t_enemy.start();
			
			//敌方坦克添加子弹
			Shot s=new Shot(et.x+10,et.y+30,2);
			et.ss.add(s);
			Thread t_enemyShot=new Thread(s);
			t_enemyShot.start();
			
			ets.add(et);
		}
		
		//初始化图片
		image1=Toolkit.getDefaultToolkit().getImage("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame01.png");
		image2=Toolkit.getDefaultToolkit().getImage("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame02.png");
		image3=Toolkit.getDefaultToolkit().getImage("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame03.png");
		//image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame03.png"));
		
	}
	
	//重写paint方法
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//画出玩家坦克
		this.drawTank(hero.getX(),hero.getY(),g,this.hero.getDirect(),1);
		
		//绘制子弹
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myshot=hero.ss.get(i);
			
			if(myshot!=null&&myshot.isLive==true)
			{
				g.draw3DRect(myshot.x, myshot.y, 1, 1, false);	
			}
			
			//若子弹逻辑死亡，则将该子弹对象从数组中移除
			if(myshot.isLive==false)
			{
				hero.ss.remove(myshot);
			}
		}
		
		//绘制爆炸场景
		for(int i=0;i<bombs.size();i++)
		{
			Bomb b=bombs.get(i);
			
			if(b.life>6)
			{
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			}
			else if(b.life>3)
			{
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			}
			else
			{
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			
			b.lifeDown();
			
			if(b.life==0)
			{
				bombs.remove(b);
			}
		}
		
		//画出敌方坦克
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
				//绘制敌方坦克
				this.drawTank(et.getX(), et.getY(), g, et.direct, et.color);	
				//绘制敌方坦克子弹
				for(int j=0;j<et.ss.size();j++)
				{
					Shot enemyShot=et.ss.get(j);
					
					if(enemyShot.isLive)
					{
						g.draw3DRect(enemyShot.x, enemyShot.y, 1, 1, false);	
					}
					else
					{
						et.ss.remove(enemyShot);
					}
				}
			}
		}	
	}
	
	//判断子弹是否击中坦克
	public void hitTank(Shot s,EnemyTank et)
	{
		//判断坦克方向
		switch(et.direct)
		{
		case 0:
		case 2:
			if(s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30)
			{
				//击中
				s.isLive=false;//子弹死亡
				et.isLive=false;//敌方坦克死亡
				
				//创建爆炸对象并放入集合中
				Bomb b=new Bomb(et.x,et.y);
				bombs.add(b);
			}
		case 1:
		case 3:
			if(s.x>et.x&&s.x<et.x+30&&s.y>et.y&&s.y<et.y+20)
			{
				//击中
				s.isLive=false;//子弹死亡
				et.isLive=false;//敌方坦克死亡
				
				//创建爆炸对象并放入集合中
				Bomb b=new Bomb(et.x,et.y);
				bombs.add(b);
			}
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
	public void keyPressed(KeyEvent e) 
	{
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
		
		//判断玩家是否按下J键,让坦克发射子弹
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			//若当前子弹不多于4颗，可继续添加一颗子弹，即最多5颗子弹
			if(hero.ss.size()<=4)
			{
				this.hero.shotEnemy();//开火	
			}	
		}
				
		//重绘窗口
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		while(true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//判断子弹是否击中坦克
			for(int i=0;i<hero.ss.size();i++)
			{
				Shot myshot=hero.ss.get(i);
				//判断子弹状态
				if(myshot.isLive)
				{
					for(int j=0;j<ets.size();j++)
					{
						EnemyTank et =ets.get(j);
						
						if(et.isLive)
						{
							this.hitTank(myshot, et);
						}
					}	
				}
			}
			

			
			//重绘
			this.repaint();
		}
	}
}

