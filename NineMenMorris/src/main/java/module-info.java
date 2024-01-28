module nine.men.morris.ninemenmorris {
    requires javafx.controls;
    requires javafx.fxml;


    opens nine.men.morris.ninemenmorris to javafx.fxml;
    exports nine.men.morris.ninemenmorris;
}