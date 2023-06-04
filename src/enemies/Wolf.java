package enemies;

import static helpz.Constants.Enemies.WOLF;

import managers.EnemyManager;

public class Wolf extends Enemy{
	public Wolf(float x, float y, int ID, int enemyType, EnemyManager em) {
		super(x, y, ID, WOLF, em);

	}
	

}