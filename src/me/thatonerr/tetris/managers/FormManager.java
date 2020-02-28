package me.thatonerr.tetris.managers;

import me.thatonerr.tetris.components.Form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.thatonerr.tetris.enums.Shapes.*;

public class FormManager {

    private static Map<Integer, List<Form>> shapeForms = new HashMap<>();

    public FormManager() {
        List<Form> tForms = new ArrayList<>();
        tForms.add(new Form("0 1 0 : 1 1 1"));
        tForms.add(new Form("1 0 : 1 1 : 1 0"));
        tForms.add(new Form("1 1 1 : 0 1 0"));
        tForms.add(new Form("0 1 : 1 1 : 0 1"));
        shapeForms.put(T_SHAPE, tForms);

        List<Form> leftLForms = new ArrayList<>();
        leftLForms.add(new Form("0 1 : 0 1 : 1 1"));
        leftLForms.add(new Form("1 0 0 : 1 1 1"));
        leftLForms.add(new Form("1 1 : 1 0 : 1 0"));
        leftLForms.add(new Form("1 1 1 : 0 0 1"));
        shapeForms.put(LEFT_L, leftLForms);

        List<Form> rightLForms = new ArrayList<>();
        rightLForms.add(new Form("1 0 : 1 0 : 1 1"));
        rightLForms.add(new Form("1 1 1 : 1 0 0"));
        rightLForms.add(new Form("1 1 : 0 1 : 0 1"));
        rightLForms.add(new Form("0 0 1 : 1 1 1"));
        shapeForms.put(RIGHT_L, rightLForms);

        List<Form> squareForms = new ArrayList<>();
        squareForms.add(new Form(("1 1 : 1 1")));
        shapeForms.put(SQUARE, squareForms);

        List<Form> longForms = new ArrayList<>();
        longForms.add(new Form(("1 : 1 : 1 : 1")));
        longForms.add(new Form(("1 1 1 1")));
        shapeForms.put(LONG, longForms);

        List<Form> leftDogForms = new ArrayList<>();
        leftDogForms.add(new Form(("1 1 0 : 0 1 1")));
        leftDogForms.add(new Form(("0 1 : 1 1 : 1 0")));
        shapeForms.put(LEFT_DOG, leftDogForms);

        List<Form> rightDogForms = new ArrayList<>();
        rightDogForms.add(new Form(("0 1 1 : 1 1 0")));
        rightDogForms.add(new Form(("1 0 : 1 1 : 0 1")));
        shapeForms.put(RIGHT_DOG, rightDogForms);
    }

    public static List<Form> getShapeForms(int shapeID) {
        return shapeForms.get(shapeID);
    }

}
