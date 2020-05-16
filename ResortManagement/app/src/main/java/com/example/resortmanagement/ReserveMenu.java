package com.example.resortmanagement;

import com.example.resortmanagement.MainActivity;
import com.example.resortmanagement.R;
import com.example.resortmanagement.fragments.ExcursionFragment;
import com.example.resortmanagement.fragments.RestaurantFragment;
import com.example.resortmanagement.fragments.SpaFragment;
import com.example.resortmanagement.util.DBOperator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReserveMenu extends Activity implements OnClickListener {
    private Button excursion_btn,spa_btn,restaurant_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenity);
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
        excursion_btn=this.findViewById(R.id.excursion_btn);
        excursion_btn.setOnClickListener(this);
        spa_btn=this.findViewById(R.id.spa_btn);
        spa_btn.setOnClickListener(this);
        restaurant_btn=this.findViewById(R.id.restaurant_btn);
        restaurant_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.excursion_btn){Intent intent = new Intent(this, ExcursionFragment.class);
            this.startActivity(intent);
        }
        else if (id==R.id.spa_btn){Intent intent = new Intent(this, SpaFragment.class);
            this.startActivity(intent);
        }
        else if (id==R.id.restaurant_btn){Intent intent = new Intent(this, RestaurantFragment.class);
            this.startActivity(intent);
        }
    }
}
