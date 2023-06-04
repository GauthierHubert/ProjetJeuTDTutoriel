package scenes;

import java.awt.image.BufferedImage;

import main.Game;

public class GameScene {

	protected Game game;
	protected int animationIndex;
	protected int ANIMATION_SPEED = 20;
	protected int tick;

	public GameScene(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
	
	protected boolean isAnimation(int spriteID) {
		return game.getTileManager().isSpriteAnimation(spriteID);
	}
	
	protected void updateTick() {
		tick++;
			if(tick >= ANIMATION_SPEED) {
				tick = 0;
				animationIndex++;
				if(animationIndex >=4)
					animationIndex = 0;
			}
		
	}

	protected BufferedImage getSprite(int spriteID, int animationIndex) {
		return game.getTileManager().getAnySprites(spriteID, animationIndex);
	}
	protected BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID);
	}
}
