package src.main;
import src.controller.Controller;
import src.model.Model;
import src.view.View;

public class MVCMain {
    public static void main(String[] args) {
        //create model
        Model tmpModel = new Model();
        //create controller
        Controller tmpCtrl = new Controller(tmpModel);
        //create view(GUI)
        new View(tmpModel,tmpCtrl);
    }
}
