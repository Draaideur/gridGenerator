package com.infinitesheep.gridgenerator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Sofie on 2018-04-20.
 */

public class EditListColorItemFragment extends Fragment {

    private List<MainActivity.GridColorItem> gridColorItems;
    private int position;

    public static EditListColorItemFragment newInstance(List<MainActivity.GridColorItem> gridColorItems, int position) {
        EditListColorItemFragment fragment = new EditListColorItemFragment();
        fragment.gridColorItems = gridColorItems;
        fragment.position = position;
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
                gridColorItems.get(position).cellCount = cells;
                getActivity().onBackPressed();
            }
        });
        ((EditText)getView().findViewById(R.id.input_cell_count)).setText("" + gridColorItems.get(position).cellCount);
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
}
