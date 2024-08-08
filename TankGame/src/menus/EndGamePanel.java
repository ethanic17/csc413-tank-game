package TankGame.src.menus;

import TankGame.src.Launcher;
import TankGame.src.game.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;
        this.menuBackground = ResourceManager.getSprite("menu");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JLabel whoWon = new JLabel("Game Over! Player " +  lf.getWinner() +  " Wins!"); // get winner from game panel
        whoWon.setFont(new Font("Courier New", Font.BOLD, 28));
        whoWon.setBounds(50, 260, 600, 50);
        whoWon.setForeground(Color.WHITE);

        JButton start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 300, 250, 50);
        start.addActionListener((actionEvent -> this.lf.setFrame("game")));

        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(150, 400, 250, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(whoWon);
        this.add(start);
        this.add(exit);
    }

    public void updateWinner(int winner) {
        JLabel whoWon = (JLabel) this.getComponent(0);
        whoWon.setText("Game Over! Player " +  winner +  " Wins!");
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
