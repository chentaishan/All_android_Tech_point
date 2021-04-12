package com.example.circlemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @author zhy 
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class CircleActivity extends Activity
{

	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "安全中心 ", "特色服务", "投资理财",
			"转账汇款", "我的账户", "信用卡" };
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };
	private List<MeunItem> meunItems;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initData();
		//自已切换布局文件看效果
		setContentView(R.layout.activity_main02);
//		setContentView(R.layout.activity_main);

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
//		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

		mCircleMenuLayout.setListData(meunItems, new ICircleCallback() {
			@Override
			public View getItemView(ViewGroup parent,MeunItem item) {
				View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_menu_item,parent,false);
				ImageView iv = (ImageView) root
						.findViewById(R.id.id_circle_menu_item_image);
				TextView tv = (TextView) root
						.findViewById(R.id.id_circle_menu_item_text);

				iv.setImageResource(item.getImgId());
				iv.setVisibility(View.VISIBLE);
				tv.setText(item.getTitle());
				return root;
			}
		});
		

		mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
				Toast.makeText(CircleActivity.this, mItemTexts[pos],
						Toast.LENGTH_SHORT).show();

			}
			
			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(CircleActivity.this,
						"you can do something just like ccb  ",
						Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

	private void initData() {

		meunItems = new ArrayList<>();

		meunItems.add(new MeunItem(R.drawable.home_mbank_1_normal,"安全中心"));
		meunItems.add(new MeunItem(R.drawable.home_mbank_2_normal,"安全中心"));
		meunItems.add(new MeunItem(R.drawable.home_mbank_3_normal,"安全中心"));
		meunItems.add(new MeunItem(R.drawable.home_mbank_4_normal,"安全中心"));
		meunItems.add(new MeunItem(R.drawable.home_mbank_5_normal,"安全中心"));
		meunItems.add(new MeunItem(R.drawable.home_mbank_6_normal,"安全P心"));
	}

}
