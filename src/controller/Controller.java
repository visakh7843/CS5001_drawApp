package src.controller;
import src.model.Model;
import src.model.shapeAttribute;

import java.util.ArrayList;

public class Controller {

    final private Model tmpModel;

    public Controller(Model tmpModel){
        this.tmpModel =  tmpModel;
    }


    public void fillColor(Boolean fillColors){
        tmpModel.fillColor(fillColors);
    }


    public void setupLine(int x1, int y1, int x2, int y2){
        tmpModel.setupLine(x1,y1,x2,y2);
    }
    public void setupRect(int x1, int y1, int x2, int y2){
        tmpModel.setupRect(x1,y1,x2,y2);
    }
    public void setupEllipse(int x1, int y1, int x2, int y2){
        tmpModel.setupEllipse(x1,y1,x2,y2);
    }
    public void setupCross(int x1, int y1, int x2, int y2){
        tmpModel.setupCross(x1,y1,x2,y2);
    }
    public ArrayList<shapeAttribute> getShapes(){
        return tmpModel.getShapeAttributes();
    }
    public void setColorR(int color){
        tmpModel.setColorR(color);
    }
    public void setColorG(int color){
        tmpModel.setColorG(color);
    }
    public void setColorB(int color){
        tmpModel.setColorB(color);
    }
    public void clearLists(){
        tmpModel.clearLists();
    }
    public void undoTask(){
        tmpModel.undoTask();
    }
    public void redoTask(){
        tmpModel.redoTask();
    }
}