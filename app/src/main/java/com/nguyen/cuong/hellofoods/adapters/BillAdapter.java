package com.nguyen.cuong.hellofoods.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nguyen.cuong.hellofoods.R;
import com.nguyen.cuong.hellofoods.models.Bill;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by cuong on 11/26/2017.
 */

public class BillAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bill> bills;

    public BillAdapter(Context context, ArrayList<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int position) {
        return bills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_history, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.totalMoney = (TextView) view.findViewById(R.id.total_money);
            viewHolder.status = (TextView) view.findViewById(R.id.status);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.time.setText(bills.get(position).getTime());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        viewHolder.totalMoney.setText(formatter.format(bills.get(position).getTotalMoney()) + " đ");
        if (bills.get(position).getStatus() == 1) {
            viewHolder.status.setText("Chưa thanh toán");
        } else if (bills.get(position).getStatus() == 0) {
            viewHolder.status.setText("Đã thanh toán");
        } else if (bills.get(position).getStatus() == 2) {
            viewHolder.status.setText("Hủy bỏ");
        }
        return view;
    }

    class ViewHolder {
        TextView time, totalMoney, status;
    }
}
