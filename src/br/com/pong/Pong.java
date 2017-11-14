package br.com.pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import br.com.pong.components.Ball;
import br.com.pong.components.Paddle;

public class Pong implements ActionListener, KeyListener {

	public static Pong pong;
	private int width = 900, height = 600;
	private Renderer renderer;
	private Paddle player1;
	private Paddle player2;
	private Ball ball;
	private int gameStatus = 2;

	private boolean bot = false;
	private boolean w, s, up, down;
	private boolean space;

	public Pong() {

		Timer timer = new Timer(20, this);

		JFrame jframe = new JFrame("Pong");
		renderer = new Renderer();

		// O + 15 e o +39 é para caber tudo na janela.
		// Pelo menos no meu notebook esses valores serviram.
		jframe.setSize(width + 16, height + 39);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);
		jframe.setVisible(true);

		start();

		timer.start();
	}

	public void render(Graphics2D g) {

		// Cor de fundo
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);

		/*
		 * if (gameStatus == 0) { g.setColor(Color.WHITE); g.setFont(new
		 * Font("TimesRoman", Font.BOLD, 48)); g.drawString("PONG", width / 2 -
		 * 80, height / 2 - 50);
		 * 
		 * g.setColor(Color.WHITE); g.setFont(new Font("TimesRoman", Font.BOLD,
		 * 28)); g.drawString("ONE PLAYER - SHIFT", width / 2 - 150, height /
		 * 2);
		 * 
		 * g.setColor(Color.WHITE); g.setFont(new Font("TimesRoman", Font.BOLD,
		 * 28)); g.drawString("TWO PLAYER - SPACE", width / 2 - 160, height / 2
		 * + 50);
		 * 
		 * } else if (gameStatus == 1) { String score;
		 * 
		 * score = player1.getScore() + "     " + player2.getScore();
		 * 
		 * g.setColor(Color.WHITE); g.setStroke(new BasicStroke(5f));
		 * g.drawLine(width / 2, 0, width / 2, height);
		 * 
		 * g.setColor(Color.WHITE); g.setFont(new Font("TimesRoman", Font.PLAIN,
		 * 30)); g.drawString(score, width / 2 - 35, 30);
		 * 
		 * g.setColor(Color.RED); g.setFont(new Font("TimesRoman", Font.BOLD,
		 * 48)); g.drawString("PAUSED", width / 2 - 95, height / 2);
		 * 
		 * } else if (gameStatus == 2) {
		 */
		String score;

		score = player1.getScore() + "     " + player2.getScore();

		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(5f));
		g.drawLine(width / 2, 0, width / 2, height);

		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString(score, width / 2 - 35, 30);

		ball.render(g);
		player1.render(g);
		player2.render(g);

	}

	public void start() {
		// Paddle é a requete do pong.
		player1 = new Paddle(this.width, this.height, 1);
		player2 = new Paddle(this.width, this.height, 2);
		ball = new Ball(this.width, this.height);
	}

	public static void main(String[] args) {
		pong = new Pong();
	}

	private void update() {
		if (w)
			player1.moveUp();
		if (s)
			player1.moveDown();

		if (up)
			player2.moveUp();
		if (down)
			player2.moveDown();
		if (space && gameStatus != 2)
			gameStatus = 2;
		if (space && gameStatus == 2)
			gameStatus = 1;

		// Quando a bola bate na parede oposta à do jogador, o jogador faz um
		// ponto
		// por isso está ao contrário:
		player1.setScore(ball.getHitWall2());
		player2.setScore(ball.getHitWall1());
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		update();

		if (gameStatus == 2) {
			ball.update(player1, player2);
		}
		renderer.repaint();
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		int id = keyEvent.getKeyCode();

		if (id == KeyEvent.VK_W) {
			this.w = true;
		}
		if (id == KeyEvent.VK_S) {
			this.s = true;
		}
		if (id == KeyEvent.VK_UP) {
			this.up = true;
		}
		if (id == KeyEvent.VK_DOWN) {
			this.down = true;
		}
		if (id == KeyEvent.VK_SPACE && gameStatus == 0) {
			space = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		int id = keyEvent.getKeyCode();

		if (id == KeyEvent.VK_W) {
			this.w = false;
		}
		if (id == KeyEvent.VK_S) {
			this.s = false;
		}
		if (id == KeyEvent.VK_UP) {
			this.up = false;
		}
		if (id == KeyEvent.VK_DOWN) {
			this.down = false;
		}
		if (id == KeyEvent.VK_SPACE && gameStatus == 0) {
			space = false;
		}
	}
}