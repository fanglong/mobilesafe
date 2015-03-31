package com.fanglong.mobilesafe.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.fanglong.mobilesafe.application.MobileSafeApplication;
import com.fanglong.mobilesafe.common.log.Logger;

public class FileHelper {

	public static String getAppRootPath() {
		String rootPath = null;
		String externalStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
			rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Up366Mobile";
		} else {
			rootPath = MobileSafeApplication.getGlobalContext().getFilesDir().getAbsolutePath();
		}
		return rootPath;
	}

	public static void deleteDir(String dir) {
		Logger.error("FFFFF - deleteDir(String dir) : " + dir);
		File file = new File(dir);
		File temp = null;
		for (int i = 0; i < 20; i++) {
			temp = new File(dir + "temp" + i);
			if (!temp.exists()) {
				break;
			}
		}
		if (temp != null) {
			file.renameTo(temp);
		}
	}


	public static boolean homeworkDataExists(String studentId, String homeworkId) {
		File sdDir = Environment.getExternalStorageDirectory();
		if (sdDir.exists()) {
			File homeworkDataFile = new File(sdDir.getAbsolutePath() + "/Up366Mobile/" + studentId + "/homework/" + homeworkId + "/paper/paper.xml");
			if (homeworkDataFile.exists())
				return true;
		}
		return false;
	}

	public static boolean wrongnoteDataExists(String studentId, String guid, String questionId) {
		File sdDir = Environment.getExternalStorageDirectory();
		if (sdDir.exists()) {
			File wrongnoteDataFile = new File(sdDir.getAbsolutePath() + "/Up366Mobile/" + studentId + "/wrongnote/" + guid + "/" + questionId
					+ "/wrongquestion.xml");
			if (wrongnoteDataFile.exists())
				return true;
		}
		return false;
	}

	public static boolean testDataExists(String studentId, String testId) {
		File sdDir = Environment.getExternalStorageDirectory();
		if (sdDir.exists()) {
			File testDataFile = new File(sdDir.getAbsolutePath() + "/Up366Mobile/" + studentId + "/test/" + testId + "/paper/paper.xml");
			if (testDataFile.exists())
				return true;
		}
		return false;
	}

	public static String getBooksDir() {
		String appRootPath = getAppRootPath();
		return appRootPath + File.separator + "books";
	}

	public static String getOralTestDir() {
		String appRootPath = getAppRootPath();
		return appRootPath + File.separator + "oraltests";
	}

	public static String getPapersDir() {
		String oralTestPath = getBooksDir();
		return oralTestPath + File.separator + "papers";
	}

	public static String getPaperLocalPath(String paperContentUrl) {
		String papersDir = getPapersDir();
		paperContentUrl = paperContentUrl.replaceAll("\\\\", "/");
		int pos = paperContentUrl.lastIndexOf(File.separator);
		String fileName = paperContentUrl.substring(pos + 1);
		if (fileName.contains("encryptfile") || fileName.contains("noencfile")) {
			String papersub = paperContentUrl.substring(0, pos);
			int pos1 = papersub.lastIndexOf(File.separator);
			fileName = papersub.substring(pos1 + 1) + ".zip";
		}
		return papersDir + File.separator + fileName;
	}

	public static String getPaperRootDir(String paperContentUrl) {
		String paperLocalPath = getPaperLocalPath(paperContentUrl);
		int pos = paperLocalPath.lastIndexOf(".");
		return paperLocalPath.substring(0, pos);
	}

	public static String getBookImagesDir() {
		String booksPath = getBooksDir();
		return booksPath + File.separator + "images";
	}

	public static String getBookImagePath(String bookImgUrl) {
		String bookImagesDir = getBookImagesDir();
		int pos = bookImgUrl.lastIndexOf(File.separator);
		String fileName = bookImgUrl.substring(pos + 1);
		return bookImagesDir + File.separator + fileName;
	}

	public static Bitmap getBookImage(String bookImgUrl) {
		Bitmap imageBmp = null;
		if (bookImgUrl != null) {
			String imagePath = getBookImagePath(bookImgUrl);
			File imageFile = new File(imagePath);
			if (imageFile.exists()) {
				imageBmp = BitmapFactory.decodeFile(imagePath);
			}
		}
		return imageBmp;
	}

	public static void saveMyBitmap(String bitNamePath, Bitmap mBitmap) {
		File f = new File(bitNamePath + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			Logger.error(e.getMessage(), e);
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
		try {
			fOut.close();
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
	}

	public static void saveMyBitmapJPG(String bitNamePath, Bitmap mBitmap) {
		File f = new File(bitNamePath);
		if (FileHelper.isFileExists(bitNamePath)) {
			FileHelper.deleteFile(bitNamePath);
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			Logger.error(e.getMessage(), e);
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
		try {
			fOut.close();
		} catch (IOException e) {
			Logger.error(e.getMessage(), e);
		}
	}

	public static String getImagesDir() {
		String appRootPath = getAppRootPath();
		return appRootPath + File.separator + "images" + File.separator;
	}

	public static String getImagePath(String imageUrl) {
		String imageDir = getImagesDir();
		int pos = imageUrl.lastIndexOf(File.separator);
		String fileName = imageUrl.substring(pos + 1);
		return imageDir + fileName;
	}

	public static Bitmap getImage(String imageUrl) {
		Bitmap imageBmp = null;
		String imagePath = getImagePath(imageUrl);
		File imageFile = new File(imagePath);
		if (imageFile.exists()) {
			imageBmp = BitmapFactory.decodeFile(imagePath);
		}
		return imageBmp;
	}

	public static boolean isFileExists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	public static void mkdirs(String dirstr) {
		File dir = new File(dirstr);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static void saveFile(String filename, String data) {
		File sdDir = Environment.getExternalStorageDirectory();
		if (sdDir.exists() && sdDir.canWrite()) {
			File uppDir = new File(sdDir.getAbsolutePath() + "/Up366Mobile");
			if (!uppDir.exists())
				uppDir.mkdir();

			if (uppDir.exists() && uppDir.canWrite()) {
				File file = new File(uppDir.getAbsolutePath() + "/" + filename);
				try {
					file.createNewFile();
				} catch (IOException e) {

				}
				if (file.exists() && file.canWrite()) {
					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(file);
						fos.write(data.getBytes());
					} catch (FileNotFoundException e) {
						Log.e("createfile", e.getLocalizedMessage());
					} catch (IOException e) {
						Log.e("createFile", e.getLocalizedMessage());
					} finally {
						if (fos != null) {
							try {
								fos.flush();
								fos.close();
							} catch (IOException e) {
								// swallow
							}

						}
					}
				}
			}
		}
	}

	public static void saveFile(String dirname, String filename, String data) {
		File sdDir = Environment.getExternalStorageDirectory();
		if (sdDir.exists() && sdDir.canWrite()) {
			File uppDir = new File(sdDir.getAbsolutePath() + "/Up366Mobile");
			if (!uppDir.exists())
				uppDir.mkdir();

			if (uppDir.exists() && uppDir.canWrite()) {
				File dir = new File(uppDir.getAbsolutePath() + "/" + dirname);

				if (!dir.exists())
					dir.mkdirs();

				File file = new File(dir, filename);

				try {
					file.createNewFile();
				} catch (IOException e) {

				}
				if (file.exists() && file.canWrite()) {
					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(file);
						fos.write(data.getBytes());
					} catch (FileNotFoundException e) {
						Log.e("createfile", e.getLocalizedMessage());
					} catch (IOException e) {
						Log.e("createFile", e.getLocalizedMessage());
					} finally {
						if (fos != null) {
							try {
								fos.flush();
								fos.close();
							} catch (IOException e) {
								// swallow
							}

						}
					}
				}
			}
		}
	}

	public static void saveFile(String dirname, String filename, byte[] data) {
		File sdDir = Environment.getExternalStorageDirectory();
		if (sdDir.exists() && sdDir.canWrite()) {
			File uppDir = new File(sdDir.getAbsolutePath() + "/Up366Mobile");
			if (!uppDir.exists())
				uppDir.mkdir();

			if (uppDir.exists() && uppDir.canWrite()) {
				File dir = new File(uppDir.getAbsolutePath() + "/" + dirname);

				if (!dir.exists())
					dir.mkdirs();

				File file = new File(dir, filename);

				try {
					file.createNewFile();
				} catch (IOException e) {

				}
				if (file.exists() && file.canWrite()) {
					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(file);
						fos.write(data);
					} catch (FileNotFoundException e) {
						Log.e("createfile", e.getLocalizedMessage());
					} catch (IOException e) {
						Log.e("createFile", e.getLocalizedMessage());
					} finally {
						if (fos != null) {
							try {
								fos.flush();
								fos.close();
							} catch (IOException e) {
								// swallow
							}

						}
					}
				}
			}
		}
	}

	public static String getFile(String filename) {
		String data = null;
		File sdDir = Environment.getExternalStorageDirectory();
		if (sdDir.exists() && sdDir.canRead()) {
			File uppDir = new File(sdDir.getAbsolutePath() + "/Up366Mobile");

			if (uppDir.exists() && uppDir.canRead()) {
				File file = new File(uppDir.getAbsolutePath() + "/" + filename);
				if (file.exists() && file.canRead()) {
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(file);
						byte[] reader = new byte[fis.available()];
						while (fis.read(reader) != -1) {
						}
						data = new String(reader);
					} catch (IOException e) {

					} finally {
						if (fis != null) {
							try {
								fis.close();
							} catch (IOException e) {

							}
						}
					}
				}
			}
		}

		return data;
	}

	public static String getFullPathFile(String fullPath) {
		String data = null;
		File file = new File(fullPath);
		if (file.exists() && file.canRead()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] reader = new byte[fis.available()];
				while (fis.read(reader) != -1) {
				}
				data = new String(reader);
			} catch (IOException e) {
				Logger.error("file : '" + fullPath + "' not found ...");
				//MobclickAgent.reportError(MobileSafeApplication.getGlobalContext(), e);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {

					}
				}
			}
		}
		return data;
	}

	private final static int BUFFER_SIZE = 2048;

	private static boolean mkdirs(File dir) {
		if (!dir.exists()) {
			return dir.mkdirs();
		}
		return true;
	}

	public static boolean writeToFile(InputStream inputStream, String filePath) {
		File file = new File(filePath);
		File parentDir = file.getParentFile();
		if (null != parentDir && !mkdirs(parentDir)) {
			return false;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			int readLen = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((readLen = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
				fos.write(buffer, 0, readLen);
			}
			fos.flush();
			Logger.debug("filePath: " + filePath);
		} catch (Exception ex) {
			Logger.error("writeToFile:" + filePath, ex);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		return true;
	}

	public static boolean writeToFileThrowException(InputStream inputStream, String filePath) throws Exception {
		File file = new File(filePath);
		File parentDir = file.getParentFile();
		if (null != parentDir && !mkdirs(parentDir)) {
			return false;
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			int readLen = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((readLen = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
				fos.write(buffer, 0, readLen);
			}
			fos.flush();
			Logger.debug("filePath: " + filePath);
		} catch (Exception ex) {
			Logger.error("writeToFile:" + filePath, ex);
			throw ex;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		return true;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param destFileName
	 *            目标文件名
	 * @param 搜索overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		// 判断原文件是否存在
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()) {
			Logger.error("复制文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			Logger.error("复制文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}
		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			if (overlay) {
				Logger.debug("目标文件已存在，准备删除它！");
				if (!destFile.delete()) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				if (!destFile.getParentFile().mkdirs()) {
					return false;
				}
			}
		}
		int byteread = 0;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
					in.close();
				} catch (IOException e) {
					Logger.error(e.getMessage());
				}
			}
		}
	}

	public static boolean zipFilesWithoutRelativePath(String directory, String zipFilePath) {
		try {
			File dir = new File(directory);
			if (!dir.isDirectory()) {
				return false;
			}
			FileOutputStream dest = new FileOutputStream(zipFilePath);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte buffer[] = new byte[BUFFER_SIZE];
			for (File file : dir.listFiles()) {
				FileInputStream fi = new FileInputStream(file);
				BufferedInputStream origin = new BufferedInputStream(fi, BUFFER_SIZE);
				String tmp = file.getAbsolutePath();
				String fileName = tmp.substring(tmp.lastIndexOf("/") + 1);
				ZipEntry entry = new ZipEntry(fileName);
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(buffer, 0, BUFFER_SIZE)) != -1) {
					out.write(buffer, 0, count);
				}
				origin.close();
			}
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			Logger.error("zipFilesWithoutRelativePath", e);
			return false;
		}
	}

	public static boolean zip(String resPath, String zipPath) {
		try {
			File resFile = new File(resPath);
			File zipFile = new File(zipPath);
			ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFFER_SIZE));
			boolean result = zipFile(resFile, zipout, "");
			zipout.close();
			return result;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean zipFile(File resFile, ZipOutputStream zipout, String rootpath) {
		try {
			rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator) + resFile.getName();
			if (resFile.isDirectory()) {
				File[] fileList = resFile.listFiles();
				for (File file : fileList) {
					zipFile(file, zipout, rootpath);
				}
			} else {
				byte buffer[] = new byte[BUFFER_SIZE];
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), BUFFER_SIZE);
				zipout.putNextEntry(new ZipEntry(rootpath));
				int realLength;
				while ((realLength = in.read(buffer)) != -1) {
					zipout.write(buffer, 0, realLength);
				}
				in.close();
				zipout.flush();
				zipout.closeEntry();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean unzip(String zipFile, String location) {
		try {
			if (location.endsWith(File.separator) == false) {
				location = location + File.separator;
			}

			File locationDir = new File(location);
			if (!mkdirs(locationDir)) {
				return false;
			}
			byte[] buffer = new byte[BUFFER_SIZE];
			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
			try {
				ZipEntry ze;
				while ((ze = zin.getNextEntry()) != null) {
					String path = location + ze.getName();
					File unzipFile = new File(path);
					if (ze.isDirectory()) {
						if (!mkdirs(unzipFile)) {
							return false;
						}
					} else {
						File parentDir = unzipFile.getParentFile();
						if (null != parentDir && !mkdirs(parentDir)) {
							return false;
						}
						FileOutputStream out = new FileOutputStream(unzipFile, false);
						BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
						try {
							int size;
							while ((size = zin.read(buffer, 0, BUFFER_SIZE)) != -1) {
								fout.write(buffer, 0, size);
							}
							zin.closeEntry();
							fout.flush();
						} finally {
							fout.close();
						}
					}
				}
			} finally {
				zin.close();
			}
		} catch (Exception e) {
			Logger.error("Unzip Exception", e);
			Logger.error("-------------------------------:" + e.getMessage());
			return false;
		}
		return true;
	}

	// public static boolean unzipFile(File zipFile, String unzipDir) {
	// File unzipFolder = new File(unzipDir);
	// if (!unzipFolder.exists()) {
	// unzipFolder.mkdirs();
	// }
	// try {
	// ZipFile zf = new ZipFile(zipFile);
	// Enumeration<?> entries = zf.entries();
	// while (entries.hasMoreElements()) {
	// ZipEntry zipEntry = (ZipEntry) entries.nextElement();
	// if (zipEntry.isDirectory()) {
	// String dir = unzipDir + zipEntry.getName();
	// File folder = new File(dir);
	// if (!folder.exists()) {
	// folder.mkdirs();
	// }
	// } else {
	// Logger.debug("Unzip entry name: " + zipEntry.getName());
	// InputStream is = zf.getInputStream(zipEntry);
	// File entryFile = getRealFileName(unzipDir,
	// zipEntry.getName());
	// FileOutputStream fos = new FileOutputStream(entryFile);
	// byte[] buffer = new byte[1024];
	// int readLen = -1;
	// while ((readLen = is.read(buffer)) >= 0) {
	// fos.write(buffer, 0, readLen);
	// }
	// fos.close();
	// is.close();
	// }
	// }
	// } catch (ZipException e) {
	// Logger.error(e.getMessage());
	// return false;
	// } catch (IOException e) {
	// Logger.error(e.getMessage());
	// return false;
	// }
	// return true;
	// }
	//
	// public static File getRealFileName(String baseDir, String absFileName) {
	// String[] dirs = absFileName.split("/");
	// File ret = new File(baseDir);
	// String substr = null;
	// if (dirs.length > 1) {
	// for (int i = 0; i < dirs.length - 1; i++) {
	// substr = dirs[i];
	// ret = new File(ret, substr);
	// }
	// }
	// Logger.debug("upZipFile" + "ret = " + ret);
	// if (!ret.exists())
	// ret.mkdirs();
	// substr = dirs[dirs.length - 1];
	// Logger.debug("upZipFile" + "substr = " + substr);
	//
	// ret = new File(ret, substr);
	// Logger.debug("upZipFile" + "ret = " + ret);
	// return ret;
	// }

	public static void deleteFile(String fileName) {
		if (fileName == null) {
			return;
		}

		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

//	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
//	public static void deleteDirectoryAndFiles(File rootDirectory) {
//
//		if (PackageUtils.getVersionCode(Up366Application.getGlobalContext()) > 8) {
//			if (!rootDirectory.canExecute()) {
//				return;
//			}
//		}
//
//		if (!rootDirectory.exists()) {
//			return;
//		}
//
//		File[] files = rootDirectory.listFiles();
//		if (files != null) {
//			for (File file : files) {
//				if (file.isDirectory()) {
//					deleteDirectoryAndFiles(file);
//				} else {
//					file.delete();
//				}
//			}
//		}
//		rootDirectory.delete();
//	}

	// *****************************************feishu*******************
	public static String getNotBookRootDir() {
		String appRootPath = getAppRootPath();
		return appRootPath + File.separator + "flipbook";
	}

	public static String getNotBookImagesDir() {
		String booksPath = getNotBookRootDir();
		return booksPath + File.separator + "images";
	}

	public static String getNotBookImagePath(String bookImgUrl) {
		String bookImagesDir = getNotBookImagesDir();
		int pos = bookImgUrl.lastIndexOf(File.separator);
		String fileName = bookImgUrl.substring(pos + 1);
		return bookImagesDir + File.separator + fileName;
	}

	public static String getNotBookBlurImagePath(String bookImgUrl) {
		String imagePath = getNotBookImagePath(bookImgUrl);
		return imagePath.substring(0, imagePath.length() - 4) + "_blur.png";
	}

	public static Bitmap getNotBookImage(String bookImgUrl) {
		Bitmap imageBmp = null;
		if (bookImgUrl != null) {
			String imagePath = getNotBookImagePath(bookImgUrl);
			File imageFile = new File(imagePath);
			if (imageFile.exists()) {
				imageBmp = BitmapFactory.decodeFile(imagePath);
			}
		}
		return imageBmp;
	}

	public static String getNotBookDir() { // //路径：/feishu/notbooks
		String notBookPath = getNotBookRootDir();
		return notBookPath + File.separator + "flipbooks";
	}

	public static String getNotBookZip(String bookId, String fileUrl) { // 路径：/feishu/notbooks/sdfsesge2w3r3wr/chapter.zip
		int pos = fileUrl.lastIndexOf(File.separator);
		String fileName = fileUrl.substring(pos + 1);
		return getNotBookDir() + File.separator + bookId + File.separator + fileName;
	}

	public static String getNotBookLocalPath(String bookId, String fileUrl) { // 路径：/feishu/notbooks/sdfsesge2w3r3wr/chapter
		String notLocalPath = getNotBookZip(bookId, fileUrl);
		int pos = notLocalPath.lastIndexOf(".");
		return notLocalPath.substring(0, pos);
	}

	public static String getNotBookRootPath(String bookId) {
		return getNotBookDir() + File.separator + bookId;
	}

	public static String getChapterZipLocalPath(String bookId, String chapterId, String chapterContentUrl) {
		String nooBookDir = getNotBookDir();
		int pos = chapterContentUrl.lastIndexOf(File.separator);
		String fileName = chapterContentUrl.substring(pos + 1);
		String bookIdAndChapter = bookId + File.separator + chapterId;
		return nooBookDir + File.separator + bookIdAndChapter + File.separator + fileName;
	}

	public static String getChapterRootDir(String bookId, String chapterId) {
		return getNotBookDir() + File.separator + bookId + File.separator + chapterId;
	}

	public static String getChapterPaperListJson(String bookId, String chapterId) {
		return getFullPathFile(getChapterRootDir(bookId, chapterId) + "/paperList.js");
	}

	public static String getQuestionRootDir(String bookId, String chapterId, String questionId) {
		return getChapterRootDir(bookId, chapterId) + File.separator + "questions" + File.separator + questionId;
	}

	public static String getChapterContentDir(String bookId, String chapterId, String chapterContentUrl) {
		String chapterLocalPath = getChapterZipLocalPath(bookId, chapterId, chapterContentUrl);
		int pos = chapterLocalPath.lastIndexOf(".");
		return chapterLocalPath.substring(0, pos);
	}

	public static boolean mkdirParentDir(File file) {
		File parentDir = file.getParentFile();
		if (null != parentDir && !parentDir.exists()) {
			if (!parentDir.mkdirs())
				return false;
		}
		return true;
	}

	public static void createNomediaFileUnderMain() {
		File file = new File(getAppRootPath());
		if (file.exists()) {
			File file2 = new File(getAppRootPath() + File.separator + ".nomedia");
			if (!file2.exists()) {
				try {
					file2.createNewFile();
				} catch (IOException e) {
					Logger.error("create .nomedia file error:" + e.getMessage());
				}
			}
		}
	}

	public static void createGBCheckFile() {
		String checkFile = getAppRootPath() + File.separator + ".checkfile";
		File file = new File(checkFile);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			Logger.error("create GB check file error ... :" + e.getMessage(), e);
		}
	}

	public static boolean isCheckGBFileExists() {
		String checkFile = getAppRootPath() + File.separator + ".checkfile";
		return FileHelper.isFileExists(checkFile);
	}

	public static void writeToLocalFile(InputStream is, String localPath) throws FileNotFoundException, IOException {
		File file = new File(localPath);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}

			byte[] buffer = new byte[4096];
			int readLen = -1;
			while ((readLen = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readLen);
			}
		} catch (Exception e) {

		} finally {
			if (fos != null) {
				fos.flush();
				fos.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * @return Up366Mobile/vcourse
	 */
	public static String getVCourseRootDir() {
		return getAppRootPath() + File.separator + "vcourse";
	}

	public static String getVCourseRootPath(String courseId) {
		return getVCourseRootDir() + File.separator + courseId;
	}

	public static String getFileNameFromDownUrl(String downUrl) {
		int pos = downUrl.lastIndexOf(File.separator);
		return downUrl.substring(pos + 1);
	}
}
