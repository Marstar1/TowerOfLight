import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
    private List<Integer> enemyTypes;
    public static List<String> initialDeck;
    private int currentEnemyType;

    public RogueLikeGame() {
        enemyTypes = new ArrayList<>(Arrays.asList(0, 1, 2));
        Collections.shuffle(enemyTypes);

        initialDeck = new ArrayList<>(Arrays.asList(
                "Карта1.png", "Карта2.png", "Карта3.png", "Карта4.png", "Карта5.png",
                "Карта6.png", "Карта7.png", "Карта8.png", "Карта9.png", "Карта10.png",
                "Карта11.png", "Карта12.png", "Карта13.png"
        ));
    }

    private Clip clip;
    private boolean isMusicPlaying;
    public List<String> cardPaths;

    boolean flagTriggered = false;
    private JFrame frame;

    private int finalCurrentEnergy = 3;
    private int nextButtonClicks = 0;


    public void start() {
        System.out.println(initialDeck);
        finalCurrentEnergy = 3;
        int currentEnemyType = enemyTypes.get(0);
        this.currentEnemyType = currentEnemyType;
        enemyTypes.remove(0);
        List<String> initialCards = new ArrayList<>(initialDeck);
        Collections.shuffle(initialCards, new Random());

        List<Integer> cardIndices = new ArrayList<>();
        cardPaths = new ArrayList<>();
        for (int i = 0; i < initialDeck.size(); i++) {
            initialCards.add("Карта" + i + ".png");
            cardIndices.add(i+1);
        }
        Collections.shuffle(cardIndices);
        List<String> playerHand = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            playerHand.add(initialCards.get(i));
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
                    if (nextButtonClicks ==0){
                    ImageIcon backgroundImageIcon = new ImageIcon("Images/Background9.png");
                    Image backgroundImage = backgroundImageIcon.getImage();
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);}
                    else if (nextButtonClicks ==1){
                        ImageIcon backgroundImageIcon = new ImageIcon("Images/Background10.png");
                        Image backgroundImage = backgroundImageIcon.getImage();
                        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);}
                    else{
                        ImageIcon backgroundImageIcon = new ImageIcon("Images/Background11.png");
                        Image backgroundImage = backgroundImageIcon.getImage();
                        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);}
                    
                }
            };

            panel.setLayout(null);

            EnergyManager energyManager = new EnergyManager();
            energyManager.displayEnergyIcon(panel);

            Enemy enemy;

            if (currentEnemyType == 0) {
                ImageIcon enemyIcon = new ImageIcon("Images/Облачко.png");
                int enemyIconWidth = 320;
                int enemyIconHeight = 220;
                int enemyMaxHealth = 40;
                int enemyHealth = 40;

                Enemy0 enemy0 = new Enemy0(enemyIcon, enemyIconWidth, enemyIconHeight, enemyMaxHealth);
                enemy0.setBounds(1100, 820 - enemyIconHeight, enemyIconWidth, enemyIconHeight);
                enemy0.setIcon(new ImageIcon(enemyIcon.getImage().getScaledInstance(320, 220, Image.SCALE_SMOOTH)));
                enemy0.setHealth(enemyHealth);
                enemy0.setMaxHealth(enemyMaxHealth);
                panel.add(enemy0);
                panel.add(enemy0.createHealthBar(220));

                enemy = enemy0;
            } else if (currentEnemyType == 1) {
                ImageIcon enemy1Icon = new ImageIcon("Images/Разбойница2.png");
                int enemy1IconWidth = 250;
                int enemy1IconHeight = 360;
                int enemy1MaxHealth = 60;
                int enemy1Health = 60;

                Enemy1 enemy1 = new Enemy1(enemy1Icon, enemy1IconWidth, enemy1IconHeight, enemy1MaxHealth);
                enemy1.setBounds(1100, 825 - enemy1IconHeight, enemy1IconWidth, enemy1IconHeight);
                enemy1.setIcon(new ImageIcon(enemy1Icon.getImage().getScaledInstance(250, 360, Image.SCALE_SMOOTH)));
                enemy1.setHealth(enemy1Health);
                enemy1.setMaxHealth(enemy1MaxHealth);
                panel.add(enemy1);
                panel.add(enemy1.createHealthBar2(220));

                enemy = enemy1;
            } else {
                ImageIcon enemy2Icon = new ImageIcon("Images/Ангел3.png");
                int enemy2IconWidth = 320;
                int enemy2IconHeight = 500;
                int enemy2MaxHealth = 100;
                int enemy2Health = 100;

                Enemy2 enemy2 = new Enemy2(enemy2Icon, enemy2IconWidth, enemy2IconHeight, enemy2MaxHealth);
                enemy2.setBounds(1080, 840 - enemy2IconHeight, enemy2IconWidth, enemy2IconHeight);
                enemy2.setIcon(new ImageIcon(enemy2Icon.getImage().getScaledInstance(320, 500, Image.SCALE_SMOOTH)));
                enemy2.setHealth(enemy2Health);
                enemy2.setMaxHealth(enemy2MaxHealth);
                panel.add(enemy2);
                panel.add(enemy2.createHealthBar3(220));

                enemy = enemy2;
            }

            ImageIcon heroIcon = new ImageIcon("Images/Волшебница2.png");
            int heroIconWidth = 220;
            int heroIconHeight = 360;
            int heroMaxHealth = 40;
            int heroHealth = 40;


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
                String card = playerHand.get(i);
                String filename = "Images/" + card;
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

            JButton nextButton = new JButton("Следующий этаж");
            nextButton.setBounds(680, 430, 200, 60);
            nextButton.setForeground(Color.WHITE);
            nextButton.setOpaque(false);
            nextButton.setContentAreaFilled(false);
            nextButton.setBorderPainted(false);
            nextButton.setFocusPainted(false);
            nextButton.setFont(new Font("Verdana", Font.ITALIC, 15));
            nextButton.setHorizontalTextPosition(JButton.CENTER);
            nextButton.setVerticalTextPosition(JButton.CENTER);
            nextButton.setVisible(false);

            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    clip.stop();
                    isMusicPlaying = false;
                    nextButtonClicks++;
                    start(); // Обновляем игровой цикл
                }
            });

            JButton victoryButton = new JButton("Просветление");
            victoryButton.setBounds(680, 430, 200, 60);
            victoryButton.setForeground(Color.WHITE);
            victoryButton.setOpaque(false);
            victoryButton.setContentAreaFilled(false);
            victoryButton.setBorderPainted(false);
            victoryButton.setFocusPainted(false);
            victoryButton.setFont(new Font("Verdana", Font.ITALIC, 15));
            victoryButton.setHorizontalTextPosition(JButton.CENTER);
            victoryButton.setVerticalTextPosition(JButton.CENTER);
            victoryButton.setVisible(true);
            victoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    clip.stop();
                    isMusicPlaying = false;
                    Victory victory  = new Victory();
                    victory.start();
                }
            });


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
                changeButton.setForeground(Color.MAGENTA);
                changeButton.setContentAreaFilled(false);
                changeButton.setHorizontalTextPosition(JButton.CENTER);
                changeButton.setVerticalTextPosition(JButton.CENTER);
                changeButton.setVisible(true);
                panel.add(changeButton);

                JButton endButton = new JButton("Покинуть башню");
                endButton.setBounds(680, 430, 200, 60);
                endButton.setForeground(Color.WHITE);
                endButton.setContentAreaFilled(false);
                endButton.setHorizontalTextPosition(JButton.CENTER);
                endButton.setVerticalTextPosition(JButton.CENTER);
                endButton.setVisible(true);


                JLayeredPane layeredPane = new JLayeredPane();
                layeredPane.setLayout(null);
                layeredPane.setBounds(0, 0, 1920, 1080);
                layeredPane.setOpaque(false);
                panel.add(layeredPane);

                endButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        frame.dispose(); // Close the game window

                        Menu menu = new Menu();
                        menu.start();
                        clip.stop();
                        isMusicPlaying = false;
                    }
                });

                changeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        enemy.performRandomAction();
                        List<JLabel> newCardLabels = new ArrayList<>();
                        flagTriggered = true;
                        if (flagTriggered) {
                            finalCurrentEnergy = 3;

                            String currentEnergyIconPath = "Images/Кристал энергии("+finalCurrentEnergy+").png";
                            energyManager.updateEnergyLabel(currentEnergyIconPath, panel);

                            layeredPane.removeAll();
                            // Удалить все карты из панели
                            for (JLabel cardLabel : cardLabels) {
                                panel.remove(cardLabel);
                            }
                            cardLabels.clear();

                            List<String> initialCards = new ArrayList<>(initialDeck);
                            Collections.shuffle(initialCards, new Random());

                            List<Integer> cardIndices = new ArrayList<>();
                            cardPaths = new ArrayList<>();
                            for (int i = 1; i <= initialDeck.size(); i++) {
                                initialCards.add("Карта" + i + ".png");
                                cardIndices.add(i);
                            }
                            Collections.shuffle(cardIndices);
                            List<String> playerHand = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                playerHand.add(initialCards.get(i));
                            }

                            for (int i = 1; i <= initialDeck.size(); i++) {
                                initialCards.add("Карта" + i + ".png");
                                cardIndices.add(i);
                            }

                            // Создаем новые JLabel для каждой карты
                            for (int i = 0; i < playerHand.size(); i++) {
                                String filename1 = "Images/" + playerHand.get(i);
                                ImageIcon cardIcon = new ImageIcon(filename1);
                                Image image = cardIcon.getImage();
                                Image newImage = image.getScaledInstance(160, 270, Image.SCALE_SMOOTH);
                                ImageIcon newCardIcon = new ImageIcon(newImage);
                                JLabel newCardLabel = new JLabel(newCardIcon);

                                newCardLabel.putClientProperty("imagePath", filename1);
                                cardPaths.add(filename1);
                                newCardLabel.setBounds(560 + i * 80, 660, 160, 270);
                                newCardLabels.add(newCardLabel);

                                layeredPane.add(newCardLabel, Integer.valueOf(0));

                                // Применяем действия карты E, H, S
                                actions.applyCardActionE(filename1);
                                actions.applyCardActionH(filename1);
                                actions.applyCardActionS(filename1);
                            }

                            // Очистить newCardLabel от всех карт перед добавлением новых
                            for (JLabel cardLabel : newCardLabels) {
                                cardLabel.removeAll();
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
                                        String imagePath = (String) newCardLabel.getClientProperty("imagePath");
                                        CardGame cardGame = new CardGame();
                                        int cardCost = cardGame.cardCostMap.get(imagePath);
                                        if (finalCurrentEnergy < cardCost) {
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
                                            if (hero.getHealth1() < hero.getMaxHealth1()) {
                                                hero.setHealth1(Math.min(hero.getHealth1() + heal, hero.getMaxHealth1()));
                                            }
                                            hero.setShield(hero.getShield() + shield);
                                            panel.remove(newCardLabel);
                                            newCardLabels.remove(newCardLabel);
                                            layeredPane.remove(newCardLabel);

                                            enemy.updateHealthBar();
                                            hero.updateHealthBar1();
                                            hero.updateShieldBar();
                                        }

                                        String currentEnergyIconPath = "Images/Кристал энергии("+finalCurrentEnergy+").png";
                                        energyManager.updateEnergyLabel(currentEnergyIconPath, panel);

                                        if (enemy.getHealth() <= 0) {
                                            if (currentEnemyType == 0) {
                                                initialDeck.add("Карта14.png");
                                                // Добавьте соответствующие карты для enemy0
                                            } else if (currentEnemyType == 1) {
                                                initialDeck.add("Карта15.png");
                                                // Добавьте соответствующие карты для enemy1
                                            } else {
                                                initialDeck.add("Карта16.png");
                                                // Добавьте соответствующие карты для enemy2
                                            }
                                            JLabel victoryLabel = new JLabel("Победа!");
                                            victoryLabel.setFont(new Font("Arial", Font.BOLD, 45));
                                            victoryLabel.setForeground(Color.WHITE);
                                            victoryLabel.setBounds(690, 370, 400, 50);

                                            JPanel translucentPanel = new JPanel();
                                            translucentPanel.setBackground(new Color(90, 80, 150, 150)); // 150 is the alpha value for transparency
                                            translucentPanel.setBounds(580, 340, 400, 200);

                                            panel.removeAll();
                                            if (nextButtonClicks >= 2) {

                                                panel.remove(nextButton);
                                                panel.add(victoryButton);
                                            } else {
                                                panel.add(nextButton);
                                                nextButton.setVisible(true);
                                            }

                                            panel.add(victoryLabel);
                                            panel.add(translucentPanel);

                                        }

                                        panel.revalidate();
                                        panel.repaint();

                                    }

                                });
                            }
                            if (hero.getHealth1() <= 0) {
                                JLabel defeatLabel = new JLabel("<html><center> Всю жизнь герой стремился к свету</center><center> Но долбанулся об фонарь и канул в лету</center></html>");
                                defeatLabel.setFont(new Font("Verdana", Font.ITALIC, 25));
                                defeatLabel.setForeground(Color.WHITE);
                                defeatLabel.setBounds(540, 340, 600, 70);

                                // Creating a translucent panel behind the victory label
                                JPanel translucentPanel1 = new JPanel();
                                translucentPanel1.setBackground(new Color(200, 100, 50, 150)); // 150 is the alpha value for transparency
                                translucentPanel1.setBounds(500, 310, 600, 200);
                                panel.removeAll();
                                panel.add(defeatLabel);
                                panel.add(endButton);
                                panel.add(translucentPanel1);

                            }

                            panel.revalidate();
                            panel.repaint();
                            flagTriggered = false;
                            layeredPane.revalidate();
                            layeredPane.repaint();
                        }
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
                            if (hero.getHealth1() < hero.getMaxHealth1()) {
                                hero.setHealth1(Math.min(hero.getHealth1() + heal, hero.getMaxHealth1()));
                            }
                            hero.setShield(hero.getShield() + shield);
                            panel.remove(cardLabel);


                        }

                        enemy.updateHealthBar();

                        hero.updateHealthBar1();

                        hero.updateShieldBar();


                        String currentEnergyIconPath = "Images/Кристал энергии("+finalCurrentEnergy+").png";
                        energyManager.updateEnergyLabel(currentEnergyIconPath, panel);

                        if (enemy.getHealth() <= 0) {
                            if (currentEnemyType == 0) {
                                initialDeck.add("Карта14.png");
                                // Добавьте соответствующие карты для enemy0
                            } else if (currentEnemyType == 1) {
                                initialDeck.add("Карта15.png");
                                // Добавьте соответствующие карты для enemy1
                            } else {
                                initialDeck.add("Карта16.png");
                                // Добавьте соответствующие карты для enemy2
                            }
                            JLabel victoryLabel = new JLabel("Победа!");
                            victoryLabel.setFont(new Font("Arial", Font.BOLD, 45));
                            victoryLabel.setForeground(Color.WHITE);
                            victoryLabel.setBounds(690, 370, 400, 50);

                            JPanel translucentPanel = new JPanel();
                            translucentPanel.setBackground(new Color(90, 80, 150, 150));
                            translucentPanel.setBounds(580, 340, 400, 200);

                            panel.removeAll();
                            if (nextButtonClicks >= 2) {

                                panel.remove(nextButton);
                                panel.add(victoryButton);
                            } else {
                                panel.add(nextButton);
                                nextButton.setVisible(true);
                            }
                            panel.add(victoryLabel);
                            panel.add(translucentPanel);
                        }
                        if (hero.getHealth1() == 0) {
                            JLabel defeatLabel = new JLabel("<html><center> Всю жизнь герой стремился к свету</center><center> Но долбанулся об фонарь и канул в лету</center></html>");
                            defeatLabel.setFont(new Font("Verdana", Font.ITALIC, 25));
                            defeatLabel.setForeground(Color.WHITE);
                            defeatLabel.setBounds(540, 340, 600, 70);

                            // Creating a translucent panel behind the victory label
                            JPanel translucentPanel1 = new JPanel();
                            translucentPanel1.setBackground(new Color(200, 100, 50, 150)); // 150 is the alpha value for transparency
                            translucentPanel1.setBounds(500, 310, 600, 120);
                            panel.removeAll();
                            panel.add(defeatLabel);
                            panel.add(endButton);
                            panel.add(translucentPanel1);

                        }

                        panel.revalidate();
                        panel.repaint();

                    }
                });
            }


            JButton pauseButton = new JButton();
            pauseButton.setBounds(1350, 10, 180, 60);
            pauseButton.setContentAreaFilled(false);
            ImageIcon pauseButtonIcon = new ImageIcon("Images/Пауза.png");
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
            ImageIcon returnButtonIcon = new ImageIcon("Images/Вернуться.png");
            returnButton.setIcon(new ImageIcon(returnButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
            returnButton.setHorizontalTextPosition(JButton.CENTER);
            returnButton.setVerticalTextPosition(JButton.CENTER);
            overlayPanel.add(returnButton);
            returnButton.setVisible(false);

            JButton musicButton = new JButton();
            musicButton.setBounds(700, 420, 240, 80);
            musicButton.setContentAreaFilled(false);
            ImageIcon musicButtonIcon = new ImageIcon("Images/Музыка.png");
            musicButton.setIcon(new ImageIcon(musicButtonIcon.getImage().getScaledInstance(240, 80, Image.SCALE_SMOOTH)));
            musicButton.setHorizontalTextPosition(JButton.CENTER);
            musicButton.setVerticalTextPosition(JButton.CENTER);
            overlayPanel.add(musicButton);
            musicButton.setVisible(false);

            JButton exitButton = new JButton();
            exitButton.setBounds(750, 500, 240, 80);
            exitButton.setContentAreaFilled(false);
            ImageIcon exitButtonIcon = new ImageIcon("Images/Выход.png");
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
                    hero.hideHealthBar1();
                    hero.hideShieldBar();
                    energyManager.hideEnergy();
                    enemy.hideHealthBar();
                    hero.setVisible(false);
                    overlayPanel.setVisible(true);


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
                            energyManager.seeEnergy();
                            enemy.setVisible(true);
                            hero.setVisible(true);
                            hero.seeHealthBar1();
                            hero.seeShieldBar();
                            enemy.seeHealthBar();
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
            File musicFile = new File("Music/battle" + (currentEnemyType + 1) + ".wav");
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

    private void playSound() {
        try {
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(AudioSystem.getAudioInputStream(new File("Music/card.wav")));
            soundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}






