package main.system.ui;

import main.MainGame;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Skilltree implements ActionListener {
    public JPanel skillwindow;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JButton button16;
    private JButton button17;
    private JButton button18;
    private JButton button19;
    private JButton button20;
    private JButton button21;
    private JButton button22;
    private JButton button23;
    private JButton button24;
    private JButton button25;
    private JButton skill1Button;
    private JButton button27;
    private JButton button28;
    private JButton button29;
    private JButton button30;
    private JButton button31;
    private JButton button32;
    private JButton button33;
    private JButton button34;
    private JButton button35;
    private JButton button36;
    private JButton button37;
    private JButton button38;
    private JButton button39;

    private void createUIComponents() {

    }


    MainGame mg;

    public Skilltree(MainGame mg) {
        this.mg = mg;
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        button6.addActionListener(this);
        button7.addActionListener(this);
        button8.addActionListener(this);
        button9.addActionListener(this);
        button10.addActionListener(this);
        button11.addActionListener(this);
        button12.addActionListener(this);
        button13.addActionListener(this);
        button14.addActionListener(this);
        button15.addActionListener(this);
        button16.addActionListener(this);
        button17.addActionListener(this);
        button18.addActionListener(this);
        button19.addActionListener(this);
        button20.addActionListener(this);
        button21.addActionListener(this);
        button22.addActionListener(this);
        button23.addActionListener(this);
        button24.addActionListener(this);
        button25.addActionListener(this);
        skill1Button.addActionListener(this);
        button27.addActionListener(this);
        button28.addActionListener(this);
        button29.addActionListener(this);
        button30.addActionListener(this);
        button31.addActionListener(this);
        button32.addActionListener(this);
        button33.addActionListener(this);
        button34.addActionListener(this);
        button35.addActionListener(this);
        button36.addActionListener(this);
        button37.addActionListener(this);
        button38.addActionListener(this);
        button39.addActionListener(this);
        skill1Button.setToolTipText("Click this button to disable the middle button.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mg.requestFocus();
    }
}
