package Game;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Game.Controller;
import Game.Food;
import Game.Ground;
import Game.Snake;
import Game.Global;
import Game.BottonPanel;
import Game.GameMenu;
import Game.GamePanel;


public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new GameFrame(new Controller(new Snake(), new Food(), new Ground(), 
				new GamePanel(), new GameMenu(),new BottonPanel()));

	}

	private GamePanel gamePanel;
	private GameMenu gameMenu;
	private Snake snake;
	private Controller controller;	
	private JPanel buttonPanel;

	public GameFrame(Controller c) {
		this.controller = c;
		snake = controller.getSnake();
		gameMenu = controller.getGameMenu();
		gamePanel = controller.getGamePanel();
		buttonPanel = controller.getBottonPanel();
		
		setTitle("Gluttonous Snake");
		setBounds(300,100,Global.WIDTH*Global.CELL_SIZE+250,Global.HEIGHT*Global.CELL_SIZE+60);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = this.getContentPane(); 		
		this.setJMenuBar(gameMenu);
		
		contentPane.add(gamePanel);
		contentPane.add(buttonPanel);
		
		setResizable(false);
		setVisible(true);

		
		//让窗口居中 
		this.setLocation(this.getToolkit().getScreenSize().width / 2
				- this.getWidth() / 2, this.getToolkit().getScreenSize().height
				/ 2 - this.getHeight() / 2);
		
		
		gamePanel.addKeyListener(controller);
		snake.addSnakeListener(controller);	
		controller.newGame();
		
		
	}
	
}