package com.bank.models;

import com.bank.view.ViewsFactory;

public class ViewModel {
    private static ViewModel model;
    private final ViewsFactory viewsFactory;

    private ViewModel(){
        this.viewsFactory = new ViewsFactory();
    }

    public static synchronized ViewModel getInstance(){
        if(model == null){
            model = new ViewModel();
        }
        return model;
    }

    public ViewsFactory getViewsFactory() {
        return viewsFactory;
    }
}
