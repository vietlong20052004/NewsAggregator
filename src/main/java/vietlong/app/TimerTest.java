/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietlong.app;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;

/**
 *
 * @author SingPC
 */
public class TimerTest {
    public static void main(String[] args) {
        var listener =  new TimePrinter();
        
        var timer = new Timer(1000,listener);
        timer.start();
        
        // keep program running until the user selects "OK"
        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
    }
}


class TimePrinter implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent event){
        System.out.println(""+Instant.ofEpochMilli(event.getWhen()));
        Toolkit.getDefaultToolkit().beep();
    }
}