// Generated by view binder compiler. Do not edit!
package com.example.opt_1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.opt_1.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RegisterPageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextInputEditText registerEmailInput;

  @NonNull
  public final TextInputEditText registerFirstName;

  @NonNull
  public final TextInputEditText registerLastName;

  @NonNull
  public final Button registerPageRegisterButton;

  @NonNull
  public final TextInputEditText registerPasswordInput;

  @NonNull
  public final TextInputEditText registerUsernameInput;

  private RegisterPageBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextInputEditText registerEmailInput, @NonNull TextInputEditText registerFirstName,
      @NonNull TextInputEditText registerLastName, @NonNull Button registerPageRegisterButton,
      @NonNull TextInputEditText registerPasswordInput,
      @NonNull TextInputEditText registerUsernameInput) {
    this.rootView = rootView;
    this.registerEmailInput = registerEmailInput;
    this.registerFirstName = registerFirstName;
    this.registerLastName = registerLastName;
    this.registerPageRegisterButton = registerPageRegisterButton;
    this.registerPasswordInput = registerPasswordInput;
    this.registerUsernameInput = registerUsernameInput;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RegisterPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RegisterPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.register_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RegisterPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.registerEmailInput;
      TextInputEditText registerEmailInput = ViewBindings.findChildViewById(rootView, id);
      if (registerEmailInput == null) {
        break missingId;
      }

      id = R.id.registerFirstName;
      TextInputEditText registerFirstName = ViewBindings.findChildViewById(rootView, id);
      if (registerFirstName == null) {
        break missingId;
      }

      id = R.id.registerLastName;
      TextInputEditText registerLastName = ViewBindings.findChildViewById(rootView, id);
      if (registerLastName == null) {
        break missingId;
      }

      id = R.id.registerPageRegisterButton;
      Button registerPageRegisterButton = ViewBindings.findChildViewById(rootView, id);
      if (registerPageRegisterButton == null) {
        break missingId;
      }

      id = R.id.registerPasswordInput;
      TextInputEditText registerPasswordInput = ViewBindings.findChildViewById(rootView, id);
      if (registerPasswordInput == null) {
        break missingId;
      }

      id = R.id.registerUsernameInput;
      TextInputEditText registerUsernameInput = ViewBindings.findChildViewById(rootView, id);
      if (registerUsernameInput == null) {
        break missingId;
      }

      return new RegisterPageBinding((ConstraintLayout) rootView, registerEmailInput,
          registerFirstName, registerLastName, registerPageRegisterButton, registerPasswordInput,
          registerUsernameInput);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}