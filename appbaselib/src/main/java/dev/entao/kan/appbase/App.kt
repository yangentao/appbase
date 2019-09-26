@file:Suppress(
    "DEPRECATION",
    "MemberVisibilityCanBePrivate",
    "unused",
    "ClassName",
    "ObjectPropertyName"
)

package dev.entao.kan.appbase

import android.annotation.TargetApi
import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import dev.entao.kan.appbase.ex.color
import dev.entao.kan.http.Http
import dev.entao.kan.log.Yog
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by entaoyang@163.com on 2017-03-31.
 */

object App {
    var themeColor = 0xFF34C4AA.color

    private var _inst: Application? = null
    val inst: Application
        get() {
            if (_inst == null) {
                Log.e("app", "You Need invoke App.init() first!")
            }
            return _inst!!
        }
    val context: Context get() = inst

    val hasInst: Boolean get() = _inst != null

    fun init(inst: Application) {
        if (_inst != null) {
            return
        }
        this._inst = inst
        Yog.init(inst)
        Thread.setDefaultUncaughtExceptionHandler { _, ex ->
            ex.printStackTrace()
            Yog.e(ex)
            System.exit(-1)
        }
        Http.appContext = inst
    }

    val debug: Boolean by lazy {
        0 != (inst.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)
    }
    val isDebug: Boolean get() = debug

    val resolver: ContentResolver get() = inst.contentResolver

    val contentResolver: ContentResolver get() = inst.contentResolver

    val resource: Resources get() = inst.resources

    val displayMetrics: DisplayMetrics get() = inst.resources.displayMetrics

    val metaData: Bundle
        get() {
            val ai = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            return ai.metaData
        }


    val appInfo: ApplicationInfo get() = inst.applicationInfo

    val iconLauncher: Int get() = inst.applicationInfo.icon

    val iconBitmap: Bitmap?
        get() {
            return getAppIcon(inst.packageManager, inst.packageName)
        }

    val density: Float get() = inst.resources.displayMetrics.density

    val scaledDensity: Float get() = inst.resources.displayMetrics.scaledDensity

    val appName: String get() = inst.applicationInfo.loadLabel(inst.packageManager).toString()

    val packageName: String get() = inst.packageName

    val packageInfo: PackageInfo get() = inst.packageManager.getPackageInfo(inst.packageName, 0)

    val versionCode: Int get() = packageInfo.versionCode

    val versionName: String get() = packageInfo.versionName

    val sdkVersion: Int get() = Build.VERSION.SDK_INT

    val modelBuild: String get() = Build.MODEL


    val screenWidthPx: Int get() = displayMetrics.widthPixels

    val screenHeightPx: Int get() = displayMetrics.heightPixels

    val downloadManager: DownloadManager
        get() {
            return inst.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        }

    val telephonyManager: TelephonyManager
        get() {
            return inst.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }


    val powerManager: PowerManager
        get() {
            return inst.getSystemService(Context.POWER_SERVICE) as PowerManager
        }

    val getKeyguardManager: KeyguardManager
        get() {
            return inst.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        }

