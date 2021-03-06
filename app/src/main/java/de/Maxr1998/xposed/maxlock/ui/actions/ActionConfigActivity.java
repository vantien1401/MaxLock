/*
 * MaxLock, an Xposed applock module for Android
 * Copyright (C) 2014-2018  Max Rumpf alias Maxr1998
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

package de.Maxr1998.xposed.maxlock.ui.actions;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import de.Maxr1998.xposed.maxlock.Common;
import de.Maxr1998.xposed.maxlock.R;
import de.Maxr1998.xposed.maxlock.util.Util;

import static com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE;
import static com.twofortyfouram.locale.api.Intent.EXTRA_STRING_BLURB;
import static de.Maxr1998.xposed.maxlock.ui.actions.ActionsHelper.ACTION_EXTRA_KEY;
import static de.Maxr1998.xposed.maxlock.ui.actions.ActionsHelper.getKey;

public class ActionConfigActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Intent result;
    private boolean taskerMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setTheme(this);
        super.onCreate(savedInstanceState);
        if (getCallingActivity() == null || BundleScrubber.scrub(getIntent())) {
            finish();
            return;
        }
        final Bundle extra = getIntent().getBundleExtra(EXTRA_BUNDLE);
        if (BundleScrubber.scrub(extra)) {
            finish();
            return;
        }

        String action = getIntent().getAction();
        if (action != null && action.equals("com.twofortyfouram.locale.intent.action.EDIT_SETTING")) {
            taskerMode = true;
        }

        setContentView(R.layout.activity_action_config);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name) + " Actions");
        if (taskerMode && !PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Common.ENABLE_TASKER, false)) {
            findViewById(R.id.tasker_warning).setVisibility(View.VISIBLE);
            findViewById(R.id.tasker_config_main).setVisibility(View.GONE);
            return;
        }

        final RadioGroup options = findViewById(R.id.tasker_config_options);
        final Button apply = findViewById(R.id.tasker_apply_button);
        apply.setOnClickListener(view -> {
            int checked = options.getCheckedRadioButtonId();
            result = new Intent();
            if (taskerMode) {
                Bundle resultBundle = new Bundle();
                resultBundle.putInt(ACTION_EXTRA_KEY, ActionsHelper.getKey(checked));
                result.putExtra(EXTRA_BUNDLE, resultBundle);
                result.putExtra(EXTRA_STRING_BLURB, ((RadioButton) findViewById(checked)).getText().toString());
            } else {
                Intent shortcut = new Intent(ActionConfigActivity.this, ActionActivity.class);
                shortcut.putExtra(ACTION_EXTRA_KEY, getKey(checked));
                result.putExtra(Intent.EXTRA_SHORTCUT_NAME, ((RadioButton) findViewById(checked)).getText().toString());
                result.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
                result.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcut);
            }
            finish();
        });
    }

    @Override
    public void finish() {
        if (result != null) {
            setResult(RESULT_OK, result);
        } else setResult(RESULT_CANCELED);
        super.finish();
    }
}