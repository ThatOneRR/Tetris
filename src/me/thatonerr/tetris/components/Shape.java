package me.thatonerr.tetris.components;

import me.thatonerr.tetris.managers.FormManager;

import java.util.List;

public abstract class Shape {

    protected int shapeWidth, shapeHeight, currentFormID;
    protected Form currentForm;
    protected List<Form> forms;

    public Shape(int shapeID) {
        forms = FormManager.getShapeForms(shapeID);
        currentFormID = 0;
        currentForm = forms.get(currentFormID);
        shapeWidth = currentForm.getTileWidth();
        shapeHeight = currentForm.getTileHeight();
    }

    public void cycleForm() {
        currentFormID++;
        if (currentFormID >= forms.size()) {
            currentFormID = 0;
        }
        currentForm = forms.get(currentFormID);
        shapeWidth = currentForm.getTileWidth();
        shapeHeight = currentForm.getTileHeight();
    }

    public void setForm(int formID) {
        currentFormID = formID;
        if (currentFormID >= forms.size()) {
            currentFormID = 0;
        }
        currentForm = forms.get(currentFormID);
        shapeWidth = currentForm.getTileWidth();
        shapeHeight = currentForm.getTileHeight();
    }

    public boolean hasNextForm() {
        return forms.size() != 1;
    }

    public int getShapeWidth() {
        return shapeWidth;
    }

    public void setShapeWidth(int shapeWidth) {
        this.shapeWidth = shapeWidth;
    }

    public int getShapeHeight() {
        return shapeHeight;
    }

    public void setShapeHeight(int shapeHeight) {
        this.shapeHeight = shapeHeight;
    }

    public int getCurrentFormID() {
        return currentFormID;
    }

    public void setCurrentFormID(int currentFormID) {
        this.currentFormID = currentFormID;
    }

    public Form getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(Form currentForm) {
        this.currentForm = currentForm;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

}
