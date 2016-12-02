package com.wisnu_krn.mvvmbelajar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "GOT_APP";
    private GoTAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.content_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoTAdapter(this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class GoTAdapter extends RecyclerView.Adapter<GotViewHolder> {
        private final LayoutInflater inflater;
        private final Context context;
        private final DataBaseHelper databaseHelper;
        private Cursor cursor;

        public GoTAdapter(Context context) {
            this.context = context;
            databaseHelper = DataBaseHelper.getDatabaseHelper(context);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.list_item_got, parent, false);
            return new GotViewHolder(view, ((TextView) view.findViewById(R.id.text)), ((ImageView) view.findViewById(R.id.image_character)));
        }

        @Override
        public void onBindViewHolder(GotViewHolder holder, int position) {
            holder.bindItem(context, getItem(position));
        }

        @Override
        public int getItemCount() {
            return cursor == null || cursor.isClosed() ? 0 : cursor.getCount();
        }

        public GotCharacter getItem(int position) {
            if (cursor == null) return null;
            cursor.moveToPosition(position);
            return new GotCharacter(
                    cursor.getInt(cursor.getColumnIndexOrThrow(GotCharacter._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(GotCharacter.FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(GotCharacter.LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(GotCharacter.THUMB_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(GotCharacter.FULL_URL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(GotCharacter.ALIVE)) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow(GotCharacter.HOUSE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(GotCharacter.HOUSE_RES_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(GotCharacter.DESCRIPTION))
            );
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        public void onStart() {
            Log.d(LOG_TAG, "Adapter onStart");
            if (cursor == null || cursor.isClosed())
                cursor = databaseHelper.getCharacterCursor();
            notifyItemRangeChanged(0, cursor.getCount());
        }

        public void onStop() {
            Log.d(LOG_TAG, "Adapter onStop");
            closeCursorIfOpen();
        }

        private void closeCursorIfOpen() {
            if (cursor != null && !cursor.isClosed()) cursor.close();
        }
    }

    public static class GotViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;

        public GotViewHolder(View view, TextView name, ImageView image) {
            super(view);
            this.name = name;
            this.image = image;
        }

        public void bindItem(final Context context, final GotCharacter goTCharacter) {
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_CHARACTER, goTCharacter);
                    context.startActivity(intent);
                }
            });
            this.name.setText(goTCharacter.firstName + " " + goTCharacter.lastName);
            Picasso.with(context)
                    .load(Uri.parse(goTCharacter.thumbUrl))
                    .placeholder(R.drawable.profile_placeholder)
                    .error(R.drawable.profile_placeholder_error)
                    .into(this.image);
        }
    }
}

