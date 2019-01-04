package com.hamaar.meetapresto.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hamaar.meetapresto.Adapter.MenuAdapter;
import com.hamaar.meetapresto.Model.Menu;
import com.hamaar.meetapresto.R;

public class DetailMenuActivity extends AppCompatActivity {
    private ImageView imGambar;
    private TextView tvTitle;
    private TextView tvHarga;
    private TextView tvDesc, tvRp;

    private MenuAdapter menu_adapter;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        imGambar = findViewById(R.id.img_detail);
        tvTitle = findViewById(R.id.txtNamaMenuDetail);
        tvHarga = findViewById(R.id.txtHargaDetail);
        tvDesc = findViewById(R.id.txtDescriptionDetail);
        tvRp = findViewById(R.id.Rp);


        menu_adapter = new MenuAdapter(this);

        //key dari MenuAdapter
        menu = getIntent().getParcelableExtra("key_menu");
        Glide.with(getApplication())
                .load(menu.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(imGambar);

        tvTitle.setText(menu.getName());
        tvHarga.setText(menu.getCost());
    }

}
