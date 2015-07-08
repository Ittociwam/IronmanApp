package cs246.ironmanapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Robbie on 7/6/2015.
 */
public class AdapterContestant extends ArrayAdapter<Structs.Contestant> {
    private Activity activity;
    private ArrayList<Structs.Contestant> lContestant;
    private static LayoutInflater inflater = null;

    public AdapterContestant (Activity activity, int textViewResourceId,ArrayList<Structs.Contestant> _lContestant) {
        super(activity, textViewResourceId, _lContestant);
        try {
            this.activity = activity;
            this.lContestant = _lContestant;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lContestant.size();
    }

    public String getU_name(int i) {
        return lContestant.get(i).u_name;
    }

    public double getPercentage(int i) {
        return lContestant.get(i).percentage;
    }


    public static class ViewHolder {
        public TextView display_name;
        public TextView percentage;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(android.R.layout.simple_list_item_1, null);
                holder = new ViewHolder();

                //holder.display_name = (TextView) vi.findViewById(R.id.display_name);
                //holder.percentage = (TextView) vi.findViewById(R.id.percentage);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_name.setText(lContestant.get(position).u_name);
            holder.percentage.setText(Double.toString(lContestant.get(position).percentage));


        } catch (Exception e) {


        }
        return vi;
    }
}