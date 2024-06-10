import javax.swing.*;

public abstract class Enemy extends JLabel {
    protected int maxHealth;
    protected int health;
    protected Hero hero;
    private boolean isMusicPlaying;

    public Enemy(ImageIcon icon, int width, int height, int maxHealth) {
        setIcon(icon);
        setSize(width, height);
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract void performRandomAction();

    public void updateHealthBar() {
    }
    public void hideHealthBar() {
    }
    public void seeHealthBar(){

    }
    private void playMusic() {

    }

}