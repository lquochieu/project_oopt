package TaHuuBinh;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class JFileChooserExam1 {
 
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
 
    public JFileChooserExam1() {
        prepareGUI();
    }
 
    public static void main(String[] args) {
        JFileChooserExam1 demo = new JFileChooserExam1();
        demo.showFileChooserDemo();
    }
 
    private void prepareGUI() {
        mainFrame = new JFrame("Vi du JFileChooser trong Java Swing");
        mainFrame.setSize(500, 250);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }
 
    private void showFileChooserDemo() {
        headerLabel.setText("Control in action: JFileChooser");
        final JFileChooser fileDialog = new JFileChooser();
        JButton showFileDialogButton = new JButton("Open File");
        showFileDialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    statusLabel.setText("File Selected :" + file.getName());
                } else {
                    statusLabel.setText("Open command cancelled by user.");
                }
            }
        });
        controlPanel.add(showFileDialogButton);
        mainFrame.setVisible(true);
    }
}