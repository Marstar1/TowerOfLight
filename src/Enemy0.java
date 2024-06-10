import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class Enemy0 extends Enemy {
    private JProgressBar healthBar;

    public Enemy0(ImageIcon imageIcon, int width, int height, int maxHealth) {
        super(imageIcon, width, height, maxHealth);
        this.healthBar = createHealthBar(width - 100);
        this.add(healthBar);
    }

    public JProgressBar createHealthBar(int barWidth) {
        healthBar = new JProgressBar(0, maxHealth);
        healthBar.setBounds(1150, 820, barWidth, 20);
        healthBar.setStringPainted(true);
        healthBar.setValue(health);
        healthBar.setAlignmentX(SwingConstants.CENTER);
        healthBar.setForeground(Color.CYAN);
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
            playSound2();
            int healAmount = random.nextInt(3) + 5;
            setHealth(Math.min(health + healAmount, maxHealth));
            updateHealthBar();
            System.out.println("Enemy healed itself for " + healAmount + " health.");
        } else {
            // Deal damage to the hero
            playSound3();
            int Edamage = random.nextInt(3) + 5; // Random damage between 10 and 30

            if (hero.getShield() > 0) {
                int remainingDamage = Edamage - hero.getShield();
                if (remainingDamage > 0) {
                    hero.setShield(0);
                    hero.setHealth1(Math.min(hero.getHealth1() - remainingDamage, hero.getMaxHealth1()));
                } else {
                    hero.setShield(hero.getShield() - Edamage);
                }
                hero.updateShieldBar();

            } else {
                hero.setHealth1(Math.min(hero.getHealth1() - Edamage, hero.getMaxHealth1()));
            }

            System.out.println("Enemy dealt " + Edamage + " damage to the hero.");
        }
    }
    private void playSound3() {
        try {
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(new File("Music/cloud.wav")));

            FloatControl volume = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f); // Уменьшаем громкость на 10 децибел

            soundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void playSound2() {
        try {
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(new File("Music/heal.wav")));

            FloatControl volume = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f); // Уменьшаем громкость на 10 децибел

            soundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideHealthBar() {
        healthBar.setVisible(false);
    }
    public void seeHealthBar(){
        healthBar.setVisible(true);
    }

}