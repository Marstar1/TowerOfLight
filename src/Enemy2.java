import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;
import java.util.Random;
import java.awt.*;

public class Enemy2 extends Enemy {
    private JProgressBar healthBar3;
    private boolean isMusicPlaying;

    public Enemy2(ImageIcon imageIcon, int width, int height, int maxHealth) {
        super(imageIcon, width, height, maxHealth);
        this.healthBar3 = createHealthBar3(width - 100);
        this.add(healthBar3);
    }

    public JProgressBar createHealthBar3(int barWidth) {
        healthBar3 = new JProgressBar(0, maxHealth);
        healthBar3.setBounds(1150, 820, barWidth, 20);
        healthBar3.setStringPainted(true);
        healthBar3.setValue(health);
        healthBar3.setAlignmentX(SwingConstants.CENTER);
        healthBar3.setForeground(Color.ORANGE);
        healthBar3.setString(health + "/" + maxHealth);
        return healthBar3;
    }

    public void updateHealthBar() {
        healthBar3.setValue(health);
        healthBar3.setString(health + "/" + maxHealth);
        healthBar3.repaint();
    }

    public void performRandomAction() {

        Random random = new Random();
        int action = random.nextInt(2); // Generates 0 or 1 randomly

        if (action == 0) {
            // Heal itself
            playSound2();
            int healAmount = random.nextInt(10) + 15;
            setHealth(Math.min(health + healAmount, maxHealth));
            updateHealthBar();
            System.out.println("Enemy healed itself for " + healAmount + " health.");
        } else {
            // Deal damage to the hero
            playSound4();
            int Edamage2 = random.nextInt(10) + 8; // Random damage between 10 and 30

            if (hero.getShield() > 0) {
                int remainingDamage = Edamage2 - hero.getShield();
                if (remainingDamage > 0) {
                    hero.setShield(0);
                    hero.setHealth1(Math.min(hero.getHealth1() - remainingDamage, hero.getMaxHealth1()));
                } else {
                    hero.setShield(hero.getShield() - Edamage2);
                }
                hero.updateShieldBar();

            } else {
                hero.setHealth1(Math.min(hero.getHealth1() - Edamage2, hero.getMaxHealth1()));
            }

            System.out.println("Enemy dealt " + Edamage2 + " damage to the hero.");
        }
    }
    private void playSound4() {
        try {
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(new File("Music/angel.wav")));

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
        healthBar3.setVisible(false);
    }
    public void seeHealthBar(){
        healthBar3.setVisible(true);
    }
    public void playMusic() {
        try {
            File musicFile = new File("Music/battle3.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(musicFile));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f);
            clip.start();
            isMusicPlaying = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}