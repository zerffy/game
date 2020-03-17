package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Game.Food;
import Game.Ground;
import Game.Snake;
import Game.Global;


public class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Snake snake;
	private Food food;
	private Ground ground;	
	public Color backgroundColor;
	
	public GamePanel() {
		setLocation(0, 0);		
		/* 设置大小和布局 */
		this.setSize(Global.WIDTH * Global.CELL_SIZE, Global.HEIGHT
				* Global.CELL_SIZE);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setFocusable(true);
		
	}


	public void display(Snake snake,Food food,Ground ground) {
		this.snake = snake;
		this.food = food;
		this.ground = ground;
		
		repaint();
	}

	
	//清空游戏面板（擦除效果）
	public void clearDraw(Graphics g) {
			if(backgroundColor==null) {
				g.setColor(new Color(0xFFC0CB));
			}
			else {
				g.setColor(backgroundColor);
			}
			g.fillRect(0, 0, Global.WIDTH*Global.CELL_SIZE, Global.HEIGHT*Global.CELL_SIZE);
	}
	
	
	@Override
	public void paint(Graphics g) {
			clearDraw(g);
			//重新显示		
			if(ground != null && snake != null && food != null) {
					ground.drawMe(g);
					food.drawMe(g);
					snake.drawMe(g);
			}
			if(snake!=null && snake.isLife()==false)  {
				recover(g);
			}
	
		}

	
	//恢复工作
	public void recover(Graphics g) {
		clearDraw(g);
		
		//在游戏主面板区绘制“game over”
		g.setColor(Color.red);
		g.setFont(new Font("Serif",Font.BOLD,50));
		g.drawString("Game Over", 130, 210);
		
	}
}
