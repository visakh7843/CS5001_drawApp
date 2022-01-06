package Tests;
import org.junit.Before;
import org.junit.Test;
import src.model.Model;
import src.controller.Controller;
import src.view.View;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class ModelTest {
    private Model tmpModel;
    private Controller tmpCtrl;
    private View tmpView;
    @Before
    public void modelSetup(){
        tmpModel = new Model();
        tmpCtrl = new Controller(tmpModel);
        tmpView = new View(tmpModel,tmpCtrl);

    }
    @Test
    public void ModelExists(){
        assertNotNull(tmpModel);
    }
    @Test
    public void ControllerExists(){
        assertNotNull(tmpCtrl);
    }
    @Test
    public void ViewExists(){
        assertNotNull(tmpView);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setupLine(){
        tmpModel.setupLine(0,0,0,0);
        fail("should throw Illegal Argument Exception");
    }
    @Test(expected = IllegalArgumentException.class)
    public void setupRect(){
        tmpModel.setupRect(0,0,0,0);
        fail("should throw Illegal Argument Exception");
    }
    @Test(expected = IllegalArgumentException.class)
    public void setupEllipse(){
        tmpModel.setupEllipse(0,0,0,0);
        fail("should throw Illegal Argument Exception");
    }
    @Test(expected = IllegalArgumentException.class)
    public void setupCross(){
        tmpModel.setupCross(0,0,0,0);
        fail("should throw Illegal Argument Exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkColorRangeRED(){
        tmpModel.setColorR(300);
        fail("should throw Illegal Argument Exception");
    }
    @Test(expected = IllegalArgumentException.class)
    public void checkColorRangeGREEN(){
        tmpModel.setColorG(300);
        fail("should throw Illegal Argument Exception");
    }
    @Test(expected = IllegalArgumentException.class)
    public void checkColorRangeBLUE(){
        tmpModel.setColorG(300);
        fail("should throw Illegal Argument Exception");
    }

    @Test(expected = NullPointerException.class)
    public void sliderMethodParams(){
        tmpView.setSliderAttributes(null);
        fail("should throw Illegal Argument Exception");
    }

}