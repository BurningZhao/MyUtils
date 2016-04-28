EmptyService的使用
EmptyService ：Background Service that is used to keep our process alive long enough for background threads to finish. Started and stopped directly by specific background tasks when needed.

public class EmptyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

<!-- Stub service used to keep our process alive long enough for background threads to finish their operations. -->
        <service
            android:name=".common.util.EmptyService"
            android:exported="false" />

   /**
     * Background task for persisting edited contact data, using the changes
     * defined by a set of {@link RawContactDelta}. This task starts
     * {@link EmptyService} to make sure the background thread can finish
     * persisting in cases where the system wants to reclaim our process.
     */
    private static class PersistTask extends AsyncTask<RawContactDeltaList, Void, Integer> {
        // In the future, use ContactSaver instead of WeakAsyncTask because of
        // the danger of the activity being null during a save action
        private static final int PERSIST_TRIES = 3;

        private static final int RESULT_UNCHANGED = 0;
        private static final int RESULT_SUCCESS = 1;
        private static final int RESULT_FAILURE = 2;

        private ConfirmAddDetailActivity activityTarget;

        private AccountTypeManager mAccountTypeManager;

        public PersistTask(ConfirmAddDetailActivity target, AccountTypeManager accountTypeManager) {
            activityTarget = target;
            mAccountTypeManager = accountTypeManager;
        }

        @Override
        protected void onPreExecute() {
            sProgressDialog = new WeakReference<ProgressDialog>(ProgressDialog.show(activityTarget,
                    null, activityTarget.getText(R.string.savingContact)));

            // Before starting this task, start an empty service to protect our
            // process from being reclaimed by the system.
            final Context context = activityTarget;
            context.startService(new Intent(context, EmptyService.class));
        }

        @Override
        protected Integer doInBackground(RawContactDeltaList... params) {
            final Context context = activityTarget;
            final ContentResolver resolver = context.getContentResolver();

            RawContactDeltaList state = params[0];

            if (state == null) {
                return RESULT_FAILURE;
            }

            // Trim any empty fields, and RawContacts, before persisting
            RawContactModifier.trimEmpty(state, mAccountTypeManager);

            // Attempt to persist changes
            int tries = 0;
            Integer result = RESULT_FAILURE;
            while (tries++ < PERSIST_TRIES) {
                try {
                    // Build operations and try applying
                    // Note: In case we've created a new raw_contact because the selected contact
                    // is read-only, buildDiff() will create aggregation exceptions to join
                    // the new one to the existing contact.
                    final ArrayList<ContentProviderOperation> diff = state.buildDiff();
                    ContentProviderResult[] results = null;
                    if (!diff.isEmpty()) {
                         results = resolver.applyBatch(ContactsContract.AUTHORITY, diff);
                    }

                    result = (diff.size() > 0) ? RESULT_SUCCESS : RESULT_UNCHANGED;
                    break;

                } catch (RemoteException e) {
                    // Something went wrong, bail without success
                    Log.e(TAG, "Problem persisting user edits", e);
                    break;

                } catch (OperationApplicationException e) {
                    // Version consistency failed, bail without success
                    Log.e(TAG, "Version consistency failed", e);
                    break;
                }
            }

            return result;
        }

        /** {@inheritDoc} */
        @Override
        protected void onPostExecute(Integer result) {
            final Context context = activityTarget;

            dismissProgressDialog();

            // Show a toast message based on the success or failure of the save action.
            if (result == RESULT_SUCCESS) {
                Toast.makeText(context, R.string.contactSavedToast, Toast.LENGTH_SHORT).show();
            } else if (result == RESULT_FAILURE) {
                Toast.makeText(context, R.string.contactSavedErrorToast, Toast.LENGTH_LONG).show();
            }

            // Stop the service that was protecting us
            context.stopService(new Intent(context, EmptyService.class));
            activityTarget.onSaveCompleted(result != RESULT_FAILURE);
        }
    }
