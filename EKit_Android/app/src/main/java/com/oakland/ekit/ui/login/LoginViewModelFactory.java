package com.oakland.ekit.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.oakland.ekit.viewModels.AccountInformationViewModel;
import com.oakland.ekit.viewModels.SurveyViewModel;
import com.oakland.ekit.viewModels.UserHomePageViewModel;
import com.oakland.ekit.data.LoginDataSource;
import com.oakland.ekit.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {


    //shared instance of the login repo
    LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());

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
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }


    }
}
