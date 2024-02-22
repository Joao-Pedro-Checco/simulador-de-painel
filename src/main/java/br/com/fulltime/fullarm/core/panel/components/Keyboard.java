package br.com.fulltime.fullarm.core.panel.components;

public class Keyboard {
    private boolean problem;
    private boolean tamper;

    public boolean isProblem() {
        return problem;
    }

    public void setProblem(boolean problem) {
        this.problem = problem;
    }

    public boolean isTamper() {
        return tamper;
    }

    public void setTamper(boolean tamper) {
        this.tamper = tamper;
    }
}
