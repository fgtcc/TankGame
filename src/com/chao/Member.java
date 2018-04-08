package com.chao;

import java.util.Vector;

//��¼�࣬���Ա�����ҵ�����
class Recorder
{
	private static int enNum=20;//��¼û�صз�̹������
	private static int mylife=3;//���������������
	private static int allEnNum=0;//��¼������ĵз�̹�˵�����
	
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recorder.allEnNum = allEnNum;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMylife() {
		return mylife;
	}
	public static void setMylife(int mylife) {
		Recorder.mylife = mylife;
	}
	
	public static void reduceEnNum()
	{
		enNum--;
	}
	
	public static void addEnNumRec()
	{
		allEnNum++;
	}
	
}

//��ը��
class Bomb
{
	int x,y;//���屬ը��λ������
	int life=9;//��ը����������
	boolean isLive=true;
	
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	//�������ڼ���
	public void lifeDown()
	{
		if(life>0)
		{
			life--;
		}
		else
		{
			this.isLive=false;
		}
	}
}

//�ӵ���
class Shot implements Runnable
{
	int x;//�ӵ�������
	int y;//�ӵ�������
	int direct;//�ӵ��˶�����
	int speed=2;//�ӵ��ٶ�
	boolean isLive=true;//�ӵ�״̬�߼�ֵ
	public Shot(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		while(true)
		{
			try
			{
				Thread.sleep(50);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			switch(direct)
			{
			case 0:
				this.y-=speed;
				break;
			case 1:
				this.x+=speed;
				break;
			case 2:
				this.y+=speed;
				break;
			case 3:
				this.x-=speed;
				break;
			}
			
			//�����߳��Ƿ�ɹ�����
			//System.out.println("�ӵ�����x="+x+" y="+y);
			
			//�ӵ��߼�����
			//�ж��ӵ��Ƿ�������Ե
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
	}
}

//̹����
class Tank
{
	int x=0;//̹�˵ĺ�����
	int y=0;//̹�˵�������
	int direct=0;//̹�˷��� ��0��ʾ�ϣ�1��ʾ�ң�2��ʾ�£�3��ʾ��	
	int speed=1;//̹���ٶ�	
	int color;//̹����ɫ
	boolean isLive=true;//̹���Ƿ������ı�־
	
	public int getColor() 
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public int getDirect() 
	{
		return direct;
	}

	public void setDirect(int direct) 
	{
		this.direct = direct;
	}

	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}

class Hero extends Tank
{
	Vector<Shot>ss=new Vector<Shot>();
	Shot s=null;//�ӵ�,Ŀǰֻ��һ���ӵ�
	
	public Hero(int x,int y)
	{
		super(x,y);
	}
	
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			s=new Shot(x+10,y,0);
			ss.add(s);
			break;
		case 1:
			s=new Shot(x+30,y+10,1);
			ss.add(s);
			break;
		case 2:
			s=new Shot(x+10,y+30,2);
			ss.add(s);
			break;
		case 3:
			s=new Shot(x,y+10,3);
			ss.add(s);
			break;
		}
		//�����ӵ��߳�
		Thread t=new Thread(s);
		t.start();
	}
	
	//̹�������ƶ�
	public void MoveUp()
	{
		y-=speed;
	}
	//̹�������ƶ�
	public void MoveRight()
	{
		x+=speed;
	}
	//̹�������ƶ�
	public void MoveDown()
	{
		y+=speed;
	}
	//̹�������ƶ�
	public void MoveLeft()
	{
		x-=speed;
	}
}

class EnemyTank extends Tank implements Runnable
{	
	Vector<Shot>ss=new Vector<Shot>();//�з�̹���ӵ�
	
	int times=0;
	
	//����һ��̹�����������ڴ洢����ϵĵз�̹��
	Vector<EnemyTank> ets= new Vector<EnemyTank>();
	
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}

	//�������ϵĵз�̹������
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//�ж�̹��֮���Ƿ��ص�
	public boolean isTouchOtherEnemy()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case 1:
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y+20>=et.y&&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==1||et.direct==3)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+30&&this.y+20>=et.y&&this.y+20<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case 2:
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+30>=et.y&&this.y+30<=et.y+30)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y+30>=et.y&&this.y+30<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y+30>=et.y&&this.y+30<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y+30>=et.y&&this.y+30<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case 3:
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+20>=et.y&&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==1||et.direct==3)
					{
						if(this.x>=et.x&&this.x<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+30&&this.y+20>=et.y&&this.y+20<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
			
		}
		
		return b;
	}
	
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		while(true)
		{		
			switch(this.direct)
			{
			case 0:
				for(int i=0;i<30+(int)(Math.random()*100);i++)
				{
					if(y>0&&!this.isTouchOtherEnemy())
					{
						y-=speed;						
					}
					try
					{
						Thread.sleep(50);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}
				
				break;
			case 1:
				for(int i=0;i<30+(int)(Math.random()*100);i++)
				{
					if(x<400&&!this.isTouchOtherEnemy())
					{
						x+=speed;						
					}
					try
					{
						Thread.sleep(50);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}
				
				break;
			case 2:
				for(int i=0;i<30+(int)(Math.random()*100);i++)
				{
					if(y<300&&!this.isTouchOtherEnemy())
					{
						y+=speed;						
					}
					try
					{
						Thread.sleep(50);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}
				
				break;
			case 3:
				for(int i=0;i<30+(int)(Math.random()*100);i++)
				{
					if(x>0&&!this.isTouchOtherEnemy())
					{
						x-=speed;						
					}
					try
					{
						Thread.sleep(50);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}
				
				break;
			}
				
			
			//��̹���������һ���µķ���
			this.direct=(int)(Math.random()*4);
					
			//�ж��Ƿ���Ҫ���з�̹������ӵ�
			this.times++;
			if(times%2==0)
			{
				if(this.isLive)
				{
					if(this.ss.size()<5)
					{
						Shot s=null;
						switch(this.direct)
						{
						case 0:
							s=new Shot(this.x+10,this.y,0);
							this.ss.add(s);
							break;
						case 1:
							s=new Shot(this.x+30,this.y+10,1);
							this.ss.add(s);
							break;
						case 2:
							s=new Shot(this.x+10,this.y+30,2);
							this.ss.add(s);
							break;
						case 3:
							s=new Shot(this.x,this.y+10,3);
							this.ss.add(s);
							break;
						}
						
						//�����ӵ��߳�
						Thread t_emshot=new Thread(s);
						t_emshot.start();
					}
				}
			}
			
			
			if(this.isLive==false)
			{
				//̹���������˳��߳�
				break;
			}
		}
	}
}