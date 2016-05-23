WeakAsyncTask的使用
        final ArrayList<ContentProviderOperation> diff = mAdapter.mAccounts.buildDiff();
        if (diff.isEmpty()) {
            finish();
            return;
        }

        new UpdateTask(this).execute(diff);
      
         /**
             * Background task that persists changes to {@link Groups#GROUP_VISIBLE},
             * showing spinner dialog to user while updating.
             */
            public static class UpdateTask extends
                    WeakAsyncTask<ArrayList<ContentProviderOperation>, Void, Void, Activity> {
                private ProgressDialog mProgress;
        
                public UpdateTask(Activity target) {
                    super(target);
                }
        
                /** {@inheritDoc} */
                @Override
                protected void onPreExecute(Activity target) {
                    final Context context = target;
        
                    mProgress = ProgressDialog.show(
                            context, null, context.getText(R.string.savingDisplayGroups));
        
                    // Before starting this task, start an empty service to protect our
                    // process from being reclaimed by the system.
                    context.startService(new Intent(context, EmptyService.class));
                }
        
                /** {@inheritDoc} */
                @Override
                protected Void doInBackground(
                        Activity target, ArrayList<ContentProviderOperation>... params) {
                    final Context context = target;
                    final ContentValues values = new ContentValues();
                    final ContentResolver resolver = context.getContentResolver();
        
                    try {
                        final ArrayList<ContentProviderOperation> diff = params[0];
                        resolver.applyBatch(ContactsContract.AUTHORITY, diff);
                    } catch (RemoteException e) {
                        Log.e(TAG, "Problem saving display groups", e);
                    } catch (OperationApplicationException e) {
                        Log.e(TAG, "Problem saving display groups", e);
                    }
        
                    return null;
                }
        
                /** {@inheritDoc} */
                @Override
                protected void onPostExecute(Activity target, Void result) {
                    final Context context = target;
        
                    try {
                        mProgress.dismiss();
                    } catch (Exception e) {
                        Log.e(TAG, "Error dismissing progress dialog", e);
                    }
        
                    target.finish();
        
                    // Stop the service that was protecting us
                    context.stopService(new Intent(context, EmptyService.class));
                }
            }
        