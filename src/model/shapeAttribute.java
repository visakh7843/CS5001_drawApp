package src.model;

import java.awt.*;

public class shapeAttribute {

    public int rColor;
    public int gColor;
    public int bColor;
    public Boolean fillColor;
    public Shape shape;
    public boolean crossShape;

    shapeAttribute(int rColor,int gColor, int bColor, Shape shape, Boolean fillColor, Boolean crossShape) {
        this.rColor = rColor;
        this.gColor = gColor;
        this.bColor = bColor;
        this.shape = shape;
        this.crossShape = crossShape;
        this.fillColor = (fillColor == null)?false:fillColor;
    }
}
