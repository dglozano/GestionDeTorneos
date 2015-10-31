package controllers.general;

public interface ControlledScreen {
    // Permite la injección del Screen Pane
    public void setScreenParent(PrincipalController screenPage);
}
