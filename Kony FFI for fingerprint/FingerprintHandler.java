package com.ffi.fingerprint;

import java.util.Hashtable;

import com.konylabs.vm.Function;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends
		FingerprintManager.AuthenticationCallback {

	private CancellationSignal cancellationSignal;
	private Context appContext;
	Function callback;

	public FingerprintHandler(Context context, Function callback) {
		appContext = context;
		this.callback = callback;
	}

	public void startAuth(FingerprintManager manager,
			FingerprintManager.CryptoObject cryptoObject) {

		cancellationSignal = new CancellationSignal();

		if (ActivityCompat.checkSelfPermission(appContext,
				Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

	}

	@Override
	public void onAuthenticationError(int errMsgId, CharSequence errString) {
		// Toast.makeText(appContext, "Authentication error\n" + errString,
		// Toast.LENGTH_LONG).show();

		setParamStatuswithMessages(false, "1001", "Authentication error\n"
				+ errString);

	}

	@Override
	public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
		// Toast.makeText(appContext, "Authentication help\n" + helpString,
		// Toast.LENGTH_LONG).show();
		setParamStatuswithMessages(false, "1001", "Authentication help.");
	}

	@Override
	public void onAuthenticationFailed() {
		// Toast.makeText(appContext, "Authentication failed.",
		// Toast.LENGTH_LONG)
		// .show();
		setParamStatuswithMessages(false, "1001", "Authentication failed.");
	}

	@Override
	public void onAuthenticationSucceeded(
			FingerprintManager.AuthenticationResult result) {

		// Toast.makeText(appContext, "Authentication succeeded.",
		// Toast.LENGTH_LONG).show();

		// cancellationSignal.cancel();

		setParamStatuswithMessages(true, "1000", "Authentication succeeded.");

	}

	public void setParamStatuswithMessages(boolean status, String statusCode,
			String messages) {

		Hashtable infoTable = new Hashtable();
		infoTable.put("status", status);
		infoTable.put("message", messages);
		infoTable.put("statuscode", statusCode);

		try {
			callback.execute(new Object[] { infoTable });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}