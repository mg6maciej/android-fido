package com.fido.example.fidoapiexample.utils;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fido.example.fidoapiexample.R;
import com.fido.example.fidoapiexample.U2FDemoActivity;

import java.util.List;
import java.util.Map;

public class SecurityTokenAdapter extends RecyclerView.Adapter<SecurityTokenAdapter.ViewHolder> {

    private List<Map<String, String>> tokens;
    private int rowLayout;
    private U2FDemoActivity mActivity;

    public SecurityTokenAdapter(List<Map<String, String>> applications, int rowLayout, U2FDemoActivity act) {
        this.tokens = applications;
        this.rowLayout = rowLayout;
        this.mActivity = act;
    }

    public void clearSecurityTokens() {
        int size = this.tokens.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                tokens.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addSecurityToken(List<Map<String, String>> applications) {
        this.tokens.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int item) {
        final Map<String, String> token = tokens.get(item);
        StringBuffer tokenValue = new StringBuffer();
        for (Map.Entry<String, String> entry : token.entrySet()) {
            tokenValue.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        viewHolder.content.setText(tokenValue.toString());
        /*viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "security token " + item + " is clicked", Toast.LENGTH_SHORT).show();
                //v.setBackgroundResource(R.drawable.button_rect_list_normal);
                v.setSelected(true);
            }
        });*/
        viewHolder.image.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.yubikey));
        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // confirm to remove it
                AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
                alert.setTitle("confirm to remove security token");
                alert.setMessage("Are you sure to delete this security token?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mActivity.removeTokenByIndexInList(item);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tokens == null ? 0 : tokens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView content;
        public ImageView image;
        public ImageView removeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.tokenValue);
            image = (ImageView) itemView.findViewById(R.id.tokenImage);
            removeButton = (ImageView) itemView.findViewById(R.id.removeToken);
        }

    }
}