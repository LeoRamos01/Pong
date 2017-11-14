package br.com.pong.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import br.com.pong.Pong;

public class Ball {

	private int horizontal, vertical, width, height;
	private int initHorizontal, initVertical;
	private int speed = 8;
	private Random geradorAleatorio = new Random();
	private boolean right, up;
	private int hitWall1 = 0, hitWall2 = 0;

	public Ball(int pongWidth, int pongHeight) {
		this.right = false;
		this.width = 20;
		this.height = 20;
		this.horizontal = this.initHorizontal = pongWidth / 2 - this.width / 2;
		this.vertical = this.initVertical = pongHeight / 2 - this.height / 2;
		initDirections();
	}

	private void initDirections() {
		switch (geradorAleatorio.nextInt(4)) {
		case 0:
			this.right = false;
			this.up = false;
			break;
		case 1:
			this.right = true;
			this.up = false;
			break;
		case 2:
			this.right = true;
			this.up = true;
			break;
		case 3:
			this.right = false;
			this.up = true;
			break;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(horizontal, vertical, width, height);
	}

	public void update(Paddle paddle1, Paddle paddle2) {
		if (checkCollision(paddle1) == 1) {

			this.right = true;
			this.up = geradorAleatorio.nextInt(10) >= 5;

		} else if (checkCollision(paddle1) == 2) {
			this.horizontal = this.initHorizontal;
			this.vertical = this.initVertical;
			hitWall1++;
			initDirections();
		} else if (checkCollision(paddle2) == 1) {

			this.right = false;
			this.up = geradorAleatorio.nextInt(10) >= 5;

		} else if (checkCollision(paddle2) == 2) {
			this.horizontal = this.initHorizontal;
			this.vertical = this.initVertical;
			hitWall2++;
			initDirections();
		} else if (checkCollision(paddle1) == 3) {
			this.up = !this.up;
		}

		move();
	}

	public int getHitWall1() {
		return hitWall1;
	}

	public int getHitWall2() {
		return hitWall2;
	}

	public int checkCollision(Paddle paddle) {

		if (this.vertical + this.height >= Pong.pong.getHeight() || this.vertical <= 0) {
			return 3;
		}

		if (this.horizontal < paddle.getHorizontal() + paddle.getWidth()
				&& this.horizontal + this.width > paddle.getHorizontal()
				&& this.vertical < paddle.getVertical() + paddle.getHeight()
				&& this.vertical + this.height > paddle.getVertical())
			return 1;
		else if ((paddle.getHorizontal() > this.horizontal + this.width && paddle.getPaddleNumber() == 1)
				|| (paddle.getHorizontal() < this.horizontal - this.width && paddle.getPaddleNumber() == 2))
			return 2;

		return 0;
	}

	private void move() {
		if (this.right)
			this.horizontal += this.speed;
		else
			this.horizontal -= this.speed;
		if (this.up)
			this.vertical += this.speed;
		else
			this.vertical -= this.speed;
	}
}