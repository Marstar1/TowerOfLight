import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.*;

public class RogueLikeCardGame {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}

class RogueLikeGame {
    private JFrame frame;
    static JPanel panel;

    private Clip clip;
    private boolean isMusicPlaying;
    public List<String> cardPaths;

    boolean flagTriggered = false;

    private int finalCurrentEnergy = 3;

    public void start() {
        List<String> cards = new ArrayList<>();
        List<Integer> cardIndices = new ArrayList<>();
        cardPaths = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            cards.add("Карта" + i + ".png");
            cardIndices.add(i);
        }
        Collections.shuffle(cardIndices);
        Collections.shuffle(cards, new Random());
        List<String> playerHand = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            playerHand.add(cards.get(i));
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tower of Light");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1920, 1080);
            frame.setResizable(false);

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon backgroundImageIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Background10.png");
                    Image backgroundImage = backgroundImageIcon.getImage();
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    
                }
            };

            panel.setLayout(null);

            EnergyManager energyManager = new EnergyManager();
            energyManager.displayEnergyIcon(panel);

            ImageIcon enemyIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Облачко.png");
            int enemyIconWidth = 320;
            int enemyIconHeight = 220;
            int enemyMaxHealth = 60;
            int enemyHealth = 60;

            Enemy enemy = new Enemy(enemyIcon, enemyIconWidth, enemyIconHeight, enemyMaxHealth);
            enemy.setBounds(1100, 800 - enemyIconHeight, enemyIconWidth, enemyIconHeight);
            enemy.setIcon(new ImageIcon(enemyIcon.getImage().getScaledInstance(320, 220, Image.SCALE_SMOOTH)));
            enemy.setHealth(enemyHealth);
            enemy.setMaxHealth(enemyMaxHealth);
            panel.add(enemy);
            panel.add(enemy.createHealthBar(220));


            ImageIcon heroIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Волшебница2.png");
            int heroIconWidth = 220;
            int heroIconHeight = 360;
            int heroMaxHealth = 80;
            int heroHealth = 80;


            Hero hero = new Hero(heroIcon, heroIconWidth, heroIconHeight, heroMaxHealth);
            hero.setBounds(220, 800 - heroIconHeight, heroIconWidth, heroIconHeight);
            hero.setIcon(new ImageIcon(heroIcon.getImage().getScaledInstance(280, 400, Image.SCALE_SMOOTH)));
            hero.setHealth1(heroHealth);
            hero.setMaxHealth1(heroMaxHealth);
            panel.add(hero);
            panel.add(hero.createHealthBar1(220));
            panel.add(hero.createShieldBar(220));
            CardActions actions = new CardActions(enemy, hero);
            enemy.setHero(hero);

            List<JLabel> cardLabels = new ArrayList<>();
            for (int i = 0; i < playerHand.size(); i++) {
                String filename = ("C:/Users/Зяйка/Desktop/Курсач/" + playerHand.get(i));
                ImageIcon cardIcon = new ImageIcon(filename);
                Image image = cardIcon.getImage();
                Image newImage = image.getScaledInstance(160, 270, Image.SCALE_SMOOTH);
                ImageIcon newCardIcon = new ImageIcon(newImage);
                JLabel cardLabel = new JLabel(newCardIcon);
                cardLabel.putClientProperty("imagePath", filename);
                cardPaths.add(filename);
                cardLabel.setBounds(560 + i * 80, 660, 160, 270);
                cardLabels.add(cardLabel);
                panel.add(cardLabel, 0);

                actions.applyCardActionE(filename);
                actions.applyCardActionH(filename);
                actions.applyCardActionS(filename);

            }

            for (JLabel cardLabel : cardLabels) {
                Point initialLocation = cardLabel.getLocation();
                cardLabel.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        cardLabel.setLocation(e.getXOnScreen() - cardLabel.getWidth() / 2, e.getYOnScreen() - cardLabel.getHeight() / 2);
                    }
                });

                JButton changeButton = new JButton("Следующий ход");
                changeButton.setBounds(250, 400, 180, 60);
                changeButton.setContentAreaFilled(false);
                changeButton.setHorizontalTextPosition(JButton.CENTER);
                changeButton.setVerticalTextPosition(JButton.CENTER);
                changeButton.setVisible(false);
                panel.add(changeButton);

                changeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        enemy.performRandomAction();

                        flagTriggered = true;
                        if (flagTriggered){
                            finalCurrentEnergy = 3;

                            for (JLabel cardLabel : cardLabels) {
                                panel.remove(cardLabel);
                            }
                            cardLabels.clear();

                            List<String> cards = new ArrayList<>();
                            List<Integer> cardIndices = new ArrayList<>();
                            cardPaths = new ArrayList<>();
                            for (int i = 1; i <= 13; i++) {
                                cards.add("Карта" + i + ".png");
                                cardIndices.add(i);
                            }
                            Collections.shuffle(cardIndices);
                            Collections.shuffle(cards, new Random());

                            List<String> playerHand = new ArrayList<>();

                            // Выбираем 5 случайных карт для руки игрока
                            for (int i = 0; i < 5; i++) {
                                playerHand.add(cards.get(i));
                            }

                            List<JLabel> newCardLabels = new ArrayList<>();

                            // Создаем новые JLabel для каждой карты
                            for (int i = 0; i < playerHand.size(); i++) {
                                String filename1 = "C:/Users/Зяйка/Desktop/Курсач/" + playerHand.get(i);
                                ImageIcon cardIcon = new ImageIcon(filename1);
                                Image image = cardIcon.getImage();
                                Image newImage = image.getScaledInstance(160, 270, Image.SCALE_SMOOTH);
                                ImageIcon newCardIcon = new ImageIcon(newImage);
                                JLabel newCardLabel = new JLabel(newCardIcon);
                                newCardLabel.putClientProperty("imagePath", filename1);
                                cardPaths.add(filename1);
                                newCardLabel.setBounds(560 + i * 80, 660, 160, 270);
                                newCardLabels.add(newCardLabel);
                                panel.add(newCardLabel, 0);

                                // Применяем действия карты E, H, S
                                actions.applyCardActionE(filename1);
                                actions.applyCardActionH(filename1);
                                actions.applyCardActionS(filename1);
                            }

                            for (JLabel newCardLabel : newCardLabels) {
                                Point initialLocation = newCardLabel.getLocation();
                                newCardLabel.addMouseMotionListener(new MouseMotionAdapter() {
                                    @Override
                                    public void mouseDragged(MouseEvent e) {
                                        newCardLabel.setLocation(e.getXOnScreen() - newCardLabel.getWidth() / 2, e.getYOnScreen() - newCardLabel.getHeight() / 2);
                                    }
                                });
                                newCardLabel.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mousePressed(MouseEvent e) {
                                        playSound();
                                        newCardLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

                                        if (finalCurrentEnergy == 0) {
                                            changeButton.setVisible(true);
                                            System.out.println("Недостаточно энергии");
                                            newCardLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 7));

                                        }

                                    }

                                    @Override
                                    public void mouseReleased(MouseEvent e) {
                                        newCardLabel.setBorder(null);
                                        Timer timer = new Timer(10, null);
                                        timer.addActionListener(f -> {
                                            int dx = (initialLocation.x - newCardLabel.getX()) / 10;
                                            int dy = (initialLocation.y - newCardLabel.getY()) / 10;
                                            newCardLabel.setLocation(newCardLabel.getX() + dx, newCardLabel.getY() + dy);
                                            if (dx == 0 && dy == 0) {
                                                ((Timer) f.getSource()).stop();
                                            }
                                        });
                                        timer.start();

                                        String imagePath = (String) newCardLabel.getClientProperty("imagePath");
                                        int damage = actions.applyCardActionE(imagePath);
                                        int heal = actions.applyCardActionH(imagePath);
                                        int shield = actions.applyCardActionS(imagePath);
                                        CardGame cardGame = new CardGame();
                                        int cardCost = cardGame.cardCostMap.get(imagePath);
                                        if (cardGame.cardCostMap.containsKey(imagePath)) {
                                            System.out.println("Стоимость карты " + imagePath + ": " + cardCost);
                                        }
                                        if (finalCurrentEnergy < cardCost) {
                                            System.out.println("Недостаточно энергии: ");

                                        } else {
                                            finalCurrentEnergy -= cardCost;
                                            enemy.setHealth(enemy.getHealth() - damage);
                                            hero.setHealth1(hero.getHealth1() + heal);
                                            hero.setShield(hero.getShield() + shield);
                                            panel.remove(newCardLabel);

                                        }

                                        enemy.updateHealthBar();

                                        hero.updateHealthBar1();

                                        hero.updateShieldBar();


                                        String currentEnergyIconPath = "C:/Users/Зяйка/Desktop/Курсач/Кристал энергии("+finalCurrentEnergy+").png";
                                        energyManager.updateEnergyLabel(currentEnergyIconPath, panel);

                                        if (enemy.getHealth() <= 0) {
                                            JLabel victoryLabel = new JLabel("Победа!");
                                            victoryLabel.setFont(new Font("Arial", Font.BOLD, 45));
                                            victoryLabel.setForeground(Color.WHITE);
                                            victoryLabel.setBounds(690, 370, 320, 50);

                                            // Creating a translucent panel behind the victory label
                                            JPanel translucentPanel = new JPanel();
                                            translucentPanel.setBackground(new Color(50, 80, 150, 150)); // 150 is the alpha value for transparency
                                            translucentPanel.setBounds(580, 340, 400, 100);

                                            panel.add(victoryLabel);
                                            panel.add(translucentPanel);
                                            enemy.setVisible(false);

                                        }
                                        if (hero.getHealth1() <= 0) {
                                            JLabel defeatLabel = new JLabel("<html><center> Всю жизнь герой стремился к свету</center><center> Но долбанулся об фонарь и канул в лету</center></html>");
                                            defeatLabel.setFont(new Font("Arial", Font.BOLD, 25));
                                            defeatLabel.setForeground(Color.WHITE);
                                            defeatLabel.setBounds(550, 340, 600, 70);

                                            // Creating a translucent panel behind the victory label
                                            JPanel translucentPanel1 = new JPanel();
                                            translucentPanel1.setBackground(new Color(200, 100, 50, 150)); // 150 is the alpha value for transparency
                                            translucentPanel1.setBounds(500, 310, 600, 120);

                                            panel.add(defeatLabel);
                                            panel.add(translucentPanel1);
                                            hero.setVisible(false);

                                        }

                                        panel.revalidate();
                                        panel.repaint();
                                    }

                                });

                            }
                            flagTriggered = false;
                        }
                        panel.revalidate();
                        panel.repaint();
                    }

                });

                cardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        playSound();
                        cardLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

                        if (finalCurrentEnergy == 0) {
                            changeButton.setVisible(true);
                            System.out.println("Недостаточно энергии");
                            cardLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 7));

                        }

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        cardLabel.setBorder(null);
                        Timer timer = new Timer(10, null);
                        timer.addActionListener(f -> {
                            int dx = (initialLocation.x - cardLabel.getX()) / 10;
                            int dy = (initialLocation.y - cardLabel.getY()) / 10;
                            cardLabel.setLocation(cardLabel.getX() + dx, cardLabel.getY() + dy);
                            if (dx == 0 && dy == 0) {
                                ((Timer) f.getSource()).stop();
                            }
                        });
                        timer.start();

                        String imagePath = (String) cardLabel.getClientProperty("imagePath");
                        int damage = actions.applyCardActionE(imagePath);
                        int heal = actions.applyCardActionH(imagePath);
                        int shield = actions.applyCardActionS(imagePath);
                        CardGame cardGame = new CardGame();
                        int cardCost = cardGame.cardCostMap.get(imagePath);
                        if (cardGame.cardCostMap.containsKey(imagePath)) {
                            System.out.println("Стоимость карты " + imagePath + ": " + cardCost);
                        }
                        if (finalCurrentEnergy < cardCost) {
                            System.out.println("Недостаточно энергии: ");

                        } else {
                            finalCurrentEnergy -= cardCost;
                            enemy.setHealth(enemy.getHealth() - damage);
                            hero.setHealth1(hero.getHealth1() + heal);
                            hero.setShield(hero.getShield() + shield);
                            panel.remove(cardLabel);

                        }

                        enemy.updateHealthBar();

                        hero.updateHealthBar1();

                        hero.updateShieldBar();


                        String currentEnergyIconPath = "C:/Users/Зяйка/Desktop/Курсач/Кристал энергии("+finalCurrentEnergy+").png";
                        energyManager.updateEnergyLabel(currentEnergyIconPath, panel);

                        if (enemy.getHealth() <= 0) {
                            JLabel victoryLabel = new JLabel("Победа!");
                            victoryLabel.setFont(new Font("Arial", Font.BOLD, 45));
                            victoryLabel.setForeground(Color.WHITE);
                            victoryLabel.setBounds(690, 370, 320, 50);

                            // Creating a translucent panel behind the victory label
                            JPanel translucentPanel = new JPanel();
                            translucentPanel.setBackground(new Color(50, 80, 150, 150)); // 150 is the alpha value for transparency
                            translucentPanel.setBounds(580, 340, 400, 100);

                            panel.add(victoryLabel);
                            panel.add(translucentPanel);
                            enemy.setVisible(false);

                        }
                        if (hero.getHealth1() <= 0) {
                            JLabel defeatLabel = new JLabel("<html><center> Всю жизнь герой стремился к свету</center><center> Но долбанулся об фонарь и канул в лету</center></html>");
                            defeatLabel.setFont(new Font("Arial", Font.BOLD, 25));
                            defeatLabel.setForeground(Color.WHITE);
                            defeatLabel.setBounds(550, 340, 600, 70);

                            // Creating a translucent panel behind the victory label
                            JPanel translucentPanel1 = new JPanel();
                            translucentPanel1.setBackground(new Color(200, 100, 50, 150)); // 150 is the alpha value for transparency
                            translucentPanel1.setBounds(500, 310, 600, 120);

                            panel.add(defeatLabel);
                            panel.add(translucentPanel1);
                            hero.setVisible(false);

                        }

                        panel.revalidate();
                        panel.repaint();

                    }
                });
            }


            JButton pauseButton = new JButton();
            pauseButton.setBounds(1350, 10, 180, 60);
            pauseButton.setContentAreaFilled(false);
            ImageIcon pauseButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Пауза.png");
            pauseButton.setIcon(new ImageIcon(pauseButtonIcon.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH)));
            pauseButton.setHorizontalTextPosition(JButton.CENTER);
            pauseButton.setVerticalTextPosition(JButton.CENTER);
            panel.add(pauseButton);

            JPanel overlayPanel = new JPanel();
            overlayPanel.setBackground(new Color(150, 70, 95, 150));
            overlayPanel.setBounds(0, 0, 1920, 1080);
            overlayPanel.setLayout(null);
            panel.add(overlayPanel);
            overlayPanel.setVisible(false);

            JButton returnButton = new JButton();
            returnButton.setBounds(650, 340, 240, 80);
            returnButton.setContentAreaFilled(false);
            ImageIcon returnButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Вернуться.png");
            returnButton.setIcon(new ImageIcon(returnButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
            returnButton.setHorizontalTextPosition(JButton.CENTER);
            returnButton.setVerticalTextPosition(JButton.CENTER);
            overlayPanel.add(returnButton);
            returnButton.setVisible(false);

            JButton musicButton = new JButton();
            musicButton.setBounds(700, 420, 240, 80);
            musicButton.setContentAreaFilled(false);
            ImageIcon musicButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Музыка.png");
            musicButton.setIcon(new ImageIcon(musicButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
            musicButton.setHorizontalTextPosition(JButton.CENTER);
            musicButton.setVerticalTextPosition(JButton.CENTER);
            overlayPanel.add(musicButton);
            musicButton.setVisible(false);

            JButton exitButton = new JButton();
            exitButton.setBounds(750, 500, 240, 80);
            exitButton.setContentAreaFilled(false);
            ImageIcon exitButtonIcon = new ImageIcon("C:/Users/Зяйка/Desktop/Курсач/Выход.png");
            exitButton.setIcon(new ImageIcon(exitButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
            exitButton.setHorizontalTextPosition(JButton.CENTER);
            exitButton.setVerticalTextPosition(JButton.CENTER);
            overlayPanel.add(exitButton);
            exitButton.setVisible(false);

            pauseButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    overlayPanel.setVisible(true);
                    returnButton.setVisible(true);
                    musicButton.setVisible(true);
                    exitButton.setVisible(true);
                    pauseButton.setVisible(false);

                    for (JLabel cardLabel : cardLabels) {
                        cardLabel.setVisible(false);
                    }
                    enemy.setVisible(false);
                    hero.setVisible(false);


                    returnButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            overlayPanel.setVisible(false);
                            returnButton.setVisible(false);
                            musicButton.setVisible(false);
                            exitButton.setVisible(false);
                            pauseButton.setVisible(true);

                            for (JLabel cardLabel : cardLabels) {
                                cardLabel.setVisible(true);
                            }
                            //energy.setVisible(true);
                            enemy.setVisible(true);
                            hero.setVisible(true);
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
                }
            });
            playMusic();

            frame.add(panel);
            frame.setVisible(true);
        });
    }


    private void playMusic() {
        try {
            File musicFile = new File("C:/Users/Зяйка/Desktop/Курсач/battle.wav");
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(musicFile));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            isMusicPlaying = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSound() {
        try {
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(new File("C:/Users/Зяйка/Desktop/Курсач/card.wav")));
            soundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}






