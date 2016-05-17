package com.wj.bitmaploader.example;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wj.bitmaploader.R;
import com.wj.bitmaploader.example.util.DisplayUtil;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String[] mTitles;
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View getContentView() {
        mTitles = getResources().getStringArray(R.array.titles);
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0, length = mTitles.length; i < length; i++) {
            TextView textView = new TextView(this);
            textView.setTag(mTitles[i]);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.size_50dp)));
            textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            textView.setPadding(getResources().getDimensionPixelSize(R.dimen.padding_10dp), 0, 0, 0);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.textSize_18sp));
            textView.setBackgroundDrawable(DisplayUtil.getSelectableItemBackground(this));
            textView.setOnClickListener(this);
            textView.setText(mTitles[i].split(",")[1]);
            layout.addView(textView);
            View view = new View(this);
            view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.line_1dp)));
            layout.addView(view);
        }
        return layout;
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag != null) {
            for(String string:mTitles){
                if(tag.equals(string)){
                    String[] array = string.split(",");
                    Intent intent = new Intent(array[2]);
                    intent.putExtra(TYPE, Integer.parseInt(array[0]));
                    intent.putExtra(TITLE, array[1]);
                    startActivity(intent);
                }
            }

        }

    }
}
