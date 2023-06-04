package ui;

import static main.GameStates.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import enemies.Enemy;
import helpz.Constants.Towers;
import objects.Tower;
import scenes.Playing;

public class ActionBar extends Bar {

	private Playing playing;
	private MyButton bMenu, bPause;

	private MyButton[] towerButtons;
	private Tower selectedTower;
	private Tower displayedTower;
	private MyButton sellTower, upgradeTower;

	private DecimalFormat formatter;

	private int gold = 200;
	private boolean showTowerCost;
	private int towerCostType;
	
	private int lives=5;

	public ActionBar(int x, int y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		formatter = new DecimalFormat("0.0");

		initButtons();
	}
	

	public void resetEverything() {
		lives = 5;
		towerCostType = 0;
		showTowerCost = false;
		gold = 200;
		displayedTower = null;
	}
	private void initButtons() {

		bMenu = new MyButton("Menu", 2, 642, 100, 30);
		bPause = new MyButton("Pause", 2, 675, 100, 30);


		towerButtons = new MyButton[3];

		int w = 50;
		int h = 50;
		int xStart = 110;
		int yStart = 650;
		int xOffset = (int) (w * 1.1f);

		for (int i = 0; i < towerButtons.length; i++)
			towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);

		sellTower = new MyButton("Sell", 420, 702, 80, 25);
		upgradeTower = new MyButton("Upgrade", 545, 702, 80, 25);
	}
	
	public void removeOneLife() {
		lives--;
		if(lives <= 0) {
			SetGameState(GAME_OVER);
		}
	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bPause.draw(g);

		for (MyButton b : towerButtons) {

			g.setColor(Color.gray);
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);

			drawButtonFeedback(g, b);
		}
	}

	public void draw(Graphics g) {

		// Background
		g.setColor(new Color(133, 90, 166));
		g.fillRect(x, y, width, height);

		// Buttons
		drawButtons(g);

		// Displayedtower
		drawDisplayedTower(g);

		// Wave Info
		drawWaveInfo(g);

		drawGoldAmount(g);

		if (showTowerCost)
			drawTowerCost(g);
		
		if(playing.isGamePaused()) {
			g.setColor(Color.black);
			g.drawString("Game is Paused !", 110, 780);
		}
		
		g.setColor(Color.white);
		g.drawString("Lives : " + lives, 110, 750);
	}

	private void drawTowerCost(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(280, 650, 125, 50);
		g.setColor(Color.black);
		g.drawRect(280, 650, 125, 50);

		g.drawString("" + getTowerCostName(), 285, 670);
		g.drawString("Cost :" + getTowerCostCost() + "g", 285, 695);

		if (isTowerCostMoreThanCurrentGold()) {
			g.setColor(Color.white);
			g.drawString("T'es pauvre", 285, 725);
		}
	}

	private boolean isTowerCostMoreThanCurrentGold() {
		return getTowerCostCost() > gold;
	}

	private int getTowerCostCost() {
		return helpz.Constants.Towers.GetTowerCost(towerCostType);
	}

	private String getTowerCostName() {
		return helpz.Constants.Towers.GetName(towerCostType);
	}

	private void drawGoldAmount(Graphics g) {
		g.drawString("Gold : " + gold + "g", 110, 725);
	}

	private void drawWaveInfo(Graphics g) {
		g.setFont(new Font("LucidaSans", Font.BOLD, 18));
		g.setColor(Color.white);
		drawWaveTimerInfo(g);
		if (playing.getWaveManager().isWaveTimerStarted() == false) {
			drawWavesLeftInfo(g);
			drawEnemiesLeftInfo(g);
		}
	}

	private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveManager().getWaveIndex();
		int size = playing.getWaveManager().getWaves().size();
		g.drawString("Wave " + (current + 1) + " / " + size, 480, 760);

	}

	private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
		g.drawString("Enemies Left : " + remaining, 480, 780);
	}

	private void drawWaveTimerInfo(Graphics g) {
		if (playing.getWaveManager().isWaveTimerStarted()) {
			float timeLeft = playing.getWaveManager().getTimeLeft();
			String formattedText = formatter.format(timeLeft);
			g.drawString("Time Left: " + formattedText, 500, 770);
		}
	}

	private void drawDisplayedTower(Graphics g) {
		if (displayedTower != null) {
			g.setColor(new Color(97, 35, 146));
			g.fillRect(410, 645, 220, 85);
			g.setColor(Color.gray);
			g.fillRect(420, 650, 50, 50);
			g.setColor(Color.black);
			g.drawRect(410, 645, 220, 85);
			g.drawRect(420, 650, 50, 50);
			g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()], 420, 650, 50, 50,
					null);
			g.setFont(new Font("LucidaSans", Font.BOLD, 15));
			g.drawString("" + Towers.GetName(displayedTower.getTowerType()), 485, 660);
			g.drawString("Dmg : " + displayedTower.getDmg(), 485, 677);
			g.drawString("Range : " + (int)displayedTower.getRange(), 543, 677);
			getTowerTier(g);

			drawDisplayedTowerBorder(g);
			drawDisplayedTowerRange(g);

			sellTower.draw(g);
			drawButtonFeedback(g, sellTower);

			if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)) {
				upgradeTower.draw(g);
				drawButtonFeedback(g, upgradeTower);
			}

			if (sellTower.isMouseOver()) {
				g.setColor(Color.yellow);
				g.drawString("Sell for: " + getSellAmount(displayedTower) + "g", 485, 695);
			} else if (upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)) {
				g.setColor(Color.black);
				g.drawString("Upgrade for: " + getUpgradeAmount(displayedTower) + "g", 485, 695);

			}
		}
	}

	private void getTowerTier(Graphics g) {
		int tier = displayedTower.getTier();
		for(int i = 0; i<tier; i++) {
			g.drawString("I", 595 + i*5, 660);
		}
	}

	private int getUpgradeAmount(Tower displayedTower) {
		return (int) (helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) * 0.33f);
	}
	
	private int getSellAmount(Tower displayedTower) {
		int upgradeCost = (displayedTower.getTier()-1)* getUpgradeAmount(displayedTower);
		upgradeCost *= 0.33f;
		
		return helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType())/10*9 + upgradeCost;
	}

	private void drawDisplayedTowerRange(Graphics g) {
		g.setColor(Color.white);
		g.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2,
				displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2,
				(int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);
	}

	private void drawDisplayedTowerBorder(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);

	}

	public void displayTower(Tower t) {
		displayedTower = t;
	}

	private void sellTowerClicked() {		
		playing.removeTower(displayedTower);
		
		int upgradeCost = (displayedTower.getTier()-1)* getUpgradeAmount(displayedTower);
		upgradeCost *= 0.33f;
		
		gold += (helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType())*0.9f) + upgradeCost;
		displayedTower = null;
	}

	private void upgradeTowerClicked() {
		if(gold >= getUpgradeAmount(displayedTower)){
		playing.upgradeTower(displayedTower);
		gold -= getUpgradeAmount(displayedTower);
		}
	}
	

	private void togglePause() {
		playing.setGamePaused(!playing.isGamePaused());
		
		if(playing.isGamePaused())
			bPause.setText("UnPause");
		else
			bPause.setText("Pause");
	}
	
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			playing.resetAll();
		}
		else if(bPause.getBounds().contains(x, y))
			togglePause();
		else {
			if (displayedTower != null) {
				if (sellTower.getBounds().contains(x, y)) {
					sellTowerClicked();
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
					upgradeTowerClicked();
					return;
				}
			}
			for (MyButton b : towerButtons) {
				if (b.getBounds().contains(x, y)) {
					if (!isGoldEnoughForTower(b.getId()))
						return;

					selectedTower = new Tower(0, 0, -1, b.getId());
					playing.setSelectedTower(selectedTower);
					return;
				}
			}

		}

	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			togglePause();
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_1) {
			if (!isGoldEnoughForTower(0))
				return;
			selectedTower = new Tower(0, 0, -1, 0);
			playing.setSelectedTower(selectedTower);		
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			if (!isGoldEnoughForTower(1))
				return;
			selectedTower = new Tower(0, 0, -1, 1);
			playing.setSelectedTower(selectedTower);		
		}
		if(e.getKeyCode() == KeyEvent.VK_3) {
			if (!isGoldEnoughForTower(2))
				return;
			selectedTower = new Tower(0, 0, -1, 2);
			playing.setSelectedTower(selectedTower);		
		}
		
		
		if (displayedTower != null) {
			if(e.getKeyCode() == KeyEvent.VK_A && displayedTower.getTier() < 3)
				upgradeTowerClicked();
			if(e.getKeyCode() == KeyEvent.VK_S)
				sellTowerClicked();
		}
	}
			


	private boolean isGoldEnoughForTower(int towerType) {
		return gold >= helpz.Constants.Towers.GetTowerCost(towerType);
	}

	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		showTowerCost = false;
		sellTower.setMouseOver(false);
		upgradeTower.setMouseOver(false);

		for (MyButton b : towerButtons)
			b.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if(bPause.getBounds().contains(x, y))
			bPause.setMouseOver(true);
		else {
			if (displayedTower != null) {
				if (sellTower.getBounds().contains(x, y)) {
					sellTower.setMouseOver(true);
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
					upgradeTower.setMouseOver(true);
					return;
				}
			}
			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMouseOver(true);
					towerCostType = b.getId();
					showTowerCost = true;
					return;
				}
		}
	}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if(bPause.getBounds().contains(x, y))
			bPause.setMousePressed(true);
		else {

			if (displayedTower != null) {
				if (sellTower.getBounds().contains(x, y)) {
					sellTower.setMousePressed(true);
					return;
				} else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
					upgradeTower.setMousePressed(true);
					return;
				}
			}
			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
		}

	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bPause.resetBooleans();
		
		for (MyButton b : towerButtons)
			b.resetBooleans();
		sellTower.resetBooleans();
		upgradeTower.resetBooleans();

	}

	public void payForTheTower(int towerType) {
		this.gold -= helpz.Constants.Towers.GetTowerCost(towerType);
	}

	public void addGold(int getReward) {
		this.gold += getReward;
	}
	
	public int getLives() {
		return lives;
	}


}
