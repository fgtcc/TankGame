package com.chao;

//�ӵ���
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=3;
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
	
	//̹�˷��� ��0��ʾ�ϣ�1��ʾ�ң�2��ʾ�£�3��ʾ��
	int direct=0;
	
	//̹���ٶ�
	int speed=5;
	
	//̹����ɫ
	int color;
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

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
			break;
		case 1:
			s=new Shot(x+30,y+10,1);
			break;
		case 2:
			s=new Shot(x+10,y+30,2);
			break;
		case 3:
			s=new Shot(x,y+10,3);
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

class EnemyTank extends Tank
{
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}
}