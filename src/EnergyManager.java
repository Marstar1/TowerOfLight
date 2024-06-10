import javax.swing.*;
import java.awt.*;

public class EnergyManager {

    private JLabel energyLabel;

    public int finalCurrentEnergy = 3;

    public void displayEnergyIcon(JPanel panel) {
        ImageIcon energyIcon = new ImageIcon("Images/Кристал энергии(" + finalCurrentEnergy + ").png");
        int energyIconWidth = 90;
        int energyIconHeight = 90;
        energyLabel = new JLabel(new ImageIcon(energyIcon.getImage().getScaledInstance(energyIconWidth, energyIconHeight, Image.SCALE_SMOOTH)));
        energyLabel.setBounds(90, 850 - energyIconHeight, energyIconWidth, energyIconHeight);
        panel.add(energyLabel);
    }
    public void updateEnergyLabel(String currentEnergyIconPath, JPanel panel){
        if (energyLabel == null) {
            displayEnergyIcon(panel);}

        ImageIcon newEnergyIcon = new ImageIcon(currentEnergyIconPath);
        int energyIconWidth = 90;
        int energyIconHeight = 90;
        //JLabel energyLabel = new JLabel(new ImageIcon(newEnergyIcon.getImage().getScaledInstance(energyIconWidth, energyIconHeight, Image.SCALE_SMOOTH)));
        energyLabel.setIcon(new ImageIcon(newEnergyIcon.getImage().getScaledInstance(energyIconWidth, energyIconHeight, Image.SCALE_SMOOTH)));
        //energyLabel.setBounds(90, 850 - energyIconHeight, energyIconWidth, energyIconHeight);
        panel.revalidate();
    }
    public void hideEnergy() {
        energyLabel.setVisible(false);
    }
    public void seeEnergy() {
        energyLabel.setVisible(true);
    }

}
