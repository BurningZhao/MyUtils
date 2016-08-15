package com.zhao.myutils.task;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.zhao.myutils.utils.LogUtil;

import java.lang.ref.WeakReference;

/**
 * Description:使用AsyncQueryHandler进行查询，避免weak,
 *
 * @author zhaoqingbo
 * @since 2015/10/15
 */
public class WeakAsyncQueryHandler extends AsyncQueryHandler {
    private static final String TAG = "WeakAsyncQueryHandler";

    private final WeakReference<Listener> mListener;
    private int initToken;
    private int mQueryToken;

    public WeakAsyncQueryHandler(ContentResolver cr, Listener listener) {
        super(cr);
        mListener = new WeakReference<>(listener);
    }

    public void setInitToken(int initToken) {
        this.initToken = initToken;
        mQueryToken = initToken;
    }

    public int getQueryToken() {
        return mQueryToken;
    }

    /**
     * fetch the list of search result
     * <p>
     * It will asynchronously update the content of the list view when the fetch completes.
     */
    public void startQuery(Uri uri, String[] projection, String selection,
                           String[] selectionArgs, String orderBy) {
        LogUtil.d(TAG, "fetch mQueryToken " + mQueryToken);
        if (mQueryToken > initToken) {
            cancelFetch(mQueryToken);
        }
        mQueryToken += 1;
        startQuery(mQueryToken, null, uri, projection, selection, selectionArgs, orderBy);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if (token == mQueryToken) {
            updateCursor(cursor);
        } else {
            // not update ui if token not equal current token.
            closeCursor(cursor);
        }
    }

    /**
     * Updates the adapter in the call log fragment to show the new cursor data.
     * Returns true if the listener took ownership of the cursor.
     */
    private boolean updateCursor(Cursor cursor) {
        final Listener listener = mListener.get();
        return listener != null && listener.onFetched(cursor);
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * Cancel any pending fetch request.
     */
    public void cancelFetch(int token) {
        cancelOperation(token);
    }

    /**
     * Listener to completion of various queries.
     */
    public interface Listener {
        /**
         * Called when {@link WeakAsyncQueryHandler#startQuery(Uri, String[], String, String[], String)}complete.
         * Returns true if takes ownership of cursor.
         */
        boolean onFetched(Cursor combinedCursor);
    }
}
