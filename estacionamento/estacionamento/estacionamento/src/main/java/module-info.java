module br.feevale {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.feevale to javafx.fxml;
    exports br.feevale;
}
