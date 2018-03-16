package huntermahroug.com.lille1campus.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorStateListRes;

import java.util.List;

import huntermahroug.com.lille1campus.LilleCampusApplication;
import huntermahroug.com.lille1campus.R;
import huntermahroug.com.lille1campus.model.Category;
import huntermahroug.com.lille1campus.util.helper.BottomNavigationViewHelper;
import huntermahroug.com.lille1campus.view.fragment.AddEventFragment_;
import huntermahroug.com.lille1campus.view.fragment.CategoriesFragment_;
import huntermahroug.com.lille1campus.view.fragment.EventListFragment_;
import huntermahroug.com.lille1campus.view.fragment.SearchFragment_;

@EActivity
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.list_events)
    RecyclerView listEventsView;

    @ViewById(R.id.bottom_nav_layout)
    BottomNavigationView bottomNavigationView;

    @ViewById(R.id.titleBar)
    Toolbar toolBar;

    @ViewById(R.id.progress_bar)
    ProgressBar progressBar;

    @ViewById(R.id.fragment_placeholder)
    FrameLayout fragmentPlaceholder;

    @ViewById(android.R.id.content)
    View contentView;

    @ColorStateListRes(R.color.bottom_navigation_color_selector)
    ColorStateList colorStateList;

    private boolean mKeyboardVisible = false;

    /**
     * Désigne l'onglet courant, donc à désactiver lors de l'appui
     * sur un autre onglet.
     */
    MenuItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        final FragmentManager fragmentManager = getFragmentManager();

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(currentItem == null) {
                        currentItem = bottomNavigationView.getMenu().findItem(R.id.nav_view);
                    }
                    currentItem.setChecked(false);
                    item.setChecked(true);
                    currentItem = item;

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    switch (item.getItemId()) {
                        case R.id.nav_view:
                            // TODO : migrer l'appel API getAll ici
                            fragmentTransaction.replace(R.id.fragment_placeholder, EventListFragment_.newInstance(false, false, "", -1));
                            break;

                        case R.id.nav_search:
                            fragmentTransaction.replace(R.id.fragment_placeholder, new SearchFragment_());
                            break;

                        case R.id.nav_categories:
                            fragmentTransaction.replace(R.id.fragment_placeholder, new CategoriesFragment_());
                            break;

                        case R.id.nav_add:
                            ((LilleCampusApplication)MainActivity.this.getApplication()).getCategoriesFromSharedPrefOrAPI();
                            List<Category> categoriesList = ((LilleCampusApplication) MainActivity.this.getApplication()).getCategoriesList();
                            if(categoriesList != null) {
                                fragmentTransaction.replace(R.id.fragment_placeholder, new AddEventFragment_());
                            } else {
                                Toast.makeText(MainActivity.this, R.string.internet_connection_error_msg, Toast.LENGTH_LONG).show();
                            }
                    }
                    fragmentTransaction.commit();
                    return true;
                }
            }
        );

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_placeholder, new EventListFragment_());
        fragmentTransaction.commit();

    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        fragmentPlaceholder.setVisibility(View.VISIBLE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        fragmentPlaceholder.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(mLayoutKeyboardVisibilityListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        contentView.getViewTreeObserver()
                .removeOnGlobalLayoutListener(mLayoutKeyboardVisibilityListener);
    }

    private final ViewTreeObserver.OnGlobalLayoutListener mLayoutKeyboardVisibilityListener =
            () -> {
                final Rect rectangle = new Rect();
                contentView.getWindowVisibleDisplayFrame(rectangle);
                int screenHeight = contentView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // If keypad is shown, the rectangle.bottom is smaller than that before.
                int keypadHeight = screenHeight - rectangle.bottom;
                // 0.15 ratio is perhaps enough to determine keypad height.
                boolean isKeyboardNowVisible = keypadHeight > screenHeight * 0.15;

                if (mKeyboardVisible != isKeyboardNowVisible) {
                    if (isKeyboardNowVisible) {
                        onKeyboardShown();
                    } else {
                        onKeyboardHidden();
                    }
                }

                mKeyboardVisible = isKeyboardNowVisible;
            };

    private void onKeyboardShown() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    private void onKeyboardHidden() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

}
