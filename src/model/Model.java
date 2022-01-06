package src.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class Model {
    
    //Add more private values
    private Boolean fillColor;
    //private Boolean oldFill;
    int colorR,colorG,colorB;
    final private PropertyChangeSupport notifier;
    ArrayList<shapeAttribute> undoList = new ArrayList<>();
    ArrayList<shapeAttribute> redoList = new ArrayList<>();
    public Model(){
        //Initialise more variables as required.
        this.notifier = new PropertyChangeSupport(this);
        this.fillColor = false;
        this.colorR = 0;
        this.colorG = 0;
        this.colorB = 0;
    }
    //Register Listeners so it will be notified of any changes
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    //Broadcast all recent changes to all listeners
    private void update(String s) {
            notifier.firePropertyChange(s,null,undoList);
    }
    public void setupLine(int x1,int y1,int x2, int y2){
        if(x1 == 0 && y1 ==0 && x2 == 0 && y2 == 0){
            throw new IllegalArgumentException();
        }
        Line2D line2D = new Line2D.Double(x1,y1,x2,y2);
        undoList.add(new shapeAttribute(this.colorR, this.colorG, this.colorB, line2D, this.fillColor, false));
        update("line");
    }
    public void setupRect(int x1, int y1, int x2, int y2) {
        if(x1 == 0 && y1 ==0 && x2 == 0 && y2 == 0) {
            throw new IllegalArgumentException();
        }
        Rectangle rect = new Rectangle();
        rect.setFrameFromDiagonal(x1, y1, x2, y2);
        Rectangle2D rectangle = new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height);
        undoList.add(new shapeAttribute(this.colorR, this.colorG, this.colorB, rectangle, this.fillColor, false));
        update("Rectangle");
    }
    public void setupEllipse(int x1,int y1,int x2, int y2){
        if(x1 == 0 && y1 ==0 && x2 == 0 && y2 == 0){
            throw new IllegalArgumentException();
        }
        Rectangle rect = new Rectangle();
        rect.setFrameFromDiagonal(x1,y1,x2,y2);
        double minorAxis = rect.height;
        double majorAxis = rect.width;
        Ellipse2D curve = new Ellipse2D.Double(x1, y1, majorAxis, minorAxis);
        undoList.add(new shapeAttribute(this.colorR, this.colorG, this.colorB, curve, this.fillColor, false));
        update("Ellipse");
    }
    public void setupCross(int x1, int y1, int x2, int y2) {
        if(x1 == 0 && y1 ==0 && x2 == 0 && y2 == 0) {
            throw new IllegalArgumentException();
        }
        Rectangle rect = new Rectangle();
        rect.setFrameFromDiagonal(x1,y1,x2,y2);
        Line2D line2D = new Line2D.Double(rect.x, rect.y, rect.x+rect.width, rect.y+rect.height);
        undoList.add(new shapeAttribute(this.colorR,this.colorG,this.colorB,line2D,this.fillColor,true));
        line2D = new Line2D.Double(rect.x,rect.y + rect.height,rect.x+rect.width,rect.y);
        undoList.add(new shapeAttribute(this.colorR,this.colorG,this.colorB,line2D,this.fillColor,true));
        update("cross");
    }

    public void fillColor(Boolean fillColor){
        this.fillColor = fillColor;
    }
    public Boolean getFillColor(){
        return this.fillColor;
    }
    public ArrayList<shapeAttribute> getShapeAttributes(){
        return undoList;
    }
    public void setColorR(int color) {
        if(color < 0 || color > 255){
            throw new IllegalArgumentException();
        }
        this.colorR = color;
    }
    public void setColorG(int color) {
        if(color < 0 || color > 255){
            throw new IllegalArgumentException();
        }
        this.colorG = color;
    }
    public void setColorB(int color) {
        if(color < 0 || color > 255){
            throw new IllegalArgumentException();
        }
        this.colorB = color;
    }
    public void clearLists() {
        undoList.clear();
        redoList.clear();
        update("clear");
    }
    public void undoTask() {
        if (!this.undoList.isEmpty()) {
            int index = this.undoList.size() - 1;
            shapeAttribute s = this.undoList.get(index);
            this.redoList.add(s);
            this.undoList.remove(index);

            //Special case for Cross Shape : Since it is dealt as 2 separate lines.
            if(s.crossShape){
                this.redoList.add(this.undoList.get(index - 1));
                this.undoList.remove(index - 1);
            }
            update("undo");
        }
    }
    public void redoTask(){
        if(!this.redoList.isEmpty()){
            int index = this.redoList.size() - 1;
            shapeAttribute s = this.redoList.get(index);
            this.undoList.add(s);
            this.redoList.remove(index);

           //Special case for Cross Shape : Since it is dealt as 2 separate lines.
            if (s.crossShape){
                this.undoList.add(this.redoList.get(index - 1));
                this.redoList.remove(index - 1);
            }
           update("redo");
         }
    }
}



