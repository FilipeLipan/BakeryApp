package com.github.filipelipan.bakeryapp.util.rx;

import com.github.filipelipan.bakeryapp.common.IErrorHandlerView;
import java.io.IOException;

/**
 * Created by lispa on 12/10/2017.
 */

public class IErrorHandlerHelper {

    /**
     * Helper for generic errors
     * @param iView
     * @param e
     */
    public static void defaultErrorResolver(IErrorHandlerView iView, Throwable e) {
        try {
            RxHttpError error = RxHttpError.parseError(e);
            if (error != null) {
                iView.error(error);
            } else {
                iView.error(e);
            }
        } catch (IOException e1) {
            iView.error(e);
        }
    }

}