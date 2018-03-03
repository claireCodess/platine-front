package huntermahroug.com.lille1campus.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.Serializable;

import huntermahroug.com.lille1campus.R;

/**
 * An observable class that holds a String, including event listener logic that will
 * set data back to the observable upon change in the UI layer.
 *
 * Credits to Fabio Collini https://medium.com/@fabioCollini/android-data-binding-f9f9d3afc761#.7xkobedbt
 */
public class TwoWayBoundString extends BaseObservable implements Serializable {
    static final long serialVersionUID = 1L;
    private String mValue;

    /**
     * Creates an empty observable object
     */
    public TwoWayBoundString() {
    }

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    public TwoWayBoundString(String value) {
        mValue = value;
    }

    /**
     * @return the stored value.
     */
    public String get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */
    public void set(String value) {
        if (value == null && mValue == null) return;
        if ((value == null && mValue != null) || !value.equals(mValue)) {
            mValue = value;
            notifyChange();
        }
    }

    /**
     * Set the stored value without notifying of change.
     */
    public void setSilently(String value) {
        mValue = value;
    }

    @BindingAdapter("android:text")
    public static void bindEditText(EditText view, final TwoWayBoundString twoWayBoundString) {
        if (view.getTag(R.id.textBound) == null) {
            // Hook up change listeners upon first initialization
            view.setTag(R.id.textBound, true);
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    twoWayBoundString.set(s.toString());
                }
            });
        }
        String newValue = twoWayBoundString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }
    }

    // Fallback one-way binding for non-text String attributes, like "android:hint"
    @BindingConversion
    public static String observableStringToString(TwoWayBoundString string) {
        return string.get();
    }
}