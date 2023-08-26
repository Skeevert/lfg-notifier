package lfg.notifier.java;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frontend extends JFrame {
    private static final String BUTTON_ORIG_MSG = "Start monitoring LFG search";

    private JLabel dirSelectorLabel;
    private JButton dirPathButton;
    private JLabel currentPathLabel;

    private JCheckBox exitOnFindCheckBox;
    private JCheckBox playSoundNotification;


    private JButton startMonitoringButton;

    private JFileChooser chooser;
    private File currentlySelectedDir;

    public Frontend() {
        super("LFG notifier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dirSelectorLabel = new JLabel("Select WoW Screenshots directory:");
        currentPathLabel = new JLabel("Path is not selected");

        dirPathButton = new JButton("Browse", null);
        exitOnFindCheckBox = new JCheckBox("Exit when the dungeon is found", null, false);
        playSoundNotification = new JCheckBox("Play sound notification", null, false);
        startMonitoringButton = new JButton(BUTTON_ORIG_MSG, null);

        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        JPanel listPane = new JPanel();
        listPane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        listPane.add(dirSelectorLabel, c);

        c.gridx = 1;
        listPane.add(dirPathButton, c);

        c.gridx = 0;
        c.gridy = 1;
        listPane.add(currentPathLabel, c);

        c.gridy = 2;
        listPane.add(exitOnFindCheckBox, c);

        c.gridy = 3;
        listPane.add(playSoundNotification, c);

        c.gridy = 4;
        c.weightx = 0.0;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.PAGE_END;
        listPane.add(startMonitoringButton, c);

        getContentPane().add(listPane);
        setDirectorySelectionCallback();

        pack();
    }

    public void resetState() {
        startMonitoringButton.setEnabled(true);
        startMonitoringButton.setText(BUTTON_ORIG_MSG);
    }

    public void setSearchStartCallback(Runnable callback) {
        startMonitoringButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                callback.run();
                startMonitoringButton.setEnabled(false);
                startMonitoringButton.setText("Watching...");
            }
        });
    }

    private void setDirectorySelectionCallback() {
        dirPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = chooser.showOpenDialog(getContentPane());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    currentlySelectedDir = chooser.getSelectedFile();
                    currentPathLabel.setText(currentlySelectedDir.getPath());
                }
            }
        });
    }

    public File getSelectedDir() {
        return currentlySelectedDir;
    }

    public void processFoundDungeon() {
        if (playSoundNotification.isSelected()) {
            try {
                InputStream audioSrc = Frontend.class.getResourceAsStream("/alarm.wav");
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedIn);
                
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Dungeon found");
        JOptionPane.showMessageDialog(null, "LFG proposal detected, there's a dungeon waiting for you. Hurry up!", "Dungeon found", JOptionPane.WARNING_MESSAGE);

        if (exitOnFindCheckBox.isSelected()) {
            System.exit(0);
        }
    }
}
