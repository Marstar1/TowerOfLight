import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Menu {
    private JFrame frame;
    private Clip clip;
    private boolean isMusicPlaying;

    public void start() {
        SwingUtilities.invokeLater(this::run);
    }

    private void playMusic(String fileName) {
        try {
            File musicFile = new File(fileName);
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(musicFile));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            isMusicPlaying = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() {
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImageIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/background8.png");
                Image backgroundImage = backgroundImageIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(null);

        JButton startButton = new JButton();
        startButton.setBounds(600, 450, 240, 80);
        startButton.setContentAreaFilled(false);
        ImageIcon startButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Начатьигру.png");
        startButton.setIcon(new ImageIcon(startButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        startButton.setHorizontalTextPosition(JButton.CENTER);
        startButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(startButton);


        JButton musicButton = new JButton();
        musicButton.setBounds(600, 550, 240, 80);
        musicButton.setContentAreaFilled(false);
        ImageIcon musicButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Музыка.png");
        musicButton.setIcon(new ImageIcon(musicButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        musicButton.setHorizontalTextPosition(JButton.CENTER);
        musicButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(musicButton);

        JButton exitButton = new JButton();
        exitButton.setBounds(600, 650, 240, 80);
        exitButton.setContentAreaFilled(false);
        ImageIcon exitButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Выход.png");
        exitButton.setIcon(new ImageIcon(exitButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(exitButton);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose(); // Close the menu window

                RogueLikeGame game = new RogueLikeGame();
                game.start();
                clip.stop();
                isMusicPlaying = false;
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        musicButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isMusicPlaying) {
                    clip.stop();
                    isMusicPlaying = false;
                } else {
                    clip.start();
                    isMusicPlaying = true;
                }
            }
        });

        playMusic("C:/Users/Зяйка/Desktop/Курсач/224.wav");

        frame.add(panel);
        frame.setVisible(true);
    }
}
