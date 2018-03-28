/*
 * ̹����Ϸ
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
	
	//���캯��
	public myTankGame()
	{
		mp=new MyPanel();
		//����mp�߳�
		Thread t=new Thread(mp);
		t.start();	
		
		this.add(mp);
		
		//ע�����
		this.addKeyListener(mp);
		
		this.setSize(400,300);
		this.setLocation(360, 160);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

//���
class MyPanel extends JPanel implements KeyListener,Runnable
{
	
	Hero hero=null;//�������̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();//����з�̹��
	int enSize=3;//�з�̹������
	Vector<Bomb>bombs=new Vector<Bomb>();//���屬ը����
	
	
	//���屬ը��ͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	
	//���캯��
	public MyPanel()
	{
		hero=new Hero(100,100);
		
		//��ʼ���з�̹��
		for(int i=0;i<enSize;i++)
		{
			EnemyTank et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(2);//���õз�̹�˷�������
			
			//�����з�̹���߳�
			Thread t_enemy=new Thread(et);
			t_enemy.start();
			
			//�з�̹������ӵ�
			Shot s=new Shot(et.x+10,et.y+30,2);
			et.ss.add(s);
			Thread t_enemyShot=new Thread(s);
			t_enemyShot.start();
			
			ets.add(et);
		}
		
		//��ʼ��ͼƬ
		image1=Toolkit.getDefaultToolkit().getImage("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame01.png");
		image2=Toolkit.getDefaultToolkit().getImage("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame02.png");
		image3=Toolkit.getDefaultToolkit().getImage("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame03.png");
		//image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("E:\\document_eclipse\\TankGame\\src\\com\\chao\\explode_tankmovie_frame03.png"));
		
	}
	
	//��дpaint����
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//�������̹��
		this.drawTank(hero.getX(),hero.getY(),g,this.hero.getDirect(),1);
		
		//�����ӵ�
		for(int i=0;i<hero.ss.size();i++)
		{
			Shot myshot=hero.ss.get(i);
			
			if(myshot!=null&&myshot.isLive==true)
			{
				g.draw3DRect(myshot.x, myshot.y, 1, 1, false);	
			}
			
			//���ӵ��߼��������򽫸��ӵ�������������Ƴ�
			if(myshot.isLive==false)
			{
				hero.ss.remove(myshot);
			}
		}
		
		//���Ʊ�ը����
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
		
		//�����з�̹��
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
				//���Ƶз�̹��
				this.drawTank(et.getX(), et.getY(), g, et.direct, et.color);	
				//���Ƶз�̹���ӵ�
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
	
	//�ж��ӵ��Ƿ����̹��
	public void hitTank(Shot s,EnemyTank et)
	{
		//�ж�̹�˷���
		switch(et.direct)
		{
		case 0:
		case 2:
			if(s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30)
			{
				//����
				s.isLive=false;//�ӵ�����
				et.isLive=false;//�з�̹������
				
				//������ը���󲢷��뼯����
				Bomb b=new Bomb(et.x,et.y);
				bombs.add(b);
			}
		case 1:
		case 3:
			if(s.x>et.x&&s.x<et.x+30&&s.y>et.y&&s.y<et.y+20)
			{
				//����
				s.isLive=false;//�ӵ�����
				et.isLive=false;//�з�̹������
				
				//������ը���󲢷��뼯����
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
		//����
		case 0:
			g.fill3DRect(x, y, 5, 30,false);		
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y);
			break;
		//����
		case 1:
			g.fill3DRect(x, y, 30, 5,false);		
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		//����
		case 2:
			g.fill3DRect(x, y, 5, 30,false);		
			g.fill3DRect(x+15, y, 5, 30,false);
			g.fill3DRect(x+5, y+5, 10, 20,false);
			g.fillOval(x+5, y+10, 10, 10);
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		//����
		case 3:
			g.fill3DRect(x, y, 30, 5,false);		
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect(x+5, y+5, 20, 10,false);
			g.fillOval(x+10, y+5, 10, 10);
			g.drawLine(x+15, y+10, x , y+10);
			break;
		}
	}

	
	//�����ϵ�A��S��D��W����̹�˵��ƶ�
	@Override
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub	
		//�������̹�˷���
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			//����
			this.hero.setDirect(0);
			this.hero.MoveUp();
		}
		else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			//����
			this.hero.setDirect(1);
			this.hero.MoveRight();
		}
		else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			//����
			this.hero.setDirect(2);
			this.hero.MoveDown();
		}
		else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			//����
			this.hero.setDirect(3);
			this.hero.MoveLeft();
		}
		
		//�ж�����Ƿ���J��,��̹�˷����ӵ�
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			//����ǰ�ӵ�������4�ţ��ɼ������һ���ӵ��������5���ӵ�
			if(hero.ss.size()<=4)
			{
				this.hero.shotEnemy();//����	
			}	
		}
				
		//�ػ洰��
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
			
			//�ж��ӵ��Ƿ����̹��
			for(int i=0;i<hero.ss.size();i++)
			{
				Shot myshot=hero.ss.get(i);
				//�ж��ӵ�״̬
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
			

			
			//�ػ�
			this.repaint();
		}
	}
}

