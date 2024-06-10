import javax.swing.*;
import java.awt.*;

public class Hero extends JLabel {
    public int health;
    public int shield;
    public int maxHealth;
    private JProgressBar healthBar1;
    private JProgressBar shieldBar;



    public Hero(ImageIcon imageIcon, int width, int height, int maxHealth1) {
        super(imageIcon);
        this.setSize(width, height);
        this.health = maxHealth1;
        this.shield = 0;
        this.maxHealth = maxHealth1;
        this.healthBar1 = createHealthBar1(width - 100);
        this.shieldBar = createShieldBar(width - 100);
        this.add(healthBar1);
        this.add(shieldBar);
    }

    public int getHealth1() {
        return health;
    }

    public void setHealth1(int health1) {
        this.health = Math.max(health1, 0);
        updateHealthBar1();
    }
    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
        updateHealthBar1();
    }

    public int getMaxHealth1() {
        return maxHealth;
    }

    public void setMaxHealth1(int maxHealth) {
        this.maxHealth = maxHealth;
        updateHealthBar1();
    }

    public JProgressBar createHealthBar1(int barWidth) {
        healthBar1 = new JProgressBar(0, maxHealth);
        healthBar1.setBounds(200, 820, barWidth, 20);
        healthBar1.setStringPainted(true);
        healthBar1.setValue(health);
        healthBar1.setAlignmentX(SwingConstants.CENTER);
        healthBar1.setForeground(Color.MAGENTA);
        healthBar1.setValue(health);
        healthBar1.setString(health + "/" + maxHealth);
        return healthBar1;
    }
    public JProgressBar createShieldBar(int barWidth) {
        shieldBar = new JProgressBar(0, maxHealth);
        shieldBar.setBounds(200, 800, barWidth, 20); // настраиваем положение и размер полоски щита
        shieldBar.setStringPainted(true);
        shieldBar.setValue(0); // начальное значение щита
        shieldBar.setAlignmentX(SwingConstants.CENTER);
        shieldBar.setForeground(Color.GREEN); // цвет полоски щита
        shieldBar.setString("Защита:"); // текст на полоске щита
        return shieldBar;
    }


    public void updateHealthBar1() {
        healthBar1.setValue(health);
        healthBar1.setString(health + "/" + maxHealth);
        healthBar1.repaint();
    }

    public void updateShieldBar() {
        shieldBar.setValue(shield);
        shieldBar.setString("Защита:" + shield);
        shieldBar.repaint();
    }
    public void hideHealthBar1() {
        healthBar1.setVisible(false);
    }
    public void hideShieldBar() {
        shieldBar.setVisible(false);
    }
    public void seeHealthBar1() {
        healthBar1.setVisible(true);
    }
    public void seeShieldBar() {
        shieldBar.setVisible(true);
    }
}


