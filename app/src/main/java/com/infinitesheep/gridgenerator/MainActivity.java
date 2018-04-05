package com.infinitesheep.gridgenerator;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridColorItem emptyGridColorItem = new GridColorItem(R.color.emptyGridCell, -1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.regenerate_button_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<GridColorItem> gridColorItems = new ArrayList<>();
                gridColorItems.add(new GridColorItem(R.color.redGridCell, 8));
                gridColorItems.add(new GridColorItem(R.color.blueGridCell, 7));
                gridColorItems.add(new GridColorItem(R.color.blackGridCell, 1));

                regenerate(5, 5, gridColorItems);
            }
        });

        findViewById(R.id.regenerate_button_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<GridColorItem> gridColorItems = new ArrayList<>();
                gridColorItems.add(new GridColorItem(R.color.redGridCell, 12));
                gridColorItems.add(new GridColorItem(R.color.blueGridCell, 11));
                gridColorItems.add(new GridColorItem(R.color.blackGridCell, 1));

                regenerate(6, 6, gridColorItems);
            }
        });

        findViewById(R.id.regenerate_button_7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<GridColorItem> gridColorItems = new ArrayList<>();
                gridColorItems.add(new GridColorItem(R.color.redGridCell, 16));
                gridColorItems.add(new GridColorItem(R.color.blueGridCell, 15));
                gridColorItems.add(new GridColorItem(R.color.blackGridCell, 1));

                regenerate(7, 7, gridColorItems);
            }
        });
    }

    private void regenerate(int rows, int columns, final List<GridColorItem> gridColorItems) {
        Random random = new Random();

        int givenGridCellCount = 0;
        for (GridColorItem item : gridColorItems) {
            givenGridCellCount += item.cellCount;
        }
        if (givenGridCellCount > rows * columns) {
            Log.e("tag!", "there are too many cells given to generate properly! :(");
            return;
        }

        GridColorItem[][] gridCells = new GridColorItem[columns][rows];
        for (GridColorItem item : gridColorItems) {
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
                vv.findViewById(R.id.grid_cell).setBackgroundColor(getResources().getColor(colorItem.color));
                ((LinearLayout)v).addView(vv, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            }
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
            gridLayout.addView(v, layoutParams);
        }
    }

    private class GridColorItem {
        public int color;
        public int cellCount;

        public GridColorItem(int color, int cellCount) {
            this.color = color;
            this.cellCount = cellCount;
        }
    }
}
