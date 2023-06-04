package helpz;

public class Constants {
	
	public static class Projectiles {
		
		public static final int ARROW = 0;
		public static final int CHAINS = 1;
		public static final int BOMB = 2;

		
		public static float GetSpeed(int type) {
			switch(type) {
			case ARROW:
				return 28f;
			case BOMB:
				return 4f;
			case CHAINS:
				return 7f;
			}
			return 0f;
		}
		
	}
	public static class Towers{
		public static final int CANNON = 0;
		public static final int ARCHER = 1;
		public static final int WIZARD = 2;
		
		public static int GetTowerCost(int towerType) {
			switch(towerType) {
			case CANNON:
				return 140;
			case ARCHER:
				return 60;
			case WIZARD:
				return 120;
			}
			return 0;
		}
		public static String GetName(int towerType){
			switch(towerType) {
			case CANNON:
				return "Launcher";
			case ARCHER:
				return "Marine";
			case WIZARD:
				return "Space Mage";
			}
			return "";
		}
		
		public static int GetDefaultDmg(int towerType) {
			switch(towerType) {
			case CANNON:
				return 20;
			case ARCHER:
				return 3;
			case WIZARD:
				return 5;
			}
			return 0;
		}
		
		public static float GetDefaultRange(int towerType) {
			switch(towerType) {
			case CANNON:
				return 100;
			case ARCHER:
				return 150;
			case WIZARD:
				return 100;
			}
			return 0;
		}
		
		public static float GetDefaultCooldown(int towerType) {
			switch(towerType) {
			case CANNON:
				return 120;
			case ARCHER:
				return 25;
			case WIZARD:
				return 60;
			}
			return 0;
		}
				
	}
	
	public static class Direction{
		public static final int LEFT =0;
		public static final int UP =1;
		public static final int RIGHT =2;
		public static final int DOWN =3;

	}
	public static class Enemies {
		
		public static final int ORC = 0;
		public static final int BAT = 1;
		public static final int KNIGHT = 2;
		public static final int WOLF = 3;
		
		public static int GetReward(int enemyType) {
			switch(enemyType) {
			case(ORC):
				return 15;
			case(BAT):
				return 10;
			case(KNIGHT):
				return 30;
			case(WOLF):
				return 20;
			}
			
			return 0;
		}
		
		public static float GetSpeed(int enemyType) {
			switch(enemyType) {
			case(ORC):
				return 0.6f;
			case(BAT):
				return 1f;
			case(KNIGHT):
				return 0.6f;
			case(WOLF):
				return 0.75f;
			}
			
			return 0;
		}
		
		public static int GetStartHealth(int enemyType) {
			switch(enemyType) {
			case(ORC):
				return 300;
			case(BAT):
				return 100;
			case(KNIGHT):
				return 750;
			case(WOLF):
				return 200;
			}
			
			return 0;
		}
	}
	public static class Tiles{
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}
}
