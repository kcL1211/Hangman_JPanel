package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;

/* TODO:
 * limit letters in guessTextField to 1
 * insert correct letters into arraylist in proper spots
 * allocate spaces for correct/incorrect (_)
 *
 */

public class Hangman {
    //word to be guessed
    String word;

    //letters the player guessed correctly
    ArrayList<String> correct;

    //letters the player guessed incorrectly
    ArrayList<String> incorrect;

    //textbox where guessed letters are typed
    JTextField guessTextField;

    //title above correctly guessed letters
    JLabel correctLettersText;

    //title above incorrectly guessed letters
    JLabel incorrectLettersText;

    //number of possible guesses before game over
    static final int MAX_GUESSES = 7;


    public Hangman() {
        //calls on method that asks user for word
        word = askWord();
        createShowGUI(word);

        //fills both arrayLists with underscores
        correct = new ArrayList<String>();
        for(int i = 0; i < word.length(); i++){
            correct.add("_");
        }
        incorrect = new ArrayList<String>();
    }

    //asks user for word in a dialog box
    public String askWord() {
        JFrame enterWord = new JFrame("Hangman");
        return JOptionPane.showInputDialog(
                enterWord, "Player 1: \n" + "\"The word is\"", "Hangman", JOptionPane.PLAIN_MESSAGE);
    }

    //creates GUI that displays all the components
    public void createShowGUI(String word) {
        //frame that has all components
        JFrame gameFrame = new JFrame("Hangman");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel inside frame that holds everything
        JPanel everything = new JPanel();
        gameFrame.add(everything);

        //label on top of a text box for player to guess letter
        JPanel guessPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel guessInstructions = new JLabel("Guess a letter: ");
        guessTextField = new JTextField(1);
        GuessHandler gh = new GuessHandler();
        guessTextField.addActionListener(gh);
        guessPanel.add(guessInstructions);
        guessPanel.add(guessTextField);
        guessPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        //displayed correct letters
        JPanel correctLettersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel correctLettersLabel = new JLabel("Correct Letters:");
        correctLettersText = new JLabel();
        correctLettersPanel.add(correctLettersLabel);
        correctLettersPanel.add(correctLettersText);
        correctLettersPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        //displayed incorrect letters
        JPanel incorrectLettersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel incorrectLettersLabel = new JLabel("Incorrect Letters:");
        incorrectLettersText = new JLabel();
        incorrectLettersPanel.add(incorrectLettersLabel);
        incorrectLettersPanel.add(incorrectLettersText);
        incorrectLettersPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        //display gallow
        JPanel gallowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon gallow = new ImageIcon("/Users/Casey/Hangman_JPanel/src/gallow.gif");
        JLabel holdgallow = new JLabel(gallow);
        gallowPanel.add(holdgallow);
        gallowPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        //creates layout
        GroupLayout layout = new GroupLayout(everything);
        everything.setLayout(layout);

        //set layout
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(guessPanel)
                                .addComponent(correctLettersPanel)
                                .addComponent(incorrectLettersPanel)
                        )
                        .addComponent(gallowPanel)
        );

        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(guessPanel)
                                .addComponent(correctLettersPanel)
                                .addComponent(incorrectLettersPanel)
                        )
                        .addComponent(gallowPanel)
        );

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    //check if guessed letters is correct or incorrect & resets text box after each guess
    private class GuessHandler implements ActionListener{
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == guessTextField) {
                guessTextField.setText("");
                String guess = e.getActionCommand();
                if(word.contains(guess)){
                    addCorrect(guess);
                } else{
                    addIncorrect(guess);
                }
            }
        }
    }

    //update correct with correct letter
    public void addCorrect(String guess){
        //add new letter to correct
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == guess.charAt(0)){
                correct.set(i, guess);
            }
        }

        //direct to congrats OR game over
        if(!correct.contains("_")){
            congratulations(word);
        }

        //format String for correct letters text
        String output = "";
        for(String letter:correct){
            output += letter;
            if (letter.equals("_")){
                output += " ";
            }
        }
        correctLettersText.setText(output);
    }

    public void addIncorrect(String guess){
        //add incorrect letter to incorrect
        incorrect.add(guess);
        incorrectLettersText.setText(incorrectToString(incorrect,MAX_GUESSES));

        //call on updatePic()
        updatePic();

        //brings up game over notification
        if(incorrect.size() == MAX_GUESSES){
            gameOver(word);
        }
    }

    //update picture
    public void updatePic(){
        ImageIcon head = new ImageIcon();
        //delete me
    }

    //change incorrect to a string with incorrect letters
    public String incorrectToString(ArrayList<String> letters, int numchars){
        String output = "";
        for(String letter:letters){
            output += letter;
        }
        for (int i = letters.size(); i < numchars; i++){
            output += "_ ";
        }
        return output;
    }

    //notifies player of win
    public void congratulations(String input) {
        JFrame congrats = new JFrame("Hangman");
        JOptionPane.showMessageDialog(congrats, "Congratulations! You've correctly guessed the word " + input + "!");
        playAgain();
    }

    //notifies player of lose
    public void gameOver(String input) {
        JFrame gameOver = new JFrame("Hangman");
        JOptionPane.showMessageDialog(gameOver, "Game Over! \n" + "Word was " + input);
        playAgain();
    }

    //asks to play again
    public boolean playAgain() {
        JFrame playAgain = new JFrame("Hangman");
        int numberBoolean = JOptionPane.showConfirmDialog(playAgain, "Would you like to play again?", "Hangman", JOptionPane.YES_NO_OPTION);
        return numberBoolean == JOptionPane.YES_OPTION;
    }


    public static void main(String[] args) {
        Hangman game = new Hangman();
    }
}