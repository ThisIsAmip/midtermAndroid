package com.example.btquatrinh_4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.example.btquatrinh_4.DTO.Official;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private SearchView ViewTimKiem;
    private TextView txtLocation;
    private ListView dsOfficials;
    List<Official> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            dsOfficials = findViewById(R.id.lvOfficial);
            txtLocation = findViewById(R.id.txtLocation);
            if(!checkInternetConnection()){
                noNetworkDialog("Stocks Cannot Be Update Without A Network Connection");
            }
        getItemListView();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        this.ViewTimKiem = (SearchView) menu.findItem(R.id.menuItem_search).getActionView();
        this.ViewTimKiem.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        ViewTimKiem.setBackgroundResource(0);
        this.ViewTimKiem.setIconifiedByDefault(true);
        ViewTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }
            public boolean onQueryTextSubmit(String query) {
                ViewTimKiem.clearFocus();
                ViewTimKiem.setQuery("", false);
                ViewTimKiem.setIconified(true);
                if(!checkInternetConnection()){
                    noNetworkDialog("Stocks Cannot Be Update Without A Network Connection");
                    txtLocation.setText("No Data For Location");
                }
                OfficialLoader o = new OfficialLoader(MainActivity.this);
                o.execute(query);
                return doSearch(query);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private boolean doSearch(String query) {
        return !(query == null || query.isEmpty());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuItem_information)
        {
            openInformationActivity();
        }
        return true;
    }

    private void openInformationActivity() {
        Intent intent = new Intent(this, ThongTinActivity.class);
        startActivity(intent);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        return !(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable());
    }
    public void updateOfficialData(ArrayList<Official> tempList)
    {
        list = new ArrayList<>();
        if(tempList.size()!=0)
        {
            list.addAll(tempList);
        }
        Adapter adapter = new Adapter(MainActivity.this, list);
        dsOfficials.setAdapter(adapter);
        if(dsOfficials.getCount() == 0) {
            AlertDialog dlgAlert  = new AlertDialog.Builder(this).create();
            dlgAlert.setTitle("ERROR!");
            dlgAlert.setMessage("There's no data form this address");
            dlgAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dlgAlert.show();
            txtLocation.setText("NO DATA FROM THIS ADDRESS");
        }
    }

    public void getItemListView() {
        dsOfficials.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Official temp = list.get(position);
                Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("location", (String) txtLocation.getText());
                bundle.putSerializable("official", temp);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void noNetworkDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.no_network);
        builder.setTitle(R.string.networkErrorTitle);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}