package aleksey.git.firebaslogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText nameText;
    private EditText passwordText;
    TextInputLayout errorField;
    TextInputLayout emailErrorField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameText = findViewById(R.id.nameLogin);
        passwordText = findViewById(R.id.passLogin);
        errorField = findViewById(R.id.passLoginParent);
        emailErrorField = findViewById(R.id.nameLoginParent);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void startAnotherActivity(View view) {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }


    public void signinCheck(View view) {
        if (nameText.getText().toString().trim() != "") {
            emailErrorField.setErrorEnabled(false);
        }
        if (passwordText.getText().toString().trim() != "") {
            errorField.setErrorEnabled(false);
        }
        if (nameText.getText().toString().trim().equals("") || passwordText.getText().toString().trim().equals("")) {
            emailErrorField.setError("This field can't be empty");
            errorField.setError("This field can't be empty");
        } else if (passwordText.getText().toString().length() < 6) {
            errorField.setError("Password must has at least 6 symbols.");
        } else {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
            builder.setTitle("Succsessful sign in");
            builder.setMessage("You has been signed in succsessful!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();

            signin(nameText.getText().toString(), passwordText.getText().toString());
        }
    }
}
