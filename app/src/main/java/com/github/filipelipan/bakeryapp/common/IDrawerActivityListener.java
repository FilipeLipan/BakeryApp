package com.github.filipelipan.bakeryapp.common;

import android.view.View;

public interface IDrawerActivityListener {
    /**
     * Adiciona o listener de click para a seta na action bar
     * @param onClickListener
     */
    void setBackArrowClick(View.OnClickListener onClickListener);

    /**
     * Habilita ou desabilita o menu lateral
     * @param isEnabled
     */
    void setMainMenuEnabled(boolean isEnabled);
}
