package objects;

import static helpz.Constants.Towers.*;

public class Tower {
	
	private int x, y, id, towerType, cdTick, dmg;
	private float range, cooldown;
	private int tier;
	
	public Tower(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		tier = 1;
		setDefaultDmg();
		setDefaultRange();
		setDefaultCooldown();
	}
	
	public void update() {
		cdTick++;
	}
	
	public void upgradeTower() {
		tier++;
		
		switch(towerType) {
		case ARCHER :
			dmg += 2;
			range += 25;
			cooldown -= 2;
			break;
		case CANNON :
			dmg += 3;
			range += 20;
			cooldown -= 10;
			break;
		case WIZARD :
			dmg += 0;
			range += 40;
			cooldown -= 15;
			break;
		}
	}
	
	public boolean isCooldownOver() {
		return cdTick >= cooldown;
	}

	public void resetCooldown() {
		cdTick = 0;
	}

	private void setDefaultCooldown() {
		cooldown = helpz.Constants.Towers.GetDefaultCooldown(towerType);
	}

	private void setDefaultRange() {
		range = helpz.Constants.Towers.GetDefaultRange(towerType);		
	}

	private void setDefaultDmg() {
		dmg = helpz.Constants.Towers.GetDefaultDmg(towerType);		
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTowerType() {
		return towerType;
	}

	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}

	public int getDmg() {
		return dmg;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}
	public int getTier() {
		return tier;
	}
}
