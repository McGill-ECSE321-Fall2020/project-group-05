package com.example.visartmobile.util;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * UserAuth provides helper methods to log a user into firebase, and upload and
 * access images in firebase storage.
 */
public class UserAuth {
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static StorageReference storageRef = storage.getReference();

    /**
     * A method to get the UserDto object of the currently logged in user.
     *
     * @param success handler on success, passes UserDto object
     * @param failure handler on failure, provides failure type
     */
    public static void retriveCurrentUser(Callback<UserDto> success, Callback<AuthFailureType> failure) {
        if (success == null)
            success = (userId) -> {
            };

        if (failure == null)
            failure = (failureType) -> {
            };
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Callback<UserDto> finalSuccess = success;
        Callback<AuthFailureType> finalFailure = failure;

        HttpUtils.get("users/get/" + userId, new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                finalSuccess.callback(UserDto.parseJSON(response.body().string()));
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                finalFailure.callback(AuthFailureType.GET_USER_REQUEST_FAILED);
            }
        });
    }

    /**
     * Performs full firebase/spring db login, whose success handler (callback) gives a UserDto object, instead of just a userId.
     *
     * @param identifier email/username to login with
     * @param password   password to verify user
     * @param isEmail    true if identifier is email, false if identifier is username
     * @param success    handler that returns UserDto upon completion, null if error in retrieving user
     * @param failure    handler that returns AuhFailureType based on type of authentication failure
     */
    public static void loginGetUser(String identifier, String password, boolean isEmail, Callback<UserDto> success, Callback<AuthFailureType> failure) {
        if (success == null)
            success = (userId) -> {
            };

        if (failure == null)
            failure = (failureType) -> {
            };

        Callback<UserDto> finalSuccess = success;
        Callback<AuthFailureType> finalFailure = failure;
        loginPost(identifier, password, isEmail, (userId) -> {
            HttpUtils.get("users/get/" + userId, new okhttp3.Callback() {
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    finalSuccess.callback(UserDto.parseJSON(response.body().string()));
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    finalFailure.callback(AuthFailureType.GET_USER_REQUEST_FAILED);
                }
            });
        }, failure);
    }

    /**
     * Performs full firebase/spring db login, whose success handler (callback) gives a String userId of the logged in user, instead of a UserDto.
     *
     * @param identifier email/username to login with
     * @param password   password to verify user
     * @param isEmail    true if identifier is email, false if identifier is username
     * @param success    handler that returns String userId upon completion
     * @param failure    handler that returns AuhFailureType based on type of authentication failure
     */
    public static void loginPost(String identifier, String password, boolean isEmail, Callback<String> success, Callback<AuthFailureType> failure) {
        String[][] data = {
                {"emailAddress", identifier},
                {"username", identifier},
                {"password", password}
        };
        String restEndpoint = isEmail ? "users/email_login" : "users/login";

        if (success == null)
            success = (userId) -> {
            };

        if (failure == null)
            failure = (failureType) -> {
            };

        Callback<AuthFailureType> finalFailure = failure;
        Callback<String> finalSuccess = success;
        HttpUtils.postForm(restEndpoint, data, new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                finalFailure.callback(AuthFailureType.LOGIN_REQUEST_FAILED); // Post request cannot be made
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject credentials = new JSONObject(response.body().string());
                    final String firebaseJWT = credentials.getString("firebaseJWT");
                    final String userId = credentials.getString("userId");
                    mAuth.signInWithCustomToken(firebaseJWT).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                finalSuccess.callback(user.getUid());
                            } else {
                                finalFailure.callback(AuthFailureType.FIREBASE_ERROR);
                            }
                        }
                    });
                } catch (JSONException e) {
                    finalFailure.callback(AuthFailureType.LOGIN_INCORRECT); // JSON could not be parsed, usually 500 error, failed login
                }
            }
        });
    }

    /**
     * Uploads the given file at the Uri to the firebase storage with the name filename under postImages/.
     *
     * @param file     the file to upload
     * @param filename the filename to write in the database
     * @param success  handler on success
     */
    public static void uploadImageFile(Uri file, String filename, Callback<Uri> success) {
        uploadImageFile(file, filename, success, null);
    }

    /**
     * Uploads an image given by Uri, and saves it into postImages in the database under the given filename.
     *
     * @param file     actual file to upload, designated by uri
     * @param filename string filename to store in database, should be unique
     * @param success  custom success handler, can be null for no action
     * @param failure  custom failure handler, can be null for no action
     */
    public static void uploadImageFile(Uri file, String filename, Callback<Uri> success, Callback<Task> failure) {
        // Create file metadata including the content type
        StorageReference ref = storageRef.child("postImages").child(filename);
        uploadFile(file, ref, success, failure);
    }

    /**
     * Uploads a file to firebase storage given the uri of the file to upload, a StorageReference for where to
     * upload the file, a success handler which is given the download link of the uploaded file, and a failure
     * handler, which provides the failed task as a parameter.
     *
     * @param file    the file uri to upload
     * @param ref     the ref to upload to
     * @param success handler on success
     * @param failure handler on failure
     */
    private static void uploadFile(Uri file, StorageReference ref, Callback<Uri> success, Callback<Task> failure) {
        UploadTask uploadTask = ref.putFile(file);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    if (success != null)
                        success.callback(downloadUri);
                } else {
                    // Handle failures
                    // ...
                    if (failure != null)
                        failure.callback(task);
                }
            }
        });
    }

    /**
     * gets image links for images stored in the postedImages/ directory, and only requires the unique filename, not the full path.
     *
     * @param filename the filename of the image stored in postedImages/
     * @param success  the success handler, upon retrieving the image link
     */
    public static void getImageLink(String filename, Callback<Uri> success, Callback<Task> failure) {
        getFileLink("postedImages/" + filename, success, failure);
    }

    /**
     * Gets the image link for the placeholder profile picture.
     *
     * @param success a custom success handler, can be null for no action
     * @param failure a custom failure handler, can be null for no action
     */
    public static void getDefaultPicLink(Callback<Uri> success, Callback<Task> failure) {
        getFileLink("profile-placeholder.png", success, failure);
    }

    /**
     * Gets the download link for the file in firebase storage, given the filepath, and passes it to
     * the success handler. It provides the failed task to the failure handler on failure.
     *
     * @param filepath the filepath in firebase storage to access
     * @param success  handler on success, receives download link uri
     * @param failure  handler on failure, receives failed task
     */
    private static void getFileLink(String filepath, Callback<Uri> success, Callback<Task> failure) {
        storageRef.child(filepath).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    if (success != null)
                        success.callback(downloadUri);
                } else {
                    // Handle failures
                    // ...
                    if (failure != null)
                        failure.callback(task);
                }
            }
        });
    }

    /**
     * A one method interface for use as a simple callback method.
     *
     * @param <T> the type of the parameter passed to the callback
     */
    public static interface Callback<T> {
        void callback(T t);
    }

    /**
     * The type of authorization failures that could be encountered when calling the login...() methods.
     */
    public static enum AuthFailureType {
        LOGIN_REQUEST_FAILED,
        LOGIN_INCORRECT,
        FIREBASE_ERROR,
        GET_USER_REQUEST_FAILED
    }
}
