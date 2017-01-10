package com.ffi.fingerprint;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.konylabs.android.KonyMain;
import com.konylabs.vm.Function;

/**
 * Created by ashish.ramtekkar on 05-01-2017.
 */
public class FingerPrintScannerFFI {

	private static FingerprintManager fingerprintManager;
	private static KeyguardManager keyguardManager;
	private static KeyStore keyStore;
	private static KeyGenerator keyGenerator;
	private static final String KEY_NAME = "example_key";
	private static Cipher cipher;
	private static FingerprintManager.CryptoObject cryptoObject;

	static Context context;
	private FingerprintManager.CryptoObject mCryptoObject;

	public static void initHardwareForFingerPrint(Function callback) {

		try {

			keyguardManager = (KeyguardManager) context
					.getSystemService(Context.KEYGUARD_SERVICE);
			fingerprintManager = (FingerprintManager) context
					.getSystemService(Context.FINGERPRINT_SERVICE);
			Toast.makeText(context, "init called--->", Toast.LENGTH_SHORT)
					.show();
			if (!keyguardManager.isKeyguardSecure()) {

				setParamStatuswithMessages(true, "1003",
						"Lock screen security not enabled in Settings",
						callback);
				return;
			}

			if (ActivityCompat.checkSelfPermission(context,
					Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

				setParamStatuswithMessages(true, "1004",
						"Fingerprint authentication permission not enabled",
						callback);

				return;
			}

			if (!fingerprintManager.hasEnrolledFingerprints()) {

				setParamStatuswithMessages(true, "1005",
						"Register at least one fingerprint in Settings",
						callback);
				// This happens when no fingerprints are registered.

				return;
			}

			generateKey();

			if (cipherInit()) {
				cryptoObject = new FingerprintManager.CryptoObject(cipher);
				FingerprintHandler helper = new FingerprintHandler(context,
						callback);
				helper.startAuth(fingerprintManager, cryptoObject);
			}

		} catch (Exception e) {
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	protected static void generateKey() {
		try {
			keyStore = KeyStore.getInstance("AndroidKeyStore");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			keyGenerator = KeyGenerator.getInstance(
					KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
		} catch (Exception e) {
			throw new RuntimeException("Failed to get KeyGenerator instance", e);
		}

		try {
			keyStore.load(null);
			keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
					KeyProperties.PURPOSE_ENCRYPT
							| KeyProperties.PURPOSE_DECRYPT)
					.setBlockModes(KeyProperties.BLOCK_MODE_CBC)
					.setUserAuthenticationRequired(true)
					.setEncryptionPaddings(
							KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
			keyGenerator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//

	public static boolean cipherInit() {
		try {
			cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
					+ KeyProperties.BLOCK_MODE_CBC + "/"
					+ KeyProperties.ENCRYPTION_PADDING_PKCS7);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get Cipher", e);
		}

		try {
			keyStore.load(null);
			SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return true;
		} catch (KeyPermanentlyInvalidatedException e) {
			return false;
		} catch (Exception e) {
			throw new RuntimeException("Failed to init Cipher", e);
		}
	}

	public static boolean isFingerPrintSupport() {
		context = KonyMain.getActivityContext();
		// Toast.makeText(context, " called", Toast.LENGTH_LONG).show();
		// Check if we're running on Android 6.0 (M) or higher
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// Fingerprint API only available on from Android 6.0 (M)
			FingerprintManager fingerprintManager = (FingerprintManager) context
					.getSystemService(Context.FINGERPRINT_SERVICE);
			if (!fingerprintManager.isHardwareDetected()) {
				Toast.makeText(context,
						"Device doesn't support fingerprint authentication",
						Toast.LENGTH_LONG).show();
				return false;
				//
			} else {
				// Toast.makeText(context,
				// "Everything is ready for fingerprint authentication",
				// Toast.LENGTH_LONG).show();

				return true;

			}
		} else {

			return false;
		}

	}

	private static void setParamStatuswithMessages(boolean status,
			String statusCode, String messages, Function callback) {

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
