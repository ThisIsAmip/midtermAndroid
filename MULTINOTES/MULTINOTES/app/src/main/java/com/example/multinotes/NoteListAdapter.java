package com.example.multinotes;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoteListAdapter extends BaseAdapter {

    private final List<Note> mItems = new ArrayList<>();
    private final Context mContext;

    public NoteListAdapter(Context context) {
        mContext = context;
    }

    public void add(Note item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void del(int pos) {
        mItems.remove(pos);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void change(int pos, Note item) {
        mItems.get(pos).setCaption(item.getCaption());
        mItems.get(pos).setContent(item.getContent());
        mItems.get(pos).setDateCreate(item.getDateCreate());
        mItems.get(pos).setDateRemind(item.getDateRemind());
        mItems.get(pos).setImage(item.getImage());
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int pos) {
        return mItems.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Note NoteItem = mItems.get(position);

        RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.note_layout, parent, false);

        final TextView CaptionView = itemLayout.findViewById(R.id.txtCaption);
        CaptionView.setText(NoteItem.getCaption());

        final TextView ContentView = itemLayout.findViewById(R.id.txtContent);
        ContentView.setText(NoteItem.getContent());

        final TextView dateCreateView = itemLayout.findViewById(R.id.txtDateTimeCreate);
        dateCreateView.setText(Note.FORMAT.format(NoteItem.getDateCreate()));

        final TextView dateRemindView = itemLayout.findViewById(R.id.txtDateTimeRemind);
        dateRemindView.setText(Note.FORMAT.format(NoteItem.getDateRemind()));

        final TextView priorityView = itemLayout.findViewById(R.id.txtPriority);
        priorityView.setText(NoteItem.getImage());

        return itemLayout;
    }
}
