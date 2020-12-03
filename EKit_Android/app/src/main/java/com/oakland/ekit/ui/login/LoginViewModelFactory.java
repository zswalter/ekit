package com.oakland.ekit.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.oakland.ekit.viewModels.AccountInformationViewModel;
import com.oakland.ekit.viewModels.AdminHomePageViewModel;
import com.oakland.ekit.viewModels.OpenTicketsViewModel;
import com.oakland.ekit.viewModels.SurveyViewModel;
import com.oakland.ekit.viewModels.UserHomePageViewModel;
import com.oakland.ekit.data.LoginDataSource;
import com.oakland.ekit.data.LoginRepository;
import com.oakland.ekit.viewModels.ViewModelSubComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class, Callable<? extends ViewModel>> creators;


    //shared instance of the login repo
    LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());


    public LoginViewModelFactory() {
        creators = new HashMap<>();
        // we cannot inject view models directly because they won't be bound to the owner's
        // view model scope.

        //TODO: check if this works with the repo being passed??
        //creators.put(AccountInformationViewModel.class, viewModelSubComponent::accountInformationViewModel);



    }


//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
//        Callable<? extends ViewModel> creator = creators.get(modelClass);
//        if (creator == null) {
//            for (Map.Entry<Class, Callable<? extends ViewModel>> entry : creators.entrySet()) {
//                if (modelClass.isAssignableFrom(entry.getKey())) {
//                    creator = entry.getValue();
//                    break;
//                }
//            }
//        }
//        if (creator == null) {
//            throw new IllegalArgumentException("unknown model class " + modelClass);
//        }
//        try {
//            return (T) creator.call();
//        } catch (final Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        //TODO: better way to do this instead of if statements ?
        //check which view model
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(loginRepository);
        } if (modelClass.isAssignableFrom(UserHomePageViewModel.class)) {
            return (T) new UserHomePageViewModel(loginRepository);
        } if (modelClass.isAssignableFrom(AccountInformationViewModel.class)) {
            return (T) new AccountInformationViewModel(loginRepository);
        } if (modelClass.isAssignableFrom(SurveyViewModel.class)) {
            return (T) new SurveyViewModel(loginRepository);
        } if (modelClass.isAssignableFrom(AdminHomePageViewModel.class)) {
            return (T) new AdminHomePageViewModel(loginRepository);
        } if (modelClass.isAssignableFrom(OpenTicketsViewModel.class)) {
            return (T) new OpenTicketsViewModel(loginRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }


    }
}
