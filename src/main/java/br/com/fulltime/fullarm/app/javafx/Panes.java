package br.com.fulltime.fullarm.app.javafx;

public enum Panes {
    PANEL("/view/panelPane.fxml"),
    CONFIGURATION("/view/configPane.fxml"),
    UNKNOWN("");

    private final String path;

    Panes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static Panes getByValue(String path) {
        for (Panes p : values()) {
            if (path.equals(p.getPath())) return p;
        }

        return Panes.UNKNOWN;
    }
}
