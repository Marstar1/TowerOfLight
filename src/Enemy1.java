import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class Enemy1 extends Enemy {
    private JProgressBar healthBar2;
    private boolean isMusicPlaying;

    public Enemy1(ImageIcon imageIcon, int width, int height, int maxHealth) {
        super(imageIcon, width, height, maxHealth);
        this.healthBar2 = createHealthBar2(width - 100);
        this.add(healthBar2);
    }

    public JProgressBar createHealthBar2(int barWidth) {
        healthBar2 = new JProgressBar(0, maxHealth);
        healthBar2.setBounds(1150, 820, barWidth, 20);
        healthBar2.setStringPainted(true);
        healthBar2.setValue(health);
        healthBar2.setAlignmentX(SwingConstants.CENTER);
        healthBar2.setForeground(Color.BLUE);
        healthBar2.setString(health + "/" + maxHealth);
        return healthBar2;
    }

    public void updateHealthBar() {
        healthBar2.setValue(health);
        healthBar2.setString(health + "/" + maxHealth);
        healthBar2.repaint();
    }

    public void performRandomAction() {

        Random random = new Random();
        int action = random.nextInt(2); // Generates 0 or 1 randomly

        if (action == 0) {
            // Heal itself
            playSound2();
            int healAmount = random.nextInt(5) + 10;
            setHealth(Math.min(health + healAmount, maxHealth));
            updateHealthBar();
            System.out.println("Enemy healed itself for " + healAmount + " health.");
        } else {
            // Deal damage to the hero
            playSound1();
            int Edamage1 = random.nextInt(5) + 10; // Random damage between 10 and 30

            if (hero.getShield() > 0) {
                int remainingDamage = Edamage1 - hero.getShield();
                if (remainingDamage > 0) {
                    hero.setShield(0);
                    hero.setHealth1(Math.min(hero.getHealth1() - remainingDamage, hero.getMaxHealth1()));
                } else {
                    hero.setShield(hero.getShield() - Edamage1);
                }
                hero.updateShieldBar();

            } else {
                hero.setHealth1(Math.min(hero.getHealth1() - Edamage1, hero.getMaxHealth1()));
            }

            System.out.println("Enemy dealt " + Edamage1 + " damage to the hero.");
        }
    }
    private void playSound1() {
        try {
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(new File("Music/punch.wav")));

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
            volume.setValue(-20.0f); // Уменьшаем громкость на 20 децибел

            soundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideHealthBar() {
        healthBar2.setVisible(false);
    }
    public void seeHealthBar(){
        healthBar2.setVisible(true);
    }
    public void playMusic() {
        try {
            File musicFile = new File("Music/battle2.wav");
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