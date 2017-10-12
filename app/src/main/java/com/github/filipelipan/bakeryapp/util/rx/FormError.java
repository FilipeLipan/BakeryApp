package com.github.filipelipan.bakeryapp.util.rx;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lispa on 12/10/2017.
 */

public class FormError extends HashMap<String, ArrayList<String>> {

    public String getFirstError(String key){
        return this.get(key).get(0);
    }

}
