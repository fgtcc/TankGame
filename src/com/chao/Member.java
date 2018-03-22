package com.chao;

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
	public Hero(int x,int y)
	{
		super(x,y);
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