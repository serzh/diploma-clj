(ns simple-sample-opencv.core
  (:import [org.opencv.core Mat MatOfByte]
           [org.opencv.highgui Highgui VideoCapture]
           [javax.imageio ImageIO]
           [java.io ByteArrayInputStream]
           [javax.swing JFrame JLabel ImageIcon]
           [java.awt Dimension]))

(defn buf-img [^Mat mat]
  (let [mat-of-byte (MatOfByte.)]
    (Highgui/imencode ".jpg" mat mat-of-byte)
    (-> (.toArray mat-of-byte)
        (ByteArrayInputStream.)
        (ImageIO/read))))

(defn create-frame [w h]
  (doto (JFrame.)
    (.setSize (Dimension. w h))
    (.setVisible true)))

(defn reset-frame! [frame]
  (.removeAll (.getContentPane frame)))

(defn show-mat! [mat frame]
  (let [buf (buf-img mat)
        g (.getGraphics frame)]
    (.drawImage g buf 0 0 nil)))

(defn get-one-frame [src]
  (let [vc (VideoCapture. src)
        mat (Mat.)]
    (.read vc mat)
    mat))

(defn video-seq [^VideoCapture vc]
  (lazy-seq
   (let [mat (Mat.)
         c (.read vc mat)]
     (when c
       (cons mat (video-seq vc))))))

(defn capture [src]
  (VideoCapture. src))

(defn play [seq frame]
  (doseq [f seq] (show-mat! f frame)))
