package src.view;

import src.controller.Controller;
import src.model.Model;
import src.model.shapeAttribute;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class View implements PropertyChangeListener, ActionListener {

    private final Model tmpModel;
    private final Controller tmpCtrl;

    private final static int DEFAULT_FRAME_WIDTH = 900;
    private final static int DEFAULT_FRAME_HEIGHT = 780;

    final private JFrame mainWindow;
    private JToolBar toolBar;
    final private JPanel canvas;
    final private JPanel sliderCanvas;

    private JToggleButton line;
    private JToggleButton rectangle;
    private JToggleButton ellipse;
    private JToggleButton cross;
    private JButton undo;
    private JButton redo;
    private JButton clear;

    private JSlider sliderR;
    private JSlider sliderG;
    private JSlider sliderB;
    private JLabel statusR;
    private JLabel statusG;
    private JLabel statusB;
    private JCheckBox solidColor;

    private Boolean selectedLine;
    private Boolean selectedRect;
    private Boolean selectedEllipse;
    private Boolean selectedCross;
    private Boolean clearScreen;
    private int x1,y1,x2,y2;
    public View(Model tmpModel, Controller tmpCtrl){
        this.tmpModel = tmpModel;
        this.tmpCtrl = tmpCtrl;
        selectedLine = false;
        selectedRect = false;
        selectedEllipse = false;
        clearScreen = false;
        mainWindow = new JFrame("Vector Drawing");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(DEFAULT_FRAME_WIDTH,DEFAULT_FRAME_HEIGHT);
        mainWindow.setVisible(true);

        canvas = new UndoPanel();
        sliderCanvas = new JPanel();
        sliderCanvas.setBorder(BorderFactory.createLineBorder(Color.black));
        mainWindow.getContentPane().add(canvas,BorderLayout.CENTER);
        mainWindow.getContentPane().add(sliderCanvas,BorderLayout.EAST);
        addViewElements();
        addControlElements();
        addListeners(this);
        colorSliders();
        tmpModel.addListener(this);
        mainWindow.getContentPane().add(toolBar,BorderLayout.NORTH);
        mainWindow.paintAll(mainWindow.getGraphics());

        canvas.addMouseListener(
                new MouseListener() {
                    public void mouseClicked(MouseEvent e) {}
                    public void mouseReleased(MouseEvent e) {
                        // Mouse press released, Now draw the line with x and y
                        try{
                            x2=e.getX();
                            y2=e.getY();
                            paint(canvas.getGraphics(),x1,y1,x2,y2);
                        }
                       catch (NullPointerException n){
                            //Do  nothing
                            //Can be null when no shape selection has been made
                       }
                    }
                    public void mouseEntered(MouseEvent e) {}

                    public void mouseExited(MouseEvent e) {}

                    public void mousePressed(MouseEvent e) {
                        x1 = e.getX();
                        y1 = e.getY();
                    }
                });
    }
    private void colorSliders(){
        sliderCanvas.setLayout(new GridLayout(16,1));
        statusR = new JLabel("R : ", JLabel.CENTER);
        sliderCanvas.add(statusR);
        sliderR = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        setSliderAttributes(sliderR);

        statusG = new JLabel("G : ", JLabel.CENTER);
        sliderCanvas.add(statusG);
        sliderG = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        setSliderAttributes(sliderG);

        statusB = new JLabel("B : ", JLabel.CENTER);
        sliderCanvas.add(statusB);
        sliderB = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        setSliderAttributes(sliderB);
        JLabel information = new JLabel(" Move slider's to set RGB");
        sliderCanvas.add(information);

        //Add checkbox for solid colors
        solidColor = new JCheckBox("Solid color",false);
        sliderCanvas.add(solidColor,new FlowLayout());

        solidColor.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                tmpCtrl.fillColor(((e.getStateChange() == 1)?true:false)); //= (e.getStateChange() == 1)?true:false;
            }
        });
        sliderR.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tmpCtrl.setColorR(((JSlider)e.getSource()).getValue());
                statusR.setText("R : " + ((JSlider)e.getSource()).getValue());

            }
        });

        sliderG.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tmpCtrl.setColorG(((JSlider)e.getSource()).getValue());
                statusG.setText("G : " + ((JSlider)e.getSource()).getValue());
            }
        });
        sliderB.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tmpCtrl.setColorB(((JSlider)e.getSource()).getValue());
                statusB.setText("B : " + ((JSlider)e.getSource()).getValue());
            }
        });
    }

    public void setSliderAttributes(JSlider slider) {
        if(slider.equals(null)){
            throw new NullPointerException();
        }
        slider.setMinorTickSpacing(100);
        slider.setMajorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        sliderCanvas.add(slider);
    }
    private void addViewElements() {
        toolBar = new JToolBar();
        toolBar.setRollover(true);
    }
    private void addControlElements() {
        undo = new JButton("Undo");
        redo = new JButton("Redo");
        clear = new JButton("Clear");
        line = new JToggleButton("Line");
        rectangle = new JToggleButton("Rectangle");
        ellipse = new JToggleButton("Ellipse");
        cross = new JToggleButton("Cross");

        line.setContentAreaFilled(false);
        line.setOpaque(true);
        toolBar.addSeparator();
        rectangle.setContentAreaFilled(false);
        rectangle.setOpaque(true);
        toolBar.addSeparator();
        ellipse.setContentAreaFilled(false);
        ellipse.setOpaque(true);
        cross.setContentAreaFilled(false);
        cross.setOpaque(true);

        toolBar.add(undo);
        toolBar.add(redo);
        toolBar.add(clear);
        toolBar.add(line);
        toolBar.add(rectangle);
        toolBar.add(ellipse);
        toolBar.add(cross);
        toolBar.addSeparator();
    }
    public void addListeners(ActionListener a) {
        clear.addActionListener(a);
        undo.addActionListener(a);
        redo.addActionListener(a);
        line.addActionListener(a);
        rectangle.addActionListener(a);
        ellipse.addActionListener(a);
        cross.addActionListener(a);
    }

    public void propertyChange(PropertyChangeEvent event) {
        canvas.repaint();
    }
    public void paint(Graphics g,int x1,int y1,int x2,int y2) {
        if(selectedLine) {
            tmpCtrl.setupLine(x1, y1, x2, y2);
        }
        else if(selectedRect) {
            tmpCtrl.setupRect(x1, y1, x2, y2);
        }
        else if(selectedEllipse) {
            //We consider ellipse to be an oval inside a rectangle
            tmpCtrl.setupEllipse(x1, y1, x2, y2);
        }
        else if(selectedCross){
            tmpCtrl.setupCross(x1, y1, x2, y2);
        }
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == clear){
            clearScreen = true;
            tmpCtrl.clearLists();
        }
        else if(e.getSource() == undo) {
            tmpCtrl.undoTask();
        }
        else if(e.getSource() == redo){
            tmpCtrl.redoTask();
        }
        else if((e.getSource() == line) && (line.isSelected())) {
            clearSelections();
            line.setBackground(Color.GREEN);
            selectedLine = true;
            line.setSelected(true);
        }
        else if ((e.getSource() == rectangle) && (rectangle.isSelected())) {
            clearSelections();
            rectangle.setBackground(Color.GREEN);
            selectedRect = true;
            rectangle.setSelected(true);
        }
        else if((e.getSource() == ellipse) && (ellipse.isSelected())) {
            clearSelections();
            ellipse.setBackground(Color.GREEN);
            selectedEllipse = true;
            ellipse.setSelected(true);
        }
        else if((e.getSource() == cross) && (cross.isSelected())) {
            clearSelections();
            cross.setBackground(Color.GREEN);
            selectedCross = true;
            cross.setSelected(true);
        }
        else{
            clearSelections();
        }
    }
    public void clearSelections() {
        line.setBackground(Color.WHITE);
        line.setSelected(false);
        selectedLine = false;
        rectangle.setBackground(Color.WHITE);
        selectedRect = false;
        rectangle.setSelected(false);
        ellipse.setBackground(Color.WHITE);
        selectedEllipse = false;
        ellipse.setSelected(false);
        cross.setBackground(Color.WHITE);
        selectedCross = false;
        cross.setSelected(false);
    }
     private class UndoPanel extends JPanel {
        @Override
        public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(!clearScreen) {
            ArrayList<shapeAttribute> undoList = tmpModel.getShapeAttributes();
            for (shapeAttribute shapes : undoList) {
                g2d.setStroke(new BasicStroke(1));
                Color clr = new Color(shapes.rColor, shapes.gColor, shapes.bColor);
                g2d.setColor(clr);
                if(shapes.fillColor){
                    g2d.fill(shapes.shape);
                }
                g2d.draw(shapes.shape);
            }
        }
        clearScreen = false;
        }
    }
}
