/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.xpagedemo.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.R;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xutil.resource.ResUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuexiang
 * @since 2020/10/3 11:14 PM
 */
@Page
public class StepFragment extends XPageFragment {

    private static final String KEY_STEP = "key_step";

    public static final int MAX_STEP = 5;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_next)
    Button btnNext;

    @AutoWired(name = KEY_STEP)
    int mIndex = 0;

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();
        titleBar.setTitle("登记");
        if (mIndex < MAX_STEP) {
            titleBar.setLeftImageDrawable(ResUtils.getDrawable(getContext(), R.drawable.ic_close));
        } else {
            titleBar.setLeftImageDrawable(null);
        }
        return titleBar;
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_step;
    }

    @Override
    protected void initViews() {
        if (mIndex < MAX_STEP) {
            tvTitle.setText(String.format("当前为第%d步", mIndex + 1));
            btnNext.setText("下一步");
        } else {
            tvTitle.setText("恭喜你完成所有步骤！");
            btnNext.setText("知道了");
        }
    }

    @Override
    protected void initListeners() {

    }

    @OnClick(R.id.btn_next)
    public void onViewClicked(View view) {
        if (mIndex < MAX_STEP) {
            PageOption.to(StepFragment.class)
                    .setAddToBackStack(false)
                    .putInt(KEY_STEP, mIndex + 1)
                    .open(this);
        } else {
            popToBack();
        }
    }
}
