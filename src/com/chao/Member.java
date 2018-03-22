package com.chao;

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
	public Hero(int x,int y)
	{
		super(x,y);
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