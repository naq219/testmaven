/**
 * 
 */
package com.telpoo.frame.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

/**
 * @author NAQ219
 * 
 */
public class FileSupport {
	private static String TAG = FileSupport.class.getSimpleName();

	public static String readFromInputStream(InputStream in) throws IOException {
		if (in == null)
			return null;
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"), 1000);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			byte[] utfString = line.getBytes("UTF-8");
			line = new String(utfString, "UTF-8");
			sb.append(line);
		}
		in.close();
		String re = sb.toString();
		re.trim();
		if (re.charAt(0) == 65279)
			sb.deleteCharAt(0);
		return sb.toString();
	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			Mlog.E("getBitmapFromURL-=2342234==" + e);
			return null;
		}
	}

	public static boolean unpackZip(String path, String zipname) {
		FileOutputStream out;
		// byte buf[] = new byte[16384];
		byte buf[] = new byte[8192];
		try {
			FileInputStream fin = new FileInputStream(path + zipname);
			ZipInputStream zis = new ZipInputStream(fin);
			ZipEntry entry = zis.getNextEntry();
			while (entry != null) {
				if (entry.isDirectory()) {
					File newDir = new File(path + entry.getName());
					newDir.mkdir();
				} else {
					String name = entry.getName();
					File outputFile = new File(path + name);
					String outputPath = outputFile.getCanonicalPath();
					name = outputPath.substring(outputPath.lastIndexOf("/") + 1);
					outputPath = outputPath.substring(0, outputPath.lastIndexOf("/"));
					File outputDir = new File(outputPath);
					outputDir.mkdirs();
					outputFile = new File(outputPath, name);
					outputFile.createNewFile();
					out = new FileOutputStream(outputFile);

					int numread = 0;
					do {
						numread = zis.read(buf);
						if (numread <= 0) {
							break;
						} else {
							out.write(buf, 0, numread);
						}
					} while (true);
					out.close();
				}
				entry = zis.getNextEntry();
			}
			zis.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String Stream2String(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		return sb.toString();
	}

	public static String file2String(String filePath) throws Exception {
		File fl = new File(filePath);
		FileInputStream fin = new FileInputStream(fl);
		String ret = Stream2String(fin);
		// Make sure you close all streams.
		fin.close();
		return ret;
	}

	public static boolean isExternalStorageAvailable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public static Bitmap LoadBitmapFromPath(String path) {
		Mlog.D("LoadBitmapFromPath-path=" + path);
		if (path == null)
			return null;
		Bitmap bitmap = null;
		BitmapFactory.Options resample = new BitmapFactory.Options();
		resample.inSampleSize = 1;
		boolean isOutOfMemory = false;
		do {
			try {
				bitmap = BitmapFactory.decodeFile(path, resample);
				isOutOfMemory = false;
			} catch (IllegalArgumentException e) {
				isOutOfMemory = false;
				Log.e("nth", "LoadBitmapFromInputStream - Illegal argument exception.");
			} catch (OutOfMemoryError e) {
				resample.inSampleSize *= 2;
				isOutOfMemory = true;
				Log.i("nth", "LoadBitmapFromInputStream - Out of memory! resampling to " + resample.inSampleSize);
			}
		} while (isOutOfMemory);
		return bitmap;
	}

	@Deprecated
	/**
	 * phuong thuc nay qua ngu , dung cach moi o day  See {@link copyDbFromAsset} 
	 * @param bm
	 * @param name
	 * @param directory
	 * @return
	 */
	public static boolean saveBitmap(Bitmap bm, String name, String directory) {
		File file = new File(directory, name);
		FileOutputStream outStream;
		try {
			outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
			return true;
		} catch (Exception e) {

			Mlog.E(TAG + "- saveBitmap -" + e);
			return false;
		}

	}

	public static boolean saveBitmap(Bitmap bm, String path) {

		File file = new File(path);
		File OutFolder = new File(file.getParent());
		OutFolder.mkdirs();

		FileOutputStream outStream;
		try {
			file.createNewFile();
			outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
			return true;
		} catch (Exception e) {

			Mlog.E(TAG + "- saveBitmap -" + e);
			return false;
		}

	}

	public static boolean copyDbFromAsset(final Context ctx, String dbName) {
		if (ctx != null) {
			File f = ctx.getDatabasePath(dbName);
			if (!f.exists()) {

				// check databases exists
				if (!f.getParentFile().exists())
					f.getParentFile().mkdir();

				try {
					InputStream in = ctx.getAssets().open(dbName);
					OutputStream out = new FileOutputStream(f.getAbsolutePath());

					byte[] buffer = new byte[1024];
					int length;
					while ((length = in.read(buffer)) > 0) {
						out.write(buffer, 0, length);
					}
					in.close();
					out.close();
					Mlog.I(TAG + " -copyDbFromAsset- DONE");
					return true;
				} catch (Exception ex) {
					Mlog.E(TAG + " -copyDbFromAsset- " + ex);
					return false;
				}
			} else {
				return true;
			}
		} else {

			Mlog.E(TAG + " -copyDbFromAsset- context=null");
			return false;
		}
	}

	public static String readTextFromAssets(String filePath, Context ct) throws IOException {
		InputStream input = ct.getAssets().open(filePath);
		return readFromInputStream(input);
	}

	public static void writeToFile(String data, Context ct, String filePath) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ct.openFileOutput(filePath,
					Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	/** Finds the proper location on the SD card where we can save files. */
	public static File getStorageDirectory(Context ct) {
		// Log.d(TAG, "getStorageDirectory(): API level is " +
		// Integer.valueOf(android.os.Build.VERSION.SDK_INT));

		String state = null;
		try {
			state = Environment.getExternalStorageState();
		} catch (RuntimeException e) {
			Mlog.E(TAG + " -Is the SD card visible?-" + e);
		}

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

			// We can read and write the media
			// if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) > 7) {
			// For Android 2.2 and above

			try {
				return ct.getExternalFilesDir(Environment.MEDIA_MOUNTED);
			} catch (NullPointerException e) {
				// We get an error here if the SD card is visible, but full
				Mlog.E(TAG + "-" + e);
			}

			// } else {
			// // For Android 2.1 and below, explicitly give the path as, for
			// example,
			// // "/mnt/sdcard/Android/data/edu.sfsu.cs.orange.ocr/files/"
			// return new
			// File(Environment.getExternalStorageDirectory().toString() +
			// File.separator +
			// "Android" + File.separator + "data" + File.separator +
			// getPackageName() +
			// File.separator + "files" + File.separator);
			// }

		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			Mlog.E(TAG + "External storage is read-only");
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			Mlog.E(TAG + "External storage is unavailable");
		}
		return null;
	}

	public static File getCatcheDir(Context ct) {
		return (ct.getExternalCacheDir() != null ? ct.getExternalCacheDir() : ct.getCacheDir());
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	public static void copyfile(String srFile, String dtFile) {
		try {
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("File copied.");
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	 public static String fromUTF8String(String escapedUtf8Str) {
	        if (hasText(escapedUtf8Str)) {
	            StringBuilder output = new StringBuilder();
	 
	            char[] charArray = escapedUtf8Str.toCharArray();
	 
	            for (int i = 0; i < charArray.length; ++i) {
	                char a = charArray[i];
	                if (a == '\\' && i < charArray.length - 5) {
	                    char b = charArray[i + 1];
	                    if (b == 'u') {
	                        String hex = charArray[i + 2] + "" + charArray[i + 3]
	                                + charArray[i + 4] + charArray[i + 5];
	                        int intValue = Integer.parseInt(hex, 16);
	                        output.append((char) intValue);
	                        i = i + 5;
	                    }
	                } else {
	                    output.append(a);
	                }
	            }
	            return output.toString();
	        }
	        return "";
	    }
	    public static boolean hasText(String str) {
	        if (isEmpty(str)) {
	            return false;
	        }
	        int strLen = str.length();
	        for (int i = 0; i < strLen; i++) {
	            if (!Character.isWhitespace(str.charAt(i))) {
	                return true;
	            }
	        }
	        return false;
	    }
	    public static boolean isEmpty(String str) {
	        return (str == null || str.length() == 0);
	    }

}
