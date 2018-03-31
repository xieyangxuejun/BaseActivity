package com.foretree.base.delegate;

import android.content.Intent;
import android.os.Bundle;

/**
 * Project AndroidBase
 * Created by xieyangxuejun on 17-9-11.
 */

public interface IActivityDelegate {

    void onCreateBefore(Bundle savedInstanceState);

    void onCreate(Bundle savedInstanceState);

    void onSaveInstanceState(Bundle outState);

    void onResume();

    void onPause();

    void onDestroy();

    void onBackPressed();

    void finish();

    void overridePendingTransition(int enterAnim, int exitAnim);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
