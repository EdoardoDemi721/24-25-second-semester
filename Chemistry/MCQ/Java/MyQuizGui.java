import java.awt.*;
import javax.swing.*;

public class MyQuizGui {

    public String ask(String question) {
        return JOptionPane.showInputDialog(null, question, "Input", JOptionPane.QUESTION_MESSAGE);
    }

    public String multiChoice(String question, String[] options) {
        final String[] result = {null};

        JFrame frame = new JFrame("Multiple Choice");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("<html>" + question.replaceAll("\n", "<br>") + "</html>");
        frame.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                result[0] = option;
                frame.dispose();
            });
            buttonPanel.add(button);
        }

        JButton skipButton = new JButton("Skip");
        skipButton.addActionListener(e -> {
            result[0] = "z";
            frame.dispose();
        });
        buttonPanel.add(skipButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Wait until user closes frame
        while (result[0] == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Sarcastico commento: sì certo, come se ci fregasse qualcosa dell’interrupt...
            }
        }

        return result[0];
    }

    public void display(String message) {
        final boolean[] done = {false};

        JFrame frame = new JFrame("Display");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("<html>" + message.replaceAll("\n", "<br>") + "</html>");
        frame.add(label, BorderLayout.CENTER);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            done[0] = true;
            frame.dispose();
        });

        frame.add(nextButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (!done[0]) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Ma figurati se ci svegliamo
            }
        }
    }
}
