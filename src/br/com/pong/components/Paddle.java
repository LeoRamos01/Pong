package br.com.pong.components;

import java.awt.Color;
import java.awt.Graphics;

import br.com.pong.Pong;

//Paddle é a requete do pong.
public class Paddle {
	private int paddleNumber;
	private int horizontal, vertical, width = 20, height = 100;
	private Integer score = 0;
	private final int speed = 6;

	public int getPaddleNumber() {
		return paddleNumber;
	}

	public void setPaddleNumber(int paddleNumber) {
		this.paddleNumber = paddleNumber;
	}

	public String getScore() {
		return score.toString();
	}

	public int getScoreValue() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHorizontal() {
		return horizontal;
	}

	public int getVertical() {
		return vertical;
	}

	public Paddle(int pongWidth, int pongHeight, int paddleNumber) {
		this.paddleNumber = paddleNumber;

		if (paddleNumber == 1) {
			// Canto superior esquerdo da tela
			this.horizontal = 0;
		} else if (paddleNumber == 2) {
			// Canto inferior direito da tela
			this.horizontal = pongWidth - width;
		}

		this.vertical = pongHeight / 2 - height / 2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void render(Graphics g) {
		// Cor das palhetas do pong
		g.setColor(Color.WHITE);
		g.fillRect(horizontal, vertical, width, height);
	}

	public void moveUp() {
		if (vertical - speed > 0)
			vertical -= speed;
		else
			vertical = 0;
	}

	public void moveDown() {
		if (vertical + height + speed < Pong.pong.getHeight())
			vertical += speed;
		else
			vertical = Pong.pong.getHeight() - height;
	}
}