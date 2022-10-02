package com.itsanikethere.noughtsandcrosses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Aniket Panchal
 * @since 02-10-2022
 */
@SuppressWarnings("ALL")
final class Main extends JFrame implements ActionListener {

    private static boolean firstRun = true;
    final private Misc GAME_MISC = new Misc();
    final private JLabel GAME_HEADER = new JLabel();
    final private JLabel GAME_FOOTER = new JLabel();
    final private JPanel GAME_PANEL = new JPanel();
    final private JButton[] GAME_BUTTONS = new JButton[9];
    final private ImageIcon NOUGHT_ICON = GAME_MISC.getGameIcon(
            "GAME_ICONS/PLAYER_ICONS/PLAYER1_ICON.png"
    );
    final private ImageIcon CROSS_ICON = GAME_MISC.getGameIcon(
            "GAME_ICONS/PLAYER_ICONS/PLAYER2_ICON.png"
    );
    private boolean crossTurn;

    Main() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.exit(1);
        }

        setIconImage(GAME_MISC.getGameIcon(
                "APP_ICON.png"
        ).getImage());
        setSize(new Dimension(500, 500));
        setLayout(new BorderLayout());
        setTitle("Noughts & Crosses");

        GAME_HEADER.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        GAME_HEADER.setHorizontalAlignment(SwingConstants.CENTER);
        GAME_HEADER.setText("Noughts & Crosses");

        GAME_FOOTER.setIcon(GAME_MISC.getGameIcon(
                "GAME_ICONS/LABEL_ICONS/ORIGINATOR_ICON.png"
        ));
        GAME_FOOTER.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        GAME_FOOTER.setHorizontalAlignment(SwingConstants.CENTER);
        GAME_FOOTER.setText("Originator >> Aniket Panchal");

        UIManager.put("Label.font", new Font("Comic Sans MS", Font.BOLD, 15));
        UIManager.put("Button.font", new Font("Comic Sans MS", Font.BOLD, 13));

        GAME_PANEL.setLayout(new GridLayout(3, 3, 0, 0));
        for (int i = 0; i < GAME_BUTTONS.length; i++) {
            GAME_BUTTONS[i] = new JButton();
            GAME_BUTTONS[i].setEnabled(false);
            GAME_BUTTONS[i].setFocusable(false);
            GAME_BUTTONS[i].addActionListener(this);
            GAME_PANEL.add(GAME_BUTTONS[i]);
        }

        themeSelect();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        gameStart();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void themeSelect() {
        UIManager.put("Panel.background", Color.WHITE);

        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", Color.BLACK);

        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);

        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);

        final String[] THEME_OPTIONS = {
                "Light",
                "Dark"
        };

        themeApply(JOptionPane.showOptionDialog(
                GAME_PANEL, "Select Game Mode?",
                "Noughts & Crosses", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, GAME_MISC.getGameIcon(
                        "GAME_ICONS/OPTION_PANE_ICONS/THEME_ICON.png"
                ),
                THEME_OPTIONS, null
        ));
    }

    private void themeApply(final int THEME_CHOICE) {
        if (THEME_CHOICE == 0 || THEME_CHOICE == -1) {
            GAME_HEADER.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 5,
                    Color.BLACK));
            GAME_HEADER.setBackground(Color.WHITE);
            GAME_HEADER.setForeground(Color.BLACK);

            GAME_FOOTER.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 5,
                    Color.BLACK));
            GAME_FOOTER.setBackground(Color.WHITE);
            GAME_FOOTER.setForeground(Color.BLACK);

            GAME_PANEL.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
                    Color.BLACK));
            for (JButton button : GAME_BUTTONS) {
                button.setBackground(Color.WHITE);
            }
        } else {
            GAME_HEADER.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 5,
                    Color.WHITE));
            GAME_HEADER.setBackground(Color.BLACK);
            GAME_HEADER.setForeground(Color.WHITE);

            GAME_FOOTER.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 5,
                    Color.WHITE));
            GAME_FOOTER.setBackground(Color.BLACK);
            GAME_FOOTER.setForeground(Color.WHITE);

            GAME_PANEL.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
                    Color.WHITE));
            for (JButton button : GAME_BUTTONS) {
                button.setBackground(Color.BLACK);
            }

            UIManager.put("Panel.background", Color.BLACK);

            UIManager.put("OptionPane.background", Color.BLACK);
            UIManager.put("OptionPane.messageForeground", Color.WHITE);

            UIManager.put("Button.background", Color.BLACK);
            UIManager.put("Button.foreground", Color.WHITE);
        }
        GAME_HEADER.setOpaque(true);
        GAME_FOOTER.setOpaque(true);

        add(GAME_HEADER, BorderLayout.NORTH);
        add(GAME_FOOTER, BorderLayout.SOUTH);
        add(GAME_PANEL, BorderLayout.CENTER);
    }

    private void gameStart() {
        if (firstRun) {
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.exit(1);
            }
        }
        for (JButton button : GAME_BUTTONS) {
            button.setEnabled(true);
        }
        if (GAME_MISC.getGameTurn()) {
            GAME_HEADER.setIcon(CROSS_ICON);
            crossTurn = true;
        } else {
            GAME_HEADER.setIcon(NOUGHT_ICON);
            crossTurn = false;
        }
        GAME_HEADER.setText("'s Turn");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button : GAME_BUTTONS) {
            if (button == e.getSource()) {
                if (button.getIcon() == null) {
                    if (crossTurn) {
                        button.setIcon(CROSS_ICON);
                        if (gameWinCheck()) {
                            break;
                        }
                        GAME_HEADER.setIcon(NOUGHT_ICON);
                        crossTurn = false;
                    } else {
                        button.setIcon(NOUGHT_ICON);
                        if (gameWinCheck()) {
                            break;
                        }
                        GAME_HEADER.setIcon(CROSS_ICON);
                        crossTurn = true;
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            GAME_PANEL, "Invalid Selection",
                            "Noughts & Crosses", JOptionPane.ERROR_MESSAGE,
                            GAME_MISC.getGameIcon(
                                    "GAME_ICONS/OPTION_PANE_ICONS" +
                                            "/MOVE_INVALID_ICON.png"
                            )
                    );
                }
            }
        }
    }

    private boolean gameWinCheck() {
        if (GAME_BUTTONS[0].getIcon() == CROSS_ICON && GAME_BUTTONS[1].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[2].getIcon() == CROSS_ICON) {
            gameCrossWins(0, 1, 2);
            return true;
        } else if (GAME_BUTTONS[3].getIcon() == CROSS_ICON && GAME_BUTTONS[4].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[5].getIcon() == CROSS_ICON) {
            gameCrossWins(3, 4, 5);
            return true;
        } else if (GAME_BUTTONS[6].getIcon() == CROSS_ICON && GAME_BUTTONS[7].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[8].getIcon() == CROSS_ICON) {
            gameCrossWins(6, 7, 8);
            return true;
        } else if (GAME_BUTTONS[0].getIcon() == CROSS_ICON && GAME_BUTTONS[3].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[6].getIcon() == CROSS_ICON) {
            gameCrossWins(0, 3, 6);
            return true;
        } else if (GAME_BUTTONS[1].getIcon() == CROSS_ICON && GAME_BUTTONS[4].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[7].getIcon() == CROSS_ICON) {
            gameCrossWins(1, 4, 7);
            return true;
        } else if (GAME_BUTTONS[2].getIcon() == CROSS_ICON && GAME_BUTTONS[5].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[8].getIcon() == CROSS_ICON) {
            gameCrossWins(2, 5, 8);
            return true;
        } else if (GAME_BUTTONS[0].getIcon() == CROSS_ICON && GAME_BUTTONS[4].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[8].getIcon() == CROSS_ICON) {
            gameCrossWins(0, 4, 8);
            return true;
        } else if (GAME_BUTTONS[2].getIcon() == CROSS_ICON && GAME_BUTTONS[4].getIcon() == CROSS_ICON &&
                GAME_BUTTONS[6].getIcon() == CROSS_ICON) {
            gameCrossWins(2, 4, 6);
            return true;
        } else if (GAME_BUTTONS[0].getIcon() == NOUGHT_ICON && GAME_BUTTONS[1].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[2].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(0, 1, 2);
            return true;
        } else if (GAME_BUTTONS[3].getIcon() == NOUGHT_ICON && GAME_BUTTONS[4].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[5].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(3, 4, 5);
            return true;
        } else if (GAME_BUTTONS[6].getIcon() == NOUGHT_ICON && GAME_BUTTONS[7].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[8].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(6, 7, 8);
            return true;
        } else if (GAME_BUTTONS[0].getIcon() == NOUGHT_ICON && GAME_BUTTONS[3].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[6].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(0, 3, 6);
            return true;
        } else if (GAME_BUTTONS[1].getIcon() == NOUGHT_ICON && GAME_BUTTONS[4].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[7].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(1, 4, 7);
            return true;
        } else if (GAME_BUTTONS[2].getIcon() == NOUGHT_ICON && GAME_BUTTONS[5].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[8].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(2, 5, 8);
            return true;
        } else if (GAME_BUTTONS[0].getIcon() == NOUGHT_ICON && GAME_BUTTONS[4].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[8].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(0, 4, 8);
            return true;
        } else if (GAME_BUTTONS[2].getIcon() == NOUGHT_ICON && GAME_BUTTONS[4].getIcon() == NOUGHT_ICON &&
                GAME_BUTTONS[6].getIcon() == NOUGHT_ICON) {
            gameNoughtWin(2, 4, 6);
            return true;
        } else if (GAME_BUTTONS[0].getIcon() != null && GAME_BUTTONS[1].getIcon() != null &&
                GAME_BUTTONS[2].getIcon() != null && GAME_BUTTONS[3].getIcon() != null &&
                GAME_BUTTONS[4].getIcon() != null && GAME_BUTTONS[5].getIcon() != null &&
                GAME_BUTTONS[6].getIcon() != null && GAME_BUTTONS[7].getIcon() != null &&
                GAME_BUTTONS[8].getIcon() != null) {
            gameDraw();
            return true;
        }
        return false;
    }

    private void gameCrossWins(final int... BUTTON_INDEX) {
        for (int index : BUTTON_INDEX) {
            GAME_BUTTONS[index].setBackground(Color.GREEN);
        }
        for (JButton button : GAME_BUTTONS) {
            button.setEnabled(false);
        }
        GAME_HEADER.setText("'s Wins");
        gameReplay();
    }

    private void gameNoughtWin(final int... BUTTON_INDEX) {
        for (int index : BUTTON_INDEX) {
            GAME_BUTTONS[index].setBackground(Color.GREEN);
        }
        for (JButton button : GAME_BUTTONS) {
            button.setEnabled(false);
        }
        GAME_HEADER.setText("'s Wins");
        gameReplay();
    }

    private void gameDraw() {
        for (JButton button : GAME_BUTTONS) {
            button.setEnabled(false);
        }
        GAME_HEADER.setIcon(GAME_MISC.getGameIcon(
                "GAME_ICONS/LABEL_ICONS/DRAW_ICON.png"
        ));
        GAME_HEADER.setText("Game Draw");
        gameReplay();
    }

    private void gameReplay() {
        if (JOptionPane.showConfirmDialog(
                GAME_PANEL, "Wanna Replay?",
                "Noughts & Crosses", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, GAME_MISC.getGameIcon(
                        "GAME_ICONS/OPTION_PANE_ICONS/REPLAY_ICON.png"
                )
        ) == 0) {
            dispose();

            firstRun = false;
            new Main();
        }
    }

}