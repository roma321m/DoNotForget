package roman.app.donotforget;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Validator {

    public static class Builder {
        private TextInputLayout textInputLayout;
        private ArrayList<Watcher> watchers = new ArrayList<>();
        private boolean isAlreadyBuild = false;

        public static Builder make(@NonNull TextInputLayout textInputLayout) {
            return new Builder(textInputLayout);
        }

        private Builder(@NonNull TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        public Builder addWatcher(Watcher watcher) {
            if (!isAlreadyBuild) {
                this.watchers.add(watcher);
            }
            return this;
        }

        public Validator build() {
            if (!isAlreadyBuild) {
                isAlreadyBuild = true;
                Validator v = addValidator(textInputLayout, watchers);
                return v;
            }
            return null;
        }
    }

    private static Validator addValidator(TextInputLayout textInputLayout, ArrayList<Watcher> watchers) {
        Validator v = new Validator(textInputLayout, watchers);
        return v;
    }

    private TextInputLayout textInputLayout;
    private ArrayList<Watcher> watchers;

    private Validator(TextInputLayout textInputLayout, ArrayList<Watcher> watchers) {
        this.textInputLayout = textInputLayout;
        this.watchers = watchers;

        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                String result = check(input);
                textInputLayout.setError(result);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public String getError() {
        String input = textInputLayout.getEditText().getText().toString();
        return check(input);
    }

    public boolean validateIt() {
        String input = textInputLayout.getEditText().getText().toString();
        return check(input).equals("");
    }

    private String check(String input) {
        boolean result = true;
        for (Watcher watcher : watchers) {
            result = watcher.privateCheck(input);
            if (!result) {
                return watcher.error;
            }
        }
        return "";
    }


    public abstract static class Watcher {

        private String error;

        private Watcher(String error) {
            this.error = error;
        }

        public abstract boolean privateCheck(String input);
    }

    public static class Watcher_NotEmpty extends Watcher {

        public Watcher_NotEmpty(String error) {
            super(error);
        }

        @Override
        public boolean privateCheck(String input) {
            if (input == null || input.isEmpty()) {
                return false;
            }
            return true;
        }
    }
}
