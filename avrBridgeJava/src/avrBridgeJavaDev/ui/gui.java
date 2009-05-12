/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * gui.java
 *
 * Created on Apr 26, 2009, 6:21:14 PM
 */

package avrBridgeJavaDev.ui;

import avrBridgeJavaDev.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;



/**
 *
 * @author blahlol
 */
public class gui extends javax.swing.JFrame {

    private final int redPin = 1;
    private final int bluePin = 4;
    private final int greenPin = 3;

    private avr m8;
    private Timer t = new Timer(100,new timerListener() );
    /** Creates new form gui */
    public gui(avr m8) {
        //this.setMaximumSize(new Dimension(300,500));

        this.m8 = m8;
        m8.setPortPinDir(m8.PORTB, m8.PIN1, m8.OUT);
        m8.setPortPinDir(m8.PORTB, m8.PIN3, m8.OUT);
        m8.setPortPinDir(m8.PORTB, m8.PIN4, m8.OUT);
        m8.setPort(m8.PORTB, 0xFF);
        

        m8.setPortPin(m8.PORTC, m8.PIN1, m8.LOW);
        m8.setPortPinDir(m8.PORTC, m8.PIN1, m8.OUT);

        m8.setPortPinDir(m8.PORTC, m8.PIN2, m8.OUT);
        m8.setPortPinDir(m8.PORTC, m8.PIN4, m8.OUT);
        m8.setPortPinDir(m8.PORTC, m8.PIN5, m8.OUT);
         //m8.setPortPin(m8.PORTC, m8.PIN2, m8.HIGH);
       
       // m8.setPortPin(1,1,0);
       // m8.setPortPin(1,0,0);
        //m8.setPort(1, 0x00);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        button1 = new javax.swing.JToggleButton();
        button2 = new javax.swing.JToggleButton();
        jPanel8 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox1 = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        button1.setText("Connect");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel4.add(button1);

        button2.setText("LED OFF");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel4.add(button2);

        jPanel1.add(jPanel4);

        jToggleButton1.setText("Red");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel8.add(jToggleButton1);

        jToggleButton2.setText("Green");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        jPanel8.add(jToggleButton2);

        jToggleButton3.setText("Blue");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });
        jPanel8.add(jToggleButton3);

        jButton4.setText("update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton4);

        jPanel1.add(jPanel8);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jTextField1.setEditable(false);
        jPanel2.add(jTextField1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.GridLayout(3, 0));

        jLabel4.setText("PORTB:Pin");
        jPanel6.add(jLabel4);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", " " }));
        jPanel6.add(jComboBox3);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OUTPUT", "INPUT", "ADC" }));
        jPanel6.add(jComboBox4);

        jCheckBox3.setText("Pullup");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel6.add(jCheckBox3);

        jCheckBox4.setText("High");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel6.add(jCheckBox4);
        jPanel6.add(jTextField2);

        jButton2.setText("update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2);

        jPanel3.add(jPanel6);

        jLabel3.setText("PORTC:Pin");
        jPanel5.add(jLabel3);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5" }));
        jPanel5.add(jComboBox2);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OUTPUT", "INPUT", "ADC" }));
        jPanel5.add(jComboBox1);

        jCheckBox1.setText("Pullup");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox1);

        jCheckBox2.setText("High");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel5.add(jCheckBox2);

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField3);

        jButton1.setText("update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);

        jPanel3.add(jPanel5);

        jLabel5.setText("PORTD:Pin");
        jPanel7.add(jLabel5);

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));
        jPanel7.add(jComboBox5);

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OUTPUT", "INPUT" }));
        jPanel7.add(jComboBox6);

        jCheckBox5.setText("Pullup");
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });
        jPanel7.add(jCheckBox5);

        jCheckBox6.setText("High");
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });
        jPanel7.add(jCheckBox6);
        jPanel7.add(jTextField4);

        jButton3.setText("update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton3);

        jPanel3.add(jPanel7);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        if (button1.isSelected()) {
            t.start();
          this.button1.setText("Disconnect");
        }
        else{
            t.stop();
        this.button1.setText("Connect");
        }
        
}//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
       m8.setPort(m8.PORTB, 0xFF);
}//GEN-LAST:event_button2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       String command = this.jComboBox1.getSelectedItem().toString();
       int pin        = Integer.valueOf(this.jComboBox2.getSelectedItem().toString());

       if (command.equals("OUTPUT"))  {
           if (this.jCheckBox2.isSelected()){
               m8.setPortPinDir(m8.PORTC, pin, m8.OUT);
               m8.setPortPin(m8.PORTC, pin, m8.HIGH);
           } 
           else {
               m8.setPortPinDir(m8.PORTC, pin, m8.OUT);
               m8.setPortPin(m8.PORTC, pin, m8.LOW);
           }
       }
       else if (command.equals("INPUT")) {
           if (this.jCheckBox1.isSelected()) {
               m8.setPortPinDir(m8.PORTC, pin, m8.IN);
               m8.setPortPin(m8.PORTC, pin, m8.ON);
           }
           else {
              m8.setPortPinDir(m8.PORTC, pin, m8.IN);
              m8.setPortPin(m8.PORTC, pin, m8.OFF);
           }

       }
       else if (command.equals("ADC")) {
           m8.setPortPinDir(m8.PORTC, pin, m8.OUT);
           m8.setPortPin(m8.PORTC, pin, m8.LOW);
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (this.jToggleButton1.isSelected()) {
            this.m8.setPortPin(m8.PORTB, redPin, 0);
        }
        else {
            this.m8.setPortPin(m8.PORTB, redPin, 1);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        if (this.jToggleButton2.isSelected()) {
            this.m8.setPortPin(m8.PORTB, greenPin, 0);
        }
        else {
            this.m8.setPortPin(m8.PORTB, greenPin, 1);
        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        if (this.jToggleButton3.isSelected()) {
            this.m8.setPortPin(m8.PORTB, bluePin, 0);
        }
        else {
            this.m8.setPortPin(m8.PORTB, bluePin, 1);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton3ActionPerformed

  
    /**
    * @param args the command line arguments
    */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton button1;
    private javax.swing.JToggleButton button2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    // End of variables declaration//GEN-END:variables

    private class timerListener implements ActionListener {
        private int buffer[] = {0,0,0,0,0,0,0,0,0,0};
        private int counter=0;
        public void actionPerformed(ActionEvent e) {
  
            
            buffer[counter] = 0;//m8.getAdc(1);
          //  System.out.println(m8.getPortPin(m8.PORTC, m8.PIN2));
          int sum=0;
            for (int i=0;i<10;i++) {
              sum = sum + buffer[i];
          }
           counter = (counter +1 )%10;
      //     if (sum <=30) m8.setPortPin(m8.PORTC, m8.PIN5, m8.ON);
        //   else m8.setPortPin(m8.PORTC, m8.PIN5, m8.OFF);
           int adcVal = m8.getAdc(m8.PIN1);
        //   if (adcVal> ) m8.setPortPin(m8.PORTC, m8.PIN5, m8.HIGH);
          // else m8.setPortPin(m8.PORTC, m8.PIN5, m8.LOW);
           jTextField1.setText(String.valueOf(adcVal));

        }

    }
}