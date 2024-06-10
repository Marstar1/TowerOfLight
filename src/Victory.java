import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Victory {
    private JFrame frame;
    private Clip clip;
    private boolean isMusicPlaying;

    private JPanel panel;
    private JButton exitButton;

    public void start() {
        SwingUtilities.invokeLater(this::run);
    }
    private void playMusic(String fileName) {
        try {
            File musicFile = new File(fileName);
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(musicFile));

            // Получение управления громкостью
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float currentVolume = gainControl.getValue(); // Текущая громкость
            float decreaseVolume = 15.0f; // Уменьшение громкости на 25 децибел
            gainControl.setValue(currentVolume - decreaseVolume); // Установка новой громкости

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            isMusicPlaying = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void run() {
        frame = new JFrame("End");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImageIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/victory.png");
                Image backgroundImage = backgroundImageIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            }
        };

        panel.setLayout(null);

        exitButton = new JButton();
        exitButton.setBounds(600, 750, 240, 80);
        exitButton.setContentAreaFilled(false);
        ImageIcon exitButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Выход.png");
        exitButton.setIcon(new ImageIcon(exitButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(exitButton);


        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        playMusic("C:/Users/Зяйка/Desktop/Курсач/endtheme.wav");
        
        frame.add(panel);
        frame.setVisible(true);
    }
}