/*
 * MaxLock, an Xposed applock module for Android
 * Copyright (C) 2014-2015  Maxr1998
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.Maxr1998.xposed.maxlock.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import de.Maxr1998.xposed.maxlock.Common;

public class MLPreferences {

    @SuppressLint("WorldReadableFiles")
    public static SharedPreferences getPrefsApps(Context context) {
        //noinspection deprecation
        return context.getSharedPreferences(Common.PREFS_APPS, Context.MODE_WORLD_READABLE);
    }
}
