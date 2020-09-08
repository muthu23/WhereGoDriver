# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Appoets\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and trip by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings

#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn privilege.aviva.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.daimajia.androidanimations.**
-dontwarn com.parse.**
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
  public <init>(...);
}
-keep public class privilege.aviva.** { *; }

# firebase
#-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.apache.**
-dontwarn org.w3c.dom.**


-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }

#-keepattributes Signature
#-keepattributes *Annotation*
-keepclasseswithmembers class * {
    public static FirebaseAuth getInstance ();
}
-keep class com.google.firebase.auth.FirebaseAuth
-keep class !com.my.package.** { *; }

-keep class android.support.design.widget.** { *; }
-keep interface android.support.design.widget.** { *; }

-keepclassmembers class com.touseel.driver.Bean.** {*; }
-keepclassmembers class com.touseel.driver.FCM.** {*; }
-keepclassmembers class com.touseel.driver.Activity.LoginActivity** {*; }
-keepclassmembers class com.touseel.driver.Activity.RegisterActivityEmail** {*; }
-keepclassmembers class com.touseel.driver.Activity.AccActivity** {*; }
-keepclassmembers class com.touseel.driver.Activity.ForgetPassword** {*; }
-keepclassmembers class com.touseel.driver.Activity.RegisterActivity** {*; }

