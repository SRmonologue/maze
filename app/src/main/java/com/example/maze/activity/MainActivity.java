package com.example.maze.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.maze.R;
import com.example.maze.adapter.GridPicListAdapter;
import com.example.maze.util.ScreenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_puzzle_main_type_select)
    TextView tvPuzzleMainTypeSelect;
    @BindView(R.id.ll_main_spinner)
    LinearLayout llMainSpinner;
    @BindView(R.id.gv_main_pic_list)
    GridView gvMainPicList;

    private PopupWindow mPopupWindow;
    private int[] mResPicId;
    private List<Bitmap> mPicList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private View mPopupView;
    private TextView mTvTyepe2;
    private TextView mTvTyepe3;
    private TextView mTvTyepe4;
    private int mType = 2;
    private GridPicListAdapter mAdapter;
    private String[] mCustomItems = new String[]{"本地图册", "相机拍照"};
    private static final String IMAGE_TYPE = "image/*";
    private static final int RESULT_IMAGE = 100;
    private static final int RESULT_CAMERA = 200;
    private String TEMP_IMAGE_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        tvPuzzleMainTypeSelect.setOnClickListener(v -> showPop(v));
        mAdapter = new GridPicListAdapter(this, mPicList);
        gvMainPicList.setAdapter(mAdapter);
        gvMainPicList.setOnItemClickListener((parent, view, position, id) -> {
            if (position == mResPicId.length - 1) {//最后一张图片
                showDialogCustom();
            } else {
                Intent intent = new Intent(this, ActionActivity.class);
                intent.putExtra("picSelectedID", mResPicId[position]);
                intent.putExtra("mType", mType);
                startActivity(intent);
            }
        });
    }

    /**
     * 显示系统图库
     */
    private void showDialogCustom() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("选择：");
        builder.setItems(mCustomItems, (dialog, which) -> {
            if (0 == which) {//本地图册
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
                startActivityForResult(intent, RESULT_IMAGE);
            } else if (1 == which) {//系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoUri = Uri.fromFile(new File(TEMP_IMAGE_PATH));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, RESULT_CAMERA);
            }
        });
        builder.create().show();
    }

    private void initView() {
        TEMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/temp.png";
        mResPicId = new int[]{
                R.drawable.pic1,
                R.drawable.pic2,
                R.drawable.pic3,
                R.drawable.pic4,
                R.drawable.pic5,
                R.drawable.pic6,
                R.drawable.pic7,
                R.drawable.pic8,
                R.drawable.pic9,
                R.drawable.pic10,
                R.drawable.pic11,
                R.drawable.pic12,
                R.drawable.pic13,
                R.drawable.pic14,
                R.drawable.pic15,
                R.mipmap.ic_launcher
        };
        Bitmap[] bitmaps = new Bitmap[mResPicId.length];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), mResPicId[i]);
            mPicList.add(bitmaps[i]);
        }

        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mPopupView = mLayoutInflater.inflate(R.layout.main_type_selected, null);
        mTvTyepe2 = (TextView) mPopupView.findViewById(R.id.tv_main_type_2);
        mTvTyepe3 = (TextView) mPopupView.findViewById(R.id.tv_main_type_3);
        mTvTyepe4 = (TextView) mPopupView.findViewById(R.id.tv_main_type_4);

        mTvTyepe2.setOnClickListener(v -> {
            mType = 2;
            tvPuzzleMainTypeSelect.setText("2 x 2");
            mPopupWindow.dismiss();
        });
        mTvTyepe3.setOnClickListener(v -> {
            mType = 3;
            tvPuzzleMainTypeSelect.setText("3 x 3");
            mPopupWindow.dismiss();
        });
        mTvTyepe4.setOnClickListener(v -> {
            mType = 4;
            tvPuzzleMainTypeSelect.setText("4 x 4");
            mPopupWindow.dismiss();
        });
    }

    /**
     * 显示popup window
     */
    private void showPop(View view) {
        int density = (int) ScreenUtil.getDeviceDensity(this);
        mPopupWindow = new PopupWindow(mPopupView, 200 * density, 50 * density);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        //透明背景
        ColorDrawable transpent = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(transpent);
        //获取位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(view,
                Gravity.NO_GRAVITY,
                location[0] - 40 * density,
                location[1] + 30 * density);
    }
}
