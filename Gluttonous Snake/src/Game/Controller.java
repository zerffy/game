package Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import Game.Food;
import Game.Ground;
import Game.Snake;
import Game.SnakeListener;
import Game.BottonPanel;
import Game.GameMenu;
import Game.GamePanel;


public class Controller extends KeyAdapter implements SnakeListener{
	private Snake snake;
	private Food food;
	private Ground ground;
	private GamePanel gamePanel;
	private GameMenu gameMenu;
	private BottonPanel bottonPanel;
	
	
	public Controller(Snake snake, Food food, Ground ground,GamePanel gamePanel,GameMenu gameMenu,BottonPanel bottonPanel) {
		this.snake = snake;
		this.food = food;
		this.ground = ground;
		this.gamePanel = gamePanel;
		this.gameMenu = gameMenu;
		this.bottonPanel = bottonPanel;
		
		init();
	}
	
	
	//控制器的初始化，主要初始化对控件的监听
	public void init() {
		bottonPanel.getStartButton().addActionListener(new startHandler());
		bottonPanel.getPauseButton().addActionListener(new pauseHandler());
		bottonPanel.getEndButton().addActionListener(new endHandler());
		
		gameMenu.getItem1().addActionListener(new Item1Handler());
		gameMenu.getItem2().addActionListener(new Item2Handler());
		gameMenu.getItem3().addActionListener(new Item3Handler());
		gameMenu.getItem4().addActionListener(new Item4Handler());
		
		gameMenu.getSpItem1().addActionListener(new spItem1Handler());
		gameMenu.getSpItem2().addActionListener(new spItem2Handler());
		gameMenu.getSpItem3().addActionListener(new spItem3Handler());
		gameMenu.getSpItem4().addActionListener(new spItem4Handler());
		
		gameMenu.getMapItem1().addActionListener(new mapItem1Handler());
		gameMenu.getMapItem2().addActionListener(new mapItem2Handler());
		gameMenu.getMapItem3().addActionListener(new mapItem3Handler());
		
		gameMenu.getAbItem().addActionListener(new abortItemHandler());
		
		bottonPanel.getStartButton().setEnabled(true);
	}

	//获取各对象
	public Snake getSnake() {
		return snake;
	}
	
	public Ground getGround() {
		return ground;
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public GameMenu getGameMenu() {
		return gameMenu;
	}
	
	public BottonPanel getBottonPanel() {
		return bottonPanel;
	}
	
	
	//键盘按键的监听
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:			
				snake.changeDirection(Snake.UP);
				break;
			case KeyEvent.VK_DOWN:				
				snake.changeDirection(Snake.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				snake.changeDirection(Snake.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				snake.changeDirection(Snake.RIGHT);
				break;
			case KeyEvent.VK_W:
				snake.speedUp();
				break;
			case KeyEvent.VK_S:
				snake.speedDown();
				break;
			default:
				break;
		}
	}

	
	//实现蛇移动的接口，处理蛇移动过程发生的各种事情
	@Override
	public void snakeMoved(Snake snake) {
		//每移动一次，就更新一次面板
		gamePanel.display(snake, food, ground);			
		
		if(food.isFoodEated(snake)) {
			snake.eatFood();
			food.newFood(ground.getPoint());
			
			//更新得分显示面板	
			bottonPanel.repaint();
			setScore();		
		}
		
		if(ground.isGroundEated(snake)) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		
		if(snake.isEatBody()) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		
		
	}
	
	
	//
	public void setScore() {
		int score = snake.getFoodCount() ;
		bottonPanel.setScore(score);
	}
	

	
	// 开始一个新游戏	
	public void newGame() {
		ground.clear();
		
		switch (ground.getMapType()) {
			case 1:
				ground.generateRocks1();
				break;
			case 2:
				ground.generateRocks2();
				break;
			case 3:
				ground.generateRocks3();
				break;
		}
		
		food.newFood(ground.getPoint());	
		bottonPanel.setScore(0);		
		
		bottonPanel.repaint();
	}

	

	
	//开始游戏按钮的监听
	class startHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			gamePanel.requestFocus(true);	//!!使游戏面板区获得焦点
			snake.clear();
			snake.init();		
			snake.begin();
			newGame();
			bottonPanel.getStartButton().setEnabled(false);
		}
		
	}
	
	
	//暂停按钮的监听
	class pauseHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.requestFocus(true);
			snake.changePause();

			if(e.getActionCommand()=="暂停游戏")
				bottonPanel.getPauseButton().setText("继续游戏");
			else {
				bottonPanel.getPauseButton().setText("暂停游戏");
			}
		}
		
	}
	

	//结束游戏按钮的监听
	class endHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		
	}
	
	
	//菜单栏各按钮的监听
	class Item1Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			gamePanel.backgroundColor = color;
			
		}
		
	}
	
	
	class Item2Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			food.setFoodColor(color);
		}
		
	}
	
	
	class Item3Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			snake.setHeadColor(color);
		}
		
	}
	
	class Item4Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			snake.setBodyColor(color);
		}
		
	}
	
	class spItem1Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(600);
			
		}
		
	}
	
	class spItem2Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(350);
			
		}	
	}
	
	
	class spItem3Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(150);
		}	
	}
	
	class spItem4Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(80);
			
		}	
	}
	
	
	class mapItem1Handler	implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ground.setMapType(1);
			
		}
		
	}
	
	class mapItem2Handler	implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ground.setMapType(2);
			
		}
		
	}
	
	class mapItem3Handler	implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ground.setMapType(3);
			
		}
		
	}
	
	class abortItemHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuffer sb= new StringBuffer();
			sb.append("↑ ↓ ← →控制方向\n");
			sb.append("w键加速、s键减速\n");
			String message = sb.toString();
			JOptionPane.showMessageDialog(null, message, "操作说明",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
	
}