package com.infinitesheep.gridgenerator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridColorItem emptyGridColorItem = new GridColorItem(new int[] { R.color.emptyGridCell }, -1);

    private List<GridColorItem> gridColorItems;

    private AdView mAdView;
    private boolean hasInitializedAds = false;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ca-app-pub-1269610242069363~5059629491
        MobileAds.initialize(this, "ca-app-pub-1269610242069363~5059629491");

        mAdView = findViewById(R.id.adView);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_preferences_file), MODE_PRIVATE);
        Boolean shouldShowAds = prefs.getBoolean(getString(R.string.shared_preferences_key_show_ads), true);
        ((CheckBox)findViewById(R.id.allow_ads_checkbox)).setChecked(shouldShowAds);
        showAds(shouldShowAds);
        ((CheckBox)findViewById(R.id.allow_ads_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean showAds) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "checkbox");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "show_ads");
                bundle.putString("checked", showAds ? "true" : "false");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_preferences_file), MODE_PRIVATE).edit();
                editor.putBoolean(getString(R.string.shared_preferences_key_show_ads), showAds);
                editor.apply();
                showAds(showAds);
            }
        });

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        gridColorItems = new ArrayList<>();
        gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell }, 7));
        gridColorItems.add(new GridColorItem(new int[] { R.color.blueGridCell }, 7));
        gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell, R.color.blueGridCell }, 1));
        gridColorItems.add(new GridColorItem(new int[] { R.color.blackGridCell }, 1));

        ((EditText)findViewById(R.id.input_rows)).setText("5");
        ((EditText)findViewById(R.id.input_columns)).setText("5");

        rerenderGridColorItems();

        regenerate(getUserRows(), getUserColumns(), gridColorItems, getUserSeed());

        findViewById(R.id.regenerate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "regenerate_grid");
                bundle.putString("columns", "" + getUserColumns());
                bundle.putString("rows", "" + getUserRows());
                bundle.putString("is_using_seed", "" + (getUserSeed() != null));
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                regenerate(getUserRows(), getUserColumns(), gridColorItems, getUserSeed());
            }
        });

        findViewById(R.id.add_grid_color_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridColorItems.add(new GridColorItem(new int[] {R.color.redGridCell}, 1));
                rerenderGridColorItems();
            }
        });

        findViewById(R.id.preset_codenames_5x5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridColorItems = new ArrayList<>();
                gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell }, 7));
                gridColorItems.add(new GridColorItem(new int[] { R.color.blueGridCell }, 7));
                gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell, R.color.blueGridCell }, 1));
                gridColorItems.add(new GridColorItem(new int[] { R.color.blackGridCell }, 1));

                ((EditText)findViewById(R.id.input_rows)).setText("5");
                ((EditText)findViewById(R.id.input_columns)).setText("5");

                rerenderGridColorItems();

                regenerate(getUserRows(), getUserColumns(), gridColorItems, getUserSeed());
            }
        });

        findViewById(R.id.preset_codenames_6x6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridColorItems = new ArrayList<>();
                gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell }, 11));
                gridColorItems.add(new GridColorItem(new int[] { R.color.blueGridCell }, 11));
                gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell, R.color.blueGridCell }, 1));
                gridColorItems.add(new GridColorItem(new int[] { R.color.blackGridCell }, 1));

                ((EditText)findViewById(R.id.input_rows)).setText("6");
                ((EditText)findViewById(R.id.input_columns)).setText("6");

                rerenderGridColorItems();

                regenerate(getUserRows(), getUserColumns(), gridColorItems, getUserSeed());
            }
        });

        findViewById(R.id.preset_codenames_7x7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridColorItems = new ArrayList<>();
                gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell }, 15));
                gridColorItems.add(new GridColorItem(new int[] { R.color.blueGridCell }, 15));
                gridColorItems.add(new GridColorItem(new int[] { R.color.redGridCell, R.color.blueGridCell }, 1));
                gridColorItems.add(new GridColorItem(new int[] { R.color.blackGridCell }, 1));

                ((EditText)findViewById(R.id.input_rows)).setText("7");
                ((EditText)findViewById(R.id.input_columns)).setText("7");

                rerenderGridColorItems();

                regenerate(getUserRows(), getUserColumns(), gridColorItems, getUserSeed());
            }
        });
    }

    private void showAds(boolean show) {
        if (show) {
            if (!hasInitializedAds) {
                AdRequest adRequest = new AdRequest.Builder().addTestDevice("33D89009FBEAC655B8A74DC096D1D1B1").build();
                mAdView.loadAd(adRequest);
                hasInitializedAds = true;
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                        switch (errorCode) {
                            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                                break;
                            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                                break;
                            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                                break;
                            case AdRequest.ERROR_CODE_NO_FILL:
                                break;
                        }
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when when the user is about to return
                        // to the app after tapping on an ad.
                    }
                });
            }
            mAdView.setVisibility(View.VISIBLE);
            findViewById(R.id.ad_view_container).setVisibility(View.VISIBLE);
        }
        else {
            mAdView.setVisibility(View.GONE);
            findViewById(R.id.ad_view_container).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // todo this is a hack to remove the up button when returning to main activity (also the rerender eheheh)
            ab.setDisplayHomeAsUpEnabled(false);
            rerenderGridColorItems();
        }
    }

    private Integer getUserSeed() {
        return getUserIntegerInput(R.id.input_seed);
    }

    private Integer getUserRows() {
        return getUserIntegerInput(R.id.input_rows);
    }

    private Integer getUserColumns() {
        return getUserIntegerInput(R.id.input_columns);
    }

    private Integer getUserIntegerInput(int inputId) {
        String input = ((EditText)findViewById(inputId)).getText().toString();
        if (input.equals("")) {
            return null;
        }
        else {
            long s = Long.parseLong(input);
            return (int)s;
        }
    }

    private void regenerate(Integer rows, Integer columns, final List<GridColorItem> gridColorItems, Integer seed) {
        Random random;
        if (seed == null) {
            random = new Random();
        }
        else {
            random = new Random(seed);
        }

        if (rows == null || rows == 0) {
            Toast.makeText(this, "fill in amount of rows!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (columns == null || columns == 0) {
            Toast.makeText(this, "fill in amount of columns!", Toast.LENGTH_SHORT).show();
            return;
        }

        int givenGridCellCount = 0;
        for (GridColorItem item : gridColorItems) {
            givenGridCellCount += item.cellCount;
        }
        if (givenGridCellCount > rows * columns) {
            Log.e("tag!", "there are too many cells given to generate properly! :(");
            Toast.makeText(this, "grid too small for colors!", Toast.LENGTH_SHORT).show();
            return;
        }

        GridColorItem[][] gridCells = new GridColorItem[columns][rows];
        for (GridColorItem item : gridColorItems) {
            item.chooseColor(random);
            for (int i = 0; i < item.cellCount; i++) {
                int r = random.nextInt(rows);
                int c = random.nextInt(columns);
                while (gridCells[c][r] != null) {
                    r = random.nextInt(rows);
                    c = random.nextInt(columns);
                }
                gridCells[c][r] = item;
            }
        }

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout gridLayout = findViewById(R.id.grid_layout);

        if (gridLayout != null && gridLayout.getChildCount() > 0) {
            try {
                gridLayout.removeViews (0, gridLayout.getChildCount());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int row = 0; row < rows; row++) {
            View v = inflater.inflate(R.layout.grid_row, null);
            for (int column = 0; column < columns; column++) {
                GridColorItem colorItem = gridCells[column][row];
                if (colorItem == null) {
                    colorItem = emptyGridColorItem;
                }
                View vv = inflater.inflate(R.layout.grid_cell, null);
                vv.findViewById(R.id.grid_cell).setBackgroundColor(getResources().getColor(colorItem.getChosenColor()));
                ((LinearLayout)v).addView(vv, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            }
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
            gridLayout.addView(v, layoutParams);
        }

        rerenderGridColorItems();
    }

    public class GridColorItem {
        public int[] colors;
        public int cellCount;
        private int chosenColor = -1;

        public GridColorItem(int[] colors, int cellCount) {
            this.colors = colors;
            this.cellCount = cellCount;
        }

        public void chooseColor(Random random) {
            chosenColor = colors[random.nextInt(colors.length)];
        }

        public int getChosenColor() {
            if (chosenColor == 0 || chosenColor == -1) {
                if (colors.length == 1) {
                    chosenColor = colors[0];
                }
                else {
                    return R.color.black;
                }
            }
            return chosenColor;
        }
    }

    private void rerenderGridColorItems() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout listLayout = findViewById(R.id.grid_color_items);

        if (listLayout == null) {
            return;
        }

        if (listLayout.getChildCount() > 0) {
            try {
                listLayout.removeViews (0, listLayout.getChildCount());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < gridColorItems.size(); i++) {
            GridColorItem item = gridColorItems.get(i);

            View v = inflater.inflate(R.layout.item_list_color_item, null);

            ((TextView)v.findViewById(R.id.grid_item_count)).setText("" + item.cellCount);
            v.findViewById(R.id.grid_item_color).setBackgroundColor(getResources().getColor(item.colors[0]));
            if (item.colors.length > 1) {
                v.findViewById(R.id.grid_item_color_2_container).setVisibility(View.VISIBLE);
                v.findViewById(R.id.grid_item_color_2).setBackgroundColor(getResources().getColor(item.colors[1]));
                v.findViewById(R.id.grid_item_color_chosen_container).setVisibility(View.VISIBLE);
                v.findViewById(R.id.grid_item_color_chosen).setBackgroundColor(getResources().getColor(item.getChosenColor()));
            }
            else {
                v.findViewById(R.id.grid_item_color_2_container).setVisibility(View.GONE);
                v.findViewById(R.id.grid_item_color_chosen_container).setVisibility(View.GONE);
            }

            final int position = i;
            v.findViewById(R.id.delete_list_color_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteGridColorItem(position);
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editGridColorItem(position);
                }
            });

            listLayout.addView(v, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void deleteGridColorItem(int position) {
        gridColorItems.remove(position);
        rerenderGridColorItems();
    }

    private void editGridColorItem(int position) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, EditListColorItemFragment.newInstance(gridColorItems, position), "edit_list_color_item")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
