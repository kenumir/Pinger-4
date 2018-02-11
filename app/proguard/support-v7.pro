# Samsung shit workaround
# see https://code.google.com/p/android/issues/detail?id=78377
# see also: https://code.google.com/p/android/issues/detail?id=78377#c322
# wzięte z VLC: https://code.videolan.org/videolan/vlc-android/blob/master/vlc-android/proguard.cfg#L22
-keepattributes **
-keep class !android.support.v7.view.menu.**,!android.support.design.internal.NavigationMenu,!android.support.design.internal.NavigationMenuPresenter,!android.support.design.internal.NavigationSubMenu, android.support.** {*;}
