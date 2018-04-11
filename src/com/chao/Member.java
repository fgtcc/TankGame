package com.chao;

import java.io.*;
import java.util.Vector;
import javax.sound.sampled.*;

//播放声音的类
class AePlayWave extends Thread
{
	private String filename;
	public AePlayWave(String wavfile)
	{
		filename=wavfile;
	}
	
	public void run()
	{
		File soundFile =new File(filename);
		AudioInputStream audioInputStream=null;
		
		try 
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} 
		
		AudioFormat format =audioInputStream.getFormat();
		SourceDataLine auline=null;
		DataLine.Info info =new DataLine.Info(SourceDataLine.class, format);
		
		try 
		{
			auline=(SourceDataLine)AudioSystem.getLine(info);
			auline.open(format);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		
		auline.start();
		int nBytesRead=0;
		byte[] abData=new byte[1024];
		
		try 
		{
			while(nBytesRead !=-1)
			{
				nBytesRead=audioInputStream.read(abData, 0, abData.length);
				if(nBytesRead>=0)
				{
					auline.write(abData, 0, nBytesRead);
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		finally
		{
			auline.drain();
			auline.close();
		}
		
	}
}

class Node
{
	int x;
	int y;
	int direct;
	
	public Node(int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
}

//记录类，可以保存玩家的设置
class Recorder
{
	private static int enNum=20;//记录没关敌方坦克数量
	private static int mylife=3;//设置玩家生命次数
	private static int allEnNum=0;//记录所消灭的敌方坦克的数量
	static Vector<Node>nodes=new Vector<Node>();//文件中所记录的坦克点
	
	
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<EnemyTank>ets=new Vector<EnemyTank>();
	
	//读取坦克坐标与方向
	public Vector<Node> getNodes()
	{
		try 
		{
			fr=new FileReader("E:\\document_eclipse\\TankGame\\src\\com\\chao\\myTankRecord.txt");
			br=new BufferedReader(fr);
			String n="";
			n=br.readLine();
			allEnNum=Integer.parseInt(n);
			
			while((n=br.readLine())!=null)
			{
				String []xyz=n.split(" ");
				Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]));
				nodes.add(node);
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				br.close();
				fr.close();
			}
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return nodes;
	}
	
	public Vector<EnemyTank> getEts() {
		return ets;
	}

	public void setEts(Vector<EnemyTank> ets) {
		this.ets = ets;
	}

	//从文件中读取游戏数据
	public static void getRecording()
	{
		try 
		{
			fr=new FileReader("E:\\document_eclipse\\TankGame\\src\\com\\chao\\myTankRecord.txt");
			br=new BufferedReader(fr);
			String n=br.readLine();
			allEnNum=Integer.parseInt(n);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				br.close();
				fr.close();
			}
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//游戏数据存盘
	public static void keepRecording()
	{
		try 
		{
			fw=new FileWriter("E:\\document_eclipse\\TankGame\\src\\com\\chao\\myTankRecord.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnNum+"\r\n");
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				bw.close();
				fw.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void keepRecAndEts()
	{
		try 
		{
			fw=new FileWriter("E:\\document_eclipse\\TankGame\\src\\com\\chao\\myTankRecord.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnNum+"\r\n");
			
			//保存敌方坦克位置坐标与方向
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				if(et.isLive)
				{
					String data=et.x+" "+et.y+" "+et.direct;
					
					bw.write(data+"\r\n");
				}
			}
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				bw.close();
				fw.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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

//爆炸类
class Bomb
{
	int x,y;//定义爆炸的位置坐标
	int life=9;//爆炸的生命周期
	boolean isLive=true;
	
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	//生命周期减少
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

//子弹类
class Shot implements Runnable
{
	int x;//子弹横坐标
	int y;//子弹纵坐标
	int direct;//子弹运动方向
	int speed=2;//子弹速度
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
	int direct=0;//坦克方向 ：0表示上，1表示右，2表示下，3表示左	
	int speed=1;//坦克速度	
	int color;//坦克颜色
	boolean isLive=true;//坦克是否死亡的标志
	
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

class EnemyTank extends Tank implements Runnable
{	
	Vector<Shot>ss=new Vector<Shot>();//敌方坦克子弹
	
	int times=0;
	
	//定义一个坦克向量，用于存储面板上的敌方坦克
	Vector<EnemyTank> ets= new Vector<EnemyTank>();
	
	public EnemyTank(int x,int y)
	{
		super(x,y);
	}

	//获得面板上的敌方坦克向量
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//判断坦克之间是否重叠
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
				
			
			//让坦克随机产生一个新的方向
			this.direct=(int)(Math.random()*4);
					
			//判断是否需要给敌方坦克添加子弹
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
						
						//启动子弹线程
						Thread t_emshot=new Thread(s);
						t_emshot.start();
					}
				}
			}
			
			
			if(this.isLive==false)
			{
				//坦克死亡后，退出线程
				break;
			}
		}
	}
}