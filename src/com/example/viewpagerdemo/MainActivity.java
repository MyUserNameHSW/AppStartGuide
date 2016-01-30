package com.example.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener,
		OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;

	// ����ͼƬ��Դ
	private static final int[] pics = { R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher };

	// �ײ�С��ͼƬ
	private ImageView[] dots;

	// ��¼��ǰѡ��λ��
	private int currentIndex;
	SharedPreferences preferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// �жϳ����Ƿ��һ���ڱ��ֻ�������
		isFirstRun();
		// ��ʼ��ViewPage�ĸ�������
		initViewList();
		// ��ʼ���ײ�С��
		initDots();

	}

	private void initViewList() {
		// TODO Auto-generated method stub
		views = new ArrayList<View>();
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// ��ʼ������ͼƬ�б�
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setImageResource(pics[i]);
			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager);
		// ��ʼ��Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// �󶨻ص�
		vp.setOnPageChangeListener(this);
	}

	private void isFirstRun() {
		// TODO Auto-generated method stub
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);

		// �жϳ�����ڼ������У�����ǵ�һ����������ת������ҳ��
		if (count != 0) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), TwoPageActivity.class);
			startActivity(intent);
			this.finish();
		}
		Editor editor = preferences.edit();
		// ��������
		editor.putInt("count", ++count);
		// �ύ�޸�
		editor.commit();
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[pics.length];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// ����Ϊ��ɫ
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬
	}

	/**
	 * ���õ�ǰ������ҳ
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * ��ֻ��ǰ����С���ѡ��
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	// ������״̬�ı�ʱ����
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	// ����ǰҳ�汻����ʱ����
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// ���µ�ҳ�汻ѡ��ʱ����
	@Override
	public void onPageSelected(int arg0) {
		// ���õײ�С��ѡ��״̬
		setCurDot(arg0);
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
}
