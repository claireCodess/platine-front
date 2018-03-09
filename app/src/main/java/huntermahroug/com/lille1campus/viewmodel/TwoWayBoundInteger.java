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
 * Created by Claire on 04/03/2018.
 */

public class TwoWayBoundInteger extends BaseObservable implements Serializable {
    static final long serialVersionUID = 1L;
    private Integer mValue;

    /**
     * Creates an empty observable object
     */
    public TwoWayBoundInteger() {
        mValue = -1;
    }

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    public TwoWayBoundInteger(Integer value) {
        mValue = value;
    }

    /**
     * @return the stored value.
     */
    public Integer get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */
    public void set(Integer value) {
        if (value == null && mValue == null) return;
        if ((value == null && mValue != null) || !value.equals(mValue)) {
            mValue = value;
            notifyChange();
        }
    }

    /**
     * Set the stored value without notifying of change.
     */
    public void setSilently(Integer value) {
        mValue = value;
    }

    @BindingAdapter("android:text")
    public static void bindEditText(EditText view, final TwoWayBoundInteger twoWayBoundInteger) {
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
                    int integerInText;
                    try {
                        integerInText = Integer.parseInt(s.toString());
                        twoWayBoundInteger.set(integerInText);
                    } catch(NumberFormatException e) {
                        twoWayBoundInteger.set(-1);
                    }
                }
            });
        }
        Integer newValue = twoWayBoundInteger.get();
        Integer currentValue = -1;
        if(!view.getText().toString().equals("")) {
            currentValue = Integer.parseInt(view.getText().toString());
        }
        if (!currentValue.equals(newValue)) {
            view.setText(newValue.toString());
        }
    }

    // Fallback one-way binding for non-text String attributes, like "android:hint"
    @BindingConversion
    public static Integer observableIntegerToInteger(TwoWayBoundInteger integer) {
        return integer.get();
    }
}
