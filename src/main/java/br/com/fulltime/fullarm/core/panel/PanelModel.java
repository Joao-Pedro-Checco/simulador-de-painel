package br.com.fulltime.fullarm.core.panel;

public enum PanelModel {
    AMT4010SMART("41");

    private final String modelByte;

    PanelModel(String modelByte) {
        this.modelByte = modelByte;
    }

    public String getModelByte() {
        return modelByte;
    }
}