    val connectivityManager: ConnectivityManager
        get() {
            return inst.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

    val keyguardManager: KeyguardManager
        get() {
            return inst.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        }

    val notificationManager: NotificationManager
        get() {
            return inst.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

    val prefer: Prefer by lazy { Prefer("app_global_prefer") }


    fun openOrCreateDatabase(name: String): SQLiteDatabase {
        return inst.openOrCreateDatabase(name, 0, null)
    }

    val sdAppRoot: File
        get() {
            //TODO Maybe null
            return inst.getExternalFilesDir(null)!!
        }

    fun px2dp(px: Int): Int {
        return (px / density + 0.5f).toInt()
    }

    fun px2dpF(px: Float): Float {
        return px / density
    }

    fun sp2px(spValue: Float): Int {
        return (spValue * scaledDensity + 0.5f).toInt()
    }

    fun sp2pxF(spValue: Float): Float {
        return spValue * scaledDensity
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2pxF(dpValue: Float): Float {
        return dpValue * density
    }

    fun dp2px(dpValue: Int): Int {
        return if (dpValue >= 0) {
            (dpValue * density + 0.5f).toInt()
        } else {
            -((-dpValue * density + 0.5f).toInt())
        }
    }


    @Throws(FileNotFoundException::class)
    fun openStream(uri: Uri): InputStream? = contentResolver.openInputStream(uri)


    // 获取ApiKey
    fun getMetaValue(context: Context, metaKey: String): String? {
        try {
            val ai = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            return ai.metaData.getString(metaKey)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    fun metaValue(metaKey: String): String? {
        return getMetaValue(inst, metaKey)
    }


    // 单位兆M
    val memLimit: Int by lazy {
        (inst.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).memoryClass
    }

    val isNetworkConnected: Boolean
        get() {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }


    fun systemService(name: String): Any? {
        return inst.getSystemService(name)
    }


    fun showInputMethod(view: View) {
        val imm = inst.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }


    fun installShortcut(
        name: String,
        imageRes: Int,
        cls: Class<*>,
        exKey: String,
        exValue: String
    ) {
        val it = Intent(Intent.ACTION_MAIN)
        it.addCategory(Intent.CATEGORY_LAUNCHER)
        it.setClass(inst, cls)
        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        if (exKey.isNotEmpty()) {
            it.putExtra(exKey, exValue)
        }
        installShortcut(name, imageRes, it)
    }

    @Suppress("DEPRECATION", "LocalVariableName")
    private fun installShortcut(name: String, imageRes: Int, intent: Intent) {
        val ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT"
        val addShortcutIntent = Intent(ACTION_INSTALL_SHORTCUT)
        // 不允许重复创建
        addShortcutIntent.putExtra("duplicate", false)// 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)
        addShortcutIntent.putExtra(
            Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
            Intent.ShortcutIconResource.fromContext(inst, imageRes)
        )
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent)
        inst.sendBroadcast(addShortcutIntent)
    }

    fun installApk(apkUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        try {
            inst.startActivity(intent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun openUrl(url: String) {
        try {
            val i = Intent(Intent.ACTION_VIEW)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.data = Uri.parse(url)
            inst.startActivity(i)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun drawable(@DrawableRes resId: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resource.getDrawable(resId, inst.theme)
        } else {
            resource.getDrawable(resId)
        }
    }

    object files {

        fun ensureDir(root: File, dir: String): File {
            val f = File(root, dir)
            if (!f.exists()) {
                f.mkdirs()
                f.mkdir()
                try {
                    File(f, ".nomedia").createNewFile()
                } catch (e: IOException) {
                    Log.e("app", f.absolutePath)
                    e.printStackTrace()
                }
            }
            return f
        }

        object app {
            val filesDir: File = App.inst.filesDir

            val cacheDir: File = App.inst.cacheDir

            fun docFile(filename: String): File {
                return File(filesDir, filename)
            }

            fun cacheFile(fileName: String): File {
                return File(cacheDir, fileName)
            }

            fun dir(dirName: String): File {
                return ensureDir(filesDir, dirName)
            }

            fun userDir(userName: String): File {
                val a = userName.map { it.toInt().toString(16) }.joinToString("")
                return dir(a)
            }

            fun userFile(userName: String, fileName: String): File {
                return File(userDir(userName), fileName)
            }


            val logDir: File
                get() {
                    return dir("xlog")
                }

            fun log(file: String): File {
                return File(logDir, file)
            }

            val tempDir: File
                get() {
                    return cacheDir
                }


            fun tempFile(): File {
                return tempFile(".tmp")
            }

            fun tempFile(ext: String): File {
                var dotExt = ".tmp"
                if (ext.isNotEmpty()) {
                    dotExt = if (ext[0] == '.') {//.x
                        ext
                    } else {
                        ".$ext"
                    }
                }
                val fmt = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault())
                val s = fmt.format(Date(System.currentTimeMillis()))
                return temp(s + dotExt)
            }

            fun temp(file: String): File {
                return File(tempDir, file)
            }

            val imageDir: File
                get() {
                    return dir("image")
                }

            fun image(file: String): File {
                return File(imageDir, file)
            }
        }


        object ex {
            val filesDir: File = App.inst.getExternalFilesDir(null)!!
            val cacheDir: File = App.inst.externalCacheDir!!

            fun cacheFile(fileName: String): File {
                return File(cacheDir, fileName)
            }

            fun cacheDir(dirName: String): File {
                return ensureDir(cacheDir, dirName)
            }

            fun dir(dirName: String): File {
                return ensureDir(filesDir, dirName)
            }

            fun userDir(userName: String): File {
                val a = userName.map { it.toInt().toString(16) }.joinToString("")
                return dir(a)
            }

            fun userFile(userName: String, fileName: String): File {
                return File(userDir(userName), fileName)
            }

            val logDir: File
                get() {
                    return dir("xlog")
                }

            fun log(file: String): File {
                return File(logDir, file)
            }

            val tempDir: File
                get() {
                    return cacheDir
                }

            fun tempFile(): File {
                return tempFile(".tmp")
            }

            fun tempFile(ext: String): File {
                var dotExt = ".tmp"
                if (ext.isNotEmpty()) {
                    dotExt = if (ext[0] == '.') {//.x
                        ext
                    } else {
                        ".$ext"
                    }
                }
                val fmt = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault())
                val s = fmt.format(Date(System.currentTimeMillis()))
                return temp(s + dotExt)
            }

            fun temp(file: String): File {
                return File(tempDir, file)
            }

            val imageDir: File
                get() {
                    return dir("image")
                }

            fun image(file: String): File {
                return File(imageDir, file)
            }
        }

        object pub {
            val root: File get() = Environment.getExternalStorageDirectory()
            val downloads: File get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val music: File get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            val pictures: File get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val dcim: File get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val documents: File
                @TargetApi(Build.VERSION_CODES.KITKAT)
                get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val movies: File get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        }
    }
}

val Files: App.files get() = App.files
