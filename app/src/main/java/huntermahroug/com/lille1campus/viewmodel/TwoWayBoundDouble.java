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
 * Created by Claire on 06/03/2018.
 */

public class TwoWayBoundDouble extends BaseObservable implements Serializable {
    static final long serialVersionUID = 1L;
    private Double mValue;

    /**
     * Creates an empty observable object
     */
    public TwoWayBoundDouble() {
        mValue = -1.0;
    }

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    public TwoWayBoundDouble(Double value) {
        mValue = value;
    }

    /**
     * @return the stored value.
     */
    public Double get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */
    public void set(Double value) {
        if (value == null && mValue == null) return;
        if ((value == null && mValue != null) || !value.equals(mValue)) {
            mValue = value;
            notifyChange();
        }
    }

    /**
     * Set the stored value without notifying of change.
     */
    public void setSilently(Double value) {
        mValue = value;
    }

    @BindingAdapter("android:text")
    public static void bindEditText(EditText view, final TwoWayBoundDouble twoWayBoundDouble) {
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
                    double doubleInText;
                    try {
                        doubleInText = Double.parseDouble(s.toString());
                        twoWayBoundDouble.set(doubleInText);
                    } catch(NumberFormatException e) {
                        twoWayBoundDouble.set(-1.0);
                    }
                }
            });
        }
        Double newValue = twoWayBoundDouble.get();
        Double currentValue = -1.0;
        if(!view.getText().toString().equals("")) {
            currentValue = Double.parseDouble(view.getText().toString());
        }
        if (!currentValue.equals(newValue)) {
            view.setText(newValue.toString());
        }
    }

    // Fallback one-way binding for non-text String attributes, like "android:hint"
    @BindingConversion
    public static Double observableDoubleToDouble(TwoWayBoundDouble integer) {
        return integer.get();
    }
}
