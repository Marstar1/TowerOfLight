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

    private JPanel panel;
    private JButton startButton;
    private JButton musicButton;
    private JButton ruleButton;
    private JButton exitButton;
    private JLabel ruleImageLabel;
    private JButton returnButton;

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

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImageIcon = new ImageIcon("Images/background8.png");
                Image backgroundImage = backgroundImageIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            }
        };

        panel.setLayout(null);

        startButton = new JButton();
        startButton.setBounds(600, 450, 240, 80);
        startButton.setContentAreaFilled(false);
        ImageIcon startButtonIcon = new ImageIcon("Images/Начатьигру.png");
        startButton.setIcon(new ImageIcon(startButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        startButton.setHorizontalTextPosition(JButton.CENTER);
        startButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(startButton);


        musicButton = new JButton();
        musicButton.setBounds(600, 550, 240, 80);
        musicButton.setContentAreaFilled(false);
        ImageIcon musicButtonIcon = new ImageIcon("Images/Музыка.png");
        musicButton.setIcon(new ImageIcon(musicButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        musicButton.setHorizontalTextPosition(JButton.CENTER);
        musicButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(musicButton);

        ruleButton = new JButton();
        ruleButton.setBounds(600, 650, 240, 80);
        ruleButton.setContentAreaFilled(false);
        ImageIcon ruleButtonIcon = new ImageIcon("Images/Правила.png");
        ruleButton.setIcon(new ImageIcon(ruleButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        ruleButton.setHorizontalTextPosition(JButton.CENTER);
        ruleButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(ruleButton);

        returnButton = new JButton();
        returnButton.setBounds(600, 720, 240, 80);
        returnButton.setContentAreaFilled(false);
        ImageIcon returnButtonIcon = new ImageIcon("Images/Вменю.png");
        returnButton.setIcon(new ImageIcon(returnButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
        returnButton.setHorizontalTextPosition(JButton.CENTER);
        returnButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(returnButton);
        returnButton.setVisible(false);

        exitButton = new JButton();
        exitButton.setBounds(600, 750, 240, 80);
        exitButton.setContentAreaFilled(false);
        ImageIcon exitButtonIcon = new ImageIcon("Images/Выход.png");
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

        ruleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hideButtons();
                showRuleImage();
                returnButton.setVisible(true);
            }
        });

        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showButtons();
                hideRuleImage();
                returnButton.setVisible(false);
            }
        });

        playMusic("Music/224.wav");

        frame.add(panel);
        frame.setVisible(true);
    }

    private void hideButtons() {
        startButton.setVisible(false);
        musicButton.setVisible(false);
        ruleButton.setVisible(false);
        exitButton.setVisible(false);
    }
    private void showButtons() {
        startButton.setVisible(true);
        musicButton.setVisible(true);
        ruleButton.setVisible(true);
        exitButton.setVisible(true);
    }

    private void showRuleImage() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        ruleImageLabel = new JLabel();
        ruleImageLabel.setBounds(210, 120, 1120, 600);
        ImageIcon ruleImageIcon = new ImageIcon("Images/Rules.png");
        ruleImageLabel.setIcon(new ImageIcon(ruleImageIcon.getImage().getScaledInstance(1120, 600, Image.SCALE_SMOOTH)));

        layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(ruleImageLabel, JLayeredPane.PALETTE_LAYER);

        frame.setContentPane(layeredPane);
        frame.revalidate();
        frame.repaint();
    }

    private void hideRuleImage() {
        frame.getContentPane().removeAll(); // Удалить все компоненты из контента фрейма
        frame.add(panel); // Добавить панель обратно

        // Показать все кнопки
        startButton.setVisible(true);
        musicButton.setVisible(true);
        ruleButton.setVisible(true);
        exitButton.setVisible(true);

        returnButton.setVisible(false); // Скрыть кнопку returnButton

        frame.revalidate(); // Перестроить компоненты фрейма
        frame.repaint(); // Перерисовать фрейм
    }

}