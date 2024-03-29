/*
 * Copyright (C) 2016 Hugo Matalonga & João Paulo Fernandes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hmatalonga.greenhub.ui.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.hmatalonga.greenhub.fragments.AppsFragment;
import com.hmatalonga.greenhub.fragments.DeviceFragment;
import com.hmatalonga.greenhub.fragments.HistoryFragment;
import com.hmatalonga.greenhub.fragments.HomeFragment;
import com.hmatalonga.greenhub.fragments.StatisticsFragment;

/**
 * Adapter that provides fragments for tab menus.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_TABS = 4;

    public static final int TAB_HOME      = 0;
    private static final int TAB_APPS     = 1;
    private static final int TAB_HISTORY  = 2;
    public static final int TAB_CHARTS    = 3;
//    public static final int TAB_MY_DEVICE = 4;
//     private static final int TAB_ABOUT     = 3;

    private final SparseArray<Fragment> mFragments = new SparseArray<>(NUM_TABS);

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        // work around "Fragment no longer exists for key" Android bug
        // by catching the IllegalStateException
        // https://code.google.com/p/android/issues/detail?id=42601
        try {
            super.restoreState(state, loader);
        } catch (IllegalStateException e) {
            // nop
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_HOME:
                return HomeFragment.newInstance();
            case TAB_APPS:
                return AppsFragment.newInstance();
            case TAB_HISTORY:
                return HistoryFragment.newInstance();
//            case TAB_MY_DEVICE:
//                return DeviceFragment.newInstance();
            case TAB_CHARTS:
                return StatisticsFragment.newInstance();
//            case TAB_ABOUT:
//                return AboutFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object item = super.instantiateItem(container, position);
        if (item instanceof Fragment) {
            mFragments.put(position, (Fragment) item);
        }
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getFragment(int position) {
        if (isValidPosition(position)) {
            return mFragments.get(position);
        } else {
            return null;
        }
    }

    public String getTabName(int position) {
        switch (position) {
            case TAB_HOME:
                return "Home";
            case TAB_APPS:
                return "All Apps";
            case TAB_HISTORY:
                return "Cleaning History";
//            case TAB_MY_DEVICE:
//                return "My Device";
            case TAB_CHARTS:
                return "Statistics";
            default:
                return "default";
        }
    }

    private boolean isValidPosition(int position) {
        return (position >= 0 && position < NUM_TABS);
    }
}