(ns user)
(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)
(import 'org.opencv.core.Core
        'org.opencv.highgui.Highgui)
