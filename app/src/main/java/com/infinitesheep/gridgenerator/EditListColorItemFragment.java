package com.infinitesheep.gridgenerator;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Sofie on 2018-04-20.
 */

public class EditListColorItemFragment extends Fragment {

    private List<MainActivity.GridColorItem> gridColorItems;
    private int position;
    private int[] colors;

    public static EditListColorItemFragment newInstance(List<MainActivity.GridColorItem> gridColorItems, int position) {
        EditListColorItemFragment fragment = new EditListColorItemFragment();
        fragment.gridColorItems = gridColorItems;
        fragment.position = position;
        fragment.colors = gridColorItems.get(position).colors;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_list_color_item, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.save_grid_color_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer cells = getCellCount();
                if (cells == null || cells == 0) {
                    Toast.makeText(getActivity(), "Cell count must be 1 or more!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (colors == null || colors.length == 0) {
                    Toast.makeText(getActivity(), "Must have 1 or more colors!", Toast.LENGTH_SHORT).show();
                    return;
                }
                gridColorItems.get(position).cellCount = cells;
                gridColorItems.get(position).colors = colors;
                getActivity().onBackPressed();
            }
        });
        getView().findViewById(R.id.add_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] newColors = new int[colors.length + 1];
                System.arraycopy(colors, 0, newColors, 0, colors.length);
                colors = newColors;
                editColor(newColors.length - 1);
            }
        });
        ((EditText)getView().findViewById(R.id.input_cell_count)).setText("" + gridColorItems.get(position).cellCount);
        rerenderColors();
    }

    private Integer getCellCount() {
        return getUserIntegerInput(R.id.input_cell_count);
    }

    private Integer getUserIntegerInput(int inputId) {
        String input = ((EditText)getView().findViewById(inputId)).getText().toString();
        if (input.equals("")) {
            return null;
        }
        else {
            long s = Long.parseLong(input);
            return (int)s;
        }
    }

    private void rerenderColors() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout listLayout = getActivity().findViewById(R.id.grid_color_item_colors);

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

        for (int i = 0; i < colors.length; i++) {
            int color = colors[i];

            View v = inflater.inflate(R.layout.item_list_color_item_color, null);

            v.findViewById(R.id.grid_item_color).setBackgroundColor(getResources().getColor(color));

            final int colorPosition = i;
            v.findViewById(R.id.delete_color).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteColor(colorPosition);
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editColor(colorPosition);
                }
            });

            listLayout.addView(v, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void deleteColor(int colorPosition) {
        int[] newColors = new int[colors.length - 1];
        int skipped = 0;
        for (int i = 0; i < colors.length; i++) {
            if (i == colorPosition) {
                skipped++;
                continue;
            }
            else {
                newColors[i - skipped] = colors[i];
            }
        }
        colors = newColors;
        rerenderColors();
    }

    private void editColor(final int colorPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] color_names = new String[] { "Red", "Blue", "Yellow", "Purple", "Green", "Orange", "Black", "White" };
        final int[] color_integers = new int[] { R.color.redGridCell, R.color.blueGridCell, R.color.yellowGridCell, R.color.purpleGridCell, R.color.greenGridCell, R.color.orangeGridCell, R.color.blackGridCell, R.color.whiteGridCell };
        builder.setTitle("Pick a color")
                .setItems(color_names, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        colors[colorPosition] = color_integers[which];
                        rerenderColors();
                    }
                });
        builder.create().show();
    }
}
