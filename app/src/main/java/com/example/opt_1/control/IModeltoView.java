package com.example.opt_1.control;

import com.example.opt_1.model.CRUDCallbacks;

import java.util.ArrayList;
import java.util.Map;

public interface IModeltoView {

 void getTravelledDistanceModel();
 Map<String,ArrayList<Double>> getGroupExericesforView();
}
