package com.example.opt_1.control;

/**
 * IModelToView interface that declares all the necessary methods for data transfer between model and view via controller
 */
import com.example.opt_1.model.CRUDCallbacks;

import java.util.ArrayList;
import java.util.Map;

public interface IModeltoView {
 void getTravelledDistanceModel();
 Map<String,ArrayList<Double>> getGroupExericesforView();
}
