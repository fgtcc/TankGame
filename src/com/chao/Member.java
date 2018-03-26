package com.chao;

//子弹类
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=3;
	boolean isLive=true;//子弹状态逻辑值
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
			
			//测试线程是否成功开启
			//System.out.println("子弹坐标x="+x+" y="+y);
			
			//子弹逻辑死亡
			//判断子弹是否碰到边缘
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
	}
}

//坦克类
class Tank
{
	int x=0;//坦克的横坐标
	int y=0;//坦克的纵坐标
	
	//坦克方向 ：0表示上，1表示右，2表示下，3表示左
	int direct=0;
	
	//坦克速度
	int speed=5;
	
	//坦克颜色
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
	Shot s=null;//子弹,目前只有一颗子弹
	
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
		//启动子弹线程
		Thread t=new Thread(s);
		t.start();
	}
	
	//坦克向上移动
	public void MoveUp()
	{
		y-=speed;
	}
	//坦克向右移动
	public void MoveRight()
	{
		x+=speed;
	}
	//坦克向下移动
	public void MoveDown()
	{
		y+=speed;
	}
	//坦克向左移动
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