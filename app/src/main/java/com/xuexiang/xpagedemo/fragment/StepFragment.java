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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.core.PageOption;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.R;
import com.xuexiang.xpagedemo.databinding.FragmentStepBinding;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xutil.resource.ResUtils;

/**
 * @author xuexiang
 * @since 2020/10/3 11:14 PM
 */
@Page
public class StepFragment extends XPageFragment {

    private static final String KEY_STEP = "key_step";

    public static final int MAX_STEP = 5;

    FragmentStepBinding binding;

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
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentStepBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        if (mIndex < MAX_STEP) {
            binding.tvTitle.setText(String.format("当前为第%d步", mIndex + 1));
            binding.btnNext.setText("下一步");
        } else {
            binding.tvTitle.setText("恭喜你完成所有步骤！");
            binding.btnNext.setText("知道了");
        }
    }

    @Override
    protected void initListeners() {
        binding.btnNext.setOnClickListener(v -> {
            if (mIndex < MAX_STEP) {
                PageOption.to(StepFragment.class)
                        .setAddToBackStack(false)
                        .putInt(KEY_STEP, mIndex + 1)
                        .open(StepFragment.this);
            } else {
                popToBack();
            }
        });
    }
}
