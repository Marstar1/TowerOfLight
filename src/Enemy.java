import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends JLabel {
    private int health;
    private int maxHealth;
    private JProgressBar healthBar;
    private Hero hero;
    public Enemy(ImageIcon imageIcon, int width, int height, int maxHealth) {
        super(imageIcon);
        this.setSize(width, height);
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.healthBar = createHealthBar(width - 100);
        this.add(healthBar);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
        updateHealthBar();
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        updateHealthBar();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public JProgressBar createHealthBar(int barWidth) {
        healthBar = new JProgressBar(0, maxHealth);
        healthBar.setBounds(1150, 820, barWidth, 20);
        healthBar.setStringPainted(true);
        healthBar.setValue(health);
        healthBar.setAlignmentX(SwingConstants.CENTER);
        healthBar.setForeground(Color.CYAN);
        healthBar.setValue(health);
        healthBar.setString(health + "/" + maxHealth);
        return healthBar;
    }

    public void updateHealthBar() {
        healthBar.setValue(health);
        healthBar.setString(health + "/" + maxHealth);
        healthBar.repaint();
    }

    public void performRandomAction() {
        Random random = new Random();
        int action = random.nextInt(2); // Generates 0 or 1 randomly

        if (action == 0) {
            // Heal itself
            int healAmount = random.nextInt(5) + 5;
            setHealth(Math.min(health + healAmount, maxHealth));
            System.out.println("Enemy healed itself for " + healAmount + " health.");
        } else {
            // Deal damage to the hero
            int Edamage = random.nextInt(10) + 10; // Random damage between 10 and 30
            hero.setHealth1(Math.min(hero.health - Edamage, hero.maxHealth));
            System.out.println("Enemy dealt " + Edamage + " damage to the hero.");

        }
    }
}

