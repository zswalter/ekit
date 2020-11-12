package com.oakland.ekit.viewModels;


import com.oakland.ekit.data.LoginRepository;

public interface ViewModelSubComponent {

    interface Builder {
        ViewModelSubComponent build();
    }

    AccountInformationViewModel accountInformationViewModel();



}
